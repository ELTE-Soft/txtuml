package hu.elte.txtuml.examples.train.model;

import hu.elte.txtuml.api.model.Action;
import hu.elte.txtuml.api.model.From;
import hu.elte.txtuml.api.model.ModelClass;
import hu.elte.txtuml.api.model.To;
import hu.elte.txtuml.api.model.Trigger;
import hu.elte.txtuml.examples.train.model.associations.GE;
import hu.elte.txtuml.examples.train.model.associations.GL;
import hu.elte.txtuml.examples.train.model.signals.Backward;
import hu.elte.txtuml.examples.train.model.signals.EngineOff;
import hu.elte.txtuml.examples.train.model.signals.EngineOn;
import hu.elte.txtuml.examples.train.model.signals.Forward;
import hu.elte.txtuml.examples.train.model.signals.LightOff;

public class Gearbox extends ModelClass {
	class Init extends Initial {
	}

	class Neutral extends State {
	}

	class Forwards extends CompositeState {
		class FInit extends Initial {
		}

		class F1 extends State {
		}

		class F2 extends State {
		}

		@From(FInit.class)
		@To(F1.class)
		class FInit_F1 extends Transition {
		}

		@From(F1.class)
		@To(F2.class)
		@Trigger(Forward.class)
		class F1_F2 extends Transition {
		}

		@From(F2.class)
		@To(F1.class)
		@Trigger(Forward.class)
		class F2_F1 extends Transition {
		}
	}

	class Backwards extends CompositeState {
		class BInit extends Initial {
		}

		class B1 extends State {
		}

		class B2 extends State {
		}

		@From(BInit.class)
		@To(B1.class)
		class BInit_B1 extends Transition {
		}

		@From(B1.class)
		@To(B2.class)
		@Trigger(Backward.class)
		class B1_B2 extends Transition {
		}

		@From(B2.class)
		@To(B1.class)
		@Trigger(Backward.class)
		class B2_B1 extends Transition {
		}
	}

	@From(Init.class)
	@To(Neutral.class)
	class Init_Neutral extends Transition {
	}

	@From(Neutral.class)
	@To(Forwards.class)
	@Trigger(Forward.class)
	class Neutral_Forwards extends Transition {
		@Override
		public void effect() {
			startEngineOp();
		}
	}

	@From(Neutral.class)
	@To(Backwards.class)
	@Trigger(Backward.class)
	class Neutral_Backwards extends Transition {
		@Override
		public void effect() {
			startEngineOp();
		}
	}

	@From(Forwards.class)
	@To(Neutral.class)
	@Trigger(Backward.class)
	class Forwards_Neutral extends Transition {
		@Override
		public void effect() {
			Engine e = Gearbox.this.assoc(GE.e.class).selectAny();
			Action.send(e, new EngineOff());
			Lamp l = Gearbox.this.assoc(GL.l.class).selectAny();
			Action.send(l, new LightOff());
		}
	}

	@From(Backwards.class)
	@To(Neutral.class)
	@Trigger(Forward.class)
	class Backwards_Neutral extends Forwards_Neutral {
		@Override
		public void effect() {
			Engine e = Gearbox.this.assoc(GE.e.class).selectAny();
			Action.send(e, new EngineOff());
			Lamp l = Gearbox.this.assoc(GL.l.class).selectAny();
			Action.send(l, new LightOff());
		}
	}

	void startEngineOp() {
		Engine e = Gearbox.this.assoc(GE.e.class).selectAny();
		Action.send(e, new EngineOn());
	}
}