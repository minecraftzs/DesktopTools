package zs.DesktopTools.tools;

import zs.DesktopTools.frame.mainFrame;
import zs.DesktopTools.managers.systemManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Power {
    public static void powerFrame() {
        JFrame frame = new JFrame("电源");
        int x = 400, y = 100;
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setBounds(Position.getWidth(x), Position.getHeight(y), x, y);
        frame.setResizable(false);
        frame.setLayout(null);

        JButton cancel = new JButton("取消");
        cancel.setBounds(25, 18, 65, 25);
        cancel.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec(systemManager.shutdown + " -a");
                Runtime.getRuntime().exec(systemManager.shutdown + " -a");
            } catch (Exception e1) {
                System.err.println("取消关机失败.");
                throw new RuntimeException(e1);
            }
        });
        frame.add(cancel);

        JButton lock = new JButton("锁定");
        lock.setBounds(100, 20, 60, 20);
        lock.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec(systemManager.rundll + " " + systemManager.System32 + "user32.dll LockWorkStation");
                System.exit(0);
            } catch (Exception e1) {
                System.err.println("锁定失败.");
                throw new RuntimeException(e1);
            }

        });
        frame.add(lock);

        JButton close = new JButton("关机");
        close.setBounds(165, 20, 60, 20);
        close.addActionListener(e -> {
            frame.setVisible(false);
            int s = JOptionPane.showConfirmDialog(null, "20秒后关机?", "确认", JOptionPane.YES_NO_OPTION);
            if (s == 0) {
                try {
                    Runtime.getRuntime().exec(systemManager.shutdown + " -s -t 20");
                } catch (Exception e1) {
                    System.err.println("关机失败.");
                    throw new RuntimeException(e1);
                }
            } else {
                frame.setVisible(true);
            }
        });
        frame.add(close);

        JButton restart = new JButton("重启");
        restart.setBounds(230, 20, 60, 20);
        restart.addActionListener(e -> {
            frame.setVisible(false);
            int s = JOptionPane.showConfirmDialog(null, "20秒后重启?", "确认", JOptionPane.YES_NO_OPTION);
            if (s == 0) {
                try {
                    Runtime.getRuntime().exec(systemManager.shutdown + " -r -t 20");
                } catch (Exception e1) {
                    System.err.println("重启失败.");
                    throw new RuntimeException(e1);
                }
            } else {
                frame.setVisible(true);
            }
        });
        frame.add(restart);

        JButton logout = new JButton("注销");
        logout.setBounds(295, 20, 60, 20);
        logout.addActionListener(e -> {
            frame.setVisible(false);
            int s = JOptionPane.showConfirmDialog(null, "立即注销?", "确认", JOptionPane.YES_NO_OPTION);
            if (s == 0) {
                try {
                    Runtime.getRuntime().exec(systemManager.shutdown + " -l");
                    System.exit(0);
                } catch (Exception e1) {
                    System.err.println("注销失败.");
                    throw new RuntimeException(e1);
                }
            } else {
                frame.setVisible(true);
            }
        });
        frame.add(logout);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new mainFrame();
            }
        });
    }
}
