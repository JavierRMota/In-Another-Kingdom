package mx.com.nullpointer.inanotherkingdom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Mummy extends Enemy {
    private int VELOCITY= -200;
    public Mummy(Texture texture, float x, float y)
    {
        //Region
        TextureRegion region = new TextureRegion(texture);
        //Size
        TextureRegion[][] characterTexture;
        characterTexture = region.split(88,133);
        //Animation
        animation = new Animation(0.2f,
                characterTexture[0][0],characterTexture[0][1],characterTexture[0][2]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation=0;

        this.x= x;
        this.y=y;
        sprite = new Sprite(characterTexture[0][0]);
        sprite.setPosition(this.x,this.y);
    }
    public void move(TiledMapTileLayer layer, float delta, int cx, int cy)
    {
        TiledMapTileLayer.Cell nextCellDown;
        TiledMapTileLayer.Cell nextCell;
        if(VELOCITY>0){
            nextCellDown= layer.getCell(cx+1,cy-1);
            nextCell= layer.getCell(cx+1,cy);
        }else{
            nextCellDown= layer.getCell(cx-1,cy-1);
            nextCell= layer.getCell(cx-1,cy);
        }
        if(nextCellDown != null && !nextCellDown.getTile().getProperties().get("type").equals("plataform") || nextCell != null && nextCell.getTile().getProperties().get("type").equals("plataform"))
        {
            VELOCITY=-VELOCITY;
        }
        this.x += delta*VELOCITY;

    }
}
