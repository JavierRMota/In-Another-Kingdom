package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Main extends Game {
	
	@Override
	public void create () {
		//Pantalla de menú inicial
		Preferences prefs = Gdx.app.getPreferences("Progress");
		int lastLevel = prefs.getInteger("lastLevel", 0);
		if(lastLevel ==0)
			prefs.putInteger("lastLevel", 1);

		setScreen(new PantallaMenu(this));

	}

}
