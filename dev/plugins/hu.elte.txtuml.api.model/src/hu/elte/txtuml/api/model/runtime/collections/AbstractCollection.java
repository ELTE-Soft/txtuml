package hu.elte.txtuml.api.model.runtime.collections;

import java.util.Iterator;
import java.util.function.Predicate;

import hu.elte.txtuml.api.model.Collection;

/**
 * An abstract immutable collection with a back-end Java collection.
 * <p>
 * See the documentation of {@link hu.elte.txtuml.api.model.Model} for an
 * overview on modeling in JtxtUML.
 *
 * @param <T>
 *            the type of items contained in the collection
 * @param <C>
 *            the concrete type of this immutable collection
 * @param <B>
 *            the back-end java collection
 */
public abstract class AbstractCollection<T, C extends AbstractCollection<T, C, B>, B extends java.util.Collection<T>>
		implements Collection<T> {

	final B backend;

	protected AbstractCollection(B backend) {
		this.backend = backend;
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = backend.iterator();
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public T next() {
				return it.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public boolean isEmpty() {
		return count() == 0;
	}

	@Override
	public int count() {
		return backend.size();
	}

	@Override
	public boolean contains(Object element) {
		return backend.contains(element);
	}

	@Override
	public T selectAny() {
		Iterator<T> it = backend.iterator();
		if (it.hasNext()) {
			return it.next();
		} else {
			return null;
		}
	}

	@Override
	public Collection<T> selectAll(Predicate<T> cond) {
		return createBuilder().addAll(backend.stream().filter(cond).iterator()).build();
	}

	@Override
	public C add(T element) {
		return createBuilder().addAll(backend).add(element).build();
	}

	@Override
	public C addAll(Collection<T> objects) {
		return createBuilder().addAll(backend).addAll(objects).build();
	}

	@Override
	@SuppressWarnings("unchecked")
	public C remove(Object element) {
		if (element == null) {
			return (C) this;
		}

		Builder<T, ? extends C> builder = createBuilder();
		Iterator<T> it = backend.iterator();
		while (it.hasNext()) {
			T e = it.next();
			if (element.equals(e)) {
				return builder.addAll(it).build();
			} else {
				builder.add(e);
			}
		}
		return (C) this;
	}

	protected abstract Builder<T, ? extends C> createBuilder();

}
