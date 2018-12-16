package db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserDataBase {
	private final static String USERS_FILE = "";
	private Map<String, String> users = new HashMap<String, String>();

	public UserDataBase() throws FileNotFoundException, IOException {
		this.loadUsers();
	}

	private void loadUsers() throws FileNotFoundException, IOException {
		try (FileReader fl = new FileReader(USERS_FILE); BufferedReader file = new BufferedReader(fl)) {
			while (file.ready()) {
				String line = file.readLine();
				String[] data = line.split("@");
			}
		}
	}

	public boolean isRegistered(String username) {
		return users.containsKey(username);
	}

	/**
	 * Returns whether the the login has been succesfull or not.
	 * 
	 * @param username
	 * @param password
	 * @return whether the specified password is correct for the specified user
	 * @throws UserNotFoundException if the user is not in the database
	 */
	public boolean logIn(String username, String password) throws UserNotFoundException {
		if (!isRegistered(username)) {
			throw new UserNotFoundException("The user " + username + " does not exist.");
		}
		return users.get(username).equals(String.valueOf(password.hashCode()));

	}

	public void registerUser(String username, String password) throws UserAlreadyRegisteredException {
		if (isRegistered(username)) {
			throw new UserAlreadyRegisteredException("The user is already registered");
		}
		this.users.put(username, String.valueOf(password.hashCode()));
	}

	public void updatePassword(String username, String password) throws UserNotFoundException {
		if (!isRegistered(username)) {
			throw new UserNotFoundException("The user " + username + " does not exist.");
		}
		this.users.replace(username, String.valueOf(password.hashCode()));
	}
}
