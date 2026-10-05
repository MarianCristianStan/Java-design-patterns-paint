package strategy;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import javax.swing.DefaultListModel;
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
import controller.DrawingController;
import dialogs.DlgLogParser;
import frame.DrawingFrame;
import model.DrawingModel;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Square;
import shapes.Triangle;

/**
 * Class that is responsible to save and parse log of executed commands.
 */
public class FileLog implements FileHandler {
	private BufferedWriter writer;
	private BufferedReader reader;
	private DrawingFrame frame;
	private DrawingModel model;
	private DrawingController controller;
	private DlgLogParser logParser;

	public FileLog(DrawingFrame frame, DrawingModel model, DrawingController controller) {
		this.frame = frame;
		this.model = model;
		this.controller = controller;
	}

	/**
	 * Save forwarded file as log of commands.
	 */
	@Override
	public void save(File file) {
		try {
			writer = new BufferedWriter(new FileWriter(file + ".log"));
			DefaultListModel<String> list = frame.getList();
			for (int i = 0; i < frame.getList().size(); i++) {
				writer.write(list.getElementAt(i));
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Open forwarded log file and execute it command by command in interaction with
	 * user.
	 */
	@Override
	public void open(File file) {
		try {
			reader = new BufferedReader(new FileReader(file));
			logParser = new DlgLogParser();
			logParser.setFileLog(this);
			logParser.addCommand(reader.readLine());
			logParser.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Read one line from log file and parse it.
	 * 
	 * @param command Represent command that need to be parsed.
	 */
	public void readLine(String command) {
		try {
			String[] commands1 = command.split("->");
			switch (commands1[0]) {
			case "Added":
				Shape shape = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				controller.executeCommand(new CmdAddShape(shape, model));
				frame.getList().addElement("Added->" + shape.toString());
				break;
			case "Updated":
				Shape oldShape = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				int index = model.getIndexOf(oldShape);
				if (oldShape instanceof Point) {
					Point newPoint = parsePoint(commands1[2].split(":")[1]);
					controller.executeCommand(new CmdUpdatePoint((Point) model.getByIndex(index), newPoint));
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newPoint.toString());
				} else if (oldShape instanceof Line) {
					Line newLine = parseLine(commands1[2].split(":")[1]);
					controller.executeCommand(new CmdUpdateLine((Line) model.getByIndex(index), newLine));
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newLine.toString());
				} else if (oldShape instanceof Rectangle) {
					Rectangle newRectangle = parseRectangle(commands1[2].split(":")[1]);
					controller
							.executeCommand(new CmdUpdateRectangle((Rectangle) model.getByIndex(index), newRectangle));
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newRectangle.toString());
				} else if (oldShape instanceof Square) {
					Square newSquare = parseSquare(commands1[2].split(":")[1]);
					controller.executeCommand(new CmdUpdateSquare((Square) model.getByIndex(index), newSquare));
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newSquare.toString());
				} else if (oldShape instanceof Circle) {
					Circle newCircle = parseCircle(commands1[2].split(":")[1]);
					controller.executeCommand(new CmdUpdateCircle((Circle) model.getByIndex(index), newCircle));
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newCircle.toString());
				} 
				else if (oldShape instanceof Triangle) {
					Triangle newTriangle = parseTriangle(commands1[2].split(":")[1]);
					controller.executeCommand(new CmdUpdateTriangle((Triangle) model.getByIndex(index), newTriangle));
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newTriangle.toString());
				} else if (oldShape instanceof Ellipse) {
					Ellipse newEllipse = parseEllipse(commands1[2].split(":")[1]);
					controller.executeCommand(new CmdUpdateEllipse((Ellipse) model.getByIndex(index), newEllipse));
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newEllipse.toString());
				}
				break;
			
			case "Deleted":
				Shape deletedShape = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				controller.executeCommand(new CmdRemoveShape(deletedShape, model));
				frame.getList().addElement("Deleted->" + deletedShape.toString());
				controller.handleSelect("Deleted", "parser");
				break;
			case "Moved to front":
				Shape shapeMovedToFront = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				controller.executeCommand(new CmdToFront(model, shapeMovedToFront));
				frame.getList().addElement("Moved to front->" + shapeMovedToFront.toString());
				break;
			case "Moved to back":
				Shape shapeMovedToBack = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				controller.executeCommand(new CmdToBack(model, shapeMovedToBack));
				frame.getList().addElement("Moved to back->" + shapeMovedToBack.toString());
				break;
			case "Bringed to front":
				Shape shapeBringedToFront = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				controller.executeCommand(new CmdBringToFront(model, shapeBringedToFront, model.getAll().size() - 1));
				frame.getList().addElement("Bringed to front->" + shapeBringedToFront.toString());
				break;
			case "Bringed to back":
				Shape shapeBringedToBack = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				controller.executeCommand(new CmdBringToBack(model, shapeBringedToBack));
				frame.getList().addElement("Bringed to back->" + shapeBringedToBack.toString());
				break;
			case "Selected":
				Shape selectedShape = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
			    int index1 = model.getIndexOf(selectedShape);
				controller.executeCommand(new CmdSelectShape( model.getByIndex(index1), true));
				frame.getList().addElement("Selected->" + selectedShape.toString());
				controller.handleSelect("Selected", "parser");
				controller.handleSelectButtons();
				break;
			case "Unselected":
				Shape unselectedShape = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
				int index2 = model.getIndexOf(unselectedShape);
				controller.executeCommand(new CmdSelectShape(model.getByIndex(index2), false));
				frame.getList().addElement("Unselected->" + unselectedShape.toString());
				controller.handleSelect("Unselected", "parser");
				controller.handleSelectButtons();
				break;
			}

			String line = reader.readLine();
			if (line != null)
				logParser.addCommand(line);
			else {
				logParser.closeDialog();
				return;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Determine which type of shape need to be parsed and call appropriate method.
	 * 
	 */
	private Shape parseShape(String shape, String shapeParameters) {
		if (shape.equals("Point"))
			return parsePoint(shapeParameters);
		else if (shape.equals("Line"))
			return parseLine(shapeParameters);
		else if (shape.equals("Circle"))
			return parseCircle(shapeParameters);
		else if (shape.equals("Rectangle"))
			return parseRectangle(shapeParameters);
		else if (shape.equals("Triangle")) 
			return parseTriangle(shapeParameters);
		else if (shape.equals("Ellipse"))
			return parseEllipse(shapeParameters);
		else 
			return parseSquare(shapeParameters);
	
	}

	private Triangle parseTriangle(String string) {
		String[] triangleParts = string.split(";");
		int x = Integer.parseInt(triangleParts[0].split("=")[1]);
		int y = Integer.parseInt(triangleParts[1].split("=")[1]);
		int base = Integer.parseInt(triangleParts[2].split("=")[1]);
		int height = Integer.parseInt(triangleParts[3].split("=")[1]);
		String s = triangleParts[4].split("=")[1].substring(1, triangleParts[4].split("=")[1].length() - 1);
		String[] edgeColors = s.split(",");
		String s1 = triangleParts[5].split("=")[1].substring(1, triangleParts[5].split("=")[1].length() - 1);
		String[] interiorColors = s1.split(",");
		return new Triangle(new Point(x, y), base, height,
				new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]),
						Integer.parseInt(edgeColors[2].split("-")[1])),
				new Color(Integer.parseInt(interiorColors[0].split("-")[1]),
						Integer.parseInt(interiorColors[1].split("-")[1]),
						Integer.parseInt(interiorColors[2].split("-")[1])));

	}

	private Ellipse parseEllipse(String string) {
		String[] ellipseParts = string.split(";");
		int x = Integer.parseInt(ellipseParts[0].split("=")[1]);
		int y = Integer.parseInt(ellipseParts[1].split("=")[1]);
		int width = Integer.parseInt(ellipseParts[2].split("=")[1]);
		int height = Integer.parseInt(ellipseParts[3].split("=")[1]);
		String s = ellipseParts[4].split("=")[1].substring(1, ellipseParts[4].split("=")[1].length() - 1);
		String[] edgeColors = s.split(",");
		String s1 = ellipseParts[5].split("=")[1].substring(1, ellipseParts[5].split("=")[1].length() - 1);
		String[] interiorColors = s1.split(",");
		return new Ellipse(new Point(x, y), width, height,
				new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]),
						Integer.parseInt(edgeColors[2].split("-")[1])),
				new Color(Integer.parseInt(interiorColors[0].split("-")[1]),
						Integer.parseInt(interiorColors[1].split("-")[1]),
						Integer.parseInt(interiorColors[2].split("-")[1])));

	}

	/**
	 * Method that parse {@link Point} from log file.
	 *
	 */
	private Point parsePoint(String string) {
		String[] pointParts = string.split(";");
		String s = pointParts[2].split("=")[1].substring(1, pointParts[2].split("=")[1].length() - 1);
		String[] colors = s.split(",");
		return new Point(Integer.parseInt(pointParts[0].split("=")[1]), Integer.parseInt(pointParts[1].split("=")[1]),
				new Color(Integer.parseInt(colors[0].split("-")[1]), Integer.parseInt(colors[1].split("-")[1]),
						Integer.parseInt(colors[2].split("-")[1])));
	}

	/**
	 * Method that parse {@link Circle} from log file.
	 * 
	 */
	private Circle parseCircle(String string) {
		String[] circleParts = string.split(";");
		int radius = Integer.parseInt(circleParts[0].split("=")[1]);
		int x = Integer.parseInt(circleParts[1].split("=")[1]);
		int y = Integer.parseInt(circleParts[2].split("=")[1]);
		String s = circleParts[3].split("=")[1].substring(1, circleParts[3].split("=")[1].length() - 1);
		String[] edgeColors = s.split(",");
		String s1 = circleParts[4].split("=")[1].substring(1, circleParts[4].split("=")[1].length() - 1);
		String[] interiorColors = s1.split(",");
		return new Circle(new Point(x, y), radius,
				new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]),
						Integer.parseInt(edgeColors[2].split("-")[1])),
				new Color(Integer.parseInt(interiorColors[0].split("-")[1]),
						Integer.parseInt(interiorColors[1].split("-")[1]),
						Integer.parseInt(interiorColors[2].split("-")[1])));
	}

	/**
	 * Method that parse {@link Square} from log file.
	 * 
	 */
	private Square parseSquare(String string) {
		String[] squareParts = string.split(";");
		int x = Integer.parseInt(squareParts[0].split("=")[1]);
		int y = Integer.parseInt(squareParts[1].split("=")[1]);
		int side = Integer.parseInt(squareParts[2].split("=")[1]);
		String s = squareParts[3].split("=")[1].substring(1, squareParts[3].split("=")[1].length() - 1);
		String[] edgeColors = s.split(",");
		String s1 = squareParts[4].split("=")[1].substring(1, squareParts[4].split("=")[1].length() - 1);
		String[] interiorColors = s1.split(",");
		return new Square(new Point(x, y), side,
				new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]),
						Integer.parseInt(edgeColors[2].split("-")[1])),
				new Color(Integer.parseInt(interiorColors[0].split("-")[1]),
						Integer.parseInt(interiorColors[1].split("-")[1]),
						Integer.parseInt(interiorColors[2].split("-")[1])));
	}

	/**
	 * Method that parse {@link Rectangle} from log file.
	 * 
	 */
	private Rectangle parseRectangle(String string) {
		String[] rectangleParts = string.split(";");
		int x = Integer.parseInt(rectangleParts[0].split("=")[1]);
		int y = Integer.parseInt(rectangleParts[1].split("=")[1]);
		int height = Integer.parseInt(rectangleParts[2].split("=")[1]);
		int width = Integer.parseInt(rectangleParts[3].split("=")[1]);
		String s = rectangleParts[4].split("=")[1].substring(1, rectangleParts[4].split("=")[1].length() - 1);
		String[] edgeColors = s.split(",");
		String s1 = rectangleParts[5].split("=")[1].substring(1, rectangleParts[5].split("=")[1].length() - 1);
		String[] interiorColors = s1.split(",");
		return new Rectangle(new Point(x, y), width, height,
				new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]),
						Integer.parseInt(edgeColors[2].split("-")[1])),
				new Color(Integer.parseInt(interiorColors[0].split("-")[1]),
						Integer.parseInt(interiorColors[1].split("-")[1]),
						Integer.parseInt(interiorColors[2].split("-")[1])));
	}

	/**
	 * Method that parse {@link Line} from log file.
	 * 
	 */
	private Line parseLine(String string) {
		String[] lineParts = string.split(";");
		int xStart = Integer.parseInt(lineParts[0].split("=")[1]);
		int yStart = Integer.parseInt(lineParts[1].split("=")[1]);
		int xEnd = Integer.parseInt(lineParts[2].split("=")[1]);
		int yEnd = Integer.parseInt(lineParts[3].split("=")[1]);
		String s = lineParts[4].split("=")[1].substring(1, lineParts[4].split("=")[1].length() - 1);
		String[] edgeColors = s.split(",");
		return new Line(new Point(xStart, yStart), new Point(xEnd, yEnd),
				new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]),
						Integer.parseInt(edgeColors[2].split("-")[1])));
	}
}