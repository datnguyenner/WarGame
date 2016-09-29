import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Card implements Deck, Comparable<Card> {
	
	private List<Integer> cards;
	private int card;
	private int playerNum;

	public Card(){}
	
	public Card(int card){
		this.card = card;
	}

	@Override
	public void create(int numberOfSuits, int numberOfRanks) {
		ArrayList<Integer> cards = new ArrayList<>();
		if(numberOfSuits == 0 || numberOfSuits > 4 || numberOfRanks==0 || numberOfRanks>13){
			System.out.println("Invalid Deck");
		}else{
			for(int i = 2; i < numberOfRanks+2;i++){
				for(int j = 0; j < numberOfSuits; j++){
					cards.add(i);
				}
			}
		}
		
		this.cards = cards;		
	}

	@Override
	public void shuffle() {
		Collections.shuffle(this.cards);
		
	}

	@Override
	public Card deal() {
		int lastCard = this.cards.get(this.cards.size()-1);
		this.cards.remove(this.cards.size()-1);
		return new Card(lastCard);
	}
	@Override
	public String toString() {
		
		return this.card +"";
	}

	public List<Integer> getCards() {
		return cards;
	}

	public int getCard() {
		return card;
	}

	public void setCard(int card) {
		this.card = card;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	@Override
	public int compareTo(Card card1) {
		
		return this.card-card1.getCard();
	}

}
