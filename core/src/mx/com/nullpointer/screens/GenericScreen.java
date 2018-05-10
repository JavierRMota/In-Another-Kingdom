package mx.com.nullpointer.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.com.nullpointer.inanotherkingdom.Main;

/**
 * Created by mota on 2/15/18.
 */

public abstract class GenericScreen implements Screen
{
        protected final Main game;
        protected final AssetManager assetManager;

        //Dimensiones
        public static final float WIDTH = 1280;
        public static final float HEIGHT = 720;
        public static final int MENU = 0, SETTINGS = 1, LEVELS = 2, ABOUT = 3, TUTORIAL = 4, LVLZERO = 5, LVLONE = 6, LVLTWO = 7, LVLTHREE = 8,LVLFOUR =9, LVLFIVE =10, LVLSIX = 11, LVLSEVEN = 12, LVLEIGHT = 13, STORY = 21;

        //Cámara
        protected OrthographicCamera camera;
        protected Viewport view;

        //Batch
        protected SpriteBatch batch;

        public GenericScreen(Main game)
        {
            this.game=game;
            this.assetManager = game.getAssetManager();
            camera = new OrthographicCamera(WIDTH, HEIGHT);
            camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
            camera.update();
            view = new FitViewport(WIDTH, HEIGHT, camera);
            batch = new SpriteBatch();
        }
        //Black
        public void clearScreen()
        {
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        }

        //Specific Color
        public void clearScreen(float red, float green, float blue)
        {
            Gdx.gl.glClearColor(red,green,blue,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        @Override
        public void show() {

        }


        @Override
        public void render(float delta) {

        }

        @Override
        public void resize(int width, int height) {
            view.update(width,height);

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {
            dispose();
        }

        @Override
        public void dispose() {

        }
}
