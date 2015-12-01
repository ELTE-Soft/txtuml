package hu.elte.txtuml.examples.garage.control.model;

import hu.elte.txtuml.api.model.*;
import hu.elte.txtuml.api.model.external.ExternalClass;
import hu.elte.txtuml.examples.garage.interfaces.IControl;
import hu.elte.txtuml.examples.garage.interfaces.IControlled;

// This class is the glue code between the UI and the control model
public class Glue implements ExternalClass, IControl, IControlled {
	// Model instantiation
	GarageModel gmodel = new GarageModel();
	GarageModel.Door door = gmodel.new Door();
	GarageModel.Motor motor = gmodel.new Motor();
	GarageModel.Alarm alarm = gmodel.new Alarm();
	GarageModel.Keyboard keyboard = gmodel.new Keyboard();

	// Linkage to the UI
	IControlled controlled;

	public void setControlled(IControlled ctd) {
		controlled = ctd;
	}

	// Singleton pattern
	static Glue instance = null;

	private Glue() {
		// Initialize links and start object instances
		Action.link(GarageModel.MotorMovesDoor.movedDoor.class, door,
				GarageModel.MotorMovesDoor.movingMotor.class, motor);
		Action.link(GarageModel.DoorSwitchesOnAlarm.SwitchingDoor.class, door,
				GarageModel.DoorSwitchesOnAlarm.SwitchedAlarm.class, alarm);
		Action.link(GarageModel.KeyboardProvidesCode.Provider.class, keyboard,
				GarageModel.KeyboardProvidesCode.Receiver.class, alarm);
		Action.start(door);
		Action.start(motor);
		Action.start(alarm);
		Action.start(keyboard);
	}

	public static synchronized Glue getInstance() {
		if (instance == null) {
			instance = new Glue();
		}
		return instance;
	}

	@Override
	public void progress(int percent) {
		controlled.progress(percent);
	}

	@Override
	public void stopDoor() {
		controlled.stopDoor();
	}

	@Override
	public void startDoorUp() {
		controlled.startDoorUp();
	}

	@Override
	public void startDoorDown() {
		controlled.startDoorDown();
	}

	@Override
	public void startSiren() {
		controlled.startSiren();
	}

	@Override
	public void stopSiren() {
		controlled.stopSiren();
	}

	@Override
	public void codeExpected() {
		controlled.codeExpected();
	}

	@Override
	public void oldCodeExpected() {
		controlled.oldCodeExpected();
	}

	@Override
	public void newCodeExpected() {
		controlled.newCodeExpected();
	}

	@Override
	public void alarmOff() {
		controlled.alarmOff();
	}

	@Override
	public void alarmOn() {
		controlled.alarmOn();
	}

	// IControl implementation
	@Override
	public void remoteControlButtonPressed() {
		Action.send(door, gmodel.new RemoteControlButtonPressed());
	}

	@Override
	public void motionSensorActivated() {
		Action.send(door, gmodel.new MotionSensorActivated());
	}

	@Override
	public void alarmSensorActivated() {
		Action.send(alarm, gmodel.new AlarmSensorActivated());
	}

	@Override
	public void doorReachedTop() {
		Action.send(motor, gmodel.new DoorReachedTop());
	}

	@Override
	public void doorReachedBottom() {
		Action.send(motor, gmodel.new DoorReachedBottom());
	}

	@Override
	public void keyPress(int nr) {
		Action.send(keyboard, gmodel.new KeyPress(nr));
	}

	@Override
	public void starPressed() {
		Action.send(alarm, gmodel.new StarPressed());
	}

	@Override
	public void hashPressed() {
		Action.send(alarm, gmodel.new HashPressed());
	}

}
