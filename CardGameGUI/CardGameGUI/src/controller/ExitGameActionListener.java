package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.GameFrame;

public class ExitGameActionListener implements ActionListener {
	
	private GameFrame frame;
	
	public ExitGameActionListener(GameFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		int userChoice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
				"Exit Game", JOptionPane.YES_NO_OPTION);
		
		if (userChoice == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

}
