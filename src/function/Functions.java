package function;

public class Functions {

	private Functions() {
		super();
	}

	public static final <T> Function<T, T> identity() {
		return new Function<T, T>() {

			@Override
			public T apply(final T t) {
				return t;
			}

		};
	}
}
