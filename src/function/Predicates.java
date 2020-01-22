package function;

public class Predicates {

	public static <T> Predicate<T> limit(final int limit) {
		return new Predicate<T>() {
			int n = 0;

			@Override
			public boolean test(final T t) {
				n++;
				return n <= limit;
			}

		};
	}

}
