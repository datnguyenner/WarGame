import java.util.ArrayList;
import java.util.LinkedList;
/**
 * This class/object will contains player's id and all the cards the player is 
 * holding in his/her hand.
 *
 */
public class Player {
	
	private LinkedList<Card> cardInHand = new LinkedList<>();
	private int playerId;
	
	public Player(int id){
		this.playerId=id;
	}
	public Player(){
	}

	public LinkedList<Card> getCardInHand() {
		return cardInHand;
	}
	public void setCardInHand(LinkedList<Card> cardInHand) {
		this.cardInHand = cardInHand;
	}
	public void addCard(Card card){
		cardInHand.add(card);
	}
	public Card removeCard(){
		Card card = cardInHand.removeLast();
		return card;
	}
	public void addCardsOnTable(ArrayList<Card> cards){
		for(Card card: cards){
			cardInHand.addFirst(card);
		}
	}
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	@Override
	public String toString() {
		
		return "Player" + playerId+ cardInHand.toString();
	}

}
