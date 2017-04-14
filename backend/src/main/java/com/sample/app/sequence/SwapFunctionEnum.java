package com.sample.app.sequence;

public enum SwapFunctionEnum {
	ODD(0,1),EVEN(1,1);
	int start;
	int skip;
	private SwapFunctionEnum(int start, int skip) {
		this.start = start;
		this.skip = skip;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSkip() {
		return skip;
	}
	public void setSkip(int skip) {
		this.skip = skip;
	}
	
}
