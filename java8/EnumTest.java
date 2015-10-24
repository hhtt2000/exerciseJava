package example.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumTest {

	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH)
				);
		System.out.println(
				menu.stream()
					   .filter(dish -> !dish.isVegetarian())
					   .limit(3)
					   .map(Dish::getName)
					   .collect(Collectors.toList())
		);
	}
}

class Dish {
	private final String name;
	private final boolean vegetarian;
	private final int calories;
	private final Type type;
	
	public Dish(String name, boolean vegetarian, int calories, Type type) {
		super();
		this.name = name;
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public int getCalories() {
		return calories;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public enum Type {
		MEAT, FISH, OTHER
	}
}