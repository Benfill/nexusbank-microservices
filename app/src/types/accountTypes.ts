import Customer from "./customerTypes";

export default interface Account {
    id:number;
    balance:string;
    type:string;
    customer:Partial<Customer>
    createdAt:string;
    updatedAt:string;
}