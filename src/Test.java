import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Test {

	public int count(List<Integer> deck, int card){
		int count=0;
		for(Integer i : deck){
			if(card==i){
				count++;
			}
		}
		return count;
	}
	@org.junit.Test
	public void test_war_play() { //Create new Deck
		War newGame = new War();
		newGame.play(4, 13, 8);
	}
	
	@org.junit.Test
	public void test_check_winner() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);
		Player player4 = new Player(4);
		
		player1.addCard(new Card(5));
		player2.addCard(new Card(3));
		player2.addCard(new Card(5));
		
		player3.addCard(new Card(3));
		player3.addCard(new Card(4));
		player3.addCard(new Card(2));
		player3.addCard(new Card(4));
		player3.addCard(new Card(5));
		player3.addCard(new Card(2));
		player3.addCard(new Card(3));
		
		player4.addCard(new Card(2));
		player4.addCard(new Card(4));
		
		for(Card card:player1.getCardInHand()){
			card.setPlayerNum(player1.getPlayerId());
		}
		for(Card card:player2.getCardInHand()){
			card.setPlayerNum(player2.getPlayerId());
		}
		for(Card card:player3.getCardInHand()){
			card.setPlayerNum(player3.getPlayerId());
		}
		for(Card card:player4.getCardInHand()){
			card.setPlayerNum(player4.getPlayerId());
		}

		War newGame = new War();
		Player[] players = {player1, player2,player3,player4};
		ArrayList<Card> cardOnTables = newGame.playHand(players);
		newGame.checkForWinner(cardOnTables, players);
		System.out.println(Arrays.toString(players));		
	
	}
	@org.junit.Test
	public void test_deck_creation() {
		Card card = new Card();
		card.create(3, 12);
	    assertThat(card.getCards().size(), is(36));
	    assertThat(count(card.getCards(), 2), is(3));

		card.create(4, 13);
	    assertThat(card.getCards().size(), is(52));
	    assertThat(count(card.getCards(), 5), is(4));
	    assertThat(count(card.getCards(), 1), is(0));
	    assertThat(count(card.getCards(), 14), is(4));
	    
		card.create(5, 13);
	    assertThat(card.getCards().size(), is(0));
	}

}
