package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private Stage escenaNivel1;


    //Texturas
    private Texture texturaFondo;
    //private Text msg;
    private Texture texturaTitulo;
    private Stage currentScene;


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
        //msg = new Text();
        texturaTitulo = new Texture("logo_alargado.png");

    }

    private void crearObjetos() {
        escenaNiveles = new Stage(view);
        escenaNivel1 = new Stage(view);

        Gdx.input.setInputProcessor(escenaNiveles);

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


        //Botón Primer Nivel
        TextureRegionDrawable trdFirst = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        TextureRegionDrawable trdFirstPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        ImageButton btnFirst = new ImageButton(trdFirst,trdFirstPress);
        btnFirst.setPosition(btnFirst.getWidth(),PantallaMenu.ALTO/2 - btnFirst.getHeight()/2);

        btnFirst.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(escenaNivel1);
            }
        });
        escenaNiveles.addActor(btnFirst);



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
        currentScene = escenaNiveles;

        //Botón Subnivel 1 primer nivel
        TextureRegionDrawable trdFirstSub = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdFirstSubPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnFirstSub = new ImageButton(trdFirstSub,trdFirstSubPress);
        btnFirstSub.setPosition(btnFirstSub.getWidth(),PantallaMenu.ALTO/2 - btnFirstSub.getHeight()/2);

        btnFirstSub.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Nivel_Inicial(game));

            }
        });
        escenaNivel1.addActor(btnFirstSub);

        //Botón Subnivel 2 primer nivel
        TextureRegionDrawable trdSecSub = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdSecSubPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnSecSub = new ImageButton(trdSecSub,trdSecSubPress);
        btnSecSub.setPosition(btnSecSub.getWidth(),PantallaMenu.ALTO/2 - btnSecSub.getHeight()/2);

        btnSecSub.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        escenaNivel1.addActor(btnSecSub);

        //Botón Back Sub
        ImageButton btnBackSub = new ImageButton(trdBack,trdBackPress);
        btnBackSub.setPosition(btnBackSub.getWidth()*2,PantallaMenu.ALTO/2 - btnBackSub.getHeight()/2);

        btnBackSub.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaNiveles(game));

            }
        });
        escenaNivel1.addActor(btnBackSub);


    }

    private void changeScene(Stage scene) {
        currentScene = scene;
        Gdx.input.setInputProcessor(scene);
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
        currentScene.draw();

    }

    }





