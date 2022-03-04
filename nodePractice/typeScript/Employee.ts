export class Employee {
  private id: string;
  private firstName: string;
  private lastName: string;
  private email: string;
  private phone: string;
  private address: string;

  //constructor

  constructor(
    id: string,
    firstName: string,
    lastName: string,
    email: string,
    phone: string,
    address: string
  ) {
    this.id = id;

    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.address = address;
  }
}
//exports.Employee = Employee;
//export default Employee;
//export : public in nature -> accessible to anyone
