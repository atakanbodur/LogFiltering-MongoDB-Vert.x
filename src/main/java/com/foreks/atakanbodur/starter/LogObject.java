package com.foreks.atakanbodur.starter;

import io.vertx.core.json.JsonObject;

public class LogObject {
  private String logData;



  private String returnValueOf(String value) {
    int index;
    int nextIndex;
    index = logData.indexOf(value);
    if (index == -1)
      return "null";
    else {
      index += (value.length());
      String substr = logData.substring(index);
      nextIndex = substr.indexOf(" ");
      return substr.substring(0, nextIndex);
    }
  }

  public JsonObject initJSONObject() {
    return new JsonObject()
      .put("remoteClient", returnValueOf("remoteClient: "))
      .put("user", returnValueOf("user: "))
      .put("method", returnValueOf("method: "))
      .put("statusCode", returnValueOf("statusCode: "))
      .put("processTimeMS", returnValueOf("processTimeMS: "))
      .put("protocol", returnValueOf("x-forwarded-proto="))
      .put("port", returnValueOf("x-forwarded-port="))
      .put("host", returnValueOf("host="))
      .put("resource", returnValueOf("resource="))
      .put("platform", returnValueOf("platform="))
      .put("appName", returnValueOf("app-name="))
      .put("appVersion", returnValueOf("app-version="))
      .put("company", returnValueOf("company="));
  }

  public void setLogData(String logData) {
    this.logData = logData;
  }
}
