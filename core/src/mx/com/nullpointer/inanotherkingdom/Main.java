package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class Main extends Game {

	private final AssetManager assetManager = new AssetManager();
	
	@Override
	public void create () {

		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));


		//Pantalla de menú inicial
		Preferences prefs = Gdx.app.getPreferences("Progress");
		int lastLevel = prefs.getInteger("lastLevel", 0);
		if(lastLevel ==0)
			prefs.putInteger("lastLevel", 1);

		setScreen(new MenuScreen(this));

	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void dispose() {
		super.dispose();
		assetManager.clear(); //Esto pasa al final de la aplicación
	}
}
