package function;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 * @param <T> the type of the input to the predicate
 */
public interface Predicate<T> {

	/**
	 * Evaluates this predicate on the given argument.
	 * @param t the input argument
	 * @return <CODE>true</CODE> if the input argument matches the predicate, otherwise <CODE>false</CODE>
	 */
	boolean test(T t);

}
