package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;

public class StatisticsHandler {

  private final LogObjectRepository logObjectRepository;

  public StatisticsHandler(LogObjectRepository logObjectRepository) {
    this.logObjectRepository = logObjectRepository;
  }
}
