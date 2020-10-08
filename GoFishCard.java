/**
 * SYST 17796 Project Winter 2019 Base code.
 * @Modifier: Group7: Thanveer Hauzaree,Yuxiao Fang,Shuwen Wang,Chen-yu Wu
 * @updateDate: 2020-04-12
 */
package ca.sheridancollege.project;

public class GoFishCard extends Card {

    public enum Suit {
        DIAMONDS, HEARTS, SPADES, CLUBS
    }
    
//    public enum Value {
//        ACE, TWO,Three
//    }

    public enum Value {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        JACK, QUEEN, KING
    }
    private Suit suit;
    private Value value;

    public GoFishCard(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        return getSuit()+"-"+getValue();
    }

    public Suit getSuit() {
        return this.suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Value getValue() {
        return this.value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    public boolean equals(GoFishCard one){
        return this.getValue()== one.getValue();
    }
}
