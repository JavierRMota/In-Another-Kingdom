package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import mx.com.nullpointer.inanotherkingdom.Dinosaur;
import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.MainCharacter;

public class LevelEight extends GenericLevel {
    private Texture endTexture;
    private Dinosaur finalBoss;
    private boolean tutorial,speedUp;
    private int VELOCITY, SPEEDUPVELOCITY;
    private float speedTimer;
    private Sprite tutorialSprite;
    private float SPEED =0.3f;
    private Preferences tutorialPref = Gdx.app.getPreferences("Tutorial");
    //Constructor
    public LevelEight(Main game, int level)
    {
        super(game,level,200*70,265);
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
        loadWinLooseTextures("characters/laurence_falling_three.png","characters/laurence_celebrating_three.png","characters/dinosaur_loose.png");

        //Load Character
        loadCharacter();

        //Load background
        loadBackground();

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(166);

        //Input Processors
        loadInputProcessor();

        InputMultiplexer inputMultiplexer = (InputMultiplexer) inputProcessor;
        //Add tap
        inputMultiplexer.addProcessor(new GestureDetector(new TapDetector()));
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputProcessor= inputMultiplexer;

        //Create Final Boss
        Texture boss =assetManager.get("characters/finalboss_three.png");
        finalBoss = new Dinosaur(boss,-500,50);


        //Begin game
        gameState= GameState.PLAY;

        VELOCITY= laurence.getVelocity();
        SPEEDUPVELOCITY = VELOCITY*2;
        speedTimer=0;

        //Tutorial
        tutorial = tutorialPref.getBoolean("tutorialLVL8", false);
        if(!tutorial) {
            Texture tapTexture = assetManager.get("tutorial/pushButton.png");
            tutorialSprite = new Sprite(tapTexture);
            tutorialSprite.setPosition(16 * 70, HEIGHT/2-tapTexture.getHeight()/2);
        }

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
        if(gameState==GameState.PLAY && (tutorial || cx!=16 || speedUp))
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
        if(!tutorial && cx==16)
        {
            if(tutorialSprite.getScaleX() -+0.1f*delta>1.3 ||tutorialSprite.getScaleX() -+0.1f*delta<0.8)
                SPEED =-SPEED;
            tutorialSprite.setScale(tutorialSprite.getScaleX() +SPEED*delta);
            tutorialSprite.draw(batch);
        }
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
        if(finalBoss.getX()+finalBoss.getSprite().getWidth()/2>= laurence.getX()+laurence.getSprite().getWidth())
        {
            loose();
        }
    }


    private void updateState(float delta)
    {
        int cx = (int)(laurence.getX()+70)/70;
        int cy = (int)(laurence.getY())/70;
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        if(speedUp)
        {
            speedTimer +=delta;
            if(speedTimer>1)
            {
                speedTimer=0;
                speedUp = false;
                laurence.setVelocity(VELOCITY);
            }

        }
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

        assetManager.unload("characters/dinosaur_loose.png");
        assetManager.unload("characters/laurence_celebrating_three.png");
        assetManager.unload("characters/laurence_falling_three.png");

        assetManager.unload("characters/finalboss_three.png");

        //Tutorial
        assetManager.unload("tutorial/pushButton.png");

        //Generic dispose
        disposeGenericLevel();
    }

    public class TapDetector implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {

            return false;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
            if(!speedUp && (laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                    || laurence.getMovementState() == MainCharacter.MovementState.STANDING))
            {
                laurence.setVelocity(SPEEDUPVELOCITY);
                speedUp=true;
            }
            return true;
        }

        @Override
        public boolean longPress(float x, float y) {

            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {

            return false;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {

            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {

            return false;
        }

        @Override
        public boolean zoom (float originalDistance, float currentDistance){

            return false;
        }

        @Override
        public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){

            return false;
        }
        @Override
        public void pinchStop () {
        }
    }
}