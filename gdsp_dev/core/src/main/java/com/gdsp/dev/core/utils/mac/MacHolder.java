package com.gdsp.dev.core.utils.mac;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 物理地址是48位，别和ipv6搞错了
 * 
 * @author Administrator
 *
 */
public class MacHolder {
	private MacHolder() {
	}

	private static MacHolder instance = new MacHolder();

	public static MacHolder getInstance() {
		return instance;
	}
	/**
	 * 得到当前服务器默认的IP对应的MAC地址
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public String getLocalMac() throws UnknownHostException, SocketException {
		InetAddress ia = InetAddress.getLocalHost();
		return getMac(ia);
	}
	/**
	 * 得到当前服务器所有的MAC地址
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public List<String> getAllLocalMac() throws UnknownHostException, SocketException {
		HashSet<String> macs = new HashSet<>();
		List<String> result = new ArrayList<>();
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();

			Enumeration<InetAddress> cardipaddress = ni.getInetAddresses();
			while(cardipaddress.hasMoreElements()){
				InetAddress ip = (InetAddress) cardipaddress.nextElement();
				if (!ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
					String mac = getMac(ip);
					if(!StringUtils.isEmpty(mac)){
						macs.add(getMac(ip));
					}
				}
			}
		}
		CollectionUtils.addAll(result, macs.iterator());
		return result;
	}

	private String getMac(InetAddress ia) throws SocketException {
		// 获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		if(mac==null){
			return null;
		}
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				buffer.append("-");
			}
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			if (str.length() == 1) {
				buffer.append("0" + str);
			} else {
				buffer.append(str);
			}
		}
		return buffer.toString().toUpperCase();
	}
}
