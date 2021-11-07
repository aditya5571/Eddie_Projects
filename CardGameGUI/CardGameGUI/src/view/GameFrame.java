package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.GameEngineImpl;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.gui.menubar.GameMenuBar;
import view.gui.toolbar.GameComboBox;
import view.gui.toolbar.GameToolBar;
import view.gui.toolbar.ToolBarButton;
import view.model.GameEngineImplGUI;

public class GameFrame extends JFrame {
	
	private GameEngine gameEngine = new GameEngineImpl();
	private Player player;

	private GameButtonStatus gameButtonStatus;
	private GameComboBox gameComboBox;
	
	private NewPlayerPanel newPlayer;
	private CardViewPanel cardViewPanel = new CardViewPanel();
	private GameSummaryPanel gameSummaryPanel;
	private GameStatusBarPanel gameStatusBar;

	
	public GameFrame(GameEngine gameEngine, GameEngineImplGUI gameEngineImplGUI) {
		super("BlackJack");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth()/2;
		double height = screenSize.getHeight()/2;
		this.setMinimumSize(new Dimension((int) width, (int) height));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.gameEngine = gameEngine;
		
		// instantiate different view classes, and pass the necessary references
		ToolBarButton toolBarButton = new ToolBarButton();
		gameComboBox = new GameComboBox(this, gameEngineImplGUI, player, gameEngine);
		GameToolBar gameToolBar = new GameToolBar(toolBarButton, this,
				gameEngineImplGUI, player, gameEngine);
		gameButtonStatus = new GameButtonStatus(toolBarButton, gameEngine);
		newPlayer = new NewPlayerPanel();
		gameSummaryPanel = new GameSummaryPanel(gameEngine);
		gameStatusBar = new GameStatusBarPanel();
		
		// set this frame's menubar as gameMenu
		GameMenuBar gameMenu = new GameMenuBar(this);
		setJMenuBar(gameMenu);
		
		// add toolBar and summaryPanel to a seperate panel called topPanel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(gameToolBar);
		topPanel.add(gameSummaryPanel);
		
		// set the summary table's preferred layout and dimensions
		gameSummaryPanel.setLayout(new GridLayout());
		gameSummaryPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/5));
		
		// add the topPanel to frame
		add(topPanel, BorderLayout.NORTH);

		// add gameStatusBarPanel to frame
		add(gameStatusBar, BorderLayout.SOUTH);
		
		// add cardViewPanel to frame
		cardViewPanel.setPreferredSize(new Dimension
				(getWidth(), (getHeight() - (topPanel.getHeight() + gameStatusBar.getHeight()))));
		add(cardViewPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}

	
	/**
	 * Pass the playerName entered by user to GameComboBox, to be displayed on the GameToolBar,
	 * called by AddPlayerListener and RemovePlayerListener
	 * 
	 * @param playerName entered by user
	 * @param add the boolean value passed depending on which controller calls
	 */
	public void updateComboBox(String playerName, Boolean add) {
		gameComboBox.playerComboBox(playerName, add);
	}
	
	
	public void enableBetCancellation() {
		gameButtonStatus.enableCancelBet();
	}

	
	public void disableBetCancellation() {
		gameButtonStatus.disableCancelBet();
	}
	
	
	public void enableDealing() {
		gameButtonStatus.enableDeal();
	}
	
	
	public void disableDealing() {
		gameButtonStatus.disableDeal();
	}
	
	
	public void enablePlayerRemoval() {
		gameButtonStatus.enableRemovePlayer();
	}
	
	
	public void disablePlayerRemoval() {
		gameButtonStatus.disableRemovePlayer();
	}
	
	
	public void enableBetting() {
		gameButtonStatus.enableBet();
	}
	
	
	public void disableBetting() {
		gameButtonStatus.disableBet();
	}
	
	
	/** 
	 * @return name of current player selection from the gameComboBox
	 */
	public String getPlayerFromComboBox() {
		return gameComboBox.getPlayerName();
	}
	
	
	/** 
	 * @return the gameComboBox
	 */
	public JComboBox<String> getComboBox() {
		return gameComboBox.getPlayerSelector();
	}
	
	
	public NewPlayerPanel getNewPlayerPanel() {
		return newPlayer;
	}
	

	public GameSummaryPanel getSummaryPanel() {
		return gameSummaryPanel;
	}
	
	
	public CardViewPanel getCardPanel() {
		return cardViewPanel;
	}
	
	
	public GameStatusBarPanel getStatusBar() {
		return gameStatusBar;
	}
	
	
	public int getDelay() {
		final int delay = 100;
		return delay;
	}

	
	/**
	 * Calls drawCard() method from CardViewPanel class on a seperate UI thread
	 */
	public void updateCardPanel(Player player) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				cardViewPanel.drawCard(player);
			}
		});
	}

	
	/**
	 * <pre>To be called by houseResult method, after house has finished dealing
	 * 
	 * Removes the player(s) from comboBox, summary table and model.interfaces.GameEngine
	 * if their points become 0 after win/loss is decided
	 */
	public void removeIfZeroPoints() {
		for (Player player : gameEngine.getAllPlayers()) {
			if (player.getPoints() <= 0) {
				// reference to the view, for updating the combo box
				// listener passes false because player needs to be removed from the view
				Boolean add = false;
				updateComboBox(player.getPlayerName(), add);

				// update the summary table
				getSummaryPanel().removePlayerInfo(player);
				
				JOptionPane.showMessageDialog(this,
						String.format("%s was removed because they lost their last dime!", player.getPlayerName()));

				// update model.interfaces.GameEngine
				// removed from model after view is fully updated, because the summary panel
				// uses model's getAllPlayers collection to get the player to be removed from table
				gameEngine.removePlayer(player);

				// update status bar that player is no longer in game
				Boolean removed = true;
				getStatusBar().notifyPlayerRemoved(player.getPlayerName(), removed);
			}
		}
	}
	
}