package model;

import cards.Card;
import cards.CardStack;
import cards.Rank;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the four piles that must be completed to win the game, with the 
 * Ace at the bottom, face up, and all cards of the same suit on top, in 
 * sequence.
 */
class Foundation
{
	private final Map<FoundationPile, CardStack> aPiles = new HashMap<>();
	
	/**
	 * Creates an initialized FoundationPiles object that consists of four empty piles.
	 */
	Foundation()
	{
		initialize();
	}
	
	/**
	 * @return The total number of cards in all the foundation piles.
	 */
	int getTotalSize()
	{
		int total = 0;
		for( CardStack stack : aPiles.values())
		{
			total += stack.size();
		}
		return total;
	}
	
	/**
	 * Initializes the FoundationPiles object to reset it to four empty piles.
	 */
	void initialize()
	{
		for( FoundationPile index : FoundationPile.values() )
		{
			aPiles.put(index, new CardStack());
		}
	}
	
	/**
	 * @param pLocation The location of the pile to check.
	 * @return True if the pile at pLocation is empty.
	 * @pre pLocation != null.
	 */
	boolean isEmpty(FoundationPile pLocation)
	{
		assert pLocation != null;
		return aPiles.get(pLocation).isEmpty();
	}
	
	/**
	 * @param pCard The card we wish to move.
	 * @param pLocation The desired location for pCard.
	 * @return True if pCard can be moved to the top of pLocation. This is 
	 * 	   only possible if its rank is immediately superior to that of the
	 *     card currently on top of the pile or, in the case of an Ace, if
	 *     the location is empty.
	 * @pre pCard != null && pLocation != null.
	 */
	boolean canMoveTo(Card pCard, FoundationPile pLocation )
	{
		assert pCard != null && pLocation != null;
		if( isEmpty(pLocation))
		{
			return pCard.getARank() == Rank.ACE;
		}
		else
		{
			return pCard.getASuit() == peek(pLocation).getASuit() &&
					pCard.getARank().ordinal() == peek(pLocation).getARank().ordinal() + 1;
		}
	}
	
	/**
	 * @param pLocation The location of the pile to peek at.
	 * @return The card on top of the pile at pLocation.
	 * @pre pLocation != null & !aPiles.get(pLocation).isEmpty();
	 */
	Card peek(FoundationPile pLocation)
	{
		assert pLocation != null && !aPiles.get(pLocation).isEmpty();
		return aPiles.get(pLocation).peek();
	}
	
	/**
	 * Place pCard onto the pile at the desired location.
	 * 
	 * @param pCard The card to place.
	 * @param pLocation The location where to place the card.
	 * @pre pCard != null && pLocation != null
	 */
	void push(Card pCard, FoundationPile pLocation)
	{
		assert pCard != null && pLocation != null;
		aPiles.get(pLocation).push(pCard);
	}
	
	/**
	 * Remove the card at the top of the pile at pLocation, and returns it.
	 * 
	 * @param pLocation The location where to obtain the card.
	 * @pre pLocation != null && !isEmpty(pLocation).
	 */
	Card pop(FoundationPile pLocation)
	{
		assert pLocation != null && !isEmpty(pLocation);
		return aPiles.get(pLocation).pop();
	}
}
