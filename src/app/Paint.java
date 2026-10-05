package app;

import controller.DrawingController;
import frame.DrawingFrame;
import model.DrawingModel;

/**
 */
public class Paint {

	public static void main(String[] args) {
		DrawingFrame frame = new DrawingFrame();
		frame.setVisible(true);
		frame.setTitle("Java Paint");
		DrawingModel model = new DrawingModel();
		frame.getView().setModel(model);
		frame.setController(new DrawingController(model, frame));
	}
}