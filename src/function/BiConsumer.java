package function;

/**
 * /** Represents an operation that accepts two input arguments and returns no result. This is the two-arity specialization of Consumer. Unlike most
 * other functional interfaces, BiConsumer is expected to operate via side-effects.
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 */
public interface BiConsumer<T, U> {

	/**
	 * Performs this operation on the given arguments.
	 * @param t the first input argument
	 * @param u the second input argument
	 */
	void accept(T t, U u);

}
