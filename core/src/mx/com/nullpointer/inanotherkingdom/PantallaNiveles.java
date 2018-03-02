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

import mx.com.nullpointer.niveles.Nivel_Inicial;
import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.Text;

/**
 * Created by MarinaHaro on 25/02/18.
 */

class PantallaNiveles extends GenericScreen{
    private final Main game;


    //Escena para la pantalla Niveles
    private Stage escenaNiveles;


    //Texturas
    private Texture texturaFondo;
    private Text msg;
    private Texture texturaTitulo;



    public PantallaNiveles(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

    }

    private void cargarTexturas() {
        texturaFondo = new Texture("background/menubg.png");
        msg = new Text();
        texturaTitulo = new Texture("logo_alargado.png");

    }

    private void crearObjetos() {
        escenaNiveles = new Stage(view);

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
        escenaNiveles.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaNiveles);

        //Botón Primer Nivel
        TextureRegionDrawable trdFirst = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        TextureRegionDrawable trdFirstPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        ImageButton btnFirst = new ImageButton(trdFirst,trdFirstPress);
        btnFirst.setPosition(btnFirst.getWidth(),PantallaMenu.ALTO/2 - btnFirst.getHeight()/2);

        btnFirst.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Nivel_Inicial(game));

            }
        });
        escenaNiveles.addActor(btnFirst);
        Gdx.input.setInputProcessor(escenaNiveles);

        //Botón Segundo Nivel
        TextureRegionDrawable trdSec = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdSecPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnSec = new ImageButton(trdSec,trdSecPress);
        btnSec.setPosition(3*btnSec.getWidth(),PantallaMenu.ALTO/2 - btnSec.getHeight()/2);

        btnSec.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaNiveles(game));

            }
        });
        escenaNiveles.addActor(btnSec);
        Gdx.input.setInputProcessor(escenaNiveles);

        //Botón Tercer Nivel
        TextureRegionDrawable trdThird = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnThird = new ImageButton(trdThird,trdThirdPress);
        btnThird.setPosition(5*btnThird.getWidth(),PantallaMenu.ALTO/2 - btnThird.getHeight()/2);

        btnThird.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaNiveles(game));

            }
        });
        escenaNiveles.addActor(btnThird);
        Gdx.input.setInputProcessor(escenaNiveles);



    }



    @Override
    public void render(float delta) {
        clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texturaFondo,0 ,0);
        //msg.showMsg(batch,"Niveles",ANCHO/2,3*ALTO/4,2);
        //msg.showMsg(batch,"Proximamente",ANCHO/2,ALTO/2,1);
        batch.draw(texturaTitulo, ANCHO/2 - texturaTitulo.getWidth()/2, 6*ALTO/8 );
        batch.end();
        escenaNiveles.draw();


    }




}
