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
    //Tamaño frames: 13.97cm * 8.29cm

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
        characterTexture = region.split(396,228);
        jumpingAnimation = new Animation(0.05f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        timerRunning =0;
        timerJumping =0;
        // Quieto
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(0,64);
        x = 0;
        y = 64;
    }
    public void render(SpriteBatch batch) {
        if (movementState==MovementState.STANDING)
        {
            //batch.draw(marioQuieto.getTexture(),x,y);
            this.draw(batch);
        }
        else if(movementState == MovementState.RUNNING)
        {
            timerRunning += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) runningAnimation.getKeyFrame(timerRunning);
            batch.draw(region, x, y);
        }
        else if (movementState == MovementState.JUMPING)
        {
            timerJumping+=Gdx.graphics.getDeltaTime();
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
    public void setMovementState(MovementState movment)
    {
        this.movementState = movment;
    }
    public void setX(float x)
    {
        this.x=x;
    }
    public void setY(float y)
    {
        this.y =y;
    }
    public void move(float dx,float dy) {
        this.x += dx;
        this.y +=dy;
        sprite.setPosition(x, y);
    }


    public enum MovementState
    {
        RUNNING,
        JUMPING,
        DODGING,
        STANDING
    }





}
