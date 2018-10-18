import java.util.Arrays;
public class HandCheck {
	boolean printAll = true;
	public void noPrint(){
		printAll = false;	
	}
	public void doPrint(){
		printAll = true;
	}
	//used for testing flushes and straight flushes		
	public static int[] sortCards(int[] cards){		
		Arrays.sort(cards);
		return cards;
	}
	//used for testing straights, quads, full houses, trips, and pairs
	public static int[] sortValueCards(int[] cards){
		int[] sortValueArray = new int[cards.length+1];
		for(int i=0;i<cards.length;i++){
			sortValueArray[i+1] = cards[i];
		}
		sortValueArray[0]=-100;
		for(int i=0;i<sortValueArray.length;i++){	
			if(0<=sortValueArray[i] && sortValueArray[i]<13){	
			}
			else if(12<sortValueArray[i] && sortValueArray[i]<26){
				sortValueArray[i] = sortValueArray[i] - 13;
			}
			else if(25<sortValueArray[i] && sortValueArray[i]<39){
				sortValueArray[i] = sortValueArray[i] -26;
			}
			else if(38<sortValueArray[i] && sortValueArray[i]<52){
				sortValueArray[i] = sortValueArray[i] -39;
			}
		}
		Arrays.sort(sortValueArray);
		for(int i=0;i<sortValueArray.length;i++){
			if(sortValueArray[i]==12){
				sortValueArray[0]=-1;	
			}
		}	
		return sortValueArray;
	}
	//handType
	//originally set to 'n' not set
	//'h' high card
	//'p' one pair
	//'d' two pair (d-double)
	//'t' three of a kind (trips)
	//'s' straight
	//'c' flush
	//'f' full house
	//'q' quads
	//'b' straight flush (best)
	//'r' royal flush (even better)
	private char handType = 'n';
	String printHandString = "";
	int firstValueCardInStraight=-150;
	public int[] getBestHand(int[] cards){
		handType = 'n';
		printHandString = "";
		printHand(cards);
		int[] highHand = new int[5];
		cards = sortCards(cards);
		int[] valueCards = new int[cards.length];
		valueCards = sortValueCards(cards);
		int numCards = cards.length;
		if(numCards<5){
			System.out.println("not enough cards for a poker hand (<5)");
			return highHand;
		}
		if(numCards>7){
			System.out.println("too many cards for hold'em");
		}
		int priorCard = -1;
		int priorValueCard = -1;
		int flushCardsInARow = 1;
		int straightCardsInARow = 1;
		int consecutiveCards = 1;
		boolean straight = false;
		boolean flush = false;
		boolean straightFlush = false;
		int[] highFlush = new int[5];
		int[] highStraight = new int[5];
		int[] highStraightFlush = new int[5];
		char inARowTestStraightFlush = 'z';
		char wheelStraightFlushSuit = 'z';
		int middleValueCardInStraight = -1;
		for(int i=0;i<numCards;i++){
			if(priorCard>-1){
				if(getSuit(priorCard)==getSuit(cards[i])){
					flushCardsInARow++;
				}
				if(getSuit(priorCard)!=getSuit(cards[i])){
					flushCardsInARow=1;
					wheelStraightFlushSuit='z';
				}
				inARowTestStraightFlush = inARow(cards[i],priorCard);
				if(inARowTestStraightFlush=='n'){
					straightCardsInARow=1;
					wheelStraightFlushSuit='z';
				}
				if(inARowTestStraightFlush=='y'){
					straightCardsInARow++;
				}
				//if the card value is 2, check if there is an
				//ace of the same suit, could be wheel
				//straight flush
				if ((i + 3) < cards.length) {
					if (cards[i] == 0) {
						if (cards[i + 1] == 1 && cards[i + 2] == 2
								&& cards[i + 3] == 3) {
							for (int l = 0; l < cards.length; l++) {
								if (cards[l] == 12) {
									wheelStraightFlushSuit = 'c';
								}
							}
						}
					}
					if (cards[i] == 13) {
						if (cards[i + 1] == 14 && cards[i + 2] == 15
								&& cards[i + 3] == 16) {
							for (int l = 0; l < cards.length; l++) {
								if (cards[l] == 25) {
									wheelStraightFlushSuit = 'd';
								}
							}
						}
					}
					if (cards[i] == 26) {
						if (cards[i + 1] == 27 && cards[i + 2] == 28
								&& cards[i + 3] == 29) {
							for (int l = 0; l < cards.length; l++) {
								if (cards[l] == 38) {
									wheelStraightFlushSuit = 'h';
								}
							}
						}
					}
					if (cards[i] == 39) {
						if (cards[i + 1] == 40 && cards[i + 2] == 41
								&& cards[i + 3] == 42) {
							for (int l = 0; l < cards.length; l++) {
								if (cards[l] == 51) {
									wheelStraightFlushSuit = 's';
								}
							}
						}
					}
				}
				if(wheelStraightFlushSuit == 'c'){
					highStraightFlush[0]=12;
					for(int j=1;j<5;j++){
						highStraightFlush[j]=j-1;
					}
					straightFlush = true;
					wheelStraightFlushSuit = 'z';
				}
				if(wheelStraightFlushSuit == 'd'){
					highStraightFlush[0]=25;
					for(int j=1;j<5;j++){
						highStraightFlush[j]=j+12;
					}
					straightFlush = true;
					wheelStraightFlushSuit = 'z';	
				}
				if(wheelStraightFlushSuit == 'h'){
					highStraightFlush[0]=38;
					for(int j=1;j<5;j++){
						highStraightFlush[j]=j+25;
					}
					straightFlush = true;
					wheelStraightFlushSuit = 'z';
				}
				if(wheelStraightFlushSuit == 's'){
					highStraightFlush[0]=51;
					for(int j=1;j<5;j++){
						highStraightFlush[j]=j+38;
					}
					straightFlush = true;
					wheelStraightFlushSuit = 'z';
				}	
					if(flushCardsInARow>4){
						for(int j=5;j>0;j--){
							highFlush[j-1]=cards[i+j-5];
							flush = true;
							handType = 'c';
						}
						if(straightCardsInARow>4) {
							if(wheelStraightFlushSuit == 'z'){
								for(int j=5;j>0;j--){
									highStraightFlush[j-1]=cards[i+j-5];
									straightFlush = true;
								}
							}
						}
					}
				priorCard=cards[i];	
				priorValueCard = valueCards[i];			
			}
			if(priorCard==-1){
				priorCard=cards[i];
				priorValueCard = valueCards[i];	
			}
			inARowTestStraightFlush = inARow(cards[i],priorCard);		
			if(inARowTestStraightFlush=='n'){
				straightCardsInARow=1;
			}			
		}
		consecutiveCards=1;
		char consecutiveChar = 'z';
		priorValueCard = -100;
		for(int q=0;q<valueCards.length;q++){
			consecutiveChar = consecutiveTest(valueCards[q],priorValueCard);	
			if(consecutiveChar=='y'){
				consecutiveCards++;
			}
			if(consecutiveChar=='n'){
				consecutiveCards=1;
			}
			if(consecutiveCards==3){
				middleValueCardInStraight = valueCards[q];
				//System.out.println("middleValueInStraight: " + middleValueCardInStraight);
			}
			if(consecutiveCards>5 && consecutiveChar !='s'){
				middleValueCardInStraight++;
				//System.out.println("consecutiveCards: " + consecutiveCards);
				//System.out.println("middleValueInStraight: " + middleValueCardInStraight);
			}
			if(consecutiveCards>4){
				for(int m=0;m<5;m++){
					handType = 's';
					straight = true;		
				}
			}
			priorValueCard = valueCards[q];
		}
		if(straightFlush) {
			if(!sameValue(12,highStraightFlush[4])){
				if(printAll){
					System.out.println("Straight Flush");
				}
				printHandString = ("Straight Flush");
				handType='b';
			}	
			if(sameValue(12,highStraightFlush[4])){
				if(printAll){
					System.out.println("Royal Flush");	
				}
				printHandString = ("Royal Flush");
				handType='r';
			}
			return highStraightFlush;
		}
		multipleFinder(valueCards);
		int[] multipleFive = new int[5];
		multipleFive = multipleHand(cards);
		if(handType=='q'){
			if(printAll){
				System.out.println(printHandString);
			}
			printHand(multipleFive);
			return multipleFive;
		}
		if(handType=='f'){
			if(printAll){	
				System.out.println(printHandString);
			}
			return multipleFive;
		}
		if(flush){
			if(printAll){	
				System.out.println("Flush");
			}
			printHandString = ("Flush");
			return highFlush;
		}
		if(straight){
			if(printAll){
				System.out.println("Straight");
			}
			printHandString = ("Straight");
			firstValueCardInStraight = middleValueCardInStraight-2;
			if(firstValueCardInStraight==-1){
				highStraight = findStraight(12,cards);
			}
			if(firstValueCardInStraight!=-1){
				highStraight = findStraight(firstValueCardInStraight,cards);
			}
			printHand(highStraight);
			return highStraight;
		}
		if(handType=='t' || handType=='d' || handType=='p' || handType=='h'){
			if(printAll){
				System.out.println(printHandString);
			}
		}
		return multipleFive;
	}
	public char getSuit(int card){
		char suit = 'a';
		if(0<=card && card<13){
			suit = 'c';		
		}
		else if(12<card && card<26){
			suit = 'd';
		}
		else if(25<card && card<39){
			suit = 'h';
		}
		else if(38<card && card<52){
			suit = 's';
		}
		return suit;
	}
	public char consecutiveTest(int value1, int value2){
		int tmpValue=-100;
		if(value1==value2){
			return 's';
		}
		if(value1==12){
			tmpValue = -1;			
		}
		if(value2==12){
			tmpValue = -1;			
		}
		if(value1==(value2-1) || value1==(value2+1) || value1==(tmpValue-1) || value1==(tmpValue+1) || value2==(tmpValue-1) || value2==(tmpValue+1)){
			return 'y';
		}
		return 'n';
	}
	private char inARow(int card, int priorCard){
		if(0<=card && card<13){	
		}
		else if(12<card && card<26){
			card = card - 13;
		}
		else if(25<card && card<39){
			card = card -26;
		}
		else if(38<card && card<52){
			card = card -39;
		}
		if(0<=priorCard && priorCard<13){	
		}
		else if(12<priorCard && priorCard<26){
			priorCard = priorCard - 13;
		}
		else if(25<priorCard && priorCard<39){
			priorCard = priorCard -26;
		}
		else if(38<priorCard && priorCard<52){
			priorCard = priorCard -39;
		}
		if(card==priorCard){
			return 's';
		}
		if(card==priorCard+1 || card==priorCard-1){
			return 'y';
		}
		return 'n';
	}
	public String printHand(int[] newCards){
		Cards myCards = new Cards();
		String cardString = "";
		for(int i=0;i<newCards.length-1;i++){
			cardString = cardString + (myCards.cardToString(newCards[i]) + ", ");
		}
		cardString = cardString + myCards.cardToString(newCards[newCards.length-1]);
		return cardString;
	}
	private int[] findStraight(int lowCardWithoutSuit,int[] cards){	
		int straightCardsFound = 0;
		int[] straightCards = new int[5];
		for(int i=0;i<5;i++){		
			for(int j=0;j<cards.length;j++){
				if(sameValue(lowCardWithoutSuit,cards[j])){
					if(straightCardsFound<5){
						straightCards[straightCardsFound]=cards[j];
					}
					straightCardsFound++;
					lowCardWithoutSuit++;
					if(lowCardWithoutSuit==13){
						lowCardWithoutSuit=0;
					}
				}		
			}
		}
		return straightCards;	
	}
	private int[] knownValueCards = new int[5];
	String[] multipleNames = {"Twos","Threes","Fours","Fives","Sixes","Sevens","Eights","Nines","Tens","Jacks","Queens","Kings","Aces"};
	private int[] multipleFinder(int[] valueCards){
		int[] howMany = new int[13];
		for(int i=1;i<valueCards.length;i++){
			howMany[valueCards[i]]++;
		}
		for(int i=12;i>-1;i--){
			if(howMany[i]==4){
				printHandString = ("Four of a Kind, " + multipleNames[i]);
				for(int k=0;k<4;k++){
					knownValueCards[k]=i;
				}
				knownValueCards[4]=-100;
				handType = 'q';
				printKnownValueCards(knownValueCards);
				return knownValueCards;
			}
		}
		for(int i=12;i>-1;i--){
			if(howMany[i]==3){
				for(int j=12;j>-1;j--){
					if(howMany[j]==2 && j!=i){
						printHandString = ("Full House, " + multipleNames[i] + " full of " + multipleNames[j]);
						handType = 'f';
						for(int k=0;k<3;k++){
							knownValueCards[k]=i;
						}
						for(int k=3;k<5;k++){
							knownValueCards[k]=j;
						}
						return knownValueCards;
					}
				}
				if(handType=='n'){
					printHandString = ("Three of  Kind, " + multipleNames[i]);
					handType = 't';
					for(int k=0;k<3;k++){
						knownValueCards[k]=i;
					}
					knownValueCards[3]=-100;
					knownValueCards[4]=-100;
					return knownValueCards;
				}
			}
		}
		for(int i=12;i>-1;i--){
			if(howMany[i]==2){
				for(int j=i-1;j>-1;j--){
					if(howMany[j]==2){
						printHandString = ("Two Pair, " + multipleNames[i] + " and " + multipleNames[j]);
						handType = 'd';
						for(int k=0;k<2;k++){
							knownValueCards[k]=i;
						}
						for(int k=2;k<4;k++){
							knownValueCards[k]=j;
						}
						knownValueCards[4]=-100;
						return knownValueCards;
					}
				}
				if(handType=='n'){
					printHandString = ("A Pair of " + multipleNames[i]);
					handType = 'p';
					for(int k=0;k<2;k++){
						knownValueCards[k]=i;
					}
					for(int k=2;k<5;k++){
						knownValueCards[k]=-100;
					}
					return knownValueCards;
				}
			}
		}
		if(handType=='n'){
			printHandString = ("High Card ");
			handType = 'h';
			for(int k=0;k<5;k++){
				knownValueCards[k]=-100;	
			}
		}
		return knownValueCards;
	}
	private void printKnownValueCards(int[] knownValueCards){
		for(int i=0;i<5;i++){
		}
	}
	private int[] multipleHand(int cards[]){
		int[] hand = new int[5];
		int value = -10;
		int[] copyCards = new int[cards.length];
		int[] valueCopyCards = new int[cards.length];
		int topValue = -1000;
		for(int i=0;i<cards.length;i++){
			copyCards[i] = cards[i];
		}
		boolean foundCard = false;	
		for(int i=0;i<5;i++){
			if(knownValueCards[i]!=-100){	
				value = knownValueCards[i];
			}
			if(i==0 && knownValueCards[0]==-100 && knownValueCards[1]==-100 && knownValueCards[2]==-100 && knownValueCards[3]==-100 && knownValueCards[4]==-100){				
				int[] valueArray = new int[7];
				valueArray = sortValueCards(cards);
				value = valueArray[valueArray.length-1];
			}
			foundCard = false;
			for(int j=0;j<copyCards.length;j++){
				if(sameValue(value,copyCards[j]) && !foundCard){
					hand[i]= copyCards[j];
					foundCard = true;
					copyCards[j]=-100;
				}
				if(!foundCard && j==copyCards.length-1){
					valueCopyCards = sortValueCards(copyCards);
					if(valueCopyCards[valueCopyCards.length-1]!=-100){
						topValue = valueCopyCards[valueCopyCards.length-1];
					}
					for(int k=0;k<copyCards.length;k++){
						if(sameValue(topValue,copyCards[k])){
							hand[i] = copyCards[k];					
							topValue = -1000;
							copyCards[k]=-100;
							foundCard = true;
						}
					}
				}
			}		
		}
		return hand;
	}
	private boolean sameValue(int oneValue, int card){
		if(card==(oneValue)) return true;
		if(card==(oneValue+13)) return true;
		if(card==(oneValue+26)) return true;
		if(card==(oneValue+39)) return true;
		return false;
	}	
	public static int[] getValueCards(int[] cards){
		int[] valueArray = new int[cards.length+1];	
		for(int i=0;i<cards.length;i++){
			valueArray[i+1] = cards[i];
		}
		valueArray[0]=-100;
		for(int i=0;i<valueArray.length;i++){	
			if(0<=valueArray[i] && valueArray[i]<13){	
			}
			else if(12<valueArray[i] && valueArray[i]<26){
				valueArray[i] = valueArray[i] - 13;
			}
			else if(25<valueArray[i] && valueArray[i]<39){
				valueArray[i] = valueArray[i] -26;
			}
			else if(38<valueArray[i] && valueArray[i]<52){
				valueArray[i] = valueArray[i] -39;
			}
		}
		for(int i=0;i<valueArray.length;i++){
			if(valueArray[i]==12){
				valueArray[0]=-1;	
			}
		}
		return valueArray;
	}
	public char getHandChar(int[] cards){
		getBestHand(cards);
		return handType;
	}
	//return 0 error
	//return 1 player1 wins
	//return 2 player2 wins
	//return 3 split pot
	public int getWinner(int[] cards1, int[] cards2){
		boolean print = printAll;
		if(print){
			noPrint();
		}
		char player1HandType = 'n';
		char player2HandType = 'n';
		player1HandType = getHandChar(cards1);
		player2HandType = getHandChar(cards2);
		if(print){
			doPrint();
		}
		if(player1HandType != player2HandType){
			//royal flush
			if(player1HandType == 'r') return 1;
			if(player2HandType == 'r') return 2;
			//straight flush
			if(player1HandType == 'b') return 1;
			if(player2HandType == 'b') return 2;
			//quads
			if(player1HandType == 'q') return 1;
			if(player2HandType == 'q') return 2;
			//full house
			if(player1HandType == 'f') return 1;
			if(player2HandType == 'f') return 2;
			//flush
			if(player1HandType == 'c') return 1;
			if(player2HandType == 'c') return 2;
			//straight
			if(player1HandType == 's') return 1;
			if(player2HandType == 's') return 2;
			//trips
			if(player1HandType == 't') return 1;
			if(player2HandType == 't') return 2;
			//two pair
			if(player1HandType == 'd') return 1;
			if(player2HandType == 'd') return 2;
			//pair
			if(player1HandType == 'p') return 1;
			if(player2HandType == 'p') return 2;
		}
		//if the players have the same hand type
		//see if one is better or if they tie
		if(player1HandType == player2HandType){
			char sameHandType = player1HandType;
			//royal flush (on the board)
			//only need to test player1HandType
			if(sameHandType == 'r') return 3;
			//after odd case of royal flush by both players
			//meaning royal flush on the board we will need the
			//value of the cards in both players best hands
			if(printAll){
				noPrint();
			}
			int[] player1ValueHand = getValueCards(getBestHand(cards1));
			int[] player2ValueHand = getValueCards(getBestHand(cards2));
			if(printAll) doPrint();
			//straight flush (high card will be on the right)
			if(sameHandType == 'b'){
				if(player1ValueHand[5] == player2ValueHand[5]) return 3;
				if(player1ValueHand[5] > player2ValueHand[5]) return 1;
				if(player1ValueHand[5] < player2ValueHand[5]) return 2;		
			}
			//quads, 4 of a kind starts on left X, X, X, X, kicker
			if(sameHandType =='q'){
				if(player1ValueHand[1] != player2ValueHand[1]){
					if(player1ValueHand[1] > player2ValueHand[1]) return 1;
					if(player1ValueHand[1] < player2ValueHand[1]) return 2;
				}
				//same quads, must check kicker
				if(player1ValueHand[5] != player2ValueHand[5]){
					if(player1ValueHand[5] > player2ValueHand[5]) return 1;
					if(player1ValueHand[5] < player2ValueHand[5]) return 2;
				}
				return 3;
			}
			//full house A, A, A, B, B
			if(sameHandType == 'f'){
				if(player1ValueHand[1] != player2ValueHand[1]){
					if(player1ValueHand[1]>player2ValueHand[1]) return 1;
					if(player1ValueHand[1]<player2ValueHand[1]) return 2;
				}
				if(player1ValueHand[5] != player2ValueHand[5]){
					if(player1ValueHand[5] > player2ValueHand[5]) return 1;
					if(player1ValueHand[5] < player2ValueHand[5]) return 2;
				}
				//same full house - split pot
				return 3;
			}
			//flush i am thinking about changing the order displayed
			//so high card right now is on right
			//but would like to start flush high card on left
			//in this test it does not matter
			if(sameHandType == 'c'){
				//sort will put values low -> high
				//if flush is changed remove the sorts
				Arrays.sort(player1ValueHand);
				Arrays.sort(player2ValueHand);
				for(int i=player1ValueHand.length-1;i>0;i--){
					if(player1ValueHand[i] > player2ValueHand[i]) return 1;
					if(player2ValueHand[i] > player1ValueHand[i]) return 2;
				}
				//same flush cards
				return 3;
			}
			//straight low to high
			if(sameHandType == 's'){
				if(player1ValueHand[5]!=player2ValueHand[5]){
					if(player1ValueHand[5]>player2ValueHand[5]) return 1;
					if(player1ValueHand[5]<player2ValueHand[5]) return 2;
				}
				return 3;
			}
			//trips X, X, X, kicker1, kicker2
			if(sameHandType == 't'){
				if(player1ValueHand[1] == player2ValueHand[1]){
					for(int j=4;j<6;j++){
						if(player1ValueHand[j] > player2ValueHand[j]) return 1;
						if(player2ValueHand[j] > player1ValueHand[j]) return 2;
					}
					return 3;
				}
				if(player1ValueHand[1] > player2ValueHand[1]) return 1;
				if(player1ValueHand[1] < player2ValueHand[1]) return 2;
			}
			//two pair A, A, B, B, kicker (A > B)
			if(sameHandType == 'd'){
				if(player1ValueHand[1] != player2ValueHand[1]){
					if(player1ValueHand[1] > player2ValueHand[1]) return 1;
					if(player1ValueHand[1] < player2ValueHand[1]) return 2;
				}
				if(player1ValueHand[3] != player2ValueHand[3]){
					if(player1ValueHand[3] > player2ValueHand[3]) return 1;
					if(player1ValueHand[3] < player2ValueHand[3]) return 2;
				}	
				if(player1ValueHand[5] > player2ValueHand[5]) return 1;
				if(player2ValueHand[5] > player1ValueHand[5]) return 2;
				return 3;
			}
			// A, A, kicker1, kicker2, kicker3 (kicker1 > kicker2 > kicker3)
			if(sameHandType == 'p'){
				if(player1ValueHand[1] != player2ValueHand[1]){
					if(player1ValueHand[1] > player2ValueHand[1]) return 1;
					if(player1ValueHand[1] < player2ValueHand[1]) return 2;
				}
				for(int k=3;k<6;k++){
					if(player1ValueHand[k] > player2ValueHand[k]) return 1;
					if(player2ValueHand[k] > player1ValueHand[k]) return 2;
				}
				return 3;
			}
			if(sameHandType == 'h'){
				for(int l=1;l<6;l++){
					if(player1ValueHand[l] > player2ValueHand[l]) return 1;
					if(player2ValueHand[l] > player1ValueHand[l]) return 2;
				}
				return 3;
			}			
		}
		return 0;
	}
	public String getHandString(int[] cards){
		getBestHand(cards);
		return printHandString;
	}
	
	char mostSuitedSuit = 'n';
	public int getMostCardsOneSuit(int[] cards){
		int numCards = cards.length;
		Arrays.sort(cards);
		int priorCard = cards[0];
		int flushCardsInARow = 1;
		int mostCardsOneSuit = 1;
		
		for(int i=1;i<numCards;i++){
			if(getSuit(priorCard)==getSuit(cards[i])){
				flushCardsInARow++;
				if(flushCardsInARow>mostCardsOneSuit){
					mostCardsOneSuit = flushCardsInARow;
					mostSuitedSuit = getSuit(priorCard);
				}
			}
			if(getSuit(priorCard)!=getSuit(cards[i])){
				flushCardsInARow=1;
			}
			priorCard = cards[i];
		}
		return mostCardsOneSuit;
	}
	public char getMostSuitedSuit(int[] cards){
		getMostCardsOneSuit(cards);
		return mostSuitedSuit;
	}
}