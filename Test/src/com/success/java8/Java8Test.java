package com.success.java8;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Test {

  public static void main(String[] args) {

    Stream.iterate(1, i -> i + 1).limit(45).forEach(i -> 
    System.out.println("insert into recipe_country values ('"+i+"','LATAM');"));

    /*	  Function<Country, List<CollectionDto>> func =
    		        entity ->
    		            entity
    		                .getCollectionList()
    		                .stream()
    		                .map(mapper::getDtoFromEntityLiteVersion)
    		                .collect(Collectors.toList());

    		    Function<Country, CountryCollectionDto> func2 =
    		        entity ->
    		            new CountryCollectionDto(
    		                entity.getCountryCode(),
    		                entity
    		                    .getCollectionList()
    		                    .stream()
    		                    .map(mapper::getDtoFromEntityLiteVersion)
    		                    .collect(Collectors.toList()));

    		    Function<List<List<CollectionDto>>, List<CollectionDto>> func1 = list -> list.get(0);

    		    countries.stream().map(c->func2).collect(Collectors.toList());

    		    Map<String, List<CollectionDto>> result =
    		        countries
    		            .stream()
    		            .collect(
    		                Collectors.groupingBy(
    		                    Country::getCountryCode,
    		                    Collectors.mapping(
    		                        func, Collectors.collectingAndThen(Collectors.toList(), func1))));

    */

    Function<Integer, Integer> func =
        (x) -> {
          System.out.println("one");
          return x * 2;
        };
    Function<Integer, Integer> func1 = (x) -> x * 3;

    //    System.out.println(func.apply(10));
    //    System.out.println(func.andThen(func1).apply(20));

    //    System.out.println(func.compose(func1).apply(20));
    //    Stream.generate(() -> giveSomething()).limit(10).forEach(System.out::println);

    //    Stream.iterate(10, i -> i + 5).limit(100).forEach(System.out::println);
    System.out.println(
        Stream.of("1", "2", "3", "1", "4", "3", "5")
            .collect(Collectors.groupingBy(s -> s, Collectors.toList())));
    System.out.println(
        Stream.of("1", "2", "3", "1", "4", "3", "5")
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

    Supplier<Integer> s = () -> 10;
  }

  private static Integer giveSomething() {
    return 10;
  }
}
