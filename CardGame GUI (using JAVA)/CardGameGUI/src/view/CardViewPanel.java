package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.ChangeCase;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class CardViewPanel extends JPanel {
	
	private GameEngine gameEngine;
	private Player player;
	private ChangeCase changeCase;
	
	private Boolean nextCardGeometry = false;
	private Image suitImage = null;
	private int newX = getX();
	
	private Map<Player, ArrayList<PlayingCard>> cardsHashMap = new HashMap<Player, ArrayList<PlayingCard>>();
	private ArrayList<PlayingCard> cardsList = new ArrayList<PlayingCard>();
	
	
	public void clearCards() {
		this.removeAll();
	}
	
	
	/**
	 * <pre>Dual function method
	 * When player/house is added to game, they are sent to this method and stored as key inside the
	 * hashMap, with their initial value set to null
	 * 
	 * As nextCard() calls this method, cards are then logically assigned to the particular key set
	 * in a unique arrayList, which is stored as the value of the hashMap
	 * 
	 * @param player being dealt
	 * @param card to be assigned to the player
	 * @param gameEngineImplGUI view.model which contains information about players and their cards
	 */
	public void addToHashMap(Player player, PlayingCard card, GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		
		if ((player != null) && (!cardsHashMap.containsKey(player))) {
			cardsHashMap.put(player, null);
		}
		
		if (card != null) {
			// if a new player is passed, this.player will be null
			// if house is dealing, player will be null
			// or if a new player is being dealt cards, it won't equal this.player
			// either way, re-instantiate the cardsList for this player,
			// else continue adding under the previous player's collection
			if ((this.player == null) || !(this.player.equals(player))) {
				cardsList = new ArrayList<PlayingCard>();
				cardsList.add(card);
				this.player = player;
			}
			else {
				cardsList.add(card);
			}
			
			// this cardsList is stored for the player passed in the hashMap
			cardsHashMap.replace(player, cardsList);
		}
	}
	
	
	/**
	 * Calls repaint() which eventually invokes the paintComponent() method that
	 * systematically draws the selected player's cards
	 * 
	 * @param paintNextCard Boolean value notifying the paintComponent() method to start drawing
	 * @param player whose cards need to be drawn
	 */
	public void drawCard(Player player) {
		this.player = player;
		
		clearCards();
		
		// invokes the paintComponent()
		repaint();
	}


	@SuppressWarnings("static-access")
	@Override
	public void paintComponent(Graphics g) {
		if (player != null) {
			super.paintComponent(g);
			
			// nextCardGeometry will get activated after the first card is drawn, so the
			// next card is drawn at a distance of (width + x) from the current x position
			nextCardGeometry = false;
			
			// get card values stores for this.player, and convert it to array format and store in cardsDealt array
			// that has a size = no. of cards dealt to the player
			PlayingCard[] cardsDealt = cardsHashMap.get(player).toArray(new PlayingCard[cardsHashMap.get(player).size()]);
			
			// bustCount to go off at the last, to help make that card GREY coloured
			int bustCount = 2;
			for (int i = 0; i < cardsDealt.length; i++) {
				// call changeCase class from A1, to get the required formatting for the suits
				// and value of the cards, and store them as strings
				changeCase = new ChangeCase(cardsDealt[i].getSuit(), cardsDealt[i].getValue());
				String suit = changeCase.changeSuit(cardsDealt[i].getSuit());
				String value = changeCase.changeValue(cardsDealt[i].getValue());
				
				g.setColor(Color.WHITE);

				if (nextCardGeometry) {
					if (bustCount == cardsDealt.length && (player.getResult() != gameEngine.BUST_LEVEL)) {
						g.setColor(Color.LIGHT_GRAY);
					}
					
					// new x coordinate for all cards that will be drawn after the first one
					newX = newX + getDistanceToNextCard();
					
					// draw rounded rectangle, suit image, and label using helper methods
					makeRect(g, newX);
					makeSuit(g, newX, suit);
					
					// set the label's color to match the suit image color
					setLabelColor(g, suit);
					Font f = getLabelFont(g);
					g.setFont(f);
					
					// get the string to be set as the label based on value
					String str = makeStringFromValue(value);
					makeTopLabel(g, str, newX);
					makeBottomLabel(g, f, str, newX);
					
					bustCount++;
				}
				else {
					makeRect(g, getX());
					makeSuit(g, getX(), suit);
					
					setLabelColor(g, suit);
					Font f = getLabelFont(g);
					g.setFont(f);
					
					String str = makeStringFromValue(value);
					makeTopLabel(g, str, getX());
					makeBottomLabel(g, f, str, getX());
				}
				
				nextCardGeometry = true;
			}
			
			// set the newX back to the first card's x coordinate to allow
			// proper formatting of all subsequent cards upon being repainted
			newX = getX();
		}
	}
	
	
	private void makeRect(Graphics g, int x) {
		g.fillRoundRect(x, getY(), getCardWidth(), (int) getCardHeight(), getArcWidth(), getArcHeight());
	}
	
	
	private void makeSuit(Graphics g, int x, String suit) {
		g.drawImage(getSuitImage(suit), (x + getSuitX()), (int) (getY() + getSuitY()), this);
	}
	
	
	private void makeTopLabel(Graphics g, String str, int x) {
		g.drawString(str, (x + getTopLabelX()), (int) (getY() + getTopLabelY()));
	}
	
	
	private void makeBottomLabel(Graphics g, Font f, String str, int x) {
		g.drawString(str, (int) (x + getBottomLabelX(g, f, str)), (int) (getY() + getBottomLabelY()));
	}
	
	
	private void setLabelColor(Graphics g, String suit) {
		if (suit.equals("Diamonds") || suit.equals("Hearts")) {
			g.setColor(Color.RED);
		}
		else {
			g.setColor(Color.BLACK);
		}
	}
	
	
	private Font getLabelFont(Graphics g) {
		int fontSize = 18;
        Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
        return f;
	}
	
	
	private String makeStringFromValue(String value) {
		switch (value) {
			case "Eight":
				return "8";
			case "Nine":
				return "9";
			case "Ten":
				return "T";
			case "Jack":
				return "J";
			case "Queen":
				return "Q";
			case "King":
				return "K";
			case "Ace":
				return "A";
			default:
				return value;
		}
	}


	public int getX() {
		// the x coordinate of the first rectangle
		int x = getWidth()/16;
		return x;
	}

	
	public int getY() {
		int y = (getHeight()/2) - ((int) getCardHeight()/2);
		return y;
	}

	
	private int getCardWidth() {
		// the panel width is cut in 6 parts so 6 cards can fit
		int cardWidth = (getWidth()/6) - getX();
		return cardWidth;
	}
	
	
	private double getCardHeight() {
		// card height always consistent to meet spec requirement
		double cardHeight = (double) getCardWidth()*1.5;
		return cardHeight;
	}

	
	private int getArcWidth() {
		// the x coordinate of the curve of the rounded rectangles
		int arcWidth = getX()/3;
		return arcWidth;
	}

	
	private int getArcHeight() {
		int arcHeight = getX()/3;
		return arcHeight;
	}
	
	
	private int getDistanceToNextCard() {
		// distance to the subsequent x coordinates
		int distanceToNextCard = (getCardWidth() + getX());
		return distanceToNextCard;
	}
	
	
	private int getSuitX() {
		// the x coordinate of the suit image to be placed at centre
		int suitX = (getCardWidth()/2) - (suitImage.getWidth(this)/2);
		return suitX;
	}
	
	
	private double getSuitY() {
		double suitY = (getCardHeight()/2) - (suitImage.getHeight(this)/2);
		return suitY;
	}
	
	
	private int getTopLabelX() {
		// the x coordinate of the top label
		int topLabelX = getCardWidth()/10;
		return topLabelX;
	}
	
	
	private double getTopLabelY() {
		double topLabelY = getCardHeight()/8;
		return topLabelY;
	}
	
	
	/**
	 * Helper method to get bounds of the font 'f'
	 * To be used by getBottomLabelX method
	 * 
	 * @param g graphics instance
	 * @param f font whose bounds are to be calculated
	 * @param str font as a string
	 * 
	 * @return a Rectangle2D instance that contains the font's measurement
	 */
	private Rectangle2D getLabelFontBounds(Graphics g, Font f, String str) {
		Graphics2D g2 = (Graphics2D) g;
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = f.getStringBounds(str, context);
		return bounds;
	}
	
	
	private double getBottomLabelX(Graphics g, Font f, String str) {
		Rectangle2D bounds = getLabelFontBounds(g, f, str);
		int x = getCardWidth()/2 - getSuitX();
		// the x coordinate of the bottom label
		double bottomLabelX = getCardWidth() - (x + bounds.getWidth()); 
		return bottomLabelX;
	}
	
	
	private double getBottomLabelY() {
		double bottomLabelY = getCardHeight() - (getCardHeight()/9);
		return bottomLabelY;
	}
	
	
	/**
	 * Based on provided suit string, returns the Image to be placed at the centre of each card
	 * 
	 * @param suit of the card to be drawn
	 * @return image of the suit as an Image instance
	 */
	private Image getSuitImage(String suit) {
		try {
			suitImage = ImageIO.read(new File(String.format("img%s%s.png", File.separator, suit)));
			suitImage = suitImage.getScaledInstance(getCardWidth()/4, (int) getCardHeight()/4, Image.SCALE_SMOOTH);
			return suitImage;
		}
		catch (IOException e) {
			e.printStackTrace();
		};
		return suitImage;
	}
	
}
