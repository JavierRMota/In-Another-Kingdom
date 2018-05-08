package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dragon extends Enemy {
    protected int life = 100;
    protected  Animation damageAnimation;
    protected boolean damageR=false;
    public Dragon(Texture texture, float x, float y)
    {
        //Animación de correr
        TextureRegion region = new TextureRegion(texture);
        //Tamaño
        TextureRegion[][] characterTexture;

        characterTexture = region.split(399,412);
        animation = new Animation(0.1f,
                        characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                        characterTexture[0][4],characterTexture[0][3],characterTexture[0][2],characterTexture[0][1]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;
        damageAnimation = new Animation(0.1f,characterTexture[0][5], characterTexture[0][0]);
        damageAnimation.setPlayMode(Animation.PlayMode.LOOP);

        this.x= x;
        this.y=y;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(this.x,this.y);
    }
    public boolean isDead()
    {
        return life < 1;
    }
    public void receiveDamage(int damage)
    {
        life-=damage;
        this.damageR= true;
        timerAnimation=0;
    }
    @Override
    public void render(SpriteBatch batch, float delta)
    {
        timerAnimation+=delta;
        TextureRegion region;
        if(damageR)
        {
            region = (TextureRegion) damageAnimation.getKeyFrame(timerAnimation);
            if(timerAnimation>0.4f)
            {
                timerAnimation=0;
                damageR=false;
            }
        }else {
            region = (TextureRegion) animation.getKeyFrame(timerAnimation);
        }

        batch.draw(region,x,y);
        sprite.setPosition(x,y);
    }

}
