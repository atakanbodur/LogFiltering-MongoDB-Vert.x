package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class SummaryHandler extends GenericHandler {
  public SummaryHandler(LogObjectRepository logObjectRepository) {
    super(logObjectRepository);
  }

  //TODO: add info[] section for none "user" requests

  public void read(RoutingContext rc) {

    JsonObject request = new JsonObject()
      .put("startDate", rc.request().getParam("startDate"))
      .put("endDate", rc.request().getParam("endDate"))
      .put("user", rc.request().getParam("username"));

    Boolean hasUser = true;

    try {
      JsonObject query = super.createQueryFromDateRange(request);

      if (request.getString("user").equals("")) {
        hasUser = false;
        query.remove("user");
      }
      Boolean finalHasUser = hasUser;
      super.getLogObjectRepository().read(query, (res, jsonArray) -> {
        if (res) {
          rc.response().end(analyse(finalHasUser, jsonArray).encodePrettily());
        } else {
          rc.response().end("Repository error.");
        }
      });
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    countDistinctFields();
  }

  private JsonObject analyse(Boolean hasUser, JsonArray jsonArray) {
    int totalRequest = jsonArray.size();
    int totalSuccess = 0;
    int totalFailure = 0;
    double avgProcessTime = 0;

    for (int i = 0; i < jsonArray.size(); i++) {
      avgProcessTime += Integer.parseInt(jsonArray.getJsonObject(i).getString("processTimeMS"));
      if (!jsonArray.getJsonObject(i).getString("statusCode").equals("200")) {
        totalFailure++;
      }
    }
    totalSuccess = (totalRequest - totalFailure);
    avgProcessTime = avgProcessTime / jsonArray.size();


    return new JsonObject()
      .put("totalRequest:", totalRequest)
      .put("totalSuccess:", totalSuccess)
      .put("totalFailure:", totalFailure)
      .put("avgProcessTimeMS:", avgProcessTime);
  }
}
