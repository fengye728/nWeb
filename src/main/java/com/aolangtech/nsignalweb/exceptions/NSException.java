/**
 * 
 */
/**
 * @author Maple
 *
 */
package com.aolangtech.nsignalweb.exceptions;

public class NSException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2154991171939164668L;
	
	private String errorMessage;
	

	public NSException(String aErrorMessage) {
		setErrorMessage(aErrorMessage);
	}
	
	private void setErrorMessage(String aErrorMessage) {
		this.errorMessage = aErrorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}