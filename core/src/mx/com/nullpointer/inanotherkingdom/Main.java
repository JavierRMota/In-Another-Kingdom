package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;


import mx.com.nullpointer.utils.GenericScreen;


public class Main extends Game {

	private final AssetManager assetManager = new AssetManager();
	private Music music;

	
	@Override
	public void create () {
		setScreen(new LoadingScreen(this, GenericScreen.MENU));


	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
	public void changeMusic(int screen)
	{
		switch (screen)
		{
			case GenericScreen.MENU:
				if(music!= null && music != assetManager.get("music/menu.mp3"))
				{
					music.stop();
				}
				else if(music != null)
				{
					break;
				}
				music = assetManager.get("music/menu.mp3");
				music.play();
				break;
			case GenericScreen.LVLZERO:
			case GenericScreen.LVLONE:
				if(music!=null)
				{
					music.stop();
				}
				music = assetManager.get("music/nivelUno.mp3");
				music.play();
				break;
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		assetManager.clear(); //Esto pasa al final de la aplicación
	}

	public void stopMusic() {
		if(music!=null)
		{
			music.stop();
		}
	}
}
