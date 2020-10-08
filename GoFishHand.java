/**
 * SYST 17796 Project Winter 2019 Base code.
 *
 * @Modifier: Group7: Thanveer Hauzaree,Yuxiao Fang,Shuwen Wang,Chen-yu Wu
 * @updateDate: 2020-04-12
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.HashMap;

public class GoFishHand extends GroupOfCards {

    private ArrayList<GoFishCard> cards = new ArrayList<>();
    private GoFishDeck deck;
    private int score = 0;
    private int size;//the size of the grouping

    public GoFishHand() {
        // TODO - implement GoFishHand.GoFishHand
        super(5);
    }

    public ArrayList<GoFishCard> getCards() {
        return this.cards;
    }

    public void setCards(ArrayList<GoFishCard> cards) {
        this.cards = cards;
    }

    public ArrayList<GoFishCard> getBook() {
        // map1 to find one book of cards
        // map2 to store and get the key for each card
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<Integer, String> map2 = new HashMap<>();
        ArrayList<GoFishCard> book = new ArrayList<>();

        for (int j = 0; j < this.cards.size(); j++) {
            GoFishCard card = this.cards.get(j);
            String key = card.getValue().toString();
            if (!map1.containsKey(key)) {
                map1.put(key, 1);
            } else {
                map1.put(key, map1.get(key) + 1);
            }
            map2.put(j, key);
        }

        map1.forEach((k1, v1) -> {
            if (v1 == 4) {
                map2.forEach((k2, v2) -> {
                    if (k1.equals(v2)) {
                        book.add(this.cards.get(k2));
                    }
                });
            }
        });

        return book;
    }

    public boolean askCard(GoFishCard card) {
        return cards.contains(card);
    }

    public void addCard(GoFishCard card) {
        this.cards.add(card);
    }

    public void drawOneCard(GoFishCard card) {
        this.cards.add(card);
    }

    public void removeCard(GoFishCard card) {
        this.cards.remove(card);
    }

    public void removeBook(ArrayList<GoFishCard> book) {

        for (GoFishCard card : book) {
            this.cards.remove(card);
        }
        score++;
    }

    public void drawUpFiveCards(GoFishDeck deck) {
        int i = 0;
        while (!deck.getCards().isEmpty() && i < 5) {
            cards.add(deck.getCards().get(0));
            deck.removeCard(deck.getCards().get(0));
            i++;
        }
    }

    public int getScore() {
        return this.score;
    }

}
