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
    private Animation animation;
    private float timerAnimation;
    private float x,y; //Coordenadas de dónde se moverá
    //Tamaño frames: 13.97cm * 8.29cm

    //Estados de movimiento
    MovementState movementState = MovementState.RUNNING;
    private static final float VX = 240;
    public MainCharacter(Texture texture)
    {
        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] characterTexture = region.split(396,235);
        animation = new Animation(0.15f,characterTexture[0][0],characterTexture[0][1],characterTexture[0][2]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation =0;
        // Quieto
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(0,64);
        x = 0;
        y = 64;
    }
    public void render(SpriteBatch batch) {
        if (movementState==MovementState.STANDING) {
            //batch.draw(marioQuieto.getTexture(),x,y);
            sprite.draw(batch);
        } else {
            timerAnimation += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) animation.getKeyFrame(timerAnimation);
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
    public void move(float dx) {
        this.x += dx;
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
