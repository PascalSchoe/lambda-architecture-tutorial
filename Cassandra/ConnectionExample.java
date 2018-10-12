package org.mitsubishi.model;

import com.datastax.driver.core.*;
import com.datastax.driver.core.Cluster.Builder;

public class DatabaseConnect {

	private Cluster cluster;

	private Session session;

	public void connect(String node, Integer port) {
		Builder b = Cluster.builder().addContactPoint(node);
		if (port != null) {
			b.withPort(port);
		}
		cluster = b.build();

		session = cluster.connect();
	}

	public Session getSession() {
		return this.session;
	}

	public void close() {
		session.close();
		cluster.close();
	}
}