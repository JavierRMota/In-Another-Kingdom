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

    private TextureRegionDrawable trdPlay;
    private TextureRegionDrawable trdPlayPress;
    private TextureRegionDrawable trdBackgroundSub;
    private TextureRegionDrawable trdBackSub;
    private TextureRegionDrawable trdBackSubPress;
    private TextureRegionDrawable trdStarLevels;
    private TextureRegionDrawable trdLevelLock;


    private Animation animationBook;
    private float timerAnimation;
    private boolean openBook;
    private float x;
    private float y;


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




        Texture playButtonTexture = assetManager.get("niveles/playbtnlevels.png");
        Texture playButtonPressTexture =  assetManager.get("niveles/playbtnpresslevels.png");
        trdPlay = new TextureRegionDrawable(new TextureRegion(playButtonTexture));
        trdPlayPress = new TextureRegionDrawable(new TextureRegion(playButtonPressTexture));

        Texture openBookTexture = assetManager.get("background/openBook.png");
        Texture backDark = assetManager.get("btn/backdarkbtn.png");
        Texture backDarkPress = assetManager.get("btn/backdarkbtnpress.png");
        trdBackgroundSub = new TextureRegionDrawable(new TextureRegion(openBookTexture));
        trdBackSub = new TextureRegionDrawable(new TextureRegion(backDark));
        trdBackSubPress = new TextureRegionDrawable(new TextureRegion(backDarkPress));
        Texture starTexture =assetManager.get("gameObjects/star.png");
        trdStarLevels = new TextureRegionDrawable(new TextureRegion(starTexture));
        Texture levelLockTexture = assetManager.get("niveles/levelLock.png");
        trdLevelLock = new TextureRegionDrawable(new TextureRegion(levelLockTexture));
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



        loadFirstBook();
        loadSecondBook();
        loadThirdBook();

    }

    private void loadThirdBook() {
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

        Texture levelSixTexture = assetManager.get("niveles/dos/levelThree.png");
        TextureRegionDrawable trdSix = new TextureRegionDrawable(new TextureRegion(levelSixTexture));
        Image imgLevelSix= new Image(trdSix);
        imgLevelSix.setPosition(165, 393);


        ImageButton btnSix = new ImageButton(trdPlay,trdPlayPress);
        btnSix.setPosition(397 , 400);
        btnSix.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLTHREE));

            }
        });

        Image imgStarLevel6 = new Image(trdStarLevels);
        imgStarLevel6.setPosition(397, 454);

        levelThreeStage.addActor(imgLevelSix);
        levelThreeStage.addActor(btnSix);
        levelThreeStage.addActor(imgStarLevel6);

        //Botón Subnivel 2 primer nivel
        Texture levelSevenTexture = assetManager.get("niveles/dos/levelFour.png");
        TextureRegionDrawable trdLevelSeven = new TextureRegionDrawable(new TextureRegion(levelSevenTexture));
        Image imgLevelSeven = new Image(trdLevelSeven);
        imgLevelSeven.setPosition(384 , 91);


        ImageButton btnSeven = new ImageButton(trdPlay,trdPlayPress);
        btnSeven.setPosition(137,  91);
        btnSeven.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLFOUR));

            }
        });

        Image imgStarLevel7 = new Image(trdStarLevels);
        imgStarLevel7.setPosition(137, 150);

        Image imgLevelSevenLock = new Image(trdLevelLock);
        imgLevelSevenLock.setPosition(400, 91);


        if (lastLevel >= 7){
            levelThreeStage.addActor(imgLevelSeven);
            levelThreeStage.addActor(btnSeven);
            levelThreeStage.addActor(imgStarLevel7);

        }
        else{
            levelThreeStage.addActor(imgLevelSevenLock);
        }


        //Botón Subnivel 3 segundo
        Texture levelEightTexture = assetManager.get("niveles/dos/levelFive.png");
        TextureRegionDrawable trdLevelEight = new TextureRegionDrawable(new TextureRegion(levelEightTexture));
        Image imgLevelEight = new Image(trdLevelEight);
        imgLevelEight.setPosition(702, 311);


        ImageButton btnEight = new ImageButton(trdPlay,trdPlayPress);
        btnEight.setPosition(836, 103);
        btnEight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLFIVE));

            }
        });

        Image imgStarLevel8 = new Image(trdStarLevels);
        imgStarLevel8.setPosition(836, 158);

        Image imgLevelEightLock = new Image(trdLevelLock);
        imgLevelEightLock.setPosition(836, 311);


        if (lastLevel >= 8){
            levelThreeStage.addActor(imgLevelEight);
            levelThreeStage.addActor(btnEight);
            levelThreeStage.addActor(imgStarLevel8);
        }
        else{
            levelThreeStage.addActor(imgLevelEightLock);
        }

    }

    private void loadSecondBook() {

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

        Texture levelThreeTexture = assetManager.get("niveles/dos/levelThree.png");
        TextureRegionDrawable trdLevelThree = new TextureRegionDrawable(new TextureRegion(levelThreeTexture));
        Image imgLevelThree = new Image(trdLevelThree);
        imgLevelThree.setPosition(165, 393);


        ImageButton btnThree = new ImageButton(trdPlay,trdPlayPress);
        btnThree.setPosition(397 , 400);
        btnThree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLTHREE));

            }
        });

        Image imgStarLevel3 = new Image(trdStarLevels);
        imgStarLevel3.setPosition(397, 454);

        levelTwoStage.addActor(imgLevelThree);
        levelTwoStage.addActor(btnThree);
        levelTwoStage.addActor(imgStarLevel3);

        //Botón Subnivel 2 primer nivel
        Texture levelFourTexture = assetManager.get("niveles/dos/levelFour.png");
        TextureRegionDrawable trdLevelFour = new TextureRegionDrawable(new TextureRegion(levelFourTexture));
        Image imgLevelFour = new Image(trdLevelFour);
        imgLevelFour.setPosition(384 , 91);


        ImageButton btnFour = new ImageButton(trdPlay,trdPlayPress);
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


        //Botón Subnivel 3 segundo
        Texture levelFiveTexture = assetManager.get("niveles/dos/levelFive.png");
        TextureRegionDrawable trdLevelFive = new TextureRegionDrawable(new TextureRegion(levelFiveTexture));
        Image imgLevelFive = new Image(trdLevelFive);
        imgLevelFive.setPosition(702, 311);


        ImageButton btnFive = new ImageButton(trdPlay,trdPlayPress);
        btnFive.setPosition(836, 103);
        btnFive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLFIVE));

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
    private void loadFirstBook()
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
        Texture levelZeroTexture = assetManager.get("niveles/uno/levelZero.png");
        TextureRegionDrawable trdLevelZero = new TextureRegionDrawable(new TextureRegion(levelZeroTexture));
        Image imgLevelZero = new Image(trdLevelZero);
        imgLevelZero.setPosition(165, 393);


        ImageButton btnFirstSub1 = new ImageButton(trdPlay,trdPlayPress);
        btnFirstSub1.setPosition(397 , 400);
        btnFirstSub1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,LVLZERO));

            }
        });

        Image imgStarLevel0 = new Image(trdStarLevels);
        imgStarLevel0.setPosition(397, 454);

        levelOneStage.addActor(imgLevelZero);
        levelOneStage.addActor(btnFirstSub1);
        levelOneStage.addActor(imgStarLevel0);

        //Botón Subnivel 2 primer nivel
        Texture levelOneTexture = assetManager.get("niveles/uno/levelOne.png");
        TextureRegionDrawable trdLevelOne = new TextureRegionDrawable(new TextureRegion(levelOneTexture));
        Image imgLevelOne = new Image(trdLevelOne);
        imgLevelOne.setPosition(384 , 91);


        ImageButton btnSecSub1 = new ImageButton(trdPlay,trdPlayPress);
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
        Texture levelTwoTexture = assetManager.get("niveles/uno/levelTwo.png");
        TextureRegionDrawable trdLevelTwo = new TextureRegionDrawable(new TextureRegion(levelTwoTexture));
        Image imgLevelTwo = new Image(trdLevelTwo);
        imgLevelTwo.setPosition(702, 311);


        ImageButton btnThirdSub1 = new ImageButton(trdPlay,trdPlayPress);
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
        Texture logoTexture = assetManager.get("logo_alargado.png");
        TextureRegionDrawable trdTittleLevels = new TextureRegionDrawable(new TextureRegion(logoTexture));
        Image imgTittleLevels = new Image(trdTittleLevels);
        imgTittleLevels.setPosition(WIDTH /2 - imgTittleLevels.getWidth()/2, 6 * HEIGHT/8 );
        levelsStage.addActor(imgTittleLevels);

        //Botón Back
        Texture backTexture = assetManager.get("btn/backbtn.png");
        Texture backPressTexture = assetManager.get("btn/backbtnpress.png");
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backTexture));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(backPressTexture));
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
        Texture levelOneTexture = assetManager.get("niveles/uno/levelOneBook.png");
        TextureRegionDrawable trdFirst = new TextureRegionDrawable(new TextureRegion(levelOneTexture));
        TextureRegionDrawable trdFirstPress = new TextureRegionDrawable(new TextureRegion(levelOneTexture));
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
        Texture textureNormal;
        Texture textureLock;
        if (lastLevel >= 3){
            textureNormal = assetManager.get("niveles/dos/levelTwoBook.png");
            textureLock = assetManager.get("niveles/dos/levelTwoBook.png");
        }else {
            textureNormal = assetManager.get("niveles/lockedBook.png");
            textureLock = assetManager.get("niveles/lock.png");

        }
        TextureRegionDrawable trdSec = new TextureRegionDrawable(new TextureRegion(textureNormal));
        TextureRegionDrawable trdSecPress = new TextureRegionDrawable(new TextureRegion(textureLock));
        ImageButton btnSec = new ImageButton(trdSec,trdSecPress);
        btnSec.setPosition(3*btnSec.getWidth(), HEIGHT /2 - btnSec.getHeight()/2);

        btnSec.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if (lastLevel >= 3){
                    TextureRegion region = new TextureRegion(bookTexture2);
                    TextureRegion[][] frames = region.split(bookTexture2.getWidth()/7, bookTexture2.getHeight());
                    animationBook = new Animation(0.12f, frames[0][0], frames[0][1], frames[0][2], frames[0][3], frames[0][4], frames[0][5]);
                    animationBook.setPlayMode(Animation.PlayMode.NORMAL);
                    openBook = true;
                    changeScene(Stages.TWO);
                }

            }
        });
        levelsStage.addActor(btnSec);


        //Botón Tercer Nivel
        if (lastLevel >= 6){
            textureNormal = assetManager.get("niveles/tres/levelThreeBook.png");
            textureLock = assetManager.get("niveles/tres/levelThreeBook.png");
        }else {
            textureNormal = assetManager.get("niveles/lockedBook.png");
            textureLock = assetManager.get("niveles/lock.png");

        }
        TextureRegionDrawable trdThird = new TextureRegionDrawable(new TextureRegion(textureNormal));
        TextureRegionDrawable trdThirdPress = new TextureRegionDrawable(new TextureRegion(textureLock));
        ImageButton btnThird = new ImageButton(trdThird,trdThirdPress);
        btnThird.setPosition(5*btnThird.getWidth(), HEIGHT /2 - btnThird.getHeight()/2);

        btnThird.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                if (lastLevel >= 6){
                    TextureRegion region = new TextureRegion(bookTexture2);
                    TextureRegion[][] frames = region.split(bookTexture2.getWidth()/7, bookTexture2.getHeight());
                    animationBook = new Animation(0.12f, frames[0][0], frames[0][1], frames[0][2], frames[0][3], frames[0][4], frames[0][5]);
                    animationBook.setPlayMode(Animation.PlayMode.NORMAL);
                    openBook = true;
                    changeScene(Stages.THREE);
                }


            }
        });
        levelsStage.addActor(btnThird);
        currentScene = levelsStage;

        //Boton How To
        Texture howTexture = assetManager.get("btn/howto.png");
        Texture howPressTexture = assetManager.get("btn/howto.png");
        TextureRegionDrawable trdHow = new TextureRegionDrawable(new TextureRegion(howTexture));
        TextureRegionDrawable trdHowPress = new TextureRegionDrawable(new TextureRegion(howPressTexture));
        ImageButton btnHow = new ImageButton(trdHow,trdHowPress);
        btnHow.setPosition(WIDTH/2-btnHow.getWidth()/2, HEIGHT/5 - btnHow.getHeight()/2);

        btnHow.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game,TUTORIAL));

            }
        });
        levelsStage.addActor(btnHow);
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
        Preferences prefs = Gdx.app.getPreferences("Progress");
        int score6 = prefs.getInteger("score6",0);
        String cadenaScore6 = "";
        cadenaScore6 = String.valueOf(score6);

        int score7 = prefs.getInteger("score7",0);
        String cadenaScore7 = "";
        cadenaScore7 = String.valueOf(score7);

        int score8 = prefs.getInteger("score8",0);
        String cadenaScore8 = "";
        cadenaScore8 = String.valueOf(score8);

        batch.begin();
        text.showMsg(batch,"LVL 7", 397,588,2,'l');
        text.showMsg(batch, cadenaScore6, 457,520,2,'l');
        if (lastLevel >= 7){
            text.showMsg(batch,"LVL 8", 138,288,2,'l');
            text.showMsg(batch, cadenaScore7, 199  ,212 ,2,'l');}
        if (lastLevel >= 8){
            text.showMsg(batch,"LVL 9", 834,288,2,'l');
            text.showMsg(batch, cadenaScore8, 897 ,220,2,'l');}
        batch.end();
    }

    private enum Stages
    {
        LEVELS,
        ONE,
        TWO,
        THREE
    }



    }





