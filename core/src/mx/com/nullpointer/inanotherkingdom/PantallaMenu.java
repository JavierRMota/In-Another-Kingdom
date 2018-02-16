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
        texturaFondo = new Texture("background/menubg.png");
        texturaTitulo = new Texture("logo.png");

    }

    private void crearObjetos() {
        escenaMenu = new Stage(view);

        //Botón Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtn.png")));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtnpress.png")));

        ImageButton btnPlay = new ImageButton(trdPlay,trdPlayPress);
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2, ALTO/2 - btnPlay.getHeight());

        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Nivel_Inicial(game));
            }
        });
        escenaMenu.addActor(btnPlay);

        //Botón Levels
        TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(new Texture("btn/levelsbtn.png")));
        TextureRegionDrawable trdLevelsPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/levelsbtnpress.png")));

        ImageButton btnLevels = new ImageButton(trdLevels,trdLevelsPress);
        btnLevels.setPosition(ANCHO/2 - btnLevels.getWidth()/2, ALTO/2 - btnLevels.getHeight()*2.5f);

        btnLevels.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));
            }
        });
        escenaMenu.addActor(btnLevels);


        //Botón Arsenal
        TextureRegionDrawable trdArsenal = new TextureRegionDrawable(new TextureRegion(new Texture("btn/arsenalbtn.png")));
        TextureRegionDrawable trdArsenalPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/arsenalbtnpress.png")));
        ImageButton btnArsenal = new ImageButton(trdArsenal,trdArsenalPress);
        btnArsenal.setPosition(ANCHO/2 - btnArsenal.getWidth()/2, ALTO/2 - btnArsenal.getHeight()*4f);

        btnArsenal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaArsenal(game));
            }
        });
        escenaMenu.addActor(btnArsenal);


        //Botón Ajustes
        TextureRegionDrawable trdAjustes = new TextureRegionDrawable(new TextureRegion(new Texture("btn/ajustesbtn.png")));
        TextureRegionDrawable trdAjustesPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/ajustesbtnpress.png")));
        ImageButton btnAjustes = new ImageButton(trdAjustes,trdAjustesPress);
        btnAjustes.setPosition( 3*btnAjustes.getWidth()/2, 2.5f*btnAjustes.getHeight()/2);

        btnAjustes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaAjustes(game));
            }
        });
        escenaMenu.addActor(btnAjustes);

        //Botón Pregunta
        TextureRegionDrawable trdPregunta = new TextureRegionDrawable(new TextureRegion(new Texture("btn/aboutbtn.png")));
        TextureRegionDrawable trdPreguntaPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/aboutbtnpress.png")));

        ImageButton btnPregunta = new ImageButton(trdPregunta,trdPreguntaPress);
        btnPregunta.setPosition( ANCHO - 5*btnPregunta.getWidth()/2, 2.5f*btnPregunta.getHeight()/2);

        btnPregunta.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaAcercade(game));
            }
        });
        escenaMenu.addActor(btnPregunta);

        Gdx.input.setInputProcessor(escenaMenu);


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
        clearScreen();


        batch.setProjectionMatrix(camera.combined);




        batch.begin();
        batch.draw(texturaFondo,0 ,0);
        //Dibujando el título
        batch.draw(texturaTitulo, ANCHO/2 - texturaTitulo.getWidth()/2, ALTO/2 );
        batch.end();
        escenaMenu.draw();


    }


}
