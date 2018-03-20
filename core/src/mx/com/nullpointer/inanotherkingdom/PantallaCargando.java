package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import mx.com.nullpointer.niveles.Nivel_Inicial;
import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by MarinaHaro on 13/03/18.
 */

public class PantallaCargando extends GenericScreen {

    private Main game;

    private Texture texturaCargando;
    private Sprite spriteCargando;

    private AssetManager assetManager;

    public PantallaCargando(Main game){
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {

        assetManager.load("cargando.png", Texture.class);
        assetManager.finishLoading();
        texturaCargando = assetManager.get("cargando.png");
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO/2- spriteCargando.getWidth()/2, ALTO/2-spriteCargando.getHeight()/2);

        cargarRecursos();

    }

    //Rescursos de la siguiente pantalla
    private void cargarRecursos() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("map/nivelCero.tmx", TiledMap.class);
        manager.finishLoading();

    }


    @Override
    public void render(float delta) {

        actualizarCarga();

        clearScreen();
        spriteCargando.setRotation(spriteCargando.getRotation()+10);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        spriteCargando.draw(batch);
        batch.end();
    }


    //Revisa como va la carga de los assets
    private void actualizarCarga() {
        if(assetManager.update()){
            game.setScreen(new Nivel_Inicial(game));
        } else{
            float avance = assetManager.getProgress();
        }
    }

    @Override
    public void dispose() {
        texturaCargando.dispose();
        assetManager.unload("cargando.png");
    }


}
