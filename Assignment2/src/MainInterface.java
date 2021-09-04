
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainInterface extends JFrame {
	static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	private int kustLogoSize = (int) (screenWidth * 1.8) / 100;

	private InterfaceMenuBar interfaceMenuBar;
	private StudentInfoForm studentInfoForm;
	private StudentsTablePanel studentsTablePanel;
	private JLabel copyright;
	private int selectedRowToEdit = -1;

	public MainInterface() {
		interfaceSettings("Student Management System");
		setInterfaceMenuBar(new InterfaceMenuBar());
		setStudentInfoForm(new StudentInfoForm());
		setStudentsTable(new StudentsTablePanel());
		setCopyright(new JLabel("All Right Reserved to KUST"),
				pathToIcon("Kust-logo.png", kustLogoSize, kustLogoSize), "2021");

		setVisible(true);
	}

	/**
	 * Sets the title of the frame, makes its size the same as the screen size,
	 * makes the frame maximized, sets border layout to the frame, and Sets the
	 * operation that will happen when the user initiates a "close" on this frame to
	 * be exit on close
	 */
	private void interfaceSettings(String title) {
		setTitle(title);
		setSize(new Dimension(screenWidth, screenHeight));
		setExtendedState(MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				closeApp();
			}
		});
	}

	/**
	 * Sets the copyright label of this class, sets its background color, aligns it
	 * to center, adds it to the border layout to the south, and sets the icon for
	 * it
	 * 
	 * @param copyright
	 * @param icon
	 */
	public void setCopyright(JLabel copyright, Icon icon, String year) {
		this.copyright = copyright;

		JLabel lableOfIcon = new JLabel();
		lableOfIcon.setIcon(icon);

		JPanel panelInsideSouth = new JPanel();
		panelInsideSouthSettings(panelInsideSouth);

		panelInsideSouth.add(this.copyright);
		panelInsideSouth.add(lableOfIcon);
		panelInsideSouth.add(new JLabel(year));
		add(panelInsideSouth, BorderLayout.SOUTH);
	}

	private void panelInsideSouthSettings(JPanel panelInsideSouth) {
		panelInsideSouth.setOpaque(true);
		panelInsideSouth.setBackground(Color.ORANGE);
		panelInsideSouth.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
	}

	/**
	 * Creates a scaled version icon at the specified width and height
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return icon
	 */
	public Icon pathToIcon(String path, int width, int height) {
		URL resource = MainInterface.class.getClassLoader().getResource(path);
		ImageIcon imageIcon = new ImageIcon(resource);
		Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(image);
		return icon;
	}

	/**
	 * Sets the InterfaceMenuBar as menu bar to the main interface and registers the
	 * items of the menu to ActionListener interface.
	 * 
	 * @param interfaceMenuBar
	 */
	public void setInterfaceMenuBar(InterfaceMenuBar interfaceMenuBar) {
		this.interfaceMenuBar = interfaceMenuBar;

		// Open menu item
		this.interfaceMenuBar.getFileMenuItems()[0].addActionListener((ActionEvent e) -> {
			loadFileIntoTable();
		});

		// Save menu item
		this.interfaceMenuBar.getFileMenuItems()[1].addActionListener((ActionEvent e) -> {
			saveTableInfoInFile();
		});

		// Close menu item
		this.interfaceMenuBar.getFileMenuItems()[2].addActionListener((ActionEvent e) -> {
			closeApp();
		});

		// Edit menu item
		this.interfaceMenuBar.getEditMenuItems()[0].addActionListener((ActionEvent e) -> {
			transferRowInfoToForm(this.studentsTablePanel.getTable().getSelectedRow());
		});

		// Update menu item
		this.interfaceMenuBar.getEditMenuItems()[1].addActionListener((ActionEvent e) -> {
			updateFunctionality();
		});

		// Insert menu item
		this.interfaceMenuBar.getEditMenuItems()[2].addActionListener((ActionEvent e) -> {
			insertFunctionality();
		});

		// Delete menu item
		this.interfaceMenuBar.getEditMenuItems()[3].addActionListener((ActionEvent e) -> {
			if (this.studentsTablePanel.getTable().getSelectedRow() != -1) {
				if (confirm())
					this.studentsTablePanel.deleteRow(this.studentsTablePanel.getTable().getSelectedRow());
			} else {
				selectRowFirst();
			}
		});

		setJMenuBar(this.interfaceMenuBar); // sets the interfaceMenuBar class which is JMenuBar as JMenuBar of this
											// class
	}

	public void setSelectedRowToEdit(int selectedRowToEdit) {
		this.selectedRowToEdit = selectedRowToEdit;
	}

	/**
	 * Sets the studentInfoForm class which is a panel and property of this class to
	 * the west of the interface. registers a listener to the buttons of the form
	 * 
	 * @param studentInfoForm
	 */
	public void setStudentInfoForm(StudentInfoForm studentInfoForm) {
		this.studentInfoForm = studentInfoForm;

		// Insert Button
		studentInfoForm.getInsertButton().addActionListener((ActionEvent e) -> {
			insertFunctionality();
		});

		// Update Button
		this.studentInfoForm.getUpdateButton().addActionListener((ActionEvent e) -> {
			updateFunctionality();
		});

		// Delete Button
		this.studentInfoForm.getDeleteButton().addActionListener((ActionEvent e) -> {
			if (getStudentsTable().getTable().getSelectedRow() != -1) { // if there is a selected row in the table
				if (confirm()) {
					studentsTablePanel.deleteRow(getStudentsTable().getTable().getSelectedRow());
				}
			} else {
				selectRowFirst();
			}
		});
		add(this.studentInfoForm, BorderLayout.WEST); // adding the studentInfoForm panel to the west of the interface
														// panel of border layout
	}

	/**
	 * gets the StudentsTablePanel as parameter and assigns it to the class property
	 * of type StudentsTablePanel, registers the right click pop up menu items to
	 * ActionListener as the right click class of type JPopupMenu is a property in
	 * the StudentsTablePanel class. Finally, put the StudentsTablePanel in panel to
	 * be added to the center of the interface panel
	 * 
	 * @param studentsTable
	 */
	public void setStudentsTable(StudentsTablePanel studentsTable) {
		this.studentsTablePanel = studentsTable;

		// Edit menu item in right click
		this.studentsTablePanel.getRightClick().getEdit().addActionListener((ActionEvent e) -> {
			transferRowInfoToForm(this.studentsTablePanel.getTable().getSelectedRow());
		});

		// Delete menu item in right click
		this.studentsTablePanel.getRightClick().getDelete().addActionListener((ActionEvent e) -> {
			if (confirm()) {
				this.studentsTablePanel.deleteRow(this.studentsTablePanel.getTable().getSelectedRow());
			}
		});
		JPanel containsTable = new JPanel(new BorderLayout());
		containsTable.add(new JPanel(), BorderLayout.NORTH);
		containsTable.add(new JPanel(), BorderLayout.EAST);
		containsTable.add(studentsTable, BorderLayout.CENTER);
		containsTable.add(new JPanel(), BorderLayout.WEST);
		containsTable.add(new JPanel(), BorderLayout.SOUTH);
		add(containsTable, BorderLayout.CENTER);
	}

	/**
	 * helper method that gets the selected row form the table, puts the information
	 * of that row in the form to be edited
	 * 
	 * @param selectedRow
	 */
	private void transferRowInfoToForm(int selectedRow) {
		if (selectedRow != -1) {
			getStudentInfoForm().getFirstName()
					.setText(this.studentsTablePanel.getRows()[selectedRow][0].split(" ")[0]);
			getStudentInfoForm().getMiddleName()
					.setText(this.studentsTablePanel.getRows()[selectedRow][0].split(" ")[1]);
			getStudentInfoForm().getLastName().setText(this.studentsTablePanel.getRows()[selectedRow][0].split(" ")[2]);
			getStudentInfoForm().getEmail().setText(this.studentsTablePanel.getRows()[selectedRow][1]);
			getStudentInfoForm().getAddress().setText(this.studentsTablePanel.getRows()[selectedRow][2]);
			getStudentInfoForm().getPhone().setText(this.studentsTablePanel.getRows()[selectedRow][3]);
			setSelectedRowToEdit(selectedRow);
		} else {
			selectRowFirst();
		}
	}

	/**
	 * 
	 * @param studentInfo
	 * @return true if the row passed as parameter isExists in the table. Otherwise,
	 *         false
	 */
	private boolean isExists(String[] studentInfo) {
		boolean isExists = false;
		for (int i = 0; i < studentsTablePanel.getTable().getRowCount(); i++) {
			if (Arrays.equals(studentInfo, studentsTablePanel.getRows()[i])) {
				isExists = true;
			}
		}
		return isExists;
	}

	/**
	 * 
	 */
	private void loadFileIntoTable() {
		JFileChooser jFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); // only text files
		fileChooserSettings(jFileChooser, "Open File to Load into Table", filter);
		int response = jFileChooser.showOpenDialog(this);

		if (jFileChooser != null && response == JFileChooser.APPROVE_OPTION) {
			try {
				File fileToRead = new File(jFileChooser.getSelectedFile().getAbsolutePath()); // according to the path,
																								// name, and extension
																								// of that file chosen
				Scanner myReader = new Scanner(fileToRead);
				studentsTablePanel.deleteAllRows();
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					String[] row = data.split(",");
					studentsTablePanel.insertNewRow(row, studentsTablePanel.getTable().getRowCount()); // inserting to
																										// the last of
																										// the table for
																										// each loop
				}

				myReader.close();
			} catch (FileNotFoundException ex) {
				System.out.println("An error occurred.");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private void saveTableInfoInFile() {
		JFileChooser jFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fileChooserSettings(jFileChooser, "Saving File", filter);
		int response = jFileChooser.showSaveDialog(this);

		File file = jFileChooser.getSelectedFile();
		if (file != null && response == JFileChooser.APPROVE_OPTION) {
			try {
				file = new File(file.getAbsolutePath()); // new file according to the path, name, extention that the
															// user specified
				FileWriter myWriter;
				if (file.createNewFile()) {
					myWriter = new FileWriter(file.getAbsolutePath()); // if the file is new
				} else { // if the user chose an existed file
					Object[] options = { "Replace", "Append" };

					// to alert the user that the file has previous data
					int result = JOptionPane.showOptionDialog(null,
							"Do you want to replace it with the old data in the file, or append it?", "Warning",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					if (result == 0) { // if the user chose replace option to remove the previous data
						myWriter = new FileWriter(file.getAbsolutePath());
					} else {// if the user chose append option to append the data to the previous data
						myWriter = new FileWriter(file.getAbsolutePath(), true); // to append the text as the file is
						// exists
					}

				}
				String[][] rows = studentsTablePanel.getRows();
				writeInFile(myWriter, rows);

			} catch (IOException ex) {
				System.out.println("An error occurred.");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * helper method to write in a specific file specified by the FileWriter
	 * parameter
	 * 
	 * @param myWriter
	 * @param rows
	 */
	private void writeInFile(FileWriter myWriter, String[][] rows) {
		// TODO Auto-generated method stub
		try {
			String newLine = System.getProperty("line.separator");
			for (int i = 0; i < rows.length; i++) {
				for (int j = 0; j < 4; j++) {
					myWriter.write(rows[i][j]);
					if (j != 3) {
						myWriter.write(',');
					}
				}
				myWriter.write(newLine);
			}
			myWriter.close();
			JOptionPane.showMessageDialog(this, "Successfully saved to the file.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * setDialogTitle, setFileSelectionMode, and setFileFilter to the fileChooser
	 * passed as parameter
	 * 
	 * @param fileChooser
	 * @param title
	 * @param filter
	 */
	private void fileChooserSettings(JFileChooser fileChooser, String title, FileNameExtensionFilter filter) {
		fileChooser.setDialogTitle(title);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(filter);
	}

	/**
	 * If the action is "Insert", then insertNewRow() will be called if the row is
	 * not existed in the table and if all the fields in the form are filled out. If
	 * the action is "Update", updateRow() will be called to just update the row if
	 * the conditions are met. Finally, reset fields of the form and put the focus
	 * to the first field in the form
	 * 
	 * @param action
	 */
	private void procedureForInsertOrUpdate(String action) {
		String[] studentInfo = studentInfoForm.getStudentInfo();
		if (isExists(studentInfo) == false) {
			if (studentInfo[0].length() > 0 && studentInfo[1].length() > 0 && studentInfo[2].length() > 0
					&& studentInfo[3].length() > 0) {

				String[] row = { studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3] };
				if (action == "Insert") {
					studentsTablePanel.insertNewRow(row, studentsTablePanel.getTable().getRowCount());
				} else if (action == "Update") {
					studentsTablePanel.updateRow(row, getSelectedRowToEdit());
				}

				studentInfoForm.resetFields();
				studentInfoForm.getFirstName().requestFocus();

			} else {
				fillAllFields();
			}
		} else {
			isAlreadyExists();
		}
	}

	/**
	 * calls procedureForInsertOrUpdate() method to insert new row in the table
	 */
	private void insertFunctionality() {
		procedureForInsertOrUpdate("Insert");
	}

	/**
	 * calls procedureForInsertOrUpdate() method to Update a row in the table if a
	 * row is selected
	 */
	private void updateFunctionality() {
		if (getSelectedRowToEdit() != -1) {
			procedureForInsertOrUpdate("Update");
		} else {
			selectRowFirst();
		}
	}

	/**
	 * "Are you sure that you want to delete it?"
	 * 
	 * @return true if YES_OPTION.
	 */
	private boolean confirm() {
		int answer = JOptionPane.showConfirmDialog(studentInfoForm, "Are you sure that you want to delete it?",
				"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * To dispose the application if YES_OPTION is confirmed
	 */
	private void closeApp() {
		// TODO Auto-generated method stub
		int response = JOptionPane.showConfirmDialog(MainInterface.this, "Are you sure you want to close the program",
				"Closing Program", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			dispose();
		} else {
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}

	/**
	 * MessageDialog: "Please, select row first!", "Editing Row"
	 */
	private void selectRowFirst() {
		JOptionPane.showMessageDialog(getStudentInfoForm(), "Please, select row first!", "Editing Row",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * MessageDialog: "Fill out all the fields!", "Error"
	 */
	private void fillAllFields() {
		JOptionPane.showMessageDialog(getStudentInfoForm(), "Fill out all the fields!", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * MessageDialog: "This student is already exists!", "Feedback"
	 */
	private void isAlreadyExists() {
		JOptionPane.showMessageDialog(getStudentInfoForm(), "This student is already exists!", "Feedback",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public JLabel getCopyright() {
		return copyright;
	}

	public int getSelectedRowToEdit() {
		return selectedRowToEdit;
	}

	public InterfaceMenuBar getInterfaceMenuBar() {
		return interfaceMenuBar;
	}

	public StudentsTablePanel getStudentsTable() {
		return studentsTablePanel;
	}

	public StudentInfoForm getStudentInfoForm() {
		return studentInfoForm;
	}

	/**
	 * Main Method to be called First.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new MainInterface();
	}

}
