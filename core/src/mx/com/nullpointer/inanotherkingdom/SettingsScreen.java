package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by Carlos Carbajal on 06-feb-18.
 */

public class SettingsScreen extends GenericScreen {

    private Stage escenaAjustes;
    private Table container;
    //Texturas
    private Texture texturaFondo;
    private Slider sldSound;

    private Preferences prefsMusic = Gdx.app.getPreferences("Settings");


    public SettingsScreen(Main game)
    {
        super(game);
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


        Skin skin = new Skin(Gdx.files.internal("skin/golden-ui-skin.json"));

        escenaAjustes = new Stage(view);
        //Botón Volumen

        TextureRegionDrawable trdSound = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOn.png")));
        TextureRegionDrawable trdSoundFX = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOff.png")));
        TextureRegionDrawable trdMute = new TextureRegionDrawable(new TextureRegion(new Texture("btn/mute.png")));
        ImageButton btnVolumen;



        if(prefsMusic.getInteger("music", 0)<1){
            btnVolumen = new ImageButton(trdSound,trdSoundFX);
        }

        else{
            btnVolumen = new ImageButton(trdSoundFX,trdSound);
        }

        btnVolumen.setPosition(MenuScreen.WIDTH /2 - btnVolumen.getWidth()/2, MenuScreen.HEIGHT /2 + btnVolumen.getHeight()*1.5f);

        btnVolumen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if(prefsMusic.getInteger("music", 0)<1){
                    prefsMusic.putInteger("music", (prefsMusic.getInteger("music", 0)+1)%3);
                    game.stopMusic();
                    prefsMusic.flush();

                }
                else {
                    prefsMusic.putInteger("music", (prefsMusic.getInteger("music", 0)+1)%3);
                    game.changeMusic(SETTINGS);
                    prefsMusic.flush();
                }
                crearObjetos();

            }
        });
        escenaAjustes.addActor(btnVolumen);



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
                game.setScreen(new LoadingScreen(game, MENU));

            }
        });
        escenaAjustes.addActor(btnBack);

        sldSound = new Slider(0.0f, 1.0f, 0.5f, false, skin);
        sldSound.setBounds(WIDTH/2-sldSound.getWidth()/2,3*HEIGHT/4,WIDTH/4,200);
        escenaAjustes.addActor(sldSound);

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
