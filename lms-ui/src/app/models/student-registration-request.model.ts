export interface StudentRegistrationRequest {
  firstName: string;
  lastName: string;
  birthDate: string;
  email: string;
  phoneNumber: string;
  zipCode: string;
  addressLine1: string;
  addressLine2?: string;
  city: string;
  state: string;
  password: string;
}
