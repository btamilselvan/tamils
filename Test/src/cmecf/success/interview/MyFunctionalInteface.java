package cmecf.success.interview;

@FunctionalInterface
public interface MyFunctionalInteface<T, R> {
  R compute(T input);
  
  default String computeDefault(String input) {
	  System.out.println("inside default...");
	  return input.concat("default methdod");
  }
}

@FunctionalInterface
interface Func1<T,R> {
	R add (T input);
}
@FunctionalInterface
interface Func2<T,R> {
	R add (T input);
}

@FunctionalInterface
interface Func3<T,R> extends Func1<T,R> {
}
