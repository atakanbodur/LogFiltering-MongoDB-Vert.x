package com.foreks.atakanbodur.starter;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class LogObjectHandler {
  private final LogObjectService service;

  public LogObjectHandler(LogObjectService service) {
    this.service = service;
  }

  public List<JsonObject> readAll(RoutingContext rc) {
    return service.readAll();
  }

  public void readByCompany(RoutingContext rc) {
    JsonArray jsonObjectList = service.readByCompany(rc.pathParam("company"));
    rc.response().end("jsonObjectList.toBuffer()");
  }

  public List<JsonObject> readByUser(RoutingContext rc) {
    return service.readByUser(rc.pathParam("user"));
  }

  public List<JsonObject> readByMethod(RoutingContext rc) {
    return service.readByMethod(rc.pathParam("method"));
  }

  public List<JsonObject> readByStatusCode(RoutingContext rc) {
    return service.readByStatusCode(rc.pathParam("statusCode"));
  }

  public List<JsonObject> readByProcessTimeMS(RoutingContext rc) {
    return service.readByProcessTimeMS(rc.pathParam("processTimeMS"));
  }

  public List<JsonObject> readByProtocol(RoutingContext rc) {
    return service.readByProtocol(rc.pathParam("protocol"));
  }

  public List<JsonObject> readByPort(RoutingContext rc) {
    return service.readByPort(rc.pathParam("port"));
  }

  public List<JsonObject> readByHost(RoutingContext rc) {
    return service.readByHost(rc.pathParam("host"));
  }

  public List<JsonObject> readByResource(RoutingContext rc) {
    return service.readByResource(rc.pathParam("resource"));
  }

  public List<JsonObject> readByPlatform(RoutingContext rc) {
    return service.readByPlatform(rc.pathParam("platform"));
  }

  public List<JsonObject> readByAppName(RoutingContext rc) {
    return service.readByAppName(rc.pathParam("appName"));
  }

  public List<JsonObject> readByAppVersion(RoutingContext rc) {
    return service.readByAppVersion(rc.pathParam("appVersion"));
  }
}
