package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class GenericHandler {
  private final LogObjectRepository logObjectRepository;

  public GenericHandler(LogObjectRepository logObjectRepository) {
    this.logObjectRepository = logObjectRepository;
  }

  public List<JsonObject> readAll(RoutingContext rc) {
    return logObjectRepository.readAll();
  }

  public void readByCompany(RoutingContext rc) {
    HttpServerResponse response = rc.response();
    JsonArray jsonObjectList = logObjectRepository.readByCompany(rc.pathParam("company"));
    response.write("hi!");
    rc.end();
  }

  public List<JsonObject> readByUser(RoutingContext rc) {
    return logObjectRepository.readByUser(rc.pathParam("user"));
  }

  public List<JsonObject> readByMethod(RoutingContext rc) {
    return logObjectRepository.readByMethod(rc.pathParam("method"));
  }

  public List<JsonObject> readByStatusCode(RoutingContext rc) {
    return logObjectRepository.readByStatusCode(rc.pathParam("statusCode"));
  }

  public List<JsonObject> readByProcessTimeMS(RoutingContext rc) {
    return logObjectRepository.readByProcessTimeMS(rc.pathParam("processTimeMS"));
  }

  public List<JsonObject> readByProtocol(RoutingContext rc) {
    return logObjectRepository.readByProtocol(rc.pathParam("protocol"));
  }

  public List<JsonObject> readByPort(RoutingContext rc) {
    return logObjectRepository.readByPort(rc.pathParam("port"));
  }

  public List<JsonObject> readByHost(RoutingContext rc) {
    return logObjectRepository.readByHost(rc.pathParam("host"));
  }

  public List<JsonObject> readByResource(RoutingContext rc) {
    return logObjectRepository.readByResource(rc.pathParam("resource"));
  }

  public List<JsonObject> readByPlatform(RoutingContext rc) {
    return logObjectRepository.readByPlatform(rc.pathParam("platform"));
  }

  public List<JsonObject> readByAppName(RoutingContext rc) {
    return logObjectRepository.readByAppName(rc.pathParam("appName"));
  }

  public List<JsonObject> readByAppVersion(RoutingContext rc) {
    return logObjectRepository.readByAppVersion(rc.pathParam("appVersion"));
  }
}
