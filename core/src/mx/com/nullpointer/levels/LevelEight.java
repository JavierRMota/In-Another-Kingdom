package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import mx.com.nullpointer.inanotherkingdom.Dinosaur;
import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.MainCharacter;
import mx.com.nullpointer.utils.GestureController;

public class LevelEight extends GenericLevel {
    private Texture endTexture;
    private Dinosaur finalBoss;
    private boolean tutorial,speedUp;
    private int VELOCITY, SPEEDUPVELOCITY;
    private float speedTimer;
    private Sprite swipeDown;
    private int VSWIPE= 150;
    private Preferences tutorialPref = Gdx.app.getPreferences("Tutorial");
    //Constructor
    public LevelEight(Main game, int level)
    {
        super(game,level,200*70,198);
    }
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        //Create the camera for all game information and buttons
        createHUD();
        //Load TiledMap
        loadMap("map/nivelOcho.tmx");
        //Load Textures
        loadTextures();
        //Win and loose
        loadWinLooseTextures("characters/laurence_falling_three.png","characters/laurence_celebrating_three.png","characters/laurence_burned.png");


        //Load Character
        loadCharacter();

        //Load background
        loadBackground();

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(101);

        //Input Processors
        loadInputProcessor();

        //Create Final Boss
        Texture boss =assetManager.get("characters/finalboss_three.png");
        finalBoss = new Dinosaur(boss,-300,50);


        //Begin game
        gameState= GameState.PLAY;

        VELOCITY= laurence.getVelocity();
        SPEEDUPVELOCITY = VELOCITY*2;
        speedTimer=0;

        //Tutorial
        tutorial = tutorialPref.getBoolean("tutorialLVL8", false);
        if(!tutorial) {
            Texture swipeDownTexture = assetManager.get("tutorial/swipeDown.png");
            swipeDown = new Sprite(swipeDownTexture);
            swipeDown.setPosition(41 * 70, 50);
        }

    }
    //Load processors
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
            public void onRight()
            {
                if(laurence.getMovementState() == MainCharacter.MovementState.RUNNING)
                {
                    laurence.resetTimerAction();
                    laurence.setX(laurence.getX()+18);
                    laurence.setMovementState(MainCharacter.MovementState.ATTACKING);
                    game.playSound((Music)assetManager.get("music/sword.mp3"));
                } else if (laurence.getMovementState() == MainCharacter.MovementState.JUMPING || laurence.getMovementState()== MainCharacter.MovementState.FALLING)
                {
                    laurence.resetSecondaryActionTimer();
                    laurence.setMovementState(MainCharacter.MovementState.AIR_ATTACKING);
                    game.playSound((Music)assetManager.get("music/sword.mp3"));
                }
            }
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
                if(!speedUp && (laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                        || laurence.getMovementState() == MainCharacter.MovementState.STANDING))
                {
                    laurence.setVelocity(SPEEDUPVELOCITY);
                    speedUp=true;
                }
                if(laurence.getMovementState() == MainCharacter.MovementState.FALLING || laurence.getMovementState() == MainCharacter.MovementState.JUMPING)
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

        //Create Win Loose Stage
        createWinLooseStage();

        //Begin input processor
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessor = inputMultiplexer;
    }
    protected void loadBackground()
    {
        Texture backgroundTexture = assetManager.get("map/bookThreeBg.png");
        backgroundOne = new Sprite(backgroundTexture);
        backgroundTwo = new Sprite(backgroundTexture);
        backgroundOne.setPosition(0,0);
        backgroundTwo.setPosition(backgroundOne.getWidth(),0);
        Texture cloudsTexture = assetManager.get("map/clouds_tres.png");
        cloudsOne = new Sprite(cloudsTexture);
        cloudsTwo = new Sprite(cloudsTexture);
        cloudsOne.setPosition(0,0);
        cloudsTwo.setPosition(cloudsOne.getWidth(),0);
        Texture objectTexture = assetManager.get("map/bookThreeF.png");
        objectsOne = new Sprite(objectTexture);
        objectsTwo = new Sprite(objectTexture);
        objectsOne.setPosition(0,0);
        objectsTwo.setPosition(objectsOne.getWidth(),0);

    }


    @Override
    public void render(float delta) {
        int cx = (int)(laurence.getX()+70)/70;

        //Check if paused
        if(gameState==GameState.PLAY && (tutorial || cx!=13 || speedUp))
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
        finalBoss.render(batch,delta);
        batch.end();
        //Draw buttons and information
        batch.setProjectionMatrix(cameraHUD.combined);
        batch.begin();
        //Draw coin
        batch.draw(coinTexture,4* WIDTH /5+0.8f*coinTexture.getWidth(), HEIGHT -1.2f*coinTexture.getHeight());
        //Draw keys
        drawKeys();
        //Display score
        scoreDisplay.showMsg(batch, coinScore,9* WIDTH /10, HEIGHT,2,'c');

        if(!tutorial && cx==13)
        {
            swipeDown.setY(swipeDown.getY()+delta*VSWIPE);
            if(swipeDown.getY()>200 ||swipeDown.getY()<50)
                VSWIPE=-VSWIPE;
            swipeDown.draw(batch);
        }


        //End batch
        batch.end();
        //Draw current input scene
        drawInputScene();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if(gameState==GameState.PLAY)
            {
                pause();
            }

        }
    }




    private void update(float delta) {
        updateState(delta);
        updateCamera(delta);
        updateBackground(delta);
        updateBoss(delta);

    }

    private void updateBoss(float delta) {
        finalBoss.run(delta);
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
                if(laurence.getMovementState() == MainCharacter.MovementState.ATTACKING  || laurence.getMovementState() == MainCharacter.MovementState.AIR_ATTACKING)
                {
                    Integer number = Integer.parseInt((String) currentCell.getTile().getProperties().get("number"));
                    enemies++;
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
                    loose();
                }

            }
        }



    }

    protected void winOrLoose() {
        if(laurence.getX()< camera.position.x-3* WIDTH /4 || laurence.getY()<0)
        {
            loose();
        }
        else if(laurence.getX()>MAP_WIDTH)
        {
            if(!tutorial)
            {
                tutorialPref.putBoolean("tutorialLVL8",true);
                tutorialPref.flush();
            }
            win();
        }
    }


    private void updateCamera(float delta) {
        //Para que la cámara avance sola hasta el final de la pantalla
        float posX;
        if(laurence.getX()>= camera.position.x-WIDTH/4)
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
        assetManager.unload("map/nivelOcho.tmx");

        assetManager.unload("map/bookThreeF.png");
        assetManager.unload("map/bookThreeBg.png");
        assetManager.unload("map/clouds_tres.png");

        assetManager.unload("characters/laurence_burned.png");
        assetManager.unload("characters/laurence_celebrating_three.png");
        assetManager.unload("characters/laurence_falling_three.png");

        assetManager.unload("characters/finalboss_three.png");

        //Generic dispose
        disposeGenericLevel();
    }
}