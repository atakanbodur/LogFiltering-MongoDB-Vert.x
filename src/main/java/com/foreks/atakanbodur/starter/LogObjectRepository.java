package com.foreks.atakanbodur.starter;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.bson.Document;

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
    dbClient.find(COLLECTION_NAME,null, res -> {
      if (res.succeeded()){
        for (JsonObject json : res.result()) {
          jsonObjectList.add(json);
          System.out.println(json.encodePrettily());
        }
      }
      else res.cause().printStackTrace();
    });
    return jsonObjectList;
  }
}
