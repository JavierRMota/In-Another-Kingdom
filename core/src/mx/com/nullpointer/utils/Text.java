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
    }
    public void showMsg(SpriteBatch batch, String msg, float x, float y)
    {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, msg);
        float textWidth = glyph.width;

        font.draw(batch, glyph,x-textWidth/2, y);


    }
}
