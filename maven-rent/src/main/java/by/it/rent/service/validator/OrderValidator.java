package by.it.rent.service.validator;

import java.time.LocalDate;

public class OrderValidator {
	private static final OrderValidator instance = new OrderValidator ();

	public static OrderValidator getInstance() {
		return instance;
	}
	
	private OrderValidator () {
		
	}
	
	public boolean check (String status, String dateRent) {
		String dateReturn = status.substring(13);
		LocalDate rent = LocalDate.parse(dateRent);
		LocalDate ofReturn = LocalDate.parse(dateReturn);
		if (ofReturn.isBefore(rent)) {
			return true;
		} else
			return false;
	}
}
