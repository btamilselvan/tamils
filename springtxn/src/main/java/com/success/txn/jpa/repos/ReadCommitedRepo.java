package com.success.txn.jpa.repos;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.success.txn.jpa.entities.Student;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ReadCommitedRepo {

  @Autowired private EntityManager em;
  private static final Logger logger = LoggerFactory.getLogger(ReadCommitedRepo.class);

  public String insertOne() {
    Student s = new Student();
    String address = "" + new Date();
    s.setName(address);
    s.setAddress(address);
    em.persist(s);
    logger.info("new row inserted with address {}", address);
    Student s1 = em.find(Student.class, 1);
    logger.info("new name for student 1 {}", address);
    s1.setName(address);
//    sleep(20000);
    sleep(5000);
    logger.info("awoke");
    return address;
  }

  public String getCount() {
    // when this method and insertOne are executed in parallel in two different txn, this code
    // executed but this txn was able to get the committed data from insertOne (count and name are
    // different after sleep)
    // meaning the data modified by insertOne did not reflect in this method

    // the same test while insertOne txn is in progress, this txn did not bring the data modified by
    // insertOne (count and name stayed same after sleep)
    CriteriaBuilder qb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    cq.select(qb.count(cq.from(Student.class)));
    logger.info("total rows before sleep {}", em.createQuery(cq).getSingleResult());
//    sleep(5000);
    sleep(10000);
    logger.info("total rows after sleep {}", em.createQuery(cq).getSingleResult());
    logger.info("name of the student 1 {}", em.find(Student.class, 1).getName());
    return "total rows " + em.createQuery(cq).getSingleResult();
  }

  private void sleep(long seconds) {
    try {
      Thread.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
