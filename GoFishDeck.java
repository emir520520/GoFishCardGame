/**
 * SYST 17796 Project Winter 2019 Base code.
 *
 * @Modifier: Group7: Thanveer Hauzaree,Yuxiao Fang,Shuwen Wang,Chen-yu Wu
 * @updateDate: 2020-04-12
 */
package ca.sheridancollege.project;

import ca.sheridancollege.project.GoFishCard.Suit;
import ca.sheridancollege.project.GoFishCard.Value;
import java.util.ArrayList;
import java.util.Collections;

public class GoFishDeck extends GroupOfCards {
    
    private ArrayList<GoFishCard> cards = new ArrayList<>();
    private int size;
    
    public GoFishDeck(int givenSize) {
        super(givenSize);
        this.size = givenSize;
    }
    
    public ArrayList<GoFishCard> getCards() {
        return this.cards;
    }
    
    @Override
    public void shuffle() {
        Collections.shuffle(this.cards);
    }
    
    public void setCards(ArrayList<GoFishCard> cards) {
        this.cards = cards;
    }
    
    public void initDeck() {
        for (Suit s : Suit.values()) {
            for (Value v : Value.values()) {
                this.cards.add(new GoFishCard(s, v));
            }
        }
    }
    
    public void removeCard(GoFishCard card) {
        this.cards.remove(card);
    }
    
    public void removeCards(ArrayList<GoFishCard> cards) {
        for (GoFishCard card : cards) {
            this.cards.remove(card);
        }
    }
    
    public GoFishCard DispatchOneCard() {
        return this.cards.get(0);
    }
    
    public ArrayList<GoFishCard> DispatchStartCards(int numOfStartCards) {
        ArrayList<GoFishCard> startCards = new ArrayList<>();
        for (int i = 0; i < numOfStartCards; i++) {
            startCards.add(this.cards.get(i));
        }
        
        for (int i = 0; i < numOfStartCards; i++) {
            this.cards.remove(startCards.get(i));
        }
        
        return startCards;
    }
    
    public void removeFirstCard() {
        this.cards.remove(this.cards.get(0));
    }
    
}
