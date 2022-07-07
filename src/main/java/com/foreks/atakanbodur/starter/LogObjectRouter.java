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
    logRouter.get("/api/logs/:user").handler(logObjectHandler::readByUser);

    return logRouter;
  }
}
