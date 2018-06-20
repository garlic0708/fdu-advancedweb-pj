import random = require('lodash/random')

export const mockData = {
  'GET /api/node/attachments/:id': {
    'questions': [
      { id: 1, type: 'multipleChoice', description: 'Multiple Choice 1', },
      { id: 2, type: 'multipleChoice', description: 'Multiple Choice 2', },
      { id: 3, type: 'shortAnswer', description: 'Short Answer 1', },
    ],
    'coursewares': [
      { id: 4, description: 'CW 1', },
      { id: 5, description: 'CW 2', },
    ],
    'resources': [
      { id: 6, type: 'file', description: 'File 1', },
      { id: 7, type: 'url', description: 'URL 1', },
    ],
  },

  'GET /api/mindmaps/list/:courseId': [
    { id: 0, name: '...' },
  ],

  'GET /api/homework/getMC/:qid':
    {
      type: 'multipleChoice',
      content: '1 + 1 = ?',
      answers: {
        a: "1",
        b: '2',
        c: '3',
      },
      correct: 'b',
      answer: 'c',
    },

  'GET /api/homework/getSA/:qid': {
    type: 'shortAnswer',
    content: '1 + 1 = ?',
    answer: '2...',
    correct: '2',
  },

  'POST /api/homework/updateMC/:qid': true,
  'POST /api/homework/updateSA/:qid': true,
  'POST /api/homework/addMCA/:qid': true,
  'POST /api/homework/addSAA/:qid': true,

  'GET /api/node/getAll/:mapId': {
    "id": 123,
    internalId: 123,
    "name": "rootNode",
    "childNodes": [
      {
        "id": 158,
        internalId: 158,
        "name": "child1_2",
        "childNodes": [],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      },
      {
        "id": 159,
        internalId: 159,
        "name": "child1_3",
        "childNodes": [],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      },
      {
        "id": 160,
        internalId: 160,
        "name": "child1_4",
        "childNodes": [],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      },
      {
        "id": 133,
        internalId: 133,
        "name": "child1_1",
        "childNodes": [
          {
            "id": 138,
            internalId: 138,
            color: '#80ff80',
            "name": "child2_1",
            "childNodes": [
              {
                "id": 200,
                internalId: 200,
                "name": "child3_2",
                "childNodes": [],
                "homeWork": [],
                "coursewares": [],
                "resources": []
              },
              {
                "id": 201,
                internalId: 201,
                "name": "child3_3",
                "childNodes": [],
                "homeWork": [],
                "coursewares": [],
                "resources": []
              },
              {
                "id": 156,
                internalId: 156,
                "name": "child3_1",
                "childNodes": [],
                "homeWork": [],
                "coursewares": [],
                "resources": []
              }
            ],
            "homeWork": [],
            "coursewares": [],
            "resources": []
          }
        ],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      }
    ],
    "homeWork": [],
    "coursewares": [],
    "resources": []
  },

  'POST /api/mindmap/manipulate/:mapId': {
    "id": 123,
    internalId: 123,
    "name": "rootNode",
    "childNodes": [
      {
        "id": 158,
        internalId: 158,
        "name": "child1_2",
        "childNodes": [],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      },
      {
        "id": 159,
        internalId: 159,
        "name": "child1_3",
        "childNodes": [],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      },
      {
        "id": 160,
        internalId: 160,
        "name": "child1_4",
        "childNodes": [],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      },
      {
        "id": 133,
        internalId: 133,
        "name": "child1_1",
        "childNodes": [
          {
            "id": 138,
            internalId: 138,
            color: '#80ff80',
            "name": "child2_1",
            "childNodes": [
              {
                "id": 200,
                internalId: 200,
                "name": "child3_2",
                "childNodes": [],
                "homeWork": [],
                "coursewares": [],
                "resources": []
              },
              {
                "id": 201,
                internalId: 201,
                "name": "child3_3",
                "childNodes": [],
                "homeWork": [],
                "coursewares": [],
                "resources": []
              },
              {
                "id": 156,
                internalId: 156,
                "name": "child3_1",
                "childNodes": [],
                "homeWork": [],
                "coursewares": [],
                "resources": []
              }
            ],
            "homeWork": [],
            "coursewares": [],
            "resources": []
          }
        ],
        "homeWork": [],
        "coursewares": [],
        "resources": []
      },
      { id: 200, internalId: 202, name: 'newly added', childNodes: [] }
    ],
    "homeWork": [],
    "coursewares": [],
    "resources": []
  },

  'GET /api/course/get': [
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
  ],

  'POST /api/course/add': {
    id: random(10000),
    name: 'just added',
  },

  'DELETE /api/course/delete/:id': true,
  'DELETE /api/mindmap/delete/:id': true,

  'POST /login': {
    id: 78,
    name: 'garlic0708',
    role: 'TEACHER',
  },
  'POST /logout': true,

  'POST /api/homework/addMC/:id':
    { id: random(10000), type: 'multipleChoice', description: 'Just added MC', },

  'POST /api/homework/addSA/:id':
    { id: random(10000), type: 'shortAnswer', description: 'Just added SA', },

  'GET /api/homework/getMCA/:id': {
    a: 24,
    b: 21,
    c: 30,
    d: 4,
  }
};
