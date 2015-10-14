package com.throne.emm.eventbus.event;


public class MyAppAddEvent extends BaseEvent{
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
