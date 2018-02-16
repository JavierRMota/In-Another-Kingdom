package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

class PantallaArsenal extends GenericScreen {

    private final Main game;

    //Texturas
    private Texture texturaFondo;

    private Stage escenaArsenal;


    public PantallaArsenal(Main game) {this.game = game;}

    @Override
    public void show() {
        cargarTexturas();
        crearArsenal();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("background/menubg.png");

    }

    private void crearArsenal() {
        escenaArsenal = new Stage(view);

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
        escenaArsenal.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaArsenal);
    }


    BitmapFont font= new BitmapFont();
    @Override
    public void render(float delta) {
        clearScreen(25/255f,158/255f,218/255f);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texturaFondo,0 ,0);
        font.draw(batch, "Esta es la pantalla dónde se personaliza a Laurence", ANCHO/2, ALTO/2);
        batch.end();
        escenaArsenal.draw();
    }

}
