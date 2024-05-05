package gui;

import cards.Card;
import cards.CardSerializer;
import cards.CardStack;
import model.GameModel;
import model.GameModelListener;
import model.TableauPile;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;

/**
 * Component that shows a stack of cards in the tableau.
 */
public class CardPileView extends StackPane implements GameModelListener
{
	private static final int PADDING = 5;
	private static final int Y_OFFSET = 17;
	private static final ClipboardContent CLIPBOARD_CONTENT = new ClipboardContent();

	private TableauPile aIndex;
	private final GameModel aModel;

	SoundPlayer cardSound = new SoundPlayer(ClassLoader.getSystemResource("card.wav").toString());
	
	CardPileView(GameModel pModel, TableauPile pIndex)
	{
    	aModel = pModel;
		aIndex = pIndex;
		setPadding(new Insets(PADDING));
    	setAlignment(Pos.TOP_CENTER);
    	buildLayout();
    	aModel.addListener(this);
	}
	
	private Image getImage(Card pCard)
	{
		if( aModel.isVisibleInTableau(pCard) )
		{
			return CardImage.imageFor(pCard);
		}
		else
		{
			return CardImage.imageForBackOfCard();
		}
	}
	
	private void buildLayout()
    {
		getChildren().clear();
		
		int offset = 0;
		CardStack stack = aModel.getTableauPile(aIndex);
		if( stack.isEmpty() ) // this essentially acts as a spacer
		{
			ImageView image = new ImageView(CardImage.imageForBackOfCard());
			image.setVisible(false);
			getChildren().add(image);
			return;
		}
		
		for( Card cardView : stack)
		{
			final ImageView image = new ImageView(getImage(cardView));
        	image.setTranslateY(Y_OFFSET * offset);
        	offset++;
        	getChildren().add(image);
        
        	setOnDragOver(createDragOverHandler(image, cardView));
    		setOnDragEntered(createDragEnteredHandler(image, cardView));
    		setOnDragExited(createDragExitedHandler(image, cardView));
    		setOnDragDropped(createDragDroppedHandler(image, cardView));
    		
        	if( aModel.isVisibleInTableau(cardView))
        	{
        		image.setOnDragDetected(createDragDetectedHandler(image, cardView));
        	}
		}
    }
	
	private EventHandler<MouseEvent> createDragDetectedHandler(final ImageView pImageView, final Card pCard)
	{
		return new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent pMouseEvent) 
			{
				Dragboard db = pImageView.startDragAndDrop(TransferMode.ANY);
				CLIPBOARD_CONTENT.putString(CardSerializer.serialize(aModel.getSubStack(pCard, aIndex)));
				db.setContent(CLIPBOARD_CONTENT);
				db.setDragView(pImageView.getImage());
				pMouseEvent.consume();
			}
		};
	}
	
	private EventHandler<DragEvent> createDragOverHandler(final ImageView pImageView, final Card pCard)
	{
		return new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent pEvent) 
			{
				if(pEvent.getGestureSource() != pImageView && pEvent.getDragboard().hasString())
				{
					if( aModel.isLegalMove(CardSerializer.deserializeBottomCard(pEvent.getDragboard().getString()), aIndex) )
					{

						pEvent.acceptTransferModes(TransferMode.MOVE);
					}
				}
				pEvent.consume();
			}
		};
	}
	
	private EventHandler<DragEvent> createDragEnteredHandler(final ImageView pImageView, final Card pCard)
	{
		return new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent pEvent)
			{
				cardSound.stopPlay();
				if( aModel.isLegalMove(CardSerializer.deserializeBottomCard(pEvent.getDragboard().getString()), aIndex) )
				{
					pImageView.setEffect(new DropShadow());
				}
				pEvent.consume();
			}
		};
	}
	
	private EventHandler<DragEvent> createDragExitedHandler(final ImageView pImageView, final Card pCard)
	{
		return new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent pEvent)
			{
				pImageView.setEffect(null);
				pEvent.consume();

			}
		};
	}
	
	private EventHandler<DragEvent> createDragDroppedHandler(final ImageView pImageView, final Card pCard)
	{
		return new EventHandler<DragEvent>() 
		{
			@Override
			public void handle(DragEvent pEvent)
			{
				Dragboard db = pEvent.getDragboard();
				boolean success = false;

				if(db.hasString()) 
				{
					aModel.getCardMove(CardSerializer.deserializeBottomCard(db.getString()), aIndex).perform(); 
					success = true;
				}

				pEvent.setDropCompleted(success);
				cardSound.setVolume(1.0);
				cardSound.startPlay();
				pEvent.consume();
			}
		};
	}

	@Override
	public void gameStateChanged()
	{
		buildLayout();
	}
}