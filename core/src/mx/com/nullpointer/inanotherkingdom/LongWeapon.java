package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.com.nullpointer.levels.GameState;


public class LongWeapon extends GameObject{
    private Animation animation;
    public boolean isBad;
    private float x,y;
    private float VX =-600;
    private float VY = -220;
    private boolean fight;
    public LongWeapon(Texture bad, Texture good, float x, float y ,float maxX, boolean fight)
    {
        animation = new Animation(1f, bad, good);
        sprite = new Sprite(bad);
        isBad = true;
        this.fight = fight;
        if(x<maxX)
            this.x=x;
        else
            this.x =maxX;
        if(fight)
            this.y = 300;
        else
            this.y = y;
        sprite.setPosition(this.x,y);
        Preferences prefs = Gdx.app.getPreferences("Settings");
        switch (prefs.getInteger("Difficulty",400))
        {
            case 500:
                VX*=1.3;
                VY*=1.3;
                break;
        }
    }
    public void changeDirection()
    {
        VX=-VX;
        isBad =!isBad;
        VY=-VY;
    }
    public void render(SpriteBatch batch, GameState state)
    {
        Texture region;
        if(state == GameState.PLAY)
        {
            x+= VX* Gdx.graphics.getDeltaTime();
            if(fight)
                y+= VY*Gdx.graphics.getDeltaTime();
        }
        if(isBad)
            region = (Texture) animation.getKeyFrame(0);
        else
            region = (Texture) animation.getKeyFrame(1);
        batch.draw(region, x,y);
        sprite.setPosition(x,y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
