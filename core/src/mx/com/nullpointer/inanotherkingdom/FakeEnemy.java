package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.com.nullpointer.screens.GenericScreen;

public class FakeEnemy extends Enemy {
    private float VX =-500;
    public FakeEnemy(Texture texture) {
        //Animación de correr
        TextureRegion region = new TextureRegion(texture);
        //Tamaño
        TextureRegion[][] characterTexture = region.split(399,412);
        animation = new Animation(0.1f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][3],characterTexture[0][2],characterTexture[0][1]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;
        x= GenericScreen.WIDTH;
        y=300;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(x,y);

    }
    public void moveEnemy(float delta, SpriteBatch batch)
    {
        x+=delta*VX;
        timerAnimation+=delta;
        TextureRegion region = (TextureRegion) animation.getKeyFrame(timerAnimation);
        if(x<-1000||x>2000)
        {
            VX=-VX;
        }
        if(VX>0)
            batch.draw(region,x,y,-region.getRegionWidth(),region.getRegionHeight());
        else
            batch.draw(region, x, y);
        sprite.setPosition(x,y);
    }
}
