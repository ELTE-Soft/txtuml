package hu.elte.txtuml.api.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;
import java.util.function.Consumer;

import hu.elte.txtuml.api.model.error.CollectionCreationError;
import hu.elte.txtuml.api.model.error.LowerBoundError;
import hu.elte.txtuml.api.model.error.MultiplicityError;
import hu.elte.txtuml.api.model.error.UninitializedCollectionError;
import hu.elte.txtuml.api.model.error.UpperBoundError;
import hu.elte.txtuml.utils.InstanceCreator;
import hu.elte.txtuml.utils.RuntimeInvocationTargetException;

//TODO document
abstract class AbstractGeneralCollection<E, C extends AbstractGeneralCollection<E, C>> extends GeneralCollection<E>
		implements Cloneable {

	static final Object UNINITIALIZED_BACKEND;

	static {
		ClassLoader loader = AbstractGeneralCollection.class.getClassLoader();

		Class<?>[] interfaces = new Class[] { java.util.Collection.class };

		InvocationHandler handler = (proxy, method, args) -> {
			throw new UninitializedCollectionError();
		};

		UNINITIALIZED_BACKEND = (Collection<?>) Proxy.newProxyInstance(loader, interfaces, handler);
	}

	@SuppressWarnings("unchecked")
	private java.util.Collection<E> backend = (Collection<E>) UNINITIALIZED_BACKEND;

	AbstractGeneralCollection() {
	}

	// ACCESSIBLE METHODS

	@Override
	public final boolean isEmpty() {
		return backend.isEmpty();
	}

	@Override
	public final int size() {
		return backend.size();
	}

	@Override
	public final E one() {
		// FIXME NoSuchElementException
		return backend.iterator().next();
	}

	@Override
	public final boolean contains(Object element) {
		return backend.contains(element);
	}

	@Override
	public final C add(E element) {
		return createSameTyped(builder -> {
			builder.addAll(backend);
			builder.add(element);
		});
	}

	@Override
	public final C remove(Object element) {
		return createSameTyped(builder -> {
			Iterator<E> it = backend.iterator();
			while (it.hasNext()) {
				E e = it.next();
				if (e.equals(element)) {
					builder.addAll(it);
					break;
				} else {
					builder.add(e);
				}
			}
		});
	}

	@Override
	public final int getLowerBound() {
		return getLowerBoundPackagePrivate();
	}

	@Override
	public final int getUpperBound() {
		return getUpperBoundPackagePrivate();
	}

	@Override
	public final Iterator<E> iterator() {
		return backend.iterator();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected final C clone() {
		try {
			return (C) super.clone();
		} catch (CloneNotSupportedException e) {
			// Cannot happen.
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public final int hashCode() {
		// TODO collection hashCode
		return super.hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		// TODO collection equals
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getElementsListed();
	}

	@Override
	public final String getElementsListed() {
		StringJoiner joiner = new StringJoiner(", ");
		backend.forEach(e -> joiner.add(e.toString()));
		return joiner.toString();
	}

	// PACKAGE PRIVATE HELPER METHODS

	static <C extends GeneralCollection<?>, E> C create(Class<C> collectionType, Consumer<Builder<E>> backendBuilder)
			throws CollectionCreationError, MultiplicityError {
		return fillUninitialized(createUninitialized(collectionType), backendBuilder);
	}

	static <E> Any<E> createAnyOf(Consumer<Builder<E>> backendBuilder) {
		return fillUninitialized(new Any<>(), backendBuilder);
	}

	@SuppressWarnings("unchecked")
	final <C2 extends GeneralCollection<?>> C2 asUnsafe(Class<C2> collectionType)
			throws CollectionCreationError, MultiplicityError {
		C2 result = createUninitialized(collectionType);
		((AbstractGeneralCollection<E, ?>) result).setBackend(backend);
		return result;
	}

	final Any<E> asAnyUnsafe() {
		Any<E> result = new Any<>();
		((AbstractGeneralCollection<E, ?>) result).setBackend(backend);
		return result;
	}

	final OrderedAny<E> asOrderedAnyUnsafe() {
		OrderedAny<E> result = new OrderedAny<>();
		((AbstractGeneralCollection<E, ?>) result).setBackend(backend);
		return result;
	}

	final UniqueAny<E> asUniqueAnyUnsafe() {
		UniqueAny<E> result = new UniqueAny<>();
		((AbstractGeneralCollection<E, ?>) result).setBackend(backend);
		return result;
	}

	final OrderedUniqueAny<E> asOrderedUniqueAnyUnsafe() {
		OrderedUniqueAny<E> result = new OrderedUniqueAny<>();
		((AbstractGeneralCollection<E, ?>) result).setBackend(backend);
		return result;
	}

	final C createSameTyped(Consumer<Builder<E>> backendBuilder) throws MultiplicityError {
		C other = clone();
		((AbstractGeneralCollection<E, C>) other).setBackend(backendBuilder);
		return other;
	}

	final java.util.Collection<E> getBackend() {
		return backend;
	}

	int getLowerBoundPackagePrivate() {
		Min min = getClass().getAnnotation(Min.class);
		return (min == null) ? 0 : min.value();
	}

	int getUpperBoundPackagePrivate() {
		Max max = getClass().getAnnotation(Max.class);
		return (max == null) ? GeneralCollection.INFINITE_BOUND : max.value();
	}

	abstract java.util.Collection<E> createBackend(Consumer<Builder<E>> backendBuilder);

	// PRIVATE HELPER METHODS

	private static <C extends GeneralCollection<?>> C createUninitialized(Class<C> collectionType)
			throws CollectionCreationError {
		try {
			return InstanceCreator.create(collectionType);
		} catch (IllegalArgumentException | RuntimeInvocationTargetException e) {
			e.printStackTrace();
			throw new CollectionCreationError();
		}
	}

	private static <C extends GeneralCollection<?>, E> C fillUninitialized(C collection,
			Consumer<Builder<E>> backendBuilder) throws CollectionCreationError, MultiplicityError {
		try {
			@SuppressWarnings("unchecked")
			AbstractGeneralCollection<E, ?> casted = ((AbstractGeneralCollection<E, ?>) collection);
			casted.setBackend(backendBuilder);
		} catch (ClassCastException e) {
			// TODO exception handling
			throw new Error();
		}

		return collection;
	}

	private void setBackend(java.util.Collection<E> backend) throws MultiplicityError {
		int size = backend.size();
		if (size < getLowerBoundPackagePrivate()) {
			throw new LowerBoundError();
		}

		int upperBound = getUpperBoundPackagePrivate();
		if (size > upperBound && upperBound >= 0) {
			throw new UpperBoundError();
		}

		this.backend = backend;
	}

	private void setBackend(Consumer<Builder<E>> backendBuilder) throws MultiplicityError {
		java.util.Collection<E> backend = createBackend(backendBuilder);
		setBackend(backend);
	}

	// BUILDER

	@FunctionalInterface
	interface Builder<E> {

		@SafeVarargs
		static <E> Consumer<Builder<E>> createConsumerFor(E... elements) {
			return builder -> builder.add(elements);
		}

		void addNoReturn(E element);

		default Builder<E> add(E element) {
			addNoReturn(element);
			return this;
		}

		default Builder<E> add(E[] elements) {
			for (E e : elements) {
				this.addNoReturn(e);
			}
			return this;
		}

		default Builder<E> addAll(Iterable<? extends E> elements) {
			elements.forEach(this::addNoReturn);
			return this;
		}

		default Builder<E> addAll(Iterator<? extends E> elements) {
			elements.forEachRemaining(this::addNoReturn);
			return this;
		}

	}

}
