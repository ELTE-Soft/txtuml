package hu.elte.txtuml.api.model.util;

public class MutableBoolean {

	public boolean value;

	public MutableBoolean() {
		this(false);
	}

	public MutableBoolean(boolean value) {
		this.value = value;
	}

}
