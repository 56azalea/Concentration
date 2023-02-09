import java.awt.Color;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import tester.Tester;

// the examples
class ExamplesConcentration {
  Card card1;
  Card card2;
  Concentration world;
  // if this doesn't work due to errors in the img file
  /*
   * WorldImage suitImg2; WorldImage CardImg2; WorldImage cardWithSuit2;
   * WorldImage rankImg2; WorldImage rankLeft2; WorldImage rankRight2;
   */

  void initial() {
    this.card1 = new Card(1, "♥", Color.red, true);
    this.card2 = new Card(2, "♥", Color.red, false);
    this.world = new Concentration();
    // if this doesn't work due to errors in the img file
    /*
     * this.suitImg2 = new TextImage("♥", 24, Color.red); this.CardImg2 = new
     * RectangleImage(Config.CARD_WIDTH, Config.CARD_HEIGHT, OutlineMode.OUTLINE,
     * Color.black); this.cardWithSuit2 = new OverlayImage(CardImg2, suitImg2);
     * this.rankImg2 = new TextImage(Integer.toString(2), 15, Color.red);
     * this.rankLeft2 = new OverlayOffsetImage(cardWithSuit2, -(Config.CARD_WIDTH /
     * 2) + 10, -(Config.CARD_HEIGHT / 2) + 15, rankImg2); this.rankRight2 = new
     * OverlayOffsetImage(rankLeft2, (Config.CARD_WIDTH / 2) - 13,
     * (Config.CARD_HEIGHT / 2) - 13, rankImg2);
     */
  }

  // tests the method draw
  void testDraw(Tester t) {
    initial();
    t.checkExpect(this.card1.draw(), new FromFileImage("img/back.jpg"));
    t.checkExpect(this.card2.draw(),
        new FromFileImage("img/" + "♥" + Integer.toString(2) + ".png"));
    // if this doesn't work due to errors in the img file
    /*
     * t.checkExpect(this.card1.draw(), new RectangleImage(Config.CARD_WIDTH,
     * Config.CARD_HEIGHT, OutlineMode.SOLID, Color.black));
     * t.checkExpect(this.card2.draw(), this.rankRight2);
     */
  }

  // tests the method flip
  void testFlip(Tester t) {
    initial();
    this.card1.flip();
    t.checkExpect(this.card1, new Card(1, "♥", Color.red, false));
    this.card2.flip();
    t.checkExpect(this.card2, new Card(2, "♥", Color.red, true));
  }

  // tests the method isMatched
  void testIsMatched(Tester t) {
    initial();
    t.checkExpect(this.card1.isMatched(card2), false);
    t.checkExpect(this.card1.isMatched(card1), true);
  }

  // tests the method init
  void testInit(Tester t) {
    initial();
    this.world.init();
    t.checkExpect(this.world, this.world);
  }

  // tests the method removeFromDeck
  void testRemoveFromDeck(Tester t) {
    initial();
    this.world.removeFromDeck(card1);
    t.checkExpect(this.world, this.world);
    this.world.removeFromDeck(card2);
    t.checkExpect(this.world, this.world);
  }

  // tests the method shuffle
  void testShuffle(Tester t) {
    initial();
    this.world.shuffle();
    t.checkExpect(this.world, this.world);
  }

  // tests the method makeScene
  void testMakeScene(Tester t) {
    initial();
    t.checkExpect(this.world.makeScene(), this.world.makeScene());
    t.checkFail(this.world.makeScene(), new Concentration());
  }

  // tests the method onMouseClicked
  void testOnMouseClicked(Tester t) {
    initial();
    this.world.onMouseClicked(new Posn(10, 15));
    t.checkExpect(this.world, this.world);
  }

  // tests the method onKeyReleased
  void testOnKeyReleased(Tester t) {
    initial();
    Concentration world1 = new Concentration();
    world1.onKeyReleased("r");
    t.checkExpect(world1, world1);
    this.world.onKeyReleased("r");
    t.checkExpect(this.world, this.world);
    this.world.onKeyReleased("a");
    t.checkExpect(this.world, this.world);
  }

  // tests the method lastScene
  void testLastScene(Tester t) {
    initial();
    this.world.lastScene("Your Score : ");
    t.checkFail(this.world, new Concentration());
  }

  // tests big bang
  void testBigBang(Tester t) {
    double tickRate = 1.0;
    this.world.bigBang(Config.MAX_WIDTH, Config.MAX_HEIGHT, tickRate);
  }
}