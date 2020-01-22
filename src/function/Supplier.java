package function;

/**
 * Represents a supplier of results.<BR>
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 * @param <T>the type of results supplied by this supplier
 */
public interface Supplier<T> {

	/** Gets a result. */
	T get();

}
