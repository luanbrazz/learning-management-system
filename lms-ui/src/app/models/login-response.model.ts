export interface LoginResponse {
  access_token: string;
  role: string;
  full_name: string;
  user_id: string;
  student_id?: string;
}
