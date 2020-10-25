const express = require('express')
const app = express()
const port = 3001

//Create the correct table here
//
var orderlineTable = {
  "KeyValue": [
    {
      "keylist": [
        "key1 key2"
      ],
      "comment": "A/B sequence document for user Ctulhu",
      "orderline": [
        {
          "v": [
            "value1_1 value2_1"
          ]
        },
        {
          "v": [
            "value1_2 value2_2"
          ]
        },
        {
          "v": [
            "value1_3 value2_3"
          ]
        },
        {
          "v": [
            "value1_4 value2_4"
          ]
        },
        {
          "v": [
            "value1_5 value2_5"
          ]
        },
        {
          "v": [
            "value1_6 value2_6"
          ]
        },
        {
          "v": [
            "value1_7 value2_7"
          ]
        },
        {
          "v": [
            "value1_8 value2_8"
          ]
        },
        {
          "v": [
            "value1_9 value2_9"
          ]
        },
        {
          "v": [
            "value1_10 value2_10"
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
