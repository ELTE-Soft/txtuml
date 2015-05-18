package hu.elte.txtuml.api.tests.shutdown;

import hu.elte.txtuml.api.Action;
import hu.elte.txtuml.api.ModelExecutor;
import hu.elte.txtuml.api.backend.messages.LogMessages;
import hu.elte.txtuml.api.tests.base.SimpleModelTestsBase;
import hu.elte.txtuml.api.tests.models.SimpleModel.Sig;
import hu.elte.txtuml.api.tests.util.MutableBoolean;
import hu.elte.txtuml.api.tests.util.SeparateClassloaderTestRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SeparateClassloaderTestRunner.class)
public class ShutdownWithTenMessagesTest extends SimpleModelTestsBase {

	@Test
	public void test() {
		MutableBoolean actionPerformed = new MutableBoolean();
		ModelExecutor.addToShutdownQueue(() -> actionPerformed.value = true);

		Action.start(a);
		for (int i = 0; i < 10; ++i) {
			Action.send(a, new Sig());
		}

		Assert.assertEquals(false, actionPerformed.value);
		stopModelExecution(() -> {
			ModelExecutor.shutdown();
			Assert.assertEquals(false, actionPerformed.value);			
		});
		Assert.assertEquals(true, actionPerformed.value);

		Assert.assertArrayEquals(
				new String[] {
						LogMessages.getUsingTransitionMessage(a,
								a.new Initialize()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S1_S2()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S2_S1()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S1_S2()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S2_S1()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S1_S2()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S2_S1()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S1_S2()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S2_S1()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S1_S2()),
						LogMessages.getProcessingSignalMessage(a, new Sig()),
						LogMessages.getUsingTransitionMessage(a, a.new S2_S1()),
						LogMessages.getModelExecutionShutdownMessage() },
				executorStream.getOutputAsArray());
	}

}
