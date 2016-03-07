package hu.elte.txtuml.api.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks that the annotated element (type, field, method or constructor) is not
 * part of the model therefore it is not validated or exported and is invisible
 * from other (non-external) parts of the model. It may contain any valid Java
 * code, its correctness is the user's responsibility.
 * <p>
 * See the documentation of {@link Model} for an overview on modeling in
 * JtxtUML.
 * 
 * @see ExternalBody
 * @see ExternalSuperInterface
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR })
@Documented
@Inherited
public @interface External {

}
