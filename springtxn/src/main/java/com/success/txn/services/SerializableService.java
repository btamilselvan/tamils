package com.success.txn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.success.txn.jpa.repos.SerializableRepo;

@Service
public class SerializableService {
  @Autowired private SerializableRepo repo;

  public String insertOne() {
    return repo.insertOne();
  }

  public String getCount() {
    return repo.getCount();
  }
}
