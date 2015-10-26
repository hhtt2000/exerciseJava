package example.java8;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PythagorasTheorem {

	public static void main(String[] args) {
		//1
		Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
																	.flatMap(a -> IntStream.rangeClosed(a, 100)
																	.filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
																	.mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));
		
		pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] +", "+t[1]+", "+t[2]));
		//2
		Stream<int[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
																	.flatMap(a -> IntStream.rangeClosed(a, 100)
																								.mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a* a + b * b)})
																								.filter(t -> t[2] % 1 == 0));
	}

}
