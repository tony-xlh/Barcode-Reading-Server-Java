# Barcode-Reading-Server-Java

A Java HTTP server app with Jetty which provides an API interface to read barcodes.

[Dynamsoft Barcode Reader](https://www.dynamsoft.com/barcode-reader/overview/) is used for barcode reading.

## Web API Usage

* Endpoint: `/readBarcodes`

* Request: 

   Headers:
   
   ```json
   {
     "Content-Type":"application/json"
   }
   ```

   Body:
   
   ```json
   {
     "base64": "the base64 string of the image for decoding"
   }
   ```

* Example response:

```json
{
  "elapsedTime": 0,
  "results": [
    {
      "barcodeText":"",
      "barcodeFormat":"",
      "barcodeBytes":"",
      "confidence":0,
      "x1":0,
      "x2":0,
      "x3":0,
      "x4":0,
      "y1":0,
      "y2":0,
      "y3":0,
      "y4":0
    }
  ]
}
```
