package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import commands.CmdAddShape;
import commands.CmdBringToBack;
import commands.CmdBringToFront;
import commands.CmdRemoveShape;
import commands.CmdSelectShape;
import commands.CmdToBack;
import commands.CmdToFront;
import commands.CmdUpdateCircle;
import commands.CmdUpdateEllipse;
import commands.CmdUpdateLine;
import commands.CmdUpdatePoint;
import commands.CmdUpdateRectangle;
import commands.CmdUpdateSquare;
import commands.CmdUpdateTriangle;
import commands.Command;
import decorator.ShapeDecorator;
import dialogs.DlgCircle;
import dialogs.DlgEllipse;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import dialogs.DlgSquare;
import dialogs.DlgTriangle;
import frame.DrawingFrame;
import interpreter.AreaInterpreter;
import memento.CareTaker;
import memento.MementoShape;
import model.DrawingModel;
import restoreTemplate.RestoreTriangle;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Square;
import shapes.Triangle;
import strategy.FileDraw;
import strategy.FileLog;
import strategy.FileManager;
import strategy.FilePicture;
import view.DrawingView;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;

/**
 * <h2>Class that represent controller in MVC architectural pattern.</h2>
 * 
 * Called by the {@link DrawingView} when user click something and act depending
 * on the command (usually update {@link DrawingModel}).
 */

public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private Point initialPointOfLine;
	private Color edgeColor = Color.BLACK;
	private Color interiorColor = Color.WHITE;
	private Color choosenEdgeColor;
	private Color choosenInteriorColor;
	private PropertyChangeSupport propertyChangeSupport;
	private int counterOfSelectedShapes = 0;
	private FileManager fileManager;
	private DefaultListModel<String> log;
	private Stack<String> undoCommandsLog;
	private Stack<Command> commands;
	private Stack<Command> undoCommands;
	private boolean decoratorActive = false;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		initialPointOfLine = null;
		propertyChangeSupport = new PropertyChangeSupport(this);
		log = frame.getList();
		commands = new Stack<>();
		undoCommands = new Stack<>();
		undoCommandsLog = new Stack<String>();
	}

	/**
	 * <h3>Add listener that will listen (observe) to the changes in this
	 * class.</h3>
	 * 
	 */
	public void addPropertyChangedListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * User clicked to choose edge color, show {@inheritDoc JColorChooser}.
	 * 
	 */
	public void btnShapeDecoratorClicked(MouseEvent click) {
		if (decoratorActive) {
			ImageIcon icon = new ImageIcon(DrawingController.class.getResource("/icons/denied.png"));
			frame.getBtnShapeDecorator().setBackground(Color.LIGHT_GRAY);
			JOptionPane.showMessageDialog(null, "Decorator system is now inactive", "Decorator",
					JOptionPane.INFORMATION_MESSAGE, icon);
			this.decoratorActive = false;
		} else {
			ImageIcon icon = new ImageIcon(DrawingController.class.getResource("/icons/approve.png"));
			frame.getBtnShapeDecorator().setBackground(Color.GREEN);
			JOptionPane.showMessageDialog(null, "Decorator system is now active", "Decorator",
					JOptionPane.INFORMATION_MESSAGE, icon);
			this.decoratorActive = true;

		}
	}

	public void AreaShape(Shape shape) {
		if (shape instanceof Line) {
			JOptionPane.showMessageDialog(null, "The selected shape is a Line and doesn't have area", "Area",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else if (shape instanceof Point) {
			JOptionPane.showMessageDialog(null, "The selected shape is a Point and doesn't have area", "Area",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		AreaInterpreter area = new AreaInterpreter(shape.toString());
		double ShapeArea = area.evaluate();
		JOptionPane.showMessageDialog(null, "Area of the shape is " + ShapeArea + " mm^2", "Area",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public Color btnEdgeColorClicked() {
		choosenEdgeColor = JColorChooser.showDialog(null, "Colors pallete", edgeColor);
		if (choosenEdgeColor != null) {
			if (choosenEdgeColor.equals(Color.WHITE)) {
				JOptionPane.showMessageDialog(null, "Background is white :D");
				return null;
			}
			edgeColor = choosenEdgeColor;
			return edgeColor;
		}
		return choosenEdgeColor;
	}

	/**
	 * <h3>User clicked to choose area color, show {@inheritDoc JColorChooser}.</h3>
	 * 
	 */
	public Color btnInteriorColorClicked() {
		choosenInteriorColor = JColorChooser.showDialog(null, "Colors pallete", interiorColor);
		if (choosenInteriorColor != null) {
			interiorColor = choosenInteriorColor;
			return interiorColor;
		}
		return choosenEdgeColor;
	}

	/**
	 * <h3>Called when user click to add new {@link Point}.</h3>
	 * 
	 */
	public void btnAddPointClicked(MouseEvent click) {
		Point point = new Point(click.getX(), click.getY(), edgeColor);
		if (decoratorActive)
			new ShapeDecorator(point);
		executeCommand(new CmdAddShape(point, getModel()));
		getLog().addElement("Added->" + point.toString());
		point.initCareTaker();
		point.createSavepoint();
	}

	/**
	 * <h3>Called when user click to add new {@link Line}.</h3>
	 * 
	 */
	public void btnAddLineClicked(MouseEvent click) {
		if (initialPointOfLine == null)
			initialPointOfLine = new Point(click.getX(), click.getY(), edgeColor);
		else {
			Line line = new Line(initialPointOfLine, new Point(click.getX(), click.getY()), edgeColor);
			if (decoratorActive)
				new ShapeDecorator(line);
			executeCommand(new CmdAddShape(line, getModel()));
			getLog().addElement("Added->" + line.toString());
			line.initCareTaker();
			line.createSavepoint();
			initialPointOfLine = null;
		}
	}

	/**
	 * <h3>Called when user click to add new {@link Square}.</h3>
	 *
	 */

	public void btnAddTriangleClicked(MouseEvent click) {
		DlgTriangle dlgTriangle = new DlgTriangle();
		dlgTriangle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgTriangle.deleteButtons();
		dlgTriangle.setVisible(true);
		if (dlgTriangle.isConfirmed()) {
			Triangle triangle = new Triangle(new Point(click.getX(), click.getY()), dlgTriangle.getBaseLength(),
					dlgTriangle.getHeightLength(), edgeColor, interiorColor);
			if (decoratorActive)
				new ShapeDecorator(triangle);

			executeCommand(new CmdAddShape(triangle, getModel()));
			getLog().addElement("Added->" + triangle.toString());
			triangle.initCareTaker();
			triangle.createSavepoint();
		}

	}

	public void btnAddEllipseClicked(MouseEvent click) {
		DlgEllipse dlgEllipse = new DlgEllipse();
		dlgEllipse.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgEllipse.deleteButtons();
		dlgEllipse.setVisible(true);
		if (dlgEllipse.isConfirmed()) {
			Ellipse ellipse = new Ellipse(new Point(click.getX(), click.getY()), dlgEllipse.getWidthLength(),
					dlgEllipse.getHeightLength(), edgeColor, interiorColor);
			if (decoratorActive)
				new ShapeDecorator(ellipse);
			executeCommand(new CmdAddShape(ellipse, getModel()));
			getLog().addElement("Added->" + ellipse.toString());
			ellipse.initCareTaker();
			ellipse.createSavepoint();
		}

	}

	public void btnAddSquareClicked(MouseEvent click) {
		DlgSquare dlgSquare = new DlgSquare();
		dlgSquare.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgSquare.deleteButtons();
		dlgSquare.setVisible(true);
		if (dlgSquare.isConfirmed()) {
			Square square = new Square(new Point(click.getX(), click.getY()), dlgSquare.getSideLength(), edgeColor,
					interiorColor);
			if (decoratorActive)
				new ShapeDecorator(square);
			executeCommand(new CmdAddShape(square, getModel()));
			getLog().addElement("Added->" + square.toString());
			square.initCareTaker();
			square.createSavepoint();
		}
	}

	/**
	 * <h3>Called when user click to add new {@link Rectangle}.</h3>
	 * 
	 * 
	 */
	public void btnAddRectangleClicked(MouseEvent click) {
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.deleteButtons();
		dlgRectangle.setVisible(true);
		if (dlgRectangle.isConfirmed()) {
			Rectangle rectangle = new Rectangle(new Point(click.getX(), click.getY()), dlgRectangle.getRectangleWidth(),
					dlgRectangle.getRectangleHeight(), edgeColor, interiorColor);
			if (decoratorActive)
				new ShapeDecorator(rectangle);
			executeCommand(new CmdAddShape(rectangle, getModel()));
			getLog().addElement("Added->" + rectangle.toString());

			rectangle.initCareTaker();
			rectangle.createSavepoint();
		}
	}

	/**
	 * <h3>Called when user click to add new {@link Circle}.</h3>
	 * 
	 * 
	 */
	public void btnAddCircleClicked(MouseEvent click) {
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.deleteButtons();
		dlgCircle.setVisible(true);
		if (dlgCircle.isConfirmed()) {
			Circle circle = new Circle(new Point(click.getX(), click.getY()), dlgCircle.getRadiusLength(), edgeColor,
					interiorColor);
			if (decoratorActive)
				new ShapeDecorator(circle);
			executeCommand(new CmdAddShape(circle, getModel()));
			getLog().addElement("Added->" + circle.toString());
			circle.initCareTaker();
			circle.createSavepoint();
		}
	}

	/**
	 * <h3>Called when user select some shape on a draw.</h3>
	 * 
	 * 
	 */
	public void btnSelectShapeClicked(MouseEvent click) {
		Iterator<Shape> iterator = getModel().getAll().iterator();
		ArrayList<Integer> tempListOfShapes = new ArrayList<>();

		while (iterator.hasNext()) {
			Shape shapeForSelection = iterator.next();
			if (shapeForSelection.containsClick(click.getX(), click.getY()))
				tempListOfShapes.add(getModel().getIndexOf(shapeForSelection));

		}

		if (!tempListOfShapes.isEmpty()) {
			Shape shape = getModel().getByIndex(Collections.max(tempListOfShapes));

			if (!shape.isSelected()) {
				++counterOfSelectedShapes;
				executeCommand(new CmdSelectShape(shape, true));
				getLog().addElement("Selected->" + shape.toString());
			} else {
				--counterOfSelectedShapes;
				executeCommand(new CmdSelectShape(shape, false));
				getLog().addElement("Unselected->" + shape.toString());
			}

			handleSelectButtons();
		}

		frame.getView().repaint();
	}

	/**
	 * Count how many shapes are selected. This method is called by undo command,
	 * redo command and log parser.
	 * 
	 */
	public void handleSelect(String s, String command) {
		if (command.equals("redo")) {
			if (s.equals("Selected"))
				++counterOfSelectedShapes;
			else
				--counterOfSelectedShapes;
			handleSelectButtons();
		} else if (command.equals("undo")) {
			if (s.equals("Selected"))
				--counterOfSelectedShapes;
			else
				++counterOfSelectedShapes;
			handleSelectButtons();
		} else if (command.equals("parser")) {
			if (s.equals("Selected"))
				++counterOfSelectedShapes;
			else
				--counterOfSelectedShapes;

		}

	}

	/**
	 * Handle buttons state depend on number of selected shapes.
	 */
	public void handleSelectButtons() {
		if (counterOfSelectedShapes == 0)
			propertyChangeSupport.firePropertyChange("shape unselected", false, true);
		else if (counterOfSelectedShapes == 1) {
			propertyChangeSupport.firePropertyChange("update/move turn on", false, true);
			propertyChangeSupport.firePropertyChange("shape selected", false, true);
		} else if (counterOfSelectedShapes > 1)
			propertyChangeSupport.firePropertyChange("update/move turn off", false, true);
	}

	/**
	 * <h3>Method that is called when user choose to update some shape.</h3>
	 * 
	 * Determines instance of selected shape and call appropriate method forwarding
	 * casted type of shape.
	 */
	public void updateShapeClicked() {
		Shape shape = getSelectedShape();
		if (shape instanceof Point)
			btnUpdatePointClicked((Point) shape);
		else if (shape instanceof Line)
			btnUpdateLineClicked((Line) shape);
		else if (shape instanceof Rectangle)
			btnUpdateRectangleClicked((Rectangle) shape);
		else if (shape instanceof Square)
			btnUpdateSquareClicked((Square) shape);
		else if (shape instanceof Circle)
			btnUpdateCircleClicked((Circle) shape);
		else if (shape instanceof Triangle)
			btnUpdateTriangleClicked((Triangle) shape);
		else if (shape instanceof Ellipse)
			btnUpdateEllipseClicked((Ellipse) shape);
	}

	public void restoreVersionOfShape() {

		Shape shape = getSelectedShape();
		if (shape instanceof Triangle) {
//			CareTaker careTaker = ((Triangle) shape).getCareTaker();
//			Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
//			String[] optionsToChooseVersion = new String[savepoints.size()];
//			int i = 0;
//			for (var keys : savepoints.keySet()) {
//				optionsToChooseVersion[i] = keys;
//				i++;
//			}
//			String getVersionSelected = (String) JOptionPane.showInputDialog(null,
//					"What version do you like to restore?", "Choose version", JOptionPane.QUESTION_MESSAGE, null,
//					optionsToChooseVersion, optionsToChooseVersion);
//			System.out.println("Version:  " + getVersionSelected);
//
//			if (getVersionSelected != null) {
//
//				// Triangle restoreTriangle = ((Triangle)shape).getInitialTriangle();
//				Triangle restoreTriangle = ((Triangle) shape).getTriangleBySavepoint(getVersionSelected);
//				log.addElement("Updated->" + ((Triangle) shape).toString() + "->" + restoreTriangle.toString());
//				executeCommand(new CmdUpdateTriangle(((Triangle) shape), restoreTriangle));
//
//			}
			new RestoreTriangle().restoreShape(this,shape);
			
			
		} else if (shape instanceof Ellipse) {
			CareTaker careTaker = ((Ellipse) shape).getCareTaker();
			Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
			String[] optionsToChooseVersion = new String[savepoints.size()];
			int i = 0;
			for (var keys : savepoints.keySet()) {
				optionsToChooseVersion[i] = keys;
				i++;
			}
			for(int j=0;j<i-1;j++)
				for(int r=j;r<i;r++)
				{
					if(optionsToChooseVersion[j].compareTo(optionsToChooseVersion[r]) == 1)
					{
						String aux=optionsToChooseVersion[j];
						optionsToChooseVersion[j] = optionsToChooseVersion[r];
						optionsToChooseVersion[r] = aux;
					}
				}
			String getVersionSelected = (String) JOptionPane.showInputDialog(null,
					"What version do you like to restore?", "Choose version", JOptionPane.QUESTION_MESSAGE, null,
					optionsToChooseVersion, optionsToChooseVersion);
			System.out.println("Version:  " + getVersionSelected);
			

			if (getVersionSelected != null) {

				Ellipse restoreEllipse = ((Ellipse) shape).getEllipseBySavepoint(getVersionSelected);
				getLog().addElement("Updated->" + ((Ellipse) shape).toString() + "->" + restoreEllipse.toString());
				executeCommand(new CmdUpdateEllipse(((Ellipse) shape), restoreEllipse));

			}

		} else if (shape instanceof Circle) {
			CareTaker careTaker = ((Circle) shape).getCareTaker();
			Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
			String[] optionsToChooseVersion = new String[savepoints.size()];
			int i = 0;
			for (var keys : savepoints.keySet()) {
				optionsToChooseVersion[i] = keys;
				i++;
			}
			for(int j=0;j<i-1;j++)
				for(int r=j;r<i;r++)
				{
					if(optionsToChooseVersion[j].compareTo(optionsToChooseVersion[r]) == 1)
					{
						String aux=optionsToChooseVersion[j];
						optionsToChooseVersion[j] = optionsToChooseVersion[r];
						optionsToChooseVersion[r] = aux;
					}
				}
			String getVersionSelected = (String) JOptionPane.showInputDialog(null,
					"What version do you like to restore?", "Choose version", JOptionPane.QUESTION_MESSAGE, null,
					optionsToChooseVersion, optionsToChooseVersion);
			System.out.println("Version:  " + getVersionSelected);

			if (getVersionSelected != null) {

				Circle restoreCircle = ((Circle) shape).getCircleBySavepoint(getVersionSelected);
				getLog().addElement("Updated->" + ((Circle) shape).toString() + "->" + restoreCircle.toString());
				executeCommand(new CmdUpdateCircle(((Circle) shape), restoreCircle));

			}
		} else if (shape instanceof Rectangle) {
			CareTaker careTaker = ((Rectangle) shape).getCareTaker();
			Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
			String[] optionsToChooseVersion = new String[savepoints.size()];
			int i = 0;
			for (var keys : savepoints.keySet()) {
				optionsToChooseVersion[i] = keys;
				i++;
			}
			for(int j=0;j<i-1;j++)
				for(int r=j;r<i;r++)
				{
					if(optionsToChooseVersion[j].compareTo(optionsToChooseVersion[r]) == 1)
					{
						String aux=optionsToChooseVersion[j];
						optionsToChooseVersion[j] = optionsToChooseVersion[r];
						optionsToChooseVersion[r] = aux;
					}
				}
			String getVersionSelected = (String) JOptionPane.showInputDialog(null,
					"What version do you like to restore?", "Choose version", JOptionPane.QUESTION_MESSAGE, null,
					optionsToChooseVersion, optionsToChooseVersion);
			System.out.println("Version:  " + getVersionSelected);

			if (getVersionSelected != null) {

				Rectangle restoreRectangle = ((Rectangle) shape).getRectangleBySavepoint(getVersionSelected);
				getLog().addElement("Updated->" + ((Rectangle) shape).toString() + "->" + restoreRectangle.toString());
				executeCommand(new CmdUpdateRectangle(((Rectangle) shape), restoreRectangle));

			}
		} else if (shape instanceof Line) {
			CareTaker careTaker = ((Line) shape).getCareTaker();
			Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
			String[] optionsToChooseVersion = new String[savepoints.size()];
			int i = 0;
			for (var keys : savepoints.keySet()) {
				optionsToChooseVersion[i] = keys;
				i++;
			}
			for(int j=0;j<i-1;j++)
				for(int r=j;r<i;r++)
				{
					if(optionsToChooseVersion[j].compareTo(optionsToChooseVersion[r]) == 1)
					{
						String aux=optionsToChooseVersion[j];
						optionsToChooseVersion[j] = optionsToChooseVersion[r];
						optionsToChooseVersion[r] = aux;
					}
				}
			String getVersionSelected = (String) JOptionPane.showInputDialog(null,
					"What version do you like to restore?", "Choose version", JOptionPane.QUESTION_MESSAGE, null,
					optionsToChooseVersion, optionsToChooseVersion);
			System.out.println("Version:  " + getVersionSelected);

			if (getVersionSelected != null) {

				Line restoreLine = ((Line) shape).getLineBySavepoint(getVersionSelected);
				getLog().addElement("Updated->" + ((Line) shape).toString() + "->" + restoreLine.toString());
				executeCommand(new CmdUpdateLine(((Line) shape), restoreLine));

			}
		} else if (shape instanceof Point) {
			CareTaker careTaker = ((Point) shape).getCareTaker();
			Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
			String[] optionsToChooseVersion = new String[savepoints.size()];
			int i = 0;
			for (var keys : savepoints.keySet()) {
				optionsToChooseVersion[i] = keys;
				i++;
			}
			for(int j=0;j<i-1;j++)
				for(int r=j;r<i;r++)
				{
					if(optionsToChooseVersion[j].compareTo(optionsToChooseVersion[r]) == 1)
					{
						String aux=optionsToChooseVersion[j];
						optionsToChooseVersion[j] = optionsToChooseVersion[r];
						optionsToChooseVersion[r] = aux;
					}
				}
			String getVersionSelected = (String) JOptionPane.showInputDialog(null,
					"What version do you like to restore?", "Choose version", JOptionPane.QUESTION_MESSAGE, null,
					optionsToChooseVersion, optionsToChooseVersion);
			System.out.println("Version:  " + getVersionSelected);

			if (getVersionSelected != null) {

				Point restorePoint = ((Point) shape).getPointBySavepoint(getVersionSelected);
				getLog().addElement("Updated->" + ((Point) shape).toString() + "->" + restorePoint.toString());
				executeCommand(new CmdUpdatePoint(((Point) shape), restorePoint));

			}
		} else if (shape instanceof Square) {
			CareTaker careTaker = ((Square) shape).getCareTaker();
			Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
			String[] optionsToChooseVersion = new String[savepoints.size()];
			int i = 0;
			for (var keys : savepoints.keySet()) {
				optionsToChooseVersion[i] = keys;
				i++;
			}
			for(int j=0;j<i-1;j++)
				for(int r=j;r<i;r++)
				{
					if(optionsToChooseVersion[j].compareTo(optionsToChooseVersion[r]) == 1)
					{
						String aux=optionsToChooseVersion[j];
						optionsToChooseVersion[j] = optionsToChooseVersion[r];
						optionsToChooseVersion[r] = aux;
					}
				}
			String getVersionSelected = (String) JOptionPane.showInputDialog(null,
					"What version do you like to restore?", "Choose version", JOptionPane.QUESTION_MESSAGE, null,
					optionsToChooseVersion, optionsToChooseVersion);
			System.out.println("Version:  " + getVersionSelected);

			if (getVersionSelected != null) {

				Square restoreSquare = ((Square) shape).getSquareBySavepoint(getVersionSelected);
				getLog().addElement("Updated->" + ((Square) shape).toString() + "->" + restoreSquare.toString());
				executeCommand(new CmdUpdateSquare(((Square) shape), restoreSquare));

			}
		}

	}

	public void btnUpdateTriangleClicked(Triangle oldTriangle) {
		DlgTriangle dlgTriangle = new DlgTriangle();
		dlgTriangle.fillUp(oldTriangle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgTriangle.setVisible(true);
		if (dlgTriangle.isConfirmed()) {
			Triangle newTriangle = new Triangle(new Point(dlgTriangle.getXcoordinate(), dlgTriangle.getYcoordinate()),
					dlgTriangle.getBaseLength(), dlgTriangle.getHeightLength(), dlgTriangle.getEdgeColor(),
					dlgTriangle.getInteriorColor());

			getLog().addElement("Updated->" + oldTriangle.toString() + "->" + newTriangle.toString());
			executeCommand(new CmdUpdateTriangle(oldTriangle, newTriangle));
			oldTriangle.createSavepoint();

			// log.addElement("Updated->" + oldTriangle.toString() + "->" +
			// newTriangle.toString());

		}
	}

	public void btnUpdateEllipseClicked(Ellipse oldEllipse) {
		DlgEllipse dlgEllipse = new DlgEllipse();
		dlgEllipse.fillUp(oldEllipse, frame.getView().getWidth(), frame.getView().getHeight());
		dlgEllipse.setVisible(true);
		if (dlgEllipse.isConfirmed()) {
			Ellipse newEllipse = new Ellipse(new Point(dlgEllipse.getxCoordinate(), dlgEllipse.getyCoordinate()),
					dlgEllipse.getWidthLength(), dlgEllipse.getHeightLength(), dlgEllipse.getEdgeColor(),
					dlgEllipse.getInteriorColor());
			getLog().addElement("Updated->" + oldEllipse.toString() + "->" + newEllipse.toString());
			executeCommand(new CmdUpdateEllipse(oldEllipse, newEllipse));
			oldEllipse.createSavepoint();

			// log.addElement("Updated->" + oldEllipse.toString() + "->" +
			// newEllipse.toString());

		}
	}

	/**
	 * <h3>Method is called when user want to update some existing {@link Point} on
	 * draw.</h3>
	 */

	public void btnUpdatePointClicked(Point oldPoint) {
		DlgPoint dlgPoint = new DlgPoint();
		dlgPoint.write(oldPoint, frame.getView().getWidth(), frame.getView().getHeight());
		dlgPoint.setVisible(true);
		if (dlgPoint.isConfirmed()) {
			Point newPoint = new Point(dlgPoint.getXcoordinate(), dlgPoint.getYcoordinate(), dlgPoint.getColor());
			getLog().addElement("Updated->" + oldPoint.toString() + "->" + newPoint.toString());
			executeCommand(new CmdUpdatePoint(oldPoint, newPoint));
			// log.addElement("Updated->" + oldPoint.toString() + "->" +
			// newPoint.toString());
			oldPoint.createSavepoint();
		}
	}

	/**
	 * <h3>Method is called when user want to update some existing {@link Line} on
	 * draw.</h3>
	 */
	public void btnUpdateLineClicked(Line oldLine) {
		DlgLine dlgLine = new DlgLine();
		dlgLine.write(oldLine);
		dlgLine.setVisible(true);
		if (dlgLine.isConfirmed()) {
			Line newLine = new Line(new Point(dlgLine.getXcoordinateInitial(), dlgLine.getYcoordinateInitial()),
					new Point(dlgLine.getXcoordinateLast(), dlgLine.getYcoordinateLast()), dlgLine.getColor());
			getLog().addElement("Updated->" + oldLine.toString() + "->" + newLine.toString());
			executeCommand(new CmdUpdateLine(oldLine, newLine));
			// log.addElement("Updated->" + oldLine.toString() + "->" + newLine.toString());
			oldLine.createSavepoint();
		}
	}

	/**
	 * <h3>Method is called when user want to update some existing {@link Rectangle}
	 * on draw.</h3>
	 */
	public void btnUpdateRectangleClicked(Rectangle oldRectangle) {
		DlgRectangle dlgRectangle = new DlgRectangle();
		dlgRectangle.fillUp(oldRectangle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.setVisible(true);
		if (dlgRectangle.isConfirmed()) {
			Rectangle newRectangle = new Rectangle(
					new Point(dlgRectangle.getXcoordinate(), dlgRectangle.getYcoordinate()),
					dlgRectangle.getRectangleWidth(), dlgRectangle.getRectangleHeight(), dlgRectangle.getEdgeColor(),
					dlgRectangle.getInteriorColor());
			getLog().addElement("Updated->" + oldRectangle.toString() + "->" + newRectangle.toString());
			executeCommand(new CmdUpdateRectangle(oldRectangle, newRectangle));
			// log.addElement("Updated->" + oldRectangle.toString() + "->" +
			// newRectangle.toString());
			oldRectangle.createSavepoint();
		}
	}

	/**
	 * <h3>Method is called when user want to update some existing {@link Square} on
	 * draw.</h3>
	 * 
	 */
	public void btnUpdateSquareClicked(Square oldSquare) {
		DlgSquare dlgSquare = new DlgSquare();
		dlgSquare.fillUp(oldSquare, frame.getView().getWidth(), frame.getView().getHeight());
		dlgSquare.setVisible(true);
		if (dlgSquare.isConfirmed()) {
			Square newSquare = new Square(new Point(dlgSquare.getXcoordinate(), dlgSquare.getYcoordinate()),
					dlgSquare.getSideLength(), dlgSquare.getEdgeColor(), dlgSquare.getInteriorColor());
			getLog().addElement("Updated->" + oldSquare.toString() + "->" + newSquare.toString());
			executeCommand(new CmdUpdateSquare(oldSquare, newSquare));
			// log.addElement("Updated->" + oldSquare.toString() + "->" +
			// newSquare.toString());
			oldSquare.createSavepoint();
		}
	}

	/**
	 * <h3>Method is called when user want to update some existing {@link Circle} on
	 * a draw.</h3>
	 * 
	 */
	public void btnUpdateCircleClicked(Circle oldCircle) {
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.fillUp(oldCircle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.setVisible(true);
		if (dlgCircle.isConfirmed()) {
			Circle newCircle = new Circle(
					new Point(dlgCircle.getXcoordinateOfCenter(), dlgCircle.getYcoordinateOfCenter()),
					dlgCircle.getRadiusLength(), dlgCircle.getEdgeColor(), dlgCircle.getInteriorColor());
			getLog().addElement("Updated->" + oldCircle.toString() + "->" + newCircle.toString());
			executeCommand(new CmdUpdateCircle(oldCircle, newCircle));
			// log.addElement("Updated->" + oldCircle.toString() + "->" +
			// newCircle.toString());
			oldCircle.createSavepoint();
		}
	}

	/**
	 * <h3>Method that call command {@link CmdToFront} which move some shape one
	 * position forward in the list of shapes if shape is not already at last
	 * position.</h3>
	 */
	public void toFront() {
		Shape shape = getSelectedShape();
		if (getModel().getIndexOf(shape) == getModel().getAll().size() - 1)
			JOptionPane.showMessageDialog(null, "Shape is already on top!");
		else {
			executeCommand(new CmdToFront(getModel(), shape));
			getLog().addElement("Moved to front->" + shape.toString());
		}
	}

	/**
	 * <h3>Method that call command {@link CmdBringToFront} which bring some shape
	 * at the end of the list of shapes if shape is not already at last
	 * position.</h3>
	 */
	public void bringToFront() {
		Shape shape = getSelectedShape();
		if (getModel().getIndexOf(shape) == getModel().getAll().size() - 1)
			JOptionPane.showMessageDialog(null, "Shape is already on top!");
		else {
			executeCommand(new CmdBringToFront(getModel(), shape, getModel().getAll().size() - 1));
			getLog().addElement("Bringed to front->" + shape.toString());
		}
	}

	/**
	 * <h3>Method that call command {@link CmdToBack} which move some shape one
	 * position backward in the list of shapes if shape is not already at first
	 * position.</h3>
	 */
	public void toBack() {
		Shape shape = getSelectedShape();
		if (getModel().getIndexOf(shape) == 0)
			JOptionPane.showMessageDialog(null, "Shape is already on bottom!");
		else {
			executeCommand(new CmdToBack(getModel(), shape));
			getLog().addElement("Moved to back->" + shape.toString());
		}
	}

	/**
	 * <h3>Method that call command {@link CmdBringToBack} which bring some shape at
	 * the beginnig of the list of shapes if shape is not already at first
	 * position.</h3>
	 */
	public void bringToBack() {
		Shape shape = getSelectedShape();
		if (getModel().getIndexOf(shape) == 0)
			JOptionPane.showMessageDialog(null, "Shape is already on bottom!");
		else {
			executeCommand(new CmdBringToBack(getModel(), shape));
			getLog().addElement("Bringed to back->" + shape.toString());
		}
	}

	/**
	 * <h3>Method that returns currently selected shape.</h3>
	 * 
	 */
	public Shape getSelectedShape() {
		Iterator<Shape> iterator = getModel().getAll().iterator();
		while (iterator.hasNext()) {
			Shape shapeForModification = iterator.next();
			if (shapeForModification.isSelected())
				return shapeForModification;
		}
		return null;
	}

	/**
	 * <h3>Method is called when user want to delete some shape(s).</h3>
	 * 
	 */
	public void btnDeleteShapeClicked() {
		if (getModel().getAll().isEmpty() == false && this.counterOfSelectedShapes != 0)
			if (JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete selected shape?", "Warning!",
					JOptionPane.YES_NO_OPTION) == 0) {
				Iterator<Shape> it = getModel().getAll().iterator();
				ArrayList<Shape> shapesForDeletion = new ArrayList<Shape>();

				while (it.hasNext()) {
					Shape shape = it.next();
					if (shape.isSelected()) {
						shapesForDeletion.add(shape);
						counterOfSelectedShapes--;
						getLog().addElement("Deleted->" + shape.toString());
					}
				}

				executeCommand(new CmdRemoveShape(shapesForDeletion, getModel()));
				handleSelectButtons();
			}
	}

	public void btnAreaShapeClicked() {
		Shape shape = getSelectedShape();

		if (shape instanceof Line) {
			JOptionPane.showMessageDialog(null, "The selected shape is a Line and doesn't have area", "Area",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else if (shape instanceof Point) {
			JOptionPane.showMessageDialog(null, "The selected shape is a Point and doesn't have area", "Area",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		AreaInterpreter area = new AreaInterpreter(shape.toString());
		double ShapeArea = area.evaluate();
		JOptionPane.showMessageDialog(null, "Area of the shape is " + ShapeArea + " mm^2", "Area",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * <h3>Method that execute some command.</h3>
	 * 
	 * Fire changes from {@link DrawingModel} to Observer {@link DrawingFrame} that
	 * updates buttons.
	 *
	 */
	public void executeCommand(Command command) {
		command.execute();
		commands.push(command);
		if (!undoCommands.isEmpty()) {
			undoCommands.removeAllElements();
			propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		}

		if (getModel().getAll().isEmpty())
			propertyChangeSupport.firePropertyChange("shape don't exist", false, true);
		else if (getModel().getAll().size() == 1)
			propertyChangeSupport.firePropertyChange("shape exist", false, true);

		if (commands.isEmpty())
			propertyChangeSupport.firePropertyChange("draw is empty", false, true);
		else if (commands.size() == 1)
			propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
		frame.getView().repaint();

	}

	/**
	 * <h3>Method that unexecute (undo) some command.</h3>
	 * 
	 * Fire changes from {@link DrawingModel} to Observer {@link DrawingFrame} that
	 * updates buttons.
	 * 
	 */
	public void undo() {
		commands.peek().unexecute();
		if (commands.peek() instanceof CmdRemoveShape) {
			int i = ((CmdRemoveShape) commands.peek()).getSize();
			for (int j = 0; j < i; j++) {
				undoCommandsLog.add(getLog().remove(getLog().size() - 1));
				++counterOfSelectedShapes;
				handleSelectButtons();
			}

		} else if (commands.peek() instanceof CmdSelectShape) {

			handleSelect((getLog().get(getLog().size() - 1)).split("->")[0], "undo");
			undoCommandsLog.add(getLog().remove(getLog().size() - 1));
			handleSelectButtons();
		} else
			undoCommandsLog.add(getLog().remove(getLog().size() - 1));

		undoCommands.push(commands.pop());
		if (getLog().isEmpty())
			propertyChangeSupport.firePropertyChange("log turn off", false, true);
		if (undoCommands.size() == 1)
			propertyChangeSupport.firePropertyChange("redo turn on", false, true);
		if (commands.isEmpty())
			propertyChangeSupport.firePropertyChange("draw is empty", false, true);

		frame.getView().repaint();

	}

	/**
	 * <h3>Method that execute previously unexecuted command.</h3>
	 * 
	 * Fire changes from {@link DrawingModel} to Observer {@link DrawingFrame} that
	 * updates buttons. <br>
	 * 
	 */
	public void redo() {
		undoCommands.peek().execute();
		if (undoCommands.peek() instanceof CmdRemoveShape) {
			int i = ((CmdRemoveShape) undoCommands.peek()).getSize();
			for (int j = 0; j < i; j++) {
				getLog().addElement(undoCommandsLog.pop());
				--this.counterOfSelectedShapes;
				handleSelectButtons();
			}

		} else if (undoCommands.peek() instanceof CmdSelectShape) {
			getLog().addElement(undoCommandsLog.pop());
			handleSelect((getLog().get(getLog().size() - 1)).split("->")[0], "redo");
			handleSelectButtons();
		} else {
			getLog().addElement(undoCommandsLog.pop());
		}
		commands.push((undoCommands.pop()));
		if (undoCommands.isEmpty())
			propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		if (commands.size() == 1) {
			propertyChangeSupport.firePropertyChange("shape exist", false, true);
			propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
			propertyChangeSupport.firePropertyChange("log turn on", false, true);
		}

		frame.getView().repaint();
	}

	/**
	 * <h3>Method that is obligate for displaying {@ JFileChooser} for user to
	 * choose where to save draw as serialized file, log file with executed commands
	 * or picture (screenshot) of draw.</h3>
	 * 
	 */
	public void save() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.enableInputMethods(false);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setDialogTitle("Save");
		chooser.setAcceptAllFileFilterUsed(false);
		if (!getModel().getAll().isEmpty()) {
			chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
			chooser.setFileFilter(new FileNameExtensionFilter("Picture", "jpeg"));
		}
		if (!commands.isEmpty())
			chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (chooser.getFileFilter().getDescription().equals("Serialized draw"))
				fileManager = new FileManager(new FileDraw(getModel()));
			else if (chooser.getFileFilter().getDescription().equals("Commands log"))
				fileManager = new FileManager(new FileLog(frame, getModel(), this));
			else
				fileManager = new FileManager(new FilePicture(frame));
			fileManager.save(chooser.getSelectedFile());
		}
		chooser.setVisible(false);
	}

	/**
	 * <h3>Method that is obligate for displaying {@ JFileChooser} for user to
	 * choose file to open.</h3>
	 */
	public void open() {
		JFileChooser chooser = new JFileChooser();
		chooser.enableInputMethods(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
		chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			this.counterOfSelectedShapes = 0;
			handleSelectButtons();

			getModel().removeAll();
			getLog().removeAllElements();
			undoCommands.clear();
			undoCommandsLog.clear();
			commands.clear();
			frame.getView().repaint();

			if (chooser.getFileFilter().getDescription().equals("Serialized draw")) {
				fileManager = new FileManager(new FileDraw(getModel()));
				propertyChangeSupport.firePropertyChange("serialized draw opened", false, true);
			} else if (chooser.getFileFilter().getDescription().equals("Commands log"))
				fileManager = new FileManager(new FileLog(frame, getModel(), this));
			fileManager.open(chooser.getSelectedFile());
		}
		chooser.setVisible(false);
	}

	/**
	 * <h3>Method that create new draw if draw is not already empty removing all
	 * executed shapes and comands.</h3>
	 */

	public void newDraw() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure that you want to start new draw?", "Warning!",
				JOptionPane.YES_NO_OPTION) == 0) {

			this.counterOfSelectedShapes = 0;
			handleSelectButtons();

			getModel().removeAll();
			getLog().removeAllElements();
			undoCommands.clear();
			undoCommandsLog.clear();
			commands.clear();
			propertyChangeSupport.firePropertyChange("draw is empty", false, true);
			frame.getView().repaint();

		}
	}

	public DrawingModel getModel() {
		return model;
	}

	public DefaultListModel<String> getLog() {
		return log;
	}

}