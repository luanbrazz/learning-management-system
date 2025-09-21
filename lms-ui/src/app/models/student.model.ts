import { User } from './user.model';

export interface Student {
  id: string;
  firstName: string;
  lastName: string;
  birthDate: string;
  email: string;
  phoneNumber: string;
  zipCode: string;
  addressLine1: string;
  addressLine2: string | null;
  city: string;
  state: string;
  user: User;
}
