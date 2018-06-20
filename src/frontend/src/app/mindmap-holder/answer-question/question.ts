export interface MultipleChoiceQuestion {
  type: string;
  content: string;
  answers: object;
  correct?: string;
}

export interface ShortAnswerQuestion {
  type: string;
  content: string;
  correct?: string;
}
