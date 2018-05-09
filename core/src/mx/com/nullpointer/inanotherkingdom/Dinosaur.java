package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dinosaur extends Enemy {
    protected float VX ;
    protected Animation animationCry;
    protected boolean crying;
    public Dinosaur(Texture texture, float x, float y)
    {
        //Animación de correr
        TextureRegion region = new TextureRegion(texture);
        //Tamaño
        TextureRegion[][] characterTexture;

        characterTexture = region.split(350,360);
        animation =new Animation(0.1f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;
        animationCry =new Animation(0.1f,
                characterTexture[0][5],characterTexture[0][6],characterTexture[0][7], characterTexture[0][8],
                characterTexture[0][9],characterTexture[0][10]);
        Preferences prefs = Gdx.app.getPreferences("Settings");
        VX = prefs.getInteger("Difficulty",400)*1.2f;

        this.x= x;
        this.y=y;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(this.x,this.y);
        crying= false;
    }
    public void run(float delta)
    {
        this.x+=VX*delta;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void cry() {
        animation = animationCry;
        timerAnimation =0;
        crying = true;
    }
    public float getTimerAnimation()
    {
        return timerAnimation;
    }
    public boolean isCrying()
    {
        return crying;
    }
}
