package hu.elte.txtuml.api.model.execution.statemachine;

import org.junit.Test;

import hu.elte.txtuml.api.model.Action;
import hu.elte.txtuml.api.model.execution.base.ChoiceModelTestsBase;
import hu.elte.txtuml.api.model.execution.models.choice.Sig;

public class ChoiceTest extends ChoiceModelTestsBase {

	@Test
	public void test() {
		executor.run(() -> {
			createAndStartA();
			Action.send(new Sig(0), a);
			Action.send(new Sig(1), a);
			Action.send(new Sig(2), a);
		});

		executionAsserter.assertEvents(x -> {
			x.executionStarted();
			transition(x, a, a.new Initialize());
			x.processingSignal(a, new Sig());
			transition(x, a, a.new S1_C());
			transition(x, a, a.new T1());
			x.processingSignal(a, new Sig());
			transition(x, a, a.new S1_C());
			transition(x, a, a.new T2());
			x.processingSignal(a, new Sig());
			transition(x, a, a.new S1_C());
			transition(x, a, a.new T3());
			x.executionTerminated();
		});

	}
}