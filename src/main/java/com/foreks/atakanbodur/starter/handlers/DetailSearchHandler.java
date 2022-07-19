package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;
import com.mongodb.BasicDBObjectBuilder;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailSearchHandler extends GenericHandler{
  private final LogObjectRepository repository;

  public DetailSearchHandler(LogObjectRepository repository) {
    super(repository);
    this.repository = repository;
  }

  public void read(RoutingContext rc){
    JsonObject request = new JsonObject()
      .put("startDate", rc.request().getParam("startDate"))
      .put("endDate", rc.request().getParam("endDate"))
      .put("user", rc.request().getParam("username"))
      .put("statusCode", rc.request().getParam("status"));
    System.out.println("req from user: \n" + request.encodePrettily());
    try {
      JsonObject query = createQueryFromDateRange(request);

      if(request.getString("statusCode").equals("")){
        //bütün kayıtlar
        repository.read(query);
      }
      else if(request.getString("statusCode").equals("OK")){
        //response status 200 olan kayıtlar dönecek
        query.put("statusCode", "200");
        repository.read(query);
      }
      else{
        //response status 200 harici kayıtlar dönecek
        query.put("statusCode", BasicDBObjectBuilder.start("$ne", "200"));
        repository.read(query);
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  private JsonObject createQueryFromDateRange(JsonObject query) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = simpleDateFormat.parse(query.getString("startDate"));
    Date endDate = simpleDateFormat.parse(query.getString("endDate"));

    return new JsonObject().put("date", BasicDBObjectBuilder.start("$gte", startDate).add("$lte", endDate).get())
      .put("user", query.getValue("user"));
  }
}
