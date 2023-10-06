package zs.DesktopTools.tools;

import org.jetbrains.annotations.NotNull;
import zs.DesktopTools.frame.toolsFrame;
import zs.DesktopTools.managers.systemManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Position {
    public Position() {
        JFrame frame = new JFrame();
        frame.setSize(170, 150);
        frame.setAlwaysOnTop(true);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setResizable(false);
        frame.setLayout(null);

        JLabel x;
        JLabel lbx = new JLabel("X:");
        lbx.setFont(new Font("宋体", 1, 18));
        lbx.setBounds(30, 10, 80, 50);
        frame.add(lbx);
        x = new JLabel("0");
        x.setFont(new Font("", 0, 27));
        x.setBounds(60, 10, 80, 50);
        frame.add(x);

        JLabel y;
        JLabel lby = new JLabel("Y:");
        lby.setFont(new Font("宋体", 1, 18));
        lby.setBounds(30, 50, 80, 50);
        frame.add(lby);
        y = new JLabel("0");
        y.setFont(new Font("", 0, 27));
        y.setBounds(60, 50, 80, 50);
        frame.add(y);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                x.setText(getX());
                y.setText(getY());
            }
        }, 100, 100);

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new toolsFrame();
            }
        });
    }

    public static @NotNull String getX() {
        return String.valueOf(MouseInfo.getPointerInfo().getLocation().x);
    }

    public static @NotNull String getY() {
        return String.valueOf(MouseInfo.getPointerInfo().getLocation().y);
    }

    public static int getWidth(int x) {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - x / 2;
    }

    public static int getHeight(int y) {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - y / 2;
    }
}
