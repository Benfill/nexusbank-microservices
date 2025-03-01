import Customer from "../types/customerTypes";

export function getOptions(customers: Customer[]): { value: number; label: string}[] {
    let array: { value: number; label: string }[] = [];

    customers.map((customer: Customer) => {
      array.push({ value: customer.id, label: customer.name });
      return customer;
    });

    return array;
  }