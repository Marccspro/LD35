package fr.ld35.main.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture
{
	public static final Texture BOTTLE = new Texture("/new/flacon_test.png");
	
	public static final Texture INFECTION_ICON_0 = new Texture("/new/symboles/symbol_infect_0.png");
	public static final Texture RADIATION_ICON_0 = new Texture("/new/symboles/symbol_rad_0.png");
	public static final Texture FAT_ICON_A_0 = new Texture("/new/symboles/symbol_fat_a_0.png");
	public static final Texture FAT_ICON_B_0 = new Texture("/new/symboles/symbol_fat_b_0.png");
	
	public static final Texture INFECTION_ICON_1 = new Texture("/new/symboles/symbol_infect_1.png");
	public static final Texture RADIATION_ICON_1 = new Texture("/new/symboles/symbol_rad_1.png");
	public static final Texture FAT_ICON_A_1 = new Texture("/new/symboles/symbol_fat_a_1.png");
	public static final Texture FAT_ICON_B_1 = new Texture("/new/symboles/symbol_fat_b_1.png");

	public static final Texture PLAYER_BG = new Texture("/new/export_interface/scanner_perso.png");
	public static final Texture CARDIO_BACK = new Texture("/new/export_interface/electrocardio_back.png");
	public static final Texture CARDIO_FRONT = new Texture("/new/export_interface/electrocardio_front.png");
	public static final Texture TRASH_PAD = new Texture("/new/export_interface/socle.png");
	public static final Texture ARROW_LEFT = new Texture("/new/export_interface/button_arrow_left.png");
	public static final Texture ARROW_RIGHT = new Texture("/new/export_interface/button_arrow_right.png");
	
	public static final Texture START_MENU = new Texture("/new/menu/ecran_titre_font.png");
	public static final Texture TUTO_MENU = new Texture("/new/menu/ecran_tuto.png");
	public static final Texture EXPLI_MENU = new Texture("/new/menu/ecran_but.png");
	
	public static final Texture DEAD_MSG = new Texture("/new/menu/YOU_DIED.png");
	public static final Texture WIN_MSG = new Texture("/new/menu/satisfied.png");
	public static final Texture REPLAY_MSG = new Texture("/new/menu/replay.png");
	
	public static Texture[] bottles;
	
	public int[] pixels;
	public int width, height;
	
	static 
	{
		bottles = new Texture[15];
		for (int i = 0; i < bottles.length; i++)
		{
			String num = "" + i;
			if (i < 10)
				num = "0" + i;
			bottles[i] = new Texture("/new/flacons/flacon_" + num + ".png");
		}
	}
	
	public Texture(String path)
	{
		try
		{
			BufferedImage image = ImageIO.read(Texture.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
