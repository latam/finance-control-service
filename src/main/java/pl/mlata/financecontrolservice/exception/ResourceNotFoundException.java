package pl.mlata.financecontrolservice.exception;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 3234383487119391724L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
