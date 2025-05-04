# api wiz 
## structure
### annotations
- annotations and aspects  packages are used to log execution time
- main controller takes the requests to this we give expected inputs
### call factory function
- main controller will create a object of asyncRestFactory for each request
- each request will be called to executeAsync
### generate request
- a request will be build by using builder design pattern
- a build request will be returned by generateRequest()
- async requests will be sent by sendAsyncRequestWithRetry()
### retry request
- in request dto we need to specify whether retry is enabled or not
- based on that a loop will be run
### custom exceptions
- we will check whether request is errored or not(above 399 )
- exceptions will be thrown based on the status code client and server side exceptions will be thrown
### filters
- we have 2 filters 
- RequestIdAddtionFilter will generate unique request id and put it in thread context
- we can access it from anywhere else in that request-response cycle
- RequestLoggingFilter will log the request body
### video
- attached a video in this folder
- we added custom thread.sleep  based on given sleep time in request
- in one we gave more called it first
- in second request we gave less sleep time and called it next
- now we can see the second request cycle finished without waiting for first request cycle to finish