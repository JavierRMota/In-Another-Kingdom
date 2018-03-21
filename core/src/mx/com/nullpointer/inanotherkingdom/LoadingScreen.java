package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import mx.com.nullpointer.niveles.LevelZero;
import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by MarinaHaro on 13/03/18.
 */

public class LoadingScreen extends GenericScreen {

    private final Main game;
    private Texture loadingTexture;
    private Sprite loadingSprite;
    private final AssetManager assetManager;
    private int screen;

    public LoadingScreen(Main game, int screen){
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.screen = screen;
    }

    @Override
    public void show() {
        loadingTexture = new Texture("cargando.png");
        loadingSprite = new Sprite(loadingTexture);
        loadingSprite.setPosition(WIDTH /2- loadingSprite.getWidth()/2, HEIGHT /2- loadingSprite.getHeight()/2);
        loadResources();

    }

    //Rescursos de la siguiente pantalla
    private void loadResources() {
        switch (screen)
        {

        }
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("map/nivelCero.tmx", TiledMap.class);
        assetManager.finishLoading();
    }


    @Override
    public void render(float delta) {

        actualizarCarga();

        clearScreen();
        loadingSprite.setRotation(loadingSprite.getRotation()+10);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        loadingSprite.draw(batch);
        batch.end();
    }


    //Revisa como va la carga de los assets
    private void actualizarCarga() {


        if(assetManager.update()){
            game.setScreen(new LevelZero(game));

        } else{
            float avance = assetManager.getProgress();
            Gdx.app.log("Avance:",avance+"");
        }
    }

    @Override
    public void dispose() {
        loadingTexture.dispose();
        assetManager.unload("cargando.png");
    }


}
