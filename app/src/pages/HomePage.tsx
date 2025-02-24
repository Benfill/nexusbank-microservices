import React from "react";
import { deleteCustomer, getCustomers } from "../api/customerService";
import Table, { Column } from "../components/Table";
import { getAccounts } from "../api/accountService";
import Customer from "../types/customerTypes";

const HomePage: React.FC = () => {
  const [customers, setCustomers] = React.useState([]);
  const [accounts, setAccounts] = React.useState([]);

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
  }, []);

  const customerDelete = async (customer: Customer) => {
    try {
      await deleteCustomer(customer); // or whatever parameter your deleteCustomer function expects
      // Optionally refresh your data or show a success message
    } catch (error) {
      // Handle any errors that occur during deletion
      console.error("Failed to delete customer:", error);
      // Optionally show an error message to the user
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
  ];

  return (
    <div className="px-40 py-10 flex flex-col gap-10">
      <h1 className="text-center">Dashboard</h1>

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
        <Table columns={accountColumns} data={accounts}></Table>
      </div>
    </div>
  );
};

export default HomePage;
