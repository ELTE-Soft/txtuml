package hu.elte.txtuml.api.model.models;

import hu.elte.txtuml.api.model.From;
import hu.elte.txtuml.api.model.Model;
import hu.elte.txtuml.api.model.ModelClass;
import hu.elte.txtuml.api.model.Signal;
import hu.elte.txtuml.api.model.To;
import hu.elte.txtuml.api.model.Trigger;

public class ChoiceModel extends Model {
	
	public static class Sig extends Signal {
		public int value;

		public Sig() {
			this(0);
		}

		public Sig(int value) {
			this.value = value;
		}
	}

	public static class A extends ModelClass {

		public class Init extends Initial {}
		public class S1 extends State {}
		public class C extends Choice {}

		@From(Init.class) @To(S1.class)
		public class Initialize extends Transition {}
		@From(S1.class) @To(C.class) @Trigger(Sig.class)
		public class S1_C extends Transition {}
		
		@From(C.class) @To(S1.class) @Trigger(Sig.class)
		public class T1 extends Transition {
			
			@Override
			public boolean guard() {
				Sig s = getSignal(Sig.class);
				return s.value == 0;
			}
			
		}

		@From(C.class) @To(S1.class) @Trigger(Sig.class)
		public class T2 extends Transition {
			
			@Override
			public boolean guard() {
				Sig s = getSignal(Sig.class);
				return s.value == 1;
			}
			
		}

		@From(C.class) @To(S1.class) @Trigger(Sig.class)
		public class T3 extends Transition {
			
			@Override
			public boolean guard() {
				return Else();
			}
			
		}	
	}
}
