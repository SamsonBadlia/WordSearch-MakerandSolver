import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception

public class WordSearch{

  private char[][]data;

  //the random seed used to produce this WordSearch
  private int seed;

  //a random Object to unify your random calls
  private Random randgen;

  //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
  private ArrayList<String>wordsToAdd;

  //all words that were successfully added get moved into wordsAdded.
  private ArrayList<String>wordsAdded;


    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows,int cols, String fileName) throws FileNotFoundException {
      if (rows < 0 || cols < 0){
        throw new IllegalArgumentException("Rows/Columns cannot be negative");
      }
      wordsToAdd = new ArrayList<>();
      wordsAdded = new ArrayList<>();
      data = new char[rows][cols];
      clear();
      randgen = new Random();
      seed = randgen.nextInt();
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
          String str = s.nextLine().toUpperCase();
          wordsToAdd.add(str);
        }
    }

    public WordSearch( int rows, int cols, String fileName, int randSeed) throws FileNotFoundException {
      if (rows < 0 || cols < 0){
        throw new IllegalArgumentException("Rows/Columns cannot be negative");
      }
      wordsToAdd = new ArrayList<>();
      wordsAdded = new ArrayList<>();
      data = new char[rows][cols];
      clear();
      randgen = new Random(randSeed);
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
          String str = s.nextLine().toUpperCase();
          wordsToAdd.add(str);
        }
    }

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
      for (int i = 0; i < data.length; ++i){
        for (int x = 0; x < data[i].length; ++x){
          data[i][x] = '_';
        }
      }
    }

    public String toString(){
      String s = "" ;
      for (int i = 0 ; i < data.length ; ++i) {
        s += "|" ;
        for (int x = 0 ; x < data[i].length ; ++x) {
          if (x < data[i].length-1) {
            s += data[i][x] + " ";
          }
          else {
            s += data[i][x];
          }
        }
        s+= "|\n";
      }
      return s;
    }

    public boolean addWord(String word, int row, int col, int rowIncrement, int colIncrement){
      //Unacceptable values for directions
      if (rowIncrement == 0 || colIncrement == 0 || rowIncrement < -1 || colIncrement < -1 || rowIncrement > 1 || colIncrement > 1){
        return false;
      }
      for (int i = 0; i < word.length(); ++i){
        if (data[row][col] == word.charAt(i) || data[row][col] == '_'){
          data[row][col] = word.charAt(i);
          row += rowIncrement;
          col += colIncrement;
        }
        else {
          return false;
        }
      }
      return true;
    }

    private void addAllWords(String fileName) throws FileNotFoundException{
      int tries = 0;
      int len = wordsToAdd.size();
      File f = new File(fileName);
      Scanner s = new Scanner(f);
      while (len < 0 ){
        //random direction
        int rowIncrement = randgen.nextInt()%2;
        int colIncrement = randgen.nextInt()%2;
        //random start
        int row = randgen.nextInt()%2;
        int col = randgen.nextInt()%2;
        //checks if there are mroe words
        if (s.hasNext()){
          addWord(s.nextLine(), row, col, rowIncrement, colIncrement );
        }
        len--;
      }
    }

}
