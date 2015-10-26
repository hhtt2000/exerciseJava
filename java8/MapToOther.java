package example.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapToOther {

	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH)
				);
		
		IntStream intStream = menu.stream().mapToInt(Dish::getCalories);

		int calories = intStream//IntStream으로 변환
				.sum();
		
		Stream<Integer> boxedStream = intStream.boxed();//숫자 스트림을 스트림으로 변환
	}

}
