package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PlaceDeparture {
	private int id;
	private String name;
	private int tourid;

	public PlaceDeparture(int id, String name, int cityid) {
		this.id = id;
		this.name = name;
		this.tourid = cityid;
	}

	public PlaceDeparture(String name, int cityid) {
		this.name = name;
		this.tourid = cityid;
	}

	public PlaceDeparture() {
	}

	public Vector<Object> setData(Connection conection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(name);
		// Statement statement = conection.createStatement();
		// ResultSet rs = statement.executeQuery("select tournumber from tour
		// where id
		// = " + tourid + ";");
		PreparedStatement pst = conection
				.prepareStatement("select tournumber from tour where id = ?;");
		pst.setInt(1, tourid);
		pst.executeQuery();
		ResultSet rs = pst.getResultSet();
		while (rs.next()) {
			data.add(rs.getString("tournumber"));
		}
		return data;
	}

	public void addElement(String name, int tourid, Connection conection)
			throws SQLException {
		// Statement statement = conection.createStatement();
		// statement.executeUpdate("insert into placedeparture values( nextval('placedeparture_id_seq'),'"
		// + name + "', " + tourid + ");");
		PreparedStatement pst = conection
				.prepareStatement("insert into placedeparture values( nextval('placedeparture_id_seq'), ?, ?);");
		pst.setString(1, name);
		pst.setInt(2, tourid);
		pst.executeUpdate();
	}

	public void removeElement(int id, Connection conection) throws SQLException {
		// Statement statement = conection.createStatement();
		// statement.executeUpdate("delete from placedeparture where id = " + id +
		// ";");
		PreparedStatement pst = conection
				.prepareStatement("delete from placedeparture where id = ?;");
		pst.setInt(1, id);
		pst.executeUpdate();
	}

	public void refreshElement(int id, String name, int tourid,
			Connection conection) throws SQLException {
		Statement stmt = null;
		stmt = conection.createStatement();
		stmt.executeUpdate("update placedeparture set name =  '" + name
				+ "', tourid = " + tourid + " where id = " + id + ";");
	}

	public DefaultTableModel TableModel(Connection connection)
			throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		ArrayList<PlaceDeparture> points = getTable(connection);
		for (int i = 0; i <= points.size() - 1; i++) {
			data.add(points.get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Номер");
		columnNames.add("Тур");
		return columnNames;
	}

	public ArrayList<PlaceDeparture> getTable(Connection connection)
			throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from placedeparture;");
		ArrayList<PlaceDeparture> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new PlaceDeparture((int) rs.getObject(1), rs.getObject(2)
					.toString(), (int) rs.getObject(3)));
		}
		return res;
	}

	public void add10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		PlaceDeparture s = new PlaceDeparture();
		int id = s.getTable(connection).size() + 1;
		for (int i = 0; i < 10000; i++, id++) {
			stmt.executeUpdate("insert into placedeparture values(" + id
					+ ",'test',1);");
		}
	}

	public void delete10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		PlaceDeparture s = new PlaceDeparture();
		int id = s.getTable(connection).size();
		for (int i = 0; id > 0 && i < 10000; id--, i++) {
			stmt.executeUpdate("delete from placedeparture where id = " + id + ";");
		}
	}

	public void createIndex(Connection connection) throws SQLException {
		clearIndex(connection);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("create index tourid_ind_sp on placedeparture (tourid);");
		stmt.close();
	}

	public void clearIndex(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("drop index tourid_ind_sp);");
		stmt.close();
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public int getTourid() {
		return tourid;
	}
}
