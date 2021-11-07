package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewPlayerPanel extends JPanel {
	
	private JPanel playerDetailsPanel = new JPanel();
	private JTextField playerIDField = new JTextField();
	private JTextField playerNameField = new JTextField();
	private JTextField initialPointsField = new JTextField();

	
	public NewPlayerPanel() {
	    playerDetailsPanel.setLayout(new BoxLayout(playerDetailsPanel, BoxLayout.Y_AXIS));
	    
		// panel to prompt user to enter player's ID, name and Initial betting points in the textfields
	    playerDetailsPanel.add(new JLabel("Enter player details below"));
	    playerDetailsPanel.add(new JLabel("Player ID:"));
	    playerDetailsPanel.add(playerIDField);
	    
	    playerDetailsPanel.add(new JLabel("Player name:"));
	    playerDetailsPanel.add(playerNameField);
	    
	    playerDetailsPanel.add(new JLabel("Initial betting points:"));
	    playerDetailsPanel.add(initialPointsField);
	}

	
	/**
	 * @return the panel that will prompt user to enter the player details
	 */
	public JPanel getPlayerDetailsPanel() {
		return playerDetailsPanel;
	}
	
	
	public JTextField getPlayerID() {
		return playerIDField;
	}
	
	
	public JTextField getPlayerName() {
		return playerNameField;
	}
	
	
	public JTextField getPlayerPoints() {
		return initialPointsField;
	}
	
	
	/**
	 * Clears the JTextFields for next entry
	 */
	public void clearTextFields() {
		playerIDField.setText("");
		playerNameField.setText("");
		initialPointsField.setText("");
	}

}
