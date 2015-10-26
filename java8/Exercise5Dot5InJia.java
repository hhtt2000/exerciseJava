package example.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Exercise5Dot5InJia {

	public static void main(String[] args) {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
				new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000),
				new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710),
				new Transaction(mario, 2012, 700),
				new Transaction(alan, 2012, 950)
		);
		
		//5-1
		List<Transaction> tr2011 = transactions.stream()
						.filter(transaction -> transaction.getYear() == 2011)
						.sorted(Comparator.comparing(Transaction::getValue))
						.collect(Collectors.toList());
		
		//5-2
		List<String> cities = transactions.stream()
						.map(transaction -> transaction.getTrader().getCity())
						.distinct()
						.collect(Collectors.toList());
		
		//5-3
		List<Trader> tradersInCambridge = transactions.stream()
						.map(Transaction::getTrader)
						.filter(trader -> trader.getCity().equals("Cambridge"))
						.distinct()
						.sorted(Comparator.comparing(Trader::getName))
						.collect(Collectors.toList());
		
		//5-4
		String traderStr = transactions.stream().map(transaction -> transaction.getTrader().getName()).distinct().sorted().collect(Collectors.joining());
		
		//5-5
		boolean milanBased = transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
		
		//5-7
		Optional<Integer> highestValue = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
		
		//5-8
		Optional<Transaction> smallestTransaction = transactions.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
//		transactions.stream().min(Comparator.comparing(Transaction::getValue));
		
	}
}

class Trader {
	private final String name;
	private final String city;
	
	public Trader(String name, String city) {
		super();
		this.name = name;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return "Trader [name=" + name + ", city=" + city + "]";
	}
}

class Transaction {
	private final Trader trader;
	private final int year;
	private final int value;
	
	public Transaction(Trader trader, int year, int value) {
		super();
		this.trader = trader;
		this.year = year;
		this.value = value;
	}

	public Trader getTrader() {
		return trader;
	}

	public int getYear() {
		return year;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Transaction [trader=" + trader + ", year=" + year + ", value="
				+ value + "]";
	}
}
