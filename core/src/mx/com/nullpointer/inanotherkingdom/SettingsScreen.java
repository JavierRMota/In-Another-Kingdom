package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by Carlos Carbajal on 06-feb-18.
 */

public class SettingsScreen extends GenericScreen {
    private final Main game;
    private final AssetManager assetManager;

    private Stage escenaAjustes;

    //Texturas
    private Texture texturaFondo;

    private Preferences prefsMusic = Gdx.app.getPreferences("Musica");


    public SettingsScreen(Main game)
    {
        this.game = game;
        this.assetManager= this.game.getAssetManager();
    }

    @Override
    public void show() {
        crearObjetos();
        cargarTexturas();

    }

    private void cargarTexturas() {

        texturaFondo = new Texture("background/menubg.png");

    }

    private void crearObjetos() {
        escenaAjustes = new Stage(view);
        //Botón Volumen

        TextureRegionDrawable trdVolumen = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOn.png")));
        TextureRegionDrawable trdVolumenPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOff.png")));
        ImageButton btnVolumen;


        if(prefsMusic.getBoolean("play", true)){
            btnVolumen = new ImageButton(trdVolumen,trdVolumenPress);
        }

        else{
            btnVolumen = new ImageButton(trdVolumenPress,trdVolumen);
        }

        btnVolumen.setPosition(MenuScreen.WIDTH /2 - btnVolumen.getWidth()/2, MenuScreen.HEIGHT /2 + btnVolumen.getHeight()*1.5f);

        btnVolumen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if(prefsMusic.getBoolean("play", true)){
                    prefsMusic.putBoolean("play", false);
                    game.stopMusic();
                    prefsMusic.flush();
                    Gdx.app.log("bool: ","false");

                }
                else {
                    prefsMusic.putBoolean("play", true);
                    game.changeMusic(SETTINGS);
                    prefsMusic.flush();
                    Gdx.app.log("bool: ","true");
                }
                crearObjetos();

            }
        });
        escenaAjustes.addActor(btnVolumen);

        //Botón Rate
        TextureRegionDrawable trdRate = new TextureRegionDrawable(new TextureRegion(new Texture("btn/rate.png")));
        TextureRegionDrawable trdRatePress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/rate.png")));
        ImageButton btnRate = new ImageButton(trdRate,trdRatePress);
        btnRate.setPosition(MenuScreen.WIDTH /2 - btnRate.getWidth()/2, MenuScreen.HEIGHT /2);

        btnRate.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);

            }
        });
        escenaAjustes.addActor(btnRate);

        //Botón SFX
        TextureRegionDrawable trdSfx = new TextureRegionDrawable(new TextureRegion(new Texture("btn/sfxxOn.png")));
        TextureRegionDrawable trdSfxPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/sfxOff.png")));
        ImageButton btnSfx = new ImageButton(trdSfx,trdSfxPress);
        btnSfx.setPosition(MenuScreen.WIDTH /2 - btnSfx.getWidth()/2, MenuScreen.HEIGHT /2 -btnSfx.getHeight()*1.5f);

        btnSfx.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);

            }
        });
        escenaAjustes.addActor(btnSfx);

        Gdx.input.setInputProcessor(escenaAjustes);
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
        escenaAjustes.addActor(btnBack);

    }



    @Override
    public void render(float delta) {
        clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texturaFondo,0 ,0);
        batch.end();
        escenaAjustes.draw();

    }


    
}
