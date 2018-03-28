package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

/**
 * Created by mota on 3/22/18.
 */

public abstract class GenericLevel extends GenericScreen {
    //Maps
    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer render;

    //Character
    protected MainCharacter laurence;

    //Scores
    protected int coins,keys, enemies;
    protected String coinScore;
    protected Text  scoreDisplay;
    protected boolean recolectedKeys[];
    protected int middleKey;

    //Textures
    protected Texture coinTexture;
    protected Texture emptyKeyTexture;
    protected Texture fullKeyTexture;

    //Sprites
    protected Sprite backgroundOne;
    protected Sprite backgroundTwo;
    protected Sprite cloudsOne;
    protected Sprite cloudsTwo;
    //Trees pyramids or whatever
    protected Sprite objectsOne;
    protected Sprite objectsTwo;


    //Camera and scene
    protected OrthographicCamera cameraHUD;
    protected Viewport viewHUD;
    protected Stage buttonScene;
    protected Stage pauseScene;
    protected InputProcessor inputProcessor;

    //Controlador de juego
    protected GameState gameState;

    public GenericLevel(Main game) {
        super(game);
    }

    //Create STATIC Camera
    protected void createHUD() {
        cameraHUD = new OrthographicCamera(WIDTH, HEIGHT);
        cameraHUD.position.set(WIDTH /2, HEIGHT /2,0);
        cameraHUD.update();
        viewHUD = new StretchViewport(WIDTH, HEIGHT,cameraHUD);

    }

    //Character loading
    protected void loadCharacter() {
        Texture standing = assetManager.get("characters/laurence_descanso.png");
        Texture running = assetManager.get("characters/laurence_running.png");
        Texture jumping = assetManager.get("characters/tira_salto.png");
        Texture dodging = assetManager.get("characters/tira_marometa.png");
        Texture attacking = assetManager.get("characters/laurence_attacking.png");

        //Load Character
        laurence = new MainCharacter(
                standing, //Standing Position
                running, //Running Position
                jumping, //Jumping Position
                dodging, //Dodging Position
                attacking);//Attacking position
    }

    //Method to load score textures
    protected void loadTextures()
    {
        coinTexture=assetManager.get("gameObjects/moneda.png");
        fullKeyTexture = assetManager.get("gameObjects/llaveFull.png");
        emptyKeyTexture = assetManager.get("gameObjects/llaveEmpty.png");
    }
    //Method to load map
    protected void loadMap(String map) {
        tiledMap = assetManager.get(map);
        render = new OrthogonalTiledMapRenderer(tiledMap);

    }

    //Method to create the pause
    protected void createPauseStage() {
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
    }

    //Method to create the buttons
    protected void createButtonStage() {
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
                    laurence.setX(laurence.getX()+18);
                    laurence.setMovementState(MainCharacter.MovementState.ATTACKING);

                }


            }
        });
        buttonScene.addActor(btnAction);
    }

    //Method to decide if the character wins or looses (depends on the level)
    protected abstract void winOrLoose();
    //Method to load the background (Assets depend on the level)
    protected abstract void loadBackground();
    //Method to start scores
    protected void scoreInit(int midKey) {
        coinScore="00";
        coins=0;
        keys=0;
        enemies=0;
        recolectedKeys = new boolean[3];
        middleKey=midKey;
        scoreDisplay = new Text();
    }
    //Updating scores
    protected void updateScore() {
        coinScore = String.format("%02d", coins);
    }

    protected void drawKeys() {
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
    }

    protected void drawBackground() {
        backgroundOne.draw(batch);
        backgroundTwo.draw(batch);

        cloudsOne.draw(batch);
        cloudsTwo.draw(batch);
        objectsOne.draw(batch);
        objectsTwo.draw(batch);

    }

    protected void drawInputScene() {
        if(gameState == GameState.PLAY)
        {
            buttonScene.draw();
        }
        else
        {
            pauseScene.draw();
        }
    }

    protected void checkCoins(int cx, int cy,TiledMapTileLayer layer)
    {
        TiledMapTileLayer.Cell currentCellDown = layer.getCell(cx,cy);
        TiledMapTileLayer.Cell currentCellUp = layer.getCell(cx,cy+1);

        if(currentCellDown != null)
        {
            checkCellForItem(cx, cy, layer, currentCellDown);

        }
        if(currentCellUp!=null)
        {
            checkCellForItem(cx, cy+1, layer, currentCellUp);
        }

    }

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
    }

    protected void updateBackground(float delta) {
        cloudsOne.setX(cloudsOne.getX()-100*delta);
        cloudsTwo.setX(cloudsTwo.getX()-100*delta);
        //Set clouds
        if(camera.position.x - 3*cloudsOne.getWidth()/2>cloudsOne.getX())
        {
            cloudsOne.setX(cloudsTwo.getX()+cloudsTwo.getWidth());
        }
        else if(camera.position.x - 3*cloudsTwo.getWidth()/2>cloudsTwo.getX())
        {
            cloudsTwo.setX(cloudsOne.getX()+cloudsOne.getWidth());
        }
        //Set background
        if(camera.position.x - 3*backgroundOne.getWidth()/2>backgroundOne.getX())
        {
            backgroundOne.setX(backgroundTwo.getX()+backgroundTwo.getWidth());
        }
        else if(camera.position.x - 3*backgroundTwo.getWidth()/2>backgroundTwo.getX())
        {
            backgroundTwo.setX(backgroundOne.getX()+backgroundOne.getWidth());
        }
        //Set objetcs
        if(camera.position.x - 3*objectsOne.getWidth()/2>objectsOne.getX())
        {
            objectsOne.setX(objectsTwo.getX()+objectsTwo.getWidth());
        }
        else if(camera.position.x - 3*objectsTwo.getWidth()/2>objectsTwo.getX())
        {
            objectsTwo.setX(objectsOne.getX()+objectsOne.getWidth());
        }
    }

    //Change proportion of the views
    @Override
    public void resize(int width, int height) {
        viewHUD.update(width,height);
        view.update(width,height);
    }

    //What we do when we press pause
    @Override
    public void pause() {
        gameState=GameState.PAUSE;
        Gdx.input.setInputProcessor(pauseScene);
        //laurence.setMovementState(MainCharacter.MovementState.STANDING);

    }

    //What we do when we resume the game
    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputProcessor);
        gameState = GameState.PLAY;
        //laurence.setMovementState(MainCharacter.MovementState.RUNNING);

    }


}
