package forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import entity.City;
import entity.Tour;
import entity.PlaceDeparture;

public class TourForm extends JFrame {
	private Connection connection = null;
	public int idSelected;
	private JPanel contentPane;
	private JTextField textField;
	private JComboBox<String> comboBox;
	private ArrayList<City> citys;
	
	/**
	 * @wbp.parser.constructor
	 */
	public TourForm(Connection connection) throws SQLException {
		this.connection = connection;
		Initiate();
		this.idSelected = -1;
		City g = new City( );
		citys = new ArrayList<>(g.getTable(connection));
		comboBox.removeAllItems();
		for (int i = 0; i < citys.size(); i++) {
			comboBox.addItem("" + citys.get(i).getName());
		}
	}

	public TourForm(int id, Connection connection) throws SQLException {
		Initiate();
		int cid = 0;
		this.connection = connection;
		this.idSelected = id;
		Tour tour = new Tour();
		ArrayList<Tour> tours = new ArrayList<>(tour.getTable(connection));
		tour = null;
		for (int i = 0; i < tours.size(); i++) {
			if (id - 1 == tours.get(i).getId() - 1) {
				tour = tours.get(i);
			}
		}
		City g = new City();
		citys = new ArrayList<>(g.getTable(connection));
		comboBox.removeAllItems();
		for (int i = 0; i < citys.size(); i++) {
			comboBox.addItem("" + citys.get(i).getName());
			if (tour.getCityid() == citys.get(i).getId()) {
				cid = i;
			}
		}
		comboBox.setSelectedItem(citys.get(cid).getName());
		textField.setText(tour.getName());
	}

	private void Initiate() {
		setTitle("Òóð");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 324, 156);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u041D\u043E\u043C\u0435\u0440");
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
				Tour tour = null;
				try {
					if (textField.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "Îøèáêà");
					}
					tour = new Tour(Integer.parseInt(textField.getText()),
							(comboBox.getSelectedIndex() + 1));
					if (idSelected < 0) {
						System.out.println(comboBox.getSelectedIndex()+1);
						
						tour.addElement(Integer.parseInt(textField.getText()),
								citys.get(comboBox.getSelectedIndex()).getId(), connection);
					} else {
						tour.refreshElement(idSelected,
								Integer.parseInt(textField.getText()),
								citys.get(comboBox.getSelectedIndex()).getId(), connection);
					}
				} catch (Exception ex) {
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

		JLabel label_1 = new JLabel("\u0413\u043E\u0440\u043E\u0434");
		label_1.setBounds(10, 42, 100, 14);
		contentPane.add(label_1);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(120, 39, 178, 20);
		contentPane.add(comboBox);
	}
}
