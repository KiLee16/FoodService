import { Employee } from "./Employee";
import { IPerson } from "./Iperson";

let employee: Employee = new Employee(
  "ab01",
  "abhi",
  "chiv",
  "this@gmail.com",
  "123455667",
  "trees"
);
console.log(employee);

let iperson: IPerson = { id: 1, name: "abhi", contactnumber: "123455667" };
console.log(iperson);
