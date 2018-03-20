package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by mota on 2/13/18.
 */

public class MainCharacter extends GameObject
{
    private Animation runningAnimation,jumpingAnimation,dodgingAnimation;
    private float timerRunning, timerAction;
    private float x,y; //Coordenadas de dónde se moverá
    private float VY = 30, VX=300,G=50;
    private boolean checkRun=false;


    //Estados de movimiento
    MovementState movementState = MovementState.RUNNING;


    public MainCharacter(Texture standingTexture,Texture runningTexture,Texture jumpingTexture, Texture dodgingTexture)
    {
        //Cargamos las animaciones

        //Animación de correr
        TextureRegion region = new TextureRegion(runningTexture);
        //Tamaño
        TextureRegion[][] characterTexture = region.split(198,114);
        runningAnimation = new Animation(0.04f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //Animación de saltar
        region = new TextureRegion(jumpingTexture);
        //Tamaño
        characterTexture = region.split(133,137);
        jumpingAnimation = new Animation(0.04f,
                characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        jumpingAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        //Animación de agacharse
        region = new TextureRegion(dodgingTexture);
        //Tamaño
        characterTexture = region.split(120,127);
        dodgingAnimation = new Animation(0.04f,
                characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11]);
        dodgingAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        //Inicializamos timers
        timerRunning = 0;
        timerAction = 0;

        // Quieto
        sprite = new Sprite(standingTexture);
        sprite.setPosition(0,64);

        //Posición
        x = GenericScreen.ANCHO/8;
        y = 70;
    }

    public  float getVelocity() {
        return VX;
    }

    public void render(SpriteBatch batch) {
        if (movementState == MovementState.STANDING)
        {
            timerRunning=0;
            timerAction=0;
            this.draw(batch);
        }
        else if(movementState == MovementState.RUNNING)
        {
            run(batch);
            timerAction=0;

        }
        else if (movementState == MovementState.JUMPING
                        || movementState == MovementState.JUMPING_PREPARE
                        || movementState == MovementState.FALLING
                        || movementState == MovementState.JUMPING_END )
        {
            if( movementState == MovementState.JUMPING_PREPARE && timerRunning%16< 15*0.04f)
            {
                run(batch);
            }
            else
            {
                jump(batch);

            }
        }
        else if(movementState == MovementState.DODGING)
        {
            if(timerRunning%16< 15*0.04f && !checkRun)
            {
                run(batch);
            }
            else
            {
                checkRun=true;
                dodge(batch);

            }
        }
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public void setMovementState(MovementState movement)
    {
        this.movementState = movement;
    }
    public void setX(float x)
    {
        this.x=x;
    }
    public void setY(float y)
    {
        this.y =y;
    }
    public  void move(TiledMapTileLayer layer, float delta, int cx,int cy)
    {

        /*if(movementState==MovementState.JUMPING_PREPARE
                ||movementState==MovementState.JUMPING_END
                ||movementState==MovementState.JUMPING
                ||movementState==MovementState.FALLING
                ||movementState==MovementState.DODGING)timerAction+=delta;
        else if(movementState==MovementState.RUNNING)timerRunning+=delta;*/

        TiledMapTileLayer.Cell currentCellDown = layer.getCell(cx,cy-1);
        TiledMapTileLayer.Cell currentCellUp = layer.getCell(cx,cy+2);
        if(movementState == MovementState.JUMPING && currentCellUp!=null
                && (currentCellUp.getTile().getId()==2
                ||currentCellUp.getTile().getId()== 3
                ||currentCellUp.getTile().getId() == 5
                ||currentCellUp.getTile().getId() == 1
                ||currentCellUp.getTile().getId() == 7
                ||currentCellUp.getTile().getId() == 6))
        {
            this.movementState=MovementState.FALLING;
            timerAction=0;

        }
        else if(movementState == MovementState.JUMPING)
        {
            this.y+=timerAction*VY-0.5*G*timerAction*timerAction;

        }
        if(currentCellDown !=null
               && (currentCellDown.getTile().getId()==2
                ||currentCellDown.getTile().getId()== 3
                ||currentCellDown.getTile().getId() == 5
                ||currentCellDown.getTile().getId() == 1
                ||currentCellDown.getTile().getId() == 7
                ||currentCellDown.getTile().getId()== 6))
        {
            if(movementState == MovementState.FALLING)
            {
                movementState = MovementState.JUMPING_END;
                timerAction= 0.08f*12;
                this.y = (cy)*70;
            }
        }
        else if(currentCellDown!=null && this.movementState!= MovementState.JUMPING)
        {
            this.movementState = MovementState.FALLING;
            this.y -=0.5*G*timerAction*timerAction;
        }
        else if(currentCellDown==null &&this.movementState!=MovementState.JUMPING &&this.movementState!=MovementState.JUMPING_PREPARE)
        {
            this.movementState = MovementState.FALLING;
            this.y -=0.5*G*timerAction*timerAction;
        }


        if(canMove(layer,cx,cy))
        {
            this.x+=VX*delta;
        }
        sprite.setPosition(x, y);
    }
    public boolean canMove(TiledMapTileLayer layer, int cx, int cy)
    {
        TiledMapTileLayer.Cell nextCell = layer.getCell(cx+1,cy);
        if(nextCell!= null && nextCell.getTile().getId() != 11 && nextCell.getTile().getId() !=13)
        {
            this.VX =300;
            return false;
        }
        this.VX =400;
        return true;

    }
    public void run(SpriteBatch batch)
    {
        timerRunning += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) runningAnimation.getKeyFrame(timerRunning);
        batch.draw(region, x, y);
    }
    public void jump(SpriteBatch batch)
    {
        timerAction+=Gdx.graphics.getDeltaTime()*1.4f;
        if(timerAction>0.04*6 && movementState==MovementState.JUMPING_PREPARE) movementState=MovementState.JUMPING;
        else if (VY*timerAction-timerAction*G*0.5*timerAction<0  &&movementState!=MovementState.FALLING && movementState!=MovementState.JUMPING_END) movementState= MovementState.FALLING;
        if(movementState== MovementState.FALLING)
        {
            TextureRegion region = (TextureRegion) jumpingAnimation.getKeyFrame(0.04f*9);
            batch.draw(region,x,y);

        }
        else
        {
            if(movementState== MovementState.JUMPING_END && timerAction >0.04*15)
            {
                movementState =MovementState.RUNNING;
                timerAction=0;
                timerRunning=0;
            }

            TextureRegion region;
            if(timerAction>=0.04*9 && movementState == MovementState.JUMPING)
                region = (TextureRegion) jumpingAnimation.getKeyFrame(0.04f*9);
            else
                region = (TextureRegion) jumpingAnimation.getKeyFrame(timerAction);
            batch.draw(region,x,y);
        }

    }
   public void dodge(SpriteBatch batch)
    {
        timerAction += Gdx.graphics.getDeltaTime();
        if(timerAction>=0.04f*12)
        {
            movementState = MovementState.RUNNING;
            timerAction=0;
            timerRunning=0;
        }
        TextureRegion region = (TextureRegion) dodgingAnimation.getKeyFrame(timerAction);
        batch.draw(region, x, y);
    }

    public MovementState getMovementState() {
        return movementState;
    }


    public enum MovementState
    {
        RUNNING,
        JUMPING,
        JUMPING_PREPARE,
        FALLING,
        JUMPING_END,
        DODGING,
        STANDING
    }





}
