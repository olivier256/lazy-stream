package stream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import function.BiConsumer;
import function.Consumer;
import function.Function;
import function.IntFunction;
import function.Predicate;
import function.Predicates;
import function.Supplier;

public class Stream<T> {

	private final Iterator<T> iterator;

	private final List<Operator> operators;

	private Stream(final Iterator<T> stream) {
		this.iterator = stream;
		operators = new LinkedList<>();
	}

	public static <T> Stream<T> of(final List<T> list) {
		return new Stream<>(list.iterator());
	}

	public Stream<T> filter(final Predicate<T> p) {
		operators.add(new Operator(OperatorType.FILTER, p));
		return this;
	}

	public <R> Stream<T> map(final Function<T, R> f) {
		operators.add(new Operator(OperatorType.MAP, f));
		return this;
	}

	public Stream<T> distinct() {
		return filter(new Predicate<T>() {
			private final Set<T> keys = new HashSet<>();

			@Override
			public boolean test(final T t) {
				return keys.add(t);
			}

		});
	}

	public int count() {
		final Collector<T, Integer, Integer> counting = Collectors.counting();
		final Integer count = (Integer) collect(counting);
		return count;
	}

	public Object collect(final Collector collector) {
		final Supplier supplier = collector.supplier();
		Object folder = supplier.get();
		BiConsumer accumulator = collector.accumulator();
		boolean noMoreEvaluation = false;
		while (!noMoreEvaluation && iterator.hasNext()) {
			Object t = iterator.next();
			boolean occurenceAExclure = false;

			for (Operator operator : operators) {
				if (operator.getOperatorType() == OperatorType.FILTER) {
					Predicate p = (Predicate) operator.getOperator();
					if (!p.test(t)) {
						occurenceAExclure = true;
						break;
					}
				} else if (operator.getOperatorType() == OperatorType.LIMITER) {
					Predicate p = (Predicate) operator.getOperator();
					if (!p.test(t)) {
						noMoreEvaluation = true;
						occurenceAExclure = true;
						break;
					}
				} else if (operator.getOperatorType() == OperatorType.MAP) {
					Function f = (Function) operator.getOperator();
					t = f.apply(t);
				}
			}

			if (!occurenceAExclure) {
				accumulator.accept(folder, t);
			}
		}
		Function finisher = collector.finisher();
		return finisher.apply(folder);
	}

	public void forEach(final Consumer<T> consumer) {
		while (iterator.hasNext()) {
			T t = iterator.next();
			consumer.apply(t);
		}
	}

	public T[] toArray(final IntFunction<T[]> f) {
		T[] a = f.apply(count());
		int i = 0;
		while (iterator.hasNext()) {
			T t = iterator.next();
			a[i] = t;
			i++;
		}
		return a;
	}

	public Stream<T> limit(final int limit) {
		operators.add(new Operator(OperatorType.LIMITER, Predicates.limit(limit)));
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		while (iterator.hasNext()) {
			T t = iterator.next();
			sb.append(t.toString() + ", ");
		}
		return sb.toString();
	}

	public static void main(final String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 3, 5, 7, 11, 3, 13, 17, 19, 21, 23, 3, 29, 31, 37);

		final Collector<Integer, List<Integer>, List<Integer>> asList = Collectors.toList();
		final Function<Integer, Double> cos = new Function<Integer, Double>() {

			@Override
			public Double apply(final Integer t) {
				return Math.cos(t);
			}

		};
		final Function times10 = new Function() {

			@Override
			public Object apply(final Object t) {
				return (int) ((Double) t * 10);
			}

		};
		final Function<Double, Integer> times102 = new Function<Double, Integer>() {

			@Override
			public Integer apply(final Double t) {
				return (int) (t * 10);
			}

		};
		List<Integer> collectAsList = (List<Integer>) Stream.of(list).distinct().map(cos).limit(5).map(times10).collect(asList);
		System.out.println(collectAsList);
		System.exit(0);

		Collector<Integer, StringBuilder, String> asString = Collectors.joining();
		String collectAsString = (String) Stream.of(list).distinct().collect(asString);
		System.out.println(collectAsString);

		Stream.of(list).forEach(new Consumer<Integer>() {

			@Override
			public void apply(final Integer t) {
				System.out.println(t);

			}

		});

		Stream.of(list).toArray(new IntFunction<Integer[]>() {

			@Override
			public Integer[] apply(final int value) {
				return new Integer[value];
			}

		});
	}
}
