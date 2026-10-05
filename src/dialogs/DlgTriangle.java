package dialogs;

import shapes.Triangle;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;

public class DlgTriangle extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel mainPanel;
	private JTextField txtXcoordinate;
	private JTextField txtYcoordinate;
	private JTextField txtBaseLength;
	private JTextField txtHeightLength;
	private JLabel lblXcoordinate;
	private JLabel lblYcoordinate;
	private JLabel lblBaseLength;
	private JLabel lblHeightLength;
	private int xCoordinate;
	private int yCoordinate;
	private int baseLength;
	private int heightLength;
	private Color edgeColorOfTriangle;
	private Color interiorColorOfTriangle;
	private Color edgeColor;
	private Color interiorColor;
	private boolean confirmed;
	private JButton btnEdgeColor;
	private JButton btnInteriorColor;
	private int drawBase;
	private int drawHeight;

	public static void main(String[] arrayOfStrings) {
		try {
			DlgTriangle dialog = new DlgTriangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public DlgTriangle() {
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setModal(true);
		setResizable(false);
		setTitle("Triangle values");
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
			lblBaseLength = new JLabel("Base length");
			GridBagConstraints gbc_lblWidthLength = new GridBagConstraints();
			gbc_lblWidthLength.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidthLength.gridx = 2;
			gbc_lblWidthLength.gridy = 7;
			mainPanel.add(lblBaseLength, gbc_lblWidthLength);

		}
		{
			txtBaseLength = new JTextField();
			lblBaseLength.setLabelFor(txtBaseLength);
			GridBagConstraints gbc_txtWidthLength = new GridBagConstraints();
			gbc_txtWidthLength.insets = new Insets(0, 0, 5, 5);
			gbc_txtWidthLength.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWidthLength.gridx = 7;
			gbc_txtWidthLength.gridy = 7;
			mainPanel.add(txtBaseLength, gbc_txtWidthLength);
			txtBaseLength.setColumns(10);
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
			lblHeightLength.setLabelFor(txtBaseLength);
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
				edgeColor = JColorChooser.showDialog(null, "Colors pallete", edgeColorOfTriangle);
				if (edgeColor != null) {
					if (edgeColor.equals(Color.WHITE))
						JOptionPane.showMessageDialog(null, "Background is white :D");
					else {
						edgeColorOfTriangle = edgeColor;
						btnEdgeColor.setBackground(edgeColorOfTriangle);
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
				interiorColor = JColorChooser.showDialog(null, "Colors pallete", interiorColorOfTriangle);
				if (interiorColor != null) {
					interiorColorOfTriangle = interiorColor;
					if (interiorColorOfTriangle.equals(Color.BLACK))
						btnInteriorColor.setForeground(Color.WHITE);
					else if (interiorColorOfTriangle.equals(Color.WHITE))
						btnInteriorColor.setForeground(Color.BLACK);
					btnInteriorColor.setBackground(interiorColorOfTriangle);
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
								|| txtHeightLength.getText().isEmpty() || txtBaseLength.getText().isEmpty())
							JOptionPane.showMessageDialog(getParent(), "Values cannot be empty!", "Error",
									JOptionPane.ERROR_MESSAGE);
						else {
							try {
								xCoordinate = Integer.parseInt(txtXcoordinate.getText());
								yCoordinate = Integer.parseInt(txtYcoordinate.getText());
								baseLength = Integer.parseInt(txtBaseLength.getText());
								heightLength = Integer.parseInt(txtHeightLength.getText());

								if (xCoordinate <= 0 || yCoordinate <= 0 || heightLength <= 0 || baseLength <= 0)
									JOptionPane.showMessageDialog(getParent(),
											"X and Y coordinates and  width and height of triangle must be positive numbers!",
											"Error", JOptionPane.ERROR_MESSAGE);
								else if (baseLength + xCoordinate > drawBase
										|| yCoordinate - heightLength > drawHeight || yCoordinate - heightLength <= 0)
									JOptionPane.showMessageDialog(null, "The triangle goes out of drawing!");
								else {
									confirmed = true;
									setVisible(false);
									dispose();
								}
							} catch (NumberFormatException exception) {
								JOptionPane.showMessageDialog(getParent(),
										"X, Y, width and height of triangle must be whole numbers!", "Error",
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

	public void write(int xClick, int yClick, int drawBase, int drawHeight) {
		txtXcoordinate.setText(String.valueOf(xClick));
		txtXcoordinate.setEnabled(false);
		txtYcoordinate.setText(String.valueOf(yClick));
		txtYcoordinate.setEnabled(false);
		this.drawBase = drawBase;
		this.drawHeight = drawHeight;
	}

	public void fillUp(Triangle triangle, int drawBase, int drawHeight) {

		txtXcoordinate.setText(String.valueOf(triangle.getLeftPoint().getXcoordinate()));
		txtYcoordinate.setText(String.valueOf(triangle.getLeftPoint().getYcoordinate()));
		txtBaseLength.setText(String.valueOf(triangle.getBase()));
		txtHeightLength.setText(String.valueOf(triangle.getHeight()));
		edgeColorOfTriangle = triangle.getColor();
		interiorColorOfTriangle = triangle.getInteriorColor();
		if (interiorColorOfTriangle.equals(Color.BLACK))
			btnInteriorColor.setForeground(Color.WHITE);
		else if (interiorColorOfTriangle.equals(Color.WHITE))
			btnInteriorColor.setForeground(Color.BLACK);
		btnEdgeColor.setBackground(edgeColorOfTriangle);
		btnInteriorColor.setBackground(interiorColorOfTriangle);
		this.drawBase = drawBase;
		this.drawHeight = drawHeight;
	}

	public void deleteButtons() {
		btnEdgeColor.setVisible(false);
		btnInteriorColor.setVisible(false);
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public int getXcoordinate() {
		return xCoordinate;
	}

	public int getYcoordinate() {
		return yCoordinate;
	}

	public int getBaseLength() {
		return baseLength;
	}

	public int getHeightLength() {
		return heightLength;
	}

	public Color getEdgeColor() {
		return edgeColorOfTriangle;
	}

	public Color getInteriorColor() {
		return interiorColorOfTriangle;
	}

}
