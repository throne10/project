package com.throne.emm.eventbus.event;


public class MyAppGetEvent extends BaseEvent{
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
