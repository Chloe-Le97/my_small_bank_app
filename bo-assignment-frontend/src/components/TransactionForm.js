import React, {useEffect, useState} from 'react';
import transactionService from '../services/transactionService';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { useSnackbar } from 'notistack';
import './Transaction.style.css';

const TransactionForm = ({customers}) => {

    const [senderID, setSenderID] = useState('');
    const [recieverID, setRecieverID] = useState('');
    const [amount, setAmount] = useState('');
    const { enqueueSnackbar, closeSnackbar } = useSnackbar();

    const makeTransaction = async (event) => {
        event.preventDefault();

        var today = new Date();

        const transactionObject = {
            senderID: senderID,
            recieverID: recieverID,
            amount: Number(amount),
            dateTransaction: today.toISOString(),
        }

        if(senderID==''||recieverID==''||amount==''){
            return enqueueSnackbar('The fields are missing, please provide all fields to make transaction',{variant: "error"});
        }

        if(isNaN(Number(amount))){
            return enqueueSnackbar('Please type number for amount of money',{variant: "error"});
        }

        if(recieverID==senderID){
            return enqueueSnackbar('Cannot make transaction to the same account',{variant: "error"})
        }
        const sender = customers.find(customer => customer.id==senderID);
        if(sender.status=='BLOCKED'){
            return enqueueSnackbar('The sender account is blocked, please activate account to make transaction',{variant: "warning"})
        }

        try{
            await transactionService.createTransaction(transactionObject);
            enqueueSnackbar('Payment successfully',{variant: "success"});
            setSenderID('');
            setRecieverID('');
            setAmount('');
        }catch(error){
            enqueueSnackbar('Something went wrong, please try again after few minutes',{variant: "error"});
        }
        
    }

    return(
        <div>
            <form className='transaction_form' onSubmit={makeTransaction}>
                <h2>Transaction Form</h2>
                <TextField className='transaction_input' variant="outlined" label='Sender ID' value={senderID} onChange={(e)=>setSenderID(e.target.value)}></TextField>
                <TextField className='transaction_input'variant="outlined" label='Reciever ID' value={recieverID} onChange={(e)=>setRecieverID(e.target.value)}></TextField>
                <TextField className='transaction_input' variant="outlined" label='Amount of money' value={amount} onChange={(e)=>setAmount(e.target.value)}></TextField>
                <Button className='submit_transaction' variant="contained" color="primary" type='submit'>Make Transaction</Button>
            </form>
        </div>
    )

}

export default TransactionForm;