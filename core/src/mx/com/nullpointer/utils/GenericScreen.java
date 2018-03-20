package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.Main;

/**
 * Created by mota on 2/15/18.
 */

public abstract class GenericScreen implements Screen
{

        //Dimensiones
        public static final float ANCHO = 1280;
        public static final float ALTO = 720;

        //Cámara
        protected OrthographicCamera camera;
        protected Viewport view;

        //Batch
        protected SpriteBatch batch;

        public GenericScreen()
        {
            camera = new OrthographicCamera(ANCHO, ALTO);
            camera.position.set(ANCHO / 2, ALTO / 2, 0);
            camera.update();
            view = new StretchViewport(ANCHO, ALTO, camera);
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
