package appx.parser;

/**
 * Class of exceptions dedicated to parsing files
 * @author jeanguy
 *
 */
public class ParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2016556922672161718L;

	/**
	 * Creates an exception with a given message
	 * @param message the error message
	 */
	public ParsingException(String message) {
		super(message);
	}

}
