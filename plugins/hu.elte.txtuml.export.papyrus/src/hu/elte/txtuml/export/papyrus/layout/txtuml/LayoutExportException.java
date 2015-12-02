package hu.elte.txtuml.export.papyrus.layout.txtuml;

/**
 * Exception for Layout export errors
 * @author Andr�s Dobreff
 */
public class LayoutExportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The Constructor
	 * @param message
	 */
	public LayoutExportException(String message) {
		super(message);
	}
	
	/**
	 * The Constructor
	 * @param exception
	 */
	public LayoutExportException(Exception exception){
		super(exception.getMessage());
	}

}
