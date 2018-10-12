package org.mitsubishi.model;

import java.util.ArrayList;

import org.mitsubishi.dto.PMDTO;
import org.mitsubishi.dto.PMWalzenOrderDTO;
import org.mitsubishi.dto.PMWalzenRollerDTO;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class LoadData {

	DatabaseConnect connect;

	public LoadData(DatabaseConnect connect) {
		this.connect = connect;
	}
	
	public LoadData() {
	}

	public PMDTO getPmDto() {

		PMDTO pmDto = new PMDTO();
		ResultSet results = connect.getSession().execute(ConstantModelData.CASSANDRA_CQL_PMWALZENORDER);
		ArrayList<PMWalzenOrderDTO> orderDto = new ArrayList<PMWalzenOrderDTO>();
		for (Row row : results) {
			PMWalzenOrderDTO dto = new PMWalzenOrderDTO();
			dto.setOriginName(row.getString(0));
			dto.setRollerNr(row.getString(1));
			dto.setBuildDate(row.getString(2));
			dto.setDiameter(row.getString(3));
			dto.setRollTime(row.getString(4));
			dto.setEvaluation(row.getString(5));
			dto.setComment(row.getString(6));
			dto.setDebuildDate(row.getString(7));
			dto.setDebuildReason(row.getString(8));
			//dto.setOriginName(row.getString(10));
			//dto.setEvaluation(row.getString(12));
			orderDto.add(dto);
		}
		
		results = connect.getSession().execute("Select * from PM.FLExcelWalzenRoll");
		ArrayList<PMWalzenRollerDTO> rollerDto = new ArrayList<PMWalzenRollerDTO>();
		for (Row row : results) {
			PMWalzenRollerDTO dto = new PMWalzenRollerDTO();
			dto.setRollerNr(row.getString(1));
			dto.setRammy(row.getString(4));
			dto.setHardPJ(row.getString(5));
			dto.setReferenceLength(row.getString(6));
			dto.setBombage(row.getString(7));
			dto.setDate(row.getString(8));
			dto.setMaxO(row.getString(9));
			dto.setMinO(row.getString(10));
			dto.setOriginName(row.getString(0));
			dto.setReferenceName(row.getString(2));
			dto.setProducerName(row.getString(3));
//			dto.setOriginName(row.getString(12));
//			dto.setReferenceName(row.getString(14));
//			dto.setProducerName(row.getString(16));
			rollerDto.add(dto);
		}
		
		pmDto.setListPmWalzenOrder(orderDto);
//		pmDto.setListPmWalzenRoller(rollerDto);
		return pmDto;
	}
}