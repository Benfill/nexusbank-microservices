import Account from "./accountTypes";
import Customer from "./customerTypes";

// Define the type for a form field
export type FormField = {
  name: string;
  label: string;
  type: "text" | "email" | "select" | "float"; // Add more types if needed
  options?: { value: string | number; label: string }[]; // Only for select fields
  value?: string | number;
};

// Define the props for the Form component
export type FormProps = {
  fields: FormField[];
  title: string;
  onSubmit: (data: Account | Customer | any) => void;
};