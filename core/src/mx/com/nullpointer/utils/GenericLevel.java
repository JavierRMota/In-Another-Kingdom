package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.LoadingScreen;

/**
 * Created by mota on 3/22/18.
 */

public abstract class GenericLevel extends GenericScreen {
    //Maps
    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer render;

    //Character
    protected MainCharacter laurence;

    //Scores
    protected int coins,keys;
    protected String coinScore;
    protected Text  scoreDisplay;
    protected boolean recolectedKeys[];
    protected int middleKey;

    //Textures
    protected Texture coinTexture;
    protected Texture emptyKeyTexture;
    protected Texture fullKeyTexture;

    //Sprites
    protected Sprite backgroundOne;
    protected Sprite backgroundTwo;
    protected Sprite cloudsOne;
    protected Sprite cloudsTwo;


    //Camera and scene
    protected OrthographicCamera cameraHUD;
    protected Viewport viewHUD;
    protected Stage buttonScene;
    protected Stage pauseScene;
    protected InputProcessor inputProcessor;

    //Controlador de juego
    protected GameState gameState;

    //Create STATIC Camera
    protected void createHUD() {
        cameraHUD = new OrthographicCamera(WIDTH, HEIGHT);
        cameraHUD.position.set(WIDTH /2, HEIGHT /2,0);
        cameraHUD.update();
        viewHUD = new StretchViewport(WIDTH, HEIGHT,cameraHUD);

    }

    //Method to decide if the character wins or looses (depends on the level)
    protected abstract void winOrLoose();

    //Updating scores
    protected void updateScore() {
        coinScore = String.format("%02d", coins);
    }

    //Change proportion of the views
    @Override
    public void resize(int width, int height) {
        viewHUD.update(width,height);
        view.update(width,height);
    }

    //What we do when we press pause
    @Override
    public void pause() {
        gameState=GameState.PAUSE;
        Gdx.input.setInputProcessor(pauseScene);
        //laurence.setMovementState(MainCharacter.MovementState.STANDING);

    }

    //What we do when we resume the game
    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputProcessor);
        gameState = GameState.PLAY;
        //laurence.setMovementState(MainCharacter.MovementState.RUNNING);

    }


}
