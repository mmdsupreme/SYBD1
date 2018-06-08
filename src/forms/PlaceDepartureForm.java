package forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entity.Tour;
import entity.PlaceDeparture;

public class PlaceDepartureForm extends JFrame {
	private Connection connection;
	private int idSelected;
	private JPanel contentPane;
	private JTextField textField;
	private JComboBox<String> comboBox;
	private ArrayList<Tour> tours;

	/**
	 * @wbp.parser.constructor
	 */
	public PlaceDepartureForm(Connection con) throws SQLException {
		Initiate();
		connection = con;
		this.idSelected = -1;
		Tour ph = new Tour();
		tours = new ArrayList<>(ph.getTable(connection));
		comboBox.removeAllItems();
		for (int i = 0; i < tours.size(); i++) {
			comboBox.addItem("" + tours.get(i).getName());
		}
		System.out.println("zahodit");
	}

	public PlaceDepartureForm(int id, Connection con) throws SQLException {
		Initiate();
		int rid = 0;
		connection = con;
		this.idSelected = id;
		PlaceDeparture point = new PlaceDeparture();
		ArrayList<PlaceDeparture> points = new ArrayList<>(point.getTable(connection));
		point = null;
		for (int i = 0; i < points.size(); i++) {
			if (id - 1 == points.get(i).getId() - 1) {
				point = points.get(i);
				break;
			}
		}
		Tour al = new Tour();
		tours = new ArrayList<>(al.getTable(connection));
		comboBox.removeAllItems();
		for (int i = 0; i < tours.size(); i++) {
			comboBox.addItem("" + tours.get(i).getName());
			if (point.getTourid() == tours.get(i).getId()) {
				rid = i;
			}
		}
		comboBox.setSelectedItem(tours.get(rid).getName());
		textField.setText(point.getName());
	}

	private void Initiate() {
		setTitle("Место отправления");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 324, 156);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Место");
		label.setBounds(10, 14, 100, 14);
		contentPane.add(label);

		textField = new JTextField();
		textField.setBounds(120, 11, 178, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("ADD");
				PlaceDeparture point = null;
				try {
					if (textField.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "tut");
					}
					point = new PlaceDeparture(textField.getText(), tours.get(comboBox.getSelectedIndex()).getId());
					if (idSelected < 0) {
						System.out.println("add");
						point.addElement(textField.getText(), tours.get(comboBox.getSelectedIndex()).getId(),
								connection);
					} else {
						point.refreshElement(idSelected, textField.getText(),
								tours.get(comboBox.getSelectedIndex()).getId(), connection);
					}
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				setVisible(false);
			}
		});
		button.setBounds(10, 87, 144, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		button_1.setBounds(164, 87, 134, 23);
		contentPane.add(button_1);

		JLabel label_1 = new JLabel("\u041C\u0430\u0440\u0448\u0440\u0443\u0442");
		label_1.setBounds(10, 42, 100, 14);
		contentPane.add(label_1);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(120, 39, 178, 20);
		contentPane.add(comboBox);
	}
}
