package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mota on 2/13/18.
 */

public class MainCharacter extends Objeto
{
    private Animation runningAnimation,jumpingAnimation,dodgingAnimation;
    private float timerRunning, timerAction;
    private float x,y; //Coordenadas de dónde se moverá
    private float DY = 40, DX=400,G=85;


    //Estados de movimiento
    MovementState movementState = MovementState.RUNNING;


    public MainCharacter(Texture standingTexture,Texture runningTexture,Texture jumpingTexture, Texture dodgingTexture)
    {
        //Cargamos las animaciones

        //Animación de correr
        TextureRegion region = new TextureRegion(runningTexture);
        //Tamaño
        TextureRegion[][] characterTexture = region.split(396,228);
        runningAnimation = new Animation(0.05f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //Animación de saltar
        region = new TextureRegion(jumpingTexture);
        //Tamaño
        characterTexture = region.split(266,275);
        jumpingAnimation = new Animation(0.08f,
                characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        jumpingAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        //Animación de agacharse
        region = new TextureRegion(dodgingTexture);
        //Tamaño
        characterTexture = region.split(241,254);
        dodgingAnimation = new Animation(0.08f,
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
        x = 0;
        y = 64;
    }
    public void render(SpriteBatch batch) {
        if (movementState == MovementState.STANDING)
        {
            this.draw(batch);
        }
        else if(movementState == MovementState.RUNNING)
        {
            timerRunning += Gdx.graphics.getDeltaTime();
            move();
            TextureRegion region = (TextureRegion) runningAnimation.getKeyFrame(timerRunning);
            batch.draw(region, x, y);
        }
        else if (movementState == MovementState.JUMPING_UP || movementState == MovementState.JUMPING_PREPARE || movementState == MovementState.JUMPING_DOWN )
        {
            timerAction+=Gdx.graphics.getDeltaTime();
            if(timerAction> 0.08*6 && timerAction<0.08*11) movementState= MovementState.JUMPING_UP;
            else if (timerAction> 0.08*11) movementState=MovementState.JUMPING_DOWN;
            move();
            if(timerAction>=0.08*16)
            {
                movementState = MovementState.RUNNING;
                timerAction=0;
            }
            TextureRegion region = (TextureRegion) jumpingAnimation.getKeyFrame(timerAction);
            batch.draw(region,x,y);
        }
        else if(movementState == MovementState.DODGING)
        {
            timerAction += Gdx.graphics.getDeltaTime();
            move();
            if(timerAction>=0.08*12)
            {
                movementState = MovementState.RUNNING;
                timerAction=0;
            }
            TextureRegion region = (TextureRegion) dodgingAnimation.getKeyFrame(timerAction);
            batch.draw(region, x, y);

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
    public void move()
    {

        if(movementState == MovementState.JUMPING_UP|| movementState ==MovementState.JUMPING_DOWN)
        {

            this.y+= timerAction*DY - 0.5*G*timerAction*timerAction;
            this.x+= 1.5*DX*Gdx.graphics.getDeltaTime();
        }
        else if(movementState == MovementState.RUNNING || movementState == MovementState.JUMPING_PREPARE || movementState == MovementState.DODGING)
            this.x += DX*Gdx.graphics.getDeltaTime();
        sprite.setPosition(x, y);
    }

    public MovementState getMovementState() {
        return movementState;
    }


    public enum MovementState
    {
        RUNNING,
        JUMPING_UP,
        JUMPING_PREPARE,
        JUMPING_DOWN,
        DODGING,
        STANDING
    }





}
