package function;

/**
 * Represents a function that accepts an int-valued argument and produces a result. This is the int-consuming primitive specialization for Function.
 * @param <R> the type of the result of the function
 */
public interface IntFunction<R> {

	/**
	 * Applies this function to the given argument.
	 * @param value the function argument
	 * @return the function result
	 */
	R apply(int value);

}
