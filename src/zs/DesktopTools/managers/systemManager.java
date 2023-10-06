package zs.DesktopTools.managers;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zs.DesktopTools.Main;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.security.SecureRandom;

public class systemManager {
    public static String Windows = System.getenv("windir") + "\\";
    public static String System32 = Windows + "System32\\";
    public static String cmd = systemManager.System32 + "cmd.exe /c";
    public static String shutdown = systemManager.System32 + "shutdown.exe";
    public static String rundll = systemManager.System32 + "rundll32.exe";
    public static String User = System.getProperty("user.home");
    public static String Roaming = System.getenv("APPDATA");
    public static String Desktop = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    public static File workPath = new File(new File("").getAbsolutePath() + "\\");
    public static File DTPath = new File(workPath + "\\" + Main.name + ".jar");
    public static File startPath = new File(Roaming + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\");
    public static File lnkPath = new File(startPath + "\\" + Main.name + ".lnk");
    public static String explorer = systemManager.Windows + "explorer.exe";

    public static @Nullable String getClipboardString() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        if (contents != null) {
            if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    return (String) contents.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    System.out.println("取剪贴板失败.");
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void checkStart() {
        String start = settingsManager.getSetting("Start");
        if (start != null &&
                start.equals("true") &&
                workPath != startPath &&
                (!lnkPath.exists() ||
                        !lnkPath.isFile() ||
                        lnkPath.length() <= 0 ||
                        !lnkPath.toString().endsWith(Main.name + ".lnk"))) {
            lnkPath.delete();
            try {
                lnkPath.createNewFile();
                Runtime.getRuntime().exec(System32 + "mshta.exe VBScript:Execute(\"Set a=CreateObject(\"\"WScript.Shell\"\"):Set b=a.CreateShortcut(\"\"" + lnkPath + "\"\"):b.TargetPath=\"\"" + DTPath + "\"\":b.Save:close\")");
            } catch (Exception e) {
                System.err.println("创建快捷方式失败.");
                e.printStackTrace();
            }

        } else if (start.equals("false") &&
                (lnkPath.exists() ||
                        lnkPath.isFile() ||
                        lnkPath.length() > 0 ||
                        lnkPath.toString().endsWith(Main.name + ".lnk"))) {
            lnkPath.delete();
        }
    }

    @Contract("_ -> new")
    public static @NotNull String randomValue(int length) {
        String randomString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&_+`-=,./(){}[]";
        char[] value = new char[length];
        for (int index = 0; index < value.length; ++index) {
            value[index] = randomString.charAt(new SecureRandom().nextInt(randomString.length()));
        }
        return new String(value);
    }

    public static void deleteDirectory(String directoryPath) {
        File directoryFile = new File(directoryPath);
        File[] files = directoryFile.listFiles();
        if (files != null) {
            for (File tempFile : files) {
                deleteDirectory(tempFile.getAbsolutePath());
            }
        }
        directoryFile.delete();
    }

    public static @NotNull Color getColor() {
        return new Color(Integer.parseInt(settingsManager.getLine("Color", 1)), Integer.parseInt(settingsManager.getLine("Color", 2)), Integer.parseInt(settingsManager.getLine("Color", 3)));
    }
}
