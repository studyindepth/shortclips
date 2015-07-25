package vn.shortclips.infrastructure.json;

public class JsonSerializationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1099461159333840075L;

	public JsonSerializationException() {
		super();
	}

	public JsonSerializationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JsonSerializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonSerializationException(String message) {
		super(message);
	}

	public JsonSerializationException(Throwable cause) {
		super(cause);
	}

}
