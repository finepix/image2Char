package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author F-zx
 * 图片文件读取然后转为字符
 */
public class TransTool {

	/**
	 * @return 返回bufferedImage
	 * @throws IOException 
	 * 
	 */
	public static BufferedImage trans2GrayImBuffer(InputStream imageis) throws IOException{
		
		
		BufferedImage bi = ImageIO.read(imageis);
		BufferedImage grayImg = null;
		int r,g,b;
		
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        int minx = bi.getMinX();  
        int miny = bi.getMinY();  
        

		grayImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        System.out.println("width=" + width + ",height=" + height + ".");  
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");  
        for (int i = minx; i < width; i++) {  
            for (int j = miny; j < height; j++) {  
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字  
                r = (pixel & 0xff0000) >> 16;  
                g = (pixel & 0xff00) >> 8;  
                b = (pixel & 0xff);  
                
                int gray = (int) (0.21*r + 0.71*g + 0.07*b);
                
                int newPixel = colorToRGB(255, gray, gray, gray);
                grayImg.setRGB(i, j, newPixel);
            }  
        }
        
        return grayImg;
//        File out = new File("E://out.jpg");
//        ImageIO.write(grayImg, "jpg", out);
//        System.out.println("ok");
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		InputStream imageis = TransTool.class.getClassLoader().getResourceAsStream("image/test.jpg");
		
		BufferedImage bi = ImageIO.read(imageis);
		BufferedImage grayImg = null;
		int r,g,b;
		
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        int minx = bi.getMinX();  
        int miny = bi.getMinY();  
        

		grayImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        System.out.println("width=" + width + ",height=" + height + ".");  
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");  
        for (int i = minx; i < width; i++) {  
            for (int j = miny; j < height; j++) {  
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字  
                r = (pixel & 0xff0000) >> 16;  
                g = (pixel & 0xff00) >> 8;  
                b = (pixel & 0xff);  
                
                int gray = (int) (0.21*r + 0.71*g + 0.07*b);
                
                int newPixel = colorToRGB(255, gray, gray, gray);
                grayImg.setRGB(i, j, newPixel);
            }  
        }
        File out = new File("E://out.jpg");
        ImageIO.write(grayImg, "jpg", out);
        System.out.println("ok");
        
	}
	
	/**
	 * 颜色分量转换为RGB值
	 * 
	 * @param alpha
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	private static int colorToRGB(int alpha, int red, int green, int blue) {
		
		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;

		return newPixel;
		
	}
}

