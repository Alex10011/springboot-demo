package com.alex10011.example.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtil {
	static Logger log = LoggerFactory.getLogger(ZipUtil.class);

	/**
	 * 对象转Byte数组
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] objectToByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();

		} catch (IOException e) {
			log.error("objectToByteArray failed, " + e);
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					log.error("close objectOutputStream failed, " + e);
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					log.error("close byteArrayOutputStream failed, " + e);
				}
			}

		}
		return bytes;
	}

	/**
	 * Byte数组转对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object byteArrayToObject(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(bytes);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			obj = objectInputStream.readObject();
		} catch (Exception e) {
			log.error("byteArrayToObject failed, " + e);
		} finally {
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
				} catch (IOException e) {
					log.error("close byteArrayInputStream failed, " + e);
				}
			}
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					log.error("close objectInputStream failed, " + e);
				}
			}
		}
		return obj;
	}

	public static void zipFile(String path, File file, ZipOutputStream zos) throws IOException {
		try {
			// 读Object内容
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			zos.putNextEntry(new ZipEntry(path + file.getName()));
			byte[] car = new byte[1024];
			int L = 0;
			while ((L = in.read(car)) != -1) {
				zos.write(car, 0, L);
			}

			if (in != null) {
				in.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
