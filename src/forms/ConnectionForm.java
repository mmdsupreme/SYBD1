package forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ConnectionForm extends JDialog {
	private JFrame frame;
	private JTextField textFieldHost;
	private JTextField textFieldPort;
	private JTextField textFieldBDName;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
	private Connection connection = null;

	public ConnectionForm(JFrame frame) {
		super(frame, true);
		connect();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void connect() {
		this.setBounds(100, 100, 326, 239);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel label = new JLabel(
				"\u0423\u0441\u0442\u0430\u043D\u043E\u0432\u043A\u0430 \u043F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u044F");
		label.setBounds(10, 11, 286, 14);
		this.getContentPane().add(label);

		JLabel labelHost = new JLabel("\u0425\u043E\u0441\u0442:");
		labelHost.setBounds(10, 36, 138, 14);
		this.getContentPane().add(labelHost);

		JLabel labelPort = new JLabel(
				"\u041F\u043E\u0440\u0442 \u0441\u0435\u0440\u0432\u0435\u0440\u0430:");
		labelPort.setBounds(10, 61, 150, 14);
		this.getContentPane().add(labelPort);

		JLabel labelBDName = new JLabel(
				"\u041D\u0430\u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u0435 \u0411\u0414:");
		labelBDName.setBounds(10, 86, 150, 14);
		this.getContentPane().add(labelBDName);

		JLabel labelLogin = new JLabel("\u041B\u043E\u0433\u0438\u043D:");
		labelLogin.setBounds(10, 111, 138, 14);
		this.getContentPane().add(labelLogin);

		JLabel labelPassword = new JLabel(
				"\u041F\u0430\u0440\u043E\u043B\u044C:");
		labelPassword.setBounds(10, 136, 138, 14);
		this.getContentPane().add(labelPassword);

		textFieldHost = new JTextField();
		textFieldHost.setText("localhost");
		textFieldHost.setBounds(158, 30, 138, 20);
		this.getContentPane().add(textFieldHost);
		textFieldHost.setColumns(10);

		textFieldPort = new JTextField();
		textFieldPort.setText("5432");
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(158, 55, 138, 20);
		this.getContentPane().add(textFieldPort);

		textFieldBDName = new JTextField();
		textFieldBDName.setText("postgres");
		textFieldBDName.setColumns(10);
		textFieldBDName.setBounds(158, 80, 138, 20);
		this.getContentPane().add(textFieldBDName);

		textFieldLogin = new JTextField();
		textFieldLogin.setText("postgres");
		textFieldLogin.setColumns(10);
		textFieldLogin.setBounds(158, 105, 138, 20);
		this.getContentPane().add(textFieldLogin);

		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(158, 130, 138, 20);
		this.getContentPane().add(textFieldPassword);

		JButton buttonConnect = new JButton(
				"\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0438\u0442\u044C\u0441\u044F");
		buttonConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JOptionPane.showMessageDialog(
							null,
							Connect(textFieldHost.getText(),
									textFieldPort.getText(),
									textFieldBDName.getText(),
									textFieldLogin.getText(),
									textFieldPassword.getText()));
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		buttonConnect.setBounds(158, 161, 138, 23);
		this.getContentPane().add(buttonConnect);

		JButton ButtonCancel = new JButton(
				"\u041E\u0442\u043C\u0435\u043D\u0430");
		ButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ButtonCancel.setBounds(10, 161, 89, 23);
		this.getContentPane().add(ButtonCancel);

	}

	public String Connect(String host, String port, String bd, String user,
			String password) {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://" + host + ":" + port + "/" + bd;
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			if (connection != null) {
				return "Вы успешно подключились к БД";
			} else {
				return "Ошибка при подключении к БД";
			}
		} catch (ClassNotFoundException ex) {
			return "Драйвер не найден";
		} catch (SQLException e) {
			return "Ошибка подключения";
		}
	}

	public void disconnect() {
		try {
			connection.close();
			JOptionPane.showMessageDialog(null, "Соеденение закрыто");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ошибка");
		}
	}

	public Connection getConnection() {
		return connection;
	}
}
