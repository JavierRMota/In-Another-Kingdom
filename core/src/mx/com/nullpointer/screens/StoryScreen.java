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

    private Stage storyStage;
    private Stage continueStage;

    private Stage currentStage;

    private int part;

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

        currentStage = storyStage;
        Gdx.input.setInputProcessor(currentStage);
        part=1;

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
                game.setScreen(new LoadingScreen(game,LEVELS));

            }
        });

        //Escena1
        Texture nextTexture = assetManager.get("btn/nextbtn.png");
        Texture nextPressTexture = assetManager.get("btn/nextbtnpress.png");

        TextureRegionDrawable trdNext1 = new TextureRegionDrawable(new TextureRegion(nextTexture));
        TextureRegionDrawable trdNextPress1 = new TextureRegionDrawable(new TextureRegion(nextPressTexture));
        ImageButton btnNext1 = new ImageButton(trdNext1,trdNextPress1);
        btnNext1.setPosition(WIDTH - btnNext1.getWidth(), HEIGHT/2 - btnNext1.getHeight()/2);

        btnNext1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                part+=1;
                if(part>=8)
                {
                    currentStage = continueStage;
                    Gdx.input.setInputProcessor(currentStage);

                }

            }
        });
        storyStage.addActor(btnNext1);


        //Escena 8
        continueStage.addActor(btnBack);
    }

    private void loadScenes() {
        continueStage = new Stage(view);

        storyStage = new Stage(view);

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
        switch (part)
        {
            case 1:
                batch.draw(story1,0,0);
                break;
            case 2:
                batch.draw(story2,0,0);
                break;
            case 3:
                batch.draw(story3,0,0);
                break;
            case 4:
                batch.draw(story4,0,0);
                break;
            case 5:
                batch.draw(story5,0,0);
                break;
            case 6:
                batch.draw(story6,0,0);
                break;
            case 7:
                batch.draw(story7,0,0);
                break;
            case 8:
                batch.draw(story8,0,0);
                break;
        }
        batch.end();
        currentStage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {game.setScreen(new LoadingScreen(game, MENU));}

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


}
