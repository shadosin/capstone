

const GOOGLE_KEY = process.env.GOOGLE_KEY;
const spreadsheetId = '1QuOGfeLhBDMQkq_iwmX8gj_BXaBL_W54C3ztEOXM63E'
const sheetName = 'Sheet1'
const sheet_url = `https://sheets.googleapis.com/v4/spreadsheets/${spreadsheetId}/values/${sheetName}?alt=json&key=${GOOGLE_KEY}`

const response = await fetch(sheet_url, {method: 'GET'})
  .then(res => res.json());


let data = response.values

let keys = data[3]
let values = data.splice(5)

let jsonArray = [];

for (const value of values) {
  let newObj = {}
  newObj[keys[1]] = value[1] ? {"S": value[1]} : null // String
  newObj[keys[2]] = value[2] ? {"S": value[2]} : {"S": "TYPE"}
  newObj[keys[3]] = value[3] ? {"S": value[3]} : null
  newObj[keys[4]] = value[4] ? {"S": value[4]} : null
  newObj[keys[5]] = value[5] ? {"N": value[5].toString()} : null; // int
  newObj[keys[6]] = value[6] ? {"N": value[6].toString()} : null; // int
  newObj[keys[7]] = value[7] ? {"N": value[7].toString()} : null; // int
  newObj[keys[8]] = value[8] ? {"N": value[8].toString()} : null; // double
  newObj[keys[9]] = value[9] ? {"N": value[9].toString()} : null; // double
  newObj[keys[10]] = value[10] ? {"S": value[10]} : null

  newObj = Object.fromEntries(Object.entries(newObj).filter(([_, v]) => v != null));

  jsonArray.push(newObj)
}

let JsonObj = {"Exercise": []}

for (const item of jsonArray) {
  JsonObj.Exercise.push(
    {
      "PutRequest": {
        "Item": {
          ...item
        }
      }
    }
  )
}

console.log(JSON.stringify(JsonObj))
