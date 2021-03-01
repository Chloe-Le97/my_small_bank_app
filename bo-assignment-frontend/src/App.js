import React, { useState, useEffect } from 'react';
import customerService from './services/customerService';
import {
  BrowserRouter as Router,
  Switch, Route, Link
} from "react-router-dom";
import Customers from './components/Customers';
import Customer from './components/Customer';
import TransactionForm from './components/TransactionForm';
import Modal from "@material-ui/core/Modal";
import { makeStyles } from "@material-ui/core/styles";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import './App.css';


function getModalStyle() {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

const useStyles = makeStyles((theme) => ({
  paper: {
    position: "absolute",
    width: 300,
    backgroundColor: theme.palette.background.paper,
    border: "2px solid #000",
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
}));


const App = () =>{
  const classes = useStyles();
  const [customers, setCustomers] = useState([]);
  const [filterCustomers, setFilterCustomers] = useState([]);
  const [modalStyle] = useState(getModalStyle);
  const [openTransaction, setOpenTransaction] = useState(false);
  
  useEffect(() => {
    // Create an scoped async function in the hook
    async function getAllCustomers() {
     customerService.getCustomers().then(customers=>{
       setCustomers(customers)
       setFilterCustomers(customers)
      })
    }
    // Execute the created function directly
    getAllCustomers();
},[])

  const filter = (value) =>{
    const filterCustomer = customers.filter(item => item.firstName.toLowerCase().includes(value.toLowerCase()))
    setFilterCustomers(filterCustomer)
  }
  

  return(
    <div className="App">
      <Router>
        <div className='header'>
          <Button variant="contained" color="primary" className='home_btn'><Link to="/" className='home_link'>Home</Link></Button>
        </div>
        <h1>My small Bank</h1>
        <Switch>
              <Route exact path="/">
                  <div xlassName='title'>
                    <Button variant="contained" color="primary" onClick={()=> setOpenTransaction(true)}>Make transaction</Button>
                    <div>
                      <h3>Search for customer</h3>
                      <TextField className='searchCustomer' placeholder='Type customer name' onChange={(e)=> filter(e.target.value)}></TextField>
                    </div>
                  </div>

                <Modal open={openTransaction} className="modal" onClose={() => setOpenTransaction(false)}>
                  <div style={modalStyle} className={classes.paper}>
                    <TransactionForm customers={customers}/>
                  </div>
                </Modal>
                
                <Customers customers={filterCustomers}/>
              </Route>

              <Route path="/customers/:id">
                  <Customer allCustomers={customers} setAllCustomers={setCustomers} setFilterCustomers={setFilterCustomers}/>
              </Route>
        </Switch>
      </Router>
    </div>
  )

}

export default App;
