package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.Text;

/**
 * Created by MarinaHaro on 12/02/18.
 */

class AboutScreen extends GenericScreen {

    //Escena para la pantalla Acercade
    private Stage aboutStage;


    //Texturas
    private Texture backgroundTexture;
    private Texture bannerTexture;
    private Texture bere, charly, eli, javier, marina;
    private Text msg;


    public AboutScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        loadTextures();
        createObjects();

    }

    private void loadTextures() {
        backgroundTexture = assetManager.get("background/menubg.png");
        bannerTexture = assetManager.get("screens/avatarBanner.png");
        bere = assetManager.get("screens/avatarBere.png");
        charly = assetManager.get("screens/avatarCharly.png");
        eli = assetManager.get("screens/avatarEli.png");
        javier = assetManager.get("screens/avatarJavier.png");
        marina = assetManager.get("screens/avatarMarina.png");
        msg = new Text();

    }
}
