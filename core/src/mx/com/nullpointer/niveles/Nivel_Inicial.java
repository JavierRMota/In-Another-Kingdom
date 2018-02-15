package mx.com.nullpointer.niveles;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.PantallaMenu;
import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.MainCharacter;

/**
 * Created by mota on 2/12/18.
 */

public class Nivel_Inicial extends GenericScreen {
    private final Main game;

    //Mapas
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer render;
    private MainCharacter laurence;

    //Cámaras
    private OrthographicCamera cameraHUD;
    private Viewport viewHUD;
    private Stage buttons;
    private Stage pause;

    public Nivel_Inicial(Main game){this.game =game;}
    @Override
    public void show() {
        createHUD();

    }

    private void createHUD() {
        cameraHUD = new OrthographicCamera(PantallaMenu.ANCHO,PantallaMenu.ALTO);
        cameraHUD.position.set(PantallaMenu.ANCHO/2,PantallaMenu.ALTO/2,0);
        cameraHUD.update();
        viewHUD = new StretchViewport(PantallaMenu.ANCHO,PantallaMenu.ALTO,cameraHUD);



    }

    @Override
    public void render(float delta) {
        updateHUD();

        //Borrar pantalla
        clearScreen();

        //Projection matrix
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        laurence.render(batch);
        batch.end();



    }

    private void updateHUD() {
        //Para que siga a Laurence
        float posX = laurence.getX();
        if(posX<ANCHO/2)
        {
            cameraHUD.position.set(ANCHO/2, ALTO/2, 0);
        } else if (posX > ANCHO/2) {   // Última mitad de la pantalla
            cameraHUD.position.set(ANCHO,cameraHUD.position.y,0);
        } else {    // En 'medio' del mapa
            cameraHUD.position.set(posX,cameraHUD.position.y,0);
        }
        cameraHUD.update();
    }

    @Override
    public void resize(int width, int height) {
    viewHUD.update(width,height);
    view.update(width,height);

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
