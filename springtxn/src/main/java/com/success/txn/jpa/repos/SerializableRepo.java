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
@Transactional(isolation = Isolation.SERIALIZABLE)
public class SerializableRepo {

  @Autowired private EntityManager em;
  private static final Logger logger = LoggerFactory.getLogger(SerializableRepo.class);

  public String insertOne() {
    Student s = new Student();
    String address = "" + new Date();
    s.setName(address);
    s.setAddress(address);
    em.persist(s);
    logger.info("new row inserted with address {}", address);
    sleep(20000);
    logger.info("awoke");
    return address;
  }

  public String getCount() {
    // when this method and insertOne are executed in parallel in two different txn, this code did
    // not execute (read operation) until insertOne txn is completed
    CriteriaBuilder qb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    cq.select(qb.count(cq.from(Student.class)));
    logger.info("total rows before sleep {}", em.createQuery(cq).getSingleResult());
    sleep(5000);
    logger.info("total rows after sleep {}", em.createQuery(cq).getSingleResult());
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
