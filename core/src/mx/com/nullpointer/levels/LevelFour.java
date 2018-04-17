package mx.com.nullpointer.levels;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.Mummy;

public class LevelFour extends GenericLevel {
    private Texture mummyTexture;
    private Array<Mummy> mummies;
    //Constructor
    public LevelFour(Main game, int level)
    {
        super(game,level,200*70,183);
    }
    @Override
    public void show() {
        //Create the camera for all game information and buttons
        createHUD();
        //Load TiledMap
        loadMap("map/nivelTres.tmx");
        //Load Textures
        loadTextures();

        //Load Character
        loadCharacter();

        //Load background
        loadBackground();

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(113);

        //Input Processors
        loadInputProcessor();

        //Begin enemies array
        mummies = new Array<Mummy>();
        mummyTexture = assetManager.get("characters/momia.png");
        initEnemies();

        //Begin game
        gameState= GameState.PLAY;

    }
    protected  void initEnemies(){
        mummies.add(new Mummy(mummyTexture,15*70,70));
        mummies.add(new Mummy(mummyTexture,24*70,70*4));
        mummies.add(new Mummy(mummyTexture,36*70,70));
        mummies.add(new Mummy(mummyTexture,35*70,70*6));
        mummies.add(new Mummy(mummyTexture,49*70,70*4));
        mummies.add(new Mummy(mummyTexture,55*70,70));
        mummies.add(new Mummy(mummyTexture,59*70,70*5));
        mummies.add(new Mummy(mummyTexture,80*70,70*5));
        mummies.add(new Mummy(mummyTexture,89*70,70*2));
        mummies.add(new Mummy(mummyTexture,105*70,70*5));


    }
    protected void loadBackground()
    {
        Texture backgroundTexture = assetManager.get("map/bookTwoBg.png");
        backgroundOne = new Sprite(backgroundTexture);
        backgroundTwo = new Sprite(backgroundTexture);
        backgroundOne.setPosition(0,0);
        backgroundTwo.setPosition(backgroundOne.getWidth(),0);
        Texture cloudsTexture = assetManager.get("map/clouds.png");
        cloudsOne = new Sprite(cloudsTexture);
        cloudsTwo = new Sprite(cloudsTexture);
        cloudsOne.setPosition(0,0);
        cloudsTwo.setPosition(cloudsOne.getWidth(),0);
        Texture objectTexture = assetManager.get("map/bookTwoP.png");
        objectsOne = new Sprite(objectTexture);
        objectsTwo = new Sprite(objectTexture);
        objectsOne.setPosition(0,0);
        objectsTwo.setPosition(objectsOne.getWidth(),0);

    }


    @Override
    public void render(float delta) {
        //Check if paused
        if(gameState==GameState.PLAY)
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
        //Enemies
        drawEnemies(delta);
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
    }

    private void drawEnemies(float delta) {
        for(Mummy mummy: mummies)
        {
            mummy.render(batch,delta);
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
            loose();
        }
        else if(laurence.getX()>MAP_WIDTH)
        {
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
        assetManager.unload("map/nivelTres.tmx");
        assetManager.unload("music/nivelUno.mp3");
        assetManager.unload("music/sword.mp3");
        assetManager.unload("map/bookTwoP.png");
        assetManager.unload("characters/laurence_descanso.png");
        assetManager.unload("characters/laurence_running.png");
        assetManager.unload("characters/tira_salto.png");
        assetManager.unload("characters/tira_marometa.png");
        assetManager.unload("characters/laurence_burned.png");
        assetManager.unload("characters/laurence_celebrating.png");
        assetManager.unload("characters/laurence_drowning.png");
        assetManager.unload("background/winLooseBg.png");
        assetManager.unload("characters/momia.png");
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
        assetManager.unload("btn/backdarkbtn.png");
        assetManager.unload("btn/backdarkbtnpress.png");
        assetManager.unload("btn/levelsdarkbtn.png");
        assetManager.unload("btn/levelsdarkbtnpress.png");
        assetManager.unload("btn/nextbtn.png");
        assetManager.unload("btn/nextbtnpress.png");
        assetManager.unload("btn/resetdarkbtn.png");
        assetManager.unload("btn/resetdarkbtnpress.png");
        assetManager.unload("map/bookTwoBg.png");
        assetManager.unload("map/clouds.png");
        assetManager.unload("gameObjects/actionbtn.png");
        assetManager.unload("gameObjects/actionbtnpress.png");
        assetManager.unload("characters/laurence_attacking.png");
        buttonScene.dispose();
        looseScene.dispose();
        winScene.dispose();
        tiledMap.dispose();
    }


}
