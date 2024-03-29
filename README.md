# Log Filtering using REST, Vert.x and MongoDB

## Parse logs from a logfile.txt to a JSON object
### Opening the File
First I need to open the text file and read it line by line. I created an OpenLogFile.java in order to achieve this. The constructor of the class demands a `vertx`, a `filepath`, a `mongoclient` and an `openoptions`. It has an `execute()` function that simply will achieve my goal. 

I created a LogObject so that I can write the attributes of it to the database when reading the log is complete and, I use `io.vertx.core.file.AsyncFile` to open the file.

    logObject = new LogObject();  
    AsyncFile asyncFile = this.vertx.fileSystem().openBlocking(logFileName, new OpenOptions());
Then, I have to read each log one by one. I have used RecordParser to achieve this goal.

    RecordParser recordParser = RecordParser.newDelimited("\n", bufferedLine -> {//DO SMTH WITH THE LINE}




## Write the logs to Mongo Database with relevant fields
### Determining Fields
While reading the lines, for each new line, I had to determine and find the fields I needed to save to the database.
Here is an example log from my txt:

> 2021-07-30 09:42:12,778 INFO  -- remoteClient: 52.28.90.156 user: atakan method: GET statusCode: 200 processTimeMS: 294 x-forwarded-for=10.10.10.10 x-forwarded-proto=https x-forwarded-port=443 host=snapshot.xxx.com authorization=basic resource=default company=xxxx accept=application/json cache-control=no-cache pragma=no-cache user-agent=java/1.8.0_40 Thr:[vert.x-eventloop-thread-5] 
### LogObject.java
I wanted to get the `date`, `remoteClient`, `user`, `method`, `statusCode`, `processTimeMS`... fields from the log. In order to get these fields; I created a function named `returnValueOf()` that will take the `key` we want to search for in `LogObject.java` and return the `value`, and called it from the `initJSONObject()`. Handling the dates is a bit tricky but here, we just have to determine the format that the date is in, and put the `logData.substring(0, 23)` into our JSONObject. Remember that the Mongo does not save Dates as a String but as an ISODate Object. If any of these values are non-existent, the value is assigned as **null**.
### Saving the log to the MongoDB
After these steps, I only need to call the `initJSONObject()` and since the method returns a JSON Object, log will be saved to the database without any problems. 

    logObject.setLogData(bufferedLine.toString());  
    client.save("logs", logObject.initJSONObject(), result ->{//DO SMTH WITH THE RESULT}

## LogObjectRepository.java
In order to get any data from the database, it's in my best interest to create a `LogObjectRepository.java` class. This class will not implement any algorithms but simply will call dbClient.find() with the relevant query.
`dbClient.find(collectionName, query, res -> {//DO SMTH WITH THE RESULT})`

This class has `readAll()`, `readBy's()`, `read()` and `aggregate()` functions.  Each of these functions, apart from the `aggregate()`, calles `getJsonObjects()` with the relevant query.
Here is an example for `readByCompany():`

    public void readByCompany(String company_, BiConsumer<Boolean, JsonArray> consumer) {  
      JsonObject query = new JsonObject().put("company", trim(company_));  
      getJsonObjects(query, consumer);  
    } 
## Create a Generic Service
This service will use a `GenericHandler.java` class, like all other services, to communicate between the repository and the user. The methods will get an `RoutingContext` and it should have the relevant field on its route. Here is an example url:
http://localhost:8080/api/logs/company/xxxx. In order to get it from the repository, we call `logObjectRepository.readByCompany(rc.pathParam("company"), (res, jsonArray) -> {...}` and the method will send the result through `rc.response().end(jsonArray.encodePrettily())`

The `GenericService.java` also has other methods that I use to create either queries or to implement a certain logic for me to help create queries.

 - `createQueryFromDateRange()`
 - `countDistinctFields()`
 - `createFacetQuery()`
 
 ### Creating Query to Get Logs From a Date Range
 This one was a tricky subject to overcome; I've used `com.mongodb.BasicDBObjectBuilder` to create No-SQL queries to achieve my goal. First I get a `JsonObject` that has keys `startDate` and an `endDate` in `createQueryFromDateRange()`, then I create two `DateFormat`'s: a `new SimpleDateFormat()` to convert `String date` to a `Date` object and an other one to convert the dates into a date format that the Mongo will understand(a.k.a. `ISODate`).

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = simpleDateFormat.parse(query_.getString("startDate"));  
    Date endDate = simpleDateFormat.parse(query_.getString("endDate"));
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
I first need to determine which `key(s)` in the database I will search for. That is in my case is the `"logDate"` and the `user` field. After that, I create a JsonObject like this and return it to where the method was requested:

    return new JsonObject()  
      .put("logDate",  
      BasicDBObjectBuilder.start("$gte",  
     new JsonObject()  
              .put("$date", df.format(startDate)))  
          .add("$lte", new JsonObject().put("$date", df.format(endDate))).get())  
      .put("user", query_.getValue("user"));

### Grouping and Counting Unique Values from a Dataset
In my other service, I had to analyze the logs' details such as: how many different `remoteClient`'s have they used etc in a given date range and how many times they were used. In order to achieve this, I needed to create pipelines with multiple stages. I tested possible queries that I can create with MongoDB Compass and then created the most suitable one inside Java. The method takes a JsonObject with `key`'s `startDate`, `endDate`, `filterFor` and `user`. The `filterFor` value determines which unique value on my Documents' I want to count and output.

In my case, I had to match logs with their `user` fields, then `group` them with their, for example, `remoteClient`'s. 
The pipeline in the format of JSON looks like this:

    [{
        $match: {
            user: 'atakan'
        }
    }, {
        $group: {
            _id: '$remoteClient',
            count: {
                $sum: 1
            }
        }
    }]
The implementation on Java looks like this:

    return new JsonArray()  
      .add(new JsonObject()  
        .put("$match", new JsonObject()  
          .put("user", query_.getValue("user"))  
    /1    .put("logDate", BasicDBObjectBuilder.start("$gte", new JsonObject()  
    /2       .put("$date", df.format(startDate)))  
    /3    .add("$lte", new JsonObject().put("$date", df.format(endDate))).get())))  
      .add(new JsonObject()  
        .put("$group", new JsonObject()  
          .put("_id", "$" + query_.getValue("filterFor"))  
          .put("count", new JsonObject().put("$sum", 1))));
As you can see, I have used the same code from `createQueryFromDateRange()` on line *{1, 2, 3}* and the other ones are simply the same with the pipeline.

### Creating a Query to Use $facet Keyword
The need for me to create this function was I had to call `aggregate()` multiple times with different `filterFor` values, but since Vert.x is getting the results from the database async and I didn't want to user Future<>, I was forced to find a way to only call `aggregate()` one time. `$facet` solves my problem with ease.

You can read more  through [here](https://www.mongodb.com/docs/manual/reference/operator/aggregation/facet/) but its simple. You first name your sequence of queries, or pipelines, and then put the pipeline to the corresponding place with an array. It looks like this:
 

        [{
        $facet: {
            categorizedByRemoteClient: [{
                    $match: {
                        user: 'atakan'
                    }
                },
                {
                    $group: {
                        _id: '$remoteClient',
                        count: {
                            $sum: 1
                        }
                    }
                }
            ],
            categorizedByCompany: [{
                    $match: {
                        user: 'atakan'
                    }
                },
                {
                    $group: {
                        _id: '$company',
                        count: {
                            $sum: 1
                        }
                    }
                }
            ]
        }
    }]

On the Java side, I wanted to create a functional way to get a list of things you want to group for, and then output them. The `createFacetQuery()` method in Java gets a `Map<String, JsonArray>`, it uses the `String value` as the name of the pipeline, and the `JsonArray pipeline` is, the pipeline itself.

    JsonObject facetObject = new JsonObject();  
    for (Map.Entry<String, JsonArray> me : categories.entrySet()) {  
      facetObject.put("categorizeBy" + me.getKey(), me.getValue());  
    }
In the end of the iteration, the `facetObject` will look like the above JSON object but after the `$facet:` Then I simply do 

    return new JsonArray()  
      .add(new JsonObject()  
        .put("$facet",  
      facetObject  
        ));
## Detailed Search Service
The service takes a `user`, a `date range` and a `statusCode` . The example url looks like this:

    http://localhost:8080/api/logs/detail?startDate=2021-01-01&endDate=2023-12-30&username=atakan&status=OK

 - If the status equals to OK, the service should look for documents where `statusCode = 200`.
 - If the status equals to !OK, the service should look for documents where `statusCode != 200`.
 - If the status is null, the service should get all the documents.

I created a `DetailSearchHandler.java` in order to implement this service. `LogObjectRepository.java` already has the methods I need, I just need to implement a condition check for the `statusCode`.

The `read()` method will take a `RoutingContext` and as you can see from the example url, it has `startDate`, `endDate`, `username`, and `status` fields. I parse these fields to a JSON Object apart from the `status`, I put it after the condition checks.

    if (request.getString("statusCode").equals("OK")) {  
      //response status 200 olan kayıtlar dönecek  
      query.put("statusCode", "200");  
    } else if (request.getString("statusCode").equals("!OK")){  
      //response status 200 harici kayıtlar dönecek  
      query.put("statusCode", new JsonObject().put("$ne", "200"));  
    }
Then, I send the query to the `LogObjectRepository.read()` and responde with the result: 

    super.getLogObjectRepository().read(query, (res, jsonArray) -> {  
      if (res) {  
        rc.response().end(jsonArray.encodePrettily());  
      } else {  
        rc.response().end("Repository error.");  
      }  
    });

## Summary Service
Summary Service will be used to give details about the statistics and, **if the user field is present**info about the user's preferences in a given date range. 
Example result:

    [ {
      "totalRequest:" : 12,
      "totalSuccess:" : 8,
      "totalFailure:" : 4,
      "avgProcessTimeMS:" : 163.66666666666666,
      "info" : [ {
        "categorizeBycompany" : [ {
          "_id" : "200test",
          "count" : 8
        }, {
          "_id" : "400test",
          "count" : 4
        } ],
        "categorizeByremoteClient" : [ {
          "_id" : "52.28.90.156",
          "count" : 12
        } ],
        "categorizeBystatusCode" : [ {
          "_id" : "400",
          "count" : 4
        }, {
          "_id" : "200",
          "count" : 8
        } ]
      } ]
    } ]
    
To achieve this, I had to first `$match` the user and `$group` the results by respected `filterFor` value. Since I wanted to give multiple filtered results in one object, and since I wanted to obey the async nature of Vert.x, I had to send the pipeline as a one query. Therefore I created `countDistinctFields()` and `createFacetQuery()` functions. I explained how they do what they do above. 

I created a `SummaryHandler.java` class and it operates with the `read()` function. Again, from the Routing Context I got from the user, I put the startDate and the endDate to a JSON Object. Then I check if the `rc` I got has a field `username`, if it doesn't, I won't be needing the info:[...] field. Then I create the list of items I want to include in the info field:

    List<String> dataToBeFilteredFor = new ArrayList<>();  
    dataToBeFilteredFor.add("remoteClient");  
    dataToBeFilteredFor.add("company");  
    dataToBeFilteredFor.add("statusCode");

Then, I created an `analyze()` function to both serve the purpose of creating the statistics and the info field. The `read()` method sends its results to this function in a JSONArray and it handles it with:

    for (int i = 0; i < dbResults.size(); i++) {
      avgProcessTime += Integer.parseInt(dbResults.getJsonObject(i).getString("processTimeMS"));
      if (!dbResults.getJsonObject(i).getString("statusCode").equals("200")) {
        totalFailure++;
      }
    }
    totalSuccess = (totalRequest - totalFailure);
    avgProcessTime = avgProcessTime / dbResults.size(); 

After that, if the query had a `user` field, it called a new function `createInfoField()`.

`createInfoField()` requires a query and a `List<String>` that I created above, in the `read()` function. Inside a loop, I fill a `Map<String, JsonArray> categories`  with the relevant `categoryName` and the one of the `pipeline`'s that will be used in `aggregate()` function.

    for (String filterFor : dataToBeFilteredFor) {  
      query.put("filterFor", filterFor);  
      categories.put(filterFor, countDistinctFields(query));  
    }
After that, I send the `categories` to the `createFacetQuery()` function inside the `GenericHandler.java`. Again, this will return a pipeline that will make it possible for me to process multiple aggregation pipelines.

## Routing Handlers
I didn't create a `Router` class as per request from my supervisor, I handled all of the routing inside the `MainVerticle.java`. First I create a `Router` object and initialize the routes:

        Router router = Router.router(vertx);
        router.get("/api/logs").handler(genericHandler::readAll);  
        router.get("/api/logs/company/:company").handler(genericHandler::readByCompany);
        router.get("/api/logs/detail").handler(detailSearchHandler::read);  
	    router.get("/api/logs/summary").handler(summaryHandler::read);

