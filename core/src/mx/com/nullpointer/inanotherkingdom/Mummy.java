package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mummy extends Enemy {
    public Mummy(Texture texture, float x, float y)
    {
        //Region
        TextureRegion region = new TextureRegion(texture);
        //Size
        TextureRegion[][] characterTexture;
        characterTexture = region.split(88,133);
        //Animation
        animation = new Animation(0.05f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;

        this.x= x;
        this.y=y;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(this.x,this.y);
    }
}
