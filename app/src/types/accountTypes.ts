import Customer from "./customerTypes";

export default interface Account {
    id:number;
    balance:number;
    type:string;
    customer:Partial<Customer>
    createdAt:string;
    updatedAt:string;
}