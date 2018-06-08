package forms;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;

import entity.TourFirma;
import entity.City;
import entity.Tour;
import entity.PlaceDeparture;
import entity.Transport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TestForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public TestForm(Connection connection) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 215, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		String[] str = { "Турфирма", "Город", "Тур", "Место отправления",
				"Транспорт" };

		JComboBox comboBox = new JComboBox(str);
		comboBox.setBounds(10, 11, 168, 23);
		contentPane.add(comboBox);

		JButton btnNewButton = new JButton(
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C 10000");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long start = System.currentTimeMillis();
				if (connection != null) {
					switch ((String) comboBox.getSelectedItem()) {
					case "Турфирма":
						try {
							TourFirma a = new TourFirma();
							a.add10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Город":
						try {
							City t = new City();
							t.add10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Тур":
						try {
							Tour t = new Tour();
							t.createIndex(connection);
							t.add10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Место отправления":
						try {
							PlaceDeparture t = new PlaceDeparture();
							t.createIndex(connection);
							t.add10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Транспорт":
						try {
							Transport t = new Transport();
							t.createIndex(connection);
							t.add10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					}
					long end = System.currentTimeMillis();
					textField.setText(Long.toString((end - start)));
					JOptionPane.showMessageDialog(null, "Добавлено");
				}
			}
		});
		btnNewButton.setBounds(10, 45, 168, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton(
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C 10000");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long start = System.currentTimeMillis();
				if (connection != null) {
					switch ((String) comboBox.getSelectedItem()) {
					case "Турфирма":
						try {
							TourFirma a = new TourFirma();
							a.delete10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Город":
						try {
							City t = new City();
							t.delete10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Тур":
						try {
							Tour t = new Tour();
							t.delete10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Место отправления":
						try {
							PlaceDeparture t = new PlaceDeparture();
							t.delete10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
							return;
						}
						break;
					case "Транспорт":
						try {
							Transport t = new Transport();
							t.delete10k(connection);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					}
				}
				long end = System.currentTimeMillis();
				textField.setText(Long.toString((end - start)));
				JOptionPane.showMessageDialog(null, "Удалено");
			}
		});
		btnNewButton_1.setBounds(10, 79, 168, 23);
		contentPane.add(btnNewButton_1);

		JButton button = new JButton(
				"\u0417\u0430\u043A\u0440\u044B\u0442\u044C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		button.setBounds(100, 183, 89, 23);
		contentPane.add(button);

		JLabel label = new JLabel(
				"Время");
		label.setBounds(10, 113, 168, 14);
		contentPane.add(label);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 138, 168, 20);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
