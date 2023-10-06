package zs.DesktopTools.managers;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class netManager {
    private static int retry = 0;

    public static boolean checkNet() {
        System.out.print("检查网络...");
        try {
            InputStream is = Runtime.getRuntime().exec(systemManager.cmd + "ping www.baidu.com").getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String i;
            while ((i = br.readLine()) != null) {
                sb.append(i);
            }
            is.close();
            isr.close();
            br.close();
            if (!sb.toString().isEmpty() && sb.toString().indexOf("TTL") > 0) {
                System.out.println("完成");
                return true;
            } else {
                System.err.println("网络未连接!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("网络检查失败.");
            e.printStackTrace();
            return false;
        }
    }

    public static void download(String urlPath) {
        try {
            System.out.println("连接网络资源中...");
            URL url1 = new URL(urlPath);
            HttpURLConnection c1 = (HttpURLConnection) url1.openConnection();
            c1.connect();
            String urlPath2 = c1.getURL().toString();
            URL url2 = new URL(urlPath2);
            HttpURLConnection c2 = (HttpURLConnection) url2.openConnection();
            c2.connect();
            InputStream is = url2.openConnection().getInputStream();
            String fileName = null;
            String content = c2.getHeaderField("Content-disposition");
            if (content == null) {
                fileName = url2.getFile().substring(url2.getFile().lastIndexOf("/") + 1).replace("%20", " ");
            } else {
                fileName = content.substring(content.lastIndexOf("\"") + 19);
                if (fileName.startsWith("me=")) {
                    fileName = content.substring(content.lastIndexOf("\"") + 22);
                }
                if (fileName.endsWith("=")) {
                    //["|", "/", "\\", "\"", "?", "<", ">", ":", "*"]
                    fileName = "null";
                }
            }
            System.out.println("资源名: " + fileName);
            int fileSize = c2.getContentLength();
            if (fileSize <= 0) {
                System.out.println("资源大小未知.");
            } else {
                System.out.println("资源大小: " + fileSize + " B");
            }
            File filePath = new File(settingsManager.getSetting("downloadPath") + "\\" + fileName);
            filePath.delete();
            filePath.createNewFile();
            System.out.print("开始下载...");
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] b = new byte[1024];
            int i;
            while ((i = is.read(b)) > 0) {
                fos.write(b, 0, i);
            }
            c1.disconnect();
            c2.disconnect();
            is.close();
            fos.flush();
            fos.close();
            System.out.println("完成");
        } catch (Exception e) {
            System.err.println("下载失败.");
            e.printStackTrace();
            if (retry == 0) {
                retry++;
                download(urlPath);
            } else {
                JOptionPane.showMessageDialog(null, "下载失败, 请重试.", "下载失败", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
