package view.gui.menubar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.ExitGameActionListener;
import view.GameFrame;

public class GameMenuBar extends JMenuBar {

	public GameMenuBar(GameFrame frame) {
		JMenu gameMenu = new JMenu("Menu");
		add(gameMenu);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ExitGameActionListener(frame));
		gameMenu.add(exitItem);
	}

}
