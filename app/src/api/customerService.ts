import axios from 'axios';
import { CustomerFormData } from '../components/CustomerForm';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const getCustomers = async () => {
  const response = await axios.get(`${API_BASE_URL}/customers`);
  return response.data;
};

export const createCustomer = async (customerData: CustomerFormData) => {
  const response = await axios.post(`${API_BASE_URL}/customers`, customerData);
  return response.data;
};