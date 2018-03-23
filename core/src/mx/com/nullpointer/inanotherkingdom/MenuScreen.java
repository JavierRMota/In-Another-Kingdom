package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mx.com.nullpointer.utils.Enemy;
import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by Carlos Carbajal on 05-feb-18.
 */

public class MenuScreen extends GenericScreen {


    //ESCENA para el MENU
    private Stage menuStage;
    private int lastLevel;


    //Texturas
    private Texture titleTexture;
    private Texture backgroundTexture;
    private Texture frontTexture;

    //Enemigo
    private Enemy bigDragon;




    public MenuScreen(Main game) {
       super(game);
    }

    @Override
    public void show() {
        Preferences prefs = Gdx.app.getPreferences("Progress");
        lastLevel = prefs.getInteger("lastLevel", 0);
        cargarTexturas();
        crearObjetos();

    }

    private void cargarTexturas() {
        String levelTextureName;
        switch(lastLevel)
        {
            default:
                levelTextureName = "background/menu_bg_1.png";

        }
        backgroundTexture = assetManager.get(levelTextureName);
        titleTexture = assetManager.get("logo.png");
        frontTexture = assetManager.get("background/menu_bg_cover.png");


    }

    private void crearObjetos() {
        menuStage = new Stage(view);

        //Botón Play
        Texture playTexture=assetManager.get("btn/playbtn.png");
        Texture playPressTexture= assetManager.get("btn/playbtnpress.png");
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(playPressTexture));
        ImageButton btnPlay = new ImageButton(trdPlay,trdPlayPress);
        btnPlay.setPosition(WIDTH - WIDTH /4 - btnPlay.getWidth()/2 + btnPlay.getWidth()/4, 9* HEIGHT /16 - btnPlay.getHeight());
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,lastLevel+5));
            }
        });
        menuStage.addActor(btnPlay);

        //Botón Levels
        Texture levelsTexture=assetManager.get("btn/levelsbtn.png");
        Texture levelsPressTexture= assetManager.get("btn/levelsbtnpress.png");
        TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(levelsTexture));
        TextureRegionDrawable trdLevelsPress = new TextureRegionDrawable(new TextureRegion(levelsPressTexture));
        ImageButton btnLevels = new ImageButton(trdLevels,trdLevelsPress);
        btnLevels.setPosition(WIDTH - 2*(WIDTH /6)- btnLevels.getWidth(), HEIGHT /2 - btnLevels.getHeight()*2.5f);
        btnLevels.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LEVELS));
            }
        });
        menuStage.addActor(btnLevels);


        //Botón Arsenal
        /*
        Texture arsenalTexture=assetManager.get("btn/arsenalbtn.png");
        Texture arsenalPressTexture= assetManager.get("btn/arsenalbtnpress.png");
        TextureRegionDrawable trdArsenal = new TextureRegionDrawable(new TextureRegion(arsenalTexture));
        TextureRegionDrawable trdArsenalPress = new TextureRegionDrawable(new TextureRegion(arsenalPressTexture));
        ImageButton btnArsenal = new ImageButton(trdArsenal,trdArsenalPress);
        btnArsenal.setPosition(WIDTH - 40 -4*btnArsenal.getWidth(), HEIGHT /2 - btnArsenal.getHeight()*2.5f );

        btnArsenal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new ArmoryScreen(game));
            }
        });
        menuStage.addActor(btnArsenal);
        */


        //Botón Ajustes
        Texture settingsTexture=assetManager.get("btn/ajustesbtn.png");
        Texture settingsPressTexture= assetManager.get("btn/ajustesbtnpress.png");
        TextureRegionDrawable trdAjustes = new TextureRegionDrawable(new TextureRegion(settingsTexture));
        TextureRegionDrawable trdAjustesPress = new TextureRegionDrawable(new TextureRegion(settingsPressTexture));
        ImageButton btnAjustes = new ImageButton(trdAjustes,trdAjustesPress);
        btnAjustes.setPosition( WIDTH - WIDTH /6 - btnAjustes.getWidth(), HEIGHT /2 - btnAjustes.getHeight()*2.5f);
        btnAjustes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,SETTINGS));
            }
        });
        menuStage.addActor(btnAjustes);

        //Botón Pregunta
        Texture aboutTexture=assetManager.get("btn/aboutbtn.png");
        Texture aboutPressTexture= assetManager.get("btn/aboutbtnpress.png");
        TextureRegionDrawable trdAbout = new TextureRegionDrawable(new TextureRegion(aboutTexture));
        TextureRegionDrawable trdAboutPress = new TextureRegionDrawable(new TextureRegion(aboutPressTexture));
        ImageButton btnAbout = new ImageButton(trdAbout,trdAboutPress);
        btnAbout.setPosition( WIDTH -10-btnAbout.getWidth(), HEIGHT /2 - btnAbout.getHeight()*2.5f);
        btnAbout.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new AboutScreen(game));
            }
        });
        menuStage.addActor(btnAbout);

        Gdx.input.setInputProcessor(menuStage);

        Texture bigDragonTexture = assetManager.get("characters/dragon_volando_tira.png");
        bigDragon = new Enemy(bigDragonTexture);

        //Creamos la música
        this.game.changeMusic(MENU);


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
        assetManager.unload("characters/dragon_volando_tira.png");
        assetManager.unload("btn/aboutbtn.png");
        assetManager.unload("btn/aboutbtnpress.png");
        assetManager.unload("btn/playbtn.png");
        assetManager.unload("btn/playbtnpress.png");
        assetManager.unload("btn/ajustesbtn.png");
        assetManager.unload("btn/ajustesbtnpress.png");
        assetManager.unload("btn/arsenalbtn.png");
        assetManager.unload("btn/arsenalbtnpress.png");
        assetManager.unload("btn/levelsbtn.png");
        assetManager.unload("btn/levelsbtnpress.png");
    }
}
