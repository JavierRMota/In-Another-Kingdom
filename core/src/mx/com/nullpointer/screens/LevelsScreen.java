package mx.com.nullpointer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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


import mx.com.nullpointer.inanotherkingdom.Main;
import mx.com.nullpointer.utils.Text;


/*
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
    private Texture bookTexture1;
    private Texture bookTexture2;


    private Animation animationBook;
    private float timerAnimation;
    private boolean openBook;
    private float x;
    private float y;

    private String textureName;
    private String textureLockName;



    private  Stages stage;


    private int lastLevel;

    //Text
    private Text text;




    public LevelsScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        loadTextures();
        createObjects();
        x = 454;
        y = 245;
        openBook = false;
        Gdx.input.setCatchBackKey(true);


    }

    private void loadTextures() {
        backgroundTexture = new Texture("background/menubg.png");
        bookTexture1 = new Texture("background/tiraLibroAbriendose.png");
        bookTexture2 = new Texture("background/tiraLibroAbriendose2.png");

    }

    private void createObjects() {
        levelsStage = new Stage(view);
        levelOneStage = new Stage(view);
        levelTwoStage = new Stage(view);
        levelThreeStage = new Stage(view);
        text = new Text(55/255f,26/255f,2/255f);
        stage = Stages.LEVELS;
        Preferences prefs = Gdx.app.getPreferences("Progress");
        lastLevel = prefs.getInteger("lastLevel", 0);
        Gdx.input.setInputProcessor(levelsStage);
        loadLevelsStage();



        //Objetos general
        TextureRegionDrawable trdBackgroundSub = new TextureRegionDrawable(new TextureRegion(new Texture("background/openBook.png")));
        TextureRegionDrawable trdBackSub = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backdarkbtn.png")));
        TextureRegionDrawable trdBackSubPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backdarkbtnpress.png")));
        loadFirstBook(trdBackgroundSub,trdBackSub,trdBackSubPress);
        loadSecondBook(trdBackgroundSub,trdBackSub,trdBackSubPress);
        loadThirdBook(trdBackgroundSub,trdBackSub,trdBackSubPress);

    }

    private void loadThirdBook(TextureRegionDrawable trdBackgroundSub,TextureRegionDrawable trdBackSub,TextureRegionDrawable trdBackSubPress) {
        //Objetos subniveles tercer nivel
        Image imgBackgroundSub = new Image(trdBackgroundSub);
        imgBackgroundSub.setPosition(0,0);
        levelThreeStage.addActor(imgBackgroundSub);

        //Boton back
        ImageButton btnBackSub3 = new ImageButton(trdBackSub,trdBackSubPress);
        btnBackSub3.setPosition(btnBackSub3.getWidth()/2, HEIGHT /2 - btnBackSub3.getHeight()/2);

        btnBackSub3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.LEVELS);

            }
        });
        levelThreeStage.addActor(btnBackSub3);

    }

    private void loadSecondBook(TextureRegionDrawable trdBackgroundSub,TextureRegionDrawable trdBackSub,TextureRegionDrawable trdBackSubPress) {

        //Objetos subniveles segundo nivel
        Image imgBackgroundSub = new Image(trdBackgroundSub);
        imgBackgroundSub.setPosition(0,0);
        levelTwoStage.addActor(imgBackgroundSub);

        //Boton back
        ImageButton btnBackSub2 = new ImageButton(trdBackSub,trdBackSubPress);
        btnBackSub2.setPosition(btnBackSub2.getWidth()/2, MenuScreen.HEIGHT /2 - btnBackSub2.getHeight()/2);

        btnBackSub2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.LEVELS);

            }
        });
        levelTwoStage.addActor(btnBackSub2);


        TextureRegionDrawable trdLevelThree = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/dos/levelThree.png")));
        Image imgLevelThree = new Image(trdLevelThree);
        imgLevelThree.setPosition(165, 393);

        TextureRegionDrawable trdThree = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdThreePress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnThree = new ImageButton(trdThree,trdThreePress);
        btnThree.setPosition(397 , 400);
        btnThree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLTHREE));

            }
        });

        TextureRegionDrawable trdStarLevels = new TextureRegionDrawable(new TextureRegion(new Texture("gameObjects/star.png")));
        Image imgStarLevel3 = new Image(trdStarLevels);
        imgStarLevel3.setPosition(397, 454);

        levelTwoStage.addActor(imgLevelThree);
        levelTwoStage.addActor(btnThree);
        levelTwoStage.addActor(imgStarLevel3);

        //Botón Subnivel 2 primer nivel
        TextureRegionDrawable trdLevelFour = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/dos/levelFour.png")));
        Image imgLevelFour = new Image(trdLevelFour);
        imgLevelFour.setPosition(384 , 91);

        TextureRegionDrawable trdFour = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdFourPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnFour = new ImageButton(trdFour,trdFourPress);
        btnFour.setPosition(137,  91);
        btnFour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLFOUR));

            }
        });

        Image imgStarLevel4 = new Image(trdStarLevels);
        imgStarLevel4.setPosition(137, 150);

        TextureRegionDrawable trdLevelLock = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/levelLock.png")));
        Image imgLevelFourLock = new Image(trdLevelLock);
        imgLevelFourLock.setPosition(400, 91);


        if (lastLevel >= 4){
            levelTwoStage.addActor(imgLevelFour);
            levelTwoStage.addActor(btnFour);
            levelTwoStage.addActor(imgStarLevel4);

        }
        else{
            levelTwoStage.addActor(imgLevelFourLock);
        }


        //Botón Subnivel 3 primer nivel

        TextureRegionDrawable trdLevelFive = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelTwo.png")));
        Image imgLevelFive = new Image(trdLevelFive);
        imgLevelFive.setPosition(702, 311);

        TextureRegionDrawable trdFive = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdFivePress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnFive = new ImageButton(trdFive,trdFivePress);
        btnFive.setPosition(836, 103);
        btnFive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                //game.setScreen(new LoadingScreen(game,LVLFOUR));

            }
        });

        Image imgStarLevel5 = new Image(trdStarLevels);
        imgStarLevel5.setPosition(836, 158);

        Image imgLevelFiveLock = new Image(trdLevelLock);
        imgLevelFiveLock.setPosition(836, 311);


        if (lastLevel >= 5){
            levelTwoStage.addActor(imgLevelFive);
            levelTwoStage.addActor(btnFive);
            levelTwoStage.addActor(imgStarLevel5);
        }
        else{
            levelTwoStage.addActor(imgLevelFiveLock);
        }



    }
    private void loadFirstBook(TextureRegionDrawable trdBackgroundSub,TextureRegionDrawable trdBackSub,TextureRegionDrawable trdBackSubPress)
    {
        Image imgBackgroundSub = new Image(trdBackgroundSub);
        imgBackgroundSub.setPosition(0,0);
        levelOneStage.addActor(imgBackgroundSub);

        //Boton back subnivel 1
        ImageButton btnBackSub = new ImageButton(trdBackSub,trdBackSubPress);
        btnBackSub.setPosition(btnBackSub.getWidth()/2, HEIGHT /2 - btnBackSub.getHeight()/2);

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
        imgLevelZero.setPosition(165, 393);

        TextureRegionDrawable trdFirstSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdFirstSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnFirstSub1 = new ImageButton(trdFirstSub1,trdFirstSub1Press);
        btnFirstSub1.setPosition(397 , 400);
        btnFirstSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLZERO));

            }
        });

        TextureRegionDrawable trdStarLevels = new TextureRegionDrawable(new TextureRegion(new Texture("gameObjects/star.png")));
        Image imgStarLevel0 = new Image(trdStarLevels);
        imgStarLevel0.setPosition(397, 454);

        levelOneStage.addActor(imgLevelZero);
        levelOneStage.addActor(btnFirstSub1);
        levelOneStage.addActor(imgStarLevel0);

        //Botón Subnivel 2 primer nivel
        TextureRegionDrawable trdLevelOne = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelOne.png")));
        Image imgLevelOne = new Image(trdLevelOne);
        imgLevelOne.setPosition(384 , 91);

        TextureRegionDrawable trdSecSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdSecSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnSecSub1 = new ImageButton(trdSecSub1,trdSecSub1Press);
        btnSecSub1.setPosition(137,  91);
        btnSecSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLONE));

            }
        });

        Image imgStarLevel1 = new Image(trdStarLevels);
        imgStarLevel1.setPosition(137, 150);

        TextureRegionDrawable trdLevelLock = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/levelLock.png")));
        Image imgLevelOneLock = new Image(trdLevelLock);
        imgLevelOneLock.setPosition(400, 91);


        if (lastLevel >= 1){
            levelOneStage.addActor(imgLevelOne);
            levelOneStage.addActor(btnSecSub1);
            levelOneStage.addActor(imgStarLevel1);

        }
        else{
            levelOneStage.addActor(imgLevelOneLock);
        }


        //Botón Subnivel 3 primer nivel

        TextureRegionDrawable trdLevelTwo = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/uno/levelTwo.png")));
        Image imgLevelTwo = new Image(trdLevelTwo);
        imgLevelTwo.setPosition(702, 311);


        TextureRegionDrawable trdThirdSub1 = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnlevels.png")));
        TextureRegionDrawable trdThirdSub1Press = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/playbtnpresslevels.png")));
        ImageButton btnThirdSub1 = new ImageButton(trdThirdSub1,trdThirdSub1Press);
        btnThirdSub1.setPosition(836, 103);
        btnThirdSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLTWO));

            }
        });

        Image imgStarLevel2 = new Image(trdStarLevels);
        imgStarLevel2.setPosition(836, 158);

        Image imgLevelTwoLock = new Image(trdLevelLock);
        imgLevelTwoLock.setPosition(836, 311);


        if (lastLevel >= 2){
            levelOneStage.addActor(imgLevelTwo);
            levelOneStage.addActor(btnThirdSub1);
            levelOneStage.addActor(imgStarLevel2);
        }
        else{
            levelOneStage.addActor(imgLevelTwoLock);
        }
    }

    private void loadLevelsStage() {
        //Title
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
                TextureRegion region = new TextureRegion(bookTexture1);
                TextureRegion[][] frames = region.split(bookTexture1.getWidth()/7, bookTexture1.getHeight());
                animationBook = new Animation(0.12f, frames[0][0], frames[0][1], frames[0][2], frames[0][3], frames[0][4], frames[0][5]);
                animationBook.setPlayMode(Animation.PlayMode.NORMAL);
                openBook = true;
                changeScene(Stages.ONE);
            }
        });
        levelsStage.addActor(btnFirst);


        //Botón Segundo Nivel

        if (lastLevel >= 3){
            textureName = "niveles/dos/levelTwoBook.png";
            textureLockName = "niveles/dos/levelTwoBook.png";
        }
        if (lastLevel < 3){
            textureName = "niveles/lockedBook.png";
            textureLockName = "niveles/lock.png";

        }
        TextureRegionDrawable trdSec = new TextureRegionDrawable(new TextureRegion(new Texture(textureName)));
        TextureRegionDrawable trdSecPress = new TextureRegionDrawable(new TextureRegion(new Texture(textureLockName)));
        ImageButton btnSec = new ImageButton(trdSec,trdSecPress);
        btnSec.setPosition(3*btnSec.getWidth(), HEIGHT /2 - btnSec.getHeight()/2);

        btnSec.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                TextureRegion region = new TextureRegion(bookTexture2);
                TextureRegion[][] frames = region.split(bookTexture2.getWidth()/7, bookTexture2.getHeight());
                animationBook = new Animation(0.12f, frames[0][0], frames[0][1], frames[0][2], frames[0][3], frames[0][4], frames[0][5]);
                animationBook.setPlayMode(Animation.PlayMode.NORMAL);
                if (lastLevel >= 3){
                    openBook = true;
                    changeScene(Stages.TWO);
                }

            }
        });
        levelsStage.addActor(btnSec);


        //Botón Tercer Nivel
        TextureRegionDrawable trdThird = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lockedBook.png")));
        TextureRegionDrawable trdThirdPress = new TextureRegionDrawable(new TextureRegion(new Texture("niveles/lock.png")));
        ImageButton btnThird = new ImageButton(trdThird,trdThirdPress);
        btnThird.setPosition(5*btnThird.getWidth(), HEIGHT /2 - btnThird.getHeight()/2);

        btnThird.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);


            }
        });
        levelsStage.addActor(btnThird);
        currentScene = levelsStage;
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
;       batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.end();
        update();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {
            switch (stage)
            {
                case ONE:
                case TWO:
                case THREE:
                    changeScene(Stages.LEVELS);
                    break;
                case LEVELS:
                    game.setScreen(new LoadingScreen(game, MENU));
                    break;
            }

        }
    }

    private void update()
    {
        if(!openBook)
        {
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
        else {
                openBook();
            }

    }

    private void openBook() {
        timerAnimation+=Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animationBook.getKeyFrame(timerAnimation);
        if(timerAnimation>0.12*6)
        {
            openBook = false;
            timerAnimation=0;
        }
        batch.begin();
        batch.draw(region,x,y);
        batch.end();


    }

    private void drawOne() {
        Preferences prefs = Gdx.app.getPreferences("Progress");

        int score0 = prefs.getInteger("score0",0);
        String cadenaScore0 = "";
        cadenaScore0 = String.valueOf(score0);

        int score1 = prefs.getInteger("score1",0);
        String cadenaScore1 = "";
        cadenaScore1 = String.valueOf(score1);

        int score2 = prefs.getInteger("score2",0);
        String cadenaScore2 = "";
        cadenaScore2 = String.valueOf(score2);

        batch.begin();
        text.showMsg(batch,"LVL 1", 397,588,2,'l');
        text.showMsg(batch, cadenaScore0, 457,520,2,'l');
        if (lastLevel >= 1){
            text.showMsg(batch,"LVL 2", 138,288,2,'l');
            text.showMsg(batch, cadenaScore1, 199  ,212 ,2,'l');}
        if (lastLevel >= 2){
            text.showMsg(batch,"LVL 3", 834,288,2,'l');
            text.showMsg(batch, cadenaScore2, 897 ,220,2,'l');}
        batch.end();
    }

    private void drawTwo() {
        Preferences prefs = Gdx.app.getPreferences("Progress");
        int score3 = prefs.getInteger("score3",0);
        String cadenaScore3 = "";
        cadenaScore3 = String.valueOf(score3);

        int score4 = prefs.getInteger("score4",0);
        String cadenaScore4 = "";
        cadenaScore4 = String.valueOf(score4);

        int score5 = prefs.getInteger("score5",0);
        String cadenaScore5 = "";
        cadenaScore5 = String.valueOf(score5);

        batch.begin();
        text.showMsg(batch,"LVL 4", 397,588,2,'l');
        text.showMsg(batch, cadenaScore3, 457,520,2,'l');
        if (lastLevel >= 4){
            text.showMsg(batch,"LVL 5", 138,288,2,'l');
            text.showMsg(batch, cadenaScore4, 199  ,212 ,2,'l');}
        if (lastLevel >= 5){
            text.showMsg(batch,"LVL 6", 834,288,2,'l');
            text.showMsg(batch, cadenaScore5, 897 ,220,2,'l');}
        batch.end();

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





