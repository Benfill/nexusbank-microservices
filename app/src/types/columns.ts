
export type ColumnType = "text" | "status" | "actions" | "image" | "boolean" | "date";

export interface Column {
  key: string;
  label: string;
  type?: ColumnType;
}

export interface GenericTableProps<T> {
  columns: Column[];
  data: T[];
  onEdit?: (item: T) => void;
  onDelete?: (item: T) => void;
}

export const customerColumns: Column[] = [
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

  export const accountColumns: Column[] = [
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