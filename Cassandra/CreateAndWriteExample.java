package org.mitsubishi.model;

import java.util.ArrayList;

import org.mitsubishi.dto.PMDTO;
import org.mitsubishi.dto.PMWalzenOrderDTO;
import org.mitsubishi.dto.PMWalzenRollerDTO;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class WriteData {

	DatabaseConnect connect;
	private static final String TABLE_NAME = "books";

	public WriteData(DatabaseConnect connect) {
		this.connect = connect;
	}

	public void createKeyspace(
	  String keyspaceName, String replicationStrategy, int replicationFactor) {
	  StringBuilder sb = 
		new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
		  .append(keyspaceName).append(" WITH replication = {")
		  .append("'class':'").append(replicationStrategy)
		  .append("','replication_factor':").append(replicationFactor)
		  .append("};");
			 
		String query = sb.toString();
		connect.getSession().execute(query);
	}	
	
	public void createTable() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
		  .append(TABLE_NAME).append("(")
		  .append("id uuid PRIMARY KEY, ")
		  .append("title text,")
		  .append("subject text);");
	 
		String query = sb.toString();
		connect.getSession().execute(query);
	}
	
	public void insertInTable(Book book) {
		StringBuilder sb = new StringBuilder("INSERT INTO ")
		  .append(TABLE_NAME_BY_TITLE).append("(id, title) ")
		  .append("VALUES (").append(book.getId())
		  .append(", '").append(book.getTitle()).append("');");
	 
		String query = sb.toString();
		connect.getSession().execute(query);
	}
	
	public void clearTable(){
		StringBuilder sb = new StringBuilder("TRUNCATE ")
		  .append(TABLE_NAME_BY_TITLE);
	 
		String query = sb.toString();
		connect.getSession().execute(query);
	}
	
	public void writeData() {

	}
}