import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ChipCounter {
	public int getNumberOfBets(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) throws AWTException, IOException{
		Robot myRobot = new Robot();
		Rectangle myRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage myBufferedImage = myRobot.createScreenCapture(myRectangle);
		int numberTopOfChips = 0;
		File f1 = new File("C:/chips.jpg");
		javax.imageio.ImageIO.write(myBufferedImage, "jpg", f1);
		long start = System.currentTimeMillis();
		int lastChipTmp1 = 0;
		int lastChipX = 0;
		int lastChipY = 0;
		int tmpBets = 0;
		int maxBets = 0;
		//System.out.println("topLeftX: " + topLeftX);
		//System.out.println("topLeftY: " + topLeftY);
		//System.out.println("bottomRightX: " + bottomRightX);
		//System.out.println("bottomRightY: " + bottomRightY);
		for (int y = topLeftY; y < bottomRightY; y++) {
			for (int x = topLeftX; x < bottomRightX; x++) {
				//so i don't look in the pot or where the cards are for number of bet chips
				if(!(((x>(topLeftX+300) && x<(topLeftX+500))&&(y>(topLeftY+150) && y<(topLeftY+270)))||(y<topLeftY+110)||(y>topLeftY+370))){
				
					int tmp1 = myBufferedImage.getRGB(x, y);
					int tmp2 = myBufferedImage.getRGB(x, y+1);
					int tmp3 = myBufferedImage.getRGB(x, y+2);
					int tmp4 = myBufferedImage.getRGB(x, y+3);
					int tmp5 = myBufferedImage.getRGB(x+13, y);
					int tmp6 = myBufferedImage.getRGB(x+13, y+1);
					int tmp7 = myBufferedImage.getRGB(x+13, y+2);
					int tmp8 = myBufferedImage.getRGB(x+13, y+3);
					if((tmp1==-7434867 && tmp2==-4079167 && tmp3==-4079167 && tmp4==-7434867 && tmp5==-7434867 && tmp6==-4079167 && tmp7==-4079167 && tmp8==-7434867)
							|| (tmp1==-8291455 && tmp2==-4211265 && tmp3==-4211265 && tmp4==-8291455 && tmp5==-8291455 && tmp6==-4211265 && tmp7==-4211265 && tmp8==-8291455)
							|| (tmp1==-7435893 && tmp2==-3947837 && tmp3==-3947837 && tmp4==-7435893 && tmp5==-1 && tmp6==-1 && tmp7==-1 && tmp8==-1)
							|| (tmp1==-8289925 && tmp2==-4210755 && tmp3==-4210755 && tmp4==-8289925 && tmp5==-8289925 && tmp6==-4210755 && tmp7==-4210755 && tmp8==-8289925)
							|| (tmp1==-8683135 && tmp2==-4341825 && tmp3==-4341825 && tmp4==-8683135 && tmp5==-8683135 && tmp6==-4341825 && tmp7==-4341825 && tmp8==-8683135)
							|| (tmp1==-8291455 && tmp2==-4211265 && tmp3==-4211265 && tmp4==-8291455 && tmp5==-8291455 && tmp6==-4211265 && tmp7==-4211265 && tmp8==-8291455)
							|| (tmp1==-8289925 && tmp2==-4210755 && tmp3==-4210755 && tmp4==-8289925 && tmp5==-8289925 && tmp6==-4210755 && tmp7==-4210755 && tmp8==-8289925)
							|| (tmp1==-8683141 && tmp2==-4341827 && tmp3==-4341827 && tmp4==-8683141 && tmp5==-8683141 && tmp6==-4341827 && tmp7==-4341827 && tmp8==-8683141)
							|| (tmp1==-8816254 && tmp2==-4408129 && tmp3==-4408129 && tmp4==-8816254 && tmp5==-8816254 && tmp6==-4408129 && tmp7==-4408129 && tmp8==-8816254)){
						numberTopOfChips++;
						//System.out.println("Chip at (x,y): (" + x + "," + y + ")");
						//System.out.println("Last Chip at (x,y): (" + lastChipX + "," + lastChipY + ")");
						if(x==(lastChipX+23) && y==lastChipY && tmp1==lastChipTmp1){
							tmpBets++;
						}
						else{
							tmpBets=1;
						}
						lastChipX = x;
						lastChipY = y;
						lastChipTmp1 = tmp1;
					}
					if(tmpBets > maxBets){
						maxBets = tmpBets;
					}
				}	
			}
		}

		long end = System.currentTimeMillis();
		//System.out.println("Chip Counting took: " + (end-start) + " ms");
		//System.out.println("Number Top of Chips: " + numberTopOfChips);
		//System.out.println("Number of bets: " + maxBets);
		
		
		return maxBets;
	}
}
