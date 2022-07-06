package com.foreks.atakanbodur.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.ext.mongo.MongoClient;


public class MainVerticle extends AbstractVerticle {

  static int index = 0;
  static int nextIndex = 0;
  static String dummyLog = "";
  static MongoClient client;

  static String remoteClient = "";
  static String user = "";
  static String method = "";
  static String statusCode = "";
  static String processTimeMS = "";
  static String protocol = "";
  static String port = "";
  static String host = "";
  static String resource = "";
  static String platform = "";
  static String appName = "";
  static String appVersion = "";
  static String company = "";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonObject config = new JsonObject()
      .put("connection_string", "mongodb://192.168.0.136:27017").put("db_name", "logInfoProject");
    client =  MongoClient.createShared(vertx, config);

    AsyncFile asyncFile = vertx.fileSystem().openBlocking("dummylogfile.txt", new OpenOptions());
    RecordParser recordParser = RecordParser.newDelimited("\n", bufferedLine -> {
      dummyLog = bufferedLine.toString();
      initValues();
      client.save("logInfos", initJSONObject(), result -> {
        if (result.succeeded()){
          System.out.println("Inserted id: " + result.result());
        }
        else
          result.cause().printStackTrace();
      });
      System.out.println(initJSONObject());
    });

    asyncFile.handler(recordParser)
      .endHandler(v -> {
        asyncFile.close();
        System.out.println("Done");
      });
  }


  static String returnValueOf(String value){
    index = dummyLog.indexOf(value);
    if (index == -1)
      return "null";
    else {
      index += (value.length());
      String substr = dummyLog.substring(index);
      nextIndex = substr.indexOf(" ");
      return substr.substring(0,nextIndex);
    }
  }

  static void initValues(){
    remoteClient = returnValueOf("remoteClient: ");
    user = returnValueOf("user: ");
    method = returnValueOf("method: ");
    statusCode = returnValueOf("statusCode: ");
    processTimeMS = returnValueOf("processTimeMS: ");
    protocol = returnValueOf("x-forwarded-proto=");
    port = returnValueOf("x-forwarded-port=");
    host = returnValueOf("host=");
    resource = returnValueOf("resource=");
    platform = returnValueOf("platform=");
    appName = returnValueOf("app-name=");
    appVersion = returnValueOf("app-version=");
    company = returnValueOf("company=");
  }

  static JsonObject initJSONObject(){
    JsonObject JSONObject = new JsonObject();
    JSONObject.put("remoteClient", remoteClient);
    JSONObject.put("user", user);
    JSONObject.put("method", method);
    JSONObject.put("statusCode", statusCode);
    JSONObject.put("processTimeMS", processTimeMS);
    JSONObject.put("protocol", protocol);
    JSONObject.put("port", port);
    JSONObject.put("host", host);
    JSONObject.put("resource", resource);
    JSONObject.put("platform", platform);
    JSONObject.put("appName", appName);
    JSONObject.put("appVersion", appVersion);
    JSONObject.put("company", company);
    return JSONObject;
  }

}
