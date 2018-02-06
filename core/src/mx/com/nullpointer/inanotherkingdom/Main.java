package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*Hola Charly*/

public class Main extends Game {
	
	@Override
	public void create () {
		//Pantalla de menú inicial
		setScreen(new PantallaMenu(this));

	}

}
