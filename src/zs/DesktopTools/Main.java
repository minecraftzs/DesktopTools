package zs.DesktopTools;

import com.melloware.jintellitype.JIntellitype;
import zs.DesktopTools.frame.mainFrame;
import zs.DesktopTools.managers.settingsManager;
import zs.DesktopTools.tools.Screenshot;

public class Main {
    public static String name = "DesktopTools";
    public static String version = "v6";

    public static void main(String[] args) {
        JIntellitype hotkey = new JIntellitype();
        hotkey.registerHotKey(1, 1, 0x53);//alt + s局部截图
        hotkey.registerHotKey(2, 1, 0x4D);//alt + m主界面
        hotkey.addHotKeyListener(identifier -> {
            switch (identifier) {
                case 1:
                    new Screenshot.PartialScreenshot().setVisible(true);
                    break;
                case 2:
                    settingsManager.checkSettings();
                    new mainFrame();
                    break;
            }
        });
        settingsManager.checkSettings();
        /*Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event.getID() == 401) {
                if (((KeyEvent) event).getKeyChar() == 0x1B) {
                    System.exit(0);
                }
            }
        }, 0x08);*/
    }
}
