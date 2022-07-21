package com.foreks.atakanbodur.starter.repositories;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import java.util.function.BiConsumer;

public class LogObjectRepository {


  //TODO: consumer

  private final String COLLECTION_NAME = "logs";

  private final MongoClient dbClient;


  public LogObjectRepository(MongoClient dbClient) {
    this.dbClient = dbClient;
  }

  public void read(JsonObject query, BiConsumer<Boolean, JsonArray> consumer) {
    getJsonObjects(query, consumer);
  }

  public void readAll(BiConsumer<Boolean, JsonArray> consumer) {
    getJsonObjects(new JsonObject(), consumer);
  }

  public void readByCompany(String company_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("company", trim(company_));
    getJsonObjects(query, consumer);
  }

  public void readByUser(String user_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("user", trim(user_));
    getJsonObjects(query, consumer);
  }

  public void readByMethod(String method_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("method", trim(method_));
    getJsonObjects(query, consumer);
  }

  public void readByStatusCode(String status_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("statusCode", trim(status_));
    getJsonObjects(query, consumer);
  }

  public void readByProcessTimeMS(String processTimeMS_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("processTimeMS", trim(processTimeMS_));
    getJsonObjects(query, consumer);
  }

  public void readByProtocol(String protocol_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("protocol", trim(protocol_));
    getJsonObjects(query, consumer);
  }

  public void readByPort(String port_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("port", trim(port_));
    getJsonObjects(query, consumer);
  }

  public void readByHost(String host_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("host", trim(host_));
    getJsonObjects(query, consumer);
  }

  public void readByResource(String resource_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("resource", trim(resource_));
    getJsonObjects(query, consumer);
  }

  public void readByPlatform(String platform_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("platform", trim(platform_));
    getJsonObjects(query, consumer);
  }

  public void readByAppName(String appName_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("appName", trim(appName_));
    getJsonObjects(query, consumer);
  }

  public void readByAppVersion(String appVersion_, BiConsumer<Boolean, JsonArray> consumer) {
    JsonObject query = new JsonObject().put("appVersion", trim(appVersion_));
    getJsonObjects(query, consumer);
  }


  public String trim(String str_) {
    String str = str_.replaceAll("\\s", "");
    if (str.equals("")) return "null";
    else return str_;
  }


  public void getJsonObjects(JsonObject query, BiConsumer<Boolean, JsonArray> consumer) {
    JsonArray jsonObjectList = new JsonArray();
    dbClient.find(COLLECTION_NAME, query, res -> {
      if (res.succeeded()) {
        for (JsonObject json : res.result()) {
          jsonObjectList.add(json);
          System.out.println("DB operation:  json with id " + json.getString("_id"));
        }
        consumer.accept(true ,jsonObjectList);
      } else{
        res.cause().printStackTrace();
        consumer.accept(false, null);
      }
    });
  }
}
