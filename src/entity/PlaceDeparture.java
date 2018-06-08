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
		// ResultSet rs = statement.executeQuery("select tournumber from tour where id
		// = " + tourid + ";");
		PreparedStatement pst = conection.prepareStatement("select tournumber from tour where id = ?;");
		pst.setInt(1, tourid);
		pst.executeQuery();
		ResultSet rs = pst.getResultSet();
		while (rs.next()) {
			data.add(rs.getString("tournumber"));
		}
		return data;
	}

	public void addElement(String name, int tourid, Connection conection) throws SQLException {
		//Statement statement = conection.createStatement();
		//statement.executeUpdate("insert into placeDeparture values( nextval('placedeparture_id_seq'),'" + name + "', " + tourid + ");");
		PreparedStatement pst = conection.prepareStatement("insert into placedeparture values( nextval('placedeparture_id_seq'), ?, ?);");
		pst.setString(1, name);
		pst.setInt(2, tourid);
		pst.executeUpdate();
	}

	public void removeElement(int id, Connection conection) throws SQLException {
		//Statement statement = conection.createStatement();
		//statement.executeUpdate("delete from placedeparture where id = " + id + ";");
		PreparedStatement pst = conection.prepareStatement("delete from placedeparture where id = ?;");
		pst.setInt(1, id);
		pst.executeUpdate();
	}

	public void refreshElement(int id, String name, int tourid, Connection conection) throws SQLException {
		Statement stmt = null;
		stmt = conection.createStatement();
		stmt.executeUpdate(
				"update placedeparture set name =  '" + name + "', tourid = " + tourid + " where id = " + id + ";");
	}

	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i <= getTable(connection).size() - 1; i++) {
			data.add(getTable(connection).get(i).setData(connection));
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

	public ArrayList<PlaceDeparture> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from placedeparture;");
		ArrayList<PlaceDeparture> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new PlaceDeparture((int) rs.getObject(1), rs.getObject(2).toString(), (int) rs.getObject(3)));
		}
		return res;
	}


/*package entity;

import java.sql.Connection;
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
	private ArrayList<PlaceDeparture> placedepartures;

	public PlaceDeparture(int id, String name, int cityid) {
	this.id = id;
		this.name = name;
		this.tourid = cityid;
		this.placedepartures = new ArrayList<PlaceDeparture>();
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
		Statement statement = conection.createStatement();
		ResultSet rs = statement
				.executeQuery("select tournumber from tour where id = "
						+ tourid + ";");
		while (rs.next()) {
			data.add(rs.getString("tournumber"));
		}
		return data;
	}

	public void addElement(String name, int tourid, Connection conection)
			throws SQLException {
		Statement statement = null;
		try {
			System.out.println(tourid);
			statement = conection.createStatement();
			statement
					.executeUpdate("insert into placedeparture values( nextval('placedeparture_id_seq'),'"
							+ name + "', " + tourid + ");");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public void removeElement(int id, Connection conection) throws SQLException {
		Statement statement = null;
		try {
			statement = conection.createStatement();
			statement.executeUpdate("delete from placedeparture where id = " + id
					+ ";");
		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public void refreshElement(int id, String name, int tourid,
			Connection conection) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conection.createStatement();
			stmt.executeUpdate("update placedeparture set name =  '" + name
					+ "', tourid = " + tourid + " where id = " + id + ";");
		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public DefaultTableModel TableModel(Connection connection)
			throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i <= getTable(connection).size() - 1; i++) {
			data.add(getTable(connection).get(i).setData(connection));
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
*/
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getTourid() {
		return tourid;
	}

	
}
