package com.foreks.atakanbodur.starter;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class LogObjectRouter {
  private final Vertx vertx;
  private final LogObjectHandler logObjectHandler;

  public LogObjectRouter(Vertx vertx, LogObjectHandler logObjectHandler) {
    this.vertx = vertx;
    this.logObjectHandler = logObjectHandler;
  }

  public Router buildLogObjectRouter(Router logRouter){
    logRouter.route("/api/logs*").handler(BodyHandler.create());
    logRouter.get("/api/logs").handler(logObjectHandler::readAll);
    logRouter.get("/api/logs/company/:company").handler(logObjectHandler::readByCompany);
    logRouter.get("/api/logs/user/:user").handler(logObjectHandler::readByUser);
    logRouter.get("/api/logs/method/:method").handler(logObjectHandler::readByMethod);
    logRouter.get("/api/logs/statusCode/:statusCode").handler(logObjectHandler::readByStatusCode);
    logRouter.get("/api/logs/processTimeMS/:processTimeMS").handler(logObjectHandler::readByProcessTimeMS);
    logRouter.get("/api/logs/protocol/:protocol").handler(logObjectHandler::readByProtocol);
    logRouter.get("/api/logs/port/:port").handler(logObjectHandler::readByPort);
    logRouter.get("/api/logs/host/:host").handler(logObjectHandler::readByHost);
    logRouter.get("/api/logs/resource/:resource").handler(logObjectHandler::readByResource);
    logRouter.get("/api/logs/platform/:platform").handler(logObjectHandler::readByPlatform);
    logRouter.get("/api/logs/appName/:appName").handler(logObjectHandler::readByAppName);
    logRouter.get("/api/logs/appVersion/:appVersion").handler(logObjectHandler::readByAppVersion);

    return logRouter;
  }
}
