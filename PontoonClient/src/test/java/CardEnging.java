import com.pontoonClient.cardEngine.Card;
import com.pontoonClient.cardEngine.Deck;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
public class CardEnging {


    @Test
    public void TestCardNumberCreated(){
        Deck deck = new Deck();
        assertTrue(deck.cards.size() == 52);
    }

    @Test
    public void TestCardNumberReduced(){
        Deck deck = new Deck();
        deck.deal();
        assertTrue(deck.cards.size() == 51);
    }

    @Test
    public void TestCardSpadesNumber(){
        Deck deck = new Deck();
        int numberOfSpades = 0;
        for (Card card:
             deck.cards) {
            if(card.color == Card.CardColor.SPADES)
                numberOfSpades++;
        }
        assertTrue(numberOfSpades == 13);
    }

    @Test
    public void TestCardClubsNumber(){
        Deck deck = new Deck();
        int numberOfClubs = 0;
        for (Card card:
             deck.cards) {
            if(card.color == Card.CardColor.CLUB)
                numberOfClubs++;
        }
        assertTrue(numberOfClubs == 13);
    }

    @Test
    public void TestCardDiamondNumber(){
        Deck deck = new Deck();
        int numberOfDiamonds = 0;
        for (Card card:
             deck.cards) {
            if(card.color == Card.CardColor.DIAMOND)
                numberOfDiamonds++;
        }
        assertTrue(numberOfDiamonds == 13);
    }

    @Test
    public void TestCardHeartsNumber(){
        Deck deck = new Deck();
        int numberOfHearts = 0;
        for (Card card:
             deck.cards) {
            if(card.color == Card.CardColor.HEART)
                numberOfHearts++;
        }
        assertTrue(numberOfHearts == 13);
    }

    @Test
    public void textCardValue(){

    }

}
