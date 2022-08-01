package com.foreks.atakanbodur.starter.entities;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.ext.mongo.MongoClient;

//TODO: should find a way to determine if the file has been written before. .txt file holding logfile names??

public class OpenLogFile {
  String logFileName;
  OpenOptions options;
  Vertx vertx;
  LogObject logObject;
  MongoClient client;
  Boolean hasExecuted;

  public OpenLogFile(String logFileName, OpenOptions options, Vertx vertx, MongoClient client) {
    this.logFileName = logFileName;
    this.options = options;
    this.vertx = vertx;
    this.client = client;
    this.hasExecuted = false;
  }

  public void execute() {
    if (!hasExecuted) {
      logObject = new LogObject();
      AsyncFile asyncFile = this.vertx.fileSystem().openBlocking(logFileName, new OpenOptions());

      //get logs line by line from the log file
      RecordParser recordParser = RecordParser.newDelimited("\n", bufferedLine -> {
        logObject.setLogData(bufferedLine.toString());
        client.save("logs", logObject.initJSONObject(), result -> {
          if (result.succeeded()) {
            System.out.println("Inserted id: " + result.result());
          } else {
            System.out.println("resul not sucess");
            result.cause().printStackTrace();
          }
        });
      });

      //close file
      asyncFile.handler(recordParser)
        .endHandler(v -> {
          asyncFile.close();
          System.out.println("Done");
        });

    } else {
      System.out.println("File has already been executed before.");
    }

  }
}
