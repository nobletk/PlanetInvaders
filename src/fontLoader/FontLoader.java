package fontLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    public static Font loadFont(String path, float size) {
        Font font = null;
        try {
            InputStream is = FontLoader.class.getResourceAsStream(path);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return font;
    }
}
