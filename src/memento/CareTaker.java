package memento;

import java.util.HashMap;
import java.util.Map;

public class CareTaker {

	private Map<String, MementoShape> shapesRestorePoints = new HashMap <String,MementoShape>(0);

	public CareTaker()
	{
		
	}
	public void saveMemento(MementoShape memento)
	{
		int versionNumber = shapesRestorePoints.size()+1;
		String version = "Version "+versionNumber;
		shapesRestorePoints.put(version, memento);
		
	}
	
	public MementoShape getMemento(String version)
	{
		return shapesRestorePoints.get(version);
	}
	
	public Map<String, MementoShape> getAllsavepoints()
	{
		return  shapesRestorePoints;
		
	}
	public void clearRestorePoints()
	{
		shapesRestorePoints.clear();
	}

}
