package com.foreks.atakanbodur.starter;



import io.vertx.core.json.JsonArray;
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
  public JsonArray readByCompany(String company){
    return logObjectRepository.readByCompany(company);
  }
  public List<JsonObject> readByUser(String user_){
    return logObjectRepository.readByUser(user_);
  }
  public List<JsonObject> readByMethod(String method_){
    return logObjectRepository.readByMethod(method_);
  }
  public List<JsonObject> readByStatusCode(String status_){
    return logObjectRepository.readByStatusCode(status_);
  }
  public List<JsonObject> readByProcessTimeMS(String processTimeMS_){ return logObjectRepository.readByProcessTimeMS(processTimeMS_); }
  public List<JsonObject> readByProtocol(String protocol_){
    return logObjectRepository.readByProtocol(protocol_);
  }
  public List<JsonObject> readByPort(String port_){
    return logObjectRepository.readByPort(port_);
  }
  public List<JsonObject> readByHost(String host_){
    return logObjectRepository.readByHost(host_);
  }
  public List<JsonObject> readByResource(String resource_){
    return logObjectRepository.readByResource(resource_);
  }
  public List<JsonObject> readByPlatform(String platform_){
    return logObjectRepository.readByPlatform(platform_);
  }
  public List<JsonObject> readByAppName(String appName_){
    return logObjectRepository.readByAppName(appName_);
  }
  public List<JsonObject> readByAppVersion(String appVersion_){ return logObjectRepository.readByAppVersion(appVersion_); }
}
