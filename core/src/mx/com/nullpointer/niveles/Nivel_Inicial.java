package mx.com.nullpointer.niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.com.nullpointer.inanotherkingdom.Main;

import mx.com.nullpointer.inanotherkingdom.PantallaMenu;
import mx.com.nullpointer.utils.GenericScreen;

import mx.com.nullpointer.utils.GestureController;
import mx.com.nullpointer.utils.MainCharacter;
import mx.com.nullpointer.utils.MusicController;

/**
 * Created by mota on 2/12/18.
 */

public class Nivel_Inicial extends GenericScreen {
    private final Main game;

    //Mapas
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer render;
    private MainCharacter laurence;
    private static final float ANCHO_MAPA = 3840;

    //Cámaras
    private OrthographicCamera cameraHUD;
    private Viewport viewHUD;
    private Stage buttonScene;

    //Textura del mapa
    private Texture background = new Texture("fondoN1Temp.png");


    public Nivel_Inicial(Main game){this.game =game;}
    @Override
    public void show() {
        createHUD();
        loadMap();
        laurence = new MainCharacter(
                new Texture("characters/laurence_descanso.png"),
                new Texture("characters/laurence_running.png"),
                new Texture("characters/tira_salto.png"),
                new Texture("characters/tira_marometa.png"));
        MusicController.stopMusic();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        GestureController gestureDetector = new GestureController(new GestureController.DirectionListener() {

            @Override
            public void onUp() {
                if(laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                        || laurence.getMovementState() == MainCharacter.MovementState.STANDING)
                    laurence.setMovementState(MainCharacter.MovementState.JUMPING_PREPARE);
            }

            @Override
            public void onRight() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLeft() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDown() {
                if(laurence.getMovementState()== MainCharacter.MovementState.RUNNING
                        || laurence.getMovementState() == MainCharacter.MovementState.STANDING)
                    laurence.setMovementState(MainCharacter.MovementState.DODGING);

            }
        });
        inputMultiplexer.addProcessor(gestureDetector);
        buttonScene = new Stage(viewHUD);
        //Botón Play
        TextureRegionDrawable trdPause = new TextureRegionDrawable(new TextureRegion(new Texture("btn/pausebtn.png")));
        TextureRegionDrawable trdPausePress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/pausebtnpress.png")));

        ImageButton btnPause = new ImageButton(trdPause,trdPausePress);
        btnPause.setPosition(ANCHO/2 - btnPause.getWidth()/2, ALTO - 1.5f*btnPause.getHeight());

        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new PantallaMenu(game));
            }
        });
        buttonScene.addActor(btnPause);
        inputMultiplexer.addProcessor(buttonScene);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    private void loadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("map/nivelCero.tmx", TiledMap.class);
        manager.finishLoading();

        tiledMap = manager.get("map/nivelCero.tmx");
        render = new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void createHUD() {
        cameraHUD = new OrthographicCamera(ANCHO,ALTO);
        cameraHUD.position.set(ANCHO/2,ALTO/2,0);
        cameraHUD.update();
        viewHUD = new StretchViewport(ANCHO,ALTO,cameraHUD);

    }

    @Override
    public void render(float delta) {

        updateAllCameras();


        //Borrar pantalla
        clearScreen();

        //Projection matrix
        batch.setProjectionMatrix(camera.combined);
        render.setView(camera);
        render.render();

        batch.begin();
        //batch.draw(background,0,0);
        laurence.render(batch);
        batch.end();

        batch.setProjectionMatrix(cameraHUD.combined);
        batch.begin();
        buttonScene.draw();
        batch.end();




    }


    private void updateAllCameras() {
        //Para que siga a Laurence
        float posX = laurence.getX();
        // Primera mitad de la pantalla
        if (posX < ANCHO/2 ) {
            camera.position.set(ANCHO/2, ALTO/2, 0);
        } else if (posX > ANCHO_MAPA - ANCHO/2) {   // Última mitad de la pantalla
            camera.position.set(ANCHO_MAPA-ANCHO/2,camera.position.y,0);
        } else {    // En 'medio' del mapa
            camera.position.set(posX,camera.position.y,0);
        }
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
    viewHUD.update(width,height);
    view.update(width,height);

    }
    @Override
    public void dispose()
    {
        buttonScene.dispose();
    }


}
