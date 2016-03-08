package hu.elte.txtuml.examples.clock.model.associations;

import hu.elte.txtuml.api.model.Association;
import hu.elte.txtuml.api.stdlib.timers.Timer;
import hu.elte.txtuml.examples.clock.model.classes.Pendulum;

public class TimerOfPendulum extends Association {
	public class timer extends One<Timer> {}
	public class pendulum extends HiddenMaybeOne<Pendulum> {}
}
