
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * It is JPopupMenu that has two items: Edit and Delete
 * 
 * @author Abdulrahman
 *
 */
public class RightClick extends JPopupMenu {
	private JMenuItem edit;
	private JMenuItem delete;

	public RightClick() {
		setEdit(new JMenuItem("Edit"));
		setDelete(new JMenuItem("Delete"));
	}

	public JMenuItem getEdit() {
		return edit;
	}

	public void setEdit(JMenuItem edit) {
		this.edit = edit;
		add(this.edit);
	}

	public JMenuItem getDelete() {
		return delete;
	}

	public void setDelete(JMenuItem delete) {
		this.delete = delete;
		add(this.delete);
	}

}
