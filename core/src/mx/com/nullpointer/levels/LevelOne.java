package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mx.com.nullpointer.inanotherkingdom.LoadingScreen;
import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.utils.GameState;
import mx.com.nullpointer.utils.GenericLevel;
import mx.com.nullpointer.utils.GestureController;
import mx.com.nullpointer.utils.MainCharacter;
import mx.com.nullpointer.utils.Text;

/**
 * Created by mota on 2/12/18.
 */

public class LevelOne extends GenericLevel {

    private static final float MAP_WIDTH = 200*70;


    //Constructor
    public LevelOne(Main game)
    {
        super(game);
    }
    @Override
    public void show() {
        //Create the camera for all game information and buttons
        createHUD();
        //Load TiledMap
        loadMap();
        //Load Textures
        loadTextures();

        //Load Character
        loadCharacter();

        //Load background
        loadBackground();

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(61);

        //Input Processors
        loadInputProcessor();

        //Begin game
        gameState= GameState.PLAY;

    }
    protected void loadBackground()
    {
        Texture backgroundTexture = assetManager.get("map/bookOneBg.png");
        backgroundOne = new Sprite(backgroundTexture);
        backgroundTwo = new Sprite(backgroundTexture);
        backgroundOne.setPosition(0,0);
        backgroundTwo.setPosition(backgroundOne.getWidth(),0);
        Texture cloudsTexture = assetManager.get("map/clouds.png");
        cloudsOne = new Sprite(cloudsTexture);
        cloudsTwo = new Sprite(cloudsTexture);
        cloudsOne.setPosition(0,0);
        cloudsTwo.setPosition(cloudsOne.getWidth(),0);
        Texture objectTexture = assetManager.get("map/bookOneT.png");
        objectsOne = new Sprite(objectTexture);
        objectsTwo = new Sprite(objectTexture);
        objectsOne.setPosition(0,-130);
        objectsTwo.setPosition(objectsOne.getWidth(),-130);

    }


    private void loadInputProcessor()
    {
        //Multiple inputs
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        //Gesture detection
        GestureController gestureDetector = new GestureController(new GestureController.DirectionListener() {
            @Override
            public void onLeft() {}
            @Override
            public void onRight() {}
            @Override
            public void onUp() {
                if(laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                        || laurence.getMovementState() == MainCharacter.MovementState.STANDING
                        || laurence.getMovementState() == MainCharacter.MovementState.JUMPING_END)
                {
                    laurence.resetTimerAction();
                    laurence.setMovementState(MainCharacter.MovementState.JUMPING);
                }

            }
            @Override
            public void onDown() {
                if(laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                        || laurence.getMovementState() == MainCharacter.MovementState.STANDING)
                    laurence.setMovementState(MainCharacter.MovementState.DODGING);

            }
        });
        //Set gesture input
        inputMultiplexer.addProcessor(gestureDetector);
        //Create scene for all buttons
        buttonScene = new Stage(viewHUD);
        //Pause Button
        Texture pauseTexture = assetManager.get("btn/pausebtn.png");
        Texture pausePressTexture = assetManager.get("btn/pausebtnpress.png");
        TextureRegionDrawable trdPause = new TextureRegionDrawable(new TextureRegion(pauseTexture));
        TextureRegionDrawable trdPausePress = new TextureRegionDrawable(new TextureRegion(pausePressTexture));
        ImageButton btnPause = new ImageButton(trdPause,trdPausePress);
        btnPause.setPosition(WIDTH /2 - btnPause.getWidth()/2, HEIGHT - 1.5f*btnPause.getHeight());
        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                pause();
            }
        });
        buttonScene.addActor(btnPause);
        //Action Button
        Texture actionTexture = assetManager.get("gameObjects/actionbtn.png");
        Texture actionPressTexture = assetManager.get("gameObjects/actionbtnpress.png");
        TextureRegionDrawable trdAction = new TextureRegionDrawable(new TextureRegion(actionTexture));
        TextureRegionDrawable trdActionPress = new TextureRegionDrawable(new TextureRegion(actionPressTexture));
        ImageButton btnAction = new ImageButton(trdAction,trdActionPress);
        btnAction.setPosition(3*WIDTH/4, 0);
        btnAction.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if(laurence.getMovementState() == MainCharacter.MovementState.RUNNING)
                {
                    laurence.resetTimerAction();
                    laurence.setMovementState(MainCharacter.MovementState.ATTACKING);
                }


            }
        });
        buttonScene.addActor(btnAction);
        inputMultiplexer.addProcessor(buttonScene);


        //Pause Scene
        pauseScene = new Stage(viewHUD);
        //Button play
        Texture playTexture = assetManager.get("btn/playbtn.png");
        Texture playPressTexture = assetManager.get("btn/playbtnpress.png");
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(playPressTexture));
        ImageButton btnPlay = new ImageButton(trdPlay,trdPlayPress);
        btnPlay.setPosition(WIDTH /2 - btnPlay.getWidth()/2, HEIGHT /2 - btnPlay.getHeight()/2);
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resume();
            }
        });
        pauseScene.addActor(btnPlay);
        //Back Button
        Texture backTexture = assetManager.get("btn/backbtn.png");
        Texture backPressTexture = assetManager.get("btn/backbtnpress.png");
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backTexture));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(backPressTexture));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(WIDTH /4 - btnBack.getWidth()/2, HEIGHT /2 - btnBack.getHeight()/2);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,MENU));
            }
        });
        pauseScene.addActor(btnBack);

        //Begin input processor
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessor = inputMultiplexer;
    }

    private void loadMap() {
        tiledMap = assetManager.get("map/nivelUno.tmx");
        render = new OrthogonalTiledMapRenderer(tiledMap);
    }


    @Override
    public void render(float delta) {
        //Check if paused
        if(gameState!=GameState.PAUSE)
        {
            update(delta);
        }
        //Borrar pantalla
        clearScreen();
        //Projection matrix
        batch.setProjectionMatrix(camera.combined);
        //Background
        batch.begin();
        drawBackground();
        batch.end();
        //View for the map
        render.setView(camera);
        render.render();
        //Draw objects
        batch.begin();
        //Laurence
        laurence.render(batch);
        batch.end();
        //Draw buttons and information
        batch.setProjectionMatrix(cameraHUD.combined);
        batch.begin();
        batch.draw(coinTexture,4* WIDTH /5+0.8f*coinTexture.getWidth(), HEIGHT -1.2f*coinTexture.getHeight());
        //Draw keys
        drawKeys();
        //Display score
        scoreDisplay.showMsg(batch, coinScore,9* WIDTH /10, HEIGHT,2);
        //End batch
        batch.end();
        if(gameState == GameState.PLAY)
        {
            buttonScene.draw();
        }
        else
        {
            pauseScene.draw();
        }
    }


    private void update(float delta) {
        updateState(delta);
        updateCamera(delta);
        updateBackground(delta);

    }



    private void updateState(float delta)
    {
        int cx = (int)(laurence.getX()+70)/70;
        int cy = (int)(laurence.getY())/70;

        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        checkCoins(cx,cy,layer);
        checkEnemies(cx,cy,layer);
        laurence.move(layer,delta, cx, cy);
        winOrLoose();
    }

    private void checkEnemies(int cx, int cy, TiledMapTileLayer layer) {

    }

    protected void winOrLoose() {
        if(laurence.getX()< camera.position.x-3* WIDTH /4 || laurence.getY()<0)
        {
            gameState=GameState.LOOSE;
        }
        else if(laurence.getX()>MAP_WIDTH)
            gameState=GameState.WIN;
        if(gameState== GameState.WIN || gameState== GameState.LOOSE)
        {
            Gdx.app.log("Estado: ", gameState+"");
            pause();
        }
    }


    private void updateCamera(float delta) {
        //Para que la cámara avance sola hasta el final de la pantalla
        float posX = camera.position.x+delta*laurence.getVelocity()*0.9f;
        if (posX > MAP_WIDTH - WIDTH /2) {   // Última mitad de la pantalla
            camera.position.set(MAP_WIDTH- WIDTH /2,camera.position.y,0);
        } else {    // En 'medio' del mapa
            camera.position.set(posX,camera.position.y,0);
        }
        camera.update();
    }

    //Get rid of all loaded assets
    @Override
    public void dispose()
    {
        assetManager.unload("map/nivelUno.tmx");
        assetManager.unload("music/nivelUno.mp3");
        assetManager.unload("map/bookOneT.png");
        assetManager.unload("characters/laurence_descanso.png");
        assetManager.unload("characters/laurence_running.png");
        assetManager.unload("characters/tira_salto.png");
        assetManager.unload("characters/tira_marometa.png");
        assetManager.unload("gameObjects/llaveFull.png");
        assetManager.unload("gameObjects/llaveEmpty.png");
        assetManager.unload("gameObjects/moneda.png");
        assetManager.unload("btn/playbtn.png");
        assetManager.unload("btn/playbtnpress.png");
        assetManager.unload("btn/backbtn.png");
        assetManager.unload("btn/backbtnpress.png");
        assetManager.unload("btn/pausebtn.png");
        assetManager.unload("btn/pausebtnpress.png");
        assetManager.unload("map/bookOneBg.png");
        assetManager.unload("map/clouds.png");
        assetManager.unload("gameObjects/actionbtn.png");
        assetManager.unload("gameObjects/actionbtnpress.png");
        assetManager.unload("characters/laurence_attacking.png");
        buttonScene.dispose();
        tiledMap.dispose();
    }



}
