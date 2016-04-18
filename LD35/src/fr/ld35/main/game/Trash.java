package fr.ld35.main.game;

import fr.ld35.main.Audio;
import fr.ld35.main.Main;
import fr.ld35.main.Vec2;
import fr.ld35.main.gfx.Screen;
import fr.ld35.main.gfx.Texture;

public class Trash
{
	public Vec2 pos;
	private float size;
	private Texture[] tex;
	private int texIndex = 0;
	private boolean trashed = false;
	private int time = 0;
	
	public Trash()
	{
		pos = new Vec2(Main.WIDTH - 120, Main.HEIGHT / 2 + 100);
		size = 1;
		
		tex = new Texture[14];
		for (int i = 0; i < 14; i++)
		{
			String num = "" + (i + 1);
			if (i + 1 < 10)
				num = "0" + (i + 1);
			
			tex[i] = new Texture("/new/trash_anime/poubelle_" + num + ".png");
		}
	}
	
	public void update(Screen screen)
	{
		size += (1 - size) * 0.1f;
		if (trashed)
		{
			if (texIndex >= 13)
			{
				texIndex = 0;
				trashed = false;
			}
			else
			{
				time++;
				if (time % 2 == 0)
					texIndex++;
			}
		}
	}
	
	public void add(Bottle bottle)
	{
		trashed = true;
		Audio.trash.play();
	}
	
	public void render(Screen screen)
	{
		screen.drawTexture((int) pos.x, (int) pos.y, size, tex[texIndex], true);
	}
}
