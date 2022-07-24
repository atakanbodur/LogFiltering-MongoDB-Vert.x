package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import com.mongodb.BasicDBObjectBuilder;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GenericHandler {
  private final LogObjectRepository logObjectRepository;

  public GenericHandler(LogObjectRepository logObjectRepository) {
    this.logObjectRepository = logObjectRepository;
  }

  public void readAll(RoutingContext rc) {
    logObjectRepository.readAll((res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByCompany(RoutingContext rc) {
    logObjectRepository.readByCompany(rc.pathParam("company"), (res, jsonArray) -> {
      //hata ayıklama
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByUser(RoutingContext rc) {
    logObjectRepository.readByUser(rc.pathParam("user"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByMethod(RoutingContext rc) {
    logObjectRepository.readByMethod(rc.pathParam("method"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByStatusCode(RoutingContext rc) {
    logObjectRepository.readByStatusCode(rc.pathParam("statusCode"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByProcessTimeMS(RoutingContext rc) {
    logObjectRepository.readByProcessTimeMS(rc.pathParam("processTimeMS"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByProtocol(RoutingContext rc) {
    logObjectRepository.readByProtocol(rc.pathParam("protocol"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByPort(RoutingContext rc) {
    logObjectRepository.readByPort(rc.pathParam("port"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByHost(RoutingContext rc) {
    logObjectRepository.readByHost(rc.pathParam("host"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByResource(RoutingContext rc) {
    logObjectRepository.readByResource(rc.pathParam("resource"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByPlatform(RoutingContext rc) {
    logObjectRepository.readByPlatform(rc.pathParam("platform"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByAppName(RoutingContext rc) {
    logObjectRepository.readByAppName(rc.pathParam("appName"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByAppVersion(RoutingContext rc) {
    logObjectRepository.readByAppVersion(rc.pathParam("appVersion"), (res, jsonArray) -> {
      if (res) {
        rc.response().end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public LogObjectRepository getLogObjectRepository() {
    return logObjectRepository;
  }

  protected JsonObject createQueryFromDateRange(JsonObject query_) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Date startDate = simpleDateFormat.parse(query_.getString("startDate"));
    Date endDate = simpleDateFormat.parse(query_.getString("endDate"));


    return new JsonObject()
      .put("logDate", BasicDBObjectBuilder.start("$gte", new JsonObject()
            .put("$date", df.format(startDate)))
          .add("$lte", new JsonObject().put("$date", df.format(endDate))).get())
      .put("user", query_.getValue("user"));
  }

protected void countDistinctFields() {
//  JsonObject match = new JsonObject()
//    new JsonObject().put("user", query_.getString("user"));
//  JsonObject group = new JsonObject()
//    .put(query_.getString("key"), "$"+query_.getString("key"));
//
//  JsonArray pipeline = new JsonArray()
//    .add(new JsonObject().put("$match", new JsonObject().put("username", "atakan")));
//  getLogObjectRepository().getDbClient().find(getLogObjectRepository().getCOLLECTION_NAME(), new JsonObject()
//    .put("user", "atakan"), res -> {
//
//  });

//  System.out.println(getLogObjectRepository().getDbClient().aggregate("logs", pipeline));
  }
}

