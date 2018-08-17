package com.yun.common;

public enum GroupOrderType {
	DANDUBUY(1),
	FAQIGroupBuy(2),
	CanyuGroupBuy(3);
	GroupOrderType(int state) {
		this.state = state;
	}
	private final int state;

	public int getState() {
		return this.state;
	}
			
}
