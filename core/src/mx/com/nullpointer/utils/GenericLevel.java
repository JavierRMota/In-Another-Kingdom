package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.LoadingScreen;
import mx.com.nullpointer.inanotherkingdom.Main;

/**
 * Created by mota on 3/22/18.
 */

public abstract class GenericLevel extends GenericScreen {
    //ULTIMATE LEVEL
    protected static final int ULTIMATE_LEVEL = 3;
    //Maps
    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer render;
    protected final float MAP_WIDTH;

    //Character
    protected MainCharacter laurence;

    //Scores
    protected int coins,keys, enemies;
    protected String coinScore;
    protected Text  scoreDisplay;
    protected boolean recolectedKeys[];
    protected int middleKey;
    protected final int TOTAL_COINS;

    //Textures
    protected Texture coinTexture;
    protected Texture emptyKeyTexture;
    protected Texture fullKeyTexture;
    //Win and loose
    protected Texture winLooseBackground;
    protected Texture laurenceBurnedLoose;
    protected Texture laurenceDrownedLoose;
    protected Texture laurenceCelebration;
    protected Texture starTexture,keyTexture;
    //TEXT
    protected Text text;

    //Sprites
    //Background
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
    protected Stage winScene;
    protected Stage looseScene;
    protected InputProcessor inputProcessor;

    //Controlador de juego
    protected GameState gameState;

    //ID
    protected int LVL;

    public GenericLevel(Main game, int level, int mapWidth, int totalCoins) {
        super(game);
        LVL =level;
        MAP_WIDTH =mapWidth;
        TOTAL_COINS = totalCoins;

    }

    //Create STATIC Camera
    protected void createHUD() {
        cameraHUD = new OrthographicCamera(WIDTH, HEIGHT);
        cameraHUD.position.set(WIDTH /2, HEIGHT /2,0);
        cameraHUD.update();
        viewHUD = new FitViewport(WIDTH, HEIGHT,cameraHUD);

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

        laurenceBurnedLoose = assetManager.get("characters/laurence_burned.png");
        laurenceCelebration=assetManager.get("characters/laurence_celebrating.png");
        laurenceDrownedLoose = assetManager.get("characters/laurence_drowning.png");
        winLooseBackground = assetManager.get("background/winLooseBg.png");
        starTexture = assetManager.get("gameObjects/star.png");
        keyTexture = assetManager.get("gameObjects/llave.png");
    }
    //Method to load map
    protected void loadMap(String map) {
        tiledMap = assetManager.get(map);
        render = new OrthogonalTiledMapRenderer(tiledMap);

    }

    //Load processors
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
                if(laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                        || laurence.getMovementState() == MainCharacter.MovementState.STANDING)
                    laurence.setMovementState(MainCharacter.MovementState.DODGING);
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

    //Method to create the pause
    protected void createPauseStage() {
        //Pause Scene
        pauseScene = new Stage(viewHUD);
        //Button reset
        Texture resetTexture =  assetManager.get("btn/resetbtn.png");
        Texture resetPressTexture  = assetManager.get("btn/resetbtnpress.png");
        TextureRegionDrawable trdReset = new TextureRegionDrawable(new TextureRegion(resetTexture));
        TextureRegionDrawable trdResetPress = new TextureRegionDrawable(new TextureRegion(resetPressTexture));
        ImageButton btnReset = new ImageButton(trdReset,trdResetPress);
        btnReset.setPosition(3*WIDTH/4 -btnReset.getWidth()/2,HEIGHT/2-btnReset.getHeight()/2);
        btnReset.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVL));
            }
        });
        pauseScene.addActor(btnReset);
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

    //Create win and loose stage
    protected void createWinLooseStage()
    {
        winScene = new Stage(viewHUD);
        looseScene = new Stage(viewHUD);

        //Reset
        Texture resetTexture =  assetManager.get("btn/resetdarkbtn.png");
        Texture resetPressTexture  = assetManager.get("btn/resetdarkbtnpress.png");
        TextureRegionDrawable trdReset = new TextureRegionDrawable(new TextureRegion(resetTexture));
        TextureRegionDrawable trdResetPress = new TextureRegionDrawable(new TextureRegion(resetPressTexture));
        ImageButton btnReset = new ImageButton(trdReset,trdResetPress);
        btnReset.setPosition(WIDTH/2 -btnReset.getWidth()/2,3*HEIGHT /16-btnReset.getHeight()/4);
        btnReset.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVL));
            }
        });
        winScene.addActor(btnReset);

        if(LVL-5 < ULTIMATE_LEVEL-1)
        {
        //Button next
        Texture nextTexture = assetManager.get("btn/nextbtn.png");
        Texture nextPressTexture = assetManager.get("btn/nextbtnpress.png");
        TextureRegionDrawable trdNext = new TextureRegionDrawable(new TextureRegion(nextTexture));
        TextureRegionDrawable trdNextPress = new TextureRegionDrawable(new TextureRegion(nextPressTexture));
        ImageButton btnNext = new ImageButton(trdNext,trdNextPress);
        btnNext.setPosition(3*WIDTH /4 - btnNext.getWidth()/2, 3*HEIGHT /16 - btnNext.getHeight()/4);
        btnNext.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game, LVL+1));
            }
        });
        winScene.addActor(btnNext);
        }
        //Back Button
        Texture backTexture = assetManager.get("btn/backdarkbtn.png");
        Texture backPressTexture = assetManager.get("btn/backdarkbtnpress.png");
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backTexture));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(backPressTexture));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(WIDTH /4 - btnBack.getWidth()/2, 3*HEIGHT /16 - btnBack.getHeight()/4);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,MENU));
            }
        });
        winScene.addActor(btnBack);


        //Loose reset button
        ImageButton btnLooseReset = new ImageButton(trdReset,trdResetPress);
        btnLooseReset.setPosition(3*WIDTH/4 -btnLooseReset.getWidth()/2,3*HEIGHT /16-btnLooseReset.getHeight()/4);
        btnLooseReset.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVL));
            }
        });
        looseScene.addActor(btnLooseReset);

        //Loose back button
        ImageButton btnLooseBack = new ImageButton(trdBack,trdBackPress);
        btnLooseBack.setPosition(WIDTH /4 - btnLooseBack.getWidth()/2, 3*HEIGHT /16 - btnLooseBack.getHeight()/4);
        btnLooseBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,MENU));
            }
        });
        looseScene.addActor(btnLooseBack);

        //Levels Button
        Texture levelsTexture = assetManager.get("btn/levelsdarkbtn.png");
        Texture levelsPressTexture = assetManager.get("btn/levelsdarkbtnpress.png");
        TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(levelsTexture));
        TextureRegionDrawable trdLevelsPress = new TextureRegionDrawable(new TextureRegion(levelsPressTexture));
        ImageButton btnLevels = new ImageButton(trdLevels,trdLevelsPress);
        btnLevels.setPosition(WIDTH /2 - btnLevels.getWidth()/2, 3*HEIGHT /16 - btnLevels.getHeight()/4);
        btnLevels.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LEVELS));
            }
        });
        looseScene.addActor(btnLevels);

        text = new Text(55/255f,26/255f,2/255f);

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
        btnPause.setPosition(WIDTH /16 - btnPause.getWidth()/2, HEIGHT - 1.5f*btnPause.getHeight());
        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                pause();
            }
        });
        buttonScene.addActor(btnPause);
        //Action Button
        Preferences prefs = Gdx.app.getPreferences("Settings");
        if(LVL !=LVLZERO && prefs.getBoolean("mode", true))
        {
            Texture actionTexture = assetManager.get("gameObjects/actionbtn.png");
            Texture actionPressTexture = assetManager.get("gameObjects/actionbtnpress.png");
            TextureRegionDrawable trdAction = new TextureRegionDrawable(new TextureRegion(actionTexture));
            TextureRegionDrawable trdActionPress = new TextureRegionDrawable(new TextureRegion(actionPressTexture));
            ImageButton btnAction = new ImageButton(trdAction,trdActionPress);
            if(prefs.getBoolean("position", true)){
                btnAction.setPosition(WIDTH/16, 0);
            }

            else {
                btnAction.setPosition(14*WIDTH/16- btnAction.getWidth()/2, 0);
            }

            btnAction.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    super.clicked(event, x, y);
                    if(laurence.getMovementState() == MainCharacter.MovementState.RUNNING)
                    {
                        laurence.resetTimerAction();
                        laurence.setX(laurence.getX()+18);
                        laurence.setMovementState(MainCharacter.MovementState.ATTACKING);
                        game.playSound((Music)assetManager.get("music/sword.mp3"));

                    }


                }
            });

            buttonScene.addActor(btnAction);

        }

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
        else if(gameState == GameState.PAUSE)
        {
            pauseScene.draw();
        }
        else if(gameState == GameState.WIN)
        {
            drawWin();
        }
        else if(gameState == GameState.LOOSE)
        {
           drawLoose();
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
        cloudsOne.setX(cloudsOne.getX()-50*delta);
        cloudsTwo.setX(cloudsTwo.getX()-50*delta);
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

    //Win
    protected void win()
    {
        gameState = GameState.WIN;
        Gdx.input.setInputProcessor(winScene);
        Preferences prefs = Gdx.app.getPreferences("Progress");
        int maxScore = prefs.getInteger("score"+(LVL-5),0);
        int lastLevel = prefs.getInteger("lastLevel", 0);
        if(lastLevel==LVL-5 && LVL-5 <ULTIMATE_LEVEL-1)
        {
            prefs.putInteger("lastLevel",LVL-4);
        }
        if(maxScore<keys*1000+coins*100+enemies*500)
        {
            prefs.putInteger("score"+(LVL-5),keys*1000+coins*100+enemies*500 );
        }
        prefs.flush();

    }
    protected void drawWin()
    {
        batch.begin();
        batch.draw(winLooseBackground,0,0);
        text.showMsg(batch, "You won!",WIDTH/2,7*HEIGHT/8,2,'c');
        batch.draw(laurenceCelebration,WIDTH/8 - laurenceCelebration.getWidth()/8,3*HEIGHT/8-laurenceCelebration.getHeight()/8);
        batch.draw(starTexture,WIDTH/2,3*HEIGHT/4-starTexture.getHeight());
        text.showMsg(batch, ""+(keys*1000+coins*100+enemies*500),5*WIDTH/8,3*HEIGHT/4,2,'l');
        batch.draw(coinTexture,WIDTH/2,5*HEIGHT/8-coinTexture.getHeight());
        text.showMsg(batch, coins+"/"+TOTAL_COINS,5*WIDTH/8,5*HEIGHT/8,2,'l');
        batch.draw(keyTexture,WIDTH/2,HEIGHT/2-keyTexture.getHeight());
        text.showMsg(batch, keys+"/3",5*WIDTH/8,HEIGHT/2,2,'l');
        batch.end();
        winScene.draw();
    }
    //Loose
    protected void loose()
    {
        gameState = GameState.LOOSE;
        Gdx.input.setInputProcessor(looseScene);
    }
    protected void drawLoose()
    {
        batch.begin();
        batch.draw(winLooseBackground,0,0);
        text.showMsg(batch, "Game Over!",WIDTH/2,7*HEIGHT/8,2,'c');
        if(laurence.getY()<0)
            batch.draw(laurenceDrownedLoose,WIDTH/8 - laurenceDrownedLoose.getWidth()/8,3*HEIGHT/8-laurenceDrownedLoose.getHeight()/8);
        else
            batch.draw(laurenceBurnedLoose,WIDTH/8 - laurenceBurnedLoose.getWidth()/8,HEIGHT/2-laurenceBurnedLoose.getHeight()/2);
        text.showMsg(batch, "Progress: "+String.format("%.2f",laurence.getX()/MAP_WIDTH*100) +"%",WIDTH/2,3*HEIGHT/4,1.5f,'l');
        batch.end();
        looseScene.draw();
    }



}
