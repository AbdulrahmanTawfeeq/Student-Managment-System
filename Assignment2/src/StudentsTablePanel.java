
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ComponentSampleModel;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Abdulrahman
 *
 */
public class StudentsTablePanel extends JPanel {
	private String[] columnNames = { "Name", "Email", "Address", "Phone" };
	private String[][] rows = new String[0][4];
	private JTable table;
	private JScrollPane jScrollPane;
	private RightClick rightClick;

	public StudentsTablePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setTable(new JTable(rows, columnNames));
		setRightClick(new RightClick());
		setjScrollPane(new JScrollPane(table));
	}

	/**
	 * 
	 * @param row
	 * @param atRow
	 */
	public void insertNewRow(String[] row, int atRow) {
		String[][] oldRows = getRows();
		String[][] newRows = new String[oldRows.length + 1][4]; // more than the old table of 1 row

		for (int i = 0; i < oldRows.length; i++) { // set the rows to the newRows array of new length
			for (int j = 0; j < 4; j++) {
				newRows[i][j] = oldRows[i][j];
			}
		}
		setRows(newRows); // table with last row as empty
		updateRow(row, atRow); // calling for this method to update the last row which is atRow with row array
								// values

	}

	/**
	 * Sets the same rows to the table again excluding the row to be deleted that
	 * gotten as parameter atRow
	 * 
	 * @param atRow
	 */
	public void deleteRow(int atRow) {
		String[][] oldRows = getRows();
		String[][] newRows = new String[oldRows.length - 1][4]; // less than the old table of 1 row

		int ii = 0;
		for (int i = 0; i < newRows.length; i++) { // according to the new table length
			if (i != atRow) { // before reaching the row to be deleted
				for (int j = 0; j < 4; j++) {
					newRows[i][j] = oldRows[ii][j];
				}
			} else { // After reaching the row to be deleted, a jump will be happened by ii++; to
						// avoid adding it to the new one
				ii++;
				for (int j = 0; j < 4; j++) {
					newRows[i][j] = oldRows[ii][j];
				}
			}
			ii++;
		}
		setRows(newRows);
		tableRevalidation(getRows(), columnNames);
	}

	/**
	 * Calls setRows() to set String[][] newRows which is empty and then to call for
	 * the tableRevalidation method to revalidated the panel of the table
	 */
	public void deleteAllRows() {
		String[][] newRows = new String[0][4];
		setRows(newRows);
		tableRevalidation(getRows(), columnNames);
	}

	/**
	 * Gets the row by calling getRows() and by using atRow parameter, updates the
	 * value of each cell according to the row array, and finally calls
	 * tableRevalidation method to revalidated the panel of the table
	 * 
	 * @param row
	 * @param atRow
	 */
	public void updateRow(String[] row, int atRow) {
		try {
			getRows()[atRow][0] = row[0];
			getRows()[atRow][1] = row[1];
			getRows()[atRow][2] = row[2];
			getRows()[atRow][3] = row[3];
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		tableRevalidation(getRows(), columnNames);
	}

	/**
	 * removes all components from the table panel, sets new table with the rows and
	 * columns parameters, sets new JScrollPane after adding the new table to it,
	 * adds the new JScrollPane to the table panel, and finally revalidate the table
	 * panel
	 * 
	 * @param rows
	 * @param columns
	 */
	public void tableRevalidation(String[][] rows, String[] columns) {
		this.removeAll();
		setTable(new JTable(rows, columns));
		setjScrollPane(new JScrollPane(table));
		this.add(getjScrollPane());
		this.revalidate();
	}

	/**
	 * Assigns the table to the property of the class, gives styles to the header of
	 * the table, gives styles to the table itself, and registers the table to the
	 * MouseListerner
	 * 
	 * @param table
	 */
	@SuppressWarnings("serial")
	public void setTable(JTable table) {
		this.table = table;
		this.table.getTableHeader().setBackground(new Color(100, 160, 100));
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.getTableHeader().setPreferredSize(new Dimension(this.table.getTableHeader().getSize().width, 25));
		this.table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

		this.table.setRowHeight(25);
		this.table.setRowSelectionAllowed(true);
		this.table.setAlignmentX(CENTER_ALIGNMENT);
		this.table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		this.table.addMouseListener(new TableMouseEvents()); // registers the table to the MouseListerner

		// This makes the cell not to be editable
		this.table.setModel(new DefaultTableModel(getRows(), getColumnNames()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public void setRows(String[][] rows) {
		this.rows = rows;
	}

	/**
	 * Assigns the jScrollPane to the property of the class, gives it property, and
	 * add it to the StudentsTablePanel
	 * 
	 * @param jScrollPane
	 */
	public void setjScrollPane(JScrollPane jScrollPane) {
		this.jScrollPane = jScrollPane;
		this.jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(this.jScrollPane);
	}

	/**
	 * Assigns the right click to the property of the class, then adds it to the
	 * table
	 * 
	 * @param rightClick
	 */
	public void setRightClick(RightClick rightClick) {
		this.rightClick = rightClick;
		getTable().add(this.rightClick);
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public String[][] getRows() {
		return rows;
	}

	public JTable getTable() {
		return table;
	}

	public JScrollPane getjScrollPane() {
		return jScrollPane;
	}

	public RightClick getRightClick() {
		return rightClick;
	}

	/**
	 * private class that extends MouseAdapter and overrides mouseClicked method
	 * 
	 * @author Abdulrahman
	 *
	 */
	private class TableMouseEvents extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() == getTable()) {
				getRightClick().show(getTable(), e.getX(), e.getY()); // to show the popUpMenu on right click on the
																		// table
				int row = getTable().rowAtPoint(new Point(e.getX(), e.getY())); // to get the row where the right click
																				// triggers
				if (row >= 0 && row < getTable().getRowCount()) {
					getTable().setRowSelectionInterval(row, row); // to select the row if it is in the table
				}
			} else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == getTable()) {
				getTable().clearSelection(); // to clear the selection on double left mouse click on the row
			}
		}

	}

}
