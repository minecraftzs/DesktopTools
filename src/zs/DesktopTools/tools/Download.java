package zs.DesktopTools.tools;

import zs.DesktopTools.frame.toolsFrame;
import zs.DesktopTools.managers.netManager;
import zs.DesktopTools.managers.systemManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Download {
    public static void downloadFrame() {
        JFrame frame = new JFrame("下载");
        int x = 290, y = 90;
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.setBounds(Position.getWidth(x), Position.getHeight(y), x, y);

        JTextField textField = new JTextField();
        textField.setColumns(20);
        textField.setFont(new Font("楷体", 0, 20));
        frame.add(textField);

        JButton button = new JButton("确定");
        button.addActionListener(e -> {
            frame.setVisible(false);
            String urlPath = textField.getText();
            if (urlPath.startsWith("http")) {
                netManager.download(urlPath);
                frame.setVisible(true);
            } else {
                frame.setVisible(true);
            }
        });
        frame.add(button);
        frame.setVisible(true);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getID() == 401 && e.getKeyChar() == '\n') {
                    frame.setVisible(false);
                    button.doClick();
                }
            }
        });
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 3) {
                    try {
                        String urlPath = systemManager.getClipboardString();
                        if (urlPath != null && urlPath.startsWith("http")) {
                            textField.setText(urlPath);
                        }
                    } catch (Exception e1) {
                        System.err.println("取剪贴板失败.");
                        e1.printStackTrace();
                    }
                }
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new toolsFrame();
            }
        });
    }
}
