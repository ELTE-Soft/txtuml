package hu.elte.txtuml.api.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import hu.elte.txtuml.api.model.ExternalSuperInterface.Container;

/**
 * Marks that some of the super interfaces of the annotated type are external,
 * that is, they are not part of the model. This annotation can be used to
 * suppress errors about external super interfaces which would not be allowed to
 * be used in the model in any other way.
 * <p>
 * The {@link value} annotation element has to be used to list external super
 * interfaces of the annotated type.
 * <p>
 * See the documentation of {@link Model} for an overview on modeling in
 * JtxtUML.
 * 
 * @see External
 * @see ExternalBody
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Repeatable(Container.class)
public @interface ExternalSuperInterface {

	/**
	 * External super interface(s) of the annotated type.
	 */
	Class<?>[] value();

	/**
	 * Container of repeatable annotation {@link ExternalSuperInterface}.
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Target(ElementType.TYPE)
	@Documented
	@Inherited
	@interface Container {
		ExternalSuperInterface[] value();
	}
}
