package hu.elte.txtuml.layout.export.model1.associations;

import hu.elte.txtuml.api.model.Association;
import hu.elte.txtuml.layout.export.model1.A;
import hu.elte.txtuml.layout.export.model1.B;

public class T extends Association {
	public class e1 extends Many<A> {
	}

	public class e2 extends One<B> {
	}
}
