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
import java.util.Scanner;

public class GoFishGame extends Game {

    private GoFishDeck deck;
    private ArrayList<GoFishPlayer> players = new ArrayList<>();
    private ArrayList<GoFishHand> hands = new ArrayList<>();
    private ArrayList<GoFishCard> cards = new ArrayList<>();
    private static int numOfPlayers;

    public GoFishGame(String givenName) {
        // TODO - implement GoFishGame.GoFishGame
        super(givenName);
    }

    public static void main(String[] args) {
        GoFishGame gofish = new GoFishGame("GoFish");
        System.out.println("welcome to " + gofish.getGameName());
        setPlayerNumber();
        gofish.startGame(numOfPlayers);

        gofish.play(); // while there is card in the desk and hand, continue play
        gofish.declareWinner();
    }

    public static void setPlayerNumber() {
        while (!(numOfPlayers == 2 || numOfPlayers == 3 || numOfPlayers == 4)) {
            Scanner sc = new Scanner(System.in);
            try {
                System.out.println("How many players: 2,3,or 4?");
                numOfPlayers = sc.nextInt();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }

    /**
     * When the game is over, use this method to declare and display a winning
     * player.
     */
    @Override
    public void declareWinner() {
        // TODO - implement GoFishGame.declareWinner
        System.out.println("players'score: ");
        int max = 0;
        String winner = "";
        for (int i = 0; i < this.players.size(); i++) {
            System.out.println(players.get(i).getPlayerID() + " : "
                    + players.get(i).getHand().getScore());
            if (players.get(i).getHand().getScore() > max) {
                max = players.get(i).getHand().getScore();
                winner = players.get(i).getPlayerID();
            }
        }
        System.out.println("winner: " + winner + "  score: " + max);
    }

    /**
     * Play the game.
     */
    @Override
    public void play() {

//        if (!deck.getCards().isEmpty()) {
        Scanner sc = new Scanner(System.in);
        String res = "";
        for (int i = 0; i < numOfPlayers; i++) {
            if (!hands.get(i).getCards().isEmpty()) {
                callTurns(i);
                System.out.println("deck size: " + deck.getCards().size());

                boolean isGofish = false;
                while (!isGofish) {
                    isGofish = true;
                    int numOfAskCard = 0;
                    GoFishCard one = new GoFishCard(Suit.CLUBS, Value.ACE);
                    int idOfAskedPlayer = players.size();

                    // player ask any other player for a card. input number 0,1,2,3(with name shown)
                    while (!(idOfAskedPlayer < players.size() && idOfAskedPlayer >= 0 && idOfAskedPlayer != i)) {
                        try {
                            System.out.println("which player will you ask, please choose the number");
                            ArrayList<GoFishPlayer> otherPlayers = new ArrayList<>(players);
                            otherPlayers.remove(otherPlayers.get(i));
                            // remove the player with no card
                            for (GoFishPlayer sub : players) {
                                if (sub.getHand().getCards().isEmpty()) {
                                    otherPlayers.remove(sub);
                                }
                            }
                            System.out.println("players: " + otherPlayers);
                            idOfAskedPlayer = sc.nextInt();
                        } catch (Exception e) {
                            sc.nextLine();
                        }
                    }
                    // if the card is in, get card, otherwise gofish
                    //when i== numOfPlayers - 1, if gofish, i =0;
                    // validate the card 
                    System.out.println(players.get(idOfAskedPlayer).getPlayerID() + "'s cards: "
                            + players.get(idOfAskedPlayer).getHand().getCards());
//                            System.out.println("deck " + deck.getCards());

                    // ask the card
                    boolean isAskcard = false;
                    GoFishCard askedCard = new GoFishCard(Suit.CLUBS, Value.ACE);
//                        GoFishCard askedCard = null;

                    while (!isAskcard) {
                        System.out.println("Only the following cards in your hand can be asked:");
                        System.out.println(players.get(i).getPlayerID() + "'s cards: " + players.get(i).getHand().getCards());
                        System.out.println("Hey " + players.get(i).getPlayerID()
                                + ",choose index of card(0 to "
                                + (players.get(i).getHand().getCards().size() - 1)
                                + ") to ask " + players.get(idOfAskedPlayer).getPlayerID());

                        try {
                            int indexOfCard = sc.nextInt();
                            askedCard = players.get(i).getHand().getCards().get(indexOfCard);
                            isAskcard = players.get(i).getHand().askCard(askedCard);
                        } catch (Exception e) {
                            sc.nextLine();
                        }
                    }
//                        }
                    // the player asked to check the cards in player's hand
                    for (int sub = 0; sub < players.get(idOfAskedPlayer).getHand().getCards().size(); sub++) {
                        GoFishCard card = players.get(idOfAskedPlayer).getHand().getCards().get(sub);
                        if (card.equals(askedCard)) {
                            players.get(i).getHand().addCard(card);
                            players.get(idOfAskedPlayer).getHand().removeCard(card);
                            sub--;
                            numOfAskCard++;
                            one.setValue(card.getValue());
                            isGofish = false;
                        }
                    }

                    if (!isGofish) {
                        res = players.get(idOfAskedPlayer).getPlayerID() + " says I have " + numOfAskCard + " " + one.getValue();
                        System.out.println(res);

                        if (players.get(idOfAskedPlayer).getHand().getCards().isEmpty()) {
                            players.get(idOfAskedPlayer).getHand().drawUpFiveCards(deck);
                        }

                        checkBook(i);

                        // scenario 1: hand has no card after removing books
                        if (players.get(i).getHand().getCards().isEmpty()) {
                            players.get(i).getHand().drawUpFiveCards(deck);
                            checkBook(i);
                        }

                        // scenario 2: hand has no card after drawing four cards (book) and remove the book
                        if (players.get(i).getHand().getCards().isEmpty()) {
                            isGofish = true;
                        }

                    } else {
                        res = players.get(idOfAskedPlayer).getPlayerID() + " says: gofish";
                        System.out.println(res);
                        System.out.println("deck " + deck.getCards());
                        if (!deck.getCards().isEmpty()) {
                            players.get(i).getHand().drawOneCard(deck.DispatchOneCard());
                            deck.removeFirstCard();
                            System.out.println(players.get(i).getPlayerID() + " draws one card from the deck.");
                        }
                        checkBook(i);

                        if (i == numOfPlayers - 1) {
                            i = -1;
                        }
                    }

                    if (endGame()) {
                        break;
                    }

                }
            }
        }
//        } else {
//            System.out.println("No cards in the deck!");
//        }
    }

    public void startGame(int numOfPlayers) {
        this.deck = new GoFishDeck(52);
        this.deck.initDeck();
        this.deck.shuffle();
        this.cards = deck.getCards();
//        System.out.println("deck's cards: " + cards);
        switch (numOfPlayers) {
            case 2:
                setPlayers();
                break;
            case 3:
                setPlayers();
                break;
            case 4:
                setPlayers();
                break;
        }

    }

    public boolean endGame() {
        // TODO - implement GoFishGame.endGame
        boolean isEndGame = true;
        for (int i = 0; i < this.players.size(); i++) {
            if (!hands.get(i).getCards().isEmpty()) {
                isEndGame = false;
            }
        }
        if (deck.getCards().isEmpty() && isEndGame) {
            System.out.println("Game over");
        }
        return isEndGame;
    }

    public void callTurns(int i) {
        // TODO - implement GoFishGame.callTurns

        System.out.println("It's " + players.get(i).getPlayerID() + "'s turn");

    }

    public void checkBook(int i) {
        ArrayList<GoFishCard> book = players.get(i).getHand().getBook();
        if (!book.isEmpty()) {
            System.out.println(players.get(i).getPlayerID() + " says I have a book : " + book);
            hands.get(i).removeBook(book);

            if (hands.get(i).getCards().isEmpty()) {
                hands.get(i).drawUpFiveCards(deck);
            }
        }
    }

    public void setPlayers() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < numOfPlayers; i++) {
            boolean isTakenName = true;
            while (isTakenName) {
                System.out.println("please input player " + i + " name: ");
                String playername = sc.nextLine();
                boolean isContain = players.stream().anyMatch(o -> o.getPlayerID().equals(playername));
                if (!isContain) {
                    GoFishPlayer player = new GoFishPlayer(playername);
                    GoFishHand hand = new GoFishHand();
                    hand.setCards(deck.DispatchStartCards(5));
                    hands.add(hand);
                    player.setHand(hand);
                    player.setId(i);
                    players.add(player);
                    isTakenName = false;
                }
            }
        }
        System.out.println("Player's ID: "+players);
    }
    
}
