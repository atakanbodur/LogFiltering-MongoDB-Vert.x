package com.foreks.atakanbodur.starter;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LogObjectRepository {

  private static final String COLLECTION_NAME = "logInfos";

  private MongoClient dbClient;


  public LogObjectRepository(MongoClient dbClient) {
    this.dbClient = dbClient;
  }

  public List<JsonObject> readAll() {
    List<JsonObject> jsonObjectList = new ArrayList<>();
    return getJsonObjects(new JsonObject());
  }

  public List<JsonObject> readByCompany(String company_) {
    JsonObject query = new JsonObject().put("company", trim(company_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByUser(String user_){
    JsonObject query = new JsonObject().put("user", trim(user_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByMethod(String method_){
    JsonObject query = new JsonObject().put("method", trim(method_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByStatusCode(String status_){
    JsonObject query = new JsonObject().put("statusCode", trim(status_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByProcessTimeMS(String processTimeMS_){
    JsonObject query = new JsonObject().put("processTimeMS", trim(processTimeMS_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByProtocol(String protocol_){
    JsonObject query = new JsonObject().put("protocol", trim(protocol_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByPort(String port_){
    JsonObject query = new JsonObject().put("port", trim(port_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByHost(String host_){
    JsonObject query = new JsonObject().put("host", trim(host_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByResource(String resource_){
    JsonObject query = new JsonObject().put("resource", trim(resource_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByPlatform(String platform_){
    JsonObject query = new JsonObject().put("platform", trim(platform_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByAppName(String appName_){
    JsonObject query = new JsonObject().put("appName", trim(appName_));
    return getJsonObjects(query);
  }

  public List<JsonObject> readByAppVersion(String appVersion_){
    JsonObject query = new JsonObject().put("appVersion", trim(appVersion_));
    return getJsonObjects(query);
  }

  private String trim(String str_){
    String str = str_.replaceAll("\\s", "");
    if (str.equals("")) return "null";
    else return str_; //TODO: return str_ or str?
  }

  @NotNull
  private List<JsonObject> getJsonObjects(JsonObject query) {
    List<JsonObject> jsonObjectList = new ArrayList<>();
    dbClient.find(COLLECTION_NAME, query, res -> {
      if (res.succeeded()) {
        for (JsonObject json : res.result()) {
          jsonObjectList.add(json);
          System.out.println(json.encodePrettily());
        }
      } else res.cause().printStackTrace();
    });
    return jsonObjectList;
  }
}
