package com.foreks.atakanbodur.starter.repositories;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.ArrayList;
import java.util.List;

public class LogObjectRepository {


  private final String COLLECTION_NAME = "logs";

  private final MongoClient dbClient;


  public LogObjectRepository(MongoClient dbClient) {
    this.dbClient = dbClient;
  }

  public void read(JsonObject query) {
    getJsonObjects(query);
  }

  public void readAll() {
    getJsonObjects(new JsonObject());
  }

  public void readByCompany(String company_) {
    JsonObject query = new JsonObject().put("company", trim(company_));
    getJsonObjects(query);
  }

  public void readByUser(String user_) {
    JsonObject query = new JsonObject().put("user", trim(user_));
    getJsonObjects(query);
  }

  public void readByMethod(String method_) {
    JsonObject query = new JsonObject().put("method", trim(method_));
    getJsonObjects(query);
  }

  public void readByStatusCode(String status_) {
    JsonObject query = new JsonObject().put("statusCode", trim(status_));
    getJsonObjects(query);
  }

  public void readByProcessTimeMS(String processTimeMS_) {
    JsonObject query = new JsonObject().put("processTimeMS", trim(processTimeMS_));
    getJsonObjects(query);
  }

  public void readByProtocol(String protocol_) {
    JsonObject query = new JsonObject().put("protocol", trim(protocol_));
    getJsonObjects(query);
  }

  public void readByPort(String port_) {
    JsonObject query = new JsonObject().put("port", trim(port_));
    getJsonObjects(query);
  }

  public void readByHost(String host_) {
    JsonObject query = new JsonObject().put("host", trim(host_));
    getJsonObjects(query);
  }

  public void readByResource(String resource_) {
    JsonObject query = new JsonObject().put("resource", trim(resource_));
    getJsonObjects(query);
  }

  public void readByPlatform(String platform_) {
    JsonObject query = new JsonObject().put("platform", trim(platform_));
    getJsonObjects(query);
  }

  public void readByAppName(String appName_) {
    JsonObject query = new JsonObject().put("appName", trim(appName_));
    getJsonObjects(query);
  }

  public void readByAppVersion(String appVersion_) {
    JsonObject query = new JsonObject().put("appVersion", trim(appVersion_));
    getJsonObjects(query);
  }


  public String trim(String str_) {
    String str = str_.replaceAll("\\s", "");
    if (str.equals("")) return "null";
    else return str_; //TODO: return str_ or str?
  }

  public void getJsonObjects(JsonObject query) {
    List<JsonObject> jsonObjectList = new ArrayList<>();
    dbClient.find(COLLECTION_NAME, query, res -> {
      if (res.succeeded()) {
        for (JsonObject json : res.result()) {
          jsonObjectList.add(json);
          System.out.println(json.encodePrettily());
        }
      } else res.cause().printStackTrace();
    });
  }
}
