import java.util.ArrayList;
import java.util.Arrays;

// a class with constants
class Config {
  static int MAX_WIDTH = 1200;
  static int MAX_HEIGHT = 680;
  static ArrayList<String> suits = new ArrayList<String>(Arrays.asList("♠", "♦", "♣", "♥"));
  static int CARD_WIDTH = 75;
  static int CARD_HEIGHT = 100;
  static int MARGIN_X = 15;
  static int MARGIN_Y = 50;
  static int START_X = (CARD_WIDTH / 2) + (MAX_WIDTH - ((13 * CARD_WIDTH) + (12 * MARGIN_X))) / 2;
  static int START_Y = (CARD_HEIGHT / 2) + (MAX_HEIGHT - ((4 * CARD_HEIGHT) + (3 * MARGIN_Y))) / 2;
  static int shuffleNumber = 200;
  static int MAX_LIFE = 50;
}