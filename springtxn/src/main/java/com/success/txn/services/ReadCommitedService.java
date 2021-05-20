package com.success.txn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.success.txn.jpa.repos.ReadCommitedRepo;

@Service
public class ReadCommitedService {
  @Autowired private ReadCommitedRepo repo;

  public String insertOne() {
    return repo.insertOne();
  }

  public String getCount() {
    return repo.getCount();
  }
}
