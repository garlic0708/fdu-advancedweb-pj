export interface User {
  id: number,
  name: string,
  role: 'TEACHER' | 'STUDENT',
}

export interface RegisterForm {
  email: string,
  name: string,
  password: string,
  passwordRepeated: string,
  role: 'TEACHER' | 'STUDENT',
}
