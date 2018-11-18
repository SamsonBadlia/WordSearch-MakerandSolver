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


  public static void main(String[] args) {
    if (args.length < 3){
      System.out.println("Not enough temrinal inputs");
    }
    if (args.length == 3){
      try{
        int seed = (int)(Math.abs(Math.random()*10000));
        WordSearch WS = new WordSearch(Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2]);
        WS.fillRandomLetters();
        System.out.print(WS);
      }
      catch (NumberFormatException e){
        System.out.println("Something is wrong with the inputs");
      }
      catch (IllegalArgumentException f){
        System.out.println("Bad Value Found");
      }
    }
      if (args.length == 4){
        try{
          WordSearch WS = new WordSearch(Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2],Integer.parseInt(args[3]),false);
          WS.fillRandomLetters();
          System.out.print(WS);
        }
        catch (NumberFormatException e){
          System.out.println("Something is wrong with the inputs");
        }
        catch (IllegalArgumentException f){
          System.out.println("Bad Value Found");
        }
      }
        if (args.length > 4){
          for (int i = 0; i < args.length; ++i){
            if (args[i] == "key"){
                try{
                  WordSearch WS = new WordSearch(Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2],Integer.parseInt(args[3]),true);
                  WS.fillRandomLetters();
                  System.out.print(WS);
                }
                catch (NumberFormatException e){
                  System.out.println("Something is wrong with the inputs");
                }
                catch (IllegalArgumentException f){
                  System.out.println("Bad Value Found");
                }
          }
        }
      }
    }

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
      if (rowIncrement < -1 || colIncrement < -1 || rowIncrement > 1 || colIncrement > 1){
        return false;
      }
      if (rowIncrement == 0 && colIncrement == 0){
        return false;
      }
      char[][] copy;
      copy = makeCopy();
      if (col + 1 + (word.length() * colIncrement) > data[0].length + 1){
        return false;
      }
      if (row + 1 + (word.length() * rowIncrement) > data.length + 1){
        return false;
      }
      if (col + 1 + (word.length() * colIncrement) < 0 ){
        return false;
      }
      if (row + 1 + (word.length() * rowIncrement) < 0 ){
        return false;
      }
      for (int i = 0; i < word.length(); ++i){
        if (copy[row][col] == word.charAt(i) || copy[row][col] == '_'){
          copy[row][col] = word.charAt(i);
          row += rowIncrement;
          col += colIncrement;
        }
        else {
          return false;
        }
      }
      data = copy;
      return true;
    }

    private char[][] makeCopy(){
      char[][] copy = new char[data.length][data[0].length];
      for (int i = 0; i < data.length; ++i){
        for (int x = 0; x < data[i].length; ++x){
          copy[i][x] = data[i][x];
        }
    }
    return copy;
  }

    public void addAllWords(String fileName){
      wordsToAdd = getWords(fileName);
      int tries = 0;
      String word;
      for (int i = 0; i < wordsToAdd.size(); ++i){
        word = wordsToAdd.get(i);
        int r = Math.abs(randgen.nextInt()%data.length);
        int c = Math.abs(randgen.nextInt()%data[0].length);
        int rI = Math.abs(randgen.nextInt()%2);
        int cI = Math.abs(randgen.nextInt()%2);
        while (tries < 500 && addWord(word, r, c, rI, cI) == false){
            tries++;
            if (addWord(word, r, c, rI, cI)){
              wordsAdded.add(word);
              wordsToAdd.remove(i);
            }
          }
          wordsToAdd.remove(i);
          }
        }
      }
