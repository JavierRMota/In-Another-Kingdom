package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class LongWeapon extends GameObject{
    private Animation animation;
    public boolean isBad;
    private float x,y;
    private Preferences prefs = Gdx.app.getPreferences("Settings");
    private float VX =-600*prefs.getInteger("Difficulty",400)/400;
    private float VY = -220*prefs.getInteger("Difficulty",400)/400;
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
    }
    public void changeDirection()
    {
        VX=-VX;
        isBad =!isBad;
        VY=-VY;
    }
    public void render(SpriteBatch batch)
    {
        Texture region;
        x+= VX* Gdx.graphics.getDeltaTime();
        if(fight)
            y+= VY*Gdx.graphics.getDeltaTime();
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
