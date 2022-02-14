package com.success.txn.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.success.txn.jpa.entities.Recipe;
import com.success.txn.jpa.repos.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DataService {

  @Autowired private DateServiceOne one;

  @Autowired private RecipeRepository repo;

  public String paginate() {

    log.info("is active: {}", TransactionSynchronizationManager.isActualTransactionActive());

    //    Pageable pageable = PageRequest.of(1, 50);
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));

    Slice<Recipe> slices = repo.findAllSlice(pageable);
    System.out.println("next " + slices.hasNext());
    System.out.println("previous " + slices.hasPrevious());

    /*    pages.getContent();
    System.out.println("pages "+pages);
    System.out.println("num "+pages.getNumber());
    System.out.println("size "+pages.getSize());
    System.out.println("totalPages "+pages.getTotalPages());*/

    sort();
    return String.valueOf(slices.getNumberOfElements());
  }

  public void sort() {
    log.info("sort - is active: {}", TransactionSynchronizationManager.isActualTransactionActive());
    Pageable pageable = PageRequest.of(7, 50, Sort.by("title"));
    //    Page<Recipe> pages = repo.findAll(pageable);
    List<Recipe> pages1 = (List<Recipe>) repo.findAll(Sort.by("title"));

    one.txnCheck();
    one.txnRequiresNew();
    one.txnSupports();
    one.txnMandatory();
    one.txnRequired();
//    one.txnNever();
    one.txnNotSupported();
  }
}
