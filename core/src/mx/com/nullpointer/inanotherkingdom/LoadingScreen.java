package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import mx.com.nullpointer.levels.LevelOne;
import mx.com.nullpointer.levels.LevelZero;
import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by MarinaHaro on 13/03/18.
 */

public class LoadingScreen extends GenericScreen {

    private final Main game;
    private Texture loadingTexture;
    private Sprite loadingSprite;
    private final AssetManager assetManager;
    private int screen;

    public LoadingScreen(Main game, int screen){
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.screen = screen;
    }

    @Override
    public void show() {
        loadingTexture = new Texture("cargando.png");
        loadingSprite = new Sprite(loadingTexture);
        loadingSprite.setPosition(WIDTH /2- loadingSprite.getWidth()/2, HEIGHT /2- loadingSprite.getHeight()/2);
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
                assetManager.finishLoading();
                break;
            case LVLONE:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                assetManager.load("map/nivelUno.tmx", TiledMap.class);
                assetManager.load("music/nivelUno.mp3",Music.class);
                break;
            default:
                Gdx.app.log("ERROR","Screen not implemented");

        }

    }


    @Override
    public void render(float delta) {

        updateLoad();
        clearScreen();
        loadingSprite.setRotation(loadingSprite.getRotation()+10*delta);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        loadingSprite.draw(batch);
        batch.end();
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
    }


}
