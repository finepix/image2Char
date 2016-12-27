package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import calculate.CalculateCharArea;
import calculate.CalculateCharArea.CharArea;
import util.TransTool;

public class Starter {
	
	private static char [] gradations = new char[256];

	private final static int WIDTH = 100;

	
	/**
	 * 
	 * 对图片进行缩放，缩放至设定好的width
	 * @param source_bi
	 * @return
	 * @throws IOException 
	 */
	private static BufferedImage zoom(BufferedImage source_bi) throws IOException{
		int height =  source_bi.getHeight();
		int width = source_bi.getWidth();
		BufferedImage bi = new BufferedImage(WIDTH, height * WIDTH / width , BufferedImage.TYPE_BYTE_GRAY);
		
		Graphics g = bi.getGraphics();
		g.drawImage(source_bi, 0, 0, WIDTH, height * WIDTH / width , null);
		g.dispose();
		
		File file = new File("e://final.jpg");
		
		ImageIO.write(bi, "jpg", file);
		
		return bi;
	}
	
	/**
	 *  将计算出来的字符数据转化为256色阶中对应位置
	 * @param arrays
	 */
	public static void hashChars2Arrays(CharArea[] arrays){
		int min = arrays[0].pixNum;
		int max = arrays[ arrays.length - 1].pixNum;
		int gap = max - min;
		
//		double ratio = gap / 256.0 ;
//		System.out.println(ratio);
		
		for (CharArea charArea : arrays) {
			gradations[(int)((charArea.pixNum - min) * 255 / gap)] = charArea.c;
		}
		
		char c = gradations[0];
		for(int i = 1 ; i < 255 ; i++){
			if(gradations[i] == '\0')
				gradations[i] = c;
			else
				c = gradations[i];
		}
		
		for (char s : gradations) {
			System.out.print(s+" ");
		}
	}
	
	public static void print(int height, int width, BufferedImage bi){
		int b = 0;
		 for (int i = 0; i < height; i++) {  
	            for (int j = 0; j < width; j++) {  
	                int pixel = bi.getRGB(j, i); // 下面三行代码将一个数字转换为RGB数字  
	                b = (pixel & 0xff);
	                System.out.printf("%2s", gradations[b]);
	            }  
	            System.out.print('\n');
	        }
		
	}
	
	public static void main(String[] args) throws IOException {
		
		//引入文件
		InputStream imageis = TransTool.class.getClassLoader().getResourceAsStream("image/test.jpg");
		
		BufferedImage bi = zoom(TransTool.trans2GrayImBuffer(imageis));
		int height = bi.getHeight();
		int width = bi.getWidth();
		
		CharArea[] arrays = CalculateCharArea.getArrays();
		
		hashChars2Arrays(arrays);
		gradations[0] = ' ';
		print(height, width, bi);
		
	}
}
