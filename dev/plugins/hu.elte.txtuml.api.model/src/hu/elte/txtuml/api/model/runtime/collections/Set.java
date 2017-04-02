package hu.elte.txtuml.api.model.runtime.collections;

import java.util.HashSet;

import hu.elte.txtuml.api.model.Collection;

public class Set<T> extends AbstractCollection<T, Set<T>, HashSet<T>> implements Collection<T> {

	static <T> Builder<T, Set<T>> builder() {
		return Builder.create(HashSet<T>::new, Set<T>::new);
	}

	/**
	 * Creates an empty {@code Set}.
	 */
	public static <T> Set<T> empty() {
		return new Set<>();
	}

	/**
	 * Creates a {@code Set} which will contain the elements of the given
	 * iterable.
	 */
	public static <T> Set<T> copyOf(Iterable<T> elements) {
		return Set.<T> builder().addAll(elements).build();
	}
	
	private Set() {
		super(new HashSet<T>());
	}

	private Set(HashSet<T> backend) {
		super(backend);
	}

	@Override
	protected Builder<T, Set<T>> createBuilder() {
		return builder();
	}

}
