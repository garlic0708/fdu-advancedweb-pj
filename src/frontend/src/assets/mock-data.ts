export const mockData = {
  'GET /api/node/attachments/:id': {
    'questions': [
      {id: 1, type: 'multipleChoice', description: 'Multiple Choice 1',},
      {id: 2, type: 'multipleChoice', description: 'Multiple Choice 2',},
      {id: 3, type: 'shortAnswer', description: 'Short Answer 1',},
    ],
    'courseWares': [
      {id: 4, description: 'CW 1',},
      {id: 5, description: 'CW 2',},
    ],
    'resources': [
      {id: 6, type: 'file', description: 'File 1',},
      {id: 7, type: 'url', description: 'URL 1',},
    ],
  },

  'GET /api/mindmaps/list/:courseid': [
    {id: 0, name: '...'},
  ],

  'GET /api/question/:qid': {
//    type: 'multipleChoice',
//    content: '1 + 1 = ?',
//    answers: {
//      a: "1",
//      b: '2',
//      c: '3',
//    }

     type: 'shortAnswer',
     content: '1 + 1 = ?',
     answers: '2'
  },

  'GET /api/course/get/:id': [
    {
        "id": 122,
        "name": "apue",
        "maps": [
            {
                "id": 168,
                "name": "map3",
                "rootNode": null
            },
            {
                "id": 169,
                "name": "map2",
                "rootNode": null
            },
            {
                "id": 167,
                "name": "map1",
                "rootNode": null
            }
        ]
    },
    {
        "id": 120,
        "name": "ooad",
        "maps": [
            {
                "id": 176,
                "name": "mp4",
                "rootNode": null
            }
        ]
    },
    {
        "id": 125,
        "name": "ics",
        "maps": []
    },
    {
        "id": 171,
        "name": "sss",
        "maps": []
    }
]
};
