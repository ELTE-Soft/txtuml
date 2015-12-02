package hu.elte.txtuml.api.model.statemachine;

import org.junit.Test;
import org.junit.runner.RunWith;

import hu.elte.txtuml.api.model.Action;
import hu.elte.txtuml.api.model.base.TransitionsModelTestsBase;
import hu.elte.txtuml.api.model.models.transitions.Sig1;
import hu.elte.txtuml.api.model.models.transitions.Sig2;
import hu.elte.txtuml.api.model.util.SeparateClassloaderTestRunner;

@RunWith(SeparateClassloaderTestRunner.class)
public class TriggerTest extends TransitionsModelTestsBase {

	@Test
	public void test() {
		Action.send(a, new Sig1());
		Action.send(a, new Sig2());
		Action.send(a, new Sig1());
		Action.send(a, new Sig1());
		Action.send(a, new Sig1());
		Action.send(a, new Sig2());

		stopModelExecution();

		executionAsserter.assertEvents(x -> {
			transition(x, a, a.new Initialize());
			x.processingSignal(a, new Sig1());
			transition(x, a, a.new T1());
			x.processingSignal(a, new Sig2());
			transition(x, a, a.new T2());
			x.processingSignal(a, new Sig1());
			transition(x, a, a.new T1());
			x.processingSignal(a, new Sig1());
			transition(x, a, a.new T1());
			x.processingSignal(a, new Sig1());
			transition(x, a, a.new T1());
			x.processingSignal(a, new Sig2());
			transition(x, a, a.new T2());
			x.executionTerminated();
		});
	}
}
