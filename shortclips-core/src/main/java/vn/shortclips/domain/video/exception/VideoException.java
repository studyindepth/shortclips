package vn.shortclips.domain.video.exception;

public class VideoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VideoException() {
		super();
	}

	public VideoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VideoException(String message, Throwable cause) {
		super(message, cause);
	}

	public VideoException(String message) {
		super(message);
	}

	public VideoException(Throwable cause) {
		super(cause);
	}

}
