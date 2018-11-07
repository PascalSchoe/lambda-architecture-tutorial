package org.mitsubishi.createcylindercsv;

public class RollerDTO {
	
	private String rollerNr = "";
	private String originId = "";
	private String referenceId = "";
	private String producerId = "";
	private String hardPJ = "";
	private String referenceLength = "";
	private String date = "";
	private String maxO = "";
	private String minO = "";
	private String rammy = "";
	private String bombage = "";
	
	public RollerDTO () {
		
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producer) {
		this.producerId = producer;
	}

	public String getHardPJ() {
		return hardPJ;
	}

	public void setHardPJ(String hardPJ) {
		this.hardPJ = hardPJ;
	}

	public String getReferenceLength() {
		return referenceLength;
	}

	public void setReferenceLength(String referenceLength) {
		this.referenceLength = referenceLength;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMaxO() {
		return maxO;
	}

	public void setMaxO(String maxO) {
		this.maxO = maxO;
	}

	public String getMinO() {
		return minO;
	}

	public void setMinO(String minO) {
		this.minO = minO;
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

	public String getBombage() {
		return bombage;
	}

	public void setBombage(String bombage) {
		this.bombage = bombage;
	}

	public String getRammy() {
		return rammy;
	}

	public void setRammy(String rammy) {
		this.rammy = rammy;
	}
}
