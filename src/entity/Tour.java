package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Tour {
	private int id;
	private int name;
	private int cityid;

	public Tour(int id, int name, int cityid) {
		this.id = id;
		this.name = name;
		this.cityid = cityid;
	}

	public Tour(int name, int cityid) {
		this.name = name;
		this.cityid = cityid;
	}

	public Tour() {
	}

	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(name);
		Statement statement = connection.createStatement();
		ResultSet rs = statement
				.executeQuery("select name from city where id = " + cityid
						+ ";");
		while (rs.next()) {
			data.add(rs.getString("name"));
		}
		return data;
	}

	public void addElement(int name, int cityid, Connection connection)
			throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement
				.executeUpdate("insert into tour values(nextval('route_id_seq'), '"
						+ name + "', " + cityid + ");");
	}

	public void removeElement(int id, Connection connection)
			throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from tour where id = " + id + ";");
	}

	public void refreshElement(int id, int name, int cityid,
			Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("update tour set tournumber = " + name
				+ ", cityid = " + cityid + " where id = " + id + ";");
	}

	public DefaultTableModel TableModel(Connection connection)
			throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		ArrayList<Tour> tours = getTable(connection);
		for (int i = 0; i <= tours.size() - 1; i++) {
			data.add(tours.get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Название");
		columnNames.add("Город");
		return columnNames;
	}

	public ArrayList<Tour> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from tour;");
		ArrayList<Tour> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Tour((int) rs.getObject(1), (int) rs.getObject(2),
					(int) rs.getObject(3)));
		}
		return res;
	}

	public void add10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		Tour r = new Tour();
		int id = r.getTable(connection).size() + 1;
		for (int i = 0; i < 10000; i++, id++) {
			stmt.executeUpdate("insert into tour values(" + id + ", 0, 1);");
		}
	}

	public void delete10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		Tour r = new Tour();
		int id = r.getTable(connection).size();
		for (int i = 0; id > 0 && i < 10000; id--, i++) {
			stmt.executeUpdate("delete from tour where id = " + id + ";");
		}
	}
	
	public void createIndex(Connection connection) throws SQLException{
		clearIndex(connection);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("create index cityid_ind on tour (cityid);");
		stmt.close();
	}
	
	public void clearIndex(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("drop index cityid_ind;");
		stmt.close();
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return Integer.toString(name);
	}

	public int getCityid() {
		return cityid;
	}
}
