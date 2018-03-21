package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import mx.com.nullpointer.utils.GenericScreen;


public class Main extends Game {

	private final AssetManager assetManager = new AssetManager();

	
	@Override
	public void create () {

		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		setScreen(new LoadingScreen(this, GenericScreen.MENU));

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
