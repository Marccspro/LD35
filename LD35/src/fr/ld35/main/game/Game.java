package fr.ld35.main.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import fr.ld35.main.Audio;
import fr.ld35.main.Main;
import fr.ld35.main.Vec2;
import fr.ld35.main.gfx.Screen;
import fr.ld35.main.gfx.Texture;

public class Game
{
	private Bottle bottle;
	private boolean bottleGrabbed;
	private Vec2 toPos;
	public Player player;
	private Trash trash;
	private boolean bottleRight, bottleLeft;
	private int tickTime = 0;
	private int time = 0;
	private int bgTickTime = 0;
	private int bgTime = 0;
	private Texture[] bg;
	private Texture[] timerTex;
	private Button left, right;
	private String name;
	public boolean win = false;
	private int timer = 60;
	private boolean sideChosed = false;
	private int startBPM = 80;
	
	private float timerScale = 1f;
	
	public Game(String name)
	{	
		this.name = name;
		left = new Button(Texture.ARROW_LEFT, 300, Main.HEIGHT / 2);
		right = new Button(Texture.ARROW_RIGHT, 528, Main.HEIGHT / 2);
		bg = new Texture[50];
		for (int i = 0; i < 50; i++)
		{
			String num = "" + i;
			if (i < 10)
				num = "0" + i;
			
			bg[i] = new Texture("/new/fond_anime/sun_ray02_000" + num + ".png");
		}
		timerTex = new Texture[5];
		for (int i = 0; i < 5; i++)
		{
			String num = "" + i;
			if (i < 10)
				num = "0" + i;
			
			timerTex[i] = new Texture("/new/timer/timer_" + num + ".png");
		}
		player = new Player();
		trash = new Trash();
		bottle = new Bottle();
		bottleGrabbed = false;
		Audio.music.play();
	}
	
	public void update(Screen screen)
	{
		if (player.dead)
			return;
		if (win)
			return;
		
		tickTime++;
		bgTickTime++;
		if (tickTime % 60 == 0)
		{
			time++;
			timer--;
			timerScale = 1.2f;
			Audio.bitTimer.play();
		}
		timerScale += (1 - timerScale) * 0.1f;
		if (timer <= 0)
			win = true;
		bottle.update(screen);
		trash.update(screen);
		player.update(screen);
		left.update(screen);
		right.update(screen);
		if (left.click)
			bottleLeft = true;
		if (right.click)
			bottleRight = true;
		if (bottle.isHovered(screen.mouseX, screen.mouseY) && ! bottleGrabbed)
		{
			if (screen.mouse)
			{
				bottleGrabbed = true;
			}
		}
		
		if (bottleGrabbed)
		{
			bottle.x = screen.mouseX;
			bottle.y = screen.mouseY;
			if (!screen.mouse)
				bottleGrabbed = false;
		}
		
		toPos = new Vec2(Main.WIDTH * 13/24 + 22, Main.HEIGHT / 2);
		if (bottle.x < Main.WIDTH * 5/12 && !screen.mouse)
		{
			bottleLeft = true;
		}
		else if (bottle.x > Main.WIDTH * 7/12 && !screen.mouse)
		{
			bottleRight = true;
		}
		
		if ((bottleRight || bottleLeft) && ! sideChosed)
		{
			Audio.beep.play();
			sideChosed = true;
		}
		
		if (screen.leftKey)
			bottleLeft = true;
		
		if (bottleLeft)
		{
			toPos = player.pos;
		}
		
		if (screen.rightKey)
			bottleRight = true;
		
		if (bottleRight)
		{
			toPos = trash.pos;
		}
		
		if (time >= 4)
		{
			toPos = player.pos;
		}
		
		if (toPos != null && !screen.mouse)
		{
			bottle.x += (toPos.x - bottle.x) * 0.1f;
			bottle.y += (toPos.y - bottle.y) * 0.1f;
			if (dist(toPos, bottle.x, bottle.y) < 20 && (bottleLeft))
			{				
				player.add(bottle);
				bottle = new Bottle();
				bottleRight = false;
				bottleLeft = false;
				time = 0;
				sideChosed = false;
			}
			else if (dist(toPos, bottle.x, bottle.y) < 20 && (bottleRight))
			{
				trash.add(bottle);  
				bottle = new Bottle();
				bottleRight = false;
				bottleLeft = false;
				time = 0;
				sideChosed = false;
			}
		}
	}
	
	public void render(Screen screen)
	{
		if (player.dead || win) screen.drawQuad(0, 0, screen.width, screen.height, 0x797979, false);
		if (!player.dead && !win) screen.drawTexture(0, 0, bg[(int)(((float)bgTickTime) * 0.5f) % 50], false);
		if (!player.dead && !win) screen.drawTexture(28, 10, Texture.CARDIO_BACK, false);
		if (!player.dead && !win) screen.drawTexture(28, 10, Texture.CARDIO_FRONT, false);
		screen.drawTexture(28, 10, Texture.PLAYER_BG, false);
		if (player.radiation > 0)screen.drawTexture(28, 10, player.radTexture[player.rad], false);
		if (!player.dead && !win) screen.drawTexture(547, 385, Texture.TRASH_PAD, false);
		if (!player.dead && !win) screen.drawQuad(0, screen.height - 30, screen.width, 30, 0x014627, false);
		player.render(screen);
		if (!player.dead && !win) trash.render(screen);
		if (!player.dead && !win) bottle.render(screen, player);
		if (!player.dead && !win) left.render(screen);
		if (!player.dead && !win) right.render(screen);
		if (!player.dead && !win) screen.drawTexture(650, 60, timerScale, timerTex[4 - time], true);
		if (player.dead) screen.drawTexture(300, Main.HEIGHT / 2 - 60, Texture.DEAD_MSG, false);
		if (win) screen.drawTexture(300, Main.HEIGHT / 2 - 60, Texture.WIN_MSG, false);
		if (player.dead || win) screen.drawTexture(430, Main.HEIGHT - 40, 0.5f, Texture.REPLAY_MSG, false);
	}
	
	public void renderGui(Graphics g)
	{
		left.renderGui(g);
		right.renderGui(g);
		g.setFont(new Font("Arial", 0, 20));
		g.setColor(Color.WHITE);
		if (!player.dead && !win) g.drawString("Name: " + name, 10, Main.HEIGHT - 7);
		if (!player.dead && !win) g.drawString("Time left: " + timer + "s", 520, Main.HEIGHT - 7);
	}
	
	public float dist(Vec2 v, float x, float y)
	{
		float xx = v.x - x;
		float yy = v.y - y;
		return (float) Math.sqrt(xx * xx + yy * yy);
	}
}
