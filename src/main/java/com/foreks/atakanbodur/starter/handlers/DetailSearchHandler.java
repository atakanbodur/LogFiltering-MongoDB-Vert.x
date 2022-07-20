package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class DetailSearchHandler extends GenericHandler {

  public DetailSearchHandler(LogObjectRepository repository) {
    super(repository);
  }

  public void read(RoutingContext rc) {
    super.getLogObjectRepository().setRc(rc);
    JsonObject request = new JsonObject()
      .put("startDate", rc.request().getParam("startDate"))
      .put("endDate", rc.request().getParam("endDate"))
      .put("user", rc.request().getParam("username"))
      .put("statusCode", rc.request().getParam("status"));
    try {
      JsonObject query = super.createQueryFromDateRange(request);

      if (request.getString("statusCode").equals("OK")) {
        //response status 200 olan kayıtlar dönecek
        query.put("statusCode", "200");
      } else if (request.getString("statusCode").equals("!OK")){
        //response status 200 harici kayıtlar dönecek
        query.put("statusCode", new JsonObject().put("$ne", "200"));
      }
      super.getLogObjectRepository().read(query);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }


}
