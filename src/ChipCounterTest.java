import java.awt.AWTException;
import java.io.IOException;


public class ChipCounterTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws AWTException 
	 */
	public static void main(String[] args) throws AWTException, IOException {
		ChipCounter myChipCounter = new ChipCounter();
		System.out.println("Number of Bets: " + myChipCounter.getNumberOfBets(4, 30, 795, 575));

	}

}
