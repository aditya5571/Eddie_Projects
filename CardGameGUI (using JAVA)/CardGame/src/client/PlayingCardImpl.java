package client;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard {

	private Suit suit;
	private Value value;

	private final int eight = 8;
	private final int nine = 9;
	private final int ten = 10;
	private final int eleven = 11;
	private final int illegalCardValue = -1;
	
	private ChangeCase changeCase = new ChangeCase(this.suit, this.value);
	
	
	public PlayingCardImpl(Suit suit, Value value) {
		this.suit = suit;
		this.value = value;
	}

	
	@Override
	public Suit getSuit() {
		return suit;
	}

	
	@Override
	public Value getValue() {
		return value;
	}

	
	@Override
	public int getScore() {
		if (value == Value.EIGHT) {
			return eight;
		}
		else if (value == Value.NINE) {
			return nine;
		}
		else if (value == Value.TEN ||
				value == Value.JACK ||
				value == Value.QUEEN ||
				value == Value.KING) {
			return ten;
		}
		else if (value == Value.ACE) {
			return eleven;
		}
		
		return illegalCardValue;
	}

	
	@Override
	public String toString() {
		// calls helper class ChangeCase in order to meet formatting requirements
		return String.format("Suit: %s, Value: %s, Score: %d",
				changeCase.changeSuit(suit), changeCase.changeValue(value), getScore());
	}

	
	@Override
	public boolean equals(PlayingCard card) {
		return (value == card.getValue()
			&& suit == card.getSuit());
	}

	
	@Override
	   public boolean equals(Object card) {
		if ((card.getClass().equals(PlayingCard.class)) && card != null) {
			PlayingCard newCard = (PlayingCard) card;
			return (equals(newCard));
		}
		
		return false;
	}

	
	@Override
	   public int hashCode() {
		return (value.hashCode() + suit.hashCode());
	}
	
}