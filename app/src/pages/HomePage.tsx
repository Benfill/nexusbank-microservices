import React, { useState } from "react";
import { deleteCustomer, getCustomers } from "../api/customerService";
import Table from "../components/Table";
import { deleteAccount, getAccounts } from "../api/accountService";
import Customer from "../types/customerTypes";
import Alert from "@mui/material/Alert";
import Account from "../types/accountTypes";
import { accountColumns, customerColumns } from "../types/columns";

const HomePage: React.FC = () => {
  const [customers, setCustomers] = useState([]);
  const [accounts, setAccounts] = useState([]);
  const [isShown, setIsShowen] = useState(false);
  const [message, setMessage] = useState("");
  const [severity, setSeverity] = useState<"success" | "error" | undefined>();

  React.useEffect(() => {
    const fetchCustomers = async () => {
      const data = await getCustomers();
      setCustomers(data);
    };
    const fetchAccount = async () => {
      const data = await getAccounts();
      setAccounts(data);
    };

    fetchCustomers();
    fetchAccount();
  }, [isShown]);

  const customerDelete = async (customer: Customer) => {
    try {
      await deleteCustomer(customer);
      setIsShowen(true);
      setMessage("Customer deleted successfully");
      setSeverity("success");
    } catch (error: any) {
      // Handle any errors that occur during deletion
      console.error("Failed to delete customer:", error);
      setIsShowen(true);
      setMessage(error.response.data.message);
      setSeverity("error");
    } finally {
      setTimeout(() => {
        setIsShowen(false);
        setMessage("");
        setSeverity(undefined);
      }, 5000);
    }
  };

  const accountDelete = async (account: Account) => {
    try {
      await deleteAccount(account);
      setIsShowen(true);
      setMessage("Account deleted successfully");
      setSeverity("success");
    } catch (error: any) {
      // Handle any errors that occur during deletion
      console.error("Failed to delete account:", error);
      setIsShowen(true);
      setMessage(error.response.data.message);
      setSeverity("error");
    } finally {
      setTimeout(() => {
        setIsShowen(false);
        setMessage("");
        setSeverity(undefined);
      }, 5000);
    }
  };

  return (
    <div className="px-40 py-10 flex flex-col gap-4">
      {isShown && <Alert severity={severity}> {message && message} </Alert>}
      <h1 className="text-center font-extrabold text-xl">Dashboard</h1>

      <div>
        <h2>Customers</h2>
        <Table
          columns={customerColumns}
          data={customers}
          onDelete={customerDelete}
        ></Table>
      </div>

      <div>
        <h2>Accounts</h2>
        <Table
          columns={accountColumns}
          data={accounts}
          onDelete={accountDelete}
        ></Table>
      </div>
    </div>
  );
};

export default HomePage;
