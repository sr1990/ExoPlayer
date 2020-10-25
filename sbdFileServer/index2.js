const express = require('express')
const app = express()
const port = 3002

//Create the correct table here
//
var orderlineTable = {
  "KeyValue": [
    {
      "keylist": [
        "key3 key4"
      ],
      "comment": "A/B sequence document for user Ctulhu",
      "orderline": [
        {
          "v": [
            "value3_1 value4_1"
          ]
        },
        {
          "v": [
            "value3_2 value4_2"
          ]
        },
        {
          "v": [
            "value3_3 value4_3"
          ]
        },
        {
          "v": [
            "value3_4 value4_4"
          ]
        },
        {
          "v": [
            "value3_5 value4_5"
          ]
        },
        {
          "v": [
            "value3_6 value4_6"
          ]
        },
        {
          "v": [
            "value3_7 value4_7"
          ]
        },
        {
          "v": [
            "value3_8 value4_8"
          ]
        },
        {
          "v": [
            "value3_9 value4_9"
          ]
        },
        {
          "v": [
            "value3_10 value4_10"
          ]
        }
      ]
    }
  ]
}

app.get('/', (req, res) => {
  res.send(orderlineTable);
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
