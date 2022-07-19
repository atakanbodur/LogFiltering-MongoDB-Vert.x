package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;

public class StatisticsHandler extends GenericHandler{

  private final LogObjectRepository logObjectRepository;

  public StatisticsHandler(LogObjectRepository logObjectRepository) {
    super(logObjectRepository);
    this.logObjectRepository = logObjectRepository;
  }
}
