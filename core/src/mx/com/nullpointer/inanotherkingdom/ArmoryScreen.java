package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mx.com.nullpointer.utils.GenericScreen;
import mx.com.nullpointer.utils.Text;

/**
 * Created by Carlos Carbajal on 06-feb-18.
 */

class ArmoryScreen extends GenericScreen {

    //Texturas
    private Texture backgroundTexture;

    private Stage armoryStage;
    private Text msg;


    public ArmoryScreen(Main game)
    {
       super(game);
    }

    @Override
    public void show() {
        cargarTexturas();
        crearArsenal();
    }

    private void cargarTexturas() {
        backgroundTexture = new Texture("background/menubg.png");
        msg = new Text();

    }

    private void crearArsenal() {
        armoryStage = new Stage(view);

        //Botón Back
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtn.png")));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/backbtnpress.png")));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()*2, MenuScreen.HEIGHT /2 - btnBack.getHeight()/2);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MenuScreen(game));

            }
        });
        armoryStage.addActor(btnBack);
        Gdx.input.setInputProcessor(armoryStage);
    }

    @Override
    public void render(float delta) {
        clearScreen(25/255f,158/255f,218/255f);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        msg.showMsg(batch,"Arsenal", WIDTH /2,3* HEIGHT /4,2);
        msg.showMsg(batch,"Proximamente", WIDTH /2, HEIGHT /2,1);
        batch.end();
        armoryStage.draw();
    }

}
