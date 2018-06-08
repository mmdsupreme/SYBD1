package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import entity.TourFirma;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TourfirmaForm extends JFrame {
	public int idSelected;
	private Connection connection = null;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * @wbp.parser.constructor
	 */
	public TourfirmaForm(Connection connection) {
		Initiate();
		this.idSelected = -1;
		this.connection = connection;
	}

	public TourfirmaForm(int id, Connection connection) throws SQLException {
		Initiate();
		this.idSelected = id;
		this.connection = connection;
		TourFirma auto = new TourFirma();
		ArrayList<TourFirma> autos = new ArrayList<>(auto.getTable(connection));
		auto = null;
		for (int i = 0; i < autos.size(); i++) {
			if (id == autos.get(i).getId()) {
				auto = autos.get(i);
				break;
			}
		}
		textField.setText(auto.getName());
	}

	private void Initiate() {
		setTitle("\u0410\u0432\u0442\u043E\u043F\u0440\u0435\u0434\u043F\u0440\u0438\u044F\u0442\u0438\u0435");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 324, 127);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435");
		label.setBounds(10, 14, 100, 14);
		contentPane.add(label);

		textField = new JTextField();
		textField.setBounds(120, 11, 178, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TourFirma autoenterprise = null;
				if (textField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Îøèáêà");
				}
				try {
					autoenterprise = new TourFirma(textField.getText());
					if (idSelected < 0) {
						autoenterprise.addElement(textField.getText(), connection);
					} else {
						autoenterprise.refreshElement(idSelected, textField.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		button.setBounds(10, 54, 144, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		button_1.setBounds(164, 54, 134, 23);
		contentPane.add(button_1);
	}
}
