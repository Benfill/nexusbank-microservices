import axios from 'axios';
import Customer from '../types/customerTypes';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const getCustomers = async () => {
  const response = await axios.get(`${API_BASE_URL}/customers?size=100`);
  return response.data;
};

export const createCustomer = async (customerData: Customer) => {
  const response = await axios.post(`${API_BASE_URL}/customers`, customerData);
  return response.data;
};

export const updateCustomer = async (id:number, customerData: Customer) => {
  const response = await axios.put(`${API_BASE_URL}/customers/${id}`, customerData);
  return response.data;
};

export const deleteCustomer = async (item:Customer) => {
  const response = await axios.delete(`${API_BASE_URL}/customers/${item.id}`);
  return response.data;
};