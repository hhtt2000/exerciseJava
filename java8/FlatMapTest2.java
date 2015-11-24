package example.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * �� ���� ���� ����Ʈ�� ���� �� ��� ���� ���� ����Ʈ ��ȯ�ϱ�.
 * ���� ��� ����Ʈ[1,2,3]�� [3,4]�� �־����� [(1,3),(1,4),(2,3),(2,4),(3,3),(3,4)] ��ȯ.
 */
public class FlatMapTest2 {

	public static void main(String[] args) {
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);
		List<Object> collect = numbers1.stream()
												 .flatMap(i -> numbers2.stream()
														 				   .map(j -> "("+i+", "+j+")"))
												 .collect(Collectors.toList());
		
//		�帧
//		1   flatMap
//		2   map
//		3   collect(1, 3)
//		4   map
//		5   collect(1, 4)
//		------------------------
//		6   flatMap
//		7   map
//		8   collect(2, 3)
//		9   map
//		10 collect(2, 4)
//		------------------------
//		11 flatMap
//		12 map
//		13 collect(3, 3)
//		14 map
//		15 collect(3, 4)
		numbers1.stream()
					 .flatMap(i -> {
						 System.out.println("flatMap");
						 return numbers2.stream()
							 				   .map(j -> {
							 					   System.out
														.println("map");
							 					   return "("+i+", "+j+")";
							 				   });
					})
					.forEach(n -> System.out.println("collect" + n));
//					 .collect(Collectors.toList());
	}

}
