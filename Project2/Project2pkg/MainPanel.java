/*Author: Dinh Hoang
 * Project 2 - CS3381 OO Java development
 * Front-end for Patient Collection program
 * MainPanel: including home panel, viewPanel, updatePanel and insertPanel
 */
package Project2pkg;

import javax.swing.JPanel;

import project1.PatientCollection;

import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTree;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class MainPanel extends JPanel {
	private final int WIDTH = 800, HEIGHT = 500;
	private PatientCollection mypats;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textFieldsetresult;
	private JTextField textFieldRemove;
	private String fileChosen;
	private ArrayList<String> myIds1;
	private JComboBox<String> comboBoxID_1;
	private JComboBox<String> comboBoxID;
	private String patientUpdateId = null;
	private JLabel lblGetPatient;
	private JButton btnViewAllPat;
	private JTextArea textAreaViewAllPat;
	private JPanel panelView;
	private JPanel panelAdd;
	private JPanel panelUpdate;
	private JPanel panelHome;
	private JLabel lblRemoveThePatient;

	public MainPanel() {

		mypats = new PatientCollection("./data.csv");
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		fileChosen = null;
		myIds1 = mypats.getIds();

		// Panel Update
		panelUpdate = new JPanel();
		panelUpdate.setBounds(10, 25, 515, 464);
		add(panelUpdate);
		panelUpdate.setVisible(false);
		panelUpdate.setLayout(null);

		JRadioButton rdbtnDP = new JRadioButton("DP");
		buttonGroup.add(rdbtnDP);
		rdbtnDP.setBounds(127, 146, 47, 23);
		panelUpdate.add(rdbtnDP);
		rdbtnDP.setSelected(false);

		JRadioButton rdbtnCR = new JRadioButton("CR");
		buttonGroup.add(rdbtnCR);
		rdbtnCR.setBounds(127, 120, 47, 23);
		panelUpdate.add(rdbtnCR);
		rdbtnCR.setSelected(false);

		JLabel lblChangingTheResult = new JLabel("Set the result for the patient with ID number ...");
		lblChangingTheResult.setBounds(46, 99, 344, 14);
		panelUpdate.add(lblChangingTheResult);

		JButton btnUpdate_1 = new JButton("Update");
		btnUpdate_1.setBounds(189, 124, 84, 23);
		panelUpdate.add(btnUpdate_1);

		textFieldsetresult = new JTextField();
		textFieldsetresult.setBounds(67, 197, 323, 43);
		panelUpdate.add(textFieldsetresult);
		textFieldsetresult.setColumns(10);

		JLabel lblPatientsId_1 = new JLabel("Patient's ID");
		lblPatientsId_1.setBounds(127, 36, 67, 14);
		panelUpdate.add(lblPatientsId_1);

		comboBoxID_1 = new JComboBox<String>();
		comboBoxID_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				patientUpdateId = (String) comboBoxID_1.getSelectedItem();
				lblChangingTheResult
						.setText("Set the result for the patient with ID number " + comboBoxID_1.getSelectedItem());
				lblRemoveThePatient.setText("Remove the patient with ID number " + comboBoxID_1.getSelectedItem());
				textFieldsetresult.setText("");
				textFieldRemove.setText("");
				
			}
		});
		comboBoxID_1.setModel(new DefaultComboBoxModel(myIds1.toArray()));
		comboBoxID_1.setBounds(204, 32, 53, 22);
		panelUpdate.add(comboBoxID_1);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(152, 289, 90, 23);
		panelUpdate.add(btnRemove);

		 lblRemoveThePatient = new JLabel("Remove the patient with ID number ...");
		lblRemoveThePatient.setBounds(46, 251, 344, 27);
		panelUpdate.add(lblRemoveThePatient);

		textFieldRemove = new JTextField();
		textFieldRemove.setColumns(10);
		textFieldRemove.setBounds(67, 336, 323, 43);
		panelUpdate.add(textFieldRemove);

		JLabel lblUpdateGuide = new JLabel("Choose a Patient ID to update or remove ");
		lblUpdateGuide.setBounds(77, 11, 374, 14);
		panelUpdate.add(lblUpdateGuide);
		rdbtnDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnDP.isSelected())
					rdbtnDP.setSelected(false);
			}
		});
		rdbtnCR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnCR.isSelected())
					rdbtnDP.setSelected(false);
			}
		});
		// Action listeners for panelUpdate:
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (patientUpdateId == null) {
					textFieldRemove.setText("You haven't chosen any patient to remove yet.");
				} else {
					String warningMsg = "Are you sure you want to delete Patient ID number " + patientUpdateId + "?";
					if (JOptionPane.showConfirmDialog(null, warningMsg, "Confirmation",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						mypats.removePatient(patientUpdateId);
						textFieldRemove.setText("The patient with ID number " + patientUpdateId + " is removed");
						comboBoxID_1.setModel(new DefaultComboBoxModel(myIds1.toArray()));
						comboBoxID.setModel(new DefaultComboBoxModel(myIds1.toArray()));
						// reset the patient Update Id and the labels
						patientUpdateId = null;
						lblChangingTheResult.setText("Set the result for the patient with ID number ...");
						lblRemoveThePatient.setText("Remove the patient with ID number ...");
					}
				}
			}
		});
		btnUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (patientUpdateId == null) {
					textFieldsetresult.setText("You haven't chosen any patient to update yet.");
				} else {
					if (rdbtnCR.isSelected() == false && rdbtnDP.isSelected() == false) {
						textFieldsetresult.setText("Error! Please choose which result you want to set to.");
					} else {
						if (rdbtnCR.isSelected()) {
							mypats.getPatient(patientUpdateId).setResult("CR");
							textFieldsetresult.setText(
									"Successfully set the result to CR for patient ID number " + patientUpdateId);
						} else {
							mypats.getPatient(patientUpdateId).setResult("DP");
							textFieldsetresult.setText(
									"Successfully set the result to DP for patient ID number " + patientUpdateId);
						}
					}
				}
			}
		});

		// Panle Home
		panelHome = new JPanel();
		panelHome.setBounds(10, 33, 465, 424);
		add(panelHome);
		panelHome.setVisible(true);

		JButton btnView = new JButton("View Patient Information");
		btnView.setBounds(142, 108, 209, 23);

		panelHome.setLayout(null);
		panelHome.add(btnView);

		JButton btnUpdate = new JButton("Update or Remove");
		btnUpdate.setBounds(142, 162, 209, 23);

		panelHome.add(btnUpdate);

		JButton btnAdd = new JButton("Add New Patient");
		btnAdd.setBounds(142, 214, 209, 23);

		panelHome.add(btnAdd);
		// Action listeners for panelHome
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelView.setVisible(true);
				panelAdd.setVisible(false);
				panelHome.setVisible(false);
				panelUpdate.setVisible(false);
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelView.setVisible(false);
				panelAdd.setVisible(false);
				panelHome.setVisible(false);
				panelUpdate.setVisible(true);
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelView.setVisible(false);
				panelAdd.setVisible(true);
				panelHome.setVisible(false);
				panelUpdate.setVisible(false);
			}
		});

		// panel "Add"
		panelAdd = new JPanel();
		panelAdd.setBounds(10, 24, 479, 476);
		add(panelAdd);
		panelAdd.setLayout(null);
		panelAdd.setVisible(false);

		JLabel lblFileIntro = new JLabel("Choose a file to add new patients");
		lblFileIntro.setBounds(119, 54, 249, 39);
		panelAdd.add(lblFileIntro);

		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(164, 104, 89, 23);
		panelAdd.add(btnOpen);

		JLabel lblFileChosen = new JLabel("The file you chose is: ...");
		lblFileChosen.setVerticalAlignment(SwingConstants.TOP);
		lblFileChosen.setBounds(60, 138, 353, 30);
		panelAdd.add(lblFileChosen);

		JButton btnAddFile = new JButton("Add to Record");
		btnAddFile.setBounds(148, 179, 137, 23);
		panelAdd.add(btnAddFile);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(60, 227, 359, 130);
		panelAdd.add(scrollPane);

		JTextArea textAreaFileAdd = new JTextArea();
		scrollPane.setViewportView(textAreaFileAdd);
		// Action listener for panel Insert
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser("./");

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					fileChosen = selectedFile.getName();
					String text = "The file you chose is: ";
					text += fileChosen;
					lblFileChosen.setText(text);
					System.out.println(text);
				}
			}
		});
		btnAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChosen == null) {
					textAreaFileAdd.setText("You haven't chosen any file yet.");
				} else {
					String error = "";
					String fileToADD = "./";
					fileToADD += fileChosen;
					System.out.println(fileToADD);
					error = mypats.addPatientsFromFile(fileToADD);
					String text = "Successfully added to the record!\n";
					if (error == null)
						text += "There is no error";
					else
						text += "There are some errors:\n";
					text += error;

					textAreaFileAdd.setText(text);
					System.out.println(text);
					comboBoxID_1.setModel(new DefaultComboBoxModel(myIds1.toArray()));
					comboBoxID.setModel(new DefaultComboBoxModel(myIds1.toArray()));
				}

			}
		});

		// Panel View
		panelView = new JPanel();
		panelView.setBounds(25, 33, 526, 456);
		add(panelView);
		panelView.setVisible(false);
		panelView.setLayout(null);

		JLabel lblPatientsId = new JLabel("Patient's ID");
		lblPatientsId.setBounds(122, 42, 67, 14);
		panelView.add(lblPatientsId);

		comboBoxID = new JComboBox<String>();
		comboBoxID.setModel(new DefaultComboBoxModel(myIds1.toArray()));
		comboBoxID.setBounds(199, 38, 53, 22);
		panelView.add(comboBoxID);
		comboBoxID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = (String) comboBoxID.getSelectedItem();
				String text = mypats.getPatient(id).toString();
				lblGetPatient.setText(text);
			}
		});

		lblGetPatient = new JLabel("Patient's Information display here");
		lblGetPatient.setVerticalAlignment(SwingConstants.TOP);
		lblGetPatient.setBounds(49, 82, 364, 32);
		panelView.add(lblGetPatient);

		btnViewAllPat = new JButton("View All Patients");
		btnViewAllPat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = "";
				String text = "";
				for (int i = 0; i < myIds1.size(); i++) {
					id = myIds1.get(i);
					text += mypats.getPatient(id).toString();
					text += '\n';
				}
				textAreaViewAllPat.setText(text);

			}
		});
		btnViewAllPat.setBounds(108, 118, 154, 23);
		panelView.add(btnViewAllPat);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(49, 164, 431, 223);
		panelView.add(scrollPane_1);

		textAreaViewAllPat = new JTextArea();
		scrollPane_1.setViewportView(textAreaViewAllPat);
		myIds1 = mypats.getIds();

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBounds(0, 0, WIDTH, 22);
		add(menuBar);
		JMenu mnHome = new JMenu("Home");
		menuBar.add(mnHome);
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		JMenu mnUpdate = new JMenu("Update");
		menuBar.add(mnUpdate);
		JMenu mnInsert = new JMenu("Insert");
		menuBar.add(mnInsert);

		/*
		 * Listener functions start here
		 */
		// Action listeners for menu bar
		mnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelView.setVisible(false);
				panelAdd.setVisible(false);
				panelHome.setVisible(true);
				panelUpdate.setVisible(false);
			}
		});
		mnView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelView.setVisible(true);
				panelAdd.setVisible(false);
				panelHome.setVisible(false);
				panelUpdate.setVisible(false);
			}
		});
		mnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelView.setVisible(false);
				panelAdd.setVisible(false);
				panelHome.setVisible(false);
				panelUpdate.setVisible(true);
			}
		});

		mnInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelView.setVisible(false);
				panelAdd.setVisible(true);
				panelHome.setVisible(false);
				panelUpdate.setVisible(false);
			}
		});
	}

	public void doClose() {
		mypats.doWrite("./data.csv"); // change it to data.csv after done
	}
}
