package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Scorpion extends Enemy{
    protected int life = 200;
    protected Animation attackAnimation;
    protected boolean attack = false;
    public Scorpion(Texture texture, float x, float y)
    {
        //Animación de correr
        TextureRegion region = new TextureRegion(texture);
        //Tamaño
        TextureRegion[][] characterTexture;

        characterTexture = region.split(572,412);
        animation = new Animation(0.1f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][2],characterTexture[0][1]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        attackAnimation = new Animation(0.1f,
                characterTexture[0][4],characterTexture[1][0], characterTexture[1][1],characterTexture[1][2],characterTexture[1][3],characterTexture[1][2],characterTexture[1][1], characterTexture[1][0] );
        attackAnimation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;

        this.x= x;
        this.y=y;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(this.x,this.y);
    }
    public boolean isAttacking()
    {
        return attack;
    }
    public boolean isDead()
    {
        return life < 1;
    }
    public void receiveDamage(int damage)
    {
        life-=damage;
        attack=false;
    }

    public void attack() {
        attack=true;
        timerAnimation=0;

    }
    @Override
    public void render(SpriteBatch batch, float delta)
    {
        timerAnimation+=delta;
        TextureRegion region;
        if(attack)
        {
            region = (TextureRegion) attackAnimation.getKeyFrame(timerAnimation);
            if(timerAnimation>0.1f*8)
            {
                attack = false;
            }

        }
        else {
                region = (TextureRegion) animation.getKeyFrame(timerAnimation);
            }
        batch.draw(region,x,y);
        sprite.setPosition(x,y);
    }

    public float getTimer() {
        return timerAnimation;
    }
}
