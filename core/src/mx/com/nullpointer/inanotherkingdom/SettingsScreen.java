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

    private Preferences preferencesTotal = Gdx.app.getPreferences("Settings");

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

        if(preferencesTotal.getBoolean("music", true)){
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
                if(preferencesTotal.getBoolean("music", true)){
                    preferencesTotal.putBoolean("music", false);
                    game.stopMusic();
                    preferencesTotal.flush();

                }
                else {
                    preferencesTotal.putBoolean("music", true);
                    game.changeMusic(SETTINGS);
                    preferencesTotal.flush();
                }
                Gdx.app.log("prefs: ","Bool: " + preferencesTotal.getBoolean("music") );
                crearObjetos();

            }
        });
        escenaAjustes.addActor(btnVolumen);

        //Slider Dificultad
        sldDiff = new Slider(300, 500, 100, false, skin);
        sldDiff.setBounds(WIDTH/2-9*sldDiff.getWidth()/8, 2*HEIGHT/5,WIDTH/4,200);
        sldDiff.setValue(preferencesTotal.getInteger("Difficulty", 400));
        escenaAjustes.addActor(sldDiff);
        // Slider listener
        sldDiff.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Valor;","slider changed to: " + sldDiff.getValue());
                if(sldDiff.getValue()==300f) {
                    preferencesTotal.putInteger("Difficulty", 300);
                    preferencesTotal.flush();
                }
                else if(sldDiff.getValue()==500f) {
                    preferencesTotal.putInteger("Difficulty", 500);
                    preferencesTotal.flush();
                }
                else{
                    preferencesTotal.putInteger("Difficulty", 400);
                    preferencesTotal.flush();
                }

                Gdx.app.log("Valor;","diff: " + preferencesTotal.getInteger("Difficulty"));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };

        });

        //Boton modo
        Texture slideTexture = assetManager.get("btn/slider.png");
        Texture buttonTexture = assetManager.get("btn/button.png");
        TextureRegionDrawable trdSlide= new TextureRegionDrawable(new TextureRegion(slideTexture));
        TextureRegionDrawable trdButton = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        ImageButton btnMode;

        if(preferencesTotal.getBoolean("mode", true)){
            btnMode = new ImageButton(trdButton,trdSlide);

        }
        else{
            btnMode = new ImageButton(trdSlide,trdButton);
        }

        btnMode.setPosition(WIDTH /2 - btnMode.getWidth()/2, 2.5f*HEIGHT /8);

        btnMode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);

                preferencesTotal.putBoolean("mode", !preferencesTotal.getBoolean("mode", true));
                preferencesTotal.flush();
                Gdx.app.log("prefs: ","Bool: " + preferencesTotal.getBoolean("mode") );
                crearObjetos();

            }
        });
        escenaAjustes.addActor(btnMode);

        //Boton elección lado
        if(preferencesTotal.getBoolean("mode",true)){
            Texture possitionTexture = assetManager.get("btn/possitionLeft.png");
            Texture possitionRTexture = assetManager.get("btn/possitionRight.png");
            TextureRegionDrawable trdLeft= new TextureRegionDrawable(new TextureRegion(possitionTexture));
            TextureRegionDrawable trdRight = new TextureRegionDrawable(new TextureRegion(possitionRTexture));
            ImageButton btnPossition;

            if(preferencesTotal.getBoolean("position", true)){
                btnPossition = new ImageButton(trdLeft,trdRight);

            }

            else{
                btnPossition = new ImageButton(trdRight,trdLeft);
            }

            btnPossition.setPosition(WIDTH /2 - btnPossition.getWidth()/2, 1.5f*HEIGHT /8);

            btnPossition.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    super.clicked(event, x, y);
                    preferencesTotal.putBoolean("position", !preferencesTotal.getBoolean("position", true));
                    preferencesTotal.flush();
                    Gdx.app.log("prefs: ","Bool: " + preferencesTotal.getBoolean("position") );
                    crearObjetos();

                }
            });
            escenaAjustes.addActor(btnPossition);
        }



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
        if(preferencesTotal.getBoolean("mode", true)) {
            msg.showMsg(batch, "Button position: ", WIDTH / 2, 2 * HEIGHT / 8, 1, 'c');
        }
        //batch.draw(imgBtn,WIDTH/6, 3*HEIGHT/8);
        //batch.draw(imgSld,4*WIDTH/6, 3*HEIGHT/8);
        batch.end();
        escenaAjustes.draw();

    }

    @Override
    public void dispose() {
        assetManager.unload("skin/golden-ui-skin.json");
        assetManager.unload("tutorial/attack1.png");
        assetManager.unload("tutorial/attack2.png");

    }
}
