package com.melloware.jintellitype;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JIntellitype {
    public final long handler = 0;
    private final List<HotkeyListener> hotkeyListeners = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    private final List<IntellitypeListener> intellitypeListeners = Collections.synchronizedList(new CopyOnWriteArrayList<>());

    public JIntellitype() {
        try {
            String dll = System.getProperty("os.arch", "unknown").contains("64") ? "JIntellitype64.dll" : "JIntellitype.dll";
            File extractedLibrary = Paths.get(new File(System.getenv("tmp")).getAbsolutePath(), dll).toFile();
            try {
                System.load(extractedLibrary.getAbsolutePath());
            } catch (Throwable t) {
                File file = new File(extractedLibrary.getAbsolutePath());
                if (file.exists() && !file.delete()) {
                    System.err.println("Could not delete file: " + extractedLibrary.getAbsolutePath());
                }
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    System.err.println("Could not create dirs for file: " + extractedLibrary.getAbsolutePath());
                }

                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                if (cl == null) cl = ClassLoader.getSystemClassLoader();
                InputStream is = cl.getResourceAsStream("com/melloware/jintellitype/windows/" + dll);
                OutputStream os = Files.newOutputStream(Paths.get(extractedLibrary.getAbsolutePath()));
                byte[] b = new byte[8192];
                int read;
                while ((read = is.read(b)) != -1) {
                    os.write(b, 0, read);
                }
                is.close();
                os.close();
            }
            System.load(extractedLibrary.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeLibrary();
    }

    public void addHotKeyListener(HotkeyListener listener) {
        hotkeyListeners.add(listener);
    }

    public void registerHotKey(int identifier, int modifier, int keycode) {
        int mask = 0;
        if ((modifier & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK || (modifier & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK) {
            mask |= 4;
        }
        if ((modifier & InputEvent.ALT_DOWN_MASK) == InputEvent.ALT_DOWN_MASK || (modifier & InputEvent.ALT_DOWN_MASK) == InputEvent.ALT_DOWN_MASK) {
            mask |= 1;
        }
        if ((modifier & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK || (modifier & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK) {
            mask |= 2;
        }
        if ((modifier & InputEvent.META_DOWN_MASK) == InputEvent.META_DOWN_MASK || (modifier & InputEvent.META_DOWN_MASK) == InputEvent.META_DOWN_MASK) {
            mask |= 8;
        }
        if (mask == 0) {
            mask = modifier;
        }
        regHotKey(identifier, mask, keycode);
    }

    private synchronized native void initializeLibrary();

    private synchronized native void regHotKey(int identifier, int modifier, int keycode);

    private void onHotKey(final int identifier) {
        for (final HotkeyListener hotkeyListener : hotkeyListeners) {
            SwingUtilities.invokeLater(() -> hotkeyListener.onHotKey(identifier));
        }
    }

    private void onIntellitype(final int command) {
        for (final IntellitypeListener intellitypeListener : intellitypeListeners) {
            SwingUtilities.invokeLater(() -> intellitypeListener.onIntellitype(command));
        }
    }

    public interface HotkeyListener {
        void onHotKey(int identifier);
    }

    public interface IntellitypeListener {
        void onIntellitype(int command);
    }
}
