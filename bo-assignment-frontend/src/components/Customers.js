import React, {useState, useEffect} from 'react';
import {Link} from "react-router-dom";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import './Customers.style.css'

const Customers = ({customers}) =>{
    if(customers.length<1){return(<div className='not_found_customer'>Customer not found</div>)};

    return (
      <TableContainer className='customers_table_container'>
        <Table aria-label="simple table">
                <TableHead className='customers_table_head'>
                  <TableRow >
                    <TableCell className='customers_table_head_row'>ID</TableCell>
                    <TableCell className='customers_table_head_row'>First name</TableCell>
                    <TableCell className='customers_table_head_row'>Last name</TableCell>
                    <TableCell className='customers_table_head_row'>Status</TableCell>
                    <TableCell className='customers_table_head_row'></TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                {customers.map(customer=>(
                <TableRow key={customer.id} className='customers_table_body_row_container'>
                    <TableCell>{customer.id}</TableCell>
                    <TableCell>{customer.firstName}</TableCell>
                    <TableCell>{customer.lastName}</TableCell>
                    <TableCell>{customer.status}</TableCell>
                    <TableCell><Link to={`/customers/${customer.id}`}>View More</Link></TableCell>
                </TableRow>
            ))}
              </TableBody>
          </Table>
      </TableContainer>
    )
}

export default Customers