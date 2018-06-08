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
			int tourid) {
		this.gosnumber = gosnumber;
		this.type = type;
		this.tourid = tourid;
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
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into transport values(nextval('transport_id_seq'),'"
					+ gosnumber
					+ "', '"
					+ type
					+ "', "
					+ tourfirmaid
					+ ", " + tourid + ");");
		} catch (Exception e) {
			throw new SQLException();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void removeElement(int id, Connection connection)
			throws SQLException {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate("delete from transport where id = " + id + ";");
		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void refreshElement(int id, String gosnumber, String type,
			int tourfirmaid, int tourid, Connection connection)
			throws SQLException {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate("update transport set " + "gosnumber = '"
					+ gosnumber + "',type = '" + type
					+ "', tourfirmaid = " + tourfirmaid
					+ ", tourid = " + tourid + " where id = " + id + ";");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
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
		columnNames.add("номер");
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

	public int getRouteid() {
		return tourid;
	}

	public int getTourfirmaid() {
		return tourfirmaid;
	}
}
