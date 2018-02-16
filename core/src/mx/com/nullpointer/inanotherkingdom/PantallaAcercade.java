package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by MarinaHaro on 12/02/18.
 */

class PantallaAcercade extends GenericScreen {
    private final Main game;


    //Escena para la pantalla Acercade
    private Stage escenaAcercade;


    //Texturas
    private Texture texturaFondo;



    public PantallaAcercade(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

    }

    private void cargarTexturas() {
        texturaFondo = new Texture("background/menubg.png");

    }

    private void crearObjetos() {
        escenaAcercade = new Stage(view);

        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()/2,PantallaMenu.ALTO - btnBack.getHeight());

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));

            }
        });
        escenaAcercade.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaAcercade);

    }

    BitmapFont font = new BitmapFont();

    @Override
    public void render(float delta) {
        clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texturaFondo,0 ,0);
        font.draw(batch, "Esta es la pantalla de información", ANCHO/2, ALTO/2);
        batch.end();
        escenaAcercade.draw();


    }



}
