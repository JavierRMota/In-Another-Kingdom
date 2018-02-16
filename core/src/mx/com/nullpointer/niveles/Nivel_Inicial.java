package mx.com.nullpointer.niveles;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.Main;

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

    //Textura del mapa
    private Texture background = new Texture("fondoN1.png");
    private Texture path = new Texture("caminoN1.png");

    public Nivel_Inicial(Main game){this.game =game;}
    @Override
    public void show() {
        createHUD();
        laurence = new MainCharacter(new Texture("laurence_running.png"));

    }

    private void createHUD() {
        cameraHUD = new OrthographicCamera(ANCHO,ALTO);
        cameraHUD.position.set(ANCHO/2,ALTO/2,0);
        cameraHUD.update();
        viewHUD = new StretchViewport(ANCHO,ALTO,cameraHUD);



    }

    @Override
    public void render(float delta) {
        laurence.move(4);
        updateHUD();


        //Borrar pantalla
        clearScreen();

        //Projection matrix
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background,0,0);
        batch.draw(path,0,0);
        laurence.render(batch);
        batch.end();



    }

    private void updateHUD() {
        //Para que siga a Laurence
        float posX = laurence.getX();
       /* if(posX < ANCHO/4)
        {
            camera.position.set(ANCHO/2, ALTO/2, 0);
        } else if (posX > ANCHO/4) {   // Última mitad de la pantalla
            camera.position.set(3*ANCHO/4,cameraHUD.position.y,0);
        } else {    // En 'medio' del mapa
            camera.position.set(posX,cameraHUD.position.y,0);
        }*/
       if(posX>ANCHO/3)
       camera.position.set(posX+ ANCHO/3,camera.position.y,0);
       //else
        //   camera.position.set(posX+ANCHO/2,camera.position.y,0);

        camera.update();
    }

    @Override
    public void resize(int width, int height) {
    viewHUD.update(width,height);
    view.update(width,height);

    }

}
