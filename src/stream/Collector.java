package stream;

import function.BiConsumer;
import function.Function;
import function.Supplier;

/**
 * /** A mutable reduction operation that accumulates input elements into a mutable result container
 * @param <T> the type of input elements to the reduction operation
 * @param <A> the mutable accumulation type of the reduction operation (often hidden as an implementation detail)
 * @param <R> the result type of the reduction operation
 */
public interface Collector<T, A, R> {

	/** A function that creates and returns a new mutable result container. */
	Supplier<A> supplier();

	/** A function that folds a value into a mutable result container. */
	BiConsumer<A, T> accumulator();

	/** Perform the final transformation from the intermediate accumulation type A to the final result type R. */
	Function<A, R> finisher();

}
