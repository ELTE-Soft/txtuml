package hu.elte.txtuml.api.model;

import java.util.function.Consumer;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

import hu.elte.txtuml.api.model.GeneralCollection.NonUnique;
import hu.elte.txtuml.api.model.GeneralCollection.Unordered;

// TODO document
public abstract class Collection<E, C extends Collection<E, C>>
		extends AbstractGeneralCollection<E, java.util.Collection<E>, C>
		implements Unordered<E>, NonUnique<E> {

	protected Collection() {
	}

	@Override
	public <C2 extends GeneralCollection<E>> C2 as(Class<C2> collectionType) {
		if (Unordered.class.isAssignableFrom(collectionType)
				&& NonUnique.class.isAssignableFrom(collectionType)) {
			return asUnsafe(collectionType);
		} else {
			// TODO exception handling
			throw new Error();
		}
	}

	@Override
	public final int countOf(E element) {
		java.util.Collection<E> backend = getBackend();
		if (backend instanceof Multiset) {
			return ((Multiset<E>) backend).count(element);
		}
		int count = 0;
		for (E e : backend) {
			if (e.equals(element)) {
				++count;
			}
		}
		return count;
	}

	@Override
	public final GeneralCollection<E> unbound() {
		return asAnyUnsafe();
	}

	@Override
	final java.util.Collection<E> getUninitializedBackend() {
		return null; // TODO uninitialized collection
	}

	@Override
	java.util.Collection<E> createBackend(Consumer<Builder<E>> backendBuilder) {
		ImmutableMultiset.Builder<E> builder = ImmutableMultiset.builder();
		backendBuilder.accept(builder::add);
		return builder.build();
	}

}
