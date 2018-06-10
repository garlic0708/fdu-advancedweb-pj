export interface User {
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
