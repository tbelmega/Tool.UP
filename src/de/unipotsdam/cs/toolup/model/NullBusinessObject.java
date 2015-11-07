package de.unipotsdam.cs.toolup.model;


/**
 * NullBusinessObject represents the absence of a {@link BusinessObject} according to the NullObjectPattern.
 * @author Thiemo
 *
 */
public class NullBusinessObject extends BusinessObject {
	
	private static NullBusinessObject instance = null;


	private NullBusinessObject(){
		super("","Null Object","This object represents null.");
	}

	/**
	 * NullBusinessObject represents the absence of a {@link BusinessObject}.
	 * @return The singleton instance of {@link NullBusinessObject}
	 */
	public static BusinessObject getInstance() {
		//Singleton implementation without synchronization, does not matter
		if (instance == null){
			instance = new NullBusinessObject();
		}
		return instance;
	}

	@Override
	public void addRelation(String string) {
	}

}
