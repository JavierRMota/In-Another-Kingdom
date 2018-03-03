package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by mota on 2/13/18.
 */

public class Text {
    private BitmapFont font;
    public Text()
    {
        font = new BitmapFont(Gdx.files.internal("fonts/oldEngFont.fnt"));
        font.setColor(225/255f,213/255f,164/255f,1);
    }
    public void showMsg(SpriteBatch batch, String msg, float x, float y, float size)
    {
        font.getData().setScale(size);
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, msg);

        float textWidth = glyph.width;
        font.draw(batch, glyph,x-textWidth/2, y);


    }
}
