import React from "react";
import {
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
  Button,
  Input,
} from "@mui/material";
import DoubleInput from "./DoubleInput";

// Define the type for a form field
export type FormField = {
  name: string;
  label: string;
  type: "text" | "email" | "select" | "float"; // Add more types if needed
  options?: { value: string | number; label: string }[]; // Only for select fields
};

// Define the props for the Form component
type FormProps = {
  fields: FormField[];
  title: string;
  onSubmit: (data: Record<string, string>) => void;
};

const Form: React.FC<FormProps> = ({ fields, title, onSubmit }) => {
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data: Record<string, string> = {};

    fields.forEach((field) => {
      data[field.name] = formData.get(field.name) as string;
    });

    onSubmit(data);
  };

  return (
    <div className="px-40 py-10 flex flex-col gap-10">
      <h1 className="text-center font-extrabold text-xl">{title}</h1>
      <form onSubmit={handleSubmit}>
        {fields.map((field) => (
          <FormControl key={field.name} fullWidth margin="normal">
            {field.type === "select" ? (
              <>
                <InputLabel>{field.label}</InputLabel>
                <Select name={field.name} label={field.label} required>
                  {field.options!.map((option) => (
                    <MenuItem key={option.value} value={option.value}>
                      {option.label}
                    </MenuItem>
                  ))}
                </Select>
              </>
            ) : (
              <TextField
                name={field.name}
                label={field.label}
                type={field.type}
                fullWidth
                required
              />
            )}
          </FormControl>
        ))}
        <Button type="submit" variant="contained" color="primary">
          Submit
        </Button>
      </form>
    </div>
  );
};

export default Form;
