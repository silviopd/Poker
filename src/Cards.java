import java.util.Random;
public class Cards {
	boolean[] cards = new boolean[52];
	boolean test;
	int cardsTaken = 0;
	Random random = new Random();
	int[] communityCards = {-1,-1,-1,-1,-1};
	public boolean cardInDeck(int card){
		if(!cards[card]) return true;
		return false;
	}
	public int takeRandomCard(){
		if(cardsTaken>=52){
			System.out.println("no more cards left");
			return -1;
		}
		int cardInt = (int)(random.nextDouble()*52);
		int random = cardInt;
		if(cards[random]==false){
			cards[random]=true;
			cardsTaken++;
			return random;
		}
		return takeRandomCard();	
	}
	public boolean takeCard(int card){
		if(cards[card]==false){
			cards[card]=true;
			cardsTaken++;
			return true;
		}
		return false;
	}
	public void setFlop(int card1, int card2, int card3){
		if(takeCard(card1) && takeCard(card2) && takeCard(card3)){
			communityCards[0]=card1;
			communityCards[1]=card2;
			communityCards[2]=card3;
		}
	}
	public void setTurn(int turn){
		if(takeCard(turn)){
			communityCards[3]=turn;
		}
	}
	public void setRiver(int river){
		if(takeCard(river)){
			communityCards[4]=river;
		}
	}
	public String cardToString(int randomCard){
		String suit = "";
		String value = "";
		if(0<=randomCard && randomCard<13){
			suit = "c";		
		}
		else if(12<randomCard && randomCard<26){
			suit = "d";
			randomCard = randomCard - 13;
		}
		else if(25<randomCard && randomCard<39){
			suit = "h";
			randomCard = randomCard -26;
		}
		else if(38<randomCard && randomCard<52){
			suit = "s";
			randomCard = randomCard -39;
		}
		if(0<=randomCard && randomCard<9){
			value = Integer.toString(randomCard+2);
			return (value+suit);
		}
		if(randomCard==9){
			value = "J";
			return (value+suit);
		}
		if(randomCard==10){
			value = "Q";
			return (value+suit);
		}
		if(randomCard==11){
			value = "K";
			return (value+suit);
		}
		if(randomCard==12){
			value = "A";
			return (value+suit);
		}
		System.out.print(randomCard + " not valid (0-51 valid)");
		return "";	
	}
	public int stringToCard(String card){
		String suit = card.substring(card.length()-1,card.length());
		suit = suit.toLowerCase();
		String value = card.substring(0,card.length()-1);
		int cardNumber = 0;
		int tempNumber = 52;
		if(value.equalsIgnoreCase("J")){
			cardNumber = 9;
		}
		else if(value.equalsIgnoreCase("Q")){
			cardNumber = 10;
		}
		else if(value.equalsIgnoreCase("K")){
			cardNumber = 11;
		}
		else if(value.equalsIgnoreCase("A")){
			cardNumber = 12;
		}
		if((!value.equalsIgnoreCase("J"))&&(!value.equalsIgnoreCase("Q"))&&(!value.equalsIgnoreCase("K"))&&(!value.equalsIgnoreCase("A")) ){	
			tempNumber = Integer.parseInt(value);
			tempNumber = tempNumber-2;
		}
		if(suit.equalsIgnoreCase("d")){
			cardNumber = cardNumber + 13;	
		}
		if(suit.equalsIgnoreCase("h")){
			cardNumber = cardNumber + 26;
		}
		if(suit.equalsIgnoreCase("s")){
			cardNumber = cardNumber + 39;
		}
		if(tempNumber != 52){
			cardNumber = cardNumber + tempNumber;
		}
		return cardNumber;
	}
	public void reset(){
		cardsTaken = 0;
		for(int i=0;i<cards.length;i++){
			cards[i]=false;
		}
	}
}