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
        loadMap("map/nivelUno.tmx");
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
                if(laurence.getMovementState() == MainCharacter.MovementState.FALLING)
                    laurence.quickFall();

            }
        });
        //Set gesture input
        inputMultiplexer.addProcessor(gestureDetector);
        //Create stage for all buttons
        createButtonStage();
        inputMultiplexer.addProcessor(buttonScene);

        //Create Pause Stage
        createPauseStage();


        //Begin input processor
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessor = inputMultiplexer;
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
        //Draw current input scene
        drawInputScene();
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
        TiledMapTileLayer.Cell currentCell = layer.getCell(cx,cy);
        if(currentCell!=null)
        {
            String cellType = (String) currentCell.getTile().getProperties().get("type");
            if(cellType.equals("enemy"))
            {
                if(laurence.getMovementState() == MainCharacter.MovementState.ATTACKING)
                {
                    Integer number = Integer.parseInt((String) currentCell.getTile().getProperties().get("number"));
                    switch (number)
                    {
                        case 1:
                            layer.setCell(cx,cy,null);
                            layer.setCell(cx,cy-1,null);
                            layer.setCell(cx+1,cy,null);
                            layer.setCell(cx+1,cy-1,null);
                            break;
                        case 2:
                            layer.setCell(cx,cy,null);
                            layer.setCell(cx,cy-1,null);
                            layer.setCell(cx-1,cy,null);
                            layer.setCell(cx-1,cy-1,null);
                            break;
                        case 3:
                            layer.setCell(cx,cy,null);
                            layer.setCell(cx,cy+1,null);
                            layer.setCell(cx+1,cy,null);
                            layer.setCell(cx+1,cy+1,null);
                            break;
                        case 4:
                            layer.setCell(cx,cy,null);
                            layer.setCell(cx,cy+1,null);
                            layer.setCell(cx-1,cy,null);
                            layer.setCell(cx-1,cy+1,null);
                            break;
                    }
                }
                else
                {
                    gameState= GameState.LOOSE;
                }

            }
        }


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
        float posX;
        if(laurence.getX()>= camera.position.x-3*WIDTH/8)
            posX = camera.position.x+delta*laurence.getVelocity();
        else if(laurence.getX()>=camera.position.x-WIDTH/2)
            posX =camera.position.x+delta*laurence.getVelocity()*0.8f;
        else
            posX=camera.position.x+delta*laurence.getVelocity()*0.6f;
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
