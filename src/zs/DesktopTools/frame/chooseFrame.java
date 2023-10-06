package zs.DesktopTools.frame;

import zs.DesktopTools.managers.settingsManager;
import zs.DesktopTools.tools.Position;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class chooseFrame {
    public static File fileChooser(String root) {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setDialogTitle("选择文件");
        fc.setCurrentDirectory(new File(root));
        fc.setFileSelectionMode(0);
        if (fc.showDialog(new JLabel(), "选择") == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            System.exit(0);
        }
        return null;
    }

    public static File directoryChooser(String root) {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setDialogTitle("选择路径");
        fc.setCurrentDirectory(new File(root));
        fc.setFileSelectionMode(1);
        if (fc.showDialog(new JLabel(), "选择") == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            System.exit(0);
        }
        return null;
    }

    public static void colorChooser() {
        AtomicInteger R = new AtomicInteger(100);
        AtomicInteger G = new AtomicInteger(150);
        AtomicInteger B = new AtomicInteger(255);
        AtomicReference<Color> c = new AtomicReference<>(new Color(R.intValue(), G.intValue(), B.intValue()));
        JFrame frame = new JFrame("选择主题颜色");
        int x = 600, y = 380;
        frame.setBounds(Position.getWidth(x), Position.getHeight(y), x, y);
        frame.setLayout(null);

        JLabel lR = new JLabel("R: ");
        lR.setBounds(70, 70, 20, 20);
        frame.add(lR);

        JLabel lG = new JLabel("G: ");
        lG.setBounds(70, 120, 20, 20);
        frame.add(lG);

        JLabel lB = new JLabel("B: ");
        lB.setBounds(70, 170, 20, 20);
        frame.add(lB);

        JSlider sR = new JSlider(0, 255, 100);
        sR.setBounds(100, 60, 150, 30);
        frame.add(sR);

        JSlider sG = new JSlider(0, 255, 150);
        sG.setBounds(100, 110, 150, 30);
        frame.add(sG);

        JSlider sB = new JSlider(0, 255, 255);
        sB.setBounds(100, 160, 150, 30);
        frame.add(sB);

        JTextField tfR = new JTextField(String.valueOf(R));
        tfR.setBounds(260, 50, 50, 50);
        tfR.setEditable(false);
        frame.add(tfR);

        JTextField tfG = new JTextField(String.valueOf(G));
        tfG.setBounds(260, 100, 50, 50);
        tfG.setEditable(false);
        frame.add(tfG);

        JTextField tfB = new JTextField(String.valueOf(B));
        tfB.setBounds(260, 150, 50, 50);
        tfB.setEditable(false);
        frame.add(tfB);

        JTextArea ta = new JTextArea(5, 10);
        ta.setBounds(350, 50, 150, 150);
        ta.setBackground(c.get());
        ta.setEditable(false);
        frame.add(ta);
        ChangeListener cl = e -> {
            int r = sR.getValue();
            int g = sG.getValue();
            int b = sB.getValue();
            tfR.setText(String.valueOf(r));
            tfG.setText(String.valueOf(g));
            tfB.setText(String.valueOf(b));
            c.set(new Color(r, g, b));
            ta.setBackground(c.get());
            R.set(r);
            G.set(g);
            B.set(b);
        };
        sR.addChangeListener(cl);
        sG.addChangeListener(cl);
        sB.addChangeListener(cl);

        JButton button = new JButton("确定");
        button.setBounds(350, 230, 150, 40);
        button.addActionListener(e -> {
            frame.setVisible(false);
            settingsManager.writeSetting("Color", R.intValue() + "\r\n" + G.intValue() + "\r\n" + B.intValue());
        });
        frame.add(button);

        frame.setVisible(true);
    }
}
