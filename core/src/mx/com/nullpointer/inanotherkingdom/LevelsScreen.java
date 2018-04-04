package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.Text;


/**
 * Created by MarinaHaro on 25/02/18.
 */

class LevelsScreen extends GenericScreen{

    //Escena para la pantalla Niveles
    private Stage levelsStage;
    private Stage levelOneStage;
    private Stage levelTwoStage;
    private Stage levelThreeStage;
    private Stage currentScene;

    //Texturas
    private Texture backgroundTexture;
    private Texture bookTexture;

    private Animation animationBook;

    private  Stages stage;


    private int lastLevel;
    private float timerAnimation;
    private boolean OpenBook;
    private float x;
    private float y;

    //Text
    private Text text;




    public LevelsScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        TextureRegion region = new TextureRegion(bookTexture);
        TextureRegion[][] frames = region.split(bookTexture.getWidth()/7, bookTexture.getHeight());
        animationBook = new Animation(0.1f, frames[0][0], frames[0][1], frames[0][2], frames[0][3], frames[0][4], frames[0][5]);
        animationBook.setPlayMode(Animation.PlayMode.NORMAL);
        x = 186/2;
        y = HEIGHT/2 - 239/2;

    }

    private void cargarTexturas() {
        backgroundTexture = new Texture("background/menubg.png");
        bookTexture = new Texture("background/tiraLibroAbriendose.png");

    }

    private void crearObjetos() {
        levelsStage = new Stage(view);
        levelOneStage = new Stage(view);
        levelTwoStage = new Stage(view);
        levelThreeStage = new Stage(view);

        text = new Text(55/255f,26/255f,2/255f);
        stage = Stages.LEVELS;

        Preferences prefs = Gdx.app.getPreferences("Progress");
        lastLevel = prefs.getInteger("lastLevel", 0);

        Gdx.input.setInputProcessor(levelsStage);

        //Tittle
        TextureRegionDrawable trdTittleLevels = new TextureRegionDrawable(new TextureRegion(new Texture("logo_alargado.png")));
        Image imgTittleLevels = new Image(trdTittleLevels);
        imgTittleLevels.setPosition(WIDTH /2 - imgTittleLevels.getWidth()/2, 6 * HEIGHT/8 );
        levelsStage.addActor(imgTittleLevels);

        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()/2, HEIGHT/2 - btnBack.getHeight()/2);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,MENU));

            }
        });
        levelsStage.addActor(btnBack);

        //Botón Primer Nivel
        TextureRegionDrawable trdFirst = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        TextureRegionDrawable trdFirstPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOneBook.png")));
        ImageButton btnFirst = new ImageButton(trdFirst,trdFirstPress);
        btnFirst.setPosition(btnFirst.getWidth(), HEIGHT /2 - btnFirst.getHeight()/2);

        btnFirst.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.ONE);
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


            }
        });
        levelsStage.addActor(btnThird);
        currentScene = levelsStage;


        //Objetos sibniveles primer nivel

        TextureRegionDrawable trdBackgroundSub = new TextureRegionDrawable(new TextureRegion(new Texture("background/openBook.png")));
        Image imgBackgroundSub = new Image(trdBackgroundSub);
        imgBackgroundSub.setPosition(0,0);
        levelOneStage.addActor(imgBackgroundSub);

        //Boton back subnivel 1

        TextureRegionDrawable trdBackSub = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backdarkbtn.png")));
        TextureRegionDrawable trdBackSubPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backdarkbtnpress.png")));
        ImageButton btnBackSub = new ImageButton(trdBackSub,trdBackSubPress);
        btnBackSub.setPosition(btnBackSub.getWidth()/2, MenuScreen.HEIGHT /2 - btnBackSub.getHeight()/2);

        btnBackSub.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.LEVELS);

            }
        });
        levelOneStage.addActor(btnBackSub);


        //Botón Subnivel 1 primer nivel
        TextureRegionDrawable trdLevelZero = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelZero.png")));
        Image imgLevelZero = new Image(trdLevelZero);
        imgLevelZero.setPosition(imgLevelZero.getWidth()/4*3, HEIGHT - imgLevelZero.getHeight()- imgLevelZero.getHeight()/5);

        TextureRegionDrawable trdFirstSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdFirstSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnFirstSub1 = new ImageButton(trdFirstSub1,trdFirstSub1Press);
        btnFirstSub1.setPosition(WIDTH/3 - 30 , HEIGHT/4*2 +40);
        btnFirstSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLZERO));

            }
        });

        TextureRegionDrawable trdStarLevels = new TextureRegionDrawable(new TextureRegion(new Texture("gameObjects/star.png")));
        Image imgStarLevel0 = new Image(trdStarLevels);
        imgStarLevel0.setPosition(btnFirstSub1.getX() , HEIGHT - imgLevelZero.getY()/3*2);

        levelOneStage.addActor(imgLevelZero);
        levelOneStage.addActor(btnFirstSub1);
        levelOneStage.addActor(imgStarLevel0);

        //Botón Subnivel 2 primer nivel
        TextureRegionDrawable trdLevelOne = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOne.png")));
        Image imgLevelOne = new Image(trdLevelOne);
        imgLevelOne.setPosition(imgLevelOne.getWidth()/4*7 , imgLevelOne.getHeight()/3);

        TextureRegionDrawable trdSecSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdSecSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnSecSub1 = new ImageButton(trdSecSub1,trdSecSub1Press);
        btnSecSub1.setPosition(imgLevelOne.getX()/2 - 55,  imgLevelOne.getHeight()/3);
        btnSecSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLONE));

            }
        });

        Image imgStarLevel1 = new Image(trdStarLevels);
        imgStarLevel1.setPosition(btnSecSub1.getX(), imgLevelOne.getY()*1.65f);

        TextureRegionDrawable trdLevelLock = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/levelLock.png")));
        Image imgLevelOneLock = new Image(trdLevelLock);
        imgLevelOneLock.setPosition(imgLevelOne.getWidth()/4*7.5f - 55, imgLevelOne.getHeight()/3);


        if (lastLevel >= LVLONE - 5){
            levelOneStage.addActor(imgLevelOne);
            levelOneStage.addActor(btnSecSub1);
            levelOneStage.addActor(imgStarLevel1);

        }
        else if (lastLevel < LVLONE - 5 || lastLevel >= 0){
            levelOneStage.addActor(imgLevelOneLock);
        }


        //Botón Subnivel 3 primer nivel

        TextureRegionDrawable trdLevelTwo = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelTwo.png")));
        Image imgLevelTwo = new Image(trdLevelTwo);
        imgLevelTwo.setPosition(WIDTH - imgLevelTwo.getWidth()/3*4, HEIGHT - imgLevelOne.getHeight()/2*3);

        TextureRegionDrawable trdThirdSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdThirdSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnThirdSub1 = new ImageButton(trdThirdSub1,trdThirdSub1Press);
        btnThirdSub1.setPosition(imgLevelTwo.getX() + imgLevelTwo.getWidth()/2 - btnThirdSub1.getWidth()/2, HEIGHT/7);
        btnThirdSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLTWO));

            }
        });

        Image imgStarLevel2 = new Image(trdStarLevels);
        imgStarLevel2.setPosition(btnThirdSub1.getX(), btnThirdSub1.getY() + imgStarLevel2.getHeight());

        Image imgLevelTwoLock = new Image(trdLevelLock);
        imgLevelTwoLock.setPosition(btnThirdSub1.getX(), imgLevelTwo.getY());

        Preferences prefs2 = Gdx.app.getPreferences("Progress");
        lastLevel = prefs2.getInteger("lastLevel", 0);

        if (lastLevel >= LVLTWO - 5){
            levelOneStage.addActor(imgLevelTwo);
            levelOneStage.addActor(btnThirdSub1);
            levelOneStage.addActor(imgStarLevel2);
        }
        if (lastLevel < LVLONE - 5 || lastLevel >= 0){
            levelOneStage.addActor(imgLevelTwoLock);
        }



        //Objetos subniveles segundo nivel

        Image imgBackgroundSub2 = new Image(trdBackgroundSub);
        imgBackgroundSub2.setPosition(0,0);
        levelTwoStage.addActor(imgBackgroundSub2);

        //Boton back
        ImageButton btnBackSub2 = new ImageButton(trdBackSub,trdBackSubPress);
        btnBackSub2.setPosition(btnBackSub2.getWidth()/2, MenuScreen.HEIGHT /2 - btnBackSub2.getHeight()/2);

        btnBackSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);


            }
        });
        levelTwoStage.addActor(btnBackSub2);

        //Botón Subnivel 1 segundo nivel
        TextureRegionDrawable trdFirstSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdFirstSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnFirstSub2 = new ImageButton(trdFirstSub2,trdFirstSub2Press);
        btnFirstSub2.setPosition(btnFirstSub2.getWidth(), MenuScreen.HEIGHT /2 - btnFirstSub2.getHeight()/2);

        btnFirstSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);


            }
        });
        levelTwoStage.addActor(btnFirstSub2);

        //Botón Subnivel 2 segundo nivel
        TextureRegionDrawable trdSecSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdSecSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnSecSub2 = new ImageButton(trdSecSub2,trdSecSub2Press);
        btnSecSub2.setPosition(3*btnSecSub2.getWidth(), MenuScreen.HEIGHT /2 - btnSecSub2.getHeight()/2);

        btnSecSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);


            }
        });
        levelTwoStage.addActor(btnSecSub2);

        //Botón Subnivel 3 segundo nivel
        TextureRegionDrawable trdThirdSub2 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdThirdSub2Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnThirdSub2 = new ImageButton(trdThirdSub2,trdThirdSub2Press);
        btnThirdSub2.setPosition(5*btnThirdSub2.getWidth(), MenuScreen.HEIGHT /2 - btnThirdSub2.getHeight()/2);

        btnThirdSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);


            }
        });
        levelTwoStage.addActor(btnThirdSub2);

        //Objetos subniveles tercer nivel
        Image imgBackgroundSub3 = new Image(trdBackgroundSub);
        imgBackgroundSub3.setPosition(0,0);
        levelThreeStage.addActor(imgBackgroundSub3);

        //Boton back
        ImageButton btnBackSub3 = new ImageButton(trdBackSub,trdBackSubPress);
        btnBackSub3.setPosition(btnBackSub3.getWidth()/2, MenuScreen.HEIGHT /2 - btnBackSub3.getHeight()/2);

        btnBackSub3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.LEVELS);

            }
        });
        levelThreeStage.addActor(btnBackSub3);

    }

    private void changeScene(Stages stage) {
        switch (stage)
        {
            case LEVELS:
                currentScene = levelsStage;
                this.stage = Stages.LEVELS;
                break;
            case ONE:
                currentScene = levelOneStage;
                this.stage = Stages.ONE;
                break;
            case TWO:
                currentScene = levelTwoStage;
                this.stage = Stages.TWO;
                break;
            case THREE:
                currentScene = levelThreeStage;
                this.stage = Stages.THREE;
                break;
        }
        Gdx.input.setInputProcessor(currentScene);
    }


    @Override
    public void render(float delta) {
        clearScreen();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.end();
        currentScene.draw();
        switch (stage)
        {
            case LEVELS:
                break;
            case ONE:
                drawOne();
                break;
            case TWO:
                drawTwo();
                break;
            case THREE:
                drawThree();
                break;
        }


    }

    private void drawOne() {
        Preferences prefs = Gdx.app.getPreferences("Progress");
        int score1 = prefs.getInteger("score0",0);
        String cadenaScore1 = "";
        cadenaScore1 = String.valueOf(score1);

        int score2 = prefs.getInteger("score1",0);
        String cadenaScore2 = "";
        cadenaScore2 = String.valueOf(score2);

        int score3 = prefs.getInteger("score2",0);
        String cadenaScore3 = "";
        cadenaScore3 = String.valueOf(score3);

        batch.begin();
        text.showMsg(batch,"LVL 1", WIDTH/3 - 30,HEIGHT/10*9 - 60,2,'l');
        text.showMsg(batch, cadenaScore1, WIDTH/3 + 30,HEIGHT/4*3 - 20,2,'l');
        if (lastLevel >= LVLONE - 5){
        text.showMsg(batch,"LVL 2", WIDTH/7 - 45,HEIGHT/5*2,2,'l');
        text.showMsg(batch, cadenaScore2, WIDTH/6 - 15  ,HEIGHT/4 + 32 ,2,'l');}
        if (lastLevel >= LVLTWO - 5){
        text.showMsg(batch,"LVL 3", WIDTH/6*4 - 20,HEIGHT/5*2,2,'l');
        text.showMsg(batch, cadenaScore3, WIDTH/4*3 - 63 ,HEIGHT/4 + 40 ,2,'l');}
        batch.end();
    }

    private void drawTwo() {

    }

    private void drawThree() {
    }

    private enum Stages
    {
        LEVELS,
        ONE,
        TWO,
        THREE
    }



    }





