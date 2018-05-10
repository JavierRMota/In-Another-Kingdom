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
import mx.com.nullpointer.utils.Text;

/**
 * Created by MarinaHaro on 12/02/18.
 */

class AboutScreen extends GenericScreen {

    //Escena para la pantalla Acercade
    private Stage aboutStage, currentStage;
    private Stage informationStage;
    private Information scene;


    //Texturas
    private Texture backgroundTexture;
    private Texture bere, charly, eli, javier, marina;
    private Text msg;


    public AboutScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        loadTextures();
        createObjects();
        Gdx.input.setCatchBackKey(true);

    }

    private void loadTextures() {
        backgroundTexture = assetManager.get("background/menubg.png");
        bere = assetManager.get("screens/fotoBere.png");
        charly = assetManager.get("screens/fotoCharly.png");
        eli = assetManager.get("screens/fotoEli.png");
        javier = assetManager.get("screens/fotoJavier.png");
        marina = assetManager.get("screens/fotoMarina.png");
        msg = new Text();

    }

    private void createObjects() {
        aboutStage = new Stage(view);
        informationStage= new Stage(view);
        
        loadAbout();
        loadInformation();

        currentStage= aboutStage;
        Gdx.input.setInputProcessor(currentStage);
        scene = Information.ABOUT;


    }

    private void loadInformation() {
        //Boton back
        Texture backbtn = assetManager.get("btn/backbtn.png");
        Texture backbtnpress = assetManager.get("btn/backbtnpress.png");
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backbtn));
        TextureRegionDrawable trdBackPress = new TextureRegionDrawable(new TextureRegion(backbtnpress));
        ImageButton btnBackSub3 = new ImageButton(trdBack,trdBackPress);
        btnBackSub3.setPosition(btnBackSub3.getWidth()/2, HEIGHT /2 - btnBackSub3.getHeight()/2);

        btnBackSub3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Information.ABOUT);

            }
        });
        informationStage.addActor(btnBackSub3);
    }

    private void loadAbout() {
        Texture bereAvatar = assetManager.get("screens/avatarBere.png");
        Texture charlyAvatar = assetManager.get("screens/avatarCharly.png");
        Texture eliAvatar = assetManager.get("screens/avatarEli.png");
        Texture javierAvatar = assetManager.get("screens/avatarJavier.png");
        Texture marinaAvatar = assetManager.get("screens/avatarMarina.png");
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

        //Botón Contacto
        Texture contactTexture = assetManager.get("btn/contactbtn.png");
        Texture contactTexturePress = assetManager.get("btn/contactbtnpress.png");
        TextureRegionDrawable trdContact = new TextureRegionDrawable(new TextureRegion(contactTexture));
        TextureRegionDrawable trdContactPress = new TextureRegionDrawable(new TextureRegion(contactTexturePress));
        ImageButton btnContact = new ImageButton(trdContact,trdContactPress);
        btnContact.setPosition(WIDTH /2 - btnContact.getWidth()/2, 40);

        btnContact.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                Gdx.net.openURI("http://nullpointer.com.mx/contact.html");
            }
        });
        aboutStage.addActor(btnContact);

        //Botón ACKNOWLEDGMENTS
        Texture ackTexture = assetManager.get("btn/ackbtn.png");
        Texture ackTexturePress = assetManager.get("btn/ackbtnpress.png");
        TextureRegionDrawable trdAck = new TextureRegionDrawable(new TextureRegion(ackTexture));
        TextureRegionDrawable trdAckPress = new TextureRegionDrawable(new TextureRegion(ackTexturePress));
        ImageButton btnAck = new ImageButton(trdAck,trdAckPress);
        btnAck.setPosition(WIDTH /2 - btnAck.getWidth()/2, 50+btnContact.getHeight());

        btnAck.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Information.ACKNOWLEDGMENT);
            }
        });
        aboutStage.addActor(btnAck);

        //Botón Bere
        TextureRegionDrawable trdChar = new TextureRegionDrawable(new TextureRegion(bereAvatar));
        TextureRegionDrawable trdCharPress = new TextureRegionDrawable(new TextureRegion(bere));
        ImageButton btnBere = new ImageButton(trdChar,trdCharPress);
        btnBere.setPosition(WIDTH/4-btnBere.getWidth()/2,HEIGHT/2-btnBere.getHeight()/4);
        btnBere.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Information.BERE);
            }
        });
        aboutStage.addActor(btnBere);

        //Botón Charly
        trdChar = new TextureRegionDrawable(new TextureRegion(charlyAvatar));
        trdCharPress = new TextureRegionDrawable(new TextureRegion(charly));
        ImageButton btnCharly = new ImageButton(trdChar,trdCharPress);
        btnCharly.setPosition(3*WIDTH/8 - btnCharly.getWidth()/2,HEIGHT/2-btnCharly.getHeight()/4);
        btnCharly.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Information.CHARLY);
            }
        });
        aboutStage.addActor(btnCharly);



        //Botón Eli
        trdChar = new TextureRegionDrawable(new TextureRegion(eliAvatar));
        trdCharPress = new TextureRegionDrawable(new TextureRegion(eli));
        ImageButton btnEli = new ImageButton(trdChar,trdCharPress);
        btnEli.setPosition(4*WIDTH/8 - btnEli.getWidth()/2,HEIGHT/2-btnEli.getHeight()/4);
        btnEli.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Information.ELI);
            }
        });
        aboutStage.addActor(btnEli);

        //Botón Javier
        trdChar = new TextureRegionDrawable(new TextureRegion(javierAvatar));
        trdCharPress = new TextureRegionDrawable(new TextureRegion(javier));
        ImageButton btnJavier = new ImageButton(trdChar,trdCharPress);
        btnJavier.setPosition(5*WIDTH/8 - btnJavier.getWidth()/2,HEIGHT/2-btnJavier.getHeight()/4);
        btnJavier.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Information.JAVIER);
            }
        });
        aboutStage.addActor(btnJavier);

        //Botón Marina
        trdChar = new TextureRegionDrawable(new TextureRegion(marinaAvatar));
        trdCharPress = new TextureRegionDrawable(new TextureRegion(marina));
        ImageButton btnMarina = new ImageButton(trdChar,trdCharPress);
        btnMarina.setPosition(6*WIDTH/8 - btnMarina.getWidth()/2,HEIGHT/2-btnMarina.getHeight()/4);
        btnMarina.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                changeScene(Information.MARINA);
            }
        });
        aboutStage.addActor(btnMarina);

    }


    @Override
    public void render(float delta) {
        clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture,0 ,0);
        switch (scene)
        {
            case ABOUT:
                //Intro
                msg.showMsg(batch,"About", WIDTH /2,7* HEIGHT/8,2,'c');
                msg.showMsg(batch,"Made by: ",WIDTH/2,3*HEIGHT/4 +15,1,'c');
                //Bere
                msg.showMsg(batch,"Bere",WIDTH/4,3*HEIGHT/8,.8f,'c');
                //Charly
                msg.showMsg(batch,"Charly",3*WIDTH/8,3*HEIGHT/8,.8f,'c');
                //Eli
                msg.showMsg(batch,"Eli",4*WIDTH/8,3*HEIGHT/8, .8f,'c');
                //Javier
                msg.showMsg(batch,"Javier",5*WIDTH/8,3*HEIGHT/8,.8f,'c');
                //Marina
                msg.showMsg(batch,"Marina",6*WIDTH/8,3*HEIGHT/8,.8f,'c');
                //Title
                msg.showMsg(batch, "Proyecto de desarrollo de videojuegos", 255, HEIGHT-20, 1, 'c' );
                break;
            case ELI:
                //Information Eli
                batch.draw(eli,WIDTH/4-eli.getWidth()/2,3*HEIGHT/4-eli.getHeight());
                msg.showMsg(batch,"Elizabeth Badillo Gomez", 3*WIDTH /8,3*HEIGHT/4,2,'l');
                msg.showMsg(batch,"LAD", 3*WIDTH /8,5*HEIGHT/8,1,'l');
                msg.showMsg(batch,"Creative Director, character design and animations.", 3*WIDTH /8,HEIGHT/2,1,'l');
                break;
            case BERE:
                //Information Bere
                batch.draw(bere,WIDTH/4-eli.getWidth()/2,3*HEIGHT/4-eli.getHeight());
                msg.showMsg(batch,"Berenice Alamilla Montano", 3*WIDTH /8,3*HEIGHT/4,2,'l');
                msg.showMsg(batch,"LAD", 3*WIDTH /8,5*HEIGHT/8,1,'l');
                msg.showMsg(batch,"Background designer and .", 3*WIDTH /8,HEIGHT/2,1,'l');
                break;
            case CHARLY:
                //Information Charly
                batch.draw(charly,WIDTH/4-eli.getWidth()/2,3*HEIGHT/4-eli.getHeight());
                msg.showMsg(batch,"Carlos E. Carbajal Nogués", 3*WIDTH /8,3*HEIGHT/4,2,'l');
                msg.showMsg(batch,"ISC", 3*WIDTH /8,5*HEIGHT/8,1,'l');
                msg.showMsg(batch,"Backend settings and screen programmer.", 3*WIDTH /8,HEIGHT/2,1,'l');
                break;
            case MARINA:
                //Information Marina
                batch.draw(marina,WIDTH/4-eli.getWidth()/2,3*HEIGHT/4-eli.getHeight());
                msg.showMsg(batch,"Marina I. Haro Hernandez", 3*WIDTH /8,3*HEIGHT/4,2,'l');
                msg.showMsg(batch,"ISC", 3*WIDTH /8,5*HEIGHT/8,1,'l');
                msg.showMsg(batch,"Frontend screen programmer.", 3*WIDTH /8,HEIGHT/2,1,'l');
                break;
            case JAVIER:
                //Information Javier
                batch.draw(javier,WIDTH/4-eli.getWidth()/2,3*HEIGHT/4-eli.getHeight());
                msg.showMsg(batch,"Jose Javier Rodriguez Mota", 3*WIDTH /8,3*HEIGHT/4,2,'l');
                msg.showMsg(batch,"ISC", 3*WIDTH /8,5*HEIGHT/8,1,'l');
                msg.showMsg(batch,"Level designer and backend programmer.", 3*WIDTH /8,HEIGHT/2,1,'l');
                break;
            case ACKNOWLEDGMENT:
                //Information Acknowledgments
                msg.showMsg(batch,"Acknowledgment", WIDTH /2,3*HEIGHT/4,2,'c');
                msg.showMsg(batch,"Music: David Garcia German IMI", WIDTH /2,HEIGHT/2,0.8f,'c');
                msg.showMsg(batch,"Knowledge: Roberto Martinez Roman M.C.S.", WIDTH /2,7*HEIGHT/16,0.8f,'c');
                break;

        }

        batch.end();

        //Buttons
        currentStage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {game.setScreen(new LoadingScreen(game, MENU));}

    }
    public void changeScene(Information scene)
    {
        switch (scene)
        {
            case ABOUT:
                currentStage= aboutStage;
                Gdx.input.setInputProcessor(currentStage);
                this.scene = Information.ABOUT;
                break;
            case ELI:
                currentStage= informationStage;
                Gdx.input.setInputProcessor(currentStage);
                this.scene = Information.ELI;
                break;
            case BERE:
                currentStage= informationStage;
                Gdx.input.setInputProcessor(currentStage);
                this.scene = Information.BERE;
                break;
            case CHARLY:
                currentStage= informationStage;
                Gdx.input.setInputProcessor(currentStage);
                this.scene = Information.CHARLY;
                break;
            case MARINA:
                currentStage= informationStage;
                Gdx.input.setInputProcessor(currentStage);
                this.scene = Information.MARINA;
                break;
            case JAVIER:
                currentStage= informationStage;
                Gdx.input.setInputProcessor(currentStage);
                this.scene = Information.JAVIER;
                break;
            case ACKNOWLEDGMENT:
                currentStage= informationStage;
                Gdx.input.setInputProcessor(currentStage);
                this.scene = Information.ACKNOWLEDGMENT;
                break;
        }

    }

    @Override
    public void dispose()
    {
        assetManager.unload("background/menubg.png");
        assetManager.unload("music/menu.mp3");
        assetManager.unload("btn/backbtn.png");
        assetManager.unload("btn/backbtnpress.png");
        assetManager.unload("btn/ackbtn.png");
        assetManager.unload("btn/ackbtnpress.png");
        assetManager.unload("btn/contactbtn.png");
        assetManager.unload("btn/contactbtnpress.png");
        assetManager.unload("screens/avatarBere.png");
        assetManager.unload("screens/avatarCharly.png");
        assetManager.unload("screens/avatarEli.png");
        assetManager.unload("screens/avatarJavier.png");
        assetManager.unload("screens/avatarMarina.png");
        assetManager.unload("screens/fotoEli.png");
        assetManager.unload("screens/fotoBere.png");
        assetManager.unload("screens/fotoCharly.png");
        assetManager.unload("screens/fotoJavier.png");
        assetManager.unload("screens/fotoMarina.png");

    }
    private enum Information{ ABOUT, ACKNOWLEDGMENT, BERE, CHARLY, ELI, JAVIER, MARINA}
}
