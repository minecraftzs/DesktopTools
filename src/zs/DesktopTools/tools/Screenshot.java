package zs.DesktopTools.tools;

import org.jetbrains.annotations.NotNull;
import zs.DesktopTools.frame.toolsFrame;
import zs.DesktopTools.managers.filesManager;
import zs.DesktopTools.managers.systemManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Screenshot {
    public static void screenshotFrame() {
        JFrame frame = new JFrame();
        frame.setSize(180, 160);
        frame.getContentPane().setBackground(systemManager.getColor());
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        JButton mode1 = new JButton("全屏");
        mode1.setFont(new Font("", 1, 15));
        mode1.setSize(70, 30);
        mode1.setLocation(45, 20);
        mode1.addActionListener(e -> {
            try {
                frame.setVisible(false);
                Thread.sleep(800);
                filesManager.saveImage(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
                frame.setVisible(true);
            } catch (Exception e1) {
                System.err.println("截屏失败.");
                throw new RuntimeException(e1);
            }
        });
        frame.add(mode1);

        JButton mode2 = new JButton("局部");
        mode2.setFont(new Font("", 1, 15));
        mode2.setSize(70, 30);
        mode2.setLocation(45, 70);
        mode2.addActionListener(e -> EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(false);
                Thread.sleep(800);
                new PartialScreenshot().setVisible(true);
                frame.setVisible(true);
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }));
        frame.add(mode2);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new toolsFrame();
            }
        });
    }

    public static class PartialScreenshot extends JWindow {
        PartialScreenshot tools;
        int x1, y1, x2, y2;
        BufferedImage image;
        BufferedImage tmp = null;
        BufferedImage saveImage = null;

        public PartialScreenshot() {
            try {
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                setBounds(0, 0, d.width, d.height);
                image = new Robot().createScreenCapture(new Rectangle(0, 0, d.width, d.height));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        x1 = e.getX();
                        y1 = e.getY();
                        if (tools != null) {
                            tools.setVisible(false);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (tools == null) {
                            try {
                                PartialScreenshot.this.setAlwaysOnTop(true);
                                PartialScreenshot.this.setLocation(e.getX(), e.getY());
                                PartialScreenshot.this.pack();
                                PartialScreenshot.this.setVisible(false);
                                filesManager.saveImage(saveImage);
                                PartialScreenshot.this.setVisible(true);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            tools.setLocation(e.getX(), e.getY());
                        }
                    }
                });

                addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        x2 = e.getX();
                        y2 = e.getY();
                        int x = Math.min(x1, x2);
                        int y = Math.min(y1, y2);
                        int width = Math.abs(x2 - x1) + 1;
                        int height = Math.abs(y2 - y1) + 1;
                        Image tmp2 = createImage(PartialScreenshot.this.getWidth(), PartialScreenshot.this.getHeight());
                        Graphics g = tmp2.getGraphics();
                        g.drawImage(tmp, 0, 0, null);
                        g.setColor(systemManager.getColor());
                        g.drawRect(x - 1, y - 1, width + 1, height + 1);
                        saveImage = image.getSubimage(x, y, width, height);
                        g.drawImage(saveImage, x, y, null);
                        PartialScreenshot.this.getGraphics().drawImage(tmp2, 0, 0, PartialScreenshot.this);
                    }
                });
            } catch (Exception e) {
                System.err.println("截屏失败.");
                e.printStackTrace();
            }
        }

        @Override
        public void paint(@NotNull Graphics g) {
            tmp = new RescaleOp(0.5f, 0, null).filter(image, null);
            g.drawImage(tmp, 0, 0, this);
            PartialScreenshot.this.setAlwaysOnTop(true);
        }
    }
}
