public class HandValue {
	int[][] allCards;
	boolean[] playersCardsSet;
	boolean[] communityCardsSet=new boolean[5];
	int numGames;
	int numPlayers;
	int[][] playerSetCards;	
	StringBuffer printFinal = new StringBuffer();
	public HandValue(int numGames, int numPlayers) {
		allCards = new int[7][numPlayers];
		playersCardsSet = new boolean[numPlayers];
		this.numGames = numGames;
		this.numPlayers = numPlayers;
	}
	boolean print = false;
	Cards cards = new Cards();
	public void printEverything(boolean printAll) {
		print = printAll;
	}
	int[] communityCards = new int[5];
	public boolean setPlayerCards(int player, String card1, String card2) {
		if (player > numPlayers) {
			if(print)System.out.println("error player is not available");
			return false;
		}
		player = player - 1;
		if (cards.cardInDeck(cards.stringToCard(card1))
			&& cards.cardInDeck(cards.stringToCard(card2))
			&& (card1 != card2)) {
			allCards[5][player] = cards.stringToCard(card1);
			allCards[6][player] = cards.stringToCard(card2);
			cards.takeCard(cards.stringToCard(card1));
			cards.takeCard(cards.stringToCard(card2));
			playersCardsSet[player] = true;
			return true;
		}
		if(print)System.out.println("error setting player " + (player + 1) + "'s cards");
		return false;
	}
	public void setFlop(String card1, String card2, String card3){
		if (cards.cardInDeck(cards.stringToCard(card1))
				&& cards.cardInDeck(cards.stringToCard(card2))
				&& cards.cardInDeck(cards.stringToCard(card3))) {
				int flop1 = cards.stringToCard(card1);
				int flop2 = cards.stringToCard(card2);
				int flop3 = cards.stringToCard(card3);
				for(int i=0;i<numPlayers;i++){
					allCards[0][i]=flop1;
					allCards[1][i]=flop2;
					allCards[2][i]=flop3;
				}
				communityCardsSet[0]=true;
				communityCardsSet[1]=true;
				communityCardsSet[2]=true;
		}	
	}
	public void setTurn(String turn){
		if(cards.cardInDeck(cards.stringToCard(turn))){
			int turnInt = cards.stringToCard(turn);
			for(int i=0;i<numPlayers;i++){
				allCards[3][i]=turnInt;
			}
			communityCardsSet[3]=true;
		}
	}
	public void setRiver(String river){
		if(cards.cardInDeck(cards.stringToCard(river))){
			int riverInt = cards.stringToCard(river);
			for(int i=0;i<numPlayers;i++){
				allCards[4][i]=riverInt;
			}
			communityCardsSet[4]=true;
		}
	}
	double player1SplitPercentage = 0;
	public double playPoker() {
		long start = System.currentTimeMillis();
		HandCheck handCheck = new HandCheck();
		int[] playerWins = new int[numPlayers];
		int[] playerSplits = new int[numPlayers];
		int splitPot = 0;
		int[][] finalHands = new int[5][numPlayers];
		for (int j = 0; j < numGames; j++) {
			for (int i = 0; i < numPlayers; i++) {
				if (playersCardsSet[i]) {
					cards.takeCard(allCards[5][i]);
					cards.takeCard(allCards[6][i]);
				}
			}
			if (print == false) {
				handCheck.noPrint();
			}
			if (print == true) {
				handCheck.doPrint();
			}
			if (print) {
				if(print)System.out.println(
					"--------------------------------------------------------------");
				if(print)System.out.println("Game " + (j + 1) + "");
			}
			for (int i = 0; i < 5; i++) {
				int tmp=-1;
				if(!communityCardsSet[i]){	
					tmp = cards.takeRandomCard();
				}
				if(communityCardsSet[i]){
					tmp = allCards[i][0];
					cards.takeCard(tmp);
				}
				communityCards[i] = tmp;
				for (int w = 0; w < numPlayers; w++) {
					allCards[i][w] = tmp;
				}
			}
			for (int i = 0; i < numPlayers; i++) {
				if (playersCardsSet[i] == false) {
					allCards[5][i] = cards.takeRandomCard();
					allCards[6][i] = cards.takeRandomCard();
				}
			}
			boolean play = true;
			if (play) {
				if (print) {
					if(print)System.out.println("------------------------------------");
					if(print)System.out.println("Community Cards: ");
					if(print)System.out.println(handCheck.printHand(communityCards) + "");
				}
				for (int t = 0; t < numPlayers; t++) {
					int[] tmpAllCards = new int[7];
					int[] tmpHoleCards = new int[2];
					for (int s = 0; s < 7; s++) {
						tmpAllCards[s] = allCards[s][t];
					}
					if (print) {
						if(print)System.out.println("Player " + (t + 1) + "");
						if(print)System.out.println("--- Hole Cards: ");
						tmpHoleCards[0] = tmpAllCards[5];
						tmpHoleCards[1] = tmpAllCards[6];
						if(print)System.out.println(handCheck.printHand(tmpHoleCards)+"");
						handCheck.noPrint();
					}
					int[] tmpBestHand = handCheck.getBestHand(tmpAllCards);
					handCheck.noPrint();
					String bestHand = handCheck.getHandString(tmpAllCards);
					if (print) {
						if(print)System.out.println("--- " + bestHand +"");
						if(print)System.out.println(
							"--- Best Hand: " + handCheck.printHand(tmpBestHand) + "");
					}
					for (int s = 0; s < 5; s++) {
						finalHands[s][t] = tmpBestHand[s];
					}
				}
				int firstCheck = -1;
				int secondCheck = -1;
				int winner = -1;
				int tmpBestHand1[] = new int[5];
				int tmpBestHand2[] = new int[5];
				boolean lastSplit = false;
				boolean[] sameWinningHand = new boolean[numPlayers];
				for (int t = 0; t < numPlayers - 1; t++) {
					if (t == 0) {
						for (int s = 0; s < 5; s++) {
							tmpBestHand1[s] = finalHands[s][t];
							tmpBestHand2[s] = finalHands[s][t + 1];
						}
						//winner is 1 or 2 (1-left,2-right) 3 split
						winner = handCheck.getWinner(tmpBestHand1, tmpBestHand2);
						if (winner == 3) {
							sameWinningHand[0] = true;
							sameWinningHand[1] = true;
							lastSplit = true;
							winner = 2;
						}
					}
					if (t > 0) {
						firstCheck = winner;
						secondCheck = t + 2;
						for (int s = 0; s < 5; s++) {
							tmpBestHand1[s] = finalHands[s][firstCheck - 1];
							tmpBestHand2[s] = finalHands[s][t + 1];
						}
						winner = handCheck.getWinner(tmpBestHand1, tmpBestHand2);
						if (winner == 3) {
							if (lastSplit == true) {
								sameWinningHand[t + 1] = true;
							}
							if (lastSplit != true) {
								for (int y = 0; y < numPlayers; y++) {
									sameWinningHand[y] = false;
								}
								sameWinningHand[firstCheck - 1] = true;
								sameWinningHand[secondCheck - 1] = true;
							}
							winner = secondCheck;
							lastSplit = true;
						}
						boolean check = true;
						if (winner == 1 && check) {
							winner = firstCheck;
							check = false;
						}
						if (winner == 2 && check) {
							winner = secondCheck;
							check = false;
							lastSplit = false;
						}
						check = true;
					}
				}
				if (!lastSplit) {
					playerWins[winner - 1]++;
					if (print) {
						if(print)System.out.println("*************");
						if(print)System.out.println("*Player " + winner + " Wins!*");
						if(print)System.out.println("*************");
					}
				}
				if (lastSplit) {
					if (print) {
						if(print)System.out.println("Split pot between Players: ");
					}
					splitPot++;
					for (int u = 0; u < numPlayers; u++) {
						if (sameWinningHand[u] == true) {
							if (print) {
								if(print)System.out.println((u + 1) + ", ");
							}
							playerSplits[u]++;
						}
					}
					if (print) {
						if(print)System.out.println("");
					}
				}
				if (print) {
					if(print)System.out.println("------------------------------------");
				}
			}
			cards.reset();
		}
		long end = System.currentTimeMillis();
		long totalTime = end - start;
		if(print)System.out.println(
			"---------------------------------------------------");
		if(print)System.out.println("Statistics for " + numGames + " hands");
		boolean printCommunity = true;
		if(printCommunity && communityCardsSet[0]&&communityCardsSet[1]&&communityCardsSet[2]&&communityCardsSet[3]){
			if(print)System.out.println("Community Cards (preset): " + cards.cardToString(communityCards[0])+", "+ cards.cardToString(communityCards[1])+", "+ cards.cardToString(communityCards[2]) + ", "+ cards.cardToString(communityCards[3])+"");
			printCommunity = false;
		}
		if(printCommunity && communityCardsSet[0]&&communityCardsSet[1]&&communityCardsSet[2]){
			if(print)System.out.println("Community Cards (preset): " + cards.cardToString(communityCards[0])+", "+ cards.cardToString(communityCards[1])+", "+ cards.cardToString(communityCards[2])+"");
		}
		
		for (int i = 0; i < numPlayers; i++) {
			if(print)System.out.println("Player " + (i + 1));
			if (playersCardsSet[i]) {
				if(print)System.out.println(
					" Hole Cards: "
						+ cards.cardToString(allCards[5][i])
						+ ", "
						+ cards.cardToString(allCards[6][i])
						+ " (preset)");
			}
			if (!playersCardsSet[i]) {
				if(print)System.out.println("");
			}
			if(print)System.out.println(
				"--- Win Percentage: "
					+ (double) playerWins[i] * 100 / (double) numGames
					+ "%");
			if(print)System.out.println(
				"--- Split Percentage: "
					+ (double) playerSplits[i] * 100 / (double) numGames
					+ "%");
		}
		player1SplitPercentage = round(((double) playerSplits[0] * 100 / (double) numGames),2);
		if(print)System.out.println(
			"Overall Split Percententage: "
				+ (double) splitPot * 100 / (double) numGames
				+ "%");
		if(print)System.out.println("Total Time: " + totalTime + " ms");
		if(print)System.out.println(
			"Average Hands Checked / Second: "
				+ (double) numGames * 1000 / (double) totalTime + "");
		if(print)System.out.println(
			"---------------------------------------------------");
		double percentWin = (double) playerWins[0] * 100 / (double) numGames;
		System.out.println("Win Percentage (against random hands):" + round(percentWin,2) + "%");
		double averageWin = ((double)100/(double)numPlayers);
		System.out.println("Average Random Win Percentage: " + round(averageWin,2) + "%");
		double handValue = percentWin-averageWin;
		return round(handValue,2);
	}
	//rounds a double to some number of decimal places
    public static double round(double val, int places) {
    	long factor = (long)Math.pow(10,places);
    	val = val * factor;
    	long tmp = Math.round(val);
    	return (double)tmp / factor;
    }
    //only to be used after playPoker(), does not do this automatically
    //will return 0 if playPoker() has not been run
    public double getPlayer1SplitPercentage(){
    	return player1SplitPercentage;
    }
}