package bussiness;

import java.io.FileNotFoundException;
import java.io.IOException;

import db.UserAlreadyRegisteredException;
import db.UserDataBase;
import db.UserNotFoundException;

public class Login {
	private final static String NO_USER = "";
	private String currentUser;
	private UserDataBase userDb;

	public Login() throws FileNotFoundException, IOException {
		this.currentUser = NO_USER;
		this.userDb = new UserDataBase();
	}

	public boolean isRegistered(String user) {
		return this.userDb.isRegistered(user);
	}

	public void registerUser(String user, String password) throws BusinessException {
		try {
			this.userDb.registerUser(user, password);
		} catch (UserAlreadyRegisteredException e) {
			throw new BusinessException(e);
		}
	}

	public void changePassword(String user, String password) throws BusinessException {
		try {
			this.userDb.updatePassword(user, password);
		} catch (UserNotFoundException e) {
			throw new BusinessException(e);
		}
	}

	public void logout() {
		this.currentUser = NO_USER;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void logIn(String currentUser, String password) throws BusinessException {
		try {
			if(userDb.logIn(currentUser, password)) {
				this.currentUser = currentUser;
			}else {
				throw new BusinessException("Wrong password.");
			}			
		} catch (UserNotFoundException e) {
			throw new BusinessException(e);
		}

	}

}
