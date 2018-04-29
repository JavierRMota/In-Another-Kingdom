package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.MainCharacter;
/**
 * Created by mota on 2/12/18.
 */

public class LevelOne extends GenericLevel {

    private Sprite tutorialSprite;
    private float SPEED =0.3f;
    private Preferences prefs = Gdx.app.getPreferences("Settings"), tutorialPref = Gdx.app.getPreferences("Tutorial");
    private boolean tutorial;


    //Constructor
    public LevelOne(Main game, int level)
    {
        super(game,level,200*70,125);
    }
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
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
        //Win and loose
        loadWinLooseTextures("characters/laurence_drowning.png","characters/laurence_celebrating.png","characters/laurence_burned.png");

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(61);

        //Input Processors
        loadInputProcessor();

        //Tutorial
        tutorial = tutorialPref.getBoolean("tutorialLVL1", false);
        if(!tutorial)
        {
            if(prefs.getBoolean("mode", true))
            {
                Texture tutorialTexture = assetManager.get("tutorial/pushButton.png");
                tutorialSprite = new Sprite(tutorialTexture);
                if(prefs.getBoolean("position", true)){
                    tutorialSprite.setPosition(WIDTH/16, 0);
                }

                else {
                    tutorialSprite.setPosition(14*WIDTH/16- tutorialSprite.getWidth()/2, 0);
                }
            }
            else{
                Texture tutorialTexture = assetManager.get("tutorial/swipeRight.png");
                tutorialSprite = new Sprite(tutorialTexture);
                tutorialSprite.setPosition(WIDTH/2 - tutorialSprite.getWidth()/2, HEIGHT/2-tutorialSprite.getHeight()/2);
            }
        }


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
        objectsOne.setPosition(0,0);
        objectsTwo.setPosition(objectsOne.getWidth(),0);

    }


    @Override
    public void render(float delta) {
        int cx = (int)(laurence.getX()+70)/70;
        if(!tutorial && cx<11 && laurence.getMovementState() != MainCharacter.MovementState.RUNNING && laurence.getMovementState()!= MainCharacter.MovementState.ATTACKING)
        {
            laurence.setMovementState(MainCharacter.MovementState.RUNNING);
        }
        //Check if paused
        if(gameState==GameState.PLAY && (tutorial || cx != 10 || laurence.getMovementState()== MainCharacter.MovementState.ATTACKING))
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
        //Draw coin
        batch.draw(coinTexture,4* WIDTH /5+0.8f*coinTexture.getWidth(), HEIGHT -1.2f*coinTexture.getHeight());
        //Draw keys
        drawKeys();
        //Display score
        scoreDisplay.showMsg(batch, coinScore,9* WIDTH /10, HEIGHT,2,'c');

        //Check tutorial
        if(!tutorial && cx == 10 && laurence.getMovementState()!= MainCharacter.MovementState.ATTACKING) {
            if(prefs.getBoolean("mode", true)){
                if(tutorialSprite.getScaleX() -+0.1f*delta>1.3 ||tutorialSprite.getScaleX() -+0.1f*delta<0.8)
                    SPEED =-SPEED;
                tutorialSprite.setScale(tutorialSprite.getScaleX() +SPEED*delta);
                tutorialSprite.draw(batch);
            }
            else{

                tutorialSprite.setX(tutorialSprite.getX()+delta*150);
                if(tutorialSprite.getX()>4.3f*WIDTH/8){
                    tutorialSprite.setX(WIDTH/2-tutorialSprite.getWidth()/2);
                }

                tutorialSprite.draw(batch);
            }
        }

        //End batch
        batch.end();
        //Draw current input scene
        drawInputScene();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            pause();
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
        TiledMapTileLayer.Cell currentCell = layer.getCell(cx,cy);
        if(currentCell!=null)
        {
            String cellType = (String) currentCell.getTile().getProperties().get("type");
            if(cellType.equals("enemy"))
            {
                if(laurence.getMovementState() == MainCharacter.MovementState.ATTACKING)
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
                tutorialPref.putBoolean("tutorialLVL1",true);
                tutorialPref.flush();
            }
            win();
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

        assetManager.unload("map/bookOneT.png");
        assetManager.unload("map/bookOneBg.png");
        assetManager.unload("map/clouds.png");

        assetManager.unload("characters/laurence_burned.png");
        assetManager.unload("characters/laurence_celebrating.png");
        assetManager.unload("characters/laurence_drowning.png");

        assetManager.unload("tutorial/pushButton.png");
        assetManager.unload("tutorial/swipeRight.png");
        //Generic dispose
        disposeGenericLevel();
    }



}
