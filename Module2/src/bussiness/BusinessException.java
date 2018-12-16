package bussiness;

import db.UserNotFoundException;

public class BusinessException extends Exception {

	public BusinessException(UserNotFoundException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2445718097666226245L;

}
