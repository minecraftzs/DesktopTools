package zs.DesktopTools.managers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class filesManager {
    public static void saveImage(BufferedImage saveImage) {
        try {
            File dir = new File(settingsManager.getSetting("screenrecordPath"));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH时mm分ss秒");
            File imageFile = new File(dir.toString(), (df.format(new Date()) + ".png"));
            if (saveImage == null) {
                filesManager.saveImage(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
            } else {
                ImageIO.write(saveImage, "png", imageFile);
            }
        } catch (Exception e) {
            System.err.println("保存失败.");
            e.printStackTrace();
        }
    }
}
