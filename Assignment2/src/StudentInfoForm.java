
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * JPanel, creates a form for filling a student information
 * 
 * @author Abdulrahman
 *
 */
public class StudentInfoForm extends JPanel {
	private JTextField firstName;
	private JTextField middleName;
	private JTextField lastName;
	private JTextField email;
	private JTextField address;
	private JTextField phone;
	private int textFieldColumns = 12;
	private JPanel panelContainsFields;

	private static int row = 0; // static variable will be used when adding the labels and text fields to the
								// gridBagLayout

	private JButton insertButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JPanel panelContainsButtons;

	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * Once a new object is created, a panel that contains a form of student
	 * information with buttons will be ready to be added in any other panel or
	 * frame
	 */
	public StudentInfoForm() {
		formPanelSettings();

		setPanelContainsFields(new JPanel(new GridBagLayout()));
		setFirstName(new JTextField(textFieldColumns));
		setMiddleName(new JTextField(textFieldColumns));
		setLastName(new JTextField(textFieldColumns));
		setEmail(new JTextField(textFieldColumns));
		setAddress(new JTextField(textFieldColumns));
		setPhone(new JTextField(textFieldColumns));

		setPanelContainsButtons(new JPanel());
		setInsertButton(new JButton("Insert"));
		setUpdateButton(new JButton("Update"));
		setDeleteButton(new JButton("Delete"));
	}

	/**
	 * Sets the properties of the form panel like: background, border, and size
	 */
	private void formPanelSettings() {
		setOpaque(true);
		style(this, Color.WHITE, null, null, BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK), null,
				new Dimension((int) (MainInterface.screenWidth * 25) / 100, getSize().height));
	}

	/**
	 * private helper method to set background, foreground, cursor, border, font,
	 * and preferredSize for the component after getting them as parameters if they
	 * are not null
	 * 
	 * @param component
	 * @param background
	 * @param foreground
	 * @param cursor
	 * @param border
	 * @param font
	 * @param preferredSize
	 */
	private void style(JComponent component, Color background, Color foreground, Cursor cursor, Border border,
			Font font, Dimension preferredSize) {
		component.setBackground(background);
		component.setBorder(border);
		if (foreground != null) {
			component.setForeground(foreground);
		}

		if (cursor != null) {
			component.setCursor(cursor);
		}

		if (font != null) {
			component.setFont(font);
		}

		if (preferredSize != null) {
			component.setPreferredSize(preferredSize);
		}
	}

	/**
	 * 
	 * @param label
	 * @param textField
	 */
	private void addToPanelOfFields(JLabel label, JTextField textField) {
		gbc.gridx = 0; // column zero, fixed
		gbc.gridy = row;
		gbc.insets = new Insets(0, 0, 2, 0); // This field specifies the external padding of the component, the minimum
												// amount of space between the component and the edges of its display
												// area. top left bottom right
		// this panel has layout of type gridBagLayout
		getPanelContainsFields().add(label, gbc);
		gbc.gridx = 1; // column one, fixed
		gbc.gridy = row;
		getPanelContainsFields().add(textField, gbc);
		row++; // each time the method called, the label and text field will be added in
				// specific row then the row static value to be increased for the next insertion
	}

	/**
	 * Sets the text field in terms of its cursor, alignment, background,
	 * foreground, font, and border
	 * 
	 * @param textField
	 */
	private void fieldProperties(JTextField textField) {
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		style(textField, new Color(150, 150, 150), Color.WHITE, new Cursor(Cursor.TEXT_CURSOR), null,
				new Font(Font.SANS_SERIF, Font.PLAIN, (int) (MainInterface.screenWidth * 1.4) / 100), null);
	}

	/**
	 * Sets the button in terms of its cursor, alignment, background, foreground,
	 * font, border, and size. As well as, adding mouse listener to the button when
	 * mouse enters or exits
	 * 
	 * @param jButton
	 * @param color
	 */
	private void buttonProperties(JButton jButton, Color color) {
		jButton.setFocusable(false);
		style(jButton, color, Color.WHITE, new Cursor(Cursor.HAND_CURSOR), null,
				new Font(Font.SANS_SERIF, Font.PLAIN, (int) (MainInterface.screenWidth * 1.5) / 100), new Dimension(
						(int) (MainInterface.screenWidth * 6.3) / 100, (int) (MainInterface.screenWidth * 3.15) / 100));
		MouseAdapterEx listener = new MouseAdapterEx(jButton, Color.BLACK);
		jButton.addMouseListener(listener);
	}

	/**
	 * Returns a new Label with a specific text, alignment, and size
	 * 
	 * @param text
	 * @return
	 */
	private JLabel makeLable(String text) {
		JLabel newLable = new JLabel(text, SwingConstants.LEFT);
		newLable.setPreferredSize(new Dimension((int) (MainInterface.screenWidth * 7.85) / 100,
				(int) (MainInterface.screenWidth * 1.2) / 100)); // according to screen size, specific percentage of it
		return newLable;
	}

	public JTextField getFirstName() {
		return firstName;
	}

	/**
	 * Assigns the parameter to firstName class property, sets its properties, and
	 * adds it to the panel that contains the text fields
	 * 
	 * @param firstName
	 */
	public void setFirstName(JTextField firstName) {
		this.firstName = firstName;
		fieldProperties(this.firstName);
		addToPanelOfFields(makeLable("First Name: "), this.firstName);
	}

	public JTextField getMiddleName() {
		return middleName;
	}

	/**
	 * Assigns the parameter to middleName class property, sets its properties, and
	 * adds it to the panel that contains the text fields
	 * 
	 * @param middleName
	 */
	public void setMiddleName(JTextField middleName) {
		this.middleName = middleName;
		fieldProperties(this.middleName);
		addToPanelOfFields(makeLable("Middle Name: "), this.middleName);
	}

	public JTextField getLastName() {
		return lastName;
	}

	/**
	 * Assigns the parameter to lastName class property, sets its properties, and
	 * adds it to the panel that contains the text fields
	 * 
	 * @param lastName
	 */
	public void setLastName(JTextField lastName) {
		this.lastName = lastName;
		fieldProperties(this.lastName);
		addToPanelOfFields(makeLable("Last Name: "), this.lastName);
	}

	public JTextField getEmail() {
		return email;
	}

	/**
	 * Assigns the parameter to email class property, sets its properties, and adds
	 * it to the panel that contains the text fields
	 * 
	 * @param email
	 */
	public void setEmail(JTextField email) {
		this.email = email;
		fieldProperties(this.email);
		addToPanelOfFields(makeLable("E-Mail: "), this.email);
	}

	public JTextField getAddress() {
		return address;
	}

	/**
	 * Assigns the parameter to address class property, sets its properties, and
	 * adds it to the panel that contains the text fields
	 * 
	 * @param address
	 */
	public void setAddress(JTextField address) {
		this.address = address;
		fieldProperties(this.address);
		addToPanelOfFields(makeLable("Address: "), this.address);
	}

	public JTextField getPhone() {
		return phone;
	}

	/**
	 * Assigns the parameter to phone class property, sets its properties, and adds
	 * it to the panel that contains the text fields
	 * 
	 * @param phone
	 */
	public void setPhone(JTextField phone) {
		this.phone = phone;
		fieldProperties(this.phone);
		addToPanelOfFields(makeLable("Phone: "), this.phone);
	}

	public JButton getInsertButton() {
		return insertButton;
	}

	/**
	 * Assigns the parameter to insertButton class property, sets its properties,
	 * and adds it to the panel that contains the buttons
	 * 
	 * @param insertButton
	 */
	public void setInsertButton(JButton insertButton) {
		this.insertButton = insertButton;
		buttonProperties(this.insertButton, new Color(32, 165, 27));
		getPanelContainsButtons().add(this.insertButton);
	}

	public JButton getUpdateButton() {
		return updateButton;
	}

	/**
	 * Assigns the parameter to updateButton class property, sets its properties,
	 * and adds it to the panel that contains the buttons
	 * 
	 * @param updateButton
	 */
	public void setUpdateButton(JButton updateButton) {
		this.updateButton = updateButton;
		buttonProperties(this.updateButton, Color.ORANGE);
		getPanelContainsButtons()
				.add(Box.createRigidArea(new Dimension((int) (MainInterface.screenWidth * 0.8) / 100, 0))); // space
																											// between
																											// the
																											// buttons
		getPanelContainsButtons().add(this.updateButton);
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	/**
	 * Assigns the parameter to deleteButton class property, sets its properties,
	 * and adds it to the panel that contains the buttons
	 * 
	 * @param deleteButton
	 */
	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
		buttonProperties(this.deleteButton, new Color(239, 86, 46));
		getPanelContainsButtons()
				.add(Box.createRigidArea(new Dimension((int) (MainInterface.screenWidth * 0.8) / 100, 0))); // space
																											// between
																											// the
																											// buttons
		getPanelContainsButtons().add(this.deleteButton);
	}

	public JPanel getPanelContainsFields() {
		return panelContainsFields;
	}

	/**
	 * Assigns the parameter to panelContainsFields class property, sets its
	 * properties, sets GridBagLayout to the panel, and adds it to the panel of
	 * StudentInfoForm class.
	 * 
	 * @param panelContainsFields
	 */
	public void setPanelContainsFields(JPanel panelContainsFields) {
		this.panelContainsFields = panelContainsFields;
		this.panelContainsFields.setBackground(null);
		this.panelContainsFields.setLayout(new GridBagLayout());
		add(panelContainsFields);
	}

	public JPanel getPanelContainsButtons() {
		return panelContainsButtons;
	}

	/**
	 * Assigns the parameter to panelContainsButtons class property, sets its
	 * properties, and adds it to the panel of StudentInfoForm class.
	 * 
	 * @param panelContainsButtons
	 */
	public void setPanelContainsButtons(JPanel panelContainsButtons) {
		this.panelContainsButtons = panelContainsButtons;
		this.panelContainsButtons.setBackground(null);
		add(panelContainsButtons);
	}

	/**
	 * returns Student Info from the fields of the form. returns firstName,
	 * middleName, lastName, email, address, and phone as array of string
	 * 
	 * @return studentInfo as array
	 */
	public String[] getStudentInfo() {
		String[] studentInfo = { firstName.getText() + " " + middleName.getText() + " " + lastName.getText(),
				email.getText(), address.getText(), phone.getText() };
		return studentInfo;
	}

	/**
	 * Resets all the fields' text in the form to null
	 */
	public void resetFields() {
		getFirstName().setText(null);
		getMiddleName().setText(null);
		getLastName().setText(null);
		getEmail().setText(null);
		getAddress().setText(null);
		getPhone().setText(null);
	}

	/**
	 * extends MouseAdapter and overrides mouseEntered and mouseExited methods only
	 * 
	 * @author ATJ
	 *
	 */
	private class MouseAdapterEx extends MouseAdapter {
		JComponent component;
		Color borderColor;

		/**
		 * The bottom border of the component will be set to 1px and its color will be
		 * changed according to the borderColor when the mouseEntered. Returns to
		 * original when mouseExited
		 * 
		 * @param component
		 * @param borderColor
		 */
		public MouseAdapterEx(JComponent component, Color borderColor) {
			this.component = component;
			this.borderColor = borderColor;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			this.component.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, borderColor));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			this.component.setBorder(null);
		}
	}

}
