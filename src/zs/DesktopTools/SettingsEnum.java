package zs.DesktopTools;

public enum SettingsEnum {
    Start("开机自启"),
    mclPath("MC启动器路径"),
    screenshotPath("截屏文件保存路径"),
    screenrecordPath("录屏文件保存路径"),
    downloadPath("下载文件保存路径"),
    Color("主题颜色");

    private final String name;

    SettingsEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
