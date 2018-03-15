package mx.com.nullpointer.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.com.nullpointer.inanotherkingdom.PantallaMenu;

/**
 * Created by mota on 2/20/18.
 */
public class Enemy extends Objeto
{
    private Animation animation;
    private float timerAnimation;
    private float x,y;
    private float VX =200;
    //Frame 154*131
    public Enemy(Texture texture)
    {
        //Animación de correr
        TextureRegion region = new TextureRegion(texture);
        //Tamaño
        TextureRegion[][] characterTexture = region.split(399,412);
        animation = new Animation(0.2f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][3],characterTexture[0][2],characterTexture[0][1]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;
        x= GenericScreen.ANCHO;
        y=300;
    }
    public void moveEnemy(float delta, SpriteBatch batch)
    {
        x-=delta*VX;
        if(x<-500)
            x= 2*GenericScreen.ANCHO;
        timerAnimation+=delta;
        TextureRegion region = (TextureRegion) animation.getKeyFrame(timerAnimation);
        batch.draw(region, x, y);

    }
}
