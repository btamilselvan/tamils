package cmecf.success.interview;

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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyRunner {
	
	static Integer data = 10;
	
	private Integer myInstanceVariable = 9999;

  private static void computeUsingMyFunction(MyFunctionalInteface myFunc, Object input) {
	  System.out.println(myFunc.compute(input));
  }
  
  private static void handsOn() {
	  
	  Map<String, Integer> map = new HashMap<>();
	  Integer result = map.computeIfAbsent("Hello", s->s.length());
	  System.out.println(result);
	  
	  result = map.computeIfAbsent("Hello", s->s.length());
	  result = map.computeIfAbsent("World", s->s.length());
	  System.out.println(result);
	  
	  System.out.println(map);
	  
	  result = map.computeIfPresent("Hello", (old, value) -> value*2);
	  System.out.println(result);
	  
	  System.out.println(map);
	  
	  Function<Integer, String> someFunc = val -> String.valueOf(val -1);
	  Function<Integer, String> thenFunc = someFunc.andThen(s->s.concat(" hello"));
	  System.out.println(thenFunc.apply(10));
	  
	  Function<Integer, String> composeFunc = someFunc.compose((Integer x) -> x+10);
	  
	  System.out.println(composeFunc.apply(10));
	  
	  IntFunction<String> myIntFunc = input -> String.valueOf(input);
	  
	  DoubleToLongFunction myDoubleToLongFunc = input -> Double.doubleToLongBits(input);

	  BiFunction<Integer, Integer, String> myBiFunc = (i, j) -> String.valueOf(i*j);
	  
	  Supplier<Integer> mySupplier = () -> new Random().nextInt();
	  mySupplier.get();
	  
	  Integer data = 10;
	  
	  Supplier<Integer> anotherSupplier = () -> {
//		  data = 20;
		return data*10;  
	  };
	  
	  IntSupplier myIntSupplier = () -> 10;
	  
	  
	  List<Integer> myList1 = List.of(1,2,3,4);
	  
	  List<Integer> myList = Stream.of(1,2,3,4).collect(Collectors.toList());
	  Consumer<List<Integer>> myConsumer = (list) -> list.forEach(System.out::print);
	  myConsumer.accept(myList);
	  
	  Consumer<List<Integer>> myConsumer1 = (list) -> {
		  for (int k = 0; k < list.size(); k++) {
			  list.set(k, list.get(k)*2);
		  }
	  };
	  System.out.println(myList);
	  myConsumer1.accept(myList);
	  System.out.println(myList);
	  
	  map.forEach((k, v) -> System.out.println("Key: "+k+" Value: "+v));
	  
	  Predicate<Integer> myPredicate = input -> input % 2==0;
	  myPredicate.test(20);
	  
	  BinaryOperator<Integer> myBinaryOperator = (a,b) -> a*b;
	  myBinaryOperator.apply(10, 20);
	  
	  UnaryOperator<Integer> myUnaryOperator = input -> input * 100;
	  myUnaryOperator.apply(20);
	  
	  System.out.println(Thread.currentThread().getName());
	  
	  Thread t = new Thread(() -> System.out.println("Thread started "+Thread.currentThread().getName()));
	  t.start();
  }
  
  private Supplier<Integer> mySupplier(Integer input){
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

    System.out.println(myInnerClassFunc.compute(10)); //returns 20
    System.out.println(myLambdaFunc.compute(10)); //returns 999 (enclosing scope)
  }

  public static void main(String[] args) {
	  MyFunctionalInteface<Integer, Integer> myFunc = val -> val*10;
	  
	  System.out.println(myFunc.computeDefault("hello default ..."));
	  
	  MyFunctionalInteface<String, String> myFunc1 = val -> val.concat(" Hello");
	  
	  computeUsingMyFunction(myFunc, 10);
	  computeUsingMyFunction(myFunc, 100);
	  
	  computeUsingMyFunction(myFunc1, "Tamil");
	  
	  handsOn();
	  
	  new MyRunner().lambdaCompute();
  }
}
