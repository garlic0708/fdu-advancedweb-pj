export const mockData = {
  'GET /api/node/attachments/:id': [
    {type: 'multipleChoice', description: 'Multiple Choice 1',},
    {type: 'multipleChoice', description: 'Multiple Choice 2',},
    {type: 'shortAnswer', description: 'Short Answer 1',},
    {type: 'courseWare', description: 'Course Ware 1',},
    {type: 'resource', description: 'Resource 1',},
    {type: 'resource', description: 'Resource 2',},
  ],

  'GET /api/mindmaps/list/:courseid': [
    {id: 0, name: '...'},
  ],

  'GET /api/question/:qid': {
    type: 'multipleChoice',
    content: '1 + 1 = ?',
    answers: {
      a: "1",
      b: '2',
      c: '3',
    }
  },

  // {
  //   type: 'shortAnswer',
  //   content: 'What is the result of 1 + 1?',
  // }
};
