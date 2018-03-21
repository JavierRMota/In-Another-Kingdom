package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.MusicController;

/**
 * Created by Carlos Carbajal on 06-feb-18.
 */

public class PantallaAjustes extends GenericScreen {
    private final Main game;

    private Stage escenaAjustes;

    //Texturas
    private Texture texturaFondo;

    private Preferences prefsMusic = Gdx.app.getPreferences("Musica");


    public PantallaAjustes(Main game) {this.game = game;}

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

        TextureRegionDrawable trdSonido = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOn.png")));
        TextureRegionDrawable trdSonidoFx = new TextureRegionDrawable(new TextureRegion(new Texture("btn/musicOff.png")));
        ImageButton btnVolumen;


        if(prefsMusic.getBoolean("play", true)){
            btnVolumen = new ImageButton(trdSonido,trdSonidoFx);
        }
        else{
            btnVolumen = new ImageButton(trdSonidoFx,trdSonido);
        }

        btnVolumen.setPosition(PantallaMenu.ANCHO /2 - btnVolumen.getWidth()/2, PantallaMenu.ALTO /2 + btnVolumen.getHeight()*1.5f);

        btnVolumen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if(prefsMusic.getBoolean("play", true)){
                    prefsMusic.putBoolean("play", false);
                    MusicController.stopMusic();
                    prefsMusic.flush();
                    Gdx.app.log("bool: ","false");

                }
                else {
                    prefsMusic.putBoolean("play", true);
                    MusicController.playMusic(new MusicController("music/loop.mp3"));
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
        btnRate.setPosition(PantallaMenu.ANCHO /2 - btnRate.getWidth()/2, PantallaMenu.ALTO /2);

        btnRate.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                Gdx.net.openURI("http://nullpointer.com.mx");
            }
        });
        escenaAjustes.addActor(btnRate);


        Gdx.input.setInputProcessor(escenaAjustes);
        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()*2,PantallaMenu.ALTO/2 - btnBack.getHeight()/2);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));

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
