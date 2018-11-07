package org.mitsubishi.createcylindercsv;

public class ProducerDTO {
	private String id = "";
	private String producer = "";
	
	public ProducerDTO(String id, String producer) {
		this.id = id;
		this.producer = producer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}
}
