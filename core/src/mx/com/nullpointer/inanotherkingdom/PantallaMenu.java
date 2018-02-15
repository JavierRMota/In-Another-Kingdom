package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
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

import mx.com.nullpointer.niveles.Nivel_Inicial;
import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by Carlos Carbajal on 05-feb-18.
 */

class PantallaMenu extends GenericScreen {
    private final Main game;


    //ESCENA para el MENU
    private Stage escenaMenu;


    //Texturas
    private Texture texturaTitulo;
    private Texture texturaFondo;

    //Musica
    private com.badlogic.gdx.audio.Music musicaMenus;


    public PantallaMenu(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        crearCamara();
        cargarTexturas();
        crearObjetos();

        //Reproduce la musica
        musicaMenus.play();

    }

    private void cargarTexturas() {
        //texturaFondo = new Texture("");
        texturaTitulo = new Texture("logo.png");

    }

    private void crearObjetos() {
        escenaMenu = new Stage(view);

        //Botón Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("play.png")));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(new Texture("playpress.png")));

        ImageButton btnPlay = new ImageButton(trdPlay,trdPlayPress);
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2, ALTO/2 - btnPlay.getHeight()/2);

        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Nivel_Inicial(game));
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

        //Botón Ajustes
        TextureRegionDrawable trdAjustes = new TextureRegionDrawable(new TextureRegion(new Texture("ajustes.png")));
        TextureRegionDrawable trdAjustesPress = new TextureRegionDrawable(new TextureRegion(new Texture("ajustesPress.png")));
        ImageButton btnAjustes = new ImageButton(trdAjustes,trdAjustesPress);
        btnAjustes.setPosition( btnAjustes.getWidth()/2, btnAjustes.getHeight()/2);

        btnAjustes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaAjustes(game));
            }
        });
        escenaMenu.addActor(btnAjustes);

        //Botón Pregunta
        TextureRegionDrawable trdPregunta = new TextureRegionDrawable(new TextureRegion(new Texture("question.png")));
        TextureRegionDrawable trdPreguntaPress = new TextureRegionDrawable(new TextureRegion(new Texture("questionPress.png")));

        ImageButton btnPregunta = new ImageButton(trdPregunta,trdPreguntaPress);
        btnPregunta.setPosition( ANCHO - 3*btnPregunta.getWidth()/2, btnPregunta.getHeight()/2);

        btnPregunta.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaAcercade(game));
            }
        });
        escenaMenu.addActor(btnPregunta);

        //Añadiendo la musica
        musicaMenus= Gdx.audio.newMusic(Gdx.files.internal("loop.mp3"));
        musicaMenus.setLooping(true);
        musicaMenus.setVolume(.5f);
    }


    private void crearCamara() {
        camera = new OrthographicCamera(ANCHO, ALTO);
        camera.position.set(ANCHO/2, ALTO/2, 0);
        camera.update();
        view = new StretchViewport(ANCHO, ALTO, camera);
    }

    @Override
    public void render(float delta) {
        clearScreen(50/255f,158/255f,218/255f);


        batch.setProjectionMatrix(camera.combined);
        escenaMenu.draw();



        batch.begin();
        //Dibujando el fondo
        //batch.draw(texturaFondo,0,0 );

        //Dibujando el título
        batch.draw(texturaTitulo, ANCHO/2 - texturaTitulo.getWidth()/2, ALTO/2 + texturaTitulo.getHeight()/2);
        batch.end();


    }


}
