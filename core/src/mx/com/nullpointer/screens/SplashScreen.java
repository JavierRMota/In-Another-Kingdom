package mx.com.nullpointer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.utils.Text;

/**
 * Created by Carlos Carbajal on 06-feb-18.
 */

public class SplashScreen extends GenericScreen {

    //Texturas
    private Texture backgroundTexture;
    private Texture logo;
    private float timer;


    public SplashScreen(Main game)
    {
       super(game);
    }

    @Override
    public void show() {
        cargarTexturas();
    }

    private void cargarTexturas() {
        backgroundTexture = new Texture("background/menubg.png");
        logo = new Texture("screens/splashNull.png");


    }


    @Override
    public void render(float delta) {
        clearScreen(25/255f,158/255f,218/255f);
        timer+=delta;
        if(timer>2)
        {
            this.game.setScreen(new LoadingScreen(game, MENU));
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        batch.draw(logo,WIDTH/2-logo.getWidth()/2,HEIGHT/2-logo.getHeight()/2);
        batch.end();
    }

}
