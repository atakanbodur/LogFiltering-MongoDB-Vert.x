package com.foreks.atakanbodur.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;


/*
TODO:
rc.write(json)
rc.end()
 */
/*
TODO:
[
{Service gereksiz}
microservisler çalıştığımız için gerek yok direkt
{repo -> handler}
]
*/
/*
TODO:
[
servisleri ayır
]
*/
/*
TODO:
[
logobjectrouter içindekileri maine yaz
]
*/
/*
TODO:
[
handlelarda chain yapısı
]
*/
/*
TODO:
1. servis

/api/logs/summary?startDate=dd/MM/yyyy&endDate=dd/MM/YYYY&username=ATAKAN

iki tarih alacak bu tarihler arasında ilgili kayda ait sorgularla ilgili istatistik dönecek

input parametreleri:
startDate
EnDate;
user:

repsonse:
{
"totalRequest": 369,
"averageProcessTime": 11,
"totalSuccess": 330,
"totalFailure": 39,
"info": [

],
}

not: query'de user yoksa ilgili tarihteki bütün kayıtlar söz konusu olur.
*/
/*
TODO:
2.  servis
/api/logs/detail?startDate=dd/MM/yyyy&endDate=dd/MM/YYYY&username=ATAKAN&<status=OK>

status -> null
bütün kayıtlar
status -> OK
response status 200 olan kayıtlar dönecek
status -> !OK
response status 200 harici kayıtlar dönecek

 */
public class MainVerticle extends AbstractVerticle {
  static MongoClient client;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //init logObject
    LogObject logObject = new LogObject();
    // init and configure client
    JsonObject config = new JsonObject()
      .put("connection_string", "mongodb://192.168.0.136:27017").put("db_name", "logInfoProject");
    client = MongoClient.createShared(vertx, config);

//    //init log file
//    AsyncFile asyncFile = vertx.fileSystem().openBlocking("dummylogfile.txt", new OpenOptions());
//
//    //get logs line by line from the log file
//    RecordParser recordParser = RecordParser.newDelimited("\n", bufferedLine -> {
//      logObject.setLogData(bufferedLine.toString());
//      client.save("logInfos", logObject.initJSONObject(), result -> {
//        if (result.succeeded()) {
//          System.out.println("Inserted id: " + result.result());
//        } else
//          result.cause().printStackTrace();
//      });
//      System.out.println(logObject.initJSONObject());
//    });
//
//    //close file
//    asyncFile.handler(recordParser)
//      .endHandler(v -> {
//        asyncFile.close();
//        System.out.println("Done");
//      });
//
    LogObjectRepository logObjectRepository = new LogObjectRepository(client);
    LogObjectService logObjectService = new LogObjectService(logObjectRepository);
    LogObjectHandler logObjectHandler = new LogObjectHandler(logObjectService);
    LogObjectRouter logObjectRouter = new LogObjectRouter(vertx,logObjectHandler);
    Router router=Router.router(vertx);
    logObjectRouter.buildLogObjectRouter(router);

    vertx.createHttpServer().requestHandler(router).listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
