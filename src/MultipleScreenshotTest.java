import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MultipleScreenshotTest {

	/**
	 * @param args
	 * @throws AWTException, IOException  
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws AWTException, IOException, InterruptedException  {
		Robot myRobot = new Robot();
		Rectangle myRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage myBufferedImage = myRobot.createScreenCapture(myRectangle);
		//System.out.println("1 (55, 61): "+myBufferedImage.getRGB(55, 61));
		int topLeftX = 0;
		int topLeftY = 0;
		int bottomRightX = 850;
		int bottomRightY = 700;
		int numberTopOfChips = 0;
		File f1 = new File("C:/chips.jpg");
		javax.imageio.ImageIO.write(myBufferedImage, "jpg", f1);
		long start = System.currentTimeMillis();
		int lastChipTmp1 = 0;
		int lastChipX = 0;
		int lastChipY = 0;
		int tmpBets = 1;
		int maxBets = 0;
		for (int y = topLeftY; y < bottomRightY; y++) {
			for (int x = topLeftX; x < bottomRightX; x++) {
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
					System.out.println("Chip at (x,y): (" + x + "," + y + ")");
					System.out.println("Last Chip at (x,y): (" + lastChipX + "," + lastChipY + ")");
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

		long end = System.currentTimeMillis();
		System.out.println("Chip Counting took: " + (end-start) + " ms");
		System.out.println("Number Top of Chips: " + numberTopOfChips);
		System.out.println("Number of bets: " + maxBets);
		
/*		System.out.println("left: "+myBufferedImage.getRGB(300, 300));
		System.out.println("right: "+myBufferedImage.getRGB(500, 300));
		File f1 = new File("C:/screenshot1.jpg");
		javax.imageio.ImageIO.write(myBufferedImage,"jpg",f1);
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
			System.out.println("InterruptedException: " + ie);
		}
		long end = System.currentTimeMillis();
		System.out.println("5 second sleep and wait for all cards to be dealt: " + (end - start) + " ms");
		myBufferedImage = myRobot.createScreenCapture(myRectangle);
		f1 = new File("C:/screenshot2.jpg");
		javax.imageio.ImageIO.write(myBufferedImage,"jpg",f1);
		System.out.println("Success Printing Screenshots!");
		
		myRobot.mouseMove(100, 100);
		myRobot.mousePress(InputEvent.BUTTON1_MASK);
		int numMoves = 0;
		for(int y=100;y<201;y++){
			myRobot.mouseMove(100, y);
			myRobot.mouseMove(200, y);
		}

		while(numMoves<10000){
			myRobot.mouseMove((int)(Math.random()*300+100), (int)(Math.random()*200+100));
			numMoves++;
		}

		myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
		// whole screen
		Rectangle wholeScreenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		int width = 1280;
		int height = 768;
		//if(print)System.out.println();
		StringBuffer card = new StringBuffer();
		Vector communitycards = new Vector();
		Vector holecards = new Vector();
		boolean print = true;
		if(print)System.out.println(wholeScreenRectangle.toString());
		ValueCheck myValueCheck = new ValueCheck();

		int numberCardsOnScreen = 0;
		int numOtherPlayers = 0;
		boolean keepPlaying = false;
*/		
//		int a = 4;
//		int b = 30;
//		System.out.println("top left of window (x,y): ( " + a + " , "+ b + " ) color (integerform):" + myBufferedImage.getRGB(a,b));
/*
		boolean findWindow = true;
		int topLeftX = 4;
		int topLeftY = 30;
		int bottomRightX = 1280;
		int bottomRightY = 768;
		myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
		boolean newPot = false;
		int lastPotRGB = 0;
		System.out.println("topleft: " + myBufferedImage.getRGB(671,114));
		System.out.println("topright: " + myBufferedImage.getRGB(700, 114));
		System.out.println("bottomleft: " + myBufferedImage.getRGB(671,169));
		System.out.println("bottomright: " + myBufferedImage.getRGB(700, 169));
		while(!newPot){
			myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
			//if timed out window click ok
			
			//if sitting out click im back
			
			int currentPotRGB = myBufferedImage.getRGB(topLeftX+398, topLeftY+33);
			if(lastPotRGB==-5404045 && lastPotRGB!=-15396851){
				if(currentPotRGB==-15396851){
					System.out.println("lastPotRGB: " + lastPotRGB);
					newPot=true;
				}
			}
			lastPotRGB=currentPotRGB;
			//hand timed out, click ok
			if(myBufferedImage.getRGB(topLeftX+296, topLeftY+270)==-1250856 && myBufferedImage.getRGB(topLeftX+496, topLeftY+270)==-1250856){
				Thread.sleep(1000);
				myRobot.mouseMove(topLeftX+392, topLeftY+297);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				
			}
			//sitting out, click i'm back
			if(myBufferedImage.getRGB(topLeftX+553, topLeftY+466)==-16777216 && myBufferedImage.getRGB(topLeftX+658, topLeftY+466)==-16777216 && myBufferedImage.getRGB(topLeftX+600, topLeftY+478)==-16777216 && myBufferedImage.getRGB(topLeftX+600, topLeftY+455)==-16777216){
				Thread.sleep(1000);
				myRobot.mouseMove(topLeftX+599, topLeftY+467);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
			
			
			topLeftX = 0;
			topLeftY = 0;
			bottomRightX = 850;
			bottomRightY = 700;
			
			for (int y = 0; y < bottomRightY; y++) {
				for (int x = topLeftX; x < bottomRightX; x++) {
					if(myBufferedImage.getRGB(x, y)==-6708261){
						//System.out.println(myBufferedImage.getRGB(x, y)==-6708261);
						//System.out.println(myBufferedImage.getRGB(x+29, y)==-6708261);
						//System.out.println(myBufferedImage.getRGB(x, y+55)==-6708261);
						//System.out.println(myBufferedImage.getRGB(x+29, y+55)==-6708261);
						if(myBufferedImage.getRGB(x+29, y)==-6708261 && myBufferedImage.getRGB(x, y+55)==-6708261 && myBufferedImage.getRGB(x+29, y+55)==-6708261){
							x = bottomRightX;
							y = bottomRightY;
							newPot = true;
						}
					}
				}
			}
			for (int y = 0; y < bottomRightY; y++) {
				for (int x = topLeftX; x < bottomRightX; x++) {
					if(myBufferedImage.getRGB(x, y)==-6708261){
						//System.out.println(myBufferedImage.getRGB(x, y)==-6708261);
						//System.out.println(myBufferedImage.getRGB(x+29, y)==-6708261);
						//System.out.println(myBufferedImage.getRGB(x, y+55)==-6708261);
						//System.out.println(myBufferedImage.getRGB(x+29, y+55)==-6708261);
						if(myBufferedImage.getRGB(x+29, y)==-6708261 && myBufferedImage.getRGB(x, y+55)==-6708261 && myBufferedImage.getRGB(x+29, y+55)==-6708261){
							x = bottomRightX;
							y = bottomRightY;
							newPot = true;
						}
					}
				}
			}
			
		
		}
*/		
		System.exit(1);
	}
	
}