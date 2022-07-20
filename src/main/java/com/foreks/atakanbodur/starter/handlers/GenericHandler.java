package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import com.mongodb.BasicDBObjectBuilder;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
    logObjectRepository.readAll(rc);
  }

  public void readByCompany(RoutingContext rc) {
//    logObjectRepository.readByCompany(rc.pathParam("company"), res -> {
//      //hata ayıklama
//
//      if (res) {
//
//      }
//
//      else {
//
//      }
//    });
    logObjectRepository.readByCompany(rc.pathParam("company"), rc);
  }

  public void readByUser(RoutingContext rc) {
    logObjectRepository.readByUser(rc.pathParam("user"), rc);
  }

  public void readByMethod(RoutingContext rc) {
    logObjectRepository.readByMethod(rc.pathParam("method"), rc);
  }

  public void readByStatusCode(RoutingContext rc) {
    logObjectRepository.readByStatusCode(rc.pathParam("statusCode"), rc);
  }

  public void readByProcessTimeMS(RoutingContext rc) {
    logObjectRepository.readByProcessTimeMS(rc.pathParam("processTimeMS"), rc);
  }

  public void readByProtocol(RoutingContext rc) {
    logObjectRepository.readByProtocol(rc.pathParam("protocol"), rc);
  }

  public void readByPort(RoutingContext rc) {
    logObjectRepository.readByPort(rc.pathParam("port"), rc);
  }

  public void readByHost(RoutingContext rc) {
    logObjectRepository.readByHost(rc.pathParam("host"), rc);
  }

  public void readByResource(RoutingContext rc) {
    logObjectRepository.readByResource(rc.pathParam("resource"), rc);
  }

  public void readByPlatform(RoutingContext rc) {
    logObjectRepository.readByPlatform(rc.pathParam("platform"), rc);
  }

  public void readByAppName(RoutingContext rc) {
    logObjectRepository.readByAppName(rc.pathParam("appName"), rc);
  }

  public void readByAppVersion(RoutingContext rc) {
    logObjectRepository.readByAppVersion(rc.pathParam("appVersion"), rc);
  }

  public LogObjectRepository getLogObjectRepository() {
    return logObjectRepository;
  }

  protected JsonObject createQueryFromDateRange(JsonObject query_) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Date startDate = simpleDateFormat.parse(query_.getString("startDate"));
    Date endDate = simpleDateFormat.parse(query_.getString("endDate"));


    return new JsonObject().put("logDate",
      BasicDBObjectBuilder.start("$gte", new JsonObject()
          .put("$date", df.format(startDate)))
        .add("$lte", new JsonObject().put("$date", df.format(endDate))).get())
      .put("user", query_.getValue("user"));
  }
}

