package org.mitsubishi.createcylindercsv;

public class GetCSV {
	public static void getCsv() {
		GettingQualityData quality = new GettingQualityData();
		GettingOriginData origin = new GettingOriginData();
		GettingProducerData producer = new GettingProducerData();
		GettingCylinderData roller = new GettingCylinderData(producer.getListProducer(), quality.getListQuality(),
				origin.getOriginList());
		GettingProcedureData order = new GettingProcedureData(quality.getListQuality());
	}
}
