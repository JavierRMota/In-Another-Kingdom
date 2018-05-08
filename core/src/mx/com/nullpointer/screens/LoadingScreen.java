package mx.com.nullpointer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.levels.*;

/**
 * Created by MarinaHaro on 13/03/18.
 */

public class LoadingScreen extends GenericScreen {


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
        TextureRegion region = new TextureRegion(loadingTexture);
        TextureRegion[][] frames = region.split(loadingTexture.getWidth()/6, loadingTexture.getHeight());
        animationLoading = new Animation(0.1f, frames[0][0], frames[0][1], frames[0][2], frames[0][3], frames[0][4], frames[0][5]);
        animationLoading.setPlayMode(Animation.PlayMode.LOOP);
        x = 454;
        y = 245;
        loadResources();

    }

    //Rescursos de la siguiente pantalla
    private void loadResources() {
        switch (screen)
        {
            case MENU:
                //Background
                assetManager.load("background/menu_bg_1.png", Texture.class);
                assetManager.load("background/menu_bg_2.png", Texture.class);
                assetManager.load("background/menu_bg_3.png", Texture.class);
                assetManager.load("background/menu_bg_cover.png", Texture.class);
                //Logo
                assetManager.load("logo.png", Texture.class);
                //Bosses
                assetManager.load("characters/dragon_volando_tira.png",Texture.class);
                assetManager.load("characters/finalboss_two.png",Texture.class);
                assetManager.load("characters/finalboss_three.png",Texture.class);
                //Buttons
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
                //Music
                assetManager.load("music/menu.mp3",Music.class);
                break;
            case SETTINGS:
                //Music
                assetManager.load("music/menu.mp3",Music.class);
                //Buttons
                assetManager.load("btn/possitionLeft.png",Texture.class);
                assetManager.load("btn/possitionRight.png",Texture.class);
                assetManager.load("btn/slider.png",Texture.class);
                assetManager.load("btn/button.png",Texture.class);
                assetManager.load("btn/backbtn.png",Texture.class);
                assetManager.load("btn/backbtnpress.png",Texture.class);
                assetManager.load("btn/musicOn.png",Texture.class);
                assetManager.load("btn/musicOff.png",Texture.class);
                assetManager.load("btn/restart.png", Texture.class);
                assetManager.load("btn/restartPress.png", Texture.class);
                //Background
                assetManager.load("background/menubg.png",Texture.class);
                assetManager.load("skin/golden/golden-ui-skin.json",Skin.class);
                assetManager.load("skin/cloud/cloud-form-ui.json",Skin.class);

                break;
            case LEVELS:
                assetManager.load("music/menu.mp3",Music.class);
                assetManager.load("btn/backbtn.png",Texture.class);
                assetManager.load("btn/backbtnpress.png",Texture.class);
                assetManager.load("logo_alargado.png",Texture.class);
                assetManager.load("gameObjects/star.png",Texture.class);
                assetManager.load("niveles/uno/levelOneBook.png",Texture.class);
                assetManager.load("background/menubg.png",Texture.class);
                assetManager.load("niveles/uno/levelOne.png",Texture.class);
                assetManager.load("niveles/uno/levelTwo.png",Texture.class);
                assetManager.load("niveles/uno/levelZero.png",Texture.class);
                assetManager.load("niveles/uno/levelOneBook.png",Texture.class);
                assetManager.load("niveles/tres/levelThreeBook.png",Texture.class);
                assetManager.load("niveles/dos/levelThree.png",Texture.class);
                assetManager.load("niveles/dos/levelFour.png",Texture.class);
                assetManager.load("niveles/dos/levelFive.png",Texture.class);
                assetManager.load("niveles/dos/levelTwoBook.png",Texture.class);
                assetManager.load("niveles/tres/levelThreeBook.png",Texture.class);
                assetManager.load("niveles/tres/levelSix.png",Texture.class);
                assetManager.load("niveles/tres/levelSeven.png",Texture.class);
                assetManager.load("niveles/tres/levelEight.png",Texture.class);
                assetManager.load("niveles/levelLock.png",Texture.class);
                assetManager.load("background/openBook.png",Texture.class);
                assetManager.load("btn/backdarkbtn.png",Texture.class);
                assetManager.load("btn/backdarkbtnpress.png",Texture.class);
                assetManager.load("niveles/lock.png",Texture.class);
                assetManager.load("niveles/lockedBook.png",Texture.class);
                assetManager.load("niveles/playbtnlevels.png",Texture.class);
                assetManager.load("niveles/playbtnpresslevels.png",Texture.class);
                assetManager.load("btn/howto.png",Texture.class);
                assetManager.load("background/menubg.png",Texture.class);
                assetManager.load("background/tiraLibroAbriendose.png",Texture.class);
                assetManager.load("background/tiraLibroAbriendose2.png",Texture.class);
                assetManager.load("background/tiraLibroAbriendose3.png",Texture.class);
                break;
            case ABOUT:
                assetManager.load("background/menubg.png",Texture.class);
                assetManager.load("music/menu.mp3",Music.class);
                assetManager.load("btn/backbtn.png",Texture.class);
                assetManager.load("btn/backbtnpress.png",Texture.class);
                assetManager.load("btn/gitbtn.png",Texture.class);
                assetManager.load("btn/gitbtnpress.png",Texture.class);
                assetManager.load("screens/avatarBanner.png",Texture.class);
                assetManager.load("screens/avatarBere.png",Texture.class);
                assetManager.load("screens/avatarCharly.png",Texture.class);
                assetManager.load("screens/avatarEli.png",Texture.class);
                assetManager.load("screens/avatarJavier.png",Texture.class);
                assetManager.load("screens/avatarMarina.png",Texture.class);
                break;
            case TUTORIAL:
                assetManager.load("background/howTo.png",Texture.class);
                break;
            case LVLZERO:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelCero.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookOneBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookOneT.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_burned.png",Texture.class);
                assetManager.load("characters/laurence_celebrating.png", Texture.class);
                assetManager.load("characters/laurence_drowning.png", Texture.class);
                //Tutorial
                assetManager.load("tutorial/swipeUp.png",Texture.class);
                assetManager.load("tutorial/swipeDown.png",Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLONE:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelUno.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookOneBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookOneT.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_burned.png",Texture.class);
                assetManager.load("characters/laurence_celebrating.png", Texture.class);
                assetManager.load("characters/laurence_drowning.png", Texture.class);
                //Tutorial
                assetManager.load("tutorial/pushButton.png", Texture.class);
                assetManager.load("tutorial/swipeRight.png", Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLTWO:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelDos.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookOneBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookOneT.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_burned.png",Texture.class);
                assetManager.load("characters/laurence_celebrating.png", Texture.class);
                assetManager.load("characters/laurence_drowning.png", Texture.class);
                //Big dragon
                assetManager.load("characters/finalboss.png", Texture.class);
                assetManager.load("characters/fireballRED.png",Texture.class);
                assetManager.load("characters/fireball.png",Texture.class);
                //Final background
                assetManager.load("background/castillo.png", Texture.class);
                //Tutorial
                assetManager.load("tutorial/pushButton.png", Texture.class);
                assetManager.load("tutorial/swipeRight.png", Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLTHREE:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelTres.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookTwoBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookTwoP.png",Texture.class);
                assetManager.load("background/esfinge.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_tangled.png",Texture.class);
                assetManager.load("characters/laurence_celebrating_two.png", Texture.class);
                assetManager.load("characters/laurence_falling.png", Texture.class);
                //Enemy
                assetManager.load("characters/momia.png",Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLFOUR:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelCuatro.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookTwoBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookTwoP.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_tangled.png",Texture.class);
                assetManager.load("characters/laurence_celebrating_two.png", Texture.class);
                assetManager.load("characters/laurence_falling.png", Texture.class);
                //Enemy
                assetManager.load("characters/momia.png",Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLFIVE:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelCinco.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookTwoBg.png",Texture.class);
                assetManager.load("map/clouds.png",Texture.class);
                assetManager.load("map/bookTwoP.png",Texture.class);
                //Final background
                assetManager.load("background/esfinge.png", Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_tangled.png",Texture.class);
                assetManager.load("characters/laurence_celebrating_two.png", Texture.class);
                assetManager.load("characters/laurence_falling.png", Texture.class);
                //Enemy
                assetManager.load("characters/momia.png",Texture.class);
                assetManager.load("characters/finalboss_two.png",Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLSIX:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelSeis.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookThreeBg.png",Texture.class);
                assetManager.load("map/clouds_tres.png",Texture.class);
                assetManager.load("map/bookThreeF.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_poisoned.png",Texture.class);
                assetManager.load("characters/laurence_celebrating_three.png", Texture.class);
                assetManager.load("characters/laurence_falling_three.png", Texture.class);

                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLSEVEN:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelSiete.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookThreeBg.png",Texture.class);
                assetManager.load("map/clouds_tres.png",Texture.class);
                assetManager.load("map/bookThreeF.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/laurence_poisoned.png",Texture.class);
                assetManager.load("characters/laurence_celebrating_three.png", Texture.class);
                assetManager.load("characters/laurence_falling_three.png", Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            case LVLEIGHT:
                assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                //Map
                assetManager.load("map/nivelOcho.tmx", TiledMap.class);
                //Background
                assetManager.load("map/bookThreeBg.png",Texture.class);
                assetManager.load("map/clouds_tres.png",Texture.class);
                assetManager.load("map/bookThreeF.png",Texture.class);
                //Laurence win, loose
                assetManager.load("characters/dinosaur_loose.png",Texture.class);
                assetManager.load("characters/laurence_celebrating_three.png", Texture.class);
                assetManager.load("characters/laurence_falling_three.png", Texture.class);
                //Final boss
                assetManager.load("characters/finalboss_three.png",Texture.class);
                //Tutorial
                assetManager.load("tutorial/pushButton.png",Texture.class);
                //Normal things to load
                loadGenericLevelAssets();
                break;
            default:
                Gdx.app.log("ERROR","Screen not implemented");

        }

    }


    @Override
    public void render(float delta) {
        clearScreen();
        updateLoad();
        batch.setProjectionMatrix(camera.combined);
        timerAnimation += Gdx.graphics.getDeltaTime();
        TextureRegion frame = (TextureRegion) animationLoading.getKeyFrame(timerAnimation);
        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        batch.draw(frame, x, y);
        batch.end();

    }
    public void loadGenericLevelAssets()
    {
        //Laurence animation
        assetManager.load("characters/laurence_descanso.png",Texture.class);
        assetManager.load("characters/laurence_running.png",Texture.class);
        assetManager.load("characters/laurence_jumping.png",Texture.class);
        assetManager.load("characters/tira_marometa.png",Texture.class);
        assetManager.load("characters/laurence_attacking.png", Texture.class);
        assetManager.load("characters/laurence_air_attack.png", Texture.class);
        //Win loose background
        assetManager.load("background/winLooseBg.png",Texture.class);
        //Music
        assetManager.load("music/nivelUno.mp3",Music.class);
        assetManager.load("music/sword.mp3", Music.class);
        //Score
        assetManager.load("gameObjects/llave.png",Texture.class);
        assetManager.load("gameObjects/star.png",Texture.class);
        assetManager.load("gameObjects/llaveFull.png",Texture.class);
        assetManager.load("gameObjects/llaveEmpty.png",Texture.class);
        assetManager.load("gameObjects/moneda.png",Texture.class);
        //Buttons
        assetManager.load("btn/playbtn.png",Texture.class);
        assetManager.load("btn/playbtnpress.png",Texture.class);
        assetManager.load("btn/resetbtn.png",Texture.class);
        assetManager.load("btn/resetbtnpress.png",Texture.class);
        assetManager.load("btn/backbtn.png",Texture.class);
        assetManager.load("btn/backbtnpress.png",Texture.class);
        assetManager.load("btn/pausebtn.png",Texture.class);
        assetManager.load("btn/pausebtnpress.png",Texture.class);
        assetManager.load("btn/backdarkbtn.png",Texture.class);
        assetManager.load("btn/backdarkbtnpress.png",Texture.class);
        assetManager.load("btn/levelsdarkbtn.png",Texture.class);
        assetManager.load("btn/levelsdarkbtnpress.png",Texture.class);
        assetManager.load("btn/nextbtn.png",Texture.class);
        assetManager.load("btn/nextbtnpress.png",Texture.class);
        assetManager.load("btn/resetdarkbtn.png",Texture.class);
        assetManager.load("btn/resetdarkbtnpress.png",Texture.class);
        assetManager.load("gameObjects/actionbtn.png",Texture.class);
        assetManager.load("gameObjects/actionbtnpress.png",Texture.class);
        assetManager.load("btn/mutePause.png",Texture.class);
        assetManager.load("btn/soundPause.png",Texture.class);

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
                case TUTORIAL:
                    game.setScreen(new TutorialScreen(game));
                    break;
                case LVLZERO:
                    game.setScreen(new LevelZero(game,screen));
                    break;
                case LVLONE:
                    game.setScreen(new LevelOne(game,screen));
                    break;
                case LVLTWO:
                    game.setScreen(new LevelTwo(game,screen));
                    break;
                case LVLTHREE:
                    game.setScreen(new LevelThree(game,screen));
                    break;
                case LVLFOUR:
                    game.setScreen(new LevelFour(game,screen));
                    break;
                case LVLFIVE:
                    game.setScreen(new LevelFive(game,screen));
                    break;
                case LVLSIX:
                    game.setScreen(new LevelSix(game,screen));
                    break;
                case LVLSEVEN:
                    game.setScreen(new LevelSeven(game,screen));
                    break;
                case LVLEIGHT:
                    game.setScreen(new LevelEight(game,screen));
                    break;
                default:
                    Gdx.app.log("ERROR:","Screen not implemented");
            }
        }
    }

    @Override
    public void dispose() {
        loadingTexture.dispose();
        backgroundTexture.dispose();
    }


}
