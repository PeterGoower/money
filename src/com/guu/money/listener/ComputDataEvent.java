package com.guu.money.listener;

public interface ComputDataEvent {

	public void onDataGot(String total, String recent, String average, int[] monTotal);
	
	public void onPrecentGot(String precent);
}
