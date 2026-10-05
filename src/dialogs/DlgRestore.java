package dialogs;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import shapes.Shape;

public class DlgRestore extends JDialog{

	
	private static final long serialVersionUID = 1L;
	private final JPanel mainPanel;
	
	public static void main(String[] arrayOfStrings) {
		try {
			DlgRestore dialog = new DlgRestore();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	public DlgRestore()
	{
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 440, 320);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	
	public void fillUp(Shape shape)
	{
		
	}

}

