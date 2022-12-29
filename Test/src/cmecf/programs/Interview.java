package cmecf.programs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class Interview {
	
	static void compute2() {
//		TreeMap<K, V>
		//choose a data structure to store collection of person objects
		// 1) The DS should preserve the natural order of the elements
		// 2) The time complexity to insert the elements shoud be o(1)
		// 3) The time complexity to 
	}

  static boolean compute1(List<Person> list, Person other) {
    if (list == null || other == null) {
      return false;
    }
    return list.stream().anyMatch(p -> p.getName().equalsIgnoreCase(other.getName()));
  }

  static boolean compute(List<Person> list, Person other) {
    boolean found = false;

    for (Person p : list) {
      if (other.getName().equalsIgnoreCase(p.getName())) {
        found = true;
      } else {
        found = false;
      }
    }
    if (found == false) {
      if (list == null && list.isEmpty()) {
        System.out.println("not found");
      }
      if (other == null) {
        System.out.println("not found");
      }
    }

    if (found == true) {
      System.out.println("found");
      System.out.println(other.getPassword());
    }

    return found;
  }

  static void groupByAge(List<Person> persons) {
    Map<Integer, List<Person>> groupByAge =
        persons.stream().collect(Collectors.groupingBy(Person::getAge));
    System.out.println(groupByAge);
  }

  static void sortByName(List<Person> persons) {
    Comparator<Person> nameComparator = (p1, p2) -> p1.getName().compareTo(p2.getName());
    List<Person> sortedPersons =
        persons.stream().sorted(nameComparator).collect(Collectors.toList());
    System.out.println(sortedPersons);
  }

  public static void main(String[] args) {
    List<Person> persons =
        List.of(
            new Person(10, "Bobbie", 30, "Test1234!", new Address("Fairfax", "VA")),
            new Person(5, "Peter", 20, "Test1234!", new Address("Irvine", "CA")),
            new Person(25, "Charlie", 35, null, null),
            new Person(40, "Stevie", 25, null, new Address("Miami", "FL")),
            new Person(45, "April", 25, "Test1234!", null));
    groupByAge(persons);
    sortByName(persons);

    List<Person> list = new ArrayList<>();
    list.add(new Person(10, "Bobbie", 30, "Test1234!", new Address("Fairfax", "VA")));
    list.add(new Person(5, "Peter", 20, "Abc1234!", null));
    list.add(new Person(45, "April", 25, null, new Address("DC", "DC")));

    //    sortByName(list);
  }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Person {
  Integer id;
  String name;
  Integer age;
  String password;
  Address address;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Address {
  String city;
  String state;
}
