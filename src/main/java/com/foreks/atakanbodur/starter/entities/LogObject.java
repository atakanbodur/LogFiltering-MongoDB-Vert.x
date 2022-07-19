package com.foreks.atakanbodur.starter.entities;

import io.vertx.core.json.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

  private JsonObject getDate() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    try {
      Date date = simpleDateFormat.parse(logData.substring(0, 23));
      System.out.println("using with format(): " + simpleDateFormat.format(date));
      return new JsonObject().put("$date", df.format(date));
    } catch (Exception e) {
      System.out.println(e.getCause());
      return null;
    }
  }

  public JsonObject initJSONObject() {
    return new JsonObject()
      .put("logDate", getDate())
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
