import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ScreenReaderTest {

	public static void main(String[] args) throws AWTException, IOException, InterruptedException {
		

		

		// whole screen
		Rectangle wholeScreenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot myRobot =	new Robot();
		int width = (int)wholeScreenRectangle.getWidth();
		int height = (int)wholeScreenRectangle.getHeight();
		//if(print)System.out.println();
		StringBuffer card = new StringBuffer();
		Vector communitycards = new Vector();
		Vector holecards = new Vector();
		boolean print = true;
		double averageWinPercentage = 0;
		if(print)System.out.println(wholeScreenRectangle.toString());
		ValueCheck myValueCheck = new ValueCheck();

		int numberCardsOnScreen = 0;
		int numOtherPlayers = 0;
		boolean keepPlaying = false;
		
//		int a = 4;
//		int b = 30;
//		System.out.println("top left of window (x,y): ( " + a + " , "+ b + " ) color (integerform):" + myBufferedImage.getRGB(a,b));

		boolean findWindow = true;
		int topLeftX = 0;
		int topLeftY = 0;
		int bottomRightX = 1280;
		int bottomRightY = 768;
		BufferedImage myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
		while(findWindow){
			for(int y=0;y<height; y++){
				for(int x=0;x<width;x++){
					if(x+791<width && y+541<height){	
						if(myBufferedImage.getRGB(x, y) == -2374261 && myBufferedImage.getRGB(x+791, y+545) == -4678566){
							topLeftX = x;
							topLeftY = y;
							bottomRightX = x+791;
							bottomRightY = y+545;
							x=width;
							y=height;
						}
					}
				}
			}
			findWindow = false;
			System.out.println("Window top left ( x, y ): (" + topLeftX + ", " + topLeftY + ") bottom right ( x, y ): " + "( " + bottomRightX + ", " + bottomRightY + " )");
			if(myBufferedImage.getRGB(topLeftX+398, topLeftY+33) == -15396851){
				//System.out.println("Pot is Present!");
			}
			else{
				//System.out.println("No Pot!");
			}
		}
boolean playAnotherHand = true;
while(playAnotherHand){		
		boolean newPot = false;
		int lastPotRGB = 0;
		boolean clickedTimeOut = false;
		boolean clickedImBack = false;
		while(!newPot){
			myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
			//if timed out window click ok
			
			//if sitting out click im back
			
			int currentPotRGB = myBufferedImage.getRGB(topLeftX+398, topLeftY+33);
			if(lastPotRGB==-5404045 && lastPotRGB!=-15396851){
				if(currentPotRGB==-15396851){
					//System.out.println("lastPotRGB: " + lastPotRGB);
					newPot=true;
				}
			}
			lastPotRGB=currentPotRGB;
			//hand timed out, click ok
			if(!clickedTimeOut && myBufferedImage.getRGB(topLeftX+296, topLeftY+270)==-1250856 && myBufferedImage.getRGB(topLeftX+496, topLeftY+270)==-1250856){
				Thread.sleep(1000);
				myRobot.mouseMove(topLeftX+392, topLeftY+297);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(200);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(200);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				clickedTimeOut = true;
			}
			//sitting out, click i'm back
			if(!clickedImBack && myBufferedImage.getRGB(topLeftX+553, topLeftY+466)==-16777216 && myBufferedImage.getRGB(topLeftX+658, topLeftY+466)==-16777216 && myBufferedImage.getRGB(topLeftX+600, topLeftY+478)==-16777216 && myBufferedImage.getRGB(topLeftX+600, topLeftY+455)==-16777216){
				Thread.sleep(1000);
				myRobot.mouseMove(topLeftX+599, topLeftY+467);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(200);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(200);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				clickedImBack = true;
			}
			
			//look for hole cards here
			for (int y = topLeftY; y < bottomRightY; y++) {
				for (int x = topLeftX; x < bottomRightX; x++) {
					if (x + 29 < bottomRightX && y + 55 < bottomRightY
							&& y - 19 > topLeftY && x - 29 > topLeftX) {
						if (myBufferedImage.getRGB(x, y) == -6708261
								&& myBufferedImage.getRGB(x + 29, y) == -6708261
								&& myBufferedImage.getRGB(x, y + 55) == -6708261
								&& myBufferedImage.getRGB(x + 29, y + 55) == -6708261) {
							// make sure x,y is at top left of blue/purple
							// boarder
							if (myBufferedImage.getRGB(x, y - 1) == -1
									&& myBufferedImage.getRGB(x - 1, y) == -1) {
								if (myBufferedImage.getRGB(x - 18, y + 27) == -1) {
									y=bottomRightY;
									x=bottomRightX;
									newPot = true;
								}
							}
						}
					}
				}
			}
		}
		Rectangle myRectangle = new Rectangle(topLeftX, topLeftY, 791,545);
		System.out.println(myRectangle.toString());
		//wait until a card is seen then do this

		int numDealtIn = 0;
		int firstNumDealtIn=0;
		boolean checkNumberDealtIn = true;
		while(checkNumberDealtIn){
			myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
			for (int y = topLeftY; y < bottomRightY; y++) {
				for (int x = topLeftX; x < bottomRightX; x++) {
					if(x+15<bottomRightX && y+24<bottomRightY){
						if(x+15<bottomRightX && y+24<bottomRightY){	
							if (myBufferedImage.getRGB(x, y) == -547436
									&& myBufferedImage.getRGB(x + 15, y) == -547436
									&& myBufferedImage.getRGB(x, y + 24) == -547436
									&& myBufferedImage.getRGB(x + 15, y + 24) == -547436) {
										numDealtIn++;
										firstNumDealtIn++;
							}
						}
					}
				}
			}
			//System.out.println("numDealtIn: " + numDealtIn);
			File f1 = new File("C:/screenshot1.jpg");
			javax.imageio.ImageIO.write(myBufferedImage,"jpg",f1);
			long start = System.currentTimeMillis();
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				System.out.println("InterruptedException: " + ie);
			}
			long end = System.currentTimeMillis();
			//System.out.println(".1 second sleep and wait for all cards to be dealt: " + (end - start) + " ms");
			myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
			f1 = new File("C:/screenshot2.jpg");
			javax.imageio.ImageIO.write(myBufferedImage,"jpg",f1);
			if (numDealtIn > 0) {
				numDealtIn=0;
				for (int y = topLeftY; y < bottomRightY; y++) {
					for (int x = topLeftX; x < bottomRightX; x++) {
						if(x+15<bottomRightX && y+24<bottomRightY){
							if (myBufferedImage.getRGB(x, y) == -547436
									&& myBufferedImage.getRGB(x + 15, y) == -547436
									&& myBufferedImage.getRGB(x, y + 24) == -547436
									&& myBufferedImage.getRGB(x + 15, y + 24) == -547436) {
										numDealtIn++;
										checkNumberDealtIn = false;
							}
						}
					}
				}
			}
			//if someone folds quickly
			if(firstNumDealtIn>numDealtIn){
				numDealtIn = firstNumDealtIn;
			}
			if(checkNumberDealtIn){
				numDealtIn = 0;
				firstNumDealtIn = 0;
			}
			//System.out.println("numDealtIn: " + numDealtIn);
		}
		//System.out.println("numDealtIn (not including me): " + numDealtIn);
		keepPlaying = true;
		String lastHandStage = "";
		int timesHandStageChecked = 0;
		double lastHandValueForHandStage = 0;
		double lastSplitPercentageForHandStage = 0;		
		int numberOfBets = 0;
		int numberBetsAlreadyIn = 0;
		while (keepPlaying) {
			numOtherPlayers = 0;
			myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
			if(myBufferedImage.getRGB(topLeftX+398, topLeftY+33)==-5404045){
				keepPlaying = false;
			}
			for (int y = topLeftY; y < bottomRightY && keepPlaying; y++) {
				for (int x = topLeftX; x < bottomRightX; x++) {
					// 774 is top edge
					// if(y==775 && x<145 && x>109){
					// if(print)if(print)System.out.println(myBufferedImage.getRGB(x,y));
					// if(print)if(print)System.out.println("(x,y): ( " + x + "
					// , "+
					// y + " ) color
					// (integer form):" + myBufferedImage.getRGB(x,y));
					// } 290 193 319 248
					if(x+15<bottomRightX && y+24<bottomRightY){
						if (myBufferedImage.getRGB(x, y) == -547436
								&& myBufferedImage.getRGB(x + 15, y) == -547436
								&& myBufferedImage.getRGB(x, y + 24) == -547436
								&& myBufferedImage.getRGB(x + 15, y + 24) == -547436) {
									numOtherPlayers++;
						}
					}
					if (x + 29 < bottomRightX && y + 55 < bottomRightY
							&& y - 19 > topLeftY && x - 29 > topLeftX) {
						if (myBufferedImage.getRGB(x, y) == -6708261
								&& myBufferedImage.getRGB(x + 29, y) == -6708261
								&& myBufferedImage.getRGB(x, y + 55) == -6708261
								&& myBufferedImage.getRGB(x + 29, y + 55) == -6708261) {
							// make sure x,y is at top left of blue/purple
							// boarder
							if (myBufferedImage.getRGB(x, y - 1) == -1
									&& myBufferedImage.getRGB(x - 1, y) == -1) {
								// if(print)System.out.println("top left of
								// blue/purple
								// border ( x, y ): ( " + x + ", " + y + " )");
								numberCardsOnScreen++;
								int tmpX = x - 14;
								int tmpY = y - 5;
								if (print)
									System.out.println();

								// i < 14 will get just value i < 29 will also
								// get
								// suit
								// for(int i=0;i<14;i++){
								card.delete(0, card.length());
								// if(print)System.out.println("empty card: " +
								// card.toString());
								print = false;
								for (int i = 0; i < 14; i++) {
									for (int j = 0; j < 11; j++) {
										if (myBufferedImage.getRGB(tmpX + j,
												tmpY + i) == -1) {
											if (print)
												System.out.print(" ");
											card.append(" ");
										}
										if (myBufferedImage.getRGB(tmpX + j,
												tmpY + i) != -1) {
											if (print)
												System.out.print("0");
											card.append("0");
										}
									}
									if (print)
										System.out.println();
								}
								print = true;
								// check the card
								String cardValue = myValueCheck
										.getCardValue(card.toString());
								// if(print)System.out.println(card.toString());
								// if(print)System.out.println("card value: " +
								// cardValue);

								if (print)
									System.out.println();
								int suit = myBufferedImage
										.getRGB(x - 9, y + 17);
								// if(print)System.out.println("suit int: " +
								// suit);
								if (suit == -16777216) {
									// if(print)System.out.println("card:
									// "+cardValue+"
									// spades");
									if (myBufferedImage.getRGB(x - 18, y + 27) != -1)
										communitycards.add(cardValue + "s");
									if (myBufferedImage.getRGB(x - 18, y + 27) == -1)
										holecards.add(cardValue + "s");
								}
								if (suit == -3667960) {
									// if(print)System.out.println("card:
									// "+cardValue+"
									// hearts");
									if (myBufferedImage.getRGB(x - 18, y + 27) != -1)
										communitycards.add(cardValue + "h");
									if (myBufferedImage.getRGB(x - 18, y + 27) == -1)
										holecards.add(cardValue + "h");
								}
								if (suit == -16776979) {
									// if(print)System.out.println("card:
									// "+cardValue+"
									// diamonds");
									if (myBufferedImage.getRGB(x - 18, y + 27) != -1)
										communitycards.add(cardValue + "d");
									if (myBufferedImage.getRGB(x - 18, y + 27) == -1)
										holecards.add(cardValue + "d");
								}
								if (suit == -15240178) {
									// if(print)System.out.println("card:
									// "+cardValue+"
									// clubs");
									if (myBufferedImage.getRGB(x - 18, y + 27) != -1)
										communitycards.add(cardValue + "c");
									if (myBufferedImage.getRGB(x - 18, y + 27) == -1)
										holecards.add(cardValue + "c");
								}

								// if(print)System.out.println("(x-18,y+27): ( "
								// +
								// (x-18) + " , "+ (y+27) + " ) color
								// (integerform):" +
								// myBufferedImage.getRGB(x,y));

								if (myBufferedImage.getRGB(x - 18, y + 27) == -1) {
									numberCardsOnScreen++;
									tmpX = x - 14 - 24 + 9;
									tmpY = y - 5 + 13 - 17;
									// if(print)System.out.println();
									card.delete(0, card.length());
									print = false;
									for (int i = 0; i < 14; i++) {
										for (int j = 0; j < 11; j++) {
											if (myBufferedImage.getRGB(
													tmpX + j, tmpY + i) == -1) {
												if (print)
													System.out.print(" ");
												card.append(" ");
											}
											if (myBufferedImage.getRGB(
													tmpX + j, tmpY + i) != -1) {
												if (print)
													System.out.print("0");
												card.append("0");
											}
										}
										if (print)
											System.out.println();
									}
									print = true;
									// check the card
									cardValue = myValueCheck.getCardValue(card
											.toString());
									// if(print)System.out.println(card.toString());
									// if(print)System.out.println("card value:
									// " +
									// cardValue);
									if (print)
										System.out.println();
									suit = myBufferedImage.getRGB(x - 24,
											y + 13);
									// if(print)System.out.println("suit int: "
									// +
									// suit);
									if (suit == -16777216) {
										// if(print)System.out.println("card:
										// "+cardValue+" spades");
										holecards.add(cardValue + "s");
									}
									if (suit == -3667960) {
										// if(print)System.out.println("card:
										// "+cardValue+" hearts");
										holecards.add(cardValue + "h");
									}
									if (suit == -16776979) {
										// if(print)System.out.println("card:
										// "+cardValue+" diamonds");
										holecards.add(cardValue + "d");
									}
									if (suit == -15240178) {
										// if(print)System.out.println("card:
										// "+cardValue+" clubs");
										holecards.add(cardValue + "c");
									}
									// if(print)System.out.println("whole cards
									// found!");
								}
							}
						}
					}
				}
			}

		
		Object[] communityCardsObjectArray = communitycards.toArray();
		Object[] holeCardsObjectArray = holecards.toArray();
		int[] communityCardsIntArray = new int[communityCardsObjectArray.length];
		int[] holeCardsIntArray = new int[holeCardsObjectArray.length];


		
		if(print)System.out.println();
		if(print)System.out.print("Community Cards read: ");
		Cards myCards = new Cards();
		for(int i=0;i<communityCardsObjectArray.length;i++){
			communityCardsIntArray[i]=myCards.stringToCard(communityCardsObjectArray[i].toString());
			if(print)System.out.print(communityCardsObjectArray[i].toString()+", ");
		}
		if(print)System.out.println();
		if(print)System.out.print("Hole Cards read: ");
		for(int i=0;i<holeCardsObjectArray.length;i++){
			holeCardsIntArray[i]=myCards.stringToCard(holeCardsObjectArray[i].toString());
			if(print)System.out.print(holeCardsObjectArray[i].toString()+", ");
		}
		if(print)System.out.println();
		HandCheck myHandCheck = new HandCheck();
		//if(communityCardsIntArray.length>1){
		//	if(print)System.out.println("Community Cards Number of Suited Cards: " + myHandCheck.getMostCardsOneSuit(communityCardsIntArray) + " Suit: " + myHandCheck.getMostSuitedSuit(communityCardsIntArray));
		//}
		
		int[] handArray = new int[communityCardsIntArray.length+holeCardsIntArray.length];
		for(int i=0;i<communityCardsIntArray.length;i++){
			handArray[i] = communityCardsIntArray[i];
		}
		for(int i=communityCardsIntArray.length;i<communityCardsIntArray.length + holeCardsIntArray.length;i++){
			handArray[i] = holeCardsIntArray[i-communityCardsIntArray.length];
		}
		if(print)System.out.println();
		if(handArray.length>4){
			int[] bestHand = myHandCheck.getBestHand(handArray);
			String bestHandString = myHandCheck.printHand(bestHand);
			if(print)System.out.println("Best Hand: " + bestHandString);
			//if(print)System.out.println("Combined Number of Suited Cards: " + myHandCheck.getMostCardsOneSuit(handArray) + " Suit: " + myHandCheck.getMostSuitedSuit(handArray));
		}
		boolean raisePresent = false;
		boolean callPresent = false;
		boolean checkPresent = false;
		boolean foldPresent = false;
		boolean buttonsPresent = false;
		String handStage = "";
		if(holeCardsIntArray.length==2 && numOtherPlayers>0){
			if(print)System.out.println("Players Dealt In Hand: " + (numDealtIn+1));
			if(print)System.out.println("Players Still In Hand: " + (numOtherPlayers+1));
			if(communityCardsObjectArray.length==0){
				handStage = "preflop";
			}
			if(communityCardsObjectArray.length>2){
				handStage = "flop";
			}
			if(communityCardsObjectArray.length>3){
				handStage = "turn";
			}
			if(communityCardsObjectArray.length>4){
				handStage = "river";
			}
			int numGames = 5000;
			if(numOtherPlayers<3){
				numGames = 2500;
			}
			if(handStage.equalsIgnoreCase("river")){
				numGames = 1000;
			}
		    //the reason numDealtIn+1 is because numDealtIn is players other than me
			HandValue myHandValue = new HandValue(numGames,(numDealtIn+1));
			if(communityCardsObjectArray.length>2){
				myHandValue.setFlop((String)communityCardsObjectArray[0], (String)communityCardsObjectArray[1], (String)communityCardsObjectArray[2]);
			}
			if(communityCardsObjectArray.length>3){
				myHandValue.setTurn((String)communityCardsObjectArray[3]);
			}
			if(communityCardsObjectArray.length>4){
				myHandValue.setRiver((String)communityCardsObjectArray[4]);
			}
			if(lastHandStage.equalsIgnoreCase(handStage)){
				timesHandStageChecked++;
			}
			if(!lastHandStage.equalsIgnoreCase(handStage)){
				timesHandStageChecked=0;
			}
			
			myHandValue.setPlayerCards(1, (String)holeCardsObjectArray[0], (String)holeCardsObjectArray[1]);
			double handValue = myHandValue.playPoker();
			double splitPercentage = myHandValue.getPlayer1SplitPercentage();
			System.out.println("Hand Value: " + handValue);
			System.out.println("Split Percentage: " + splitPercentage);
			lastHandValueForHandStage = (lastHandValueForHandStage*timesHandStageChecked + handValue)/(timesHandStageChecked+1);
			lastSplitPercentageForHandStage = (lastSplitPercentageForHandStage*timesHandStageChecked + splitPercentage)/(timesHandStageChecked+1);
			System.out.println("Average Values checked against: " + numGames*(timesHandStageChecked+1) + " random hands");
			averageWinPercentage = lastHandValueForHandStage+100/(numDealtIn+1);
			System.out.println("Average Win Percentage (with current hand): " + HandValue.round(averageWinPercentage,2));
			System.out.println("Average Hand Value: " + HandValue.round(lastHandValueForHandStage,2));
			System.out.println("Average Split Percentage: " + HandValue.round(lastSplitPercentageForHandStage,2));
			myBufferedImage = myRobot.createScreenCapture(wholeScreenRectangle);
			//System.out.println("myBufferedImage.getRGB(topLeftX+725, topLeftY+538): " + myBufferedImage.getRGB(topLeftX+725, topLeftY+538));
			if(myBufferedImage.getRGB(topLeftX+725, topLeftY+538) == -13363709 || myBufferedImage.getRGB(topLeftX+725, topLeftY+538) == -13035516){
				System.out.println("Raise or Capped Call Bet Button Present!");
				if(lastHandValueForHandStage>5){
					raisePresent=true;
				}
			}
			//System.out.println("Color: (topLeftX+599, topLeftY+538): " + myBufferedImage.getRGB(topLeftX+599, topLeftY+538));
			if(myBufferedImage.getRGB(topLeftX+599, topLeftY+538) == -13363709 || myBufferedImage.getRGB(topLeftX+599, topLeftY+538) == -13035516){
				//System.out.println("Color: (topLeftX+583,topLeftY+503): " + myBufferedImage.getRGB(topLeftX+583,topLeftY+503));
				if(myBufferedImage.getRGB(topLeftX+583,topLeftY+503)==-340161){
					System.out.println("Call Button Present!");
					callPresent=true;
				}
				if(myBufferedImage.getRGB(topLeftX+583,topLeftY+503)==-7651827){
					System.out.println("Check Button Present!");
					checkPresent=true;
				}
				if(myBufferedImage.getRGB(topLeftX+583,topLeftY+503)==-6929906){
					System.out.println("Check Button Present!");
					checkPresent=true;
				}
				if(myBufferedImage.getRGB(topLeftX+583,topLeftY+503)==-7586291){
					System.out.println("Check Button Present!");
					checkPresent=true;
				}
				
			}
			if(myBufferedImage.getRGB(topLeftX+471, topLeftY+538) == -13363709){
				System.out.println("Fold Button Present!");
				buttonsPresent = true;
				foldPresent=true;
			}
			if(!lastHandStage.equalsIgnoreCase(handStage)){
				numberBetsAlreadyIn=0;
			}
			if(buttonsPresent){
				File f1 = new File("C:/buttons.jpg");
				javax.imageio.ImageIO.write(myBufferedImage,"jpg",f1);
				System.out.println("Time to do something!");
				ChipCounter myChipCounter = new ChipCounter();
				numberOfBets = myChipCounter.getNumberOfBets(topLeftX, topLeftY, bottomRightX, bottomRightY);
				System.out.println("Number of Bets: " + numberOfBets);
				
				
			}
		}
		boolean raise=false;
		boolean call=false;
		boolean checkFold = false;
		int numberOfBetsToCall = (numberOfBets-numberBetsAlreadyIn);

		if(numberOfBetsToCall<0){
			ChipCounter myChipCounter = new ChipCounter();
			numberOfBetsToCall = myChipCounter.getNumberOfBets(topLeftX, topLeftY, bottomRightX, bottomRightY);
			numberBetsAlreadyIn = 0;
		}
		if(buttonsPresent){	
			System.out.println("Bets to Call: " + numberOfBetsToCall);
		}
		
		
		if(print)System.out.println("Players Dealt In Hand: " + (numDealtIn+1));
		if(print)System.out.println("Players Still In Hand: " + (numOtherPlayers+1));
		//(numOtherPlayers+1)/(numDealtIn+1) Greater than 1
		//(numDealtIn+1)/(numOtherPlayers+1) Less than 1
		
		double random = Math.random();
		//numDealtIn refers to other people dealt in, so numDealtIn does not include me
		if(numDealtIn==9){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(13+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(11+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(averageWinPercentage>(14+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(11+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(16+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(11.5+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(16+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(12+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==8){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(14+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(12+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(averageWinPercentage>(15+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(12+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(17+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(13+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(17+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(13+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==7){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(15+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(14+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(averageWinPercentage>(17+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(14+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(19+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(15+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(19+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(15+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==6){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(17+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(16+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(averageWinPercentage>(19+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(16+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(21+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(17+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(21+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(17+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==5){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(19+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(18+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(averageWinPercentage>(21+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(18+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(23+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(19+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(23+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(19+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==4){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(22+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(21+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(averageWinPercentage>(24+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(21+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(26+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(22+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(26+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(22+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==3){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(27+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(26+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(averageWinPercentage>(29+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(26+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(31+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(27+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(31+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(27+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==2){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(35+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(34+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(numberOfBets==0 && random <.6){
					raise = true;
				}
				else if(averageWinPercentage>(37+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(34+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(39+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(35+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(39+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(35+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(numDealtIn==1){
			if(handStage.equalsIgnoreCase("preflop")){
				if(averageWinPercentage>(45+2*numberOfBets-2)&& random > .7){
					raise = true;
				}
				else if(averageWinPercentage>(40+numberOfBetsToCall-1)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("flop")){
				if(numberOfBets==0 && random <.7){
					raise = true;
				}
				else if(averageWinPercentage>(50+2*numberOfBets)&& random > .5){
					raise = true;
				}
				else if(averageWinPercentage>(40+numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("turn")){
				if(averageWinPercentage>(55+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(45+2*numberOfBetsToCall)){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
			else if(handStage.equalsIgnoreCase("river")){
				if(averageWinPercentage>(55+4*numberOfBets)){
					raise = true;
				}
				else if(averageWinPercentage>(45+2*numberOfBetsToCall)){
					call = true;
				}
				else if(lastSplitPercentageForHandStage>50){
					call = true;
				}
				else{
					checkFold = true;
				}
			}
		}
		if(buttonsPresent){
			System.out.println("What I will do:");
			if(raise == true){
				System.out.println("Raise!");
				System.out.println("myBufferedImage.getRGB(topLeftX+725, topLeftY+538): " + myBufferedImage.getRGB(topLeftX+725, topLeftY+538));
				System.out.println("Raise Present: " + raisePresent);
			}
			else if(call == true){
				System.out.println("Call!");
			}
			else if(checkFold == true){
				System.out.println("Check/Fold!");
			}
			else{
				System.out.println("Error!");
			}
		}
		//if nothing is set -> fold
		if(!raise && !call && !checkFold && buttonsPresent){
			System.out.println("Error no action is set");
			checkFold=true;
		}
		//will give a random 100 pixel area for clicking
		int randomAdjustX = (int)(Math.random()*10);
		int randomAdjustY = (int)(Math.random()*10);
		System.out.println("Times HandStage Checked: " + timesHandStageChecked);
		if(buttonsPresent && timesHandStageChecked>0){
			Thread.sleep((int)(Math.random()*300)+1000);
		
			if(raise && raisePresent){
				//click raise or call capped button
				myRobot.mouseMove(topLeftX+725+randomAdjustX, topLeftY+510+randomAdjustY);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				numberBetsAlreadyIn = numberOfBets + 1;
			}
			else if(raise && !raisePresent){
				//click call
				myRobot.mouseMove(topLeftX+600+randomAdjustX, topLeftY+510+randomAdjustY);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
			else if(call && callPresent){
				//click call
				myRobot.mouseMove(topLeftX+600+randomAdjustX, topLeftY+510+randomAdjustY);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				numberBetsAlreadyIn = numberOfBets;
			}
			else if(call && !callPresent){
				//if the betting is capped call button may be on right
				//try clicking check/call normal area first
				myRobot.mouseMove(topLeftX+600+randomAdjustX, topLeftY+510+randomAdjustY);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(100);
				myRobot.mouseMove(topLeftX+725+randomAdjustX, topLeftY+510+randomAdjustY);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				
			}
			else if(checkFold && checkPresent){
				//click check
				myRobot.mouseMove(topLeftX+600+randomAdjustX, topLeftY+510+randomAdjustY);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(100);
			}
			else if(checkFold && !checkPresent && foldPresent){
				//click fold
				myRobot.mouseMove(topLeftX+476+randomAdjustX, topLeftY+510+randomAdjustY);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				myRobot.mouseMove(topLeftX+357, topLeftY+298);
				Thread.sleep(100);
				myRobot.mousePress(InputEvent.BUTTON1_MASK);
				myRobot.mouseRelease(InputEvent.BUTTON1_MASK);
				myRobot.mouseMove(bottomRightX-20, bottomRightY-20);
			}
			//if(buttonsPresent){
			//	myRobot.mouseMove(0,0);
			//}
			//System.out.println("number of cards seen on screen: " + numberCardsOnScreen);
			//System.out.println("holeCardsObjectArray.length: " + holeCardsObjectArray.length);
			//i was not dealt in, or i folded, or no cards on screen
		}
		if(numberCardsOnScreen==0 || holeCardsObjectArray.length==0){
			keepPlaying = false;
		}
		if(holeCardsObjectArray.length>2){
			keepPlaying = false;
		}
		if(numberCardsOnScreen==0 || numberCardsOnScreen==9 || numOtherPlayers==0){
			keepPlaying = false;
		}
		raise = false;
		call = false;
		checkFold = false;
		
		lastHandStage = handStage;
		communitycards.clear();
		holecards.clear();
		if(print)System.out.println();
		if(print)System.out.println("Cards found on screen: " + numberCardsOnScreen);
		numberCardsOnScreen=0;
		myBufferedImage.flush();
		System.out.println("Complete!");
		}
	}
	System.exit(0);
	}
}