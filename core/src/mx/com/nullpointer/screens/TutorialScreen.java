package mx.com.nullpointer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.com.nullpointer.inanotherkingdom.Main;

/**
 * Created by MarinaHaro on 19/04/18.
 */

public class TutorialScreen extends GenericScreen {
    
    private Texture backgroundTexture;

    private Stage tutorialStage;

    public TutorialScreen(Main game)
    {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        /*if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
                pause();
            }*/

        backgroundTexture = assetManager.get("background/howTo.png");
        Gdx.input.setInputProcessor(tutorialStage);

        tutorialStage = new Stage(view);


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
        tutorialStage.addActor(btnBack);


    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        batch.end();
        tutorialStage.draw();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {game.setScreen(new LoadingScreen(game, MENU));}

    }

    @Override
    public void dispose() {
        assetManager.unload("background/howTo.png");
    }
}
