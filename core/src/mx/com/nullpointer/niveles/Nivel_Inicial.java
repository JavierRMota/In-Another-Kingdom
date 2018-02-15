package mx.com.nullpointer.niveles;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.utils.MainCharacter;

/**
 * Created by mota on 2/12/18.
 */

public class Nivel_Inicial implements Screen {
    private final Main game;

    //Mapas
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer render;
    private MainCharacter laurence;

    //Cámaras
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage buttons;
    private Stage pause;

    public Nivel_Inicial(Main game){this.game =game;}
    @Override
    public void show() {
        createHUD();

    }

    private void createHUD() {


    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
