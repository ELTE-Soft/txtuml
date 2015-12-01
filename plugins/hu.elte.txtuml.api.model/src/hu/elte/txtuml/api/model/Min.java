package hu.elte.txtuml.api.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A runtime annotation to define the lower bound of a custom multiplicity association end.
 * 
 * <p>
 * <b>Represents:</b> lower bound of a custom multiplicity association end
 * <p>
 * <b>Usage:</b>
 * <p>
 * 
 * See the documentation of {@link Association.Multiple}.
 * 
 * <p>
 * <b>Example:</b>
 * 
 * <pre>
 * <code>
 * {@literal @Min(1) @Max(2)}
 * class SampleEnd extends Multiple{@literal <SampleClass>} {}
 * </code>
 * </pre>
 * 
 * See the documentation of {@link Model} for an overview on modeling in JtxtUML.
 *
 * @author Gabor Ferenc Kovacs
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Min {
	
	/**
	 * The lower bound of the association end this annotation is used on.
	 */
	int value();
}
