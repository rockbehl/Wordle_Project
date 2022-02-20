import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordleGame {
        private ArrayList<String> Dictionary = new ArrayList<>();
        private Random rand = new Random();
        private char[][] board = new char[5][6];
        private String[][] indicators = new String[5][6];
        private char aChar = 0x00002588;
        private int turns = 0;
        private boolean win;

        WordleGame(){
            win = false;
        }

        public void run() throws IOException {
            File file = new File("wordle-answers-alphabetical.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                Dictionary.add(st);
            }

            String CW = Dictionary.get(rand.nextInt(Dictionary.size()));
            //System.out.println(CW);

            System.out.println();
            setup();

            while (turns < (board[0].length-1)) {
                //System.out.println(CW);
                userInterface(CW);
                display();
                if (win){
                    break;
                }
                turns++;
            }
            if (!win){
                System.out.println();
                System.out.println("-=-=-=- The word was: "+CW+" -=-=-=-");
            }

        }

        public void userInterface(String CW) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Guess: ");
            String userGuess = sc.nextLine();
            while (!Dictionary.contains(userGuess) || userGuess.length() != 5){
                System.out.println("Word not in list!");
                System.out.print("Try Again: ");
                userGuess = sc.nextLine();
            }
            check(userGuess, CW);
            userGuess = userGuess.toUpperCase();
            char[] uc = userGuess.toCharArray();

            for (int i = 0; i < board.length; i++) {
                board[turns][i] = uc[i];
            }

        }

        public void setup(){

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board[j][i] = aChar;
                }
            }

            for (int i = 0; i < indicators.length; i++) {
                for (int j = 0; j < indicators.length; j++) {
                    indicators[j][i] = "🆓️";
                }
            }

        }
        public void display(){

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    System.out.print(" " + board[i][j]);
                }
                System.out.print("   ||   ");
                for (int j = 0; j < indicators.length; j++) {
                    System.out.print(" " + indicators[i][j]);
                }
                System.out.println();
            }

        }
        public void check(String ug, String CW){
            ug = ug.toLowerCase();

            char[] cwChars = CW.toCharArray();
            ArrayList<Character> cwList = new ArrayList<>();
            for (int i = 0; i < cwChars.length; i++) {
                cwList.add(cwChars[i]);
            }
            char[] ugChars = ug.toCharArray();
            ArrayList<Character> ugList = new ArrayList<>();
            for (int i = 0; i < ugChars.length; i++) {
                ugList.add(ugChars[i]);
            }

            if (ug.equals(CW)){
                for (int i = 0; i < indicators.length; i++) {
                    indicators[turns][i] = "🟩";
                }
                win = true;
            } else {
                //-------------------Attempt #2----------------------
                //System.out.println(cwList);
                for (int i = 0; i < ugChars.length; i++) {
                        if (cwList.contains(ugList.get(i))) {
                            if (cwList.get(i) == ugList.get(i)) {
                                indicators[turns][i] = "🟩";
                            } else {
                                indicators[turns][i] = "🟨";
                            }
                        } else {
                            indicators[turns][i] = "⬜️️";
                        }

                }
            }
        }
}


