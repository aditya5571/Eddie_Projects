package client;

import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;

public class ChangeCase {
	
	public ChangeCase(Suit suit, Value value) {
		super();
	}

	
	public String changeSuit(Suit suit2) {
		String resultString = "";
		
		String str = suit2.toString().toLowerCase();
		resultString += Character.toUpperCase(str.charAt(0)) + str.substring(1);
		
		return resultString;
	}


	public String changeValue(Value value2) {
		String resultString = "";
		
		String str = value2.toString().toLowerCase();
		resultString += Character.toUpperCase(str.charAt(0)) + str.substring(1);
		
		return resultString;
	}
}
