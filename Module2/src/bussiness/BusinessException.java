package bussiness;

public class BusinessException extends Exception {

	public BusinessException(Exception e) {
		super(e);
	}

	public BusinessException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2445718097666226245L;

}
