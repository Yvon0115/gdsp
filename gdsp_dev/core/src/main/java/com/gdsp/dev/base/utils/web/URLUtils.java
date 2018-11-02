package com.gdsp.dev.base.utils.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * URL工具方法
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class URLUtils {

    /**分隔符*/
    private static final char SEPARATOR = '/';
    private static final Logger logger = LoggerFactory.getLogger(URLUtils.class);

    /**
     * 构造方法
     */
    private URLUtils() {}

    /**
     * 验证url串是否合法
     * @param url 地址串
     * @return 布尔值
     */
    public static boolean validURL(String url) {
        String regEx = "^((https|http|ftp|rtsp|mms)?://)" +
                "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" +
                "(([0-9]{1,3}.){3}[0-9]{1,3}|" +
                "([0-9a-z_!~*'()-]+.)*" +
                "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]." +
                "[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        String regEx1 = "^((/?)[_a-zA-Z0-9-]+)+(\\.[_a-zA-Z0-9-]+)?((/?)|((/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?))$";
        return url.matches(regEx) || url.matches(regEx1);
    }

    /**
     * 格式化url，格式化为前后都带分隔符形式
     * @param url url串
     * @return 格式化后的url串
     */
    public static String formatURL(String url) {
        return formatURL(url, true, true);
    }

    /**
     * 格式化url,格式化为前后不带分隔符的形式
     * @param url url串
     * @return 格式化后的url串
     */
    public static String formatCleanURL(String url) {
        return formatURL(url, false, false);
    }

    /**
     * 格式化url，格式化为指定形式
     * @param url 原有url
     * @param hasStart 开始是否带分隔符
     * @param hasEnd 结束是否带分隔符
     * @return 格式化后的url串
     */
    public static String formatURL(String url, boolean hasStart, boolean hasEnd) {
        StringBuilder buffer = new StringBuilder(url);
        int i = 0;
        while (i < buffer.length()) {
            if (buffer.charAt(i) == SEPARATOR) {
                i++;
                continue;
            }
            buffer.delete(0, i);
            break;
        }
        i = buffer.length() - 1;
        while (i >= 0) {
            if (buffer.charAt(i) == SEPARATOR) {
                i--;
                continue;
            }
            buffer.delete(i + 1, buffer.length());
            break;
        }
        if (buffer.length() == 0) {
            if (hasStart || hasEnd)
                return String.valueOf(SEPARATOR);
            else
                return "";
        }
        if (hasStart)
            buffer.insert(0, SEPARATOR);
        if (hasEnd)
            buffer.append(SEPARATOR);
        return buffer.toString();
    }

    /**
     * 取得url路径的起始路径名
     * @param url ur串
     * @return 起始路径名
     */
    public static String getStartPath(String url) {
        if (url == null)
            return null;
        int pos;
        int start = 0;
        if (url.startsWith("/")) {
            pos = url.indexOf("/", 1);
            start = 1;
        } else {
            pos = url.indexOf("/");
        }
        if (pos < 0) {
            pos = url.length();
        }
        return url.substring(start, pos);
    }

    /**
     * 获取Ip地址
     * @param request http请求
     * @return ip地址
     */
    public static String getIPAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
    /**
	 * 根据IP地址获取mac地址
	 * @param ipAddress 127.0.0.1
	 * @return
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public static String getLocalMac(String ipAddress) throws SocketException,
			UnknownHostException {
		String str = "";
		String macAddress = "";
		final String LOOPBACK_ADDRESS = "127.0.0.1";
		// 如果为127.0.0.1,则获取本地MAC地址。
		if (LOOPBACK_ADDRESS.equals(ipAddress)) {
			InetAddress inetAddress = InetAddress.getLocalHost();
			// 貌似此方法需要JDK1.6。
			byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
					.getHardwareAddress();
			// 下面代码是把mac地址拼装成String
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
			// 把字符串所有小写字母改为大写成为正规的mac地址并返回
			macAddress = sb.toString().trim().toUpperCase();
			return macAddress;
		} else {
			// 获取非本地IP的MAC地址
			try {
				Process p = Runtime.getRuntime()
						.exec("nbtstat -A " + ipAddress);
				InputStreamReader ir = new InputStreamReader(p.getInputStream());
				BufferedReader br = new BufferedReader(ir);
				while ((str = br.readLine()) != null) {
					if(str.indexOf("MAC")>1){
						macAddress = str.substring(str.indexOf("MAC")+9, str.length());
						macAddress = macAddress.trim();
						break;
					}
				}
				p.destroy();
				br.close();
				ir.close();
			} catch (IOException ex) {
				logger.debug(ex.getMessage(),ex);
			}
			return macAddress;
		}
	}

    /**
     * 获取url querystring 参数
     * @param url
     * @return
     */
    public static String getParamByName(String url, String paramName) {
        if (StringUtils.isNotBlank(url)) {
            int paramUrlIdx = url.indexOf("?");
            String paramUrl = paramUrlIdx != -1 ? url.substring(paramUrlIdx) : "";
            int mnIdx = paramUrl.indexOf(paramName + "=");
            String mn = mnIdx != -1 ? paramUrl.substring(mnIdx).replace(paramName + "=", "") : "";
            //参数为空
            if (mn.startsWith("&") || mn.startsWith("#")) {
                return null;
            }
            //不为空截取参数
            List<String> splitList = new ArrayList<String>();
            splitList.add("&");
            splitList.add("#");
            int idx = -1;
            for (String split : splitList) {
                idx = mn.indexOf(split);
                if (idx != -1) {
                    return mn.substring(0, idx);
                }
            }
            return mn;
        }
        return null;
    }
    
}
