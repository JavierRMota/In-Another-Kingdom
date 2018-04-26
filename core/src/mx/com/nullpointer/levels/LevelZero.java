package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.utils.GestureController;
import mx.com.nullpointer.inanotherkingdom.MainCharacter;

/**
 * Created by mota on 2/12/18.
 */

public class LevelZero extends GenericLevel {

    //Tutorial
    private Sprite swipeUp;
    private Sprite swipeDown;
    private Sprite swordSprite;
    private int VSWIPE= 150;
    private boolean sword =false, fastFall =false;
    private Preferences tutorialPref = Gdx.app.getPreferences("Tutorial");
    private boolean tutorial;

    //Constructor
    public LevelZero(Main game, int level)
    {
        super(game,level,80*70,60);
    }
    @Override
    public void show() {
        //Create the camera for all game information and buttons
        createHUD();
        //Load TiledMap
        loadMap("map/nivelCero.tmx");
        //Load Textures
        loadTextures();
        //loadCharacter
        loadCharacter();
        //Load background
        loadBackground();

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(34);

        //Tutorial
        tutorial = tutorialPref.getBoolean("tutorialLVL0", false);
        if(!tutorial) {
            Texture swipeUpTexture = assetManager.get("tutorial/swipeUp.png");
            swipeUp = new Sprite(swipeUpTexture);
            swipeUp.setPosition(13 * 70, 50);
            Texture swipeDownTexture = assetManager.get("tutorial/swipeDown.png");
            swipeDown = new Sprite(swipeDownTexture);
            swipeDown.setPosition(41 * 70, 50);
        }
        Texture swordTexture = assetManager.get("gameObjects/actionbtn.png");
        swordSprite = new Sprite(swordTexture);
        swordSprite.setPosition(3*WIDTH/4,HEIGHT/4);

        //Input Processors
        loadInputProcessor();

        //Begin game
        gameState= GameState.PLAY;

    }

    protected void loadBackground() {
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
        objectsOne.setPosition(0,0);
        objectsTwo.setPosition(objectsOne.getWidth(),0);
    }

    @Override
    protected void loadInputProcessor()
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
                if(laurence.getMovementState() == MainCharacter.MovementState.FALLING || laurence.getMovementState() == MainCharacter.MovementState.JUMPING)
                {
                    laurence.quickFall();
                }
                int cx = (int)(laurence.getX()+70)/70;
                if(!tutorial && cx==40)
                {
                    fastFall=true;
                    laurence.setTimerAction(1.5f);
                }

            }
        });
        //Set gesture input
        inputMultiplexer.addProcessor(gestureDetector);
        //Create stage for all buttons
        createButtonStage();
        inputMultiplexer.addProcessor(buttonScene);

        //Create Pause Stage
        createPauseStage();

        //Create Win Loose Stage
        createWinLooseStage();

        //Begin input processor
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessor = inputMultiplexer;
    }



    @Override
    public void render(float delta) {
        int cx = (int)(laurence.getX()+70)/70;

            if(gameState==GameState.PLAY && (tutorial || cx!=13 || laurence.getMovementState()!= MainCharacter.MovementState.RUNNING) && (tutorial || cx!=40 || fastFall))
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

            if(!tutorial && cx==13)
            {
                swipeUp.setY(swipeUp.getY()+delta*VSWIPE);
                if(swipeUp.getY()>200 ||swipeUp.getY()<50)
                    VSWIPE=-VSWIPE;
                swipeUp.draw(batch);
            } else if(!tutorial && cx==40)
            {
                swipeDown.setY(swipeDown.getY()+delta*VSWIPE);
                if(swipeDown.getY()>200 ||swipeDown.getY()<50)
                    VSWIPE=-VSWIPE;
                swipeDown.draw(batch);
            }

            batch.end();
            //Draw buttons and information
            batch.setProjectionMatrix(cameraHUD.combined);
            batch.begin();
            batch.draw(coinTexture,4* WIDTH /5+0.8f*coinTexture.getWidth(), HEIGHT -1.2f*coinTexture.getHeight());
            //Draw keys
            drawKeys();
            //Display score
            scoreDisplay.showMsg(batch, coinScore,9* WIDTH /10, HEIGHT,2,'c');

            //Check if we have the sword
            if(sword)
            {
                if(swordSprite.getY()>0)
                {
                    swordSprite.setPosition(swordSprite.getX(),swordSprite.getY()-300*delta);
                }
                swordSprite.draw(batch);
            }

            //End batch
            batch.end();
            //Check which scene to draw
            drawInputScene();
        Gdx.app.log("render", "fps="+Gdx.graphics.getFramesPerSecond());

    }

    private void update(float delta) {
        updateState(delta);
        updateCamera(delta);
        updateBackground(delta);


    }

    private void updateState(float delta) {
        int cx = (int)(laurence.getX()+70)/70;
        int cy = (int)(laurence.getY())/70;
        winOrLoose();
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        checkCoins(cx,cy,layer);
        laurence.move(layer,delta, cx, cy);

    }

    protected void winOrLoose() {
        if(laurence.getX()< camera.position.x-3* WIDTH /4 || laurence.getY()<0)
        {
            loose();
        }
        if(laurence.getX()>MAP_WIDTH)
        {
            if(!tutorial)
            {
                tutorialPref.putBoolean("tutorialLVL0",true);
                tutorialPref.flush();
            }
            win();
        }

    }

   @Override
    protected void checkCellForItem(int cx, int cy, TiledMapTileLayer layer, TiledMapTileLayer.Cell currentCell) {
        String cellType = (String) currentCell.getTile().getProperties().get("type");
        if(cellType.equals("coin"))
        {
            coins++;
            layer.setCell(cx,cy,null);
            updateScore();
        }
        if(cellType.equals("key"))
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
        if(cellType.equals("sword1"))
        {
            layer.setCell(cx,cy,null);
            layer.setCell(cx,cy+1,null);
            sword=true;
        }
        if(cellType.equals("sword2"))
        {
            layer.setCell(cx,cy-1,null);
            layer.setCell(cx,cy,null);
            sword=true;
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
        assetManager.unload("map/nivelCero.tmx");
        assetManager.unload("music/nivelUno.mp3");
        assetManager.unload("map/bookOneT.png");
        assetManager.unload("characters/laurence_descanso.png");
        assetManager.unload("characters/laurence_running.png");
        assetManager.unload("characters/tira_salto.png");
        assetManager.unload("characters/tira_marometa.png");
        assetManager.unload("characters/laurence_burned.png");
        assetManager.unload("characters/laurence_celebrating.png");
        assetManager.unload("characters/laurence_drowning.png");
        assetManager.unload("background/winLooseBg.png");
        assetManager.unload("gameObjects/llave.png");
        assetManager.unload("gameObjects/star.png");
        assetManager.unload("gameObjects/llaveFull.png");
        assetManager.unload("gameObjects/llaveEmpty.png");
        assetManager.unload("gameObjects/moneda.png");
        assetManager.unload("btn/playbtn.png");
        assetManager.unload("btn/playbtnpress.png");
        assetManager.unload("btn/backbtn.png");
        assetManager.unload("btn/backbtnpress.png");
        assetManager.unload("btn/pausebtn.png");
        assetManager.unload("btn/pausebtnpress.png");
        assetManager.unload("btn/resetbtn.png");
        assetManager.unload("btn/resetbtnpress.png");
        assetManager.unload("map/bookOneBg.png");
        assetManager.unload("map/clouds.png");
        assetManager.unload("tutorial/swipeUp.png");
        assetManager.unload("tutorial/swipeDown.png");
        assetManager.unload("gameObjects/actionbtn.png");
        assetManager.unload("characters/laurence_attacking.png");
        assetManager.unload("btn/backdarkbtn.png");
        assetManager.unload("btn/backdarkbtnpress.png");
        assetManager.unload("btn/levelsdarkbtn.png");
        assetManager.unload("btn/levelsdarkbtnpress.png");
        assetManager.unload("btn/nextbtn.png");
        assetManager.unload("btn/nextbtnpress.png");
        assetManager.unload("btn/resetdarkbtn.png");
        assetManager.unload("btn/resetdarkbtnpress.png");
        buttonScene.dispose();
        looseScene.dispose();
        winScene.dispose();
        tiledMap.dispose();
    }



}
