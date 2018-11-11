public class WordSearch{

    private char[][]data;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows,int cols){
      data = new char[rows][cols];
      for (int i = 0; i < data.length; ++i){
        for (int x = 0; x < data[i].length; ++x){
          data[i][x] = '_';
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

    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
    public String toString(){
      String s = "";
      for (int i = 0; i < data.length; ++i){
        for (int x = 0; x < data[i].length; ++x){
          s += data[i][x] + " ";
        }
        if (i == data.length - 1){
          s += "\n";
        }
      }
      return s;
    }

    public boolean addWord(String word, int row, int col, int rowIncrement, int colIncrement){
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

}
