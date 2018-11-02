package com.gdsp.dev.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象克隆工具类
 * @author wqh
 * @date 2017年11月27日 下午7:04:10
 */
public class CloneUtils {

	/**
	 * 克隆对象，此对象必须是继承自Serializable接口。
	 * @param obj 需要克隆的对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T obj) {

		T clonedObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			clonedObj = (T) ois.readObject();
			ois.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clonedObj;
	}
}