package calculate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;


/**
 * @author F-zx
                        // Sort characters by their pixel count in ascending order
                        // Returning 0 when counts are equal ensures consistent sorting
                        return Integer.compare(this.pixNum, o.pixNum);
	
	/**
	 * @author F-zx
	 * 对compareable实现，目的在于对该类进行排序，按照pixNum排序，其实可以不用排序，这里只是想复习一个该接口的实现
	 */
	public static class CharArea implements Comparable<CharArea>{
		public int pixNum ;//该字符所有的有色点的个数
		public char c;//该字符
		
		public CharArea(int pixNum , char c) {
			this.c = c;
			this.pixNum = pixNum;
		}
		
		

		@Override
		public int compareTo(CharArea o) {
			if(o.pixNum <= this.pixNum)
				return 1;
			else
				return -1;
		}
	}
	
	private final static int NUM = 92;
	private final static int HEIGHT = 50;
	private final static int FONT_SIZE = 50;
	private final static int WIDTH = FONT_SIZE * NUM ;
	
	private static StringBuffer buffer = null;
	private static File file = null;
	private static BufferedImage bImage  = null;
	
	private static CharArea[] chars = new CharArea[NUM];
	

	
	/** 
	 * 建立一个字符的序列
	 * @return
	 */
	private static String build(){
		buffer = new StringBuffer();
		
		//取ASCII码的33----125 92个
		for(int i = 33 ; i < 125 ; i++)
			buffer.append((char)i);
		
		return buffer.toString();
		
	}
	
	/**
	 * 绘制出相应的图片
	 */
	private static void paint(){
		file = new File("e://resources/char.jpg");	
		
		bImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_BINARY);
		
		Font font = new Font(null, Font.PLAIN, FONT_SIZE);
		
		Graphics2D g = bImage.createGraphics();
		g.setBackground(Color.white);
		g.setColor(Color.black);
		g.setFont(font);
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		String strs = build();
		char[] c = strs.toCharArray();
		
		int i = 0 ;
		for (char d : c) {
			g.drawString(""+d, FONT_SIZE * ( i++ ), 40);
		}
		
		g.dispose();
		
		try {
			ImageIO.write(bImage, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
	}
	
	/**
	 * 计算出没个的面积，然后排序一下，这里计算没个区域（size*size）的大小，在其中寻找有色点的个数，然后对这些进行排序，引入一个数据结构,
	 * ps：对于每个点，-1表示该点为白色
	 * @throws IOException 
	 */
	private static void calculate() throws IOException{

		if(file == null)
			file = new File("e://resources/char.jpg");	
		if(bImage == null)
			bImage = ImageIO.read(file);
		
		int count = 0;//每个字符的有色点个数
		for(int j = 1 ; j <= NUM ; j ++){					//个数循环
			count = 0;
			chars[j-1] = new CharArea( 0 , (char) (j+32) ); //因为字符是从33开始的，所以j+32对应的是33的起始位置
			for(int k = (j-1)*50 ; k < 50*j ; k++){			//没个字符的宽度循环
				for(int m = 0 ; m < 50 ; m++ ){				//每个字符的高度循环
					int rgb = bImage.getRGB(k, m);
					
					count += (rgb != -1) ? 1: 0;
//					System.out.println(rgb); //测试点的颜色输出
				}
			}
			chars[j-1].pixNum = count;
		}
	}
	
	/**
	 * @return 返回计算好的字符的
	 * @throws IOException
	 */
	public static CharArea[] getArrays() throws IOException{
		paint();
		calculate();
		Arrays.sort(chars);
		for (CharArea area : chars) {
			System.out.println(area.c+" "+area.pixNum);
		}
		
		return chars;
	}
	
	public static void main(String[] args) throws IOException {
		paint();
		calculate();
		
//		for (CharArea area : chars) {
//			System.out.println(area.c+" "+area.pixNum);
//		}
		
		Arrays.sort(chars);
		
		for (CharArea area : chars) {
			System.out.println(area.c+" "+area.pixNum);
		}
		
//		System.out.printf("%2s", "n");
	}
}

