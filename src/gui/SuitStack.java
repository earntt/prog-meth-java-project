package gui;

import cards.Card;
import cards.CardSerializer;
import cards.CardStack;
import model.FoundationPile;
import model.GameModel;
import model.GameModelListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

/**
 * Component that shows a stack of cards in which a completed suit is
 * accumulated.
 */
public class SuitStack extends StackPane implements GameModelListener
{
	private static final int PADDING = 5;

	private static final String BORDER_STYLE = """
			-fx-border-color: #8b99c9; \
			-fx-border-width: 2; \
			-fx-border-radius: 10.0""";
	
	private static final String BORDER_STYLE_DRAGGED = """
			-fx-border-color: #63647F; \
			-fx-border-width: 2; \
			-fx-border-radius: 10.0""";
	
	private static final String BORDER_STYLE_NORMAL = """
			-fx-border-color: #8b99c9; \
			-fx-border-width: 2; 
			-fx-border-radius: 10.0""";
	
	private CardDragHandler aDragHandler;
	private FoundationPile aIndex;
	private final GameModel aModel;

	SoundPlayer cardSound = new SoundPlayer(
			ClassLoader.getSystemResource("card.wav").toString()
	);
	
	SuitStack(GameModel pModel, FoundationPile pIndex)
	{
		aModel = pModel;
		aIndex = pIndex;
		setPadding(new Insets(PADDING));
		setStyle(BORDER_STYLE);
		final ImageView image = new ImageView(CardImage.imageForBackOfCard());
    	image.setVisible(false);
       	getChildren().add(image);
    	aDragHandler = new CardDragHandler(image);
    	image.setOnDragDetected(aDragHandler);
    	setOnDragOver(createOnDragOverHandler(image));
    	setOnDragEntered(createOnDragEnteredHandler());
    	setOnDragExited(createOnDragExitedHandler());
    	setOnDragDropped(createOnDragDroppedHandler());
    	aModel.addListener(this);
	}
	
	@Override
	public void gameStateChanged()
	{
		if( aModel.isFoundationPileEmpty(aIndex))
		{
			getChildren().get(0).setVisible(false);
		}
		else
		{
			getChildren().get(0).setVisible(true);
			Card topCard = aModel.peekSuitStack(aIndex);
			ImageView image = (ImageView)getChildren().get(0);
			image.setImage(CardImage.imageFor(topCard));
			aDragHandler.setCard(topCard);
		}
	}
	
	private EventHandler<DragEvent> createOnDragOverHandler(final ImageView pView)
	{
		return new EventHandler<DragEvent>()
    	{
    	    @Override
			public void handle(DragEvent pEvent) 
    	    {
    	    	if(pEvent.getGestureSource() != pView && pEvent.getDragboard().hasString())
    	    	{
    	    		CardStack transfer = CardSerializer.deserialize(pEvent.getDragboard().getString());
    	    		if( transfer.size() == 1 && aModel.isLegalMove(transfer.peek(), aIndex) )
    	    		{
    	    			pEvent.acceptTransferModes(TransferMode.MOVE);
    	    		}
    	    	}
    	    	pEvent.consume();
    	    }
    	};
	}
	
	private EventHandler<DragEvent> createOnDragEnteredHandler()
	{
		return new EventHandler<DragEvent>()
    	{
    		@Override
			public void handle(DragEvent pEvent) 
    		{
    			CardStack transfer = CardSerializer.deserialize(pEvent.getDragboard().getString());
	    		if( transfer.size() == 1 && aModel.isLegalMove(transfer.peek(), aIndex) )
    			{
    				setStyle(BORDER_STYLE_DRAGGED);
    			}
				cardSound.stopPlay();
    			pEvent.consume();
    		}
    	};
	}
	
	private EventHandler<DragEvent> createOnDragExitedHandler()
	{
		return new EventHandler<DragEvent>()
    	{
    		@Override
			public void handle(DragEvent pEvent)
    		{

    			setStyle(BORDER_STYLE_NORMAL);
    			pEvent.consume();

    		}
    	};
	}
	
	private EventHandler<DragEvent> createOnDragDroppedHandler()
	{
		return new EventHandler<DragEvent>() 
    	{
    		@Override
			public void handle(DragEvent pEvent)
    		{
    			Dragboard db = pEvent.getDragboard();
    			boolean success = false;
				cardSound.setVolume(1.00);
				cardSound.startPlay();
    			if(db.hasString()) 
    			{
    				aModel.getCardMove(CardSerializer.deserializeBottomCard(pEvent.getDragboard().getString()), aIndex).perform();
    				success = true;
    			}
    			pEvent.setDropCompleted(success);
    			pEvent.consume();
    		}
    	};
	}
}