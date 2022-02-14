package cmecf.success.interview;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MyRunner {

  static Integer data = 10;

  private Integer myInstanceVariable = 9999;

  private static void computeUsingMyFunction(MyFunctionalInteface myFunc, Object input) {
    System.out.println(myFunc.compute(input));
  }

  private static void handsOn() {

    Map<String, Integer> map = new HashMap<>();
    Integer result = map.computeIfAbsent("Hello", s -> s.length());
    System.out.println(result);

    result = map.computeIfAbsent("Hello", s -> s.length());
    result = map.computeIfAbsent("World", s -> s.length());
    System.out.println(result);

    System.out.println(map);

    result = map.computeIfPresent("Hello", (old, value) -> value * 2);
    System.out.println(result);

    System.out.println(map);

    Function<Integer, String> someFunc = val -> String.valueOf(val - 1);
    Function<Integer, String> thenFunc = someFunc.andThen(s -> s.concat(" hello"));
    System.out.println(thenFunc.apply(10));

    Function<Integer, String> composeFunc = someFunc.compose((Integer x) -> x + 10);

    System.out.println(composeFunc.apply(10));

    IntFunction<String> myIntFunc = input -> String.valueOf(input);

    ToIntFunction<String> myToInt = input -> Integer.parseInt(input);
    System.out.println(myToInt.applyAsInt("10"));

    DoubleToLongFunction myDoubleToLongFunc = input -> Double.doubleToLongBits(input);

    BiFunction<Integer, Integer, String> myBiFunc = (i, j) -> String.valueOf(i * j);

    Supplier<Integer> mySupplier = () -> new Random().nextInt();
    mySupplier.get();

    Integer data = 10;

    Supplier<Integer> anotherSupplier =
        () -> {
          //		  data = 20;
          return data * 10;
        };

    IntSupplier myIntSupplier = () -> 10;

    List<Integer> myList1 = List.of(1, 2, 3, 4);

    List<Integer> myList = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
    Consumer<List<Integer>> myConsumer = (list) -> list.forEach(System.out::print);
    myConsumer.accept(myList);

    Consumer<List<Integer>> myConsumer1 =
        (list) -> {
          for (int k = 0; k < list.size(); k++) {
            list.set(k, list.get(k) * 2);
          }
        };
    System.out.println(myList);
    myConsumer1.accept(myList);
    System.out.println(myList);

    Consumer<String> strConsume = input -> System.out.print(input);
    Consumer<String> strConsume1 = System.out::print;
    strConsume.accept("dsdasda");

    map.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v));

    Predicate<Integer> myPredicate = input -> input % 2 == 0;
    myPredicate.test(20);

    BinaryOperator<Integer> myBinaryOperator = (a, b) -> a * b;
    myBinaryOperator.apply(10, 20);

    UnaryOperator<Integer> myUnaryOperator = input -> input * 100;
    myUnaryOperator.apply(20);

    System.out.println(Thread.currentThread().getName());

    Thread t =
        new Thread(() -> System.out.println("Thread started " + Thread.currentThread().getName()));
    t.start();

    int local = 10;

    IntSupplier intSupplier =
        () -> {
          return local;
        };
  }

  private Supplier<Integer> mySupplier(Integer input) {
    return () -> input;
  }

  private void lambdaCompute() {
    MyFunctionalInteface<Integer, Integer> myInnerClassFunc =
        // inner class gets new scope
        new MyFunctionalInteface<Integer, Integer>() {
          Integer myInstanceVariable = 20;

          @Override
          public Integer compute(Integer input) {
            return this.myInstanceVariable; // refer the variable in the new scope
          }
        };

    MyFunctionalInteface<Integer, Integer> myLambdaFunc =
        input -> {
          // lambda doesn't get new scope. it uses the enclosing scope.
          Integer myInstanceVariable = 1;
          this.myInstanceVariable = 1200;
          return this.myInstanceVariable; // refer the enclosing scope
        };

    System.out.println(myInnerClassFunc.compute(10)); // returns 20
    System.out.println(myLambdaFunc.compute(10)); // returns 999 (enclosing scope)
  }

  private void streamCompute() {

    Stream.of("A", "B", "C", "D").skip(2).forEach(System.out::println);
    Stream.iterate(10, input -> input + 2).limit(2).forEach(System.out::println);

    // initial value, hasNext function(stream will terminate when hasNext returns false),
    // UnaryOperator
    Stream.iterate(16, input -> input % 4 == 0, input -> input + 2).forEach(System.out::println);

    System.out.println("cout: " + Arrays.stream(new int[] {10, 20, 30, 40}).count());
    System.out.println("sum: " + Arrays.stream(new int[] {10, 20, 30, 40}).sum());
    System.out.println("max: " + Arrays.stream(new int[] {10, 20, 30, 40}).max().getAsInt());
    System.out.println("min: " + Arrays.stream(new int[] {10, 20, 30, 40}).min().getAsInt());

    Arrays.asList("a", "b", "c", "d").parallelStream();
    Arrays.asList("a", "b", "c", "d").stream();
    Arrays.asList("a", "b", "c", "d").stream().parallel();
    
    Collection<String> myColl = Arrays.asList("a", "b", "c", "d");
    myColl.stream();

    Stream.<Integer>builder().add(1).add(1).build();

    try {
      Files.readAllLines(Paths.get("t:\\downloads\\abc.txt")).stream().forEach(System.out::println);

      // initial value, accumulator (BinaryOperator/BiFunction)
      System.out.println(LongStream.of(10, 20, 30, 40).reduce(10, (i, j) -> i + j));
      // accumulator (BinaryOperator/BiFunction)
      System.out.println(LongStream.of(10, 20, 30, 40).reduce((i, j) -> i + j).getAsLong());

      //      System.out.println(LongStream.of(10, 20, 30, 40).reduce(10, (i, j) -> i+j, (i, j) ->
      // i+j));

      //      Files.readAllLines(Paths.get("t:\\downloads\\abc.txt")).stream().reduce(accumulator);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void collectorsCompute() {
    try {

      List<List<String>> listOfList = List.of(List.of("a", "b", "c", "d"));
      List<List<Integer>> listOfList1 = List.of(List.of(1, 2, 3, 4, 5, 6, 6, 2, 4));
      
      System.out.println(
              "group by: "
                  + listOfList1
                      .stream()
                      .flatMap(List::stream)
                      .collect(Collectors.toUnmodifiableSet()));

      System.out.println(
          "Count " + listOfList1.stream().flatMap(List::stream).collect(Collectors.counting()));

      System.out.println(
          "Avg "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.averagingInt(input -> input)));

      System.out.println(
          listOfList1
              .stream()
              .flatMap(List::stream)
              .collect(
                  Collectors.collectingAndThen(
                      Collectors.averagingInt(input -> input), input -> "Avg is: " + input)));
      
      System.out.println(
              listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(
                      Collectors.collectingAndThen(
                          Collectors.averagingInt(input -> input), input -> "Avg is: " + input)));

      listOfList.stream().flatMap(List::stream).distinct().forEach(System.out::println);

      System.out.println(
          "Distinct count: "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .distinct()
                  .collect(Collectors.counting()));

      System.out.println(
          "Only odd count: "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .distinct()
                  .collect(Collectors.filtering(input -> input % 2 == 0, Collectors.counting())));

      // group by {1=[1], 2=[2, 2], 3=[3], 4=[4, 4], 5=[5], 6=[6, 6]}
      System.out.println(
          "group by: "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.groupingBy(input -> input)));

      System.out.println(
          "group by count: "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.groupingBy(num -> num, Collectors.counting())));

      var recipes = new ArrayList<Recipe>();
      for (int i = 1; i < 5; i++) {
        var r = new Recipe("R" + i);
        var p = new Product("P" + i, "Desc" + i);
        var p2 = new Product("P2" + i, "Desc2" + i);
        var p1 = new Product("XP" + i, "XDesc" + i);
        List<Product> products = Arrays.asList(p, p1, p2);
        r.products = products;
        recipes.add(r);
      }

      System.out.println(
          "product name from list of recipes of list of products"
              + recipes
                  .stream()
                  .flatMap(r -> r.products.stream())
                  .collect(Collectors.mapping(p -> p.name, Collectors.joining(",", "[", "]"))));
      
      System.out.println(
              "distinct product name from list of recipes of list of products"
                  + recipes
                      .stream()
                      .flatMap(r -> r.products.stream())
                      .collect(Collectors.mapping(p -> p.name, Collectors.toSet())));

      System.out.println(
          "product map from list of recipes of list of products"
              + recipes
                  .stream()
                  .flatMap(r -> r.products.stream())
                  .collect(Collectors.toMap(p -> p.name, p -> p)));

      System.out.println(
          "toMap by: "
              + listOfList
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.toMap(input -> input, input -> input)));

      System.out.println(
          "partitioningBy: "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.partitioningBy(input -> input % 2 == 0)));

      System.out.println(
          "reduction using binaryoperator "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.reducing(10, (i, j) -> i + j)));

      System.out.println(
          "reduction using mapper then binaryoperator "
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.reducing(100, i -> i + 10, (i, j) -> i + j)));

      System.out.println(
          "Summarizing int statictics"
              + listOfList1
                  .stream()
                  .flatMap(List::stream)
                  .collect(Collectors.summarizingInt(input -> input)));
      
      Arrays.asList("a", "b", "c", "d").stream().close();

      /*listOfList1
      .stream()
      .flatMap(List::stream)
      .collect(Collectors.toCollection(Collections::emptyList));*/

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    /*MyFunctionalInteface<Integer, Integer> myFunc = val -> val * 10;

    System.out.println(myFunc.computeDefault("hello default ..."));

    MyFunctionalInteface<String, String> myFunc1 = val -> val.concat(" Hello");

    computeUsingMyFunction(myFunc, 10);
    computeUsingMyFunction(myFunc, 100);

    computeUsingMyFunction(myFunc1, "Tamil");

    handsOn();

    new MyRunner().lambdaCompute();
    new MyRunner().streamCompute();*/
    new MyRunner().collectorsCompute();
  }

  static class Recipe {
    String name;
    List<Product> products;

    Recipe(String name) {
      this.name = name;
    }

    Recipe() {}
  }

  static class Product {
    String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    String desc;

    Product(String name, String desc) {
      this.name = name;
      this.desc = desc;
    }

    Product() {}
  }
}
