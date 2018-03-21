package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.com.nullpointer.utils.Enemy;
import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.MusicController;

/**
 * Created by Carlos Carbajal on 05-feb-18.
 */

public class MenuScreen extends GenericScreen {
    private final Main game;
    private final AssetManager assetManager;


    //ESCENA para el MENU
    private Stage menuStage;


    //Texturas
    private Texture titleTexture;
    private Texture backgroundTexture;
    private Texture frontTexture;
    private Enemy bigDragon;




    public MenuScreen(Main game) {

        this.game = game;
        assetManager = game.getAssetManager();
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
                levelTextureName = "background/menu_bg_1.png";

        }

        assetManager.load(levelTextureName, Texture.class);
        assetManager.load("logo.png", Texture.class);
        assetManager.load("background/menu_bg_cover.png", Texture.class);


        assetManager.finishLoading();



        backgroundTexture = assetManager.get(levelTextureName);
        titleTexture = assetManager.get("logo.png");
        frontTexture = assetManager.get("background/menu_bg_cover.png");
         bigDragon = new Enemy(new Texture("characters/dragon_volando_tira.png"));

    }

    private void crearObjetos() {
        menuStage = new Stage(view);

        //Botón Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtn.png")));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtnpress.png")));

        ImageButton btnPlay = new ImageButton(trdPlay,trdPlayPress);
        btnPlay.setPosition(WIDTH - WIDTH /4 - btnPlay.getWidth()/2 + btnPlay.getWidth()/4, 9* HEIGHT /16 - btnPlay.getHeight());

        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game));
            }
        });
        menuStage.addActor(btnPlay);

        //Botón Levels
        TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(new Texture("btn/levelsbtn.png")));
        TextureRegionDrawable trdLevelsPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/levelsbtnpress.png")));

        ImageButton btnLevels = new ImageButton(trdLevels,trdLevelsPress);
        btnLevels.setPosition(WIDTH - 2*(WIDTH /6)- btnLevels.getWidth(), HEIGHT /2 - btnLevels.getHeight()*2.5f);

        btnLevels.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LevelsScreen(game));
            }
        });
        menuStage.addActor(btnLevels);


        //Botón Arsenal
        TextureRegionDrawable trdArsenal = new TextureRegionDrawable(new TextureRegion(new Texture("btn/arsenalbtn.png")));
        TextureRegionDrawable trdArsenalPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/arsenalbtnpress.png")));
        ImageButton btnArsenal = new ImageButton(trdArsenal,trdArsenalPress);
        btnArsenal.setPosition(WIDTH - 40 -4*btnArsenal.getWidth(), HEIGHT /2 - btnArsenal.getHeight()*2.5f );

        btnArsenal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new ArmoryScreen(game));
            }
        });
        //menuStage.addActor(btnArsenal);


        //Botón Ajustes
        TextureRegionDrawable trdAjustes = new TextureRegionDrawable(new TextureRegion(new Texture("btn/ajustesbtn.png")));
        TextureRegionDrawable trdAjustesPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/ajustesbtnpress.png")));
        ImageButton btnAjustes = new ImageButton(trdAjustes,trdAjustesPress);
        btnAjustes.setPosition( WIDTH - WIDTH /6 - btnAjustes.getWidth(), HEIGHT /2 - btnAjustes.getHeight()*2.5f);

        btnAjustes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new SettingsScreen(game));
            }
        });
        menuStage.addActor(btnAjustes);

        //Botón Pregunta
        TextureRegionDrawable trdPregunta = new TextureRegionDrawable(new TextureRegion(new Texture("btn/aboutbtn.png")));
        TextureRegionDrawable trdPreguntaPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/aboutbtnpress.png")));

        ImageButton btnPregunta = new ImageButton(trdPregunta,trdPreguntaPress);
        btnPregunta.setPosition( WIDTH -10-btnPregunta.getWidth(), HEIGHT /2 - btnPregunta.getHeight()*2.5f);

        btnPregunta.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new AboutScreen(game));
            }
        });
        menuStage.addActor(btnPregunta);

        Gdx.input.setInputProcessor(menuStage);

        //Creamos la música
        music = new MusicController("music/menu.mp3");


    }


    private void crearCamara() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH /2, HEIGHT /2, 0);
        camera.update();
        view = new StretchViewport(WIDTH, HEIGHT, camera);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        bigDragon.moveEnemy(delta,batch);
        batch.draw(frontTexture,0,0);
        //Dibujando el título
        batch.draw(titleTexture, 4* WIDTH /5 - titleTexture.getWidth()/2, 5* HEIGHT /8 );
        batch.end();
        menuStage.draw();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        titleTexture.dispose();
        frontTexture.dispose();
        menuStage.dispose();

        //AssetManager libera los recursos
        assetManager.unload("background/menu_bg_1.png");
        assetManager.unload("logo.png");
        assetManager.unload("background/menu_bg_cover.png");
    }
}
