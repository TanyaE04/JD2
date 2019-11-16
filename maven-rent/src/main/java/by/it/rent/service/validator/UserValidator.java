package by.it.rent.service.validator;

public class UserValidator {
	private static final UserValidator instance = new UserValidator();

	private UserValidator() {
	}

	public boolean check(String login, String password) {
		return (login.isEmpty() || password.isEmpty());
	}

	public static UserValidator getInstance() {
		return instance;
	}

}
