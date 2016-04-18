package fr.ld35.main;

import javax.swing.JApplet;

public class GameApplet extends JApplet
{
	private static final long serialVersionUID = 1L;
	
	public void init()
	{
		Main main = new Main();
		this.add(main);
		main.start();
	}
}
