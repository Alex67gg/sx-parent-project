package com.shengxun.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

/**
 * Created by ldh on 2018/7/6.
 * 获取系统参数
 */
public class SystemUtil {

    private static final Logger log = LoggerFactory.getLogger(SystemUtil.class);
    /**
     * 获取服务器地址
     * @return Ip地址
     */
    public static String getServerIp() throws Exception{
        // 获取操作系统类型
        String sysType = System.getProperties().getProperty("os.name");
        String ip;
        if (sysType.toLowerCase().startsWith("win")) {  // 如果是Windows系统，获取本地IP地址
            String localIP = null;
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if (localIP != null) {
                return localIP;
            }
        } else {
            ip = getLinuxLocalIp(); // 兼容Linux
            if (ip != null) {
                return ip;
            }
        }
        return "获取服务器IP错误";
    }

    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                log.info(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            log.info("获取ip地址异常");
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        log.info("IP:"+ip);
        return ip;
    }
}
