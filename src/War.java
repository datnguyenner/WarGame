import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class War { 
	
	public void play( int numberOfSuits, int numberOfRanks, int numberOfPlayers ) 
	
	{ 
		Card deck = new Card();
		deck.create(numberOfSuits, numberOfRanks);
		deck.shuffle();
		HashMap<Integer, LinkedList<Card>> playersHand = passOutCard(deck, numberOfPlayers);
		System.out.println(playersHand);
		boolean continuePlaying = true;
		while(continuePlaying){
			ArrayList<Card> cardsOnTable = playHand(playersHand);

			System.out.println(checkForWinner(cardsOnTable, playersHand));
			for(Entry<Integer, LinkedList<Card>> hand:playersHand.entrySet()){
				
				if(hand.getValue().size()==numberOfSuits*numberOfRanks){
					continuePlaying=false;
				}
			}
			for(Map.Entry<Integer, LinkedList<Card>> entry: playersHand.entrySet()){
				LinkedList<Card> currentPlayerHand = entry.getValue();
				for(Card newCardOwner: currentPlayerHand){
					newCardOwner.setPlayerNum(entry.getKey());
				}
			}
		}
		System.out.println(playersHand);
		
		
	}

	public HashMap<Integer, LinkedList<Card>> checkForWinner(ArrayList<Card> cardsOnTable, HashMap<Integer, LinkedList<Card>> playersHand){		

		boolean isWar= checkForWar(cardsOnTable);
		TreeSet<Card> checkCards = new TreeSet<>();

		for(Card card: cardsOnTable){
			checkCards.add(card);
		}		
		Card winningCard = checkCards.last();
		if(isWar==false){
			LinkedList<Card> winningHand = playersHand.get(winningCard.getPlayerNum());
			for(Card card: cardsOnTable){
				winningHand.addFirst(card);
			}
			playersHand.put(winningCard.getPlayerNum(), winningHand);
			return playersHand;
		}else{
			HashMap<Integer, LinkedList<Card>> newHand = new HashMap<>();
			for(Entry<Integer, LinkedList<Card>> entry:playersHand.entrySet()){
				newHand.put(entry.getKey(), entry.getValue());
			}
			for(Card card: cardsOnTable){
				if(card.getCard()!=winningCard.getCard()){
					newHand.remove(card.getPlayerNum());
				}
			}

			ArrayList<Card> faceDownCards = playHand(newHand);
			
			HashMap<Integer, LinkedList<Card>> playersWarHand = checkForWinner(faceDownCards, newHand);	
			
			int winnerPlayerNum = -1;
			int handSize = -1;
			for(Entry<Integer, LinkedList<Card>> entry: playersWarHand.entrySet()){
				int size = entry.getValue().size();
				if(size>handSize){
					handSize=size;
					winnerPlayerNum=entry.getKey();
				}
			}
			LinkedList<Card> winningHand = playersHand.get(winnerPlayerNum);
			winningHand.addAll(cardsOnTable);
			playersHand.put(winnerPlayerNum, winningHand);
			
			return playersHand;
		}
			
	}

	public HashMap<Integer, LinkedList<Card>> passOutCard(Card deck , int numberOfPlayers){
		
		HashMap<Integer, LinkedList<Card>> playersHand = new HashMap<>();
		
		for(int i=1; i <= numberOfPlayers; i++){
			playersHand.put(i, new LinkedList<>());
		}
		while(!deck.getCards().isEmpty()){
			for(Map.Entry<Integer, LinkedList<Card>> entry: playersHand.entrySet()){
				Card card = deck.deal();
				card.setPlayerNum(entry.getKey());
				LinkedList<Card> currentPlayerHand = entry.getValue();
				currentPlayerHand.add(card);
				playersHand.put(entry.getKey(), currentPlayerHand);
				if(deck.getCards().isEmpty()){
					break;
				}
			}
		}
				
		return playersHand;
	}
	
	public ArrayList<Card> playHand(HashMap<Integer, LinkedList<Card>> playersHand){

		ArrayList<Card> cardOnTables = new ArrayList<Card>();
		
		for(Map.Entry<Integer, LinkedList<Card>> playerHand:playersHand.entrySet()){
			LinkedList<Card> currentPlayerHand = playerHand.getValue();
			if(currentPlayerHand.size()>0){
				Card cardRemove = currentPlayerHand.removeLast();
				cardOnTables.add(cardRemove);
			}
		}
		return cardOnTables;
	}
	public boolean checkForWar(ArrayList<Card> cardsOnTable){
		
		TreeSet<Card> checkCards = new TreeSet<>();

		for(Card card: cardsOnTable){
			checkCards.add(card);
		}
		
		Card winningCard = checkCards.last();
		int countWinningCard=0;
		for(Card card: cardsOnTable){
			if(card.getCard()==winningCard.getCard()){
				countWinningCard++;
			}
		}
		if(countWinningCard==1){
			return false;
		}else{
			return true;
		}
		
	}
	
	
} 

