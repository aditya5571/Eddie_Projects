package controller;

import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

/**
 * For using the same frame reference for all the controller classes
 */
public abstract class AbstractGameFrameActionListener implements ActionListener {

	protected GameFrame frame;
	protected GameEngineImplGUI gameEngineGUI;
	protected GameEngine gameEngine;
	protected Player player;

	public AbstractGameFrameActionListener(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		this.frame = frame;
		this.gameEngineGUI = gameEngineGUI;
		this.player = player;
		this.gameEngine = gameEngine;
	}

}
