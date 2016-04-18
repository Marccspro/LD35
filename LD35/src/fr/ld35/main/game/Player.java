package fr.ld35.main.game;

import fr.ld35.main.Audio;
import fr.ld35.main.Main;
import fr.ld35.main.Vec2;
import fr.ld35.main.gfx.Screen;
import fr.ld35.main.gfx.Texture;

public class Player
{
	public static final int BODY_COUNT = 7;
	public static final int HAIR_COUNT = 3;
	public static final int FACE_COUNT = 5;
	public static final int RAD_COUNT = 5;
	public static final int SKL_COUNT = 7;

	public static final int SKIN_COLOR = 0xFFFAD489;
	public static final int SHADOW_COLOR = 0xFFA48C5C;
	public static final int INFECTION_SKIN_COLOR = 0xFFF5F9DE;
	public static final int INFECTION_SHADOW_COLOR = 0xFFA5A799;
	
	public boolean dead = false;
	
	public Texture[] playerBody;
	public Texture[] playerHair;
	public Texture[] playerFace;
	
	public Texture[] radTexture;
	public Texture[] sklTexture;
	
	public int radiation, fat, infection;
	public int body, hair, face, rad;
	public float radFactor = 0f;
	public Vec2 pos;
	float size;
	public int startBPM = 80;
	
	public Player()
	{
		fat = BODY_COUNT;
		playerBody = new Texture[BODY_COUNT];
		for (int i = 0; i < BODY_COUNT; i++)
		{
			playerBody[i] = new Texture("/new/corps_" + i + ".png");
		}
		playerHair = new Texture[HAIR_COUNT];
		for (int i = 0; i < HAIR_COUNT; i++)
		{
			playerHair[i] = new Texture("/new/cheveux_" + (i + 1) + ".png");
		}
		playerFace = new Texture[FACE_COUNT];
		for (int i = 0; i < FACE_COUNT; i++)
		{
			playerFace[i] = new Texture("/new/visage_" + (i + 1) + ".png");
		}
		radTexture = new Texture[RAD_COUNT];
		for (int i = 0; i < RAD_COUNT; i++)
		{
			radTexture[i] = new Texture("/new/radiations/rad_" + (i + 1) + ".png");
		}
		sklTexture = new Texture[SKL_COUNT];
		for (int i = 0; i < SKL_COUNT; i++)
		{
			sklTexture[i] = new Texture("/new/squelettes/corps_" + i + ".png");
		}
		pos = new Vec2(150, Main.HEIGHT / 2 + 20);
		size = 1;
	}
	
	public void update(Screen screen)
	{
		if (fat < 0) fat = 0;
		if (radiation < 0) radiation = 0;
		if (infection < 0) infection = 0;
		
		//System.out.println("Rad: " + radiation + " Fat: " + fat + " Infection: " + infection);
		
		if (radiation > 10)
			dead = true;
		if (infection > 10)
			dead = true;
		
		body = fat;
		hair = radiation + infection;
		face = infection;
		rad = (int) (radiation * 0.5f - 1);
		
		if (rad >= RAD_COUNT) rad = RAD_COUNT - 1;
		if (rad < 0) rad = 0;
		if (body >= BODY_COUNT) body = BODY_COUNT - 1;
		if (body < 0) body = 0;
		if (hair >= HAIR_COUNT) hair = HAIR_COUNT - 1;
		if (hair < 0) hair = 0;
		if (face >= FACE_COUNT) face = FACE_COUNT - 1;
		if (face < 0) face = 0;
		
		radFactor = (float)radiation / 10.0f - 0.5f;
		if (radFactor < 0)
			radFactor = 0;
		
		size += (1 - size) * 0.1f;
	}
	
	public void render(Screen screen)
	{
		float infectionColorFactor = (float)infection / 10;
		if (infectionColorFactor > 1)
			infectionColorFactor = 1;
		if (infectionColorFactor < 0)
			infectionColorFactor = 0;
		
		screen.drawTexture((int) pos.x, (int) pos.y, size, playerBody[body], true, infectionColorFactor, SKIN_COLOR, INFECTION_SKIN_COLOR, SHADOW_COLOR, INFECTION_SHADOW_COLOR);
		if (Math.random() < radFactor * 0.5f)
			screen.drawTexture((int) pos.x, (int) pos.y, size, sklTexture[body], true);
		screen.drawTexture((int) pos.x, (int) pos.y, size, playerHair[hair], true);
		screen.drawTexture((int) pos.x, (int) pos.y, size, playerFace[face], true);

	}
	
	public void add(Bottle bottle)
	{
		infection += bottle.infection == 0 ? -1 : 1;
		fat += bottle.fat == 0 ? -1 : 1;
		radiation += bottle.radiation == 0 ? -1 : 1;
		size = 1.2f;
		Audio.avaler.play();
	}
}
