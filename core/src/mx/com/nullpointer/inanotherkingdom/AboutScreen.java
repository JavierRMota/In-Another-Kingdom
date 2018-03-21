package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
    private final Main game;
    private final AssetManager assetManager;


    //Escena para la pantalla Acercade
    private Stage aboutStage;


    //Texturas
    private Texture backgroundTexture;
    private Text msg;


    public AboutScreen(Main game)
    {
        this.game = game;
        this.assetManager = this.game.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

    }

    private void cargarTexturas() {
        backgroundTexture = new Texture("background/menubg.png");
        msg = new Text();

    }

    private void crearObjetos() {
        aboutStage = new Stage(view);

        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()*2, MenuScreen.HEIGHT /2 - btnBack.getHeight()/2);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MenuScreen(game));

            }
        });
        aboutStage.addActor(btnBack);
        Gdx.input.setInputProcessor(aboutStage);

    }


    @Override
    public void render(float delta) {
        clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        msg.showMsg(batch,"Informacion", WIDTH /2,3* HEIGHT /4,2);
        msg.showMsg(batch,"Proximamente", WIDTH /2, HEIGHT /2,1);
        batch.end();
        aboutStage.draw();


    }



}
