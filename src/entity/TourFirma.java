package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TourFirma {
	private int id;
	private String name;
	private ArrayList<Transport> transports;

	public TourFirma(int id, String name) {
		this.id = id;
		this.name = name;
		this.transports = new ArrayList<Transport>();
	}

	public TourFirma(String name) {
		this.name = name;
		this.transports = new ArrayList<Transport>();
	}

	public TourFirma() {
	}

	public Vector<Object> setData(Connection conection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(name);
		return data;
	}

	public void addElement(String name, Connection connection)
			throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement
				.executeUpdate("insert into tourfirma values( nextval('tourfirma_id_seq'), '"
						+ name + "');");
	}

	public void removeElement(int id, Connection connection)
			throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from tourfirma where id = " + id
				+ ";");
	}

	public void refreshElement(int id, String name, Connection connection)
			throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("update tourfirma set name = '" + name
				+ "' where id = " + id + ";");
	}

	public DefaultTableModel TableModel(Connection connection)
			throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		ArrayList<TourFirma> autos = getTable(connection);
 		for (int i = 0; i <= autos.size() - 1; i++) {
			data.add(autos.get(i).setData(connection));
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

	public ArrayList<TourFirma> getTable(Connection connection)
			throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from tourfirma;");
		ArrayList<TourFirma> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new TourFirma((int) rs.getObject(1), rs.getObject(2)
					.toString()));
		}
		return res;
	}

	public void add10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		TourFirma a = new TourFirma();
		int id = a.getTable(connection).size() + 1;
		for (int i = 0; i < 10000; i++, id++) {
			stmt.executeUpdate("insert into tourfirma values(" + id
					+ ",'test');");
		}
	}

	public void delete10k(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		TourFirma a = new TourFirma();
		int id = a.getTable(connection).size();
		for (int i = 0; id > 0 && i < 10000; id--, i++) {
			stmt.executeUpdate("delete from tourfirma where id = " + id
					+ ";");
		}
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}
}
