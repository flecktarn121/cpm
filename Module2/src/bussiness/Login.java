package bussiness;

import java.io.IOException;

import db.UserAlreadyRegisteredException;
import db.UserDataBase;
import db.UserNotFoundException;
import util.Messages;

public class Login {
	public final static String NO_USER = ""; //$NON-NLS-1$
	private String currentUser;
	private UserDataBase userDb;

	public Login() throws BusinessException {
		this.currentUser = NO_USER;
		try {
			this.userDb = new UserDataBase();
		} catch (IOException e) {
			throw new BusinessException(Messages.getString("Login.1")); //$NON-NLS-1$
		}
	}

	public boolean isRegistered(String user) {
		return this.userDb.isRegistered(user);
	}

	public void registerUser(String user, String password) throws BusinessException {
		try {
			this.userDb.registerUser(user, password);
			currentUser = user;
		} catch (UserAlreadyRegisteredException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	public void changePassword(String user, String password) throws BusinessException {
		try {
			this.userDb.updatePassword(user, password);
		} catch (UserNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	public void logout() {
		this.currentUser = NO_USER;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void asGuest() {
		currentUser = NO_USER;
	}

	public void logIn(String currentUser, String password) throws BusinessException {
		try {
			if (userDb.logIn(currentUser, password)) {
				this.currentUser = currentUser;
			} else {
				throw new BusinessException(Messages.getString("Login.2")); //$NON-NLS-1$
			}
		} catch (UserNotFoundException e) {
			throw new BusinessException(e);
		}

	}

	public void close() {
		userDb.save();
	}

}
