package zs.DesktopTools.frame;

import zs.DesktopTools.Main;
import zs.DesktopTools.managers.settingsManager;
import zs.DesktopTools.managers.systemManager;
import zs.DesktopTools.tools.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class settingsFrame {
    public settingsFrame() {
        JFrame frame = new JFrame(Main.name + " " + Main.version + " | 设置");
        int x = 400, y = 400;
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setBounds(Position.getWidth(x), Position.getHeight(y), x, y);
        frame.setResizable(false);
        frame.setLayout(null);

        JButton reset = new JButton("删除设置");
        reset.setFont(new Font("", 1, 15));
        reset.setBounds(25, 10, 120, 25);
        reset.addActionListener(e -> {
            frame.setVisible(false);
            if (JOptionPane.showConfirmDialog(null, "确认删除设置以重置软件?", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                systemManager.deleteDirectory(settingsManager.settingsPath);
                settingsManager.checkSettings();
            } else {
                frame.setVisible(true);
            }
        });
        frame.add(reset);

        JButton edit = new JButton("编辑设置");
        edit.setFont(new Font("", 1, 15));
        edit.setBounds(25, 60, 120, 25);
        edit.setEnabled(false);
        edit.addActionListener(e -> {
            frame.setVisible(false);
            try {
                //Runtime.getRuntime().exec(systemManager.System32 + "notepad.exe " );
                frame.setVisible(true);
            } catch (Exception e1) {
                System.err.println("打开设置文件失败.");
                e1.printStackTrace();
            }
        });
        frame.add(edit);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new mainFrame();
            }
        });
    }
}
