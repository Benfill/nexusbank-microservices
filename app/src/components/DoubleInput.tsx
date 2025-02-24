import React from "react";

// Define the props for the DoubleInput component
type DoubleInputProps = {
  name: string;
  label: string;
  required?: boolean;
};

const DoubleInput: React.FC<DoubleInputProps> = ({ name, label, required }) => {
  return (
    <div style={{ display: "flex", gap: "10px" }}>
      <div style={{ flex: 1 }}>
        <label htmlFor={name}>{label}</label>
        <input
          id={name}
          name={name}
          type="float"
          required={required}
          style={{ width: "100%", padding: "8px", boxSizing: "border-box" }}
        />
      </div>
    </div>
  );
};

export default DoubleInput;
