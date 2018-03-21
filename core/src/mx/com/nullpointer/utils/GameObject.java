package mx.com.nullpointer.utils;

/**
 * Created by mota on 2/13/18.
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Elemento gráfico
 */


public class GameObject
{
    protected Sprite sprite;    // Imagen

    public GameObject(Texture texture, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
    }

    public GameObject() {

    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
