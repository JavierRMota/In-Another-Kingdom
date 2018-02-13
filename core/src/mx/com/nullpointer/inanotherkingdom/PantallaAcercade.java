package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Marina Haro on 12-feb-18.
 */

class PantallaAcercade implements Screen {
    private final Main game;

    //Cámara
    private OrthographicCamera camara;
    private Viewport view;

    //Batch
    private SpriteBatch batch;

    //Escena para acerca de
    private Stage escenaAcercade;


    public PantallaAcercade(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        crearCamara();
        crearAjustes();
        batch = new SpriteBatch();
        
    }

    private void crearAjustes() {
        escenaAcercade = new Stage(view);

        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("back.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("backPress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()/2,PantallaMenu.ALTO - btnBack.getHeight());

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));

            }
        });
        escenaAcercade.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaAcercade);
    }

    private void crearCamara() {
        camara = new OrthographicCamera(PantallaMenu.ANCHO, PantallaMenu.ALTO);
        camara.position.set(PantallaMenu.ANCHO/2, PantallaMenu.ALTO/2, 0);
        camara.update();
        view = new StretchViewport(PantallaMenu.ANCHO, PantallaMenu.ALTO, camara);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(25/255f,158/255f,218/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        escenaAcercade.draw();
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
