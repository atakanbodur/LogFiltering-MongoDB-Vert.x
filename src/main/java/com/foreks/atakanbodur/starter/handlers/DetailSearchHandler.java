package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import com.mongodb.BasicDBObjectBuilder;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailSearchHandler extends GenericHandler {
  private final LogObjectRepository repository;

  public DetailSearchHandler(LogObjectRepository repository) {
    super(repository);
    this.repository = repository;
  }

  public void read(RoutingContext rc) {
    JsonObject request = new JsonObject().put("startDate", rc.request().getParam("startDate")).put("endDate", rc.request().getParam("endDate")).put("user", rc.request().getParam("username")).put("statusCode", rc.request().getParam("status"));
    try {
      JsonObject query = createQueryFromDateRange(request);

      if (request.getString("statusCode").equals("OK")) {
        //response status 200 olan kayıtlar dönecek
        query.put("statusCode", "200");
      } else {
        //response status 200 harici kayıtlar dönecek
        query.put("statusCode", new JsonObject().put("$ne", "200"));
      }

      repository.read(query);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  private JsonObject createQueryFromDateRange(JsonObject query) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Date startDate = simpleDateFormat.parse(query.getString("startDate"));
    Date endDate = simpleDateFormat.parse(query.getString("endDate"));


    JsonObject result = new JsonObject().put("logDate", BasicDBObjectBuilder.start("$gte", new JsonObject().put("$date", df.format(startDate))).add("$lte", new JsonObject().put("$date", df.format(endDate))).get());

    return result;
  }
}
