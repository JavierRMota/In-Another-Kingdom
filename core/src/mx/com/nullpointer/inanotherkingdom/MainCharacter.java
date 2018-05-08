package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import mx.com.nullpointer.screens.GenericScreen;

/**
 * Created by mota on 2/13/18.
 */

public class MainCharacter extends GameObject
{
    private Animation runningAnimation,jumpingAnimation,dodgingAnimation, attackingAnimation, airAttackAnimation;
    private float timerRunning, timerAction, timerSecondaryAction;
    private float x,y; //Coordenadas de dónde se moverá
    private Preferences prefs = Gdx.app.getPreferences("Settings");
    private float VY = 20, G=30;
    private int VX = prefs.getInteger("Difficulty",400);
    private float animationSpeed = 0.04f;

    //Estados de movimiento
    MovementState movementState = MovementState.RUNNING;


    public MainCharacter(Texture standingTexture,Texture runningTexture,Texture jumpingTexture, Texture dodgingTexture, Texture attackingTexture, Texture airAttackTexture)
    {
        //Cargamos el salto
        switch (prefs.getInteger("Difficulty",400))
        {
            case 300:
                VY = 25;
                break;
            case 500:
                animationSpeed = 0.03f;
                break;
        }

        //Cargamos las animaciones

        //Animación de correr
        TextureRegion region = new TextureRegion(runningTexture);
        //Tamaño
        TextureRegion[][] characterTexture = region.split(198,114);
        runningAnimation = new Animation(animationSpeed,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //Animación de saltar
        region = new TextureRegion(jumpingTexture);
        //Tamaño
        characterTexture = region.split(105,115);
        /*jumpingAnimation = new Animation(animationSpeed,
                characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11],
                characterTexture[0][12],characterTexture[0][13],characterTexture[0][14],characterTexture[0][15]);*/
        jumpingAnimation = new Animation(animationSpeed,
                characterTexture[0][0], characterTexture[0][1],characterTexture[0][2],characterTexture[0][3],characterTexture[0][4]);
        jumpingAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        //Animación de agacharse
        region = new TextureRegion(dodgingTexture);
        //Tamaño
        characterTexture = region.split(120,127);
        dodgingAnimation = new Animation(animationSpeed,
                characterTexture[0][1],characterTexture[0][2], characterTexture[0][3],
                characterTexture[0][4],characterTexture[0][5],characterTexture[0][6],characterTexture[0][7],
                characterTexture[0][8],characterTexture[0][9],characterTexture[0][10],characterTexture[0][11]);
        dodgingAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        //Animación de atacar
        region = new TextureRegion(attackingTexture);
        //Tamaño
        characterTexture = region.split(175,155);
        attackingAnimation = new Animation(animationSpeed,
                characterTexture[0][0], characterTexture[0][1],characterTexture[0][2],
                characterTexture[0][3], characterTexture[0][4],characterTexture[0][5],
                characterTexture[0][6],characterTexture[0][7],characterTexture[0][8]);
        attackingAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        //Animación de atacar aerea
        region = new TextureRegion(airAttackTexture);
        characterTexture = region.split(140,155);
        airAttackAnimation = new Animation(animationSpeed,
                characterTexture[0][0], characterTexture[0][1],characterTexture[0][2],
                characterTexture[0][3], characterTexture[0][4],characterTexture[0][5],
                characterTexture[0][6]
                );


        //Inicializamos timers
        timerRunning = 0;
        timerAction = 0;
        timerSecondaryAction = 0;

        // Quieto
        sprite = new Sprite(standingTexture);
        sprite.setPosition(0,64);


        //Posición
        x = GenericScreen.WIDTH /8;
        y = 70;
    }

    public  int getVelocity() {
        return VX;
    }

    public void render(SpriteBatch batch) {
        switch (movementState)
        {
            case RUNNING:
                run(batch);
                if(timerAction!=0)
                    timerAction=0;
                break;
            case FALLING:
            case JUMPING:
            case JUMPING_END:
                jump(batch);
                if(timerRunning!=0)
                    timerRunning=0;
                break;
            case AIR_ATTACKING:
                airAttack(batch);
                if(timerRunning!=0)
                    timerRunning=0;
                break;
            case ATTACKING:
                attack(batch);
                if(timerRunning!=0)
                    timerRunning=0;
                break;
            case DODGING:
                dodge(batch);
                if(timerRunning!=0)
                    timerRunning=0;
                break;
            case STANDING:
                timerRunning=0;
                timerAction=0;
                this.draw(batch);
        }

    }
    //Getters
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    //Setters
    public void setMovementState(MovementState movement)
    {
        this.movementState = movement;
    }
    public void setX(float x)
    {
        this.x=x;
    }
    public void setY(float y)
    {
        this.y =y;
    }

    //Complex function for moving, recieves the layer, the time and the two squares
    public  void move(TiledMapTileLayer layer, float delta, int cx,int cy)
    {

        TiledMapTileLayer.Cell currentCellDown = layer.getCell(cx,cy-1);
        TiledMapTileLayer.Cell currentCellUp = layer.getCell(cx,cy+2);
        if(movementState == MovementState.JUMPING && currentCellUp!=null
               && currentCellUp.getTile().getProperties().get("type").equals("platform")
        )
        {
            this.movementState=MovementState.FALLING;
            timerAction=2*VY/G;

        }
        else if(movementState == MovementState.JUMPING)
        {
            this.y+=timerAction*VY-0.5*G*timerAction*timerAction;

        }

        //Si hay suelo abajo
        if(currentCellDown !=null
               && currentCellDown.getTile().getProperties().get("type").equals("platform")
                )
        {
            if(movementState == MovementState.FALLING || movementState == MovementState.AIR_ATTACKING)
            {
                movementState = MovementState.JUMPING_END;
                timerAction= animationSpeed*3;
                VY=20;
                this.y = (cy)*70;
            }
        }
        //Si  no es cualquiera de los puede caer
        else if(this.movementState != MovementState.JUMPING && this.movementState != MovementState.FALLING && this.movementState != MovementState.AIR_ATTACKING)
        {
            this.movementState = MovementState.FALLING;
            timerAction=2*VY/G;
        }


        if(this.movementState==MovementState.FALLING || this.movementState == MovementState.AIR_ATTACKING)
        {
            this.y+=timerAction*VY-0.5*G*timerAction*timerAction;
        }


        if(canMove(layer,cx,cy))
        {
            this.x+=VX*delta;
        }

        //Al final ponemos el sprite donde va
        sprite.setPosition(x, y);
    }
    //Boolean to know if there is a block in front
    public boolean canMove(TiledMapTileLayer layer, int cx, int cy)
    {
        TiledMapTileLayer.Cell nextCell = layer.getCell(cx+1,cy);
        if(nextCell!= null && nextCell.getTile().getProperties().get("type").equals("platform"))
        {
            //this.VX = 300;
            return false;
        }
        //this.VX =400;
        return true;
    }
    public void run(SpriteBatch batch)
    {
        timerRunning += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) runningAnimation.getKeyFrame(timerRunning);
        batch.draw(region, x, y);
    }
    public void jump(SpriteBatch batch)
    {
        timerAction+=Gdx.graphics.getDeltaTime()*1.4f;
        if (VY*timerAction-timerAction*G*0.5*timerAction<0  && movementState!=MovementState.FALLING && movementState!=MovementState.JUMPING_END)
        {
            movementState= MovementState.FALLING;
        }
        if(movementState== MovementState.FALLING)
        {
            TextureRegion region = (TextureRegion) jumpingAnimation.getKeyFrame(animationSpeed*4);
            batch.draw(region,x,y);

        }
        else
        {
            if(movementState== MovementState.JUMPING_END)
            {
                movementState =MovementState.RUNNING;
                timerAction=0;
                timerRunning=0;
            }
            TextureRegion region;
            if(timerAction>=animationSpeed*4 && movementState == MovementState.JUMPING)
                region = (TextureRegion) jumpingAnimation.getKeyFrame(animationSpeed*4);
            else
                region = (TextureRegion) jumpingAnimation.getKeyFrame(timerAction);
            batch.draw(region,x,y);
        }

    }
    public void airAttack(SpriteBatch batch)
    {
        timerSecondaryAction +=Gdx.graphics.getDeltaTime();
        timerAction+=Gdx.graphics.getDeltaTime()*1.4f;
        if (timerSecondaryAction >animationSpeed*6)
        {
            movementState= MovementState.FALLING;
        }
        else {
            TextureRegion region = (TextureRegion) airAttackAnimation.getKeyFrame(timerSecondaryAction);
            batch.draw(region,x,y);
        }
    }
   public void dodge(SpriteBatch batch)
    {
        timerAction += Gdx.graphics.getDeltaTime();
        if(timerAction>=animationSpeed*12)
        {
            movementState = MovementState.RUNNING;
            timerAction=0;
            timerRunning=0;
        }
        TextureRegion region = (TextureRegion) dodgingAnimation.getKeyFrame(timerAction);
        batch.draw(region, x, y);
    }
    public void attack(SpriteBatch batch)
    {
        timerAction += Gdx.graphics.getDeltaTime();
        if(timerAction>=animationSpeed*9)
        {
            movementState = MovementState.RUNNING;
            timerAction=0;
            timerRunning=0;
            this.x-=18;
        }
        TextureRegion region = (TextureRegion) attackingAnimation.getKeyFrame(timerAction);
        batch.draw(region, x, y);
    }

    public MovementState getMovementState() {
        return movementState;
    }
    public void resetTimerAction()
    {
        timerAction=0;
    }

    public void quickFall() {
        VY=0;
    }

    public void setTimerAction(float timerAction) {
        this.timerAction = timerAction;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void resetSecondaryActionTimer() {
        timerSecondaryAction =0;
    }

    public void setVelocity(int vx) {
        this.VX = vx;
    }

    public enum MovementState
    {
        RUNNING,
        JUMPING,
        FALLING,
        JUMPING_END,
        DODGING,
        STANDING,
        ATTACKING,
        AIR_ATTACKING
    }





}
