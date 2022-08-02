package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class SummaryHandler extends GenericHandler {
  public SummaryHandler(LogObjectRepository logObjectRepository) {
    super(logObjectRepository);
  }


  public void read(RoutingContext rc) {
    JsonObject request = new JsonObject()
      .put("startDate", rc.request().getParam("startDate"))
      .put("endDate", rc.request().getParam("endDate"));
    Boolean hasUser = false;
    if (rc.request().params().contains("username")) {
      hasUser = true;
      request.put("user", rc.request().getParam("username"));
    }
    List<String> dataToBeFilteredFor = new ArrayList<>();
    dataToBeFilteredFor.add("remoteClient");
    dataToBeFilteredFor.add("company");
    dataToBeFilteredFor.add("statusCode");
    try {
      JsonObject query = super.createQueryFromDateRange(request);
      Boolean finalHasUser = hasUser;
      super.getLogObjectRepository().read(query, (res, jsonArray) -> {
        if (res) {
          analyse(finalHasUser, request, dataToBeFilteredFor, jsonArray, (bool, jsonObject) -> {
            rc.response()
              .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
              .setStatusCode(200)
              .end(jsonObject.encodePrettily());
          });
        } else {
          rc.response().end("Repository error.");
        }
      });
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void analyse(Boolean hasUser, JsonObject query, List<String> dataToBeFilteredFor, JsonArray dbResults, BiConsumer<Boolean, JsonArray> consumer) {
    int totalRequest = dbResults.size();
    int totalSuccess = 0;
    int totalFailure = 0;
    double avgProcessTime = 0;

    for (int i = 0; i < dbResults.size(); i++) {
      avgProcessTime += Integer.parseInt(dbResults.getJsonObject(i).getString("processTimeMS"));
      if (!dbResults.getJsonObject(i).getString("statusCode").equals("200")) {
        totalFailure++;
      }
    }
    totalSuccess = (totalRequest - totalFailure);
    avgProcessTime = avgProcessTime / dbResults.size();

    JsonObject returnValue = new JsonObject()
      .put("totalRequest", totalRequest)
      .put("totalSuccess", totalSuccess)
      .put("totalFailure", totalFailure)
      .put("avgProcessTimeMS", avgProcessTime);


    if (hasUser) {
      createInfoField(query, dataToBeFilteredFor, (res, jsonArray) -> {
        if (!res) consumer.accept(false, new JsonArray().add(returnValue));
        else consumer.accept(true, new JsonArray().add(returnValue.put("info", jsonArray)));
      });
    } else {
      consumer.accept(true, new JsonArray().add(returnValue));
    }
  }

  private void createInfoField(JsonObject query, List<String> dataToBeFilteredFor, BiConsumer<Boolean, JsonArray> consumer) {
    Map<String, JsonArray> categories = new HashMap<>();
    try {
      for (String filterFor : dataToBeFilteredFor) {
        query.put("filterFor", filterFor);
        categories.put(filterFor, countDistinctFields(query));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    JsonArray pipelineToAggregate = super.createFacetQuery(categories);
    getLogObjectRepository().aggregate(pipelineToAggregate, (bool, jsonObject) -> {
      consumer.accept(bool, new JsonArray().add(jsonObject));
    });
  }
}
