package cards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a general-purpose stack of cards.
 */
public class CardStack extends BaseStack implements Iterable<Card>
{
	/**
	 * Creates an empty CardStack.
	 */
	public CardStack()
	{
		super();
	}
	
	/**
	 * Creates a CardStack that contains all the cards in pCard, in the
	 * iteration order, from bottom to top.
	 * 
	 * @param pCards The cards to initialize the stack with.
	 */
	public CardStack(Iterable<Card> pCards)
	{
		super();
		List<Card> cards = new ArrayList<>();
		for( Card card : pCards )
		{
			cards.add(card);
		}
		super.setACards(cards);
	}
	
	/**
	 * Pushes pCard onto the stack.
	 * 
	 * @param pCard The card to push.
	 * @pre pCard != null;
	 * @pre !aCards.contains(pCard)
	 */
	@Override
	public void push(Card pCard)
	{
		assert pCard != null && !aCards.contains(pCard);
		super.push(pCard);
	}
	
	/**
	 * Removes the card on top of the stack and returns it.
	 * 
	 * @return The card on top of the stack.
	 * @pre !isEmpty()
	 */
	@Override
	public Card pop()
	{
		assert !isEmpty();
		return super.pop();
	}
	
	/**
	 * @return The card at the top of the stack.
	 * @pre !isEmpty();
	 */
	public Card peek()
	{
		assert !isEmpty();
		return aCards.get(aCards.size() - 1);
	}
	
	/**
	 * @param pIndex The index to peek in the stack.
	 * @return The card at the position indicated by pIndex
	 * @pre pIndex >= 0 && pIndex < size();
	 */
	public Card peek(int pIndex)
	{
		assert pIndex >= 0 && pIndex < size();
		return aCards.get(pIndex);
	}
	
	/**
	 * @return The number of cards in the stack.
	 */
	public int size()
	{
		return aCards.size();
	}
	
	/**
	 * Removes all the cards in the stack.
	 */
	public void clear()
	{
		aCards.clear();
	}
	
	/**
	 * @return True if and only if the stack has no cards in it.
	 */
	@Override
	public boolean isEmpty()
	{
		return super.isEmpty();
	}
	
	@Override
	public String toString()
	{
		return aCards.toString();
	}

	@Override
	public Iterator<Card> iterator()
	{
		return aCards.iterator();
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
