import axios from 'axios'
const baseUrl = 'http://localhost:8080'

const getCustomers = async () => {
  const response = await axios.get(`${baseUrl}/customers/findAll`)
  return response.data
}

const getCustomerByID = async (customerId) => {
    const response = await axios.get(`${baseUrl}/customer/${customerId}/find`)
    return response.data
}   

const getCustomerByName = async (firstName) =>{
    const response = await axios.get(`${baseUrl}/customers/find?firstName=${firstName}`)
    return response.data
}

const blockCustomer = async(customerId) =>{
    const response = await axios.post(`${baseUrl}/customer/${customerId}/block`)
    return response.data
}

const activateCustomer = async(customerId) =>{
    const response = await axios.post(`${baseUrl}/customer/${customerId}/activate`)
    return response.data
}

export default { getCustomers, getCustomerByID, getCustomerByName, blockCustomer, activateCustomer }