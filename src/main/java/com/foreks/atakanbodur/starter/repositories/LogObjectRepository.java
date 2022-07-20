package com.foreks.atakanbodur.starter.repositories;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LogObjectRepository {


  //TODO: consumer

  private final String COLLECTION_NAME = "logs";

  private final MongoClient dbClient;


  public LogObjectRepository(MongoClient dbClient) {
    this.dbClient = dbClient;
  }

  public void read(JsonObject query, RoutingContext rc) {
    getJsonObjects(query, rc);
  }

  public void readAll(RoutingContext rc) {
    getJsonObjects(new JsonObject(), rc);
  }

//  public void readByCompany(String company_, BiConsumer<Boolean, JsonArray> consumer)
    public void readByCompany(String company_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("company", trim(company_));
//    getJsonObjects2(query, consumer);
      getJsonObjects(query, rc);
  }

  public void readByUser(String user_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("user", trim(user_));
    getJsonObjects(query, rc);
  }

  public void readByMethod(String method_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("method", trim(method_));
    getJsonObjects(query, rc);
  }

  public void readByStatusCode(String status_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("statusCode", trim(status_));
    getJsonObjects(query, rc);
  }

  public void readByProcessTimeMS(String processTimeMS_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("processTimeMS", trim(processTimeMS_));
    getJsonObjects(query, rc);
  }

  public void readByProtocol(String protocol_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("protocol", trim(protocol_));
    getJsonObjects(query, rc);
  }

  public void readByPort(String port_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("port", trim(port_));
    getJsonObjects(query, rc);
  }

  public void readByHost(String host_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("host", trim(host_));
    getJsonObjects(query, rc);
  }

  public void readByResource(String resource_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("resource", trim(resource_));
    getJsonObjects(query, rc);
  }

  public void readByPlatform(String platform_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("platform", trim(platform_));
    getJsonObjects(query, rc);
  }

  public void readByAppName(String appName_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("appName", trim(appName_));
    getJsonObjects(query, rc);
  }

  public void readByAppVersion(String appVersion_, RoutingContext rc) {
    JsonObject query = new JsonObject().put("appVersion", trim(appVersion_));
    getJsonObjects(query, rc);
  }


  public String trim(String str_) {
    String str = str_.replaceAll("\\s", "");
    if (str.equals("")) return "null";
    else return str_; //TODO: return str_ or str?
  }

  public void getJsonObjects(JsonObject query, RoutingContext rc) {
    JsonArray jsonObjectList = new JsonArray();
    dbClient.find(COLLECTION_NAME, query, res -> {
      if (res.succeeded()) {
        for (JsonObject json : res.result()) {
          jsonObjectList.add(json);
          System.out.println("DB operation:  json with id " + json.getString("_id"));
        }
      } else res.cause().printStackTrace();
      rc.response().end(jsonObjectList.encodePrettily());
    });
  }

//  public BiConsumer<Boolean, JsonArray> getJsonObjects2(JsonObject query, BiConsumer<Boolean, JsonArray> consumer) {
//    JsonArray jsonObjectList = new JsonArray();
//    dbClient.find(COLLECTION_NAME, query, res -> {
//      if (res.succeeded()) {
//        for (JsonObject json : res.result()) {
//          jsonObjectList.add(json);
//          System.out.println(json.encodePrettily());
//        }
//        consumer.accept(true ,jsonObjectList);
//      } else{
//        res.cause().printStackTrace();
//        consumer.accept(false, null);
//      }
//      //this.rc.response().end(jsonObjectList.encodePrettily());
//    });
//  }
}
