package gui;

import cards.Card;
import cards.CardSerializer;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * Stores a string representing the card dragged.
 */
public class CardDragHandler implements EventHandler<MouseEvent>
{
	private static final ClipboardContent CLIPBOARD_CONTENT = new ClipboardContent();
	
	private Card aCard;
	private ImageView aImageView;
	
	CardDragHandler( ImageView pView )
	{
		aImageView = pView;
	}
	
	void setCard(Card pCard)
	{
		aCard = pCard;
	}
	
	@Override
	public void handle(MouseEvent pMouseEvent)
	{
		Dragboard db = aImageView.startDragAndDrop(TransferMode.ANY);
        CLIPBOARD_CONTENT.putString(CardSerializer.serialize(aCard));
        db.setContent(CLIPBOARD_CONTENT);
		db.setDragView(aImageView.getImage());
        pMouseEvent.consume();
	}
}