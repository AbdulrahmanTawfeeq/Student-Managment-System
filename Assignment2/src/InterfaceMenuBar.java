
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * extends JMenuBar, the new object of it will create menu bar with file, edit,
 * and about menus with their items
 * 
 * @author AbdulrahmanTawffeq
 *
 */
public class InterfaceMenuBar extends JMenuBar {
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu aboutMenu;

	private JMenuItem[] fileMenuItems = new JMenuItem[3];
	private JMenuItem[] editMenuItems = new JMenuItem[4];
	private JMenuItem[] aboutMenuItems = new JMenuItem[3];

	private int menuItemWidth = (int) (MainInterface.screenWidth * 7.88) / 100;
	private int menuItemHeight = (int) (MainInterface.screenWidth * 2.4) / 100;

	public InterfaceMenuBar() {
		setMenuBar();
		setAboutMenu(new JMenu("About"));
		setEditMenu(new JMenu("Edit"));
		setFileMenu(new JMenu("File"));

	}

	private void setMenuBar() {
		setOpaque(true);
		setBackground(Color.WHITE);
		setBorder((BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK)));
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(Box.createHorizontalGlue());
	}

	public JMenu getFileMenu() {
		return fileMenu;
	}

	/**
	 * sets the file menu with the open, save, and close menu items. Gives settings
	 * to these menu items, adds all of them to the file menu, and finally adds the
	 * file menu to the menu bar
	 * 
	 * @param fileMenu
	 */
	public void setFileMenu(JMenu fileMenu) {
		this.fileMenu = fileMenu;
		setFileMenuItems(new JMenuItem[3]);

		add(this.fileMenu);
	}

	public JMenu getEditMenu() {
		return editMenu;
	}

	/**
	 * sets the edit menu with the Edit, Update, Insert, and Delete menu items.
	 * Gives settings to these menu items, adds all of them to the Edit menu, and
	 * finally adds the Edit menu to the menu bar
	 * 
	 * @param editMenu
	 */
	public void setEditMenu(JMenu editMenu) {
		this.editMenu = editMenu;
		setEditMenuItems(new JMenuItem[4]);

		add(this.editMenu);

	}

	public JMenu getAboutMenu() {
		return aboutMenu;
	}

	/**
	 * sets the About menu with the Contact, About Me, and Help menu items. Gives
	 * settings to these menu items, adds all of them to the About menu, and finally
	 * adds the About menu to the menu bar
	 * 
	 * @param aboutMenu
	 */
	public void setAboutMenu(JMenu aboutMenu) {
		this.aboutMenu = aboutMenu;
		setAboutMenuItems(new JMenuItem[3]);

		add(this.aboutMenu);
	}

	/**
	 * sets a name to each menu item with the same as its current text name, and
	 * sets specific size to each menu item.
	 * 
	 * @param menuItems
	 */
	public void MenuItemsSettings(JMenuItem[] menuItems, int width, int height) {
		this.fileMenuItems = menuItems;
		for (JMenuItem menuItem : menuItems) {
			menuItem.setName(menuItem.getText());
			menuItem.setPreferredSize(new Dimension(width, height));
			menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	public JMenuItem[] getFileMenuItems() {
		return fileMenuItems;
	}

	public void setFileMenuItems(JMenuItem[] fileMenuItems) {
		this.fileMenuItems = fileMenuItems;
		this.fileMenuItems[0] = new JMenuItem("Open");
		this.fileMenuItems[1] = new JMenuItem("Save");
		this.fileMenuItems[2] = new JMenuItem("Close");
		MenuItemsSettings(this.fileMenuItems, menuItemWidth, menuItemHeight);
		for (JMenuItem fileMenuItem : this.fileMenuItems) {
			this.fileMenu.add(fileMenuItem);
		}

	}

	public JMenuItem[] getEditMenuItems() {
		return editMenuItems;
	}

	public void setEditMenuItems(JMenuItem[] editMenuItems) {
		this.editMenuItems = editMenuItems;
		this.editMenuItems[0] = new JMenuItem("Edit");
		this.editMenuItems[1] = new JMenuItem("Update");
		this.editMenuItems[2] = new JMenuItem("Insert");
		this.editMenuItems[3] = new JMenuItem("Delete");
		MenuItemsSettings(this.editMenuItems, menuItemWidth, menuItemHeight);
		for (JMenuItem editMenuItem : this.editMenuItems) {
			this.editMenu.add(editMenuItem);
		}

	}

	public JMenuItem[] getAboutMenuItems() {
		return aboutMenuItems;
	}

	public void setAboutMenuItems(JMenuItem[] aboutMenuItems) {
		this.aboutMenuItems = aboutMenuItems;
		this.aboutMenuItems[0] = new JMenuItem("Contact");
		this.aboutMenuItems[1] = new JMenuItem("About Me");
		this.aboutMenuItems[2] = new JMenuItem("Help");
		MenuItemsSettings(this.aboutMenuItems, menuItemWidth, menuItemHeight);
		for (JMenuItem editMenuItem : this.aboutMenuItems) {
			this.aboutMenu.add(editMenuItem);
		}

	}

}
