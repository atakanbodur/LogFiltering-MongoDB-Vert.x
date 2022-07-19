package com.foreks.atakanbodur.starter;

import com.foreks.atakanbodur.starter.entities.LogObject;
import com.foreks.atakanbodur.starter.handlers.GenericHandler;
import com.foreks.atakanbodur.starter.handlers.DetailSearchHandler;
import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;


/*
TODO:
rc.write(json)
rc.end()
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
      .put("connection_string", "mongodb://192.168.0.137:27017").put("db_name", "logInfoProject");
    client = MongoClient.createShared(vertx, config);

    //init log file
    AsyncFile asyncFile = vertx.fileSystem().openBlocking("dummylogfile.txt", new OpenOptions());

    //get logs line by line from the log file
    RecordParser recordParser = RecordParser.newDelimited("\n", bufferedLine -> {
      logObject.setLogData(bufferedLine.toString());
      client.save("logs", logObject.initJSONObject(), result -> {
        if (result.succeeded()) {
          System.out.println("Inserted id: " + result.result());
        } else
          result.cause().printStackTrace();
      });
    });

    //close file
    asyncFile.handler(recordParser)
      .endHandler(v -> {
        asyncFile.close();
        System.out.println("Done");
      });

    LogObjectRepository logObjectRepository = new LogObjectRepository(client);
    GenericHandler genericHandler = new GenericHandler(logObjectRepository);
    DetailSearchHandler detailSearchHandler = new DetailSearchHandler(logObjectRepository);

    Router router=Router.router(vertx);
    router.route("/api/logs*").handler(BodyHandler.create());
    router.get("/api/logs").handler(genericHandler::readAll);
    router.get("/api/logs/company/:company").handler(genericHandler::readByCompany);
    router.get("/api/logs/user/:user").handler(genericHandler::readByUser);
    router.get("/api/logs/method/:method").handler(genericHandler::readByMethod);
    router.get("/api/logs/statusCode/:statusCode").handler(genericHandler::readByStatusCode);
    router.get("/api/logs/processTimeMS/:processTimeMS").handler(genericHandler::readByProcessTimeMS);
    router.get("/api/logs/protocol/:protocol").handler(genericHandler::readByProtocol);
    router.get("/api/logs/port/:port").handler(genericHandler::readByPort);
    router.get("/api/logs/host/:host").handler(genericHandler::readByHost);
    router.get("/api/logs/resource/:resource").handler(genericHandler::readByResource);
    router.get("/api/logs/platform/:platform").handler(genericHandler::readByPlatform);
    router.get("/api/logs/appName/:appName").handler(genericHandler::readByAppName);
    router.get("/api/logs/appVersion/:appVersion").handler(genericHandler::readByAppVersion);
    router.get("/api/logs/detail").handler(detailSearchHandler::read);

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
