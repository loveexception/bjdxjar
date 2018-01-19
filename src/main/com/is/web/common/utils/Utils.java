package com.is.web.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class Utils {
	
	/**
	 * 根据uuid、时间戳，生成短唯一码
	 * @return
	
	public static String shortUUIDRandom(){
		String uidStr = UUID.randomUUID().toString();
		uidStr = uidStr.concat("-").concat(Long.toString(System.currentTimeMillis()));
		uidStr = MD5Util.MD5("md5", uidStr).concat("-").concat(Long.toString(System.currentTimeMillis()));
		return genShortKeys(uidStr)[0];
	}

	public static String shortKeys(String str){
		return genShortKeys(str)[0];
	}
	 */	
	/**
	 * 把传入字符串进行md5加密后，返回处理后的数组
	 * @param str
	 * @return
	
	public static String[] genShortKeys(String str){
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		
		str = MD5Util.MD5("MD5",str);
		String[] shortUrl = new String[4];
		for (int i = 0; i < 4; i++) {
			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			String sTempSubString = str.substring(i * 8, i * 8 + 8);
			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
			// long ，则会越界
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			for (int j = 0; j < 6; j++) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				outChars += chars[(int) index];
				// 每次循环按位右移 5 位
				lHexLong = lHexLong >> 5;
			}
			// 把字符串存入对应索引的输出数组
			shortUrl[i] = outChars;
		}
		return shortUrl;
	}
	 */
	public static String stringToBase64(String postdata){
		byte[] b64=postdata.getBytes();  
        Base64 base64=new Base64();  
        b64=base64.encode(b64);
        String postdata64 = new String(b64);
        return postdata64;
	}
	
	/**
	 * 生成指定长度的数字随机数
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 生成指定长度的数字字母混合随机数
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomChar(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z'};
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
	
	/**
	 * 生成指定长度的数字字母混合随机数
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNumAndChar(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 把随机码，转换成图片的base64码
	 * @param randomCode	随机码
	 * @param imageWidth	图片宽度
	 * @param imageHeight	图片高度
	 * @return
	 */
	public static String getBase64RandomImageCode(String randomCode,
			int imageWidth, int imageHeight) {
		try {
			BufferedImage image = new BufferedImage(imageWidth, imageHeight,
					BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics g = image.getGraphics();
			// 生成随机类
			Random random = new Random();
			// 设定背景色
			g.setColor(getRandColor(220, 250));
			g.fillRect(0, 0, imageWidth, imageHeight);
			// 设定字体
			g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
			// 画边框
			// g.drawRect(0,0,width-1,height-1);
			g.draw3DRect(0, 0, imageWidth - 1, imageHeight - 1, true);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(imageWidth);
				int y = random.nextInt(imageHeight);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			// 取随机产生的认证码(6位数字)
			for (int i = 0; i < randomCode.length(); i++) {
				char rand = randomCode.charAt(i);
				// 将认证码显示到图象中
				g.setColor(new Color(20 + random.nextInt(110), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(String.valueOf(rand), 18 * i + 10, 30);
			}
			g.drawOval(0, 12, 60, 11);
			// 将认证码存入SESSION
			// 图象生效
			g.dispose();
			
			// 创建编码对象
			// 创建字符流
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			// 写入字符流
			ImageIO.write(image, "jpg", bs);
			
			byte[] b64 = bs.toByteArray();  
	        Base64 base64=new Base64();  
	        b64=base64.encode(b64);
	        
			String imgsrc = new String(b64);    
			return imgsrc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		// 转码成字符串
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 转换表格排序
	 * @param orderBys
	 * @param tableColum
	 * @return
	 */
	public static LinkedHashMap<String, String> checkOrderBys(LinkedHashMap<String, String> orderBys,Map<String, String> tableColum) {
		StringBuffer orderByStrBuf = new StringBuffer();
		for (Iterator<String> it =  orderBys.keySet().iterator();it.hasNext();){
		    Object key = it.next();
		    if(tableColum.containsKey(key) &&
		    		(StringUtils.equalsIgnoreCase(orderBys.get(key).toString(), "desc") ||
		    				StringUtils.equalsIgnoreCase(orderBys.get(key).toString(), "asc"))){
		    	if(!StringUtils.isBlank(orderByStrBuf.toString())){
		    		orderByStrBuf.append(",");
		    	}
		    	orderByStrBuf.append(StringUtils.upperCase(tableColum.get(key)));
		    	orderByStrBuf.append(" ");
		    	orderByStrBuf.append(orderBys.get(key).toString());
			}
		}
		orderBys.put("TABLEORDERBYSTR", orderByStrBuf.toString());
		return orderBys;
	}
	
	/**
	 * 根据正则表达式，校验传入字符串是否符合
	 * @param verifyStr	需要校验的内容
	 * @param regexStr	正则表达式
	 * @return
	 */
	public static boolean verifyStringWithRegex(String verifyStr,String regexStr){
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(verifyStr);
		return matcher.matches();
	}
	
}
