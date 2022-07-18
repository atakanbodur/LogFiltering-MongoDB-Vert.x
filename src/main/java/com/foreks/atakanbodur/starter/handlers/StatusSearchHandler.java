package com.foreks.atakanbodur.starter.handlers;

import com.foreks.atakanbodur.starter.repositories.LogObjectRepository;

public class StatusSearchHandler {
  private final LogObjectRepository logObjectRepository;

  public StatusSearchHandler(LogObjectRepository logObjectRepository) {
    this.logObjectRepository = logObjectRepository;
  }
}
