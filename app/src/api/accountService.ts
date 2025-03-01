import axios from 'axios';
import Account from '../types/accountTypes';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const getAccounts = async () => {
  const response = await axios.get(`${API_BASE_URL}/accounts?size=100`);
  return response.data;
};

export const createAccount = async (accountData: Account) => {
  const response = await axios.post(`${API_BASE_URL}/accounts`, accountData);
  return response.data;
};

export const updateAccount = async (id:number, customerData: Account) => {
  const response = await axios.put(`${API_BASE_URL}/accounts/${id}`, customerData);
  return response.data;
};

export const deleteAccount = async (item:Account) => {
  const response = await axios.delete(`${API_BASE_URL}/accounts/${item.id}`);
  return response.data;
};
