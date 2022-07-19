package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import io.vertx.core.Future;
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

  public void readAll(RoutingContext rc) {
    logObjectRepository.readAll();
  }

  public void readByCompany(RoutingContext rc) {
   logObjectRepository.readByCompany(rc.pathParam("company"));

  }

  public void readByUser(RoutingContext rc) {
    logObjectRepository.readByUser(rc.pathParam("user"));
  }

  public void readByMethod(RoutingContext rc) {
    logObjectRepository.readByMethod(rc.pathParam("method"));
  }

  public void readByStatusCode(RoutingContext rc) {
    logObjectRepository.readByStatusCode(rc.pathParam("statusCode"));
  }

  public void readByProcessTimeMS(RoutingContext rc) {
    logObjectRepository.readByProcessTimeMS(rc.pathParam("processTimeMS"));
  }

  public void readByProtocol(RoutingContext rc) {
    logObjectRepository.readByProtocol(rc.pathParam("protocol"));
  }

  public void readByPort(RoutingContext rc) {
    logObjectRepository.readByPort(rc.pathParam("port"));
  }

  public void readByHost(RoutingContext rc) {
    logObjectRepository.readByHost(rc.pathParam("host"));
  }

  public void readByResource(RoutingContext rc) {
    logObjectRepository.readByResource(rc.pathParam("resource"));
  }

  public void readByPlatform(RoutingContext rc) {
    logObjectRepository.readByPlatform(rc.pathParam("platform"));
  }

  public void readByAppName(RoutingContext rc) {
    logObjectRepository.readByAppName(rc.pathParam("appName"));
  }

  public void readByAppVersion(RoutingContext rc) {
    logObjectRepository.readByAppVersion(rc.pathParam("appVersion"));
  }
}
