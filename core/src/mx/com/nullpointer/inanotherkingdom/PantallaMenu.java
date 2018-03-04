package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import mx.com.nullpointer.utils.MusicController;

/**
 * Created by Carlos Carbajal on 05-feb-18.
 */

public class PantallaMenu extends GenericScreen {
    private final Main game;


    //ESCENA para el MENU
    private Stage escenaMenu;


    //Texturas
    private Texture texturaTitulo;
    private Texture texturaFondo;
    private Texture texturaFrente;
    private MusicController music;




    public PantallaMenu(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        crearCamara();
        cargarTexturas();
        crearObjetos();



    }

    private void cargarTexturas() {
        Preferences prefs = Gdx.app.getPreferences("Progress");
        int lastLevel = prefs.getInteger("lastLevel", 0);
        String levelTextureName;
        switch(lastLevel)
        {
            default:
                levelTextureName= "background/menu_bg_1.png";

        }

        texturaFondo = new Texture(levelTextureName);
        texturaTitulo = new Texture("logo.png");
        texturaFrente = new Texture("background/menu_bg_cover.png");

    }

    private void crearObjetos() {
        escenaMenu = new Stage(view);

        //Botón Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtn.png")));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtnpress.png")));

        ImageButton btnPlay = new ImageButton(trdPlay,trdPlayPress);
        btnPlay.setPosition(4*ANCHO/5 - btnPlay.getWidth()/2, 9*ALTO/16 - btnPlay.getHeight());

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
        btnLevels.setPosition(ANCHO-30 - 3*btnLevels.getWidth(), ALTO/2 - btnLevels.getHeight()*2.5f);

        btnLevels.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaNiveles(game));
            }
        });
        escenaMenu.addActor(btnLevels);


        //Botón Arsenal
        TextureRegionDrawable trdArsenal = new TextureRegionDrawable(new TextureRegion(new Texture("btn/arsenalbtn.png")));
        TextureRegionDrawable trdArsenalPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/arsenalbtnpress.png")));
        ImageButton btnArsenal = new ImageButton(trdArsenal,trdArsenalPress);
        btnArsenal.setPosition(ANCHO - 40 -4*btnArsenal.getWidth(), ALTO/2 - btnArsenal.getHeight()*2.5f );

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
        btnAjustes.setPosition( ANCHO -20- 2*btnAjustes.getWidth(), ALTO/2 - btnAjustes.getHeight()*2.5f);

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
        btnPregunta.setPosition( ANCHO-10-btnPregunta.getWidth(), ALTO/2 - btnPregunta.getHeight()*2.5f);

        btnPregunta.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaAcercade(game));
            }
        });
        escenaMenu.addActor(btnPregunta);

        Gdx.input.setInputProcessor(escenaMenu);

        //Creamos la música
        music = new MusicController("music/loop.mp3");

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
        batch.draw(texturaFrente,0,0);
        //Dibujando el título
        batch.draw(texturaTitulo, 4*ANCHO/5 - texturaTitulo.getWidth()/2, 5*ALTO/8 );
        batch.end();
        escenaMenu.draw();


    }


}
