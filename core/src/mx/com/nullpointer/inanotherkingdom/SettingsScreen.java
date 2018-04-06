package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.Text;

/**
 * Created by Carlos Carbajal on 06-feb-18.
 */

public class SettingsScreen extends GenericScreen {

    private Stage escenaAjustes;
    //Texturas
    private Texture texturaFondo,imgBtn, imgSld;
    private Slider sldDiff;
    private Slider sldAttack;

    private Preferences prefsMusic = Gdx.app.getPreferences("Settings");
    private Preferences prefsDiff = Gdx.app.getPreferences("Settings");
    private Preferences prefsAttack = Gdx.app.getPreferences("Settings");

    private Text msg;

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
        imgBtn = assetManager.get("tutorial/attack1.png");
        imgSld = assetManager.get("tutorial/attack2.png");
        texturaFondo = new Texture("background/menubg.png");
        msg = new Text();

    }

    private void crearObjetos() {


        Skin skin = new Skin(Gdx.files.internal("skin/golden-ui-skin.json"));

        escenaAjustes = new Stage(view);
        //Botón Volumen

        TextureRegionDrawable trdSound = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOn.png")));
        TextureRegionDrawable trdSoundFX = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOff.png")));
        ImageButton btnVolumen;

        if(prefsMusic.getBoolean("music", true)){
            btnVolumen = new ImageButton(trdSound,trdSoundFX);
        }

        else{
            btnVolumen = new ImageButton(trdSoundFX,trdSound);
        }

        btnVolumen.setPosition(MenuScreen.WIDTH /2 - btnVolumen.getWidth()/2, 7*HEIGHT /10);

        btnVolumen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if(prefsMusic.getBoolean("music", true)){
                    prefsMusic.putBoolean("music", false);
                    game.stopMusic();
                    prefsMusic.flush();

                }
                else {
                    prefsMusic.putBoolean("music", true);
                    game.changeMusic(SETTINGS);
                    prefsMusic.flush();
                }
                Gdx.app.log("prefs: ","Bool: " + prefsMusic.getBoolean("music") );
                crearObjetos();

            }
        });
        escenaAjustes.addActor(btnVolumen);

        //Slider Dificultad
        sldDiff = new Slider(300, 500, 100, false, skin);
        sldDiff.setBounds(WIDTH/2-9*sldDiff.getWidth()/8, 2*HEIGHT/5,WIDTH/4,200);
        sldDiff.setValue(prefsMusic.getInteger("Difficulty", 400));
        escenaAjustes.addActor(sldDiff);
        // Slider listener
        sldDiff.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Valor;","slider changed to: " + sldDiff.getValue());
                if(sldDiff.getValue()==300f) {
                    prefsDiff.putInteger("Difficulty", 300);
                    prefsDiff.flush();
                }
                else if(sldDiff.getValue()==500f) {
                    prefsDiff.putInteger("Difficulty", 500);
                    prefsDiff.flush();
                }
                else{
                    prefsDiff.putInteger("Difficulty", 400);
                    prefsDiff.flush();
                }

                Gdx.app.log("Valor;","diff: " + prefsDiff.getInteger("Difficulty"));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };

        });

        //Slider Ataque

        sldAttack = new Slider(0,1,1, false, skin);
        sldAttack.setBounds(WIDTH/2-9*sldAttack.getWidth()/8, HEIGHT/5,WIDTH/4,200);
        sldAttack.setValue(prefsAttack.getFloat("Mode", 0));
        escenaAjustes.addActor(sldAttack);
        sldAttack.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Valor;","slider changed to: " + sldAttack.getValue());
                if(sldAttack.getValue()==0f) {
                    prefsAttack.putInteger("Mode", 0);
                    prefsAttack.flush();
                }
                else{
                    prefsAttack.putInteger("Mode", 1);
                    prefsAttack.flush();
                }

                Gdx.app.log("Valor;","diff: " + prefsAttack.getInteger("Mode"));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };

        });


        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth(), HEIGHT /2 - btnBack.getHeight()/2);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game, MENU));

            }
        });
        escenaAjustes.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaAjustes);

    }



    @Override
    public void render(float delta) {
        clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texturaFondo,0 ,0);
        msg.showMsg(batch,"Settings", WIDTH /2,HEIGHT-25,2,'c');
        msg.showMsg(batch,"Difficulty: ",WIDTH/2,5.2f*HEIGHT/8,1,'c');
        msg.showMsg(batch,"Attack Mode: ",WIDTH/2,3.6f*HEIGHT/8,1,'c');
        batch.draw(imgBtn,WIDTH/6, 3*HEIGHT/8);
        batch.draw(imgSld,4*WIDTH/6, 3*HEIGHT/8);
        batch.end();
        escenaAjustes.draw();

    }

    @Override
    public void dispose() {
        assetManager.unload("skin/golden-ui-skin.json");
        assetManager.unload("btn/musicOn.png");
        assetManager.unload("btn/musicOff.png");
        assetManager.unload("tutorial/attack1.png");
        assetManager.unload("tutorial/attack2.png");

    }
}
