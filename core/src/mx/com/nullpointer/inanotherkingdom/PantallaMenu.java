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
 * Created by Carlos Carbajal on 05-feb-18.
 */

class PantallaMenu implements Screen {
    private final Main game;

    //Cámara
    private OrthographicCamera camara;
    private Viewport view;

    //ESCENA para el MENU
    private Stage escenaMenu;

    //Batch
    private SpriteBatch batch;

    //MUNDO
    public static final float ANCHO = 1280, ALTO = 720;

    public PantallaMenu(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();

    }

    private void crearMenu() {
        escenaMenu = new Stage(view);

        //Botón Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("play.png")));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(new Texture("Playpress.png")));

        ImageButton btnPlay = new ImageButton(trdPlay,trdPlayPress);
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2, ALTO/2 - btnPlay.getHeight()/2);

        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));
            }
        });
        escenaMenu.addActor(btnPlay);

        //Botón Levels
        TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(new Texture("levels.png")));
        TextureRegionDrawable trdLevelsPress = new TextureRegionDrawable(new TextureRegion(new Texture("levelsPress.png")));

        ImageButton btnLevels = new ImageButton(trdLevels,trdLevelsPress);
        btnLevels.setPosition(ANCHO/2 - btnLevels.getWidth()/2, ALTO/2 - btnLevels.getHeight()*2);

        btnLevels.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));
            }
        });
        escenaMenu.addActor(btnLevels);

        //Botón Ajustes
        TextureRegionDrawable trdAjustes = new TextureRegionDrawable(new TextureRegion(new Texture("ajustes.png")));
        TextureRegionDrawable trdAjustesPress = new TextureRegionDrawable(new TextureRegion(new Texture("ajustesPress.png")));
        ImageButton btnAjustes = new ImageButton(trdAjustes,trdAjustesPress);
        btnAjustes.setPosition(0, 0);

        btnAjustes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaAjustes(game));
            }
        });
        escenaMenu.addActor(btnAjustes);



        //Botón Arsenal
        TextureRegionDrawable trdArsenal = new TextureRegionDrawable(new TextureRegion(new Texture("arsenal.png")));
        TextureRegionDrawable trdArsenalPress = new TextureRegionDrawable(new TextureRegion(new Texture("arsenalPress.png")));
        ImageButton btnArsenal = new ImageButton(trdArsenal,trdArsenalPress);
        btnArsenal.setPosition(ANCHO/2 - btnArsenal.getWidth()/2, ALTO/2 - btnArsenal.getHeight()*3.5f);

        btnArsenal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaArsenal(game));
            }
        });
        escenaMenu.addActor(btnArsenal);
        Gdx.input.setInputProcessor(escenaMenu);
    }


    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2, ALTO/2, 0);
        camara.update();
        view = new StretchViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(25/255f,158/255f,218/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        escenaMenu.draw();

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
