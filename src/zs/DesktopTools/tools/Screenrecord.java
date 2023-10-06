package zs.DesktopTools.tools;

import zs.DesktopTools.frame.toolsFrame;
import zs.DesktopTools.managers.systemManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Screenrecord {
    public static void screenrecordFrame() {
        JFrame frame = new JFrame();
        frame.setSize(180, 160);
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        JToggleButton mic = new JToggleButton("麦克风");
        mic.setFont(new Font("", 1, 15));
        mic.setSize(90, 30);
        mic.setLocation(45, 20);
        mic.addChangeListener(e -> {
            if (((JToggleButton) e.getSource()).isSelected()) {
            }
        });
        frame.add(mic);

        JToggleButton rec = new JToggleButton("录屏");
        rec.setFont(new Font("", 1, 15));
        rec.setSize(90, 30);
        rec.setLocation(45, 70);
        rec.addChangeListener(e -> {
            if (((JToggleButton) e.getSource()).isSelected()) {
            }
        });
        frame.add(rec);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new toolsFrame();
            }
        });
    }
}
