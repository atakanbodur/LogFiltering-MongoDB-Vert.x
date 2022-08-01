package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import com.mongodb.BasicDBObjectBuilder;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class GenericHandler {
  private final LogObjectRepository logObjectRepository;

  protected static final String CONTENT_TYPE_HEADER = "Content-Type";

  protected static final String JSON_CONTENT_TYPE = "application/json; charset=UTF-8";

  public GenericHandler(LogObjectRepository logObjectRepository) {
    this.logObjectRepository = logObjectRepository;
  }

  public void readAll(RoutingContext rc) {
    logObjectRepository.readAll((res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByCompany(RoutingContext rc) {
    logObjectRepository.readByCompany(rc.pathParam("company"), (res, jsonArray) -> {
      //hata ayıklama
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByUser(RoutingContext rc) {
    logObjectRepository.readByUser(rc.pathParam("user"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByMethod(RoutingContext rc) {
    logObjectRepository.readByMethod(rc.pathParam("method"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByStatusCode(RoutingContext rc) {
    logObjectRepository.readByStatusCode(rc.pathParam("statusCode"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByProcessTimeMS(RoutingContext rc) {
    logObjectRepository.readByProcessTimeMS(rc.pathParam("processTimeMS"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByProtocol(RoutingContext rc) {
    logObjectRepository.readByProtocol(rc.pathParam("protocol"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByPort(RoutingContext rc) {
    logObjectRepository.readByPort(rc.pathParam("port"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByHost(RoutingContext rc) {
    logObjectRepository.readByHost(rc.pathParam("host"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByResource(RoutingContext rc) {
    logObjectRepository.readByResource(rc.pathParam("resource"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByPlatform(RoutingContext rc) {
    logObjectRepository.readByPlatform(rc.pathParam("platform"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByAppName(RoutingContext rc) {
    logObjectRepository.readByAppName(rc.pathParam("appName"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
      } else {
        rc.response().end("Repository error.");
      }
    });
  }

  public void readByAppVersion(RoutingContext rc) {
    logObjectRepository.readByAppVersion(rc.pathParam("appVersion"), (res, jsonArray) -> {
      if (res) {
        rc.response()
          .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
          .setStatusCode(200)
          .end(jsonArray.encodePrettily());
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
      .put("logDate",
        BasicDBObjectBuilder.start("$gte",
            new JsonObject()
              .put("$date", df.format(startDate)))
          .add("$lte", new JsonObject().put("$date", df.format(endDate))).get())
      .put("user", query_.getValue("user"));
  }


  public JsonArray countDistinctFields(JsonObject query_) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Date startDate = simpleDateFormat.parse(query_.getString("startDate"));
    Date endDate = simpleDateFormat.parse(query_.getString("endDate"));

    return new JsonArray()
      .add(new JsonObject()
        .put("$match", new JsonObject()
          .put("user", query_.getValue("user"))
          .put("logDate", BasicDBObjectBuilder.start("$gte", new JsonObject()
              .put("$date", df.format(startDate)))
            .add("$lte", new JsonObject().put("$date", df.format(endDate))).get())))
      .add(new JsonObject()
        .put("$group", new JsonObject()
          .put("_id", "$" + query_.getValue("filterFor"))
          .put("count", new JsonObject().put("$sum", 1))));
  }

  public JsonArray createFacetQuery(Map<String, JsonArray> categories) {
    JsonObject facetObject = new JsonObject();
    for (Map.Entry<String, JsonArray> me : categories.entrySet()) {
      facetObject.put("categorizeBy" + me.getKey(), me.getValue());
    }

    return new JsonArray()
      .add(new JsonObject()
        .put("$facet",
          facetObject
        ));
  }
}

