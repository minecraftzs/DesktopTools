package zs.DesktopTools.frame;

import zs.DesktopTools.Main;
import zs.DesktopTools.managers.systemManager;
import zs.DesktopTools.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class toolsFrame {
    public toolsFrame() {
        JFrame frame = new JFrame(Main.name + " " + Main.version + " | 工具");
        int x = 710, y = 500;
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setBounds(Position.getWidth(x), Position.getHeight(y), x, y);
        frame.setResizable(false);
        frame.setLayout(null);

        JButton clean = new JButton("清 理");
        clean.setFont(new Font("", 1, 20));
        clean.setSize(250, 50);
        clean.setLocation(70, 50);
        clean.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec(systemManager.System32 + "schtasks.exe /run /tn Sysclean");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        frame.add(clean);

        JButton game = new JButton("游 戏");
        game.setFont(new Font("", 1, 20));
        game.setSize(250, 50);
        game.setLocation(70, 130);
        game.addActionListener(e -> {
            frame.setVisible(false);
            Game.gameFrame();
        });
        frame.add(game);

        JButton position = new JButton("屏幕坐标");
        position.setFont(new Font("", 1, 20));
        position.setSize(250, 50);
        position.setLocation(70, 210);
        position.addActionListener(e -> {
            frame.setVisible(false);
            new Position();
        });
        frame.add(position);

        JButton screenshot = new JButton("截屏");
        screenshot.setFont(new Font("", 1, 20));
        screenshot.setSize(250, 50);
        screenshot.setLocation(70, 290);
        screenshot.addActionListener(e -> {
            frame.setVisible(false);
            Screenshot.screenshotFrame();
        });
        frame.add(screenshot);


        JButton screenrecord = new JButton("录屏");
        screenrecord.setEnabled(false);
        screenrecord.setFont(new Font("", 1, 20));
        screenrecord.setSize(250, 50);
        screenrecord.setLocation(70, 370);
        screenrecord.addActionListener(e -> {
            frame.setVisible(false);
            Screenrecord.screenrecordFrame();
        });
        frame.add(screenrecord);

        JButton download = new JButton("下载");
        download.setFont(new Font("", 1, 20));
        download.setEnabled(mainFrame.connect);
        download.setSize(250, 50);
        download.setLocation(370, 50);
        download.addActionListener(e -> {
            frame.setVisible(false);
            Download.downloadFrame();
        });
        frame.add(download);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new mainFrame();
            }
        });
    }
}
