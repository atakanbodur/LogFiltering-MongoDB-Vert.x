package com.foreks.atakanbodur.starter;



import io.vertx.core.json.JsonObject;

import java.util.List;

public class LogObjectService {
  private LogObjectRepository logObjectRepository;

  public LogObjectService(LogObjectRepository logObjectRepository) {
    this.logObjectRepository = logObjectRepository;
  }

  public List<JsonObject> readAll(){
    return logObjectRepository.readAll();
  }
}
