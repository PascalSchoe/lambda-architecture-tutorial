package org.mitsubishi.createcylindercsv;

public class OrderDTO {
	
	private String originId = "";
	private String rollerNr = "";
	private String buildDate = "";
	private String diameter = "";
	private String rollTime = "";
	private String evaluation = "";
	private String debuildDate = "";
	private String debuildReason = "";
	private String comment = "";
	
	public OrderDTO (){
		
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getRollerNr() {
		return rollerNr;
	}

	public void setRollerNr(String rollerNr) {
		this.rollerNr = rollerNr;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getRollTime() {
		return rollTime;
	}

	public void setRollTime(String rollTime) {
		this.rollTime = rollTime;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getDebuildDate() {
		return debuildDate;
	}

	public void setDebuildDate(String debuildDate) {
		this.debuildDate = debuildDate;
	}

	public String getDebuildReason() {
		return debuildReason;
	}

	public void setDebuildReason(String debuildReason) {
		this.debuildReason = debuildReason;
	}	
	
}
