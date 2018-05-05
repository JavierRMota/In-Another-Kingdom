package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dinosaur extends Enemy {
    protected float VX ;
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
        Preferences prefs = Gdx.app.getPreferences("Settings");
        VX = prefs.getInteger("Difficulty",400)*1.2f;

        this.x= x;
        this.y=y;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(this.x,this.y);
    }
    public void run(float delta)
    {
        this.x+=VX*delta;
    }
}
