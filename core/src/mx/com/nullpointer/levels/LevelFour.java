package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.MainCharacter;
import mx.com.nullpointer.inanotherkingdom.Mummy;

public class LevelFour extends GenericLevel {
    private Texture mummyTexture;
    private Array<Mummy> mummies;
    //Constructor
    public LevelFour(Main game, int level)
    {
        super(game,level,200*70,257);
    }
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        //Create the camera for all game information and buttons
        createHUD();
        //Load TiledMap
        loadMap("map/nivelCuatro.tmx");
        //Load Textures
        loadTextures();

        //Load Character
        loadCharacter();

        //Load background
        loadBackground();

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(119);

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
        mummies.add(new Mummy(mummyTexture,10*70,70));
        mummies.add(new Mummy(mummyTexture,22*70,70*4));
        mummies.add(new Mummy(mummyTexture,31*70,70*6));
        mummies.add(new Mummy(mummyTexture,34*70,70));
        mummies.add(new Mummy(mummyTexture,42*70,70*4));
        mummies.add(new Mummy(mummyTexture,54*70,70));
        mummies.add(new Mummy(mummyTexture,54*70,70*5));
        mummies.add(new Mummy(mummyTexture,73*70,70));
        mummies.add(new Mummy(mummyTexture,82*70,70*4));
        mummies.add(new Mummy(mummyTexture,115*70,70));
        mummies.add(new Mummy(mummyTexture,107*70,70*4));
        mummies.add(new Mummy(mummyTexture,121*70,70*6));
        mummies.add(new Mummy(mummyTexture,133*70,70*7));
        mummies.add(new Mummy(mummyTexture,140*70,70));
        mummies.add(new Mummy(mummyTexture,144*70,70*5));
        mummies.add(new Mummy(mummyTexture,156*70,70*6));
        mummies.add(new Mummy(mummyTexture,165*70,70*2));

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            pause();
        }
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
        checkEnemies();
        updateMummies(layer,delta);
        laurence.move(layer,delta, cx, cy);
        winOrLoose();
    }

    private void updateMummies(TiledMapTileLayer layer, float delta) {
        for(Mummy mummy: mummies)
        {
            int cx = (int)(mummy.getX()+70)/70;
            int cy = (int)(mummy.getY())/70;
            mummy.move(layer,delta,cx,cy);
        }

    }

    private void checkEnemies() {
        for(int index = mummies.size-1; index>=0; index--)
        {
            Rectangle mummyRec = mummies.get(index).getSprite().getBoundingRectangle();
            Rectangle laurenceRec = laurence.getSprite().getBoundingRectangle();
            if(mummyRec.overlaps(laurenceRec))
            {
                if(laurence.getMovementState() == MainCharacter.MovementState.ATTACKING)
                {
                    mummies.removeIndex(index);
                    return;
                }
                else{
                    loose();
                }
            }
        }

    }

    protected void winOrLoose() {
        if((laurence.getX()< camera.position.x-3* WIDTH /4 || laurence.getY()<0) && laurence.getX()<MAP_WIDTH)
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
        //Map
        assetManager.unload("map/nivelCuatro.tmx");
        //Background
        assetManager.unload("map/bookTwoP.png");
        assetManager.unload("map/bookTwoBg.png");
        assetManager.unload("map/clouds.png");
        //Laurence win, loose
        assetManager.unload("characters/laurence_burned.png");
        assetManager.unload("characters/laurence_celebrating.png");
        assetManager.unload("characters/laurence_drowning.png");
        //Enemies
        assetManager.unload("characters/momia.png");
        //Generic dispose
        disposeGenericLevel();
    }



}
