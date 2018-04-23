package hu.elte.txtuml.api.model.execution.seqdiag.sequences;

import hu.elte.txtuml.api.model.execution.testmodel.seqdiag.TestSig;
import hu.elte.txtuml.api.model.impl.SequenceDiagramRelated;
import hu.elte.txtuml.api.model.seqdiag.ExecMode;
import hu.elte.txtuml.api.model.seqdiag.ExecutionMode;
import hu.elte.txtuml.api.model.seqdiag.Sequence;

@SequenceDiagramRelated
public class SequenceMessageSendingLenientValid extends SequenceBase {

	@Override
	@ExecutionMode(ExecMode.LENIENT)
	public void run() {
		Sequence.fromActor(new TestSig(), a);
		Sequence.send(b, new TestSig(), c);
		Sequence.send(b, new TestSig(), a);
	}

}