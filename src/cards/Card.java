package cards;

import java.util.EnumMap;
import java.util.Map;


public final class Card
{
	private static final Map<Suit, Map<Rank, Card>> CARDS = new EnumMap<>(Suit.class);

	/*
	 * Create the flyweight objects eagerly.
	 */
	static
	{
		for( Suit suit : Suit.values() )
		{
			Map<Rank, Card> forSuit = CARDS.computeIfAbsent(suit, key -> new EnumMap<>(Rank.class));
			for( Rank rank : Rank.values() )
			{
				forSuit.put(rank, new Card(rank, suit));
			}
		}
	}
	
	private final Rank aRank;
	private final Suit aSuit;
	
	private Card(Rank pRank, Suit pSuit )
	{
		aRank = pRank;
		aSuit = pSuit;
	}
	
	/**
	 * Get a flyweight Card object.
	 * 
	 * @param pRank The rank of the card (from Ace to King).
	 * @param pSuit The suit of the card (Clubs, Diamond, Spades, Hearts).
	 * @return The card object representing the card with pRank and pSuit.
	 */
	public static Card get(Rank pRank, Suit pSuit)
	{
		assert pRank != null && pSuit != null;
		return CARDS.get(pSuit).get(pRank);
	}
	
	/**
	 * Obtain the rank of the card.
	 * @return An object representing the rank of the card.
	 */
	public Rank getARank()
	{
		return aRank;
	}
	
	/**
	 * Obtain the suit of the card.
	 * @return An object representing the suit of the card 
	 */
	public Suit getASuit()
	{
		return aSuit;
	}
	
	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format("%s of %s", titleCase(aRank), titleCase(aSuit));
	}
	
	private static String titleCase(Enum<?> pEnum)
	{
		return pEnum.name().charAt(0) + pEnum.name().substring(1).toLowerCase();
	}
}
