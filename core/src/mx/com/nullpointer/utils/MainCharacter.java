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
    private Animation runningAnimation,jumpingAnimation;
    private float timerRunning, timerJumping;
    private float x,y; //Coordenadas de dónde se moverá
    private float DY = 12, DX=4,G=22;


    //Estados de movimiento
    MovementState movementState = MovementState.RUNNING;
    private static final float VX = 240;

    public MainCharacter(Texture runningTexture,Texture jumpingTexture)
    {
        TextureRegion region = new TextureRegion(runningTexture);
        TextureRegion[][] characterTexture = region.split(396,228);
        runningAnimation = new Animation(0.05f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);

        region = new TextureRegion(jumpingTexture);
        characterTexture = region.split(266,275);
        jumpingAnimation = new Animation(0.1f,
                characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        jumpingAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        timerRunning = 0;
        timerJumping = 0;
        // Quieto
        sprite = new Sprite(new Texture("characters/laurence_descanso.png"));
        sprite.setPosition(0,64);
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
        else if (movementState == MovementState.JUMPING)
        {
            timerJumping+=Gdx.graphics.getDeltaTime();
            move();
            if(timerJumping>=0.1*16)
            {
                movementState = MovementState.RUNNING;
                timerJumping=0;
            }
            TextureRegion region = (TextureRegion) jumpingAnimation.getKeyFrame(timerJumping);
            batch.draw(region,x,y);
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
        if(movementState == MovementState.JUMPING)
            this.y+= timerJumping*DY - 0.5*G*timerJumping*timerJumping;
        this.x += DX;
        sprite.setPosition(x, y);
    }

    public MovementState getMovementState() {
        return movementState;
    }


    public enum MovementState
    {
        RUNNING,
        JUMPING,
        DODGING,
        STANDING
    }





}
