export interface Question {
  id: number,
  type: string,
  description: string,
}

export interface CourseWare {
  id: number,
  description: string,
}

export interface Resource {
  id: number,
  type: string,
  description: string,
}

export interface Attachments {
  questions: Question[],
  coursewares: CourseWare[],
  resources: Resource[],
}
