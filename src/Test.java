import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;


public class Test {

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
	public void test_war_play() {
		War newGame = new War();
		newGame.play(4, 6, 4);
	}

}
