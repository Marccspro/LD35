package fr.ld35.main.gfx;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Screen implements KeyListener, MouseListener, MouseMotionListener, FocusListener
{
	public int[] pixels;
	public int width, height;
	
	public int mouseX, mouseY;
	public boolean mouse;
	public boolean leftKey, rightKey;
	public boolean focused;
	public boolean space;
	
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
	}
	
	public void drawPixel(int x, int y, int color)
	{
		if (x < 0 || x >= width || y < 0 || y >= height)
			return;
		
		pixels[x + y * width] = color;
	}
	
	public void drawQuad(int xp, int yp, int w, int h, int color, boolean centered)
	{
		if (centered)
		{
			xp -= w / 2;
			yp -= h / 2;
		}
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				int xx = x + xp;
				int yy = y + yp;
				
				drawPixel(xx, yy, color);
			}	
		}
	}
	
	public void drawTexture(int xp, int yp, Texture tex, boolean centered)
	{
		int w = tex.width;
		int h = tex.height;
		if (centered)
		{
			xp -= w / 2;
			yp -= h / 2;
		}
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				int xx = x + xp;
				int yy = y + yp;
				int color = tex.pixels[x + y * tex.width];

				if (color != 0xff797979)
					drawPixel(xx, yy, color);
			}	
		}
	}
	
	public void drawTexture(int xp, int yp, float size, Texture tex, boolean centered)
	{
		int w = (int) (tex.width * size);
		int h = (int) (tex.height * size);
		if (centered)
		{
			xp -= w / 2;
			yp -= h / 2;
		}
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				int xx = x + xp;
				int yy = y + yp;
				int xt = (int) (((float) x / w) * tex.width);
				int yt = (int) (((float) y / h) * tex.height);
				int color = tex.pixels[xt + yt * tex.width];

				if (color != 0xff797979)
					drawPixel(xx, yy, color);
			}	
		}
	}
	
	public void drawTexture(int xp, int yp, float size, Texture tex, boolean centered, float replaceFactor, int... replace)
	{
		int w = (int) (tex.width * size);
		int h = (int) (tex.height * size);
		if (centered)
		{
			xp -= w / 2;
			yp -= h / 2;
		}
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				int xx = x + xp;
				int yy = y + yp;
				int xt = (int) (((float) x / w) * tex.width);
				int yt = (int) (((float) y / h) * tex.height);
				
				int color = tex.pixels[xt + yt * tex.width];
				for (int i = 0; i < replace.length; i += 2)
				{
					int current = replace[i];
					int newColor = replace[i+1];
					
					if (color == current)
					{
						float cr = (color & 0xff0000) >> 16;
						float cg = (color & 0xff00) >> 8;
						float cb = (color & 0xff);
						
						float nr = (newColor & 0xff0000) >> 16;
						float ng = (newColor & 0xff00) >> 8;
						float nb = (newColor & 0xff);
						
						int fr = (int) (cr + (nr - cr) * replaceFactor);
						int fg = (int) (cg + (ng - cg) * replaceFactor);
						int fb = (int) (cb + (nb - cb) * replaceFactor);
						
						color = fr << 16 | fg << 8 | fb;
					}
				}
				if (color != 0xff797979)
					drawPixel(xx, yy, color);
			}	
		}
	}
	
	public void drawTexture(int xp, int yp, int w, int h, Texture tex, boolean centered)
	{
		if (centered)
		{
			xp -= w / 2;
			yp -= h / 2;
		}
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				int xx = x + xp;
				int yy = y + yp;
				
				int xt = (int) (((float) x / w) * tex.width);
				int yt = (int) (((float) y / h) * tex.height);
				int color = tex.pixels[xt + yt * tex.width];
				if (color != 0xff797979)
					drawPixel(xx, yy, color);
			}	
		}
	}
	
	public void drawTexture(int xp, int yp, int w, int h, Texture tex, boolean centered, int current, int replace)
	{
		if (centered)
		{
			xp -= w / 2;
			yp -= h / 2;
		}
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				int xx = x + xp;
				int yy = y + yp;
				
				int xt = (int) (((float) x / w) * tex.width);
				int yt = (int) (((float) y / h) * tex.height);
				int color = tex.pixels[xt + yt * tex.width];
				if (color == current)
					color = replace;
				if (color != 0xff797979)
					drawPixel(xx, yy, color);
			}	
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e)
	{
		
	}

	public void mouseEntered(MouseEvent e)
	{
		
	}

	public void mouseExited(MouseEvent e)
	{
		
	}

	public void mousePressed(MouseEvent e)
	{
		mouse = true;
	}

	public void mouseReleased(MouseEvent e)
	{
		mouse = false;
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightKey = true;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			leftKey = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			space = true;
	}

	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightKey = false;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			leftKey = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			space = false;
	}

	public void keyTyped(KeyEvent e)
	{

	}

	public void focusGained(FocusEvent arg0)
	{
		focused = true;
	}

	public void focusLost(FocusEvent arg0)
	{
		focused = false;
	}
}
