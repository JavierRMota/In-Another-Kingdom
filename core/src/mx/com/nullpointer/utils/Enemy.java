package mx.com.nullpointer.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mota on 2/20/18.
 */
public class Enemy extends GameObject
{
    private Animation animation;
    private float timerAnimation;
    private float x,y;
    private float VX =-500;
    private int type;
    private int life = 100;

    public Enemy(Texture texture, float x, float y, int type)
    {
        //Animación de correr
        TextureRegion region = new TextureRegion(texture);
        this.type = type;
        //Tamaño
        TextureRegion[][] characterTexture;
        switch (type)
        {
            case 1:

            default:
                characterTexture = region.split(399,412);
                animation = new Animation(0.1f,
                        characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                        characterTexture[0][4],characterTexture[0][3],characterTexture[0][2],characterTexture[0][1]);


        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;

        this.x= x;
        this.y=y;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(this.x,this.y);
    }
    public Enemy(Texture texture)
    {
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
    public void render(SpriteBatch batch, float delta)
    {
        timerAnimation+=delta;
        TextureRegion region = (TextureRegion) animation.getKeyFrame(timerAnimation);
        batch.draw(region,x,y);
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
    public float getX() {
        return x;
    }
    public boolean isDead()
    {
        return life < 1;
    }
    public void receiveDamage(int damage)
    {
        life-=damage;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
