package hu.elte.txtuml.examples.train;

import hu.elte.txtuml.api.model.*;
import hu.elte.txtuml.examples.train.TrainModel.*;

class TrainModel extends Model{

	class Gearbox extends ModelClass {
		class Init extends Initial {}
		
		class Neutral extends State {}
		class Forwards extends CompositeState {
			class FInit extends Initial {}
			class F1 extends State {}
			class F2 extends State {}

			@From(FInit.class) @To(F1.class)
			class FInit_F1 extends Transition {}
			@From(F1.class) @To(F2.class) @Trigger(Forward.class)
			class F1_F2 extends Transition {}
			@From(F2.class) @To(F1.class) @Trigger(Forward.class)
			class F2_F1 extends Transition {}
		}
		class Backwards extends CompositeState {
			class BInit extends Initial {}
			class B1 extends State {}
			class B2 extends State {}

			@From(BInit.class) @To(B1.class)
			class BInit_B1 extends Transition {}
			@From(B1.class) @To(B2.class) @Trigger(Backward.class)
			class B1_B2 extends Transition {}
			@From(B2.class) @To(B1.class) @Trigger(Backward.class)
			class B2_B1 extends Transition {}
		}

		@From(Init.class) @To(Neutral.class)
		class Init_Neutral extends Transition {}

		@From(Neutral.class) @To(Forwards.class) @Trigger(Forward.class)
		class Neutral_Forwards extends Transition {
			@Override public void effect() {
				startEngineOp();
			}
		}
		@From(Neutral.class) @To(Backwards.class) @Trigger(Backward.class)
		class Neutral_Backwards extends Transition {
			@Override public void effect() {
				startEngineOp();
			}
		}
		
		@From(Forwards.class) @To(Neutral.class) @Trigger(Backward.class)
		class Forwards_Neutral extends Transition {
			@Override public void effect() {
				Engine e = Gearbox.this.assoc(GE.e.class).selectAny();
				Action.send(e, new EngineOff());
				Lamp l = Gearbox.this.assoc(GL.l.class).selectAny();
				Action.send(l, new LightOff());
			}
		}
		@From(Backwards.class) @To(Neutral.class) @Trigger(Forward.class)
		class Backwards_Neutral extends Forwards_Neutral {
			@Override public void effect() {
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
	
	class Engine extends ModelClass {
		class Init extends Initial {}
		class Stopped extends State {}
		class Working extends State {}
		
		@From(Init.class) @To(Stopped.class)
		class Init_Stopped extends Transition {}
		@From(Stopped.class) @To(Working.class) @Trigger(EngineOn.class)
		class Stopped_Working extends Transition {}
		@From(Working.class) @To(Stopped.class) @Trigger(EngineOff.class)
		class Working_Stopped extends Transition {}
	}

	class Lamp extends ModelClass {
		class Init extends Initial {}
		class Dark extends State {}
		class Light extends State {}
		
		@From(Init.class) @To(Dark.class)
		class Init_Dark extends Transition {}
		@From(Dark.class) @To(Light.class) @Trigger(TrainModel.Light.class)
		class Dark_Light extends Transition {}
		@From(Light.class) @To(Dark.class) @Trigger(TrainModel.Light.class)
		class Light_Dark extends Transition {}
		@From(Light.class) @To(Dark.class) @Trigger(LightOff.class)
		class Light_Dark2 extends Transition {}
	}
	
	static class Forward extends Signal {}
	static class Backward extends Signal {}
	static class Light extends Signal {}
	
	private static class LightOff extends Signal {}
	private static class EngineOn extends Signal {}
	private static class EngineOff extends Signal {}
	
	class GE extends Association {
		class g extends One<Gearbox>{}
		class e extends One<Engine>{}
	}
	class GL extends Association {
		class g extends One<Gearbox>{}
		class l extends One<Lamp>{}		
	}
	class LE extends Association {
		class l extends One<Lamp>{}
		class e extends One<Engine>{}		
	}			
}

class TrainTester {
	TrainTester() {
		createInstances();
		test();
	}

	Gearbox g;
	Engine e;
	Lamp l;

	public void createInstances() {
		ModelExecutor.Settings.setExecutorLog(true);
		g = Action.create(Gearbox.class);
		e = Action.create(Engine.class);
		l = Action.create(Lamp.class);
		Action.link(GE.g.class, g, GE.e.class, e);
		Action.link(GL.g.class, g, GL.l.class, l);
		Action.link(LE.l.class, l, LE.e.class, e);
		Action.start(g);
		Action.start(e);
		Action.start(l);
	}

	public void test() {
		try {
			int time = 50;
			for(int i=0; i<3; i++) {
				Thread.sleep(time);
				Action.log("");
				Action.send(l, new Light());
			}

			Thread.sleep(2 * time);

			for(int i=0; i<3; i++) {
				Thread.sleep(3 * time);
				Action.log("");
				Action.send(g, new Forward());
	
				Thread.sleep(time);
				Action.log("");
				Action.send(g, new Backward());
			}

			ModelExecutor.shutdown();
		} catch (InterruptedException e) {
		}
	}
}

public class Train {
	public static void main(String[] args) throws Exception {
		new TrainTester();
	}
}
