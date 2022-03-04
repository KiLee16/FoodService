import { Address } from './address';

export interface Register {
  email: string;
  password: string;
  confirmPassword: string;
  username: string;
  doj: Date;
  address: Address;
  roles: string;
}
