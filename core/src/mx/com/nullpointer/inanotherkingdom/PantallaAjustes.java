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

/**
 * Created by Carlos Carbajal on 06-feb-18.
 */

class PantallaAjustes extends GenericScreen {
    private final Main game;



    private Stage escenaAjustes;


    public PantallaAjustes(Main game) {this.game = game;}

    @Override
    public void show() {
        crearAjustes();

        
    }

    private void crearAjustes() {
        escenaAjustes = new Stage(view);
        //Botón Volumen
        TextureRegionDrawable trdVolumen = new TextureRegionDrawable(new TextureRegion(new Texture("vup.png")));
        TextureRegionDrawable trdVolumenPress = new TextureRegionDrawable(new TextureRegion(new Texture("vdown.png")));
        ImageButton btnVolumen = new ImageButton(trdVolumen,trdVolumenPress);
        btnVolumen.setPosition(PantallaMenu.ANCHO - btnVolumen.getWidth(), PantallaMenu.ALTO - btnVolumen.getHeight());

        btnVolumen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);

            }
        });
        escenaAjustes.addActor(btnVolumen);
        Gdx.input.setInputProcessor(escenaAjustes);

        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("back.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("backPress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()/2, PantallaMenu.ALTO - btnBack.getHeight());

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));

            }
        });
        escenaAjustes.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaAjustes);
    }



    @Override
    public void render(float delta) {
       clearScreen(25/255f,158/255f,218/255f);

        batch.setProjectionMatrix(camera.combined);
        escenaAjustes.draw();
    }


    
}
