import java.awt.Color;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.WorldImage;

// represents a card
class Card {
  int rank;
  String suit;
  Color color;
  boolean flipped;

  Card(int rank, String suit) {
    this.rank = rank;
    this.suit = suit;
    if (suit.equals("♣") || suit.equals("♠")) {
      this.color = Color.black;
    }
    else {
      this.color = Color.red;
    }
    this.flipped = true;
  }

  Card(int rank, String suit, Color color, boolean flipped) {
    this.rank = rank;
    this.suit = suit;
    this.color = color;
    this.flipped = flipped;
  }

  // draws the card
  WorldImage draw() {
    if (this.flipped) {
      return new FromFileImage("img/back.jpg");
      // if this doesn't work due to errors in the img file
      /*
       * return new RectangleImage(Config.CARD_WIDTH, Config.CARD_HEIGHT,
       * OutlineMode.SOLID, Color.black);
       */
    }
    else {
      return new FromFileImage("img/" + this.suit + Integer.toString(this.rank) + ".png");
      // if this doesn't work due to errors in the img file
      /*
       * WorldImage suitImg = new TextImage(this.suit, 24, this.color); WorldImage
       * cardImg = new RectangleImage(Config.CARD_WIDTH, Config.CARD_HEIGHT,
       * OutlineMode.OUTLINE, Color.black); WorldImage cardWithSuit = new
       * OverlayImage(cardImg, suitImg); WorldImage rankImg = new
       * TextImage(Integer.toString(this.rank), 15, this.color); WorldImage rankLeft =
       * new OverlayOffsetImage(cardWithSuit, -(Config.CARD_WIDTH / 2) + 10,
       * -(Config.CARD_HEIGHT / 2) + 15, rankImg); WorldImage rankRight = new
       * OverlayOffsetImage(rankLeft, (Config.CARD_WIDTH / 2) - 13,
       * (Config.CARD_HEIGHT / 2) - 13, rankImg); return rankRight;
       */
    }
  }

  // flips the card
  void flip() {
    this.flipped = !this.flipped;
  }

  // determines if the other card is a matching pair of this card
  boolean isMatched(Card other) {
    return this.rank == other.rank && this.color.equals(other.color);
  }
}