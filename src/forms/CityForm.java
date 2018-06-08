package forms;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import entity.TourFirma;
import entity.City;

public class CityForm extends JFrame {
	private Connection connection = null;
	public int idSelected;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel label;
	private JButton button;
	private JButton button_1;

	/**
	 * @wbp.parser.constructor
	 */
	public CityForm(Connection connection) {
		Initiate();
		this.idSelected = -1;
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				getContentPane(), contentPane, label, button, button_1,
				textField }));
		this.connection = connection;
	}

	public CityForm(int id, Connection connection) throws SQLException {
		Initiate();
		this.idSelected = id;
		this.connection = connection;
		City city = new City();
		ArrayList<City> citys = new ArrayList<>(city.getTable(connection));
		city = null;
		for (int i = 0; i < citys.size(); i++) {
			if (id == citys.get(i).getId()) {
				city = citys.get(i);
				break;
			}
		}
		textField.setText(city.getName());
	}

	private void Initiate() {
		setTitle("Направление");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 324, 127);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		label = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435");
		label.setBounds(10, 14, 100, 14);
		contentPane.add(label);

		textField = new JTextField();
		textField.setBounds(120, 11, 178, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		button = new JButton(
				"\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				City city = null;
				if (textField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					city = new City(textField.getText());
					if (idSelected < 0) {
						city.addElement(textField.getText(), connection);
					} else {
						city.refreshElement(idSelected, textField.getText(),
								connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					Logger.getLogger(CityForm.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
		button.setBounds(10, 54, 144, 23);
		contentPane.add(button);

		button_1 = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		button_1.setBounds(164, 54, 134, 23);
		contentPane.add(button_1);
	}
}
