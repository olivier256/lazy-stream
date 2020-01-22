package stream;

import java.util.LinkedList;
import java.util.List;

import function.BiConsumer;
import function.Function;
import function.Functions;
import function.Supplier;

public class Collectors {

	private Collectors() {
		super();
	}

	/** Returns a <CODE>Collector</CODE> that accumulates the input elements into a new <CODE>List</CODE>. */
	public static <T> Collector<T, List<T>, List<T>> toList() {
		return new Collector<T, List<T>, List<T>>() {

			@Override
			public Supplier<List<T>> supplier() {
				return new Supplier<List<T>>() {

					@Override
					public List<T> get() {
						return new LinkedList<>();
					}

				};
			}

			@Override
			public BiConsumer<List<T>, T> accumulator() {
				return new BiConsumer<List<T>, T>() {

					@Override
					public void accept(final List<T> t, final T u) {
						t.add(u);
					}
				};
			}

			@Override
			public Function<List<T>, List<T>> finisher() {
				return Functions.identity();
			}

		};
	}

	/** Returns a <CODE>Collector</CODE> that concatenates the input elements into a <CODE>String</CODE>, in encounter order. */
	public static <T> Collector<T, StringBuilder, String> joining() {
		return new Collector<T, StringBuilder, String>() {

			@Override
			public Supplier<StringBuilder> supplier() {
				return new Supplier<StringBuilder>() {

					@Override
					public StringBuilder get() {
						return new StringBuilder();
					}

				};
			}

			@Override
			public BiConsumer<StringBuilder, T> accumulator() {
				return new BiConsumer<StringBuilder, T>() {

					@Override
					public void accept(final StringBuilder sb, final T t) {
						if (sb.length() > 0) {
							sb.append(", " + t);
						} else {
							sb.append(t);
						}
					}
				};
			}

			@Override
			public Function<StringBuilder, String> finisher() {
				return new Function<StringBuilder, String>() {

					@Override
					public String apply(final StringBuilder sb) {
						return sb.toString();
					}

				};
			}

		};
	}

	/**
	 * Returns a <CODE>Collector</CODE> accepting elements of type <CODE>T</CODE> thatcounts the number of input elements. If no elements are present,
	 * the result is 0.
	 */
	public static <T> Collector<T, Integer, Integer> counting() {
		return new Collector<T, Integer, Integer>() {

			@Override
			public Supplier<Integer> supplier() {
				return new Supplier<Integer>() {

					@Override
					public Integer get() {
						return 0;
					}

				};
			}

			@Override
			public BiConsumer<Integer, T> accumulator() {
				return new BiConsumer<Integer, T>() {
					Integer n = 0;

					@Override
					public void accept(final Integer t, final T u) {
						n++;
					}

				};
			}

			@Override
			public Function<Integer, Integer> finisher() {
				return Functions.identity();
			}

		};
	}

}
