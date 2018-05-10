package mx.com.nullpointer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.com.nullpointer.inanotherkingdom.Main;

/**
 * Created by MarinaHaro on 19/04/18.
 */

public class StoryScreen extends GenericScreen {

    private Texture story1;
    private Texture story2;
    private Texture story3;
    private Texture story4;
    private Texture story5;
    private Texture story6;
    private Texture story7;
    private Texture story8;

    private Stage stage1;
    private Stage stage2;
    private Stage stage3;
    private Stage stage4;
    private Stage stage5;
    private Stage stage6;
    private Stage stage7;
    private Stage stage8;
    private Stage currentStage;

    private Stages stage;

    public StoryScreen(Main game)
    {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);

        loadTextures();
        loadScenes();
        loadObjects();

        currentStage = stage1;
        Gdx.input.setInputProcessor(currentStage);
        stage = Stages.ONE;

    }

    private void loadObjects() {
        //Botón Back
        Texture backTexture = assetManager.get("btn/backdarkbtn.png");
        Texture backPressTexture = assetManager.get("btn/backdarkbtnpress.png");
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

        //Escena1
        Texture nextTexture = assetManager.get("btn/nextbtn.png");
        Texture nextPressTexture = assetManager.get("btn/nextbtnpress.png");

        TextureRegionDrawable trdNext1 = new TextureRegionDrawable(new TextureRegion(nextTexture));
        TextureRegionDrawable trdNextPress1 = new TextureRegionDrawable(new TextureRegion(nextPressTexture));
        ImageButton btnNext1 = new ImageButton(trdNext1,trdNextPress1);
        btnNext1.setPosition(WIDTH - 3*btnNext1.getWidth()/2, HEIGHT/2 - btnNext1.getHeight()/2);

        btnNext1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.TWO);

            }
        });

        TextureRegionDrawable trdStory1 = new TextureRegionDrawable(new TextureRegion(story1));
        Image imgStory1 = new Image(trdStory1);
        imgStory1.setPosition(0, 0);
        stage1.addActor(imgStory1);
        stage1.addActor(btnNext1);

        //Escena 2
        ImageButton btnNext2 = new ImageButton(trdNext1,trdNextPress1);
        btnNext2.setPosition(WIDTH - 3*btnNext2.getWidth()/2, HEIGHT/2 - btnNext2.getHeight()/2);

        btnNext2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.THREE);

            }
        });


        TextureRegionDrawable trdStory2 = new TextureRegionDrawable(new TextureRegion(story2));
        Image imgStory2 = new Image(trdStory2);
        imgStory2.setPosition(0, 0);
        stage2.addActor(imgStory2);
        stage2.addActor(btnNext2);


        //Escena 3
        ImageButton btnNext3 = new ImageButton(trdNext1,trdNextPress1);
        btnNext3.setPosition(WIDTH - 3*btnNext3.getWidth()/2, HEIGHT/2 - btnNext3.getHeight()/2);

        btnNext3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.FOUR);

            }
        });


        TextureRegionDrawable trdStory3 = new TextureRegionDrawable(new TextureRegion(story3));
        Image imgStory3 = new Image(trdStory3);
        imgStory3.setPosition(0, 0);
        stage3.addActor(imgStory3);
        stage3.addActor(btnNext3);

        //Escena 4
        ImageButton btnNext4 = new ImageButton(trdNext1,trdNextPress1);
        btnNext4.setPosition(WIDTH - 3*btnNext4.getWidth()/2, HEIGHT/2 - btnNext4.getHeight()/2);

        btnNext4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.FIVE);

            }
        });

        TextureRegionDrawable trdStory4 = new TextureRegionDrawable(new TextureRegion(story4));
        Image imgStory4 = new Image(trdStory4);
        imgStory4.setPosition(0, 0);
        stage4.addActor(imgStory4);
        stage4.addActor(btnNext4);


        //Escena 5
        ImageButton btnNext5 = new ImageButton(trdNext1,trdNextPress1);
        btnNext5.setPosition(WIDTH - 3*btnNext5.getWidth()/2, HEIGHT/2 - btnNext5.getHeight()/2);

        btnNext5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.SIX);

            }
        });

        TextureRegionDrawable trdStory5 = new TextureRegionDrawable(new TextureRegion(story5));
        Image imgStory5 = new Image(trdStory5);
        imgStory5.setPosition(0, 0);
        stage5.addActor(imgStory5);
        stage5.addActor(btnNext5);

        //Escena 6
        ImageButton btnNext6 = new ImageButton(trdNext1,trdNextPress1);
        btnNext6.setPosition(WIDTH - 3*btnNext6.getWidth()/2, HEIGHT/2 - btnNext6.getHeight()/2);

        btnNext4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.SEVEN);

            }
        });

        TextureRegionDrawable trdStory6 = new TextureRegionDrawable(new TextureRegion(story6));
        Image imgStory6 = new Image(trdStory6);
        imgStory6.setPosition(0, 0);
        stage6.addActor(imgStory6);
        stage6.addActor(btnNext6);

        //Escena 7
        ImageButton btnNext7 = new ImageButton(trdNext1,trdNextPress1);
        btnNext7.setPosition(WIDTH - 3*btnNext7.getWidth()/2, HEIGHT/2 - btnNext7.getHeight()/2);

        btnNext7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Stages.EIGHT);

            }
        });

        TextureRegionDrawable trdStory7 = new TextureRegionDrawable(new TextureRegion(story7));
        Image imgStory7 = new Image(trdStory7);
        imgStory7.setPosition(0, 0);
        stage7.addActor(imgStory7);
        stage7.addActor(btnNext7);

        //Escena 8

        TextureRegionDrawable trdStory8 = new TextureRegionDrawable(new TextureRegion(story8));
        Image imgStory8 = new Image(trdStory8);
        imgStory8.setPosition(0, 0);
        stage8.addActor(imgStory8);
        stage8.addActor(btnBack);
    }

    private void loadScenes() {
        stage1 = new Stage(view);

        stage2 = new Stage(view);

        stage3 = new Stage(view);

        stage4 = new Stage(view);

        stage5 = new Stage(view);

        stage6 = new Stage(view);

        stage7 = new Stage(view);

        stage8 = new Stage(view);
    }



    private void loadTextures() {
        story1 = assetManager.get("story/1.png");
        story2 = assetManager.get("story/2.png");
        story3 = assetManager.get("story/3.png");
        story4 = assetManager.get("story/4.png");
        story5 = assetManager.get("story/5.png");
        story6 = assetManager.get("story/6.png");
        story7 = assetManager.get("story/7.png");
        story8 = assetManager.get("story/8.png");

    }

    @Override
    public void render(float delta) {
        clearScreen();
        //Projection matrix
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
        currentStage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {game.setScreen(new LoadingScreen(game, MENU));}

    }


    private void changeScene(Stages stage) {
        switch (stage)
        {
            case ONE:
                this.stage = Stages.ONE;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage1;
                break;
            case TWO:
                this.stage = Stages.TWO;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage2;
                break;
            case THREE:
                this.stage = Stages.THREE;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage3;
                break;
            case FOUR:
                this.stage = Stages.FOUR;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage4;
                break;
            case FIVE:
                this.stage = Stages.FIVE;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage5;
                break;
            case SIX:
                this.stage = Stages.SIX;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage6;
                break;
            case SEVEN:
                this.stage = Stages.SEVEN;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage7;
                break;
            case EIGHT:
                this.stage = Stages.EIGHT;
                Gdx.input.setInputProcessor(currentStage);
                currentStage = stage8;
                break;
        }

    }

    @Override
    public void dispose() {
        assetManager.unload("story/1.png");
        assetManager.unload("story/2.png");
        assetManager.unload("story/3.png");
        assetManager.unload("story/4.png");
        assetManager.unload("story/5.png");
        assetManager.unload("story/6.png");
        assetManager.unload("story/7.png");
        assetManager.unload("story/8.png");
        assetManager.unload("btn/backdarkbtn.png");
        assetManager.unload("btn/backdarkbtnpress.png");
        assetManager.unload("btn/nextbtn.png");
        assetManager.unload("btn/nextbtnpress.png");
    }

    private enum Stages
    {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT
    }
}
