package com.throne.emm.eventbus.event;


public class CheckVersionEvent extends BaseEvent{
	public final static int UPDATE=1;
	public static final int NO_UPDATE = 0;
	private int type;

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
