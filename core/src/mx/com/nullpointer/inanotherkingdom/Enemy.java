package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.com.nullpointer.screens.GenericScreen;

/**
 * Created by mota on 2/20/18.
 */
public class Enemy extends GameObject
{
    protected Animation animation;
    protected float timerAnimation;
    protected float x,y;

    public void render(SpriteBatch batch, float delta)
    {
        timerAnimation+=delta;
        TextureRegion region = (TextureRegion) animation.getKeyFrame(timerAnimation);
        batch.draw(region,x,y);
        sprite.setPosition(x,y);
    }

    public float getX() {
        return x;
    }
    public float getY()
    {
        return y;
    }


    public Sprite getSprite() {
        return sprite;
    }
}
