package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import com.mongodb.BasicDBObjectBuilder;
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
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readAll();
  }

  public void readByCompany(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByCompany(rc.pathParam("company"));
  }

  public void readByUser(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByUser(rc.pathParam("user"));
  }

  public void readByMethod(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByMethod(rc.pathParam("method"));
  }

  public void readByStatusCode(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByStatusCode(rc.pathParam("statusCode"));
  }

  public void readByProcessTimeMS(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByProcessTimeMS(rc.pathParam("processTimeMS"));
  }

  public void readByProtocol(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByProtocol(rc.pathParam("protocol"));
  }

  public void readByPort(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByPort(rc.pathParam("port"));
  }

  public void readByHost(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByHost(rc.pathParam("host"));
  }

  public void readByResource(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByResource(rc.pathParam("resource"));
  }

  public void readByPlatform(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByPlatform(rc.pathParam("platform"));
  }

  public void readByAppName(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByAppName(rc.pathParam("appName"));
  }

  public void readByAppVersion(RoutingContext rc) {
    this.logObjectRepository.setRc(rc);
    logObjectRepository.readByAppVersion(rc.pathParam("appVersion"));
  }

  public LogObjectRepository getLogObjectRepository() {
    return logObjectRepository;
  }

  protected JsonObject createQueryFromDateRange(JsonObject query) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Date startDate = simpleDateFormat.parse(query.getString("startDate"));
    Date endDate = simpleDateFormat.parse(query.getString("endDate"));


    return new JsonObject().put("logDate", BasicDBObjectBuilder.start("$gte", new JsonObject().put("$date", df.format(startDate)))
      .add("$lte", new JsonObject().put("$date", df.format(endDate))).get());
  }
}

