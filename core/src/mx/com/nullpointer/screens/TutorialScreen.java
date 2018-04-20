package mx.com.nullpointer.screens;

import com.badlogic.gdx.graphics.Texture;

import mx.com.nullpointer.inanotherkingdom.Main;

/**
 * Created by MarinaHaro on 19/04/18.
 */

public class TutorialScreen extends GenericScreen {
    
    private Texture backgroundTexture;

    public TutorialScreen(Main game)
    {
        super(game);
    }

    @Override
    public void show() {
        backgroundTexture = new Texture("background/howTo.png");
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        batch.end();

    }
}
