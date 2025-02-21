import axios from 'axios';
import { AccountFormData } from '../components/AccountForm';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const getAccounts = async () => {
  const response = await axios.get(`${API_BASE_URL}/accounts`);
  return response.data;
};

export const createAccount = async (accountData: AccountFormData) => {
  const response = await axios.post(`${API_BASE_URL}/accounts`, accountData);
  return response.data;
};