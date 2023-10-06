package zs.DesktopTools.managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zs.DesktopTools.Main;
import zs.DesktopTools.SettingsEnum;
import zs.DesktopTools.frame.chooseFrame;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class settingsManager {
    public static String settingsPath = System.getenv("APPDATA") + "\\" + Main.name + "\\";

    public static @NotNull String chooseFile(String message, String currentPath, String suffix) {
        JOptionPane.showMessageDialog(null, message);
        File path = chooseFrame.fileChooser(currentPath);
        if (path == null || path.length() == 0L || path.isDirectory() || !path.exists() || !path.toString().endsWith(suffix)) {
            JOptionPane.showMessageDialog(null, message);
            System.exit(0);
        }
        return String.valueOf(path);
    }

    public static @NotNull String chooseDir(String message, String currentPath) {
        JOptionPane.showMessageDialog(null, message);
        File path = chooseFrame.directoryChooser(currentPath);
        if (path == null || path.length() == 0L || path.isFile() || !path.exists()) {
            JOptionPane.showMessageDialog(null, message);
            System.exit(0);
        }
        return path + "\\";
    }

    public static void checkSetting(String settingName) {
        File settingFile = new File(settingsPath + settingName + ".inf");
        try {
            if (!settingFile.exists()) {
                settingFile.createNewFile();
            }
            if (settingFile.length() <= 0) {
                String settings = perfectSetting(settingName);
                writeSetting(settingName, settings);
            }
        } catch (Exception e) {
            System.err.println("检查设置失败");
            e.printStackTrace();
        }
    }

    public static void checkSettings() {
        File settingsDirectory = new File(settingsPath);
        if (!settingsDirectory.exists()) {
            try {
                settingsDirectory.mkdir();
            } catch (Exception e) {
                System.err.println("创建设置目录失败.");
                e.printStackTrace();
            }
        }
        HashMap<Integer, SettingsEnum> settingName = new HashMap<>();
        SettingsEnum[] enums = SettingsEnum.values();
        for (int i = 0; i < SettingsEnum.values().length; i++) {
            settingName.put(i + 1, enums[i]);
            checkSetting(settingName.get(i + 1).name());
        }
        systemManager.checkStart();
    }

    public static @Nullable String perfectSetting(@NotNull String settingName) {
        switch (settingName) {
            case "Start":
                return String.valueOf(JOptionPane.showConfirmDialog(null, SettingsEnum.Start + "?", "确认", 0) == 0);
            case "mclPath":
                return chooseFile("请选择" + SettingsEnum.mclPath, System.getenv("APPDATA"), ".jar");
            case "screenshotPath":
                return chooseDir("请选择" + SettingsEnum.screenshotPath, systemManager.Desktop);
            case "screenrecordPath":
                return chooseDir("请选择" + SettingsEnum.screenrecordPath, systemManager.Desktop);
            case "downloadPath":
                return chooseDir("请选择" + SettingsEnum.downloadPath, systemManager.Desktop);
            case "Color":
                chooseFrame.colorChooser();
                return "";
        }
        return null;
    }

    public static @Nullable String getSetting(String settingName) {
        File settingFile = new File(settingsPath + settingName + ".inf");
        try {
            if (settingFile.exists() && settingFile.length() != 0) {
                BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(settingFile.toPath()), StandardCharsets.UTF_8));
                return br.readLine();
            } else {
                File settingsDirectory = new File(settingsPath);
                if (!settingsDirectory.exists()) {
                    settingsDirectory.createNewFile();
                } else {
                    if (settingFile.exists()) {
                        settingFile.createNewFile();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("读取设置失败.");
            e.printStackTrace();
        }
        return "false";
    }

    public static void writeSetting(String settingName, String setting) {
        try {
            File settingFile = new File(settingsPath + settingName + ".inf");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(settingFile.toPath()), StandardCharsets.UTF_8));
            bw.write(setting);
            bw.flush();
        } catch (Exception e) {
            System.err.println("写入设置失败.");
            e.printStackTrace();
        }
    }

    public static @Nullable String getLine(String settingName, int line) {
        try {
            File settingFile = new File(settingsPath + settingName + ".inf");
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(settingFile.toPath()), StandardCharsets.UTF_8));
            String str = br.readLine();
            for (int i = 0; str != null; str = br.readLine()) {
                ++i;
                if (line == i) {
                    return str;
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("读取设置失败.");
            e.printStackTrace();
        }
        return null;
    }
}
