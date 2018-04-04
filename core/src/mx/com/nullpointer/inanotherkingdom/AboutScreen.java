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
 * Created by MarinaHaro on 12/02/18.
 */

class AboutScreen extends GenericScreen {

    //Escena para la pantalla Acercade
    private Stage aboutStage;


    //Texturas
    private Texture backgroundTexture;
    private Texture bannerTexture;
    private Texture bere, charly, eli, javier, marina;
    private Text msg;


    public AboutScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        loadTextures();
        createObjects();

    }

    private void loadTextures() {
        backgroundTexture = assetManager.get("background/menubg.png");
        bannerTexture = assetManager.get("screens/avatarBanner.png");
        bere = assetManager.get("screens/avatarBere.png");
        charly = assetManager.get("screens/avatarCharly.png");
        eli = assetManager.get("screens/avatarEli.png");
        javier = assetManager.get("screens/avatarJavier.png");
        marina = assetManager.get("screens/avatarMarina.png");
        msg = new Text();

    }

    private void createObjects() {
        aboutStage = new Stage(view);

        //Back
        Texture backbtn = assetManager.get("btn/backbtn.png");
        Texture backbtnpress = assetManager.get("btn/backbtnpress.png");
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backbtn));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(backbtnpress));
        ImageButton btnBack = new ImageButton(trdBack,trdBackPress);
        btnBack.setPosition(btnBack.getWidth()/2, HEIGHT /2 - btnBack.getHeight()/2);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen(game, MENU));

            }
        });
        aboutStage.addActor(btnBack);

        //Botón Rate
        TextureRegionDrawable trdRate = new TextureRegionDrawable(new TextureRegion(new Texture("btn/rate.png")));
        TextureRegionDrawable trdRatePress = new TextureRegionDrawable(new TextureRegion(new Texture("btn/rate.png")));
        ImageButton btnRate = new ImageButton(trdRate,trdRatePress);
        btnRate.setPosition(MenuScreen.WIDTH /2 - btnRate.getWidth()/2, 25);

        btnRate.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                Gdx.net.openURI("http://nullpointer.com.mx");
            }
        });
        aboutStage.addActor(btnRate);

        //Git
        Texture gitbtn = assetManager.get("btn/gitbtn.png");
        Texture gitbtnpress = assetManager.get("btn/gitbtnpress.png");
        TextureRegionDrawable trdGit = new TextureRegionDrawable(new TextureRegion(gitbtn));
        TextureRegionDrawable trdGitPress = new TextureRegionDrawable(new TextureRegion(gitbtnpress));
        ImageButton btnGit = new ImageButton(trdGit,trdGitPress);
        btnGit.setPosition(WIDTH/2-btnGit.getWidth()/2, 2.5f*HEIGHT/16 - btnGit.getHeight()/16);
        btnGit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                Gdx.net.openURI("https://github.com/JavierRMota/In-Another-Kingdom/");

            }
        });
        aboutStage.addActor(btnGit);
        Gdx.input.setInputProcessor(aboutStage);

    }


    @Override
    public void render(float delta) {
        clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        //Intro
        msg.showMsg(batch,"About", WIDTH /2,7* HEIGHT /8,2,'c');
        msg.showMsg(batch,"Made by: ",WIDTH/2,3*HEIGHT/4,1,'c');

        //Bere
        batch.draw(bannerTexture,WIDTH/2-bannerTexture.getWidth()/2,HEIGHT/2-bannerTexture.getHeight()/2);
        batch.draw(bere, WIDTH/2-bannerTexture.getWidth()/2+bere.getWidth()/16,HEIGHT/2-2*bere.getHeight()/5);
        msg.showMsg(batch,"Bere",WIDTH/2-bannerTexture.getWidth()/2+bere.getWidth()/2,HEIGHT/2-5*bannerTexture.getHeight()/16,1,'c');

        //Charly
        batch.draw(charly, WIDTH/2-bannerTexture.getWidth()/2+charly.getWidth()+2*charly.getWidth()/16,HEIGHT/2-2*bere.getHeight()/5);
        msg.showMsg(batch,"Charly",WIDTH/2-bannerTexture.getWidth()/2+1.6f*charly.getWidth(),HEIGHT/2-5*bannerTexture.getHeight()/16,1,'c');

        //Eli
        batch.draw(eli, WIDTH/2-bannerTexture.getWidth()/2+2*eli.getWidth()+3*eli.getWidth()/16,HEIGHT/2-2*bere.getHeight()/5);
        msg.showMsg(batch,"Eli",WIDTH/2-bannerTexture.getWidth()/2+2.7f*eli.getWidth(),HEIGHT/2-5*bannerTexture.getHeight()/16,1,'c');

        //Javier
        batch.draw(javier, WIDTH/2-bannerTexture.getWidth()/2+3*javier.getWidth()+4*javier.getWidth()/16,HEIGHT/2-2*bere.getHeight()/5);
        msg.showMsg(batch,"Javier",WIDTH/2-bannerTexture.getWidth()/2+3.8f*javier.getWidth(),HEIGHT/2-5*bannerTexture.getHeight()/16,1,'c');

        //Marina
        batch.draw(marina, WIDTH/2-bannerTexture.getWidth()/2+4*marina.getWidth()+5*marina.getWidth()/16,HEIGHT/2-2*bere.getHeight()/5);
        msg.showMsg(batch,"Marina",WIDTH/2-bannerTexture.getWidth()/2+4.9f*marina.getWidth(),HEIGHT/2-5*bannerTexture.getHeight()/16,1,'c');

        batch.end();

        //Buttons
        aboutStage.draw();


    }
    @Override
    public void dispose()
    {
        assetManager.unload("music/menu.mp3");
        assetManager.unload("btn/backbtn.png");
        assetManager.unload("btn/backbtnpress.png");
        assetManager.unload("btn/gitbtn.png");
        assetManager.unload("btn/gitbtnpress.png");
        assetManager.unload("screens/avatarBanner.png");
        assetManager.unload("screens/avatarBere.png");
        assetManager.unload("screens/avatarCharly.png");
        assetManager.unload("screens/avatarEli.png");
        assetManager.unload("screens/avatarJavier.png");
        assetManager.unload("screens/avatarMarina.png");
        assetManager.unload("background/menubg.png");
    }
}
