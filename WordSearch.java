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
    public WordSearch(int rows,int cols, String fileName){
      if (rows < 0 || cols < 0){
        throw new IllegalArgumentException("Rows/Columns cannot be negative");
      }
      wordsToAdd = new ArrayList<>();
      wordsAdded = new ArrayList<>();
      data = new char[rows][cols];
      clear();
      randgen = new Random();
      seed = randgen.nextInt();
      try{
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
          String str = s.nextLine().toUpperCase();
          wordsToAdd.add(str);
        }
      }
      catch(FileNotFoundException e){
        System.out.println("Wheres the file: " + fileName);
      }
    }

    public WordSearch( int rows, int cols, String fileName, int randSeed, boolean answer){
      if (rows < 0 || cols < 0){
        throw new IllegalArgumentException("Rows/Columns cannot be negative");
      }
      data = new char[rows][cols];
      clear();
      randgen = new Random(randSeed);
      addAllWords(fileName);
      if (!answer){
        fillRandomLetters();
      }
    }

    private ArrayList<String> getWords(String fileName){
      try {
      File file = new File(fileName);
      Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
          wordsToAdd.add(sc.next());
        }
        catch(FileNotFoundException e){
          System.out.println("Wheres file : " + fileName);
        }
        return wordsToAdd;
      }

    private void fillRandomLetters(){
      for (int i = 0; i < data.length; ++i){
        for (int x = 0; x < data[i].length; ++x){
          int ran = Math.abs(randgen.nextInt()% 27);
          if (data[i][x] == '_'){
            data[i][x] = (char)('A' + ran);
          }
        }
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

    private void addAllWords(String fileName){
      wordsToAdd = getWords(fileName);
      int len = wordsToAdd.size();
      int size = data.length;
      int size2 = data[0].length;
      while (len > 0){
        int index = Math.abs(randgen.nextInt()%len);
        String word = wordsToAdd.get(index);
        int row = Math.abs(randgen.nextInt()%size);
        int col = Math.abs(randgen.nextInt()%size2);
        int rowIncrement = randgen.nextInt()%2;
        int colIncrement = randgen.nextInt()%2;
        int tries = 0;
        while (tries < (size*size2)){
          if (!addWord(word, row, col, rowIncrement, colIncrement)){
            row = Math.abs(randgen.nextInt()%size);
            col = Math.abs(randgen.nextInt()%size2);
            rowIncrement = randgen.nextInt()%2;
            colIncrement = randgen.nextInt()%2;
            tries++;
          }
          else {
              wordsToAdd.remove(index);
              len--;
          }
        }

      }
    }

}
