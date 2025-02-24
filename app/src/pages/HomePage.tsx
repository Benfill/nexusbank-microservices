import React, { useState } from "react";
import { deleteCustomer, getCustomers } from "../api/customerService";
import Table, { Column } from "../components/Table";
import { deleteAccount, getAccounts } from "../api/accountService";
import Customer from "../types/customerTypes";
import Alert from "@mui/material/Alert";
import Account from "../types/accountTypes";

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
    } catch (error) {
      // Handle any errors that occur during deletion
      console.error("Failed to delete customer:", error);
      setIsShowen(true);
      setMessage("Failed to delete customer:" + error);
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
    } catch (error) {
      // Handle any errors that occur during deletion
      console.error("Failed to delete account:", error);
      setIsShowen(true);
      setMessage("Failed to delete account:" + error);
      setSeverity("error");
    } finally {
      setTimeout(() => {
        setIsShowen(false);
        setMessage("");
        setSeverity(undefined);
      }, 5000);
    }
  };

  const customerColumns: Column[] = [
    {
      key: "id",
      label: "ID",
      type: "text",
    },
    {
      key: "name",
      label: "name",
      type: "text",
    },
    {
      key: "email",
      label: "email",
      type: "text",
    },
    {
      key: "createdAt",
      label: "createdAt",
      type: "date",
    },
    {
      key: "updatedAt",
      label: "updatedAt",
      type: "date",
    },
    {
      key: "actions",
      label: "Actions",
      type: "actions",
    },
  ];

  const accountColumns: Column[] = [
    {
      key: "id",
      label: "ID",
      type: "text",
    },
    {
      key: "customer.name",
      label: "Customer Name",
      type: "text",
    },
    {
      key: "balance",
      label: "Balance",
      type: "text",
    },
    {
      key: "type",
      label: "Type",
      type: "text",
    },
    {
      key: "createdAt",
      label: "Created At",
      type: "date",
    },
    {
      key: "updatedAt",
      label: "Updated At",
      type: "date",
    },
    {
      key: "actions",
      label: "Actions",
      type: "actions",
    },
  ];

  return (
    <div className="px-40 py-10 flex flex-col gap-10">
      <h1 className="text-center">Dashboard</h1>
      {isShown && <Alert severity={severity}> {message && message} </Alert>}

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
