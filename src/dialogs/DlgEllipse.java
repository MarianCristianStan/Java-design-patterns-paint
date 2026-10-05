package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import shapes.Ellipse;

public class DlgEllipse extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel mainPanel;
	private JTextField txtXcoordinate;
	private JTextField txtYcoordinate;
	private JTextField txtWidthLength;
	private JTextField txtHeightLength;
	private JLabel lblXcoordinate;
	private JLabel lblYcoordinate;
	private JLabel lblWidthLength;
	private JLabel lblHeightLength;
	private int xCoordinate;
	private int yCoordinate;
	private int widthLength;
	private int heightLength;
	private Color edgeColorOfEllipse;
	private Color interiorColorOfEllipse;
	private Color edgeColor;
	private Color interiorColor;
	private boolean confirmed;
	private JButton btnEdgeColor;
	private JButton btnInteriorColor;
	private int drawWidth;
	private int drawHeight;

	public static void main(String[] arrayOfStrings) {
		try {
			DlgEllipse dialog = new DlgEllipse();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public DlgEllipse() {
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setModal(true);
		setResizable(false);
		setTitle("Ellipse values");
		setBounds(100, 100, 440, 320);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_mainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_mainPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };

		mainPanel.setLayout(gbl_mainPanel);
		{
			lblXcoordinate = new JLabel("X coordinate");
			GridBagConstraints gbc_lblXcoordinate = new GridBagConstraints();
			gbc_lblXcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblXcoordinate.gridx = 2;
			gbc_lblXcoordinate.gridy = 3;
			mainPanel.add(lblXcoordinate, gbc_lblXcoordinate);
		}
		{
			txtXcoordinate = new JTextField();
			lblXcoordinate.setLabelFor(txtXcoordinate);
			GridBagConstraints gbc_txtXcoordinate = new GridBagConstraints();
			gbc_txtXcoordinate.anchor = GridBagConstraints.NORTH;
			gbc_txtXcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtXcoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXcoordinate.gridx = 7;
			gbc_txtXcoordinate.gridy = 3;
			mainPanel.add(txtXcoordinate, gbc_txtXcoordinate);
			txtXcoordinate.setColumns(10);

		}
		{
			lblYcoordinate = new JLabel("Y coordinate");
			GridBagConstraints gbc_lblYcoordinate = new GridBagConstraints();
			gbc_lblYcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblYcoordinate.gridx = 2;
			gbc_lblYcoordinate.gridy = 5;
			mainPanel.add(lblYcoordinate, gbc_lblYcoordinate);

		}
		{
			txtYcoordinate = new JTextField();
			lblYcoordinate.setLabelFor(txtYcoordinate);
			GridBagConstraints gbc_txtYcoordinate = new GridBagConstraints();
			gbc_txtYcoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtYcoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYcoordinate.gridx = 7;
			gbc_txtYcoordinate.gridy = 5;
			mainPanel.add(txtYcoordinate, gbc_txtYcoordinate);
			txtYcoordinate.setColumns(10);
		}
		{
			lblWidthLength = new JLabel("Width length");
			GridBagConstraints gbc_lblWidthLength = new GridBagConstraints();
			gbc_lblWidthLength.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidthLength.gridx = 2;
			gbc_lblWidthLength.gridy = 7;
			mainPanel.add(lblWidthLength, gbc_lblWidthLength);

		}
		{
			txtWidthLength = new JTextField();
			lblWidthLength.setLabelFor(txtWidthLength);
			GridBagConstraints gbc_txtWidthLength = new GridBagConstraints();
			gbc_txtWidthLength.insets = new Insets(0, 0, 5, 5);
			gbc_txtWidthLength.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWidthLength.gridx = 7;
			gbc_txtWidthLength.gridy = 7;
			mainPanel.add(txtWidthLength, gbc_txtWidthLength);
			txtWidthLength.setColumns(10);
		}
		{
			lblHeightLength = new JLabel("Height length");
			GridBagConstraints gbc_lblHeightLength = new GridBagConstraints();
			gbc_lblHeightLength.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeightLength.gridx = 2;
			gbc_lblHeightLength.gridy = 9;
			mainPanel.add(lblHeightLength, gbc_lblHeightLength);

		}
		{
			txtHeightLength = new JTextField();
			lblHeightLength.setLabelFor(txtWidthLength);
			GridBagConstraints gbc_txtHeightLength = new GridBagConstraints();
			gbc_txtHeightLength.insets = new Insets(0, 0, 5, 5);
			gbc_txtHeightLength.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHeightLength.gridx = 7;
			gbc_txtHeightLength.gridy = 9;
			mainPanel.add(txtHeightLength, gbc_txtHeightLength);
			txtHeightLength.setColumns(10);
		}
		GridBagConstraints gbc_btnEdgeColor = new GridBagConstraints();
		gbc_btnEdgeColor.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdgeColor.gridx = 2;
		gbc_btnEdgeColor.gridy = 11;

		btnEdgeColor = new JButton("Choose edge color");
		btnEdgeColor.setForeground(Color.WHITE);
		btnEdgeColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEdgeColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				edgeColor = JColorChooser.showDialog(null, "Colors pallete", getEdgeColor());
				if (edgeColor != null) {
					if (edgeColor.equals(Color.WHITE))
						JOptionPane.showMessageDialog(null, "Background is white :D");
					else {
						setEdgeColor(edgeColor);
						btnEdgeColor.setBackground(getEdgeColor());
					}
				}
			}

		});

		mainPanel.add(btnEdgeColor, gbc_btnEdgeColor);
		GridBagConstraints gbc_btnInteriorColor = new GridBagConstraints();
		gbc_btnInteriorColor.insets = new Insets(0, 0, 5, 5);
		gbc_btnInteriorColor.gridx = 7;
		gbc_btnInteriorColor.gridy = 11;

		btnInteriorColor = new JButton("Choose interior color");
		btnInteriorColor.setForeground(Color.BLACK);
		btnInteriorColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInteriorColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				interiorColor = JColorChooser.showDialog(null, "Colors pallete", getInteriorColor());
				if (interiorColor != null) {
					setInteriorColor(interiorColor);
					if (getInteriorColor().equals(Color.BLACK))
						btnInteriorColor.setForeground(Color.WHITE);
					else if (getInteriorColor().equals(Color.WHITE))
						btnInteriorColor.setForeground(Color.BLACK);
					btnInteriorColor.setBackground(getInteriorColor());
				}
			}
		});

		mainPanel.add(btnInteriorColor, gbc_btnInteriorColor);
		{
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
			{
				JButton btnConfirm = new JButton("Confirm");
				btnConfirm.setBackground(Color.GREEN);
				btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent click) {
						if (txtXcoordinate.getText().isEmpty() || txtYcoordinate.getText().isEmpty()
								|| txtHeightLength.getText().isEmpty() || txtWidthLength.getText().isEmpty())
							JOptionPane.showMessageDialog(getParent(), "Values cannot be empty!", "Error",
									JOptionPane.ERROR_MESSAGE);
						else {
							try {
								setxCoordinate(Integer.parseInt(txtXcoordinate.getText()));
								setyCoordinate(Integer.parseInt(txtYcoordinate.getText()));
								setWidthLength(Integer.parseInt(txtWidthLength.getText()));
								setHeightLength(Integer.parseInt(txtHeightLength.getText()));

								if (getxCoordinate() <= 0 || getyCoordinate() <= 0 || getHeightLength() <= 0
										|| getWidthLength() <= 0)
									JOptionPane.showMessageDialog(getParent(),
											"X and Y coordinates and  width and height of ellipse must be positive numbers!",
											"Error", JOptionPane.ERROR_MESSAGE);
								else if (getWidthLength() + getxCoordinate() > drawWidth
										|| getHeightLength() + getyCoordinate() > drawHeight
										|| getyCoordinate() - getHeightLength() <= 0
										|| getxCoordinate() - getWidthLength() < 0)
									JOptionPane.showMessageDialog(null, "The ellipse goes out of drawing!");
								else {
									confirmed = true;
									setVisible(false);
									dispose();
								}
							} catch (NumberFormatException exception) {
								JOptionPane.showMessageDialog(getParent(),
										"X, Y, width and height of ellpise must be whole numbers!", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				btnConfirm.setActionCommand("OK");
				buttonsPanel.add(btnConfirm);
				getRootPane().setDefaultButton(btnConfirm);
			}

			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setBackground(Color.RED);
				btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnCancel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent click) {
						setVisible(false);
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonsPanel.add(btnCancel);
			}
		}
	}

	public void write(int xClick, int yClick, int drawWidth, int drawHeight) {
		txtXcoordinate.setText(String.valueOf(xClick));
		txtXcoordinate.setEnabled(false);
		txtYcoordinate.setText(String.valueOf(yClick));
		txtYcoordinate.setEnabled(false);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}

	public void deleteButtons() {
		btnEdgeColor.setVisible(false);
		btnInteriorColor.setVisible(false);
	}

	public void fillUp(Ellipse ellipse, int drawWidth, int drawHeight) {
		txtXcoordinate.setText(String.valueOf(ellipse.getCenter().getXcoordinate()));
		txtYcoordinate.setText(String.valueOf(ellipse.getCenter().getYcoordinate()));
		txtWidthLength.setText(String.valueOf(ellipse.getWidth()));
		txtHeightLength.setText(String.valueOf(ellipse.getHeight()));
		edgeColorOfEllipse = ellipse.getColor();
		interiorColorOfEllipse = ellipse.getInteriorColor();
		if (interiorColorOfEllipse.equals(Color.BLACK))
			btnInteriorColor.setForeground(Color.WHITE);
		else if (interiorColorOfEllipse.equals(Color.WHITE))
			btnInteriorColor.setForeground(Color.BLACK);
		btnEdgeColor.setBackground(edgeColorOfEllipse);
		btnInteriorColor.setBackground(interiorColorOfEllipse);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}

	public boolean isConfirmed() {
        return confirmed;
    }
	
	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public int getWidthLength() {
		return widthLength;
	}

	public void setWidthLength(int widthLength) {
		this.widthLength = widthLength;
	}

	public int getHeightLength() {
		return heightLength;
	}

	public void setHeightLength(int heightLength) {
		this.heightLength = heightLength;
	}

	public Color getEdgeColor() {
		return edgeColorOfEllipse;
	}

	public void setEdgeColor(Color edgeColorOfEllipse) {
		this.edgeColorOfEllipse = edgeColorOfEllipse;
	}

	public Color getInteriorColor() {
		return interiorColorOfEllipse;
	}

	public void setInteriorColor(Color interiorColorOfEllipse) {
		this.interiorColorOfEllipse = interiorColorOfEllipse;
	}
}
