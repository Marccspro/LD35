package fr.ld35.main.game;

import java.util.Random;

import fr.ld35.main.Main;
import fr.ld35.main.Vec2;
import fr.ld35.main.gfx.Screen;
import fr.ld35.main.gfx.Texture;

public class Bottle
{
	private Random rand;

	public int radiation;
	public int fat;
	public int infection;
	
	public	int x, y;
	public int w, h;
	private float size;
	public Vec2[] ip;
	public int[] is;
	boolean hovered;
	private int texture;
	float r;
	
	public Bottle()
	{
		r = (float)Math.random();
		rand = new Random();
		texture = rand.nextInt(15);
		radiation = rand.nextInt(2);
		fat = rand.nextInt(2);
		infection = rand.nextInt(2);
		
		x = Main.WIDTH * 7/12;
		y = -100;
		w = 250;
		h = 400;
		
		ip = new Vec2[]{
			new Vec2(rand.nextFloat() * w * 0.7f + w / 4, rand.nextFloat() * h * 0.7f + h / 4), 
			new Vec2(rand.nextFloat() * w * 0.7f + w / 4, rand.nextFloat() * h * 0.7f + h / 4), 
			new Vec2(rand.nextFloat() * w * 0.7f + w / 4, rand.nextFloat() * h * 0.7f + h / 4)
		};
		
		is = new int[] {
				rand.nextInt(30) + 30, rand.nextInt(30) + 30, rand.nextInt(30) + 30
		};
		size = 0.9f;
	}
	
	public boolean isHovered(int mx, int my)
	{
		if (mx > x - w / 4 && my > y - h / 2 && mx < x + w / 4 && my < y + h / 2)
			return hovered = true;
		return hovered = false;
	}
	
	public void update(Screen screen)
	{
		if (hovered)
			size += (1.5f - size) * 0.2f;
		
		size += (1f - size) * 0.2f;
	}
	
	public void render(Screen screen, Player player)
	{
		float sizeRatio = (1 - Math.abs((float)(x - (float) Main.WIDTH / 2.0) / (float)Main.WIDTH) * 2) * size;
		screen.drawTexture(x, y, (int) (w * sizeRatio), (int) (h * sizeRatio), Texture.bottles[texture], true);
		
//		screen.drawTexture(x + (int) ((ip[0].x - w/2) * sizeRatio), y + (int) ((ip[0].y - h/2) * sizeRatio), (int) (is[0] * sizeRatio), (int) (is[0] * sizeRatio), Texture.FAT_ICON, true);
//		screen.drawTexture(x + (int) ((ip[1].x - w/2) * sizeRatio), y + (int) ((ip[1].y - h/2) * sizeRatio), (int) (is[1] * sizeRatio), (int) (is[1] * sizeRatio), Texture.RADIATION_ICON, true);
//		screen.drawTexture(x + (int) ((ip[2].x - w/2) * sizeRatio), y + (int) ((ip[2].y - h/2) * sizeRatio), (int) (is[2] * sizeRatio), (int) (is[2] * sizeRatio), Texture.INFECTION_ICON, true);
		System.out.println("fat: " + fat + " rad: " + radiation + " inf: " + infection);
		if (fat == 0)
				screen.drawTexture(x, y + (int) (-60 * sizeRatio), (int) (60 * sizeRatio), (int) (60 * sizeRatio), Texture.FAT_ICON_A_0, true);
		else if (fat == 1)
				screen.drawTexture(x, y + (int) (-60 * sizeRatio), (int) (60 * sizeRatio), (int) (60 * sizeRatio), Texture.FAT_ICON_B_1, true);
		
		if (radiation == 0)
			screen.drawTexture(x, y + (int) (0 * sizeRatio), (int) (60 * sizeRatio), (int) (60 * sizeRatio), Texture.RADIATION_ICON_0, true);
		else if (radiation == 1)
			screen.drawTexture(x, y + (int) (0 * sizeRatio), (int) (60 * sizeRatio), (int) (60 * sizeRatio), Texture.RADIATION_ICON_1, true);
		
		if (infection == 0)
			screen.drawTexture(x, y + (int) (60 * sizeRatio), (int) (60 * sizeRatio), (int) (60 * sizeRatio), Texture.INFECTION_ICON_0, true);
		else if (infection == 1)
			screen.drawTexture(x, y + (int) (60 * sizeRatio), (int) (60 * sizeRatio), (int) (60 * sizeRatio), Texture.INFECTION_ICON_1, true);
	}
}
