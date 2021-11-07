package view;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.StatusBarTextListener;

public class GameStatusBarPanel extends JPanel {
	
	private JLabel gameStatusLabel = new JLabel("", JLabel.LEFT);
	private final int statusTimeout = 2000;

	public GameStatusBarPanel() {
		setLayout(new GridLayout());
		gameStatusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// add JLabel to GameStatusBarPanel
		add(gameStatusLabel);
	}

	
	public void notifyPlayerAdded(String playerName, Boolean added) {
		if (added) {
			gameStatusLabel.setText(String.format("Welcome, %s", playerName));
		}
		else {
			gameStatusLabel.setText(String.format("Player not added"));
		}
		
		textTimeout(statusTimeout);
	}

	
	public void notifyPlayerRemoved(String playerName, Boolean removed) {
		if (removed) {
			gameStatusLabel.setText(String.format("Good Bye, %s", playerName));
		}
		else {
			gameStatusLabel.setText(String.format("%s not removed", playerName));
		}
		
		textTimeout(statusTimeout);
	}
	
	
	public void notifyBetCancelled(Boolean cancelled) {
		if (cancelled) {
			gameStatusLabel.setText("Bet cancelled successfully");
		}
		else {
			gameStatusLabel.setText("Bet holds");
		}
		
		textTimeout(statusTimeout);
	}
	
	
	public void notifyDealingStatus(String playerName) {
		gameStatusLabel.setText(String.format("Now dealing to %s", playerName));
		
		int halfStatusTimeout = statusTimeout / 2;
		textTimeout(halfStatusTimeout);
		
		gameStatusLabel.setText("Dealing finished");
		
		textTimeout(statusTimeout);
	}

	
	/**
	 * <pre>To be called by StatusBarTextListener
	 * 
	 * To clear the status text after timeout
	 */
	public void clearStatusBar() {
		gameStatusLabel.setText("");
	}
	
	
	/**
	 * Private helper method to handle the time for which the text stays on the status bar
	 */
	private void textTimeout(int statusTimeout) {
		StatusBarTextListener statusBarTextListener = new StatusBarTextListener(this);
		
		new Thread() {
			@Override
			public void run() {
				Timer timer = new Timer(statusTimeout, statusBarTextListener);
				timer.setRepeats(false);
				timer.start();
			}
		}.start();
	}

}
