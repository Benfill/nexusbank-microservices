import React, { useState } from "react";
import Form, { FormField } from "../components/Form";
import { getCustomers } from "../api/customerService";
import { Alert } from "@mui/material";
import Customer from "../types/customerTypes";
import { createAccount } from "../api/accountService";
import { useNavigate } from "react-router-dom";

const AccountPage = () => {
  let navigate = useNavigate();
  const [customers, setCustomers] = useState([]);
  const [isShown, setIsShowen] = useState(false);
  const [message, setMessage] = useState("");
  const [severity, setSeverity] = useState<"success" | "error" | undefined>();

  React.useEffect(() => {
    const fetchCustomers = async () => {
      const data = await getCustomers();
      setCustomers(data);
    };

    fetchCustomers();
  }, [isShown]);

  const fields: FormField[] = [
    { name: "balance", label: "Balance", type: "float" },
    {
      name: "customerId",
      label: "customer",
      type: "select",
      options: getOptions(),
    },
    {
      name: "type",
      label: "Account Type",
      type: "select",
      options: [
        { value: "SAVING", label: "Saving" },
        { value: "CURRENT", label: "Current" },
      ],
    },
  ];

  function getOptions(): { value: number; label: string }[] {
    let array: { value: number; label: string }[] = [];

    customers.map((customer: Customer) => {
      array.push({ value: customer.id, label: customer.name });
      return customer;
    });

    return array;
  }

  const handleSubmit = async (data: Record<string, string>) => {
    console.log("Form Data:", data);
    try {
      await createAccount(data);
      setIsShowen(true);
      setMessage("Account created successfully");
      setSeverity("success");

      setTimeout(() => {
        setIsShowen(false);
        setMessage("");
        setSeverity(undefined);
        navigate("/");
      }, 2000);
    } catch (error) {
      // Handle any errors that occur during deletion
      console.error("Failed to create account: ", error);
      setIsShowen(true);
      setMessage(error!.response!.data!.message!);
      setSeverity("error");
    }
  };

  return (
    <div>
      {isShown && <Alert severity={severity}> {message && message} </Alert>}
      <Form fields={fields} title="Account Form" onSubmit={handleSubmit} />
    </div>
  );
};

export default AccountPage;
