package com.foreks.atakanbodur.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.ext.mongo.MongoClient;


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

    LogObjectRepository repository = new LogObjectRepository(client);
    repository.readByCompany("foreks");
  }
}
