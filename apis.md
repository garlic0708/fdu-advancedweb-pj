# 以后API说明都放在这里

###/api/course/get/:id
```json
[
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
```

###/api/node/getAll/{mindMapId}

```json
{
    "id": 123,
    "name": "rootNode",
    "childNodes": [
        {
            "id": 158,
            "name": "child1_2",
            "childNodes": [],
            "homeWork": [],
            "coursewares": [],
            "resources": []
        },
        {
            "id": 133,
            "name": "child1_1",
            "childNodes": [
                {
                    "id": 138,
                    "name": "child2_1",
                    "childNodes": [
                        {
                            "id": 156,
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
}
```