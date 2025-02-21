import React from "react";
import { getCustomers } from "../api/customerService";
import Customer from "../types/customerTypes";

const HomePage: React.FC = () => {
  const [customers, setCustomers] = React.useState([]);

  React.useEffect(() => {
    const fetchCustomers = async () => {
      const data = await getCustomers();
      setCustomers(data);
    };
    fetchCustomers();
  }, []);

  return (
    <div>
      <h1>Dashboard</h1>
      <ul>
        {customers.map((customer: Customer) => (
          <li key={customer.id}>{customer.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default HomePage;
