package org.mitsubishi.createcylindercsv;

public class GetCSV {
	public static void getCsv() {
		GettingQualityData quality = new GettingQualityData();
		GettingOriginData origin = new GettingOriginData(quality.getListQuality());
		GettingProducerData producer = new GettingProducerData();
		GettingRollerData roller = new GettingRollerData(producer.getListProducer(), quality.getListQuality(),
				origin.getOriginList());
		GettingOrderData order = new GettingOrderData(quality.getListQuality());
	}
}
