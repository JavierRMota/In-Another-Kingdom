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
    private Stage escenaNivel2;
    private Stage escenaNivel3;
    private Stage currentScene;

    //Texturas
    private Texture texturaFondo;
    //private Text msg;
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
        //msg = new Text();
        texturaTitulo = new Texture("logo_alargado.png");

    }

    private void crearObjetos() {
        escenaNiveles = new Stage(view);
        escenaNivel1 = new Stage(view);
        escenaNivel2 = new Stage(view);
        escenaNivel3 = new Stage(view);

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
        TextureRegionDrawable trdSecPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        ImageButton btnSec = new ImageButton(trdSec,trdSecPress);
        btnSec.setPosition(3*btnSec.getWidth(),PantallaMenu.ALTO/2 - btnSec.getHeight()/2);

        btnSec.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(escenaNivel2);

            }
        });
        escenaNiveles.addActor(btnSec);


        //Botón Tercer Nivel
        TextureRegionDrawable trdThird = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        ImageButton btnThird = new ImageButton(trdThird,trdThirdPress);
        btnThird.setPosition(5*btnThird.getWidth(),PantallaMenu.ALTO/2 - btnThird.getHeight()/2);

        btnThird.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(escenaNivel3);
                //game.setScreen(new PantallaNiveles(game));

            }
        });
        escenaNiveles.addActor(btnThird);
        currentScene = escenaNiveles;


        //Objetos sibniveles primer nivel

        //Boton back subnivel 1
        //Botón Back
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


        //Botón Subnivel 1 primer nivel
        TextureRegionDrawable trdFirstSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdFirstSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        ImageButton btnFirstSub1 = new ImageButton(trdFirstSub1,trdFirstSub1Press);
        btnFirstSub1.setPosition(btnFirstSub1.getWidth(),PantallaMenu.ALTO/2 - btnFirstSub1.getHeight()/2);

        btnFirstSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new Nivel_Inicial(game));

            }
        });
        escenaNivel1.addActor(btnFirstSub1);

        //Botón Subnivel 2 primer nivel
        TextureRegionDrawable trdSecSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdSecSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnSecSub1 = new ImageButton(trdSecSub1,trdSecSub1Press);
        btnSecSub1.setPosition(3*btnSecSub1.getWidth(),PantallaMenu.ALTO/2 - btnSecSub1.getHeight()/2);

        btnSecSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        escenaNivel1.addActor(btnSecSub1);

        //Botón Subnivel 3 primer nivel
        TextureRegionDrawable trdThirdSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnThirdSub1 = new ImageButton(trdThirdSub1,trdThirdSub1Press);
        btnThirdSub1.setPosition(5*btnThirdSub1.getWidth(),PantallaMenu.ALTO/2 - btnThirdSub1.getHeight()/2);

        btnThirdSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        escenaNivel1.addActor(btnThirdSub1);


        //Objetos subniveles segundo nivel

        //Boton back
        ImageButton btnBackSub2 = new ImageButton(trdBack,trdBackPress);
        btnBackSub2.setPosition(btnBackSub2.getWidth()*2,PantallaMenu.ALTO/2 - btnBackSub2.getHeight()/2);

        btnBackSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaNiveles(game));

            }
        });
        escenaNivel2.addActor(btnBackSub2);

        //Botón Subnivel 1 segundo nivel
        TextureRegionDrawable trdFirstSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdFirstSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnFirstSub2 = new ImageButton(trdFirstSub2,trdFirstSub2Press);
        btnFirstSub2.setPosition(btnFirstSub2.getWidth(),PantallaMenu.ALTO/2 - btnFirstSub2.getHeight()/2);

        btnFirstSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        escenaNivel2.addActor(btnFirstSub2);

        //Botón Subnivel 2 segundo nivel
        TextureRegionDrawable trdSecSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdSecSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnSecSub2 = new ImageButton(trdSecSub2,trdSecSub2Press);
        btnSecSub2.setPosition(3*btnSecSub2.getWidth(),PantallaMenu.ALTO/2 - btnSecSub2.getHeight()/2);

        btnSecSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        escenaNivel2.addActor(btnSecSub2);

        //Botón Subnivel 3 segundo nivel
        TextureRegionDrawable trdThirdSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnThirdSub2 = new ImageButton(trdThirdSub2,trdThirdSub2Press);
        btnThirdSub2.setPosition(5*btnThirdSub2.getWidth(),PantallaMenu.ALTO/2 - btnThirdSub2.getHeight()/2);

        btnThirdSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        escenaNivel2.addActor(btnThirdSub2);

        //Objetos subniveles tercer nivel

        //Boton back
        ImageButton btnBackSub3 = new ImageButton(trdBack,trdBackPress);
        btnBackSub3.setPosition(btnBackSub3.getWidth()*2,PantallaMenu.ALTO/2 - btnBackSub3.getHeight()/2);

        btnBackSub3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaNiveles(game));

            }
        });
        escenaNivel3.addActor(btnBackSub3);


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





