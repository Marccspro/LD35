package fr.ld35.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.ld35.main.game.Game;
import fr.ld35.main.gfx.Screen;
import fr.ld35.main.gfx.Texture;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 720;
	public static final int HEIGHT = 480;
	
	private boolean runnable = false;
	private BufferedImage image;
	private int[] pixels;
	private BufferStrategy bs;
	private Screen screen;
	private Game game;
	private String name;
	
	private boolean spaceDown = false;
	private boolean spaceTriggered = false;
	private boolean inGame = false;
	private boolean inExpli = false;
	private boolean inTuto = false;
	private boolean inStart = true;
	private boolean isDead = false;
	
	public void init()
	{
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		this.createBufferStrategy(2);
		bs = this.getBufferStrategy();
		screen = new Screen(WIDTH, HEIGHT);
		
		this.addFocusListener(screen);
		this.addKeyListener(screen);
		this.addMouseListener(screen);
		this.addMouseMotionListener(screen);
		
		name = JOptionPane.showInputDialog(null, "Enter your name:");
		game = new Game(name);
	}
	
	public void update()
	{
		if (!screen.focused)
			return;

		isDead = game.player.dead;
		if ((isDead || game.win) && screen.space)
		{
			game = new Game(name);
		}
		
		if (isDead)
			return;
		
		if (inGame)
			game.update(screen);

		if (spaceTriggered)
			spaceTriggered = false;
		
		if (screen.space && !spaceDown)
		{
			spaceTriggered = true;
			spaceDown = true;
		}
		
		if (!screen.space && spaceDown)
			spaceDown = false;
		
		if (inStart && spaceTriggered)
		{
			inExpli = true;
			inStart = false;
			Audio.beep.play();
		}
		else if (inExpli && spaceTriggered)
		{
			inTuto = true;
			inExpli = false;
			Audio.beep.play();
		}
		else if (inTuto && spaceTriggered)
		{
			inGame = true;
			inTuto = false;
			Audio.beep.play();
		}
	}
	
	public void render()
	{
		if (inGame)
			game.render(screen);
		else if (inStart)
			screen.drawTexture(0, 0, Texture.START_MENU, false);
		else if (inExpli)
			screen.drawTexture(0, 0, Texture.EXPLI_MENU, false);
		else if (inTuto)
			screen.drawTexture(0, 0, Texture.TUTO_MENU, false);
		
		for (int i = 0; i < WIDTH * HEIGHT; i++)
		{
			pixels[i] = screen.pixels[i];
			screen.pixels[i] = 0;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		renderGui(g);
		g.setFont(new Font("Phosphate", Font.PLAIN, 30));
		g.setColor(Color.WHITE);
		g.dispose();
		bs.show();
	}
	
	public void renderGui(Graphics g)
	{
		if (inGame)
			game.renderGui(g);
	}
	
	public void start()
	{
		runnable = true;
		new Thread(this).start();
	}
	
	public void run()
	{
		init();
		long before = System.nanoTime();
		long now = 0;
		double elapsed = 0.0;
		double tickTime = 1000000000.0 / 60.0;
		
		while (runnable)
		{
			now = System.nanoTime();
			elapsed = now - before;
			if (elapsed > tickTime)
			{
				update();
				render();
				before += tickTime;
			}
			else
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.exit(0);
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		
		Dimension dim = new Dimension(WIDTH, HEIGHT);
		main.setPreferredSize(dim);
		main.setMinimumSize(dim);
		main.setMaximumSize(dim);
		
		JFrame frame = new JFrame("LD35");
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(main);
		frame.pack();
		frame.setVisible(true);
		
		main.start();
	}
}
