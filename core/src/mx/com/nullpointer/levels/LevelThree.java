package mx.com.nullpointer.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.inanotherkingdom.MainCharacter;
import mx.com.nullpointer.inanotherkingdom.Mummy;

public class LevelThree extends GenericLevel {
    private  Texture mummyTexture;
    private Array<Mummy> mummies;
    //Constructor
    public LevelThree(Main game, int level)
    {
        super(game,level,200*70,196);
    }
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
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
        //Win and loose
        loadWinLooseTextures("characters/laurence_falling.png","characters/laurence_celebrating_two.png","characters/laurence_tangled.png");

        //Music adjustments
        this.game.changeMusic(LVLZERO);

        //Score initialization
        scoreInit(115);

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
        mummies.add(new Mummy(mummyTexture,23*70,70*4));
        mummies.add(new Mummy(mummyTexture,35*70,70));
        mummies.add(new Mummy(mummyTexture,34*70,70*6));
        mummies.add(new Mummy(mummyTexture,48*70,70*4));
        mummies.add(new Mummy(mummyTexture,54*70,70));
        mummies.add(new Mummy(mummyTexture,57*70,70*5));
        mummies.add(new Mummy(mummyTexture,75*70,70*5));
        mummies.add(new Mummy(mummyTexture,88*70,70));
        mummies.add(new Mummy(mummyTexture,111*70,70));
        mummies.add(new Mummy(mummyTexture,119*70,70*6));
        mummies.add(new Mummy(mummyTexture,143*70,70*4));
        mummies.add(new Mummy(mummyTexture,178*70,70));
        mummies.add(new Mummy(mummyTexture,180*70,70*4));


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
        laurence.render(batch, gameState);
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
                if(laurence.getMovementState() == MainCharacter.MovementState.ATTACKING  || laurence.getMovementState() == MainCharacter.MovementState.AIR_ATTACKING)
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

        if(laurence.getX()>MAP_WIDTH)
        {
            win();
        } else  if(laurence.getX()< camera.position.x-3* WIDTH /4 || laurence.getY()<0)
        {
            loose();
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
        assetManager.unload("map/nivelTres.tmx");
        //Background
        assetManager.unload("map/bookTwoP.png");
        assetManager.unload("map/bookTwoBg.png");
        assetManager.unload("map/clouds.png");
        //Laurence win, loose
        assetManager.unload("characters/laurence_tangled.png");
        assetManager.unload("characters/laurence_celebrating_two.png");
        assetManager.unload("characters/laurence_falling.png");
        //Enemies
        assetManager.unload("characters/momia.png");
        //Generic dispose
        disposeGenericLevel();
    }


}
