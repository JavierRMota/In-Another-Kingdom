package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.PantallaMenu;

/**
 * Created by MarinaHaro on 12/02/18.
 */

class PantallaCreditos implements Screen{
    private final Main game;

    //Cámara
    private OrthographicCamera camara;
    private Viewport view;

    //Escena para creditos
    private Stage escenaCreditos;

    //Batch
    private SpriteBatch batch;

    public PantallaCreditos(Main game) {
        this.game = game;
    }


    @Override
    public void show() {
        crearCamara();
        batch = new SpriteBatch();
    }


    private void crearCamara() {
        camara = new OrthographicCamera(PantallaMenu.ANCHO, PantallaMenu.ALTO);
        camara.position.set(PantallaMenu.ANCHO/2, PantallaMenu.ALTO/2, 0);
        camara.update();
        view = new StretchViewport(PantallaMenu.ANCHO, PantallaMenu.ALTO, camara);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50/255f,158/255f,218/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        escenaCreditos.draw();

    }

    @Override
    public void resize(int width, int height) {
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
