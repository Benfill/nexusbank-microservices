// GenericTable.tsx
import React from "react";

type ColumnType = "text" | "status" | "actions" | "image" | "boolean" | "date";

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

// Helper type to ensure nested object access with depth limit
// type NestedKeyOf<ObjectType extends object, Depth extends number = 3> = [
//   Depth,
// ] extends [never]
//   ? never
//   : {
//       [Key in keyof ObjectType &
//         (string | number)]: ObjectType[Key] extends object
//         ?
//             | Key
//             | `${Key & string}.${NestedKeyOf<ObjectType[Key], Depth extends 0 ? never : [-1, 0, 1][Depth]> & string}`
//         : Key;
//     }[keyof ObjectType & (string | number)];

const Table = <T extends { id?: string | number }>({
  columns,
  data,
  onEdit,
  onDelete,
}: GenericTableProps<T>): JSX.Element => {
  // Helper function to get nested object values using dot notation
  const getNestedValue = (obj: any, path: string): any => {
    return path.split(".").reduce((acc, part) => acc && acc[part], obj);
  };

  // Helper function to render cell content based on type
  const renderCell = (item: T, column: Column): React.ReactNode => {
    const value = getNestedValue(item, column.key);

    switch (column.type) {
      case "status":
        const isActive = String(value).toLowerCase() === "active";
        return (
          <span
            className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
              isActive
                ? "bg-green-100 text-green-800"
                : "bg-red-100 text-red-800"
            }`}
          >
            {value}
          </span>
        );
      case "actions":
        return (
          <div className="flex space-x-2">
            {onEdit && (
              <button
                onClick={() => {
                  try {
                    onEdit(item);
                  } catch (error) {
                    console.error("Error during edit:", error);
                  }
                }}
                className="px-4 py-2 font-medium text-white bg-blue-600 rounded-md hover:bg-blue-500 focus:outline-none focus:shadow-outline-blue active:bg-blue-600 transition duration-150 ease-in-out"
              >
                Edit
              </button>
            )}
            {onDelete && (
              <button
                onClick={async () => {
                  try {
                    await onDelete(item);
                  } catch (error) {
                    console.error("Error during delete:", error);
                  }
                }}
                className="px-4 py-2 font-medium text-white bg-red-600 rounded-md hover:bg-red-500 focus:outline-none focus:shadow-outline-red active:bg-red-600 transition duration-150 ease-in-out"
              >
                Delete
              </button>
            )}
          </div>
        );
      case "image":
        return (
          <img
            src={value}
            alt={String(value)}
            className="h-10 w-10 rounded-full"
          />
        );
      case "boolean":
        return value ? "✓" : "✗";
      case "date":
        return new Date(value).toLocaleDateString();
      default:
        return value;
    }
  };

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        <thead>
          <tr>
            {columns.map((column) => (
              <th
                key={column.key}
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                {column.label}
              </th>
            ))}
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {data.map((item, index) => (
            <tr key={item.id || index}>
              {columns.map((column) => (
                <td key={column.key} className="px-6 py-4 whitespace-nowrap">
                  {renderCell(item, column)}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Table;
