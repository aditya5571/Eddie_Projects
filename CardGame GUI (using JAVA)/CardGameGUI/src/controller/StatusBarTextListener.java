package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GameStatusBarPanel;

public class StatusBarTextListener implements ActionListener {
	
	private GameStatusBarPanel gameStatusBarPanel = new GameStatusBarPanel();
	
	public StatusBarTextListener(GameStatusBarPanel gameStatusBarPanel) {
		this.gameStatusBarPanel = gameStatusBarPanel;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gameStatusBarPanel.clearStatusBar();
	}

}
