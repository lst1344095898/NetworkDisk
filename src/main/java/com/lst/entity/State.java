package com.lst.entity;

public class State<T> {
	private boolean state_id;
	private T data;
	private String error;
	
	public boolean getState_id() {
		return state_id;
	}
	public void setState_id(boolean state_id) {
		this.state_id = state_id;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	@Override
	public String toString() {
		return "State [state_id=" + state_id + ", dataT=" + data + ", error=" + error + "]";
	}
	
}	
