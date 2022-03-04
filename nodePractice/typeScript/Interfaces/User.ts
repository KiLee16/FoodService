import { Address } from "./Adress";
import { Role } from "./Role";

export interface User {
  id: number;
  email: string;
  password: string;
  username: string;
  doj: Date;
  addresses: Set<Address>;
  roles: Set<Role>;
}
