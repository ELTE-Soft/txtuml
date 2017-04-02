package hu.elte.txtuml.api.model.runtime.collections;

import java.util.ArrayList;

import hu.elte.txtuml.api.model.Collection;

/**
 * An immutable set (unique unordered collection) which may contain any number
 * of elements (including zero).
 * <p>
 * See the documentation of {@link hu.elte.txtuml.api.model.Model} for an
 * overview on modeling in JtxtUML.
 *
 * @param <T>
 *            the type of the contained elements
 */
public class Sequence<T> extends AbstractCollection<T, Sequence<T>, ArrayList<T>> implements Collection<T> {

	static <T> Builder<T, Sequence<T>> builder() {
		return Builder.create(ArrayList::new, Sequence<T>::new);
	}

	/**
	 * Creates an empty {@code Sequence}.
	 */
	public static <T> Sequence<T> empty() {
		return new Sequence<>();
	}

	/**
	 * Creates a {@code Sequence} which will contain the given elements.
	 */
	@SafeVarargs
	public static <T> Sequence<T> of(T... elements) {
		Builder<T, Sequence<T>> builder = builder();
		for (T elem : elements) {
			builder.add(elem);
		}
		return builder.build();
	}

	/**
	 * Returns the element of this mutable sequence at a certain index.
	 * 
	 * @param index
	 *            the index of the required element
	 * @return the element at the given index
	 */
	public T at(int index) {
		return backend.get(index);
	}

	/**
	 * Creates a {@code Sequence} which will contain the elements of the given
	 * iterable.
	 */
	public static <T> Sequence<T> copyOf(Iterable<T> elements) {
		return Sequence.<T> builder().addAll(elements).build();
	}

	private Sequence() {
		super(new ArrayList<>());
	}

	private Sequence(ArrayList<T> backend) {
		super(backend);
	}

	@Override
	protected Builder<T, Sequence<T>> createBuilder() {
		return builder();
	}

}
