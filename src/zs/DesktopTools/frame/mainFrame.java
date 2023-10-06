package zs.DesktopTools.frame;

import zs.DesktopTools.Main;
import zs.DesktopTools.managers.netManager;
import zs.DesktopTools.managers.systemManager;
import zs.DesktopTools.tools.Position;
import zs.DesktopTools.tools.Power;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class mainFrame {
    public static boolean connect = netManager.checkNet();

    public mainFrame() {
        JFrame frame = new JFrame(Main.name + " " + Main.version + " | qq: 2669514718");
        int x = 400, y = 500;
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setBounds(Position.getWidth(x), Position.getHeight(y), x, y);
        frame.setResizable(false);
        frame.setLayout(null);
        try {
            JButton refresh = new JButton("刷 新");
            refresh.setFont(new Font("", 1, 20));
            refresh.setBounds(70, 50, 250, 50);
            refresh.addActionListener(e -> {
                try {
                    Runtime.getRuntime().exec(systemManager.cmd + " del /s /f /ah /q " + systemManager.User + "\\AppData\\Local\\IconCache.db");
                    Runtime.getRuntime().exec(systemManager.cmd + " taskkill /f /im explorer.exe&&start " + systemManager.explorer);
                } catch (Exception e1) {
                    System.err.println("刷新失败.");
                    e1.printStackTrace();
                }
            });
            frame.add(refresh);

            JButton tools = new JButton("工 具");
            tools.setFont(new Font("", 1, 20));
            tools.setBounds(70, 130, 250, 50);
            tools.addActionListener(e -> {
                frame.setVisible(false);
                new toolsFrame();
            });
            frame.add(tools);

            JButton power = new JButton("电 源");
            power.setFont(new Font("", 1, 20));
            power.setBounds(70, 210, 250, 50);
            power.addActionListener(e -> {
                frame.setVisible(false);
                Power.powerFrame();
            });
            frame.add(power);

            JButton settings = new JButton("设置");
            settings.setFont(new Font("", 1, 20));
            settings.setBounds(70, 290, 250, 50);
            settings.addActionListener(e -> {
                frame.setVisible(false);
                new settingsFrame();
            });
            frame.add(settings);

            JButton exit = new JButton("隐 藏");
            exit.setFont(new Font("", 1, 20));
            exit.setBounds(70, 370, 250, 50);
            exit.addActionListener(e -> {
                frame.setVisible(false);
                //JOptionPane.showMessageDialog(null, "按下ctrl + t唤起主界面");
            });
            frame.add(exit);

            frame.setIconImage(ImageIO.read(Objects.requireNonNull(frame.getClass().getResource("/zs/DesktopTools/resources/DesktopTools.png"))));
        } catch (Exception e) {
            System.out.println("打开界面失败.");
            throw new RuntimeException(e);
        }
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
            }
        });
    }
    /*public static void trayFrame() {
        if (SystemTray.isSupported()) {
            try {
                PopupMenu frame = new PopupMenu();

                MenuItem main = new MenuItem("mainFrame");
                main.addActionListener(e -> new mainFrame());
                frame.add(main);

                MenuItem tools = new MenuItem("toolsFrame");
                tools.addActionListener(e -> new toolsFrame());
                frame.add(tools);

                MenuItem settings = new MenuItem("settingsFrame");
                settings.addActionListener(e -> new settingsFrame());
                frame.add(settings);

                MenuItem exit = new MenuItem("Exit");
                exit.addActionListener(e -> System.exit(0));
                frame.add(exit);

                TrayIcon trayIcon = new TrayIcon(ImageIO.read(Objects.requireNonNull(frame.getClass().getResource("/zs/DesktopTools/resources/DesktopTools.png"))), "双击以打开主界面.", frame);
                trayIcon.addActionListener(e -> new mainFrame());
                SystemTray.getSystemTray().add(trayIcon);
            } catch (Exception e) {
                System.out.println("创建托盘图标错误!");
                e.printStackTrace();
            }
        }
    }
    */
}
