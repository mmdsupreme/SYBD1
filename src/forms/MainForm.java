package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import entity.City;
import entity.Tour;
import entity.Transport;
import entity.TourFirma;
import entity.PlaceDeparture;
import forms.ConnectionForm;

public class MainForm {
	JComboBox ComboBox;
	private JFrame frame;
	private JTable table;
	Connection connection = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainForm() {
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		String[] str = { "Турфирма", "Город", "Тур", "Место отправления",
				"Транспорт" };
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		table = new JTable();
		ComboBox = new JComboBox(str);
		ComboBox.setBounds(150, 380, 133, 23);
		ComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(ComboBox);

		JButton button = new JButton(
				"Подключить");
		button.setBounds(10, 350, 133, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectionForm form = new ConnectionForm(frame);
				connection = form.getConnection();
				if (connection != null) {
					refresh();
				}
			}
		});
		frame.getContentPane().add(button);

		JButton button_1 = new JButton(
				"Отключить");
		button_1.setBounds(150, 350, 133, 23);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Disconnect();
			}
		});
		frame.getContentPane().add(button_1);

		JButton button_3 = new JButton(
				"Изменить");
		button_3.setBounds(430, 350, 133, 23);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = (table.getSelectedRow());
					switch ((String) ComboBox.getSelectedItem()) {
					case "Турфирма":
						try {
							TourFirma al = new TourFirma();
							int i = al.getTable(connection).get(idEl).getId();
							TourfirmaForm form1 = new TourfirmaForm(
									i, connection);
							form1.setVisible(true);
							TourFirma g = new TourFirma();
							table.setModel(g.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Город":
						try {
							City al = new City();
							int i = al.getTable(connection).get(idEl).getId();
							CityForm form2 = new CityForm(i, connection);
							form2.setVisible(true);
							table.setModel(al.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Тур":
						try {
							Tour al = new Tour();
							int i = al.getTable(connection).get(idEl).getId();
							TourForm form3 = new TourForm(i, connection);
							form3.setVisible(true);
							Tour exh = new Tour();
							table.setModel(exh.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Место отправления":
						try {
							PlaceDeparture al = new PlaceDeparture();
							int i = al.getTable(connection).get(idEl).getId();
							PlacedepartureForm form4 = new PlacedepartureForm(i,
									connection);
							form4.setVisible(true);
							table.setModel(al.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Транспорт":
						try {
							Transport al = new Transport();
							int i = al.getTable(connection).get(idEl).getId();
							TransportForm form5 = new TransportForm(i,
									connection);
							form5.setVisible(true);
							Transport p = new Transport();
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Выберите элемент");
				}
			}
		});
		frame.getContentPane().add(button_3);

		JButton button_4 = new JButton(
				"Удалить");
		button_4.setBounds(430, 380, 133, 23);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = Integer.parseInt(table.getValueAt(
							table.getSelectedRow(), 0).toString());
					switch ((String) ComboBox.getSelectedItem()) {
					case "Турфирма":
						try {
							TourFirma g = new TourFirma();
							g.removeElement(idEl, connection);
							table.setModel(g.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Город":
						try {
							City a = new City();
							a.removeElement(idEl, connection);
							table.setModel(a.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Тур":
						try {
							Tour exh = new Tour();
							exh.removeElement(idEl, connection);
							table.setModel(exh.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Место отправления":
						try {
							PlaceDeparture al = new PlaceDeparture();
							al.removeElement(idEl, connection);
							table.setModel(al.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Транспорт":
						try {
							Transport p = new Transport();
							p.removeElement(idEl, connection);
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Выберите элемент");
				}
			}
		});
		frame.getContentPane().add(button_4);

		JButton button_5 = new JButton(
				"Добавить");
		button_5.setBounds(290, 350, 133, 23);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (connection != null) {
					switch ((String) ComboBox.getSelectedItem()) {
					case "Турфирма":
						try {
							TourfirmaForm form1 = new TourfirmaForm(
									connection);
							form1.setVisible(true);
							TourFirma g = new TourFirma();
							table.setModel(g.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Город":
						try {
							CityForm form2 = new CityForm(connection);
							form2.setVisible(true);
							City a = new City();
							table.setModel(a.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Тур":
						try {
							TourForm form3;
							form3 = new TourForm(connection);
							form3.setVisible(true);
							Tour exh = new Tour();
							table.setModel(exh.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Место отправления":
						try {
							PlacedepartureForm form4;
							form4 = new PlacedepartureForm(connection);
							form4.setVisible(true);
							PlaceDeparture al = new PlaceDeparture();
							table.setModel(al.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					case "Транспорт":
						try {
							TransportForm form5;
							form5 = new TransportForm(connection);
							form5.setVisible(true);
							Transport p = new Transport();
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					}
				}
			}
		});
		frame.getContentPane().add(button_5);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 555, 323);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton button_2 = new JButton(
				"Обновить");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		button_2.setBounds(290, 380, 133, 23);
		frame.getContentPane().add(button_2);

		JButton button_6 = new JButton("Генератор");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestForm form = new TestForm(connection);
				form.setVisible(true);
			}
		});
		button_6.setBounds(10, 380, 133, 23);
		frame.getContentPane().add(button_6);
	}

	private void Disconnect() {
		if (connection != null) {
			connection = null;
			JOptionPane.showMessageDialog(null, "Соеденение закрыто");
		}
	}

	private void refresh() {
		if (connection != null) {
			switch ((String) ComboBox.getSelectedItem()) {
			case "Турфирма":
				try {
					TourFirma g = new TourFirma();
					table.setModel(g.TableModel(connection));
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				break;
			case "Город":
				try {
					City a = new City();
					table.setModel(a.TableModel(connection));
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				break;
			case "Тур":
				try {
					Tour exh = new Tour();
					table.setModel(exh.TableModel(connection));
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				break;
			case "Место отправления":
				try {
					PlaceDeparture al = new PlaceDeparture();
					table.setModel(al.TableModel(connection));
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				break;
			case "Транспорт":
				try {
					Transport p = new Transport();
					table.setModel(p.TableModel(connection));
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				break;
			}
		}
	}
}
