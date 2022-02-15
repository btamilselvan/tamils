package com.success.services;

import java.time.Instant;

import javax.inject.Inject;

import com.success.configs.repos.IMyRepository;

public class MyService {

  @Inject private IMyRepository repo;

  public String hello() {
    repo.findAll();
    return Instant.now().toString();
  }
}
