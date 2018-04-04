package mx.com.nullpointer.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.utils.Enemy;
import mx.com.nullpointer.utils.GameState;
import mx.com.nullpointer.utils.GenericLevel;
import mx.com.nullpointer.utils.LongWeapon;
import mx.com.nullpointer.utils.MainCharacter;
/**
 * Created by mota on 2/12/18.
 */

public class LevelTwo extends GenericLevel {
    //Enemy
    private Enemy finalBoss;
    private Array<LongWeapon> fireList,friendlyFireList;
    private float timerBall;
    private Texture fireballBlue, fireballRed;
    private boolean fightStart;


    //Constructor
    public LevelTwo(Main game, int level)
    {
        super(game,level,200*70,132);
    }
    @Override
    public void show() {
        //Create the camera for all game information and buttons
        createHUD();
        //Load TiledMap
        loadMap("map/nivelDos.tmx");
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

        //Create Final Boss
        Texture boss =assetManager.get("characters/finalboss.png");
        finalBoss = new Enemy(boss,MAP_WIDTH-boss.getWidth()/4,100);
        fireList = new Array<LongWeapon>();
        friendlyFireList=new Array<LongWeapon>();
        timerBall=0;
        fightStart =false;
        fireballBlue = assetManager.get("characters/fireball.png");
        fireballRed = assetManager.get("characters/fireballRED.png");

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
        objectsOne.setPosition(0,-40);
        objectsTwo.setPosition(objectsOne.getWidth(),-40);

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
        finalBoss.render(batch,delta);
        //Fireballs
        drawFireballs();
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

    private void drawFireballs() {
        for( LongWeapon weapon : fireList)
        {
            weapon.render(batch);
        }
        for( LongWeapon weapon : friendlyFireList)
        {
            weapon.render(batch);
        }
    }

    private void updateFireballs() {
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        Rectangle laurenceRect = laurence.getSprite().getBoundingRectangle();
        Rectangle enemyRect = finalBoss.getSprite().getBoundingRectangle();
        for(int index = 0; index< friendlyFireList.size;index++)
        {
            LongWeapon weapon= friendlyFireList.get(index);
            Rectangle fireballRect = weapon.getSprite().getBoundingRectangle();
            for(int index_two = 0; index_two< fireList.size;index_two++)
            {
                LongWeapon weapon_two= fireList.get(index);
                Rectangle fireballRectTwo = weapon_two.getSprite().getBoundingRectangle();
                if(fireballRect.overlaps(fireballRectTwo))
                {
                    friendlyFireList.removeIndex(index);
                    fireList.removeIndex(index_two);
                    break;
                }
            }
            if(!fightStart)
            {
                int cx = (int)(weapon.getX()+70)/70;
                int cy = (int)(weapon.getY())/70;
                TiledMapTileLayer.Cell currentCell = layer.getCell(cx,cy);
                if(currentCell!=null)
                {
                    String cellType = (String) currentCell.getTile().getProperties().get("type");
                    if(cellType.equals("enemy"))
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
                        friendlyFireList.removeIndex(index);
                        break;

                    }
                    else if(cellType.equals("platform"))
                    {
                        friendlyFireList.removeIndex(index);
                        break;
                    }
                }

            }else
                {
                    if(enemyRect.overlaps(fireballRect))
                    {
                        win();
                    }
                }

        }
        for(int index = 0; index< fireList.size;index++)
        {

            LongWeapon weapon = fireList.get(index);
            if(weapon.getX()<camera.position.x-WIDTH/2)
            {
                fireList.removeIndex(index);
                break;
            }
            Rectangle fireballRect = weapon.getSprite().getBoundingRectangle();


            if (laurenceRect.overlaps(fireballRect))
            {
                if(laurence.getMovementState() == MainCharacter.MovementState.ATTACKING)
                {
                    weapon.changeDirection();
                    friendlyFireList.add(weapon);
                    fireList.removeIndex(index);
                }else if(laurence.getMovementState() == MainCharacter.MovementState.RUNNING){

                    loose();
                }
            }

        }
    }


    private void update(float delta) {
        updateState(delta);
        updateCamera(delta);
        updateBackground(delta);
        updateFireballs();
    }
    @Override
    protected void updateBackground(float delta) {
        cloudsOne.setX(cloudsOne.getX()-100*delta);
        cloudsTwo.setX(cloudsTwo.getX()-100*delta);
        if(fightStart)
        {
            objectsOne.setX(objectsOne.getX()-100*delta);
            objectsTwo.setX(objectsTwo.getX()-100*delta);
            cloudsOne.setX(cloudsOne.getX()-50*delta);
            cloudsTwo.setX(cloudsTwo.getX()-50*delta);
        }
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



    private void updateState(float delta)
    {
        int cx = (int)(laurence.getX()+70)/70;
        int cy = (int)(laurence.getY())/70;
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        checkCoins(cx,cy,layer);
        checkEnemies(cx,cy,layer);
        checkFire(delta);
        if(laurence.getX()<MAP_WIDTH - 7*WIDTH /8)
            laurence.move(layer,delta, cx, cy);
        else
            fightStart= true;
        winOrLoose();
    }

    private void checkFire(float delta) {
        timerBall+=delta;
        if(timerBall >2)
        {
            timerBall=0;
            float posX;
            if(fightStart)
                posX=finalBoss.getX();
            else
                posX = laurence.getX()+WIDTH;

            fireList.add(new LongWeapon(fireballBlue,fireballRed, posX,laurence.getY()+70, finalBoss.getX(),fightStart));
        }
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
        assetManager.unload("map/nivelDos.tmx");
        //Music
        assetManager.unload("music/nivelUno.mp3");
        //Background
        assetManager.unload("map/bookOneT.png");
        assetManager.unload("map/bookOneBg.png");
        assetManager.unload("map/clouds.png");
        //Laurence animation
        assetManager.unload("characters/laurence_descanso.png");
        assetManager.unload("characters/laurence_running.png");
        assetManager.unload("characters/tira_salto.png");
        assetManager.unload("characters/tira_marometa.png");
        assetManager.unload("characters/laurence_attacking.png");
        //Laurence win, loose
        assetManager.unload("characters/laurence_burned.png");
        assetManager.unload("characters/laurence_celebrating.png");
        assetManager.unload("characters/laurence_drowning.png");
        //Dragon
        assetManager.unload("characters/finalboss.png");
        assetManager.unload("characters/fireball.png");
        assetManager.unload("characters/fireballRED.png");
        //Background win loose
        assetManager.unload("background/winLooseBg.png");
        //Score
        assetManager.unload("gameObjects/llave.png");
        assetManager.unload("gameObjects/star.png");
        assetManager.unload("gameObjects/llaveFull.png");
        assetManager.unload("gameObjects/llaveEmpty.png");
        assetManager.unload("gameObjects/moneda.png");
        //Buttons
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
        assetManager.unload("gameObjects/actionbtn.png");
        assetManager.unload("gameObjects/actionbtnpress.png");


        buttonScene.dispose();
        looseScene.dispose();
        winScene.dispose();
        tiledMap.dispose();
    }



}

