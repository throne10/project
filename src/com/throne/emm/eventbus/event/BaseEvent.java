package com.throne.emm.eventbus.event;

public class BaseEvent {
	public static final int Success=200;
	public static final int Fail=400;
	private Object Event;
	private int Result; ;

	public Object getEvent() {
		return Event;
	}

	public void setEvent(Object event) {
		Event = event;
	}

	public int getResult() {
		return Result;
	}

	public void setResult(int result) {
		Result = result;
	}
}
