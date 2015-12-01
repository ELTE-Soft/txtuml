package hu.elte.txtuml.api.model.backend;

import hu.elte.txtuml.api.model.Collection;
import hu.elte.txtuml.api.model.backend.collections.JavaCollectionOfMany;

/**
 * A mutable collection builder to build {@link Collection Collections} faster.
 * 
 * @author Gabor Ferenc Kovacs
 *
 * @param <T>
 *            the type of model objects to be contained in the result collection
 */
public class CollectionBuilder<T> {

	/**
	 * The Java collection in which this builder collects the desired objects.
	 */
	private JavaCollectionOfMany<T> coll = JavaCollectionOfMany.create();

	/**
	 * Appends this builder.
	 * 
	 * @param object
	 *            the object to include in the result collection
	 * @return this object
	 * @throws NullPointerException
	 *             if this builder was already used up
	 * 
	 * @see #getJavaCollection()
	 */
	public CollectionBuilder<T> append(T object) {
		coll.add(object);
		return this;
	}

	/**
	 * Appends this builder.
	 * 
	 * @param objects
	 *            a collection which's elements are to be included in the result
	 *            collection
	 * @return this object
	 * @throws NullPointerException
	 *             if this builder was already used up
	 * 
	 * @see #getJavaCollection()
	 */
	public CollectionBuilder<T> append(Collection<T> objects) {
		objects.forEach(coll::add);
		return this;
	}

	/**
	 * Returns the Java collection in which this builder collected the desired
	 * objects. When this method is called, this builder is used up, so its Java
	 * collection is set to <code>null</code>. This way, it is ensured that the
	 * created Java collection is no more appended, which makes it possible for
	 * {@link Collection} implementations to use it directly instead of copying
	 * it (for optimization).
	 * 
	 * @return the Java collection in which this builder collected the desired
	 *         objects
	 */
	public JavaCollectionOfMany<T> getJavaCollection() {
		JavaCollectionOfMany<T> tmp = coll;
		coll = null;
		return tmp;
	}

}