package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import mx.com.nullpointer.levels.LevelOne;
import mx.com.nullpointer.levels.LevelZero;
import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by MarinaHaro on 13/03/18.
 */

public class LoadingScreen extends GenericScreen {


    //private Sprite loadingSprite;
    private Texture backgroundTexture;
    private Texture loadingTexture;
    private Animation animationLoading;
    private float timerAnimation;
    private float x;
    private float y;
    private int screen;

    public LoadingScreen(Main game, int screen){
        super(game);
        this.screen = screen;
    }

    @Override
    public void show() {
        backgroundTexture = new Texture("background/menubg.png");
        loadingTexture = new Texture("background/pantallaCarga.png");
        /*loadingSprite = new Sprite(loadingTexture);
        loadingSprite.setPosition(WIDTH /2- loadingSprite.getWidth()/2, HEIGHT /2- loadingSprite.getHeight()/2);*/
        TextureRegion region = new TextureRegion(loadingTexture);
        TextureRegion[][] frames = region.split(loadingTexture.getWidth()/6, loadingTexture.getHeight());
        animationLoading = new Animation(0.2f, frames[0][5], frames[0][2], frames[0][1]);
        animationLoading.setPlayMode(Animation.PlayMode.LOOP);
        x = HEIGHT/3*2;
        y = WIDTH/5;
        loadResources();

    }

    //Rescursos de la siguiente pantalla
    private void loadResources() {
        switch (screen)
        {
            case MENU:
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
                assetManager.load("characters/dragon_volando_tira.png",Texture.class);
                assetManager.load("btn/playbtn.png",Texture.class);
                assetManager.load("btn/playbtnpress.png",Texture.class);
                assetManager.load("btn/arsenalbtn.png",Texture.class);
                assetManager.load("btn/arsenalbtnpress.png",Texture.class);
                assetManager.load("btn/levelsbtn.png",Texture.class);
                assetManager.load("btn/levelsbtnpress.png",Texture.class);
                assetManager.load("btn/ajustesbtn.png",Texture.class);
                assetManager.load("btn/ajustesbtnpress.png",Texture.class);
                assetManager.load("btn/aboutbtn.png",Texture.class);
                assetManager.load("btn/aboutbtnpress.png",Texture.class);
                assetManager.load("music/menu.mp3",Music.class);
                assetManager.finishLoading();
                break;
            case SETTINGS:
                assetManager.load("music/menu.mp3",Music.class);
                break;
            case LEVELS:
                assetManager.load("music/menu.mp3",Music.class);
                break;
            case ABOUT:
                assetManager.load("music/menu.mp3",Music.class);
                break;
            case ARMORY:
                assetManager.load("music/menu.mp3",Music.class);
                break;
            case LVLZERO:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                assetManager.load("map/nivelCero.tmx", TiledMap.class);
                assetManager.load("music/nivelUno.mp3",Music.class);
                assetManager.load("map/bookOneBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookOneT.png",Texture.class);
                assetManager.load("characters/laurence_descanso.png",Texture.class);
                assetManager.load("characters/laurence_running.png",Texture.class);
                assetManager.load("characters/tira_salto.png",Texture.class);
                assetManager.load("characters/tira_marometa.png",Texture.class);
                assetManager.load("characters/laurence_attacking.png", Texture.class);
                assetManager.load("gameObjects/llaveFull.png",Texture.class);
                assetManager.load("gameObjects/llaveEmpty.png",Texture.class);
                assetManager.load("btn/playbtn.png",Texture.class);
                assetManager.load("btn/playbtnpress.png",Texture.class);
                assetManager.load("btn/backbtn.png",Texture.class);
                assetManager.load("btn/backbtnpress.png",Texture.class);
                assetManager.load("btn/pausebtn.png",Texture.class);
                assetManager.load("btn/pausebtnpress.png",Texture.class);
                assetManager.load("gameObjects/moneda.png",Texture.class);
                assetManager.load("tutorial/swipeUp.png",Texture.class);
                assetManager.load("gameObjects/actionbtn.png",Texture.class);
                assetManager.finishLoading();
                break;
            case LVLONE:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                assetManager.load("map/nivelUno.tmx", TiledMap.class);
                assetManager.load("music/nivelUno.mp3",Music.class);
                assetManager.load("map/bookOneBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookOneT.png",Texture.class);
                assetManager.load("characters/laurence_descanso.png",Texture.class);
                assetManager.load("characters/laurence_running.png",Texture.class);
                assetManager.load("characters/tira_salto.png",Texture.class);
                assetManager.load("characters/tira_marometa.png",Texture.class);
                assetManager.load("characters/laurence_attacking.png", Texture.class);
                assetManager.load("gameObjects/llaveFull.png",Texture.class);
                assetManager.load("gameObjects/llaveEmpty.png",Texture.class);
                assetManager.load("btn/playbtn.png",Texture.class);
                assetManager.load("btn/playbtnpress.png",Texture.class);
                assetManager.load("btn/backbtn.png",Texture.class);
                assetManager.load("btn/backbtnpress.png",Texture.class);
                assetManager.load("btn/pausebtn.png",Texture.class);
                assetManager.load("btn/pausebtnpress.png",Texture.class);
                assetManager.load("gameObjects/moneda.png",Texture.class);
                assetManager.load("gameObjects/actionbtn.png",Texture.class);
                assetManager.load("gameObjects/actionbtnpress.png",Texture.class);
                break;
            default:
                Gdx.app.log("ERROR","Screen not implemented");

        }

    }


    @Override
    public void render(float delta) {

        updateLoad();
        //loadingSprite.setRotation(loadingSprite.getRotation()+10*delta);
        //batch.setProjectionMatrix(camera.combined);
        timerAnimation += Gdx.graphics.getDeltaTime();
        TextureRegion frame = (TextureRegion) animationLoading.getKeyFrame(timerAnimation);
        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        batch.draw(frame, x, y);
        batch.end();
        //loadingSprite.draw(batch);
    }


    //Revisa como va la carga de los assets
    private void updateLoad() {


        if(assetManager.update()){
            switch (screen)
            {
                case MENU:
                    game.setScreen(new MenuScreen(game));
                    break;
                case SETTINGS:
                    game.setScreen(new SettingsScreen(game));
                    break;
                case LEVELS:
                    game.setScreen(new LevelsScreen(game));
                    break;
                case ABOUT:
                    game.setScreen(new AboutScreen(game));
                    break;
                case ARMORY:
                    game.setScreen(new ArmoryScreen(game));
                    break;
                case LVLZERO:
                    game.setScreen(new LevelZero(game));
                    break;
                case LVLONE:
                    game.setScreen(new LevelOne(game));
                    break;
                default:
                    Gdx.app.log("ERROR:","Screen not implemented");
            }
        } else{
            float avance = assetManager.getProgress();
            Gdx.app.log("Avance:",avance+"");
        }
    }

    @Override
    public void dispose() {
        loadingTexture.dispose();
        backgroundTexture.dispose();
    }


}
