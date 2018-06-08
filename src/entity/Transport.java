package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Transport {
	public int id;
	public String gosnumber;
	public String type;
	public int tourid;
	public int tourfirmaid;

	public Transport(int id, String gosnumber, String type,
			int tourfirmaid, int tourid) {
		this.id = id;
		this.gosnumber = gosnumber;
		this.type = type;
		this.tourid = tourid;
		this.tourfirmaid = tourfirmaid;
	}

	public Transport(String gosnumber, String type, int tourfirmaid,
			int routeid) {
		this.gosnumber = gosnumber;
		this.type = type;
		this.tourid = routeid;
		this.tourfirmaid = tourfirmaid;
	}

	public Transport() {
	}

	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(gosnumber);
		data.add(type);
		Statement statement = connection.createStatement();
		ResultSet rs = statement
				.executeQuery("select name from tourfirma where id = "
						+ tourfirmaid + ";");
		while (rs.next()) {
			data.add(rs.getString("name"));
		}
		rs = statement.executeQuery("select tournumber from tour where id = "
				+ tourid + ";");
		while (rs.next()) {
			data.add(rs.getString("tournumber"));
		}
		return data;
	}

	public void addElement(String gosnumber, String type, int tourfirmaid,
			int tourid, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into transport values(nextval('transport_id_seq'),'"
				+ gosnumber
				+ "', '"
				+ type
				+ "', "
				+ tourfirmaid
				+ ", "
				+ tourid + ");");
	}

	public void removeElement(int id, Connection connection)
			throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from transport where id = " + id + ";");
	}

	public void refreshElement(int id, String gosnumber, String type,
			int tourfirmaid, int tourid, Connection connection)
			throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("update transport set " + "gosnumber = '"
				+ gosnumber + "',type = '" + type + "', tourfirmaid = "
				+ tourfirmaid + ", tourid = " + tourid + " where id = "
				+ id + ";");
	}

	public DefaultTableModel TableModel(Connection connection)
			throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		ArrayList<Transport> transports = getTable(connection);
		for (int i = 0; i <= transports.size() - 1; i++) {
			data.add(transports.get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Гос номер");
		columnNames.add("Тип");
		columnNames.add("Турфирма");
		columnNames.add("Тур");
		return columnNames;
	}

	public ArrayList<Transport> getTable(Connection connection)
			throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from transport");
		ArrayList<Transport> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Transport((int) rs.getObject(1), rs.getObject(2)
					.toString(), rs.getObject(3).toString(), (int) rs
					.getObject(4), (int) rs.getObject(5)));
		}
		return res;
	}

	public void add10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		Transport t = new Transport();
		int id = t.getTable(connection).size() + 1;
		for (int i = 0; i < 10000; i++, id++) {
			stmt.executeUpdate("insert into transport values(" + id
					+ ",'test', 'test', 1, 1);");
		}
	}

	public void delete10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		Transport t = new Transport();
		int id = t.getTable(connection).size();
		for (int i = 0; id > 0 && i < 10000; id--, i++) {
			stmt.executeUpdate("delete from transport where id = " + id + ";");
		}
	}

	public void createIndex(Connection connection) throws SQLException {
		clearIndex(connection);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("create index tourid_ind_t on transport (tourid);");
		stmt.executeUpdate("create index tourfirmaid_ind on transport (tourfirmaid);");
		stmt.close();
	}

	public void clearIndex(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("drop index tourid_ind_t;");
		stmt.executeUpdate("drop index tourfirmaid_ind);");
		stmt.close();
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return gosnumber;
	}

	public String getGosnumber() {
		return gosnumber;
	}

	public String getType() {
		return type;
	}

	public int getTourid() {
		return tourid;
	}

	public int getTourfirmaid() {
		return tourfirmaid;
	}
}
