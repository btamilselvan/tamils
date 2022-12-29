package cmecf.programs;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

@RestController
public class PersonController {

  @Autowired private PersonService service;

  @GetMapping("/search")
  public List<Person> search(@RequestParam("name") String name) {
    return service.search(name);
  }
}

@Service
@Transactional
class PersonService {
  @Autowired private PersonRepository repo;

  public List<Person> search(String name) {
    return repo.findByName(name);
  }
}

@Repository
class PersonRepository {
  @Autowired private EntityManager em;

  public List<Person> findByName(String name) {
    TypedQuery<Person> query =
        em.createQuery("select p from Person p where p.name = :name", Person.class);
    query.setParameter("name", name);
    List<Person> result = query.getResultList();
    // load address
    result.forEach(p -> p.getAddress());
    return result;
  }
}

@Entity
@Getter
@Setter
class Person {
  @Id private int personId;
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "personId")
  private List<Address> address;
}

@Entity
@Getter
@Setter
class Address {
  private int addressId;
  private String city;
  private String state;
  @ManyToOne private Person personId;
}
