package example.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapTest {

	public static void main(String[] args) {
		List<String> words = Arrays.asList("Hello", "World");
		
		//[java.util.stream.ReferencePipeline$Head@4a574795, java.util.stream.ReferencePipeline$Head@f6f4d33]
		//�� �ܾ�(Hello)�� split�ؼ� ���� string(H,e,l,l,o)�� ������ stream�� ����.
		List<Stream<String>> collect = words.stream()
				                            .map(word -> word.split(""))
				                            .map(Arrays::stream)
				                            .distinct()
				                            .collect(Collectors.toList());
		
		//[[Ljava.lang.String;@3f99bd52, [Ljava.lang.String;@4f023edb]
		List<String[]> collect2 = words.stream()
				.map(word -> word.split(""))
				.distinct()
				.collect(Collectors.toList());
		
		//[H, e, l, o, W, r, d]
		List<String> collect3 = words.stream()
				.map(word -> word.split(""))
				.flatMap(Arrays::stream)
				.distinct()
				.collect(Collectors.toList());

	}

}
