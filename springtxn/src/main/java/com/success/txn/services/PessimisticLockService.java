package com.success.txn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.success.txn.jpa.repos.PesssimisticLockRepo;

@Service
public class PessimisticLockService {
  @Autowired private PesssimisticLockRepo repo;

  public String readOne() {
    return repo.readOne();
  }

  public String readTwo() {
    return repo.readTwo();
  }
}
