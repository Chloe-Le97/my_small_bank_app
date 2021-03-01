import React, {useState, useEffect} from 'react';
import {useParams} from 'react-router-dom';
import customerService from '../services/customerService';
import transactionService from '../services/transactionService';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Modal from "@material-ui/core/Modal";
import { makeStyles } from "@material-ui/core/styles";
import { useSnackbar } from 'notistack';
import './Customer.style.css'

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

const Customer = ({allCustomers,setAllCustomers,setFilterCustomers}) =>{

    const classes = useStyles();
    const { enqueueSnackbar, closeSnackbar } = useSnackbar();

    const id = useParams().id;

    const [customer,setCustomer] = useState([]);
    const [sentTransaction, setSentTransaction] = useState([]);
    const [recievedTransaction, setRecievedTransaction] = useState([]);
    const [recieverID, setRecieverID] = useState('');
    const [amount, setAmount] = useState('');
    const [openTransaction, setOpenTransaction] = useState(false);
    const [modalStyle] = useState(getModalStyle);

    async function getCustomer(){
        customerService.getCustomerByID(id).then(customer=>setCustomer(customer))
    }

    async function getTransactionSender(){
        transactionService.getTransactionSender(id).then(transaction=>setSentTransaction(transaction))
    }

    async function getTransactionReciever(){
        transactionService.getTransactionReciever(id).then(transaction=>setRecievedTransaction(transaction))
    }


    useEffect(()=>{
        getCustomer();
        getTransactionReciever();
        getTransactionSender();
        setFilterCustomers(allCustomers)
    },[])

    const block = async (customerId) => {
        const customerInfo = await customerService.blockCustomer(customerId);
        const all = await customerService.getCustomers();
        setCustomer(customerInfo)
        setAllCustomers(all)
        setFilterCustomers(all)
    }

    const activate = async (customerId) => {
        const customerInfo = await customerService.activateCustomer(customerId);
        const all = await customerService.getCustomers();
        setCustomer(customerInfo)
        setAllCustomers(all)
        setFilterCustomers(all)
    }

    const makeTransaction = async (event) =>{
        event.preventDefault();
        var today = new Date();

        const transactionObject = {
            senderID: customer.id.toString(),
            recieverID: recieverID.toString(),
            amount: Number(amount),
            dateTransaction: today.toISOString(),
        }

        if(recieverID==''||amount==''){
            return enqueueSnackbar('The fields are missing, please provide all fields to make transaction',{variant: "error"});
        }

        if(isNaN(Number(amount))){
            return enqueueSnackbar('Please type number for amount of money',{variant: "error"});
        }

        if(recieverID==id){
            return enqueueSnackbar('Cannot make transaction to the same account',{variant: "error"});
        }

        try{    
            const customerInfo = await transactionService.createTransaction(transactionObject);
            enqueueSnackbar('Payment successfully',{variant: "success"});
            setCustomer(customerInfo);
            getTransactionReciever();
            getTransactionSender();
            setAmount('');
            setRecieverID('');
        }catch(err){
            enqueueSnackbar('Something went wrong, please try again after few minutes',{variant: "error"});
        }
 
    }

    const convertTime = (formated_Date) =>{
        const date = new Date(formated_Date);
        return `${date.toLocaleDateString()}`
    }

    return(
        <div className='customer'>
            <h2>{customer.firstName} {customer.lastName}</h2>
            <h4>Personal ID: {customer.personalId}</h4>
            <div><strong>Account Balance:</strong> {customer.balance} €</div>
            <div className='status'><strong>Status:</strong> {customer.status} &nbsp;
                {customer.status=='ACTIVE'?
                (<>
                    <Button variant="contained" color="secondary" onClick={()=>block(customer.id)}>BLOCK</Button>
                <div>
                    <Button className='transaction_btn' variant="contained" color="primary" onClick={()=>setOpenTransaction(true)} >Make transaction</Button>

                    <Modal open={openTransaction} className="modal" onClose={() => setOpenTransaction(false)}>

                        <div style={modalStyle} className={classes.paper}>
                            <form className='transaction_form' onSubmit={makeTransaction}>
                                <h2>Transaction Form</h2>
                                <TextField className='transaction_input' variant="outlined" label="Reciever Account ID" value={recieverID} onChange={(e)=> setRecieverID(e.target.value)}></TextField>
                                <TextField className='transaction_input' variant="outlined" label="Amount" value={amount} onChange={(e)=> setAmount(e.target.value)}></TextField>
                                <Button className='submit_transaction' variant="contained" color="primary" type='submit'>Make payment</Button>
                            </form>
                        </div>

                    </Modal>

                </div>
                </>
                )
                :(<Button variant="contained" color="primary" onClick={()=>activate(customer.id)}>ACTIVATE</Button>)}
            </div>
            <div>
                <h3>Transaction</h3>
                <div>
                    <strong>Sent</strong>
                    {sentTransaction.length<1?
                        (<div className='no_transaction'>This account have not sent any transaction yet</div>)
                        :(<div>
                            <TableContainer className='transaction_table_container'>
                                <Table>
                                    <TableHead>
                                        <TableRow >
                                            <TableCell className='transaction_table_head_row'>First name</TableCell>
                                            <TableCell className='transaction_table_head_row'>Last name</TableCell>
                                            <TableCell className='transaction_table_head_row'>Date</TableCell>
                                            <TableCell className='transaction_table_head_row'>Amount</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                    {sentTransaction.map(transaction=>(
                                        <TableRow key={transaction.id} className='transaction_table_row_container'>
                                                <TableCell className='transaction_table_body_row'>{transaction.firstName}</TableCell>
                                                <TableCell className='transaction_table_body_row'>{transaction.lastName}</TableCell>
                                                <TableCell className='transaction_table_body_row'>{convertTime(transaction.dateTransaction)}</TableCell>
                                                <TableCell className='transaction_table_body_row'>{transaction.amount} €</TableCell>  
                                        </TableRow>
                                    ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                    </div>)}
                    
                </div>
                <div>
                    <strong>Recieved</strong>
                    {recievedTransaction.length<1?
                        (<div className='no_transaction'>This account have not recieved any transaction yet</div>)
                        :(<div>
                            <TableContainer className='transaction_table_container'>
                                <Table>
                                    <TableHead>
                                        <TableRow >
                                            <TableCell className='transaction_table_head_row'>First name</TableCell>
                                            <TableCell className='transaction_table_head_row'>Last name</TableCell>
                                            <TableCell className='transaction_table_head_row'>Date</TableCell>
                                            <TableCell className='transaction_table_head_row'>Amount</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                    {recievedTransaction.map(transaction=>(
                                        <TableRow key={transaction.id} className='transaction_table_row_container'>
                                                <TableCell className='transaction_table_body_row'>{transaction.firstName}</TableCell>
                                                <TableCell className='transaction_table_body_row'>{transaction.lastName}</TableCell>
                                                <TableCell className='transaction_table_body_row'>{convertTime(transaction.dateTransaction)}</TableCell>
                                                <TableCell className='transaction_table_body_row'>{transaction.amount} €</TableCell>  
                                        </TableRow>
                                    ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                    </div>)}
                </div>
            </div>
        </div>
    )
}

export default Customer