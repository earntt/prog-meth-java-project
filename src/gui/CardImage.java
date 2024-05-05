package gui;

import cards.Card;
import cards.Deck;
import javafx.scene.image.Image;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * A class to store and manage images of the 52 cards and the back of a card.
 * 
 * To function properly, all 53 images must be accessible from a folder at
 * the root of the path from which the class file for CardImages is loaded.
 * If a file is missing or corrupted, launching the application will fail
 * with "Exception in Application start method" indirectly caused by
 * an ExceptionInInitializerError because this class will fail to 
 * initialize.
 */
public final class CardImage
{
	private static final Image CARD_BACK = loadBackImage();
	private static final Map<Card, Image> CARD_IMAGES = loadCardImages();
	
	private CardImage()
	{}
	
	/**
	 * Return the image of a card.
	 * @param pCard the target card
	 * @return An icon representing the chosen card.
	 */
	public static Image imageFor( Card pCard )
	{
		assert pCard != null;
		return CARD_IMAGES.get(pCard);
	}
	
	/**
	 * Return an image of the back of a card.
	 * @return An icon representing the back of a card.
	 */
	public static Image imageForBackOfCard()
	{
		return CARD_BACK;
	}
	
	private static Image loadBackImage()
	{
		return new Image(CardImage.class.getClassLoader()
				.getResourceAsStream("back.jpg"));
	}
	
	/*
	 * Loads images for all 52 cards in a map. Assumes that card objects are 
	 * flyweights.
	 */
	private static Map<Card, Image> loadCardImages()
	{
		Deck deck = new Deck();
		Map<Card, Image> images = new IdentityHashMap<>();
		while( !deck.isEmpty() )
		{
			Card card = deck.pop();
			Image image = new Image(CardImage.class.getClassLoader()
					.getResourceAsStream( getFileNameFor(card) ));
			images.put(card, image);
		}
		return images;
	}
	
	private static String getFileNameFor( Card pCard )
	{
		return String.format("%s-of-%s.jpg", pCard.getARank().name().toLowerCase(),
				pCard.getASuit().name().toLowerCase());
	}
}
