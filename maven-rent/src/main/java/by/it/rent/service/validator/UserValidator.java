package by.it.rent.service.validator;

public class UserValidator {
	private static final UserValidator instance = new UserValidator();

	private UserValidator() {
	}

	public boolean check(String login, String password) {
		if (login.isEmpty() || password.isEmpty()) {
			return false;
		} else
			return true;
	}

	public static UserValidator getInstance() {
		return instance;
	}

}
