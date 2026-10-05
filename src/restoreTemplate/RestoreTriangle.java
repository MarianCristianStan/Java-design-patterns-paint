package restoreTemplate;

import java.util.Map;

import javax.swing.JOptionPane;

import commands.CmdUpdateTriangle;
import controller.DrawingController;
import memento.CareTaker;
import memento.MementoShape;
import shapes.Shape;
import shapes.Triangle;

public class RestoreTriangle extends RestoreTemplate {

	@Override
	public String getInputDialog(Shape shape) {

		CareTaker careTaker = ((Triangle) shape).getCareTaker();
		Map<String, MementoShape> savepoints = careTaker.getAllsavepoints();
		String[] optionsToChooseVersion = new String[savepoints.size()];
		int i = 0;
		for (var keys : savepoints.keySet()) {
			optionsToChooseVersion[i] = keys;
			++i;
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
		String getVersionSelected = (String) JOptionPane.showInputDialog(null, "What version do you like to restore?",
				"Choose version", JOptionPane.QUESTION_MESSAGE, null, optionsToChooseVersion, optionsToChooseVersion);
		System.out.println("Version:  " + getVersionSelected);
		return getVersionSelected;
	}

	@Override
	public void executeRestore(DrawingController controller, Shape shape, String getVersionSelected) {
	
		if (getVersionSelected != null) {
			Triangle restoreTriangle = ((Triangle) shape).getTriangleBySavepoint(getVersionSelected);
			controller.getLog()
					.addElement("Updated->" + ((Triangle) shape).toString() + "->" + restoreTriangle.toString());
			controller.executeCommand(new CmdUpdateTriangle(((Triangle) shape), restoreTriangle));

		}
	}

}
