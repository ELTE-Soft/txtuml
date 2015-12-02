package hu.elte.txtuml.api.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A runtime annotation to define the trigger of a transition.
 * 
 * <p>
 * <b>Represents:</b> trigger of a transition
 * <p>
 * <b>Usage:</b>
 * <p>
 * 
 * Use on a subclass of {@link hu.elte.txtuml.api.model.StateMachine.Transition
 * Transition} and set the value of the {@link #value() value} element to
 * reference the representing class of the desired signal (as a signal event).
 * The current implementation does not support any other kind of events on
 * triggers.
 * <p>
 * Only one trigger might be applied to a transition.
 * <p>
 * Transitions are only allowed to have no triggers if they are from an initial
 * pseudostate or a choice pseudostate. Therefore, transitions not included in
 * these two cases must have exactly one trigger defined.
 * 
 * <p>
 * <b>Example:</b>
 * 
 * <pre>
 * <code>
 * {@literal @From(SourceState.class) @To(TargetState.class) @Trigger(SampleSignal.class)}
 * class SampleTransition extends Transition {}
 * </code>
 * </pre>
 * 
 * See the documentation of {@link StateMachine} for detailed examples.
 * <p>
 * See the documentation of {@link Model} for an overview on modeling in
 * JtxtUML.
 *
 * @author Gabor Ferenc Kovacs
 * @see StateMachine.Transition
 * @see To
 * @see From
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Trigger {

	/**
	 * The triggering signal of the transition this annotation is used on.
	 */
	Class<? extends Signal> value();

}
