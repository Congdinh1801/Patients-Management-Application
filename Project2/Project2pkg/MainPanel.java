/*Author: Dinh Hoang
 * Project 2 - CS3381 OO Java development
 * Front-end for Patient Collection program
 * MainPanel: including home panel, viewPanel, updatePanel and insertPanel
 * welcome gif source: http://www.clipartbest.com/cliparts/9iR/XX9/9iRXX9nie.gif
 */
package Project2pkg;

import javax.swing.JPanel;

import project1.PatientCollection;

import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class MainPanel extends JPanel {
	//private final int WIDTH = 800, HEIGHT = 500;
	private PatientCollection mypats;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textFieldsetresult;
	private JTextField textFieldRemove;
	private String fileChosen;
	private ArrayList<String> myIds1;
	private JComboBox<String> comboBoxIDUpdate;
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
		setPreferredSize(new Dimension(615, 500));
		fileChosen = null;
		myIds1 = mypats.getIds();

		// Panel Home
		panelHome = new JPanel();
		panelHome.setBounds(10, 33, 495, 445);
		add(panelHome);
		panelHome.setVisible(true); // when open the program, the user will see the panel home first

		JButton btnView = new JButton("View Patient Information");
		btnView.setBounds(172, 206, 209, 23);

		panelHome.setLayout(null);
		panelHome.add(btnView);

		JButton btnUpdate = new JButton("Update or Remove");
		btnUpdate.setBounds(172, 260, 209, 23);

		panelHome.add(btnUpdate);

		JButton btnAdd = new JButton("Add New Patient");
		btnAdd.setBounds(172, 312, 209, 23);

		panelHome.add(btnAdd);

		JLabel lblImage = new JLabel("");
		lblImage.setBounds(69, 11, 456, 184);
		lblImage.setIcon(new ImageIcon(getClass().getResource("/Project2pkg/wel1.gif")));
		panelHome.add(lblImage);
		// Action listeners for panelHome's buttons:
		//Display the appropriate panel upon user's choice 
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

		// Panel Add
		panelAdd = new JPanel();
		panelAdd.setBounds(10, 31, 479, 469);
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
		// Action listener for panel Insert's components
		// button Open: ask the user to choose a file to add new patients
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser("./");
				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					fileChosen = selectedFile.getName(); // set the file chosen to the selected file
					String text = "The file you chose is: " + fileChosen;
					lblFileChosen.setText(text);
					System.out.println(text);
					// reset the add result text field
					textAreaFileAdd.setText("");
				}
			}
		});
		//button Addfile: Let user choose a file from FileChooser  
		btnAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChosen == null) {
					textAreaFileAdd.setText("You haven't chosen any file yet.");
				} else {
					String error = "";
					String fileToADD = "./";
					fileToADD += fileChosen;
					// System.out.println(fileToADD);
					error = mypats.addPatientsFromFile(fileToADD);
					String text = "Successfully added " + fileChosen + " to the record!\n";
					if (error == null)
						text += "There is no error";
					else
						text += "There are some errors:\n";
					text += error;
					textAreaFileAdd.setText(text);
					// System.out.println(text);
					//Update the comboBoxId in view and update panel since there are new patients
					comboBoxIDUpdate.setModel(new DefaultComboBoxModel<String>((String[]) myIds1.toArray()));
					comboBoxID.setModel(new DefaultComboBoxModel<String>((String[]) myIds1.toArray()));
					// Reset file chosen
					fileChosen = null;
					lblFileChosen.setText("The file you chose is: ...");

				}

			}
		});

		// Panel Update
		panelUpdate = new JPanel();
		panelUpdate.setBounds(10, 33, 515, 456);
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

		comboBoxIDUpdate = new JComboBox<String>();
		comboBoxIDUpdate.setModel(new DefaultComboBoxModel(myIds1.toArray()));
		comboBoxIDUpdate.setBounds(204, 32, 53, 22);
		comboBoxIDUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				patientUpdateId = (String) comboBoxIDUpdate.getSelectedItem();
				lblChangingTheResult.setText("Set the result for the patient with ID number " + patientUpdateId);
				lblRemoveThePatient.setText("Remove the patient with ID number " + patientUpdateId);
				textFieldsetresult.setText("");
				textFieldRemove.setText("");

			}
		});
		panelUpdate.add(comboBoxIDUpdate);

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
		//Action listener: display JOptionPane to let the user confirm if they want to delete the patient id
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
						comboBoxIDUpdate.setModel(new DefaultComboBoxModel(myIds1.toArray()));
						comboBoxID.setModel(new DefaultComboBoxModel(myIds1.toArray()));
						// reset the patient Update Id and the labels
						patientUpdateId = null;
						lblChangingTheResult.setText("Set the result for the patient with ID number ...");
						lblRemoveThePatient.setText("Remove the patient with ID number ...");
					}
				}
			}
		});
		//Action listner for button update: ask the user to set the result to either CR or DP
		//Display error if they chose none options
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
		//Action listener: display individual patient'information upon chosen from the comboBox
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
		//Action listener: display all patient's information upon clicking the button
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
		menuBar.setForeground(Color.BLACK);
		menuBar.setBackground(new Color(0, 153, 51));
		menuBar.setBounds(0, 0, 800, 30);
		add(menuBar);
		JMenu mnHome = new JMenu("Home");
		mnHome.setForeground(Color.WHITE);
		menuBar.add(mnHome);
		JMenu mnView = new JMenu("View");
		mnView.setForeground(Color.WHITE);
		menuBar.add(mnView);
		JMenu mnUpdate = new JMenu("Update");
		mnUpdate.setForeground(Color.WHITE);
		menuBar.add(mnUpdate);
		JMenu mnInsert = new JMenu("Insert");
		mnInsert.setForeground(Color.WHITE);
		menuBar.add(mnInsert);

		// Action listeners for menu bar's components
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

	// save data after close the program
	public void doClose() {
		mypats.doWrite("./data.csv"); // change it to data.csv after done
	}
}
