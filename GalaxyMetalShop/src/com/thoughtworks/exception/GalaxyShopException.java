package com.thoughtworks.exception;

public class GalaxyShopException extends BaseException {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = -6812240116282804493L;

	/**
	 * Creates a new GalaxyShopException object.
	 */
	public GalaxyShopException() {
		super();
	}

	/**
	 * Creates a new GalaxyShopException object.
	 * 
	 * @param message
	 *            Error message
	 */
	public GalaxyShopException(final String message) {
		super(message);
	}

	/**
	 * Creates a new GalaxyShopException object.
	 * 
	 * @param cause
	 *            Throwable cause
	 */
	public GalaxyShopException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new GalaxyShopException object.
	 * 
	 * @param message
	 *            Error message
	 * @param cause
	 *            Throwable cause
	 */
	public GalaxyShopException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new GalaxyShopException object.
	 * 
	 * @param message
	 *            Error message
	 * @param cause
	 *            Throwable cause
	 * @param errorCode
	 *            Error code
	 */
	public GalaxyShopException(final String message, final Throwable cause,
			final String errorCode) {
		super(message, cause, errorCode);
	}

	/**
	 * Instantiates with message and errorCode.
	 * 
	 * @param message
	 *            Error message
	 * @param errorCode
	 *            Error code
	 */
	public GalaxyShopException(final String message, final String errorCode) {
		super(message, errorCode);
	}

}
