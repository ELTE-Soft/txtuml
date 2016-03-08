package hu.elte.txtuml.examples.clock.model.classes;

import hu.elte.txtuml.api.model.Action;
import hu.elte.txtuml.api.model.From;
import hu.elte.txtuml.api.model.ModelClass;
import hu.elte.txtuml.api.model.OutPort;
import hu.elte.txtuml.api.model.To;
import hu.elte.txtuml.api.model.Trigger;
import hu.elte.txtuml.api.stdlib.timers.Timer;
import hu.elte.txtuml.examples.clock.model.associations.TimerOfPendulum;
import hu.elte.txtuml.examples.clock.model.interfaces.TickIfc;
import hu.elte.txtuml.examples.clock.model.signals.Tick;

public class Pendulum extends ModelClass {
	
	private int unit = 1000;
	
	public class OutTickPort extends OutPort<TickIfc> {}
	
	class Init extends Initial {}
	class Working extends State {
		public void entry() {
			Action.send(new Tick(), port(OutTickPort.class).provided::reception);
		}
	}
	
	@From(Init.class) @To(Working.class)
	class Initialize extends Transition {
		public void effect() {
			Timer timer = Timer.start(Pendulum.this, new Tick(), unit);
			Action.link(TimerOfPendulum.timer.class, timer, TimerOfPendulum.pendulum.class, Pendulum.this);
		}
	}
	
	@From(Working.class) @To(Working.class) @Trigger(Tick.class)
	class DoTick extends Transition {
		public void effect() {
			assoc(TimerOfPendulum.timer.class).selectAny().reset(unit);
		}
	}
}
