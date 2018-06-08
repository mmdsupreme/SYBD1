package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class City {
	private int id;
	private String name;
	private ArrayList<Tour> tours;

	public City(int id, String name) {
		this.id = id;
		this.name = name;
		this.tours = new ArrayList<Tour>();
	}

	public City(String name) {
		this.name = name;
		this.tours = new ArrayList<Tour>();
	}

	public City() {
	}

	public Vector<Object> setData() throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(name);
		return data;
	}

	public void addElement(String name, Connection connection) throws SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("insert into city values(nextval('city_id_seq'),'" + name + "');");
		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("delete from city where id = " + id + ";");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			throw new SQLException();
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public void refreshElement(int id, String name, Connection connection) throws SQLException {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate("update city set name = '" + name + "' where id = " + id + ";");
		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i <= getTable(connection).size() - 1; i++) {
			data.add(getTable(connection).get(i).setData());
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Название");
		return columnNames;
	}

	public ArrayList<City> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from city;");
		ArrayList<City> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new City((int) rs.getObject(1), rs.getObject(2).toString()));
		}
		return res;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}
}
