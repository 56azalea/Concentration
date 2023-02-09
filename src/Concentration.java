import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.FontStyle;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

// represents a concentration (card game)
class Concentration extends World {
  ArrayList<ArrayList<Card>> deck;
  Card pick1;
  Card pick2;
  int score;
  int life;
  int time;

  Concentration() {
    init();
  }

  // the initial condition of the game
  void init() {
    this.deck = new ArrayList<ArrayList<Card>>(4);
    for (int i = 0; i < 4; i++) {
      ArrayList<Card> row = new ArrayList<Card>(13);
      for (int j = 0; j < 13; j++) {
        String suit = Config.suits.get(i);
        Card card = new Card(j + 1, suit);
        row.add(card);
      }
      this.deck.add(row);
    }
    this.pick1 = null;
    this.pick2 = null;
    this.score = 26;
    this.life = Config.MAX_LIFE;
    this.time = 0;
    shuffle();
  }

  // takes out the cards from the game play if they are a matching pair
  void removeFromDeck(Card card) {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        Card current = this.deck.get(i).get(j);
        if (current != null && current.equals(card)) {
          this.deck.get(i).set(j, null);
        }
      }
    }
  }

  // shuffles the cards
  void shuffle() {
    Random rand = new Random();
    for (int i = 0; i < Config.shuffleNumber; i++) {
      int randomRow1 = rand.nextInt(4);
      int randomRow2 = rand.nextInt(4);
      int randomCol1 = rand.nextInt(13);
      int randomCol2 = rand.nextInt(13);
      ArrayList<Card> row1 = this.deck.get(randomRow1);
      ArrayList<Card> row2 = this.deck.get(randomRow2);
      Card card1 = row1.get(randomCol1);
      Card card2 = row2.get(randomCol2);
      row1.set(randomCol1, card2);
      row2.set(randomCol2, card1);
      this.deck.set(randomRow1, row1);
      this.deck.set(randomRow2, row2);
    }
  }

  // draws the cards/score/life/time onto the background (empty scene)
  public WorldScene makeScene() {
    WorldScene acc = new WorldScene(Config.MAX_WIDTH, Config.MAX_HEIGHT);
    // draws the cards
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        int xPos = Config.START_X + j * (Config.CARD_WIDTH + Config.MARGIN_X);
        int yPos = Config.START_Y + i * (Config.CARD_HEIGHT + Config.MARGIN_Y);
        Card card = this.deck.get(i).get(j);
        if (card != null) {
          acc.placeImageXY(card.draw(), xPos, yPos);
        }
      }
    }
    // renders the score
    WorldImage scoreImg = new TextImage("Score : " + Integer.toString(this.score), 25,
        FontStyle.BOLD, Color.black);
    acc.placeImageXY(scoreImg, Config.MAX_WIDTH / 4, 30);
    // renders the number of steps left
    WorldImage lifeImg = new TextImage("Life : " + Integer.toString(this.life), 25, FontStyle.BOLD,
        Color.black);
    acc.placeImageXY(lifeImg, Config.MAX_WIDTH / 4 * 2, 30);
    // displays a timer to keep track of the time since the beginning of the game
    int h = this.time / 3600;
    int m = this.time / 60 % 60;
    int s = this.time % 60;
    String timeStamp = String.format("%02d:%02d:%02d", h, m, s);
    WorldImage timeImg = new TextImage(timeStamp, 25, FontStyle.BOLD, Color.black);
    acc.placeImageXY(timeImg, Config.MAX_WIDTH / 4 * 3, 30);
    return acc;
  }

  // the world after every tick of the clock
  public void onTick() {
    // keeps track of the time since the beginning of the game
    this.time = this.time + 1;
    // if the cards are a matching pair, the cards are taken out of the game play
    // and the score is decreased by one
    if (this.pick1 != null && this.pick2 != null) {
      if (this.pick1.isMatched(this.pick2)) {
        removeFromDeck(this.pick1);
        removeFromDeck(this.pick2);
        this.score = this.score - 1;
      }
      else {
        this.pick1.flip();
        this.pick2.flip();
        this.life = this.life - 1;
      }
      this.pick1 = null;
      this.pick2 = null;
    }
    // game over condition
    if (this.score == 0 || this.life == 0) {
      this.endOfWorld("Game Over");
    }
  }

  // the player should be able to click on each card to flip it to face up
  public void onMouseClicked(Posn pos) {
    Card picked = null;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        int xPos = Config.START_X + j * (Config.CARD_WIDTH + Config.MARGIN_X);
        int yPos = Config.START_Y + i * (Config.CARD_HEIGHT + Config.MARGIN_Y);
        if (xPos - Config.CARD_WIDTH / 2 <= pos.x && pos.x <= xPos + Config.CARD_WIDTH / 2
            && yPos - Config.CARD_HEIGHT / 2 <= pos.y && pos.y <= yPos + Config.CARD_HEIGHT / 2) {
          picked = deck.get(i).get(j);
        }
      }
    }
    if (picked != null) {
      if (this.pick1 == null) {
        this.pick1 = picked;
        picked.flip();
      }
      else if (this.pick2 == null && !this.pick1.equals(picked)) {
        this.pick2 = picked;
        picked.flip();
      }
    }
  }

  // allows the use of the ‘r’ key to reset the game and create a new board
  public void onKeyReleased(String key) {
    if (key.equals("r")) {
      init();
    }
  }

  // the last scene of the game
  public WorldScene lastScene(String msg) {
    new WorldScene(Config.MAX_WIDTH, Config.MAX_HEIGHT)
        .placeImageXY(new TextImage("Your Score : " + Integer.toString(this.score), 36,
            FontStyle.BOLD, Color.black), Config.MAX_WIDTH / 2, Config.MAX_HEIGHT / 2);
    return new WorldScene(Config.MAX_WIDTH, Config.MAX_HEIGHT);
  }
}