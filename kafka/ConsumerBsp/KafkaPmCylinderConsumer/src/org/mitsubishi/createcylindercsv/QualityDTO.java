package org.mitsubishi.createcylindercsv;

public class QualityDTO {
	private String id;
	private String quality;
	
	public QualityDTO(String id, String quality){
		this.id = id;
		this.quality = quality;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	
}
