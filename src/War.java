import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Rules of the Game
 * -Select number of ranks and number of suits to create a full Deck of Cards.(SUITS don't matter so we will ignore it)
 * -Shuffle Deck
 * -Pass out cards to all the players
 * -Each player will play their hand
 * -If each player has the same card on the table. Then WAR! All the war players continue to draw 1 next card until we have a Winner.
 * -After each round, each player will lose card if they lost the round; Gain cards if they win.
 * -At the end of the Game the player that holds all the cards will win.
 * -Game can continue on forever! In that case, the game will end in a tie.
 */
public class War { 
	
	public void play( int numberOfSuits, int numberOfRanks, int numberOfPlayers ) 
	
	{ 
		Card deck = new Card();
		deck.create(numberOfSuits, numberOfRanks);
		deck.shuffle();
		Player[] playersHand = passOutCard(deck, numberOfPlayers); //Pass out cards to all the players
		System.out.println(Arrays.toString(playersHand));
		boolean continuePlaying = true;
		long startTime = System.currentTimeMillis();
		boolean gameTooLong = false;

		while(continuePlaying){//Keep playing until 
			ArrayList<Card> cardsOnTable = playHand(playersHand);
			System.out.println("Cards on Table "+cardsOnTable);

			System.out.println(Arrays.toString(checkForWinner(cardsOnTable, playersHand)));		
			System.out.println();		

			for(Player player:playersHand){
				if(player.getCardInHand().size()==numberOfSuits*numberOfRanks){
					continuePlaying = false;
				}
			}
			long endTime = System.currentTimeMillis();
			if(endTime - startTime>3000){
				continuePlaying = false;
				gameTooLong=true;
			}
	
		}
		if(gameTooLong){//tie Game!
			System.out.println("We have a tie");
			for(Player player:playersHand){
				if(player.getCardInHand().size()>0){
					System.out.println("Player"+player.getPlayerId()+" is the winner!");
				}
			}		
		}else{
			for(Player player:playersHand){
				if(player.getCardInHand().size()>0){
					System.out.println("Player"+player.getPlayerId()+" is the winner!");
				}
			}
		}
		
	}
	
	
	/**
	 * @param cardsOnTable (current cards on table)
	 * @param playersHand (Current hand of all players)
	 * 
	 * Determine winner of each round
	 */
	public Player[] checkForWinner(ArrayList<Card> cardsOnTable, Player[] playersHand){		

		boolean isWar= checkForWar(cardsOnTable);
		TreeSet<Card> checkCards = new TreeSet<>();

		for(Card card: cardsOnTable){
			checkCards.add(card);
		}
		if(checkCards.size()>0){
			Card winningCard = checkCards.last();
			if(isWar==false){
				for(Player player:playersHand ){
					if(player.getPlayerId()==winningCard.getPlayerNum()){
						for(Card card: cardsOnTable){
							card.setPlayerNum(player.getPlayerId());
						}	
						player.addCardsOnTable(cardsOnTable);
					}
				}
				System.out.println("Player"+winningCard.getPlayerNum()+" wins round!");
				return playersHand;
			}else{
				ArrayList<Player> warPlayers = new ArrayList<>();
				ArrayList<Integer> warPlayersRank = new ArrayList<>();

				for(Card winningWarCard: cardsOnTable){
					if(winningWarCard.getCard()==winningCard.getCard()){
						for(Player player:playersHand){
							if(player.getPlayerId()==winningWarCard.getPlayerNum() && player.getCardInHand().size()>0){
								warPlayers.add(player);
								warPlayersRank.add(player.getCardInHand().size());
							}
						}
						
					}
				}

				Player[] warPlayersArray = new Player[warPlayers.size()];
				warPlayers.toArray(warPlayersArray);
				
				ArrayList<Card> warCardsOnTable = playHand(warPlayersArray);
				Player[] warWinnerPlayer = checkForWinner(warCardsOnTable, warPlayersArray);
				int winnerId = 0;
	 			for(int i= 0; i < warPlayersArray.length; i++){
	 				if(warPlayersRank.get(i)<=warWinnerPlayer[i].getCardInHand().size()){
	 					winnerId=warPlayersArray[i].getPlayerId();
	 					break;
	 				}
	 			}
	 			for(Player winningPlayer: playersHand){
	 				if(winningPlayer.getPlayerId()==winnerId){
						for(Card card: cardsOnTable){
							card.setPlayerNum(winningPlayer.getPlayerId());
						}	
						winningPlayer.addCardsOnTable(cardsOnTable);
	 				}
	 			}		
				return playersHand;

			}
		}else{
			return playersHand;
		}
			
	}
	
	/**
	 * @param deck (deck of cards)
	 * @param numberOfPlayers (number of players in game)
	 * 
	 * Pass out cards to all the players
	 */
	public Player[] passOutCard(Card deck , int numberOfPlayers){
		
		Player[] playersHand = new Player[numberOfPlayers];
		
		for(int i=0, j=1; i < numberOfPlayers; i++,j++){			
			playersHand[i] = new Player(j);
		}
		while(!deck.getCards().isEmpty()){
			for(int i=0; i < numberOfPlayers; i++){			
				Card card = deck.deal();
				card.setPlayerNum(playersHand[i].getPlayerId());
				playersHand[i].addCard(card);
				if(deck.getCards().isEmpty()){
					break;
				}
			}
		}
				
		return playersHand;
	}
	
	/**
	 * @param playersHand (Current hand of all players)
	 * 
	 * 
	 * Play the hand of each player for every round
	 */
	public ArrayList<Card> playHand(Player[] playersHand){

		ArrayList<Card> cardOnTables = new ArrayList<Card>();
		
		for(Player player:playersHand){
			if(player.getCardInHand().size()>0){
				Card cardRemove = player.removeCard();
				cardOnTables.add(cardRemove);
			}
		}
		return cardOnTables;
	}
	
	/**
	 * @param cardsOnTable ()
	 * 
	 * Check for war between players
	 */
	public boolean checkForWar(ArrayList<Card> cardsOnTable){
		
		TreeSet<Card> checkCards = new TreeSet<>();

		for(Card card: cardsOnTable){
			checkCards.add(card);
		}
		if(checkCards.size()>0){
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
		return true;
		
	}	
} 

