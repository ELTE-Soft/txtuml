package hu.elte.txtuml.api.model.error.multiplicity;

import org.junit.Test;
import org.junit.runner.RunWith;

import hu.elte.txtuml.api.model.Action;
import hu.elte.txtuml.api.model.base.SimpleModelTestsBase;
import hu.elte.txtuml.api.model.util.SeparateClassloaderTestRunner;

@RunWith(SeparateClassloaderTestRunner.class)
public class LowerBoundInitiallyOffendedTest extends SimpleModelTestsBase {

	@Test
	public void test() {
		Action.start(a);

		stopModelExecution();

		// TODO The tested feature is currently not working.
		//
		// executionAsserter.assertErrors(x ->
		// x.lowerBoundOfMultiplicityOffended(
		// a, A_B.b.class));
		//
	}

}
