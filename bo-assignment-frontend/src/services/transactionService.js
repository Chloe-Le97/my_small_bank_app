import axios from 'axios'
const baseUrl = 'http://localhost:8080'

const createTransaction = async (newTransaction) => {
    const response = await axios.post(`${baseUrl}/transaction`,newTransaction);
    return response.data
}

const getAllTransaction = async () =>{
    const response = await axios.post(`${baseUrl}/transactions/findAll`)
    return response.data
}

const getTransactionSender = async (customerID) => {
    const response = await axios.get(`${baseUrl}/transaction/find/sender`,{params:{senderID: customerID}})
    return response.data
}

const getTransactionReciever = async (customerID) => {
    const response = await axios.get(`${baseUrl}/transaction/find/reciever`,{params:{recieverID: customerID}})
    return response.data
}

export default {createTransaction,getAllTransaction,getTransactionSender, getTransactionReciever}