package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Models a deck of 52 cards.
 */
public class Deck extends BaseStack
{

	/**
	 * Creates a new deck of 52 cards, shuffled.
	 */
	public Deck()
	{
		super();
		shuffle();
	}
	
	/**
	 * Reinitializes the deck with all 52 cards, and shuffles them.
	 */
	public void shuffle()
	{
		List<Card> cards = new ArrayList<>();
		for( Suit suit : Suit.values() )
		{
            for( Rank rank : Rank.values() )
            {
                cards.add( Card.get( rank, suit ));
            }
		}
		Collections.shuffle(cards);
		super.setACards(cards);
		//aCards = new CardStack(cards);llll
	}
	
	/**
	 * Places pCard on top of the deck.
	 * @param pCard The card to place on top of the deck.
	 * @pre pCard !=null
	 */
	@Override
	public void push(Card pCard)
	{
		assert pCard != null;
		super.push(pCard);
	}
	
	/**
	 * Draws a card from the deck and removes the card from the deck.
	 * @return The card drawn.
	 * @pre !isEmpty()
	 */
	@Override
	public Card pop()
	{
		assert !isEmpty();
		return super.pop();
	}
	
	/**
	 * @return True iff there are no cards in the deck.
	 */
	@Override
	public boolean isEmpty()
	{
		return super.isEmpty();
	}

	@Override
	public List<Card> getACards() {
		return super.getACards();
	}

	@Override
	public void setACards(List<Card> aCards) {
		super.setACards(aCards);
	}
}
