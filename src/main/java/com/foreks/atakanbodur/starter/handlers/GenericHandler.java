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

  public void readByCompanyPaginate(RoutingContext rc) {
    JsonArray paginationPipeline = addPaginationToQuery(new JsonObject()
        .put("company", rc.request().getParam("company")),
      rc.request().getParam("pageSize"), rc.request().getParam("pageNumber")
    );

    JsonArray countPipeline = new JsonArray()
      .add(new JsonObject().put("$match", new JsonObject().put("company", rc.request().getParam("company"))))
      .add(new JsonObject().put("$count", "count"));

    callAggregate(rc, paginationPipeline, countPipeline);
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

  public void readByUserPaginate(RoutingContext rc) {
    JsonArray paginationPipeline = addPaginationToQuery(new JsonObject()
        .put("user", rc.request().getParam("user")),
      rc.request().getParam("pageSize"), rc.request().getParam("pageNumber")
    );

    JsonArray countPipeline = new JsonArray()
      .add(new JsonObject().put("$match", new JsonObject().put("user", rc.request().getParam("user"))))
      .add(new JsonObject().put("$count", "count"));

    callAggregate(rc, paginationPipeline, countPipeline);
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

  public void readByPlatformPaginate(RoutingContext rc) {
    JsonArray paginationPipeline = addPaginationToQuery(new JsonObject()
        .put("platform", rc.request().getParam("platform")),
      rc.request().getParam("pageSize"), rc.request().getParam("pageNumber")
    );

    JsonArray countPipeline = new JsonArray()
      .add(new JsonObject().put("$match", new JsonObject().put("platform", rc.request().getParam("platform"))))
      .add(new JsonObject().put("$count", "count"));

    callAggregate(rc, paginationPipeline, countPipeline);
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

  public void readByAppNamePaginate(RoutingContext rc) {
    JsonArray paginationPipeline = addPaginationToQuery(new JsonObject()
        .put("appName", rc.request().getParam("appName")),
      rc.request().getParam("pageSize"), rc.request().getParam("pageNumber")
    );

    JsonArray countPipeline = new JsonArray()
      .add(new JsonObject().put("$match", new JsonObject().put("appName", rc.request().getParam("appName"))))
      .add(new JsonObject().put("$count", "count"));

    callAggregate(rc, paginationPipeline, countPipeline);
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

  public JsonArray createFacetQuery(JsonArray facetObject) {
    return new JsonArray()
      .add(new JsonObject()
        .put("$facet",
          new JsonObject().put("data", facetObject)
        ));
  }

  public JsonArray createFacetQuery(JsonObject facetObject) {
    return new JsonArray()
      .add(new JsonObject()
        .put("$facet",
          facetObject
        ));
  }

  public JsonArray addPaginationToQuery(JsonObject query_, String pageSize_, String pageNumber_) {
    int pageSize = Integer.parseInt(pageSize_);
    int pageNumber = Integer.parseInt(pageNumber_) - 1;
    return new JsonArray()
      .add(new JsonObject().put("$match", query_))
      //sort
      .add(new JsonObject().put("$skip", pageNumber * pageSize))
      .add(new JsonObject().put("$limit", pageSize));
  }

  private void callAggregate(RoutingContext rc, JsonArray paginationPipeline, JsonArray countPipeline) {
    JsonObject facetObject = new JsonObject().put("countData", countPipeline).put("paginationData", paginationPipeline);

    logObjectRepository.aggregate(createFacetQuery(facetObject), (bool, json) -> {
      JsonArray countData = (JsonArray) json.getValue("countData");
      JsonObject count = (JsonObject) countData.getValue(0);

      JsonArray paginationData = (JsonArray) json.getValue("paginationData");

      rc.response()
        .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
        .putHeader("x-count", count.getLong("count").toString())
        .setStatusCode(200)
        .end(paginationData.encodePrettily());
    });
  }
}

