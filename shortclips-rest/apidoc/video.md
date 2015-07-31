**Video APIs**
----

* **URL**

  /v1/videos

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
   
    `apiKey added later`

   **Optional:**
 
   `page=[int]` 
   
   `size=[int]`

* **Data Params**

    `None`

* **Success Response:**

    **Code:** 
    
    `200 OK`
 
    **Content:** 
    
```
  {
    "content": [
      {
        "id": "String - video id ",
        "title": "String",
        "youtubeUrl": "Video youtube url"
      },
      ...
    ],
    "last": "boolean - is last page",
    "first": "boolean - is firt page",
    "totalPages": "int - total pages",
    "totalElements": "int - total elements",
    "number": "int - page number",
    "size": "int - page size",
    "numberOfElements": "int - number of elements in this page"
  }
```

* **Error Response:**

    **Code:** 
    
    `401 UNAUTHORIZED` 
 
    **Content:** 

    `{ "error" : "UNAUTHORIZED" }`

* **Sample Call:**

    `GET /v1/videos?page=0&size=3`
    
     Code:
     
    `200 OK`
 
    Content:
    
```
{
  "content": [
    {
      "id": "a0Y5B8O_460sv.mp4",
      "title": "Cobra style welding",
      "youtubeUrl": "http://www.youtube.com/embed/T_2u7vJJf7I"
    },
    {
      "id": "a0Y5ggQ_460sv.mp4",
      "title": "Nothing is impossible!",
      "youtubeUrl": "http://www.youtube.com/embed/T_2u7vJJf7I"
    }
  ],
  "last": false,
  "first": true,
  "totalPages": 97,
  "totalElements": 291,
  "number": 0,
  "size": 2,
  "numberOfElements": 2
}
```

