package org.mitsubishi.createcylindercsv;

public class OriginDTO {
	
	private String originId = "";
	private String originName = "";
	private String activeRollId = "";
	
	public OriginDTO () {
		
	}
	
	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getActiveRollId() {
		return activeRollId;
	}

	public void setActiveRollId(String activeRollId) {
		this.activeRollId = activeRollId;
	}
	
}
