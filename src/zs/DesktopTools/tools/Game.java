package zs.DesktopTools.tools;

import zs.DesktopTools.frame.toolsFrame;
import zs.DesktopTools.managers.settingsManager;
import zs.DesktopTools.managers.systemManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game {
    public static void gameFrame() {
        JFrame frame = new JFrame("游戏");
        int x = 270, y = 100;
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setBounds(Position.getWidth(x), Position.getHeight(y), x, y);
        frame.setResizable(false);
        frame.setLayout(null);

        JButton mode1 = new JButton("MC启动器");
        mode1.setBounds(20, 18, 95, 24);
        mode1.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("java -jar " + settingsManager.getSetting("mclPath"));
            } catch (Exception e1) {
                System.err.println("请重试.");
                e1.printStackTrace();
            }
        });
        frame.add(mode1);
        JButton mode2 = new JButton("mc1.1");
        mode2.setBounds(130, 18, 75, 24);
        mode2.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("cmd /c java " +
                        "-Dminecraft.client.jar=D:\\Minecraft\\Game\\1.1\\.minecraft\\versions\\1.1\\1.1.jar " +
                        "-XX:+UnlockExperimentalVMOptions " +
                        "-XX:+UseG1GC " +
                        "-XX:G1NewSizePercent=20 " +
                        "-XX:G1ReservePercent=20 " +
                        "-XX:MaxGCPauseMillis=50 " +
                        "-XX:G1HeapRegionSize=16m " +
                        "-XX:-UseAdaptiveSizePolicy " +
                        "-XX:-OmitStackTraceInFastThrow " +
                        "-XX:-DontCompileHugeMethods " +
                        "-Xmn128m " +
                        "-Xmx1024m " +
                        "-Dfml.ignoreInvalidMinecraftCertificates=true " +
                        "-Dfml.ignorePatchDiscrepancies=true " +
                        "-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump " +
                        "-Djava.library.path=D:\\Minecraft\\Game\\1.1\\.minecraft\\versions\\1.1\\natives " +
                        "-cp D:\\Minecraft\\Game\\1.1\\.minecraft\\libraries\\net\\minecraft\\launchwrapper\\1.5\\launchwrapper-1.5.jar;" +
                        "D:\\Minecraft\\Game\\1.1\\.minecraft\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\4.5\\jopt-simple-4.5.jar;" +
                        "D:\\Minecraft\\Game\\1.1\\.minecraft\\libraries\\org\\ow2\\asm\\asm-all\\4.1\\asm-all-4.1.jar;" +
                        "D:\\Minecraft\\Game\\1.1\\.minecraft\\libraries\\net\\java\\jinput\\jinput\\2.0.5\\jinput-2.0.5.jar;" +
                        "D:\\Minecraft\\Game\\1.1\\.minecraft\\libraries\\net\\java\\jutils\\jutils\\1.0.0\\jutils-1.0.0.jar;" +
                        "D:\\Minecraft\\Game\\1.1\\.minecraft\\libraries\\org\\lwjgl\\lwjgl\\lwjgl\\2.9.0\\lwjgl-2.9.0.jar;" +
                        "D:\\Minecraft\\Game\\1.1\\.minecraft\\libraries\\org\\lwjgl\\lwjgl\\lwjgl_util\\2.9.0\\lwjgl_util-2.9.0.jar;" +
                        "D:\\Minecraft\\Game\\1.1\\.minecraft\\versions\\1.1\\1.1.jar " +
                        "net.minecraft.launchwrapper.Launch " +
                        "1.0 " +
                        "b093537e504a4caaa2fa35897c812425 " +
                        "--gameDir D:\\Minecraft\\Game\\1.1\\.minecraft " +
                        "--assetsDir D:\\Minecraft\\Game\\1.1\\.minecraft\\assets\\virtual\\pre-1.6 " +
                        "--width 854 " +
                        "--height 480");
            } catch (Exception e1) {
                System.err.println("请重试.");
                e1.printStackTrace();
            }
        });
        frame.add(mode2);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new toolsFrame();
            }
        });
    }
}
