package com.guu.money.listener;

public interface TipEvent {

	public void onHintDismiss(int eventTag);
	public void onChoose(int which, int eventTag);
	public void onConfirm(int eventTag);
}
