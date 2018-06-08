package forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entity.Tour;
import entity.Transport;
import entity.TourFirma;

public class TransportForm extends JFrame {
	private Connection connection = null;
	public int idSelected;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox_1;
	private ArrayList<Tour> tours;
	private ArrayList<TourFirma> tourfirms;

	/**
	 * @wbp.parser.constructor
	 */
	public TransportForm(Connection connection) throws SQLException {
		Initiate();
		this.connection = connection;
		this.idSelected = -1;
		TourFirma au = new TourFirma();
		tourfirms = new ArrayList<>(au.getTable(connection));
		comboBox.removeAllItems();
		for (int i = 0; i < tourfirms.size(); i++) {
			comboBox.addItem("" + tourfirms.get(i).getName());
		}
		Tour exh = new Tour();
		tours = new ArrayList<>(exh.getTable(connection));
		comboBox_1.removeAllItems();
		for (int i = 0; i < tours.size(); i++) {
			comboBox_1.addItem("" + tours.get(i).getName());
		}
	}

	public TransportForm(int id, Connection connection) throws SQLException {
		Initiate();
		int rid = 0;
		int aid = 0;
		this.connection = connection;
		this.idSelected = id;
		Transport transport = new Transport();
		ArrayList<Transport> transports = new ArrayList<>(
				transport.getTable(connection));
		transport = null;
		for (int i = 0; i < transports.size(); i++) {
			if (id == transports.get(i).getId()) {
				transport = transports.get(i);
				break;
			}
		}
		TourFirma au = new TourFirma();
		tourfirms = new ArrayList<>(au.getTable(connection));
		comboBox.removeAllItems();
		for (int i = 0; i < tourfirms.size(); i++) {
			comboBox.addItem("" + tourfirms.get(i).getName());
			if (transport.getTourfirmaid() == tourfirms.get(i).getId()) {
				aid = i;
			}
		}
		Tour exh = new Tour();
		tours = new ArrayList<>(exh.getTable(connection));
		comboBox_1.removeAllItems();
		for (int i = 0; i < tours.size(); i++) {
			comboBox_1.addItem("" + tours.get(i).getName());
			if (transport.getRouteid() == tours.get(i).getId()) {
				rid = i;
			}
		}
		textField.setText(transport.getGosnumber());
		textField_1.setText(transport.getType());
		comboBox.setSelectedItem(tourfirms.get(aid).getName());
		comboBox_1.setSelectedItem(tours.get(rid).getName());
	}

	private void Initiate() {
		setTitle("Транспорт");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 324, 202);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel(
				"Модель");
		label.setBounds(10, 14, 100, 14);
		contentPane.add(label);

		textField = new JTextField();
		textField.setBounds(120, 11, 178, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton button = new JButton(
				"\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Transport transport = null;
				if (textField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					transport = new Transport(textField.getText(), textField_1
							.getText(), (comboBox.getSelectedIndex() + 1),
							(comboBox_1.getSelectedIndex() + 1));
					if (idSelected < 0) {
						transport.addElement(textField.getText(), textField_1
								.getText(),
								tourfirms.get(comboBox.getSelectedIndex()).getId(),
								tours.get(comboBox_1.getSelectedIndex())
										.getId(), connection);
					} else {
						transport.refreshElement(idSelected, textField
								.getText(), textField_1.getText(),
								tourfirms.get(comboBox.getSelectedIndex()).getId(),
								tours.get(comboBox_1.getSelectedIndex())
										.getId(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					Logger.getLogger(TransportForm.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
		button.setBounds(10, 129, 144, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		button_1.setBounds(164, 129, 134, 23);
		contentPane.add(button_1);

		JLabel label_1 = new JLabel(
				"Турфирма");
		label_1.setBounds(10, 67, 100, 14);
		contentPane.add(label_1);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(120, 64, 178, 20);
		contentPane.add(comboBox);

		JLabel label_2 = new JLabel(
				"Тур №");
		label_2.setBounds(10, 92, 100, 14);
		contentPane.add(label_2);

		comboBox_1 = new JComboBox<String>();

		comboBox_1.setBounds(120, 89, 178, 20);
		contentPane.add(comboBox_1);

		JLabel label_3 = new JLabel("\u0422\u0438\u043F");
		label_3.setBounds(10, 39, 100, 14);
		contentPane.add(label_3);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(120, 36, 178, 20);
		contentPane.add(textField_1);
	}
}
