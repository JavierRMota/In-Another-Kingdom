package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.com.nullpointer.niveles.LevelZero;
import mx.com.nullpointer.niveles.LevelOne;
import mx.com.nullpointer.utils.GenericScreen;

/**
 * Created by MarinaHaro on 25/02/18.
 */

class LevelsScreen extends GenericScreen{
    private final Main game;


    //Escena para la pantalla Niveles
    private Stage levelsStage;
    private Stage levelOneStage;
    private Stage levelTwoStage;
    private Stage levelThreeStage;
    private Stage currentScene;

    //Texturas
    private Texture backgroundTexture;
    private Texture titleTexture;



    public LevelsScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

    }

    private void cargarTexturas() {
        backgroundTexture = new Texture("background/menubg.png");
        titleTexture = new Texture("logo_alargado.png");

    }

    private void crearObjetos() {
        levelsStage = new Stage(view);
        levelOneStage = new Stage(view);
        levelTwoStage = new Stage(view);
        levelThreeStage = new Stage(view);

        Gdx.input.setInputProcessor(levelsStage);

        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()*2, MenuScreen.HEIGHT /2 - btnBack.getHeight()/2);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MenuScreen(game));

            }
        });
        levelsStage.addActor(btnBack);

        //Botón Primer Nivel
        TextureRegionDrawable trdFirst = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        TextureRegionDrawable trdFirstPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        ImageButton btnFirst = new ImageButton(trdFirst,trdFirstPress);
        btnFirst.setPosition(btnFirst.getWidth(), MenuScreen.HEIGHT /2 - btnFirst.getHeight()/2);

        btnFirst.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(levelOneStage);
            }
        });
        levelsStage.addActor(btnFirst);


        //Botón Segundo Nivel
        TextureRegionDrawable trdSec = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdSecPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnSec = new ImageButton(trdSec,trdSecPress);
        btnSec.setPosition(3*btnSec.getWidth(), MenuScreen.HEIGHT /2 - btnSec.getHeight()/2);

        btnSec.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        levelsStage.addActor(btnSec);


        //Botón Tercer Nivel
        TextureRegionDrawable trdThird = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnThird = new ImageButton(trdThird,trdThirdPress);
        btnThird.setPosition(5*btnThird.getWidth(), MenuScreen.HEIGHT /2 - btnThird.getHeight()/2);

        btnThird.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);
                //game.setScreen(new LevelsScreen(game));

            }
        });
        levelsStage.addActor(btnThird);
        currentScene = levelsStage;


        //Objetos sibniveles primer nivel

        //Boton back subnivel 1
        //Botón Back
        ImageButton btnBackSub = new ImageButton(trdBack,trdBackPress);
        btnBackSub.setPosition(btnBackSub.getWidth()*2, MenuScreen.HEIGHT /2 - btnBackSub.getHeight()/2);

        btnBackSub.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LevelsScreen(game));

            }
        });
        levelOneStage.addActor(btnBackSub);


        //Botón Subnivel 1 primer nivel
        TextureRegionDrawable trdFirstSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdFirstSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        ImageButton btnFirstSub1 = new ImageButton(trdFirstSub1,trdFirstSub1Press);
        btnFirstSub1.setPosition(btnFirstSub1.getWidth(), MenuScreen.HEIGHT /2 - btnFirstSub1.getHeight()/2);

        btnFirstSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LevelZero(game));

            }
        });
        levelOneStage.addActor(btnFirstSub1);

        //Botón Subnivel 2 primer nivel
        TextureRegionDrawable trdSecSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdSecSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnSecSub1 = new ImageButton(trdSecSub1,trdSecSub1Press);
        btnSecSub1.setPosition(3*btnSecSub1.getWidth(), MenuScreen.HEIGHT /2 - btnSecSub1.getHeight()/2);

        btnSecSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LevelOne(game));

            }
        });
        levelOneStage.addActor(btnSecSub1);

        //Botón Subnivel 3 primer nivel
        TextureRegionDrawable trdThirdSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnThirdSub1 = new ImageButton(trdThirdSub1,trdThirdSub1Press);
        btnThirdSub1.setPosition(5*btnThirdSub1.getWidth(), MenuScreen.HEIGHT /2 - btnThirdSub1.getHeight()/2);

        btnThirdSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        levelOneStage.addActor(btnThirdSub1);


        //Objetos subniveles segundo nivel

        //Boton back
        ImageButton btnBackSub2 = new ImageButton(trdBack,trdBackPress);
        btnBackSub2.setPosition(btnBackSub2.getWidth()*2, MenuScreen.HEIGHT /2 - btnBackSub2.getHeight()/2);

        btnBackSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LevelsScreen(game));

            }
        });
        levelTwoStage.addActor(btnBackSub2);

        //Botón Subnivel 1 segundo nivel
        TextureRegionDrawable trdFirstSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdFirstSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnFirstSub2 = new ImageButton(trdFirstSub2,trdFirstSub2Press);
        btnFirstSub2.setPosition(btnFirstSub2.getWidth(), MenuScreen.HEIGHT /2 - btnFirstSub2.getHeight()/2);

        btnFirstSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        levelTwoStage.addActor(btnFirstSub2);

        //Botón Subnivel 2 segundo nivel
        TextureRegionDrawable trdSecSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdSecSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnSecSub2 = new ImageButton(trdSecSub2,trdSecSub2Press);
        btnSecSub2.setPosition(3*btnSecSub2.getWidth(), MenuScreen.HEIGHT /2 - btnSecSub2.getHeight()/2);

        btnSecSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        levelTwoStage.addActor(btnSecSub2);

        //Botón Subnivel 3 segundo nivel
        TextureRegionDrawable trdThirdSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnThirdSub2 = new ImageButton(trdThirdSub2,trdThirdSub2Press);
        btnThirdSub2.setPosition(5*btnThirdSub2.getWidth(), MenuScreen.HEIGHT /2 - btnThirdSub2.getHeight()/2);

        btnThirdSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(currentScene);

            }
        });
        levelTwoStage.addActor(btnThirdSub2);

        //Objetos subniveles tercer nivel

        //Boton back
        ImageButton btnBackSub3 = new ImageButton(trdBack,trdBackPress);
        btnBackSub3.setPosition(btnBackSub3.getWidth()*2, MenuScreen.HEIGHT /2 - btnBackSub3.getHeight()/2);

        btnBackSub3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LevelsScreen(game));

            }
        });
        levelThreeStage.addActor(btnBackSub3);


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
        batch.draw(backgroundTexture,0 ,0);
        batch.draw(titleTexture, WIDTH /2 - titleTexture.getWidth()/2, 6* HEIGHT /8 );
        batch.end();
        currentScene.draw();

    }

    }





