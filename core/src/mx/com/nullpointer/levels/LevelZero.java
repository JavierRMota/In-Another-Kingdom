package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.LoadingScreen;
import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.utils.GameState;
import mx.com.nullpointer.utils.GenericScreen;

import mx.com.nullpointer.utils.GestureController;
import mx.com.nullpointer.utils.MainCharacter;
import mx.com.nullpointer.utils.Text;

/**
 * Created by mota on 2/12/18.
 */

public class LevelZero extends GenericScreen {
    //Game object
    private final Main game;
    //Maps
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer render;
    private static final float MAP_WIDTH = 80*70;
    //Character
    private MainCharacter laurence;
    //Scores
    private int coins,keys;
    private String coinScore;
    private Text  scoreDisplay;
    private Texture coinTexture;
    private Texture emptyKeyTexture, fullKeyTexture;
    private boolean recolectedKeys[];
    private int middleKey;

    //Camera and scene
    private OrthographicCamera cameraHUD;
    private Viewport viewHUD;
    private Stage buttonScene;
    private Stage pauseScene;
    private InputProcessor inputProcessor;
    //Controlador de juego
    private GameState gameState;

    //Constructor
    public LevelZero(Main game)
    {
        this.game =game;
    }
    @Override
    public void show() {
        //Create the camera for all game information and buttons
        createHUD();
        //Load TiledMap
        loadMap();

        //Load Character
        laurence = new MainCharacter(
                new Texture("characters/laurence_descanso.png"), //Standing Position
                new Texture("characters/laurence_running.png"), //Running Position
                new Texture("characters/tira_salto.png"), //Jumping Position
                new Texture("characters/tira_marometa.png")); //Dodging Position

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        coinScore="00";
        coins=0;
        keys=0;
        recolectedKeys = new boolean[3];
        middleKey=34;
        scoreDisplay = new Text();
        coinTexture=new Texture("gameObjects/moneda.png");
        fullKeyTexture = new Texture("gameObjects/llaveFull.png");
        emptyKeyTexture = new Texture("gameObjects/llaveEmpty.png");
        //Input Processors
        loadInputProcessor();
        //Begin game
        gameState= GameState.PLAY;

    }
    private void loadInputProcessor()
    {
        //Multiple inputs
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        //Gesture detection
        GestureController gestureDetector = new GestureController(new GestureController.DirectionListener() {
            @Override
            public void onUp() {
                if(laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                        || laurence.getMovementState() == MainCharacter.MovementState.STANDING
                        || laurence.getMovementState() == MainCharacter.MovementState.JUMPING_END)
                {
                    laurence.resetTimerAction();
                    laurence.setMovementState(MainCharacter.MovementState.JUMPING_PREPARE);
                }

            }
            @Override
            public void onRight() {
                // TODO Auto-generated method stub

            }
            @Override
            public void onLeft() {
                // TODO Auto-generated method stub

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
        //Create scene
        buttonScene = new Stage(viewHUD);
        //Botón Pause
        TextureRegionDrawable trdPause = new TextureRegionDrawable(new TextureRegion(new Texture("btn/pausebtn.png")));
        TextureRegionDrawable trdPausePress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/pausebtnpress.png")));
        ImageButton btnPause = new ImageButton(trdPause,trdPausePress);
        btnPause.setPosition(WIDTH /2 - btnPause.getWidth()/2, HEIGHT - 1.5f*btnPause.getHeight());
        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                pause();
            }
        });
        //Add to the scene
        buttonScene.addActor(btnPause);
        //Set button processor
        inputMultiplexer.addProcessor(buttonScene);
        //Pause Scene
        pauseScene = new Stage(viewHUD);
        //Button play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtn.png")));
        TextureRegionDrawable trdPlayPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/playbtnpress.png")));
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
        //Button back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
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
        /*AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("map/nivelCero.tmx", TiledMap.class);
        manager.finishLoading();*/
        AssetManager manager = game.getAssetManager();
        tiledMap = manager.get("map/nivelCero.tmx");
        render = new OrthogonalTiledMapRenderer(tiledMap);

    }

    private void createHUD() {
        cameraHUD = new OrthographicCamera(WIDTH, HEIGHT);
        cameraHUD.position.set(WIDTH /2, HEIGHT /2,0);
        cameraHUD.update();
        viewHUD = new StretchViewport(WIDTH, HEIGHT,cameraHUD);

    }

    @Override
    public void render(float delta) {
        if(gameState!=GameState.PAUSE)
        {
            update(delta);
        }
        //Borrar pantalla
        clearScreen();
        //Projection matrix
        batch.setProjectionMatrix(camera.combined);
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
        if(recolectedKeys[0])
            batch.draw(fullKeyTexture,4* WIDTH/5+0.8f*fullKeyTexture.getWidth(), HEIGHT -5*fullKeyTexture.getHeight());
        else
            batch.draw(emptyKeyTexture,4* WIDTH/5+0.8f*fullKeyTexture.getWidth(), HEIGHT -5*fullKeyTexture.getHeight());
        if(recolectedKeys[1])
            batch.draw(fullKeyTexture,4* WIDTH/5+2f*fullKeyTexture.getWidth(), HEIGHT -5*fullKeyTexture.getHeight());
        else
            batch.draw(emptyKeyTexture,4* WIDTH/5+2f*fullKeyTexture.getWidth(), HEIGHT -5*fullKeyTexture.getHeight());
        if(recolectedKeys[2])
            batch.draw(fullKeyTexture,4* WIDTH/5+3.2f*fullKeyTexture.getWidth(), HEIGHT -5*fullKeyTexture.getHeight());
        else
            batch.draw(emptyKeyTexture,4* WIDTH/5+3.2f*fullKeyTexture.getWidth(), HEIGHT -5*fullKeyTexture.getHeight());
        scoreDisplay.showMsg(batch, coinScore,9* WIDTH /10, HEIGHT,2);
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
        updateScore();

    }

    private void updateState(float delta) {
        int cx = (int)(laurence.getX()+70)/70;
        int cy = (int)(laurence.getY())/70;
        winOrLoose();
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        checkCoins(cx,cy,layer);
        laurence.move(layer,delta, cx, cy);

    }

    private void winOrLoose() {
        if(laurence.getX()< camera.position.x-3* WIDTH /4 || laurence.getY()<0)
        {
            gameState=GameState.LOOSE;
        }
        if(laurence.getX()>MAP_WIDTH)
            gameState=GameState.WIN;
        if(gameState== GameState.WIN || gameState== GameState.LOOSE)
        {
            Gdx.app.log("Estado: ", gameState+"");
            game.setScreen(new LoadingScreen(game,MENU));
        }
    }

    private void checkCoins(int cx, int cy,TiledMapTileLayer layer)
    {
        TiledMapTileLayer.Cell currentCellDown = layer.getCell(cx,cy);
        TiledMapTileLayer.Cell currentCellUp = layer.getCell(cx,cy+1);

        if(currentCellDown != null)
        {
            int idCell = currentCellDown.getTile().getId();
            if(idCell ==11)
            {
                coins++;
                layer.setCell(cx,cy,null);
            }
            if(idCell ==13)
            {
                if(cx<middleKey)
                    recolectedKeys[0]=true;
                else if(cx>middleKey)
                    recolectedKeys[2]=true;
                else
                    recolectedKeys[1] = true;
                keys++;
                layer.setCell(cx,cy,null);

            }

        }
        if(currentCellUp!=null)
        {
            int idCell = currentCellUp.getTile().getId();
            if(idCell ==11)
            {
                coins++;
                layer.setCell(cx,cy+1,null);
            }
            if(idCell ==13)
            {
                if(cx<middleKey)
                    recolectedKeys[0]=true;
                else if(cx>middleKey)
                    recolectedKeys[2]=true;
                else
                    recolectedKeys[1] = true;
                keys++;
                layer.setCell(cx,cy+1,null);
            }
        }

    }

    private void updateScore() {
        coinScore = String.format("%02d", coins);
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

    @Override
    public void resize(int width, int height) {
    viewHUD.update(width,height);
    view.update(width,height);
    }
    @Override
    public void dispose()
    {
        game.getAssetManager().unload("map/nivelCero.tmx");
        buttonScene.dispose();
        tiledMap.dispose();
    }
    @Override
    public void pause() {
        gameState=GameState.PAUSE;
        Gdx.input.setInputProcessor(pauseScene);
        //laurence.setMovementState(MainCharacter.MovementState.STANDING);

    }
    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputProcessor);
        gameState = GameState.PLAY;
        //laurence.setMovementState(MainCharacter.MovementState.RUNNING);

    }


}
