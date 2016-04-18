package fr.ld35.main.game;

import java.awt.Graphics;

import fr.ld35.main.Vec2;
import fr.ld35.main.gfx.Screen;
import fr.ld35.main.gfx.Texture;

public class Button
{
	public Vec2 pos;
	public Vec2 size;
	public float scale = 1;
	public Texture texture;
	public String text;
	public boolean hovered;
	public boolean click;
	
	public Button(Texture texture, int x, int y)
	{
		this.texture = texture;
		this.pos = new Vec2(x, y);
		this.size = new Vec2(texture.width, texture.height);
	}
	
	public Button(String text, int x, int y, int w, int h)
	{
		this.text = text;
		this.pos = new Vec2(x, y);
		this.size = new Vec2(w, h);
	}
	
	public boolean isHovered(int mx, int my)
	{
		if (mx > pos.x - size.x && my > pos.y - size.y && mx < pos.x + size.x && my < pos.y + size.y)
			return hovered = true;
		return hovered = false;
	}
	
	public void update(Screen screen)
	{
		isHovered(screen.mouseX, screen.mouseY);
		if (hovered)
			scale += (1.4f - scale) * 0.2f;
		if (click)
			scale = 1.6f;
		scale += (1f - scale) * 0.2f;
		
		click = false;
		if (hovered && screen.mouse)
			click = true;
		
	}
	
	public void render(Screen screen)
	{
		if (texture != null)
			screen.drawTexture((int)pos.x, (int)pos.y, scale, texture, true);
	}
	
	public void renderGui(Graphics g)
	{
		if (text != null)
			g.drawString(text, (int) pos.x, (int) pos.y); 
	}
}
