import { User } from "./User";

export interface Food {
  id: number;
  foodname: string;
  description: string;
  foodPic: string;
  foodPrice: number;
  users: Array<User>;
}
