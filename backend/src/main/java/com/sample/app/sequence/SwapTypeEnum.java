package com.sample.app.sequence;

public enum SwapTypeEnum {
	BY_POSITION("position"),BY_VALUE("value");
	String type;
	private SwapTypeEnum(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static SwapTypeEnum getByType(String type) {
		for (SwapTypeEnum swapTypeEnum : SwapTypeEnum.values()) {
			if (swapTypeEnum.getType()
					.equalsIgnoreCase(type)) {
				return swapTypeEnum;
			}
		}
		return null;
	}
}