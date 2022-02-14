package com.success.txn.services;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.success.txn.jpa.entities.Recipe;
import com.success.txn.jpa.repos.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
// @Transactional
@Slf4j
public class DateServiceOne {
  @Autowired private RecipeRepository repo;

  public void txnCheck() {
    log.info(
        "txnCheck - is active: {}", TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));
  }

  @Transactional(value = TxType.SUPPORTS)
  public void txnSupports() {
    log.info(
        "txnSupports - is active: {}",
        TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));
  }

  @Transactional(value = TxType.MANDATORY)
  public void txnMandatory() {
    log.info(
        "txnMandatory - is active: {}",
        TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));
  }

  @Transactional(value = TxType.REQUIRES_NEW)
  public void txnRequiresNew() {
    log.info(
        "RequiresNew - is active: {}",
        TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));
  }

  @Transactional(value = TxType.REQUIRED)
  public void txnRequired() {
    log.info(
        "txnRequired - is active: {}",
        TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));
    
    txnNotSupported();
    txnNever();
  }

  @Transactional(value = TxType.NOT_SUPPORTED)
  public void txnNotSupported() {
    log.info(
        "NOT_SUPPORTED - is active: {}",
        TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));
  }

  @Transactional(value = TxType.NEVER)
  public void txnNever() {
    log.info(
        "txnNever - is active: {}", TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));
  }
}
