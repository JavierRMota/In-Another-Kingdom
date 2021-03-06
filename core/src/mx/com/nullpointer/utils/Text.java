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
    public Text(float r, float g, float b)
    {
        font = new BitmapFont(Gdx.files.internal("fonts/oldEngFont.fnt"));
        font.setColor(r,g,b,1);

    }
    public void showMsg(SpriteBatch batch, String msg, float x, float y, float size, char alignment)
    {
        font.getData().setScale(size);
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, msg);

        float textWidth = glyph.width;
        switch (alignment)
        {
            case 'r':
                font.draw(batch, glyph,x-textWidth, y);
                break;
            case 'c':
                font.draw(batch, glyph,x-textWidth/2, y);
                break;
            case 'l':
                font.draw(batch, glyph,x, y);
                break;
        }

    }
}
