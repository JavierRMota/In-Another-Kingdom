package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Main extends Game {
    SpriteBatch batch;
    Texture img;
	
	@Override
	public void create () {
		setScreen(new PantallaMenu(this));

	}
}
