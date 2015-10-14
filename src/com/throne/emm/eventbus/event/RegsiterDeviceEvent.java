package com.throne.emm.eventbus.event;


public class RegsiterDeviceEvent extends BaseEvent{
	public final static int NO_REGISTER=1;
	private int type;

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
