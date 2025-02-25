import React, { useState } from "react";
import Form, { FormField } from "../components/Form";
import { useNavigate } from "react-router-dom";
import { Alert } from "@mui/material";
import { createCustomer } from "../api/customerService";

const CustomerPage = () => {
  let navigate = useNavigate();
  const [isShown, setIsShowen] = useState(false);
  const [message, setMessage] = useState("");
  const [severity, setSeverity] = useState<"success" | "error" | undefined>();

  const fields: FormField[] = [
    { name: "name", label: "Name", type: "text" },
    { name: "email", label: "Email", type: "email" },
  ];

  const handleSubmit = async (data: Record<string, string>) => {
    console.log("Form Data:", data);
    try {
      await createCustomer(data);
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

export default CustomerPage;
