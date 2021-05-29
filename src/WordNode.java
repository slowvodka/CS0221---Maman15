/**
 * class implements a single WordNode
 * each wordnode stores a word, pointer to next wordnode and how many time the word appears
 */
public class WordNode {

    //variables
    private String _word;
    private WordNode _next;
    private int _appear;

    //constructors

    /**
     * constructor - constructs a wordnode
     * @param word string - the word to assign to the list
     * @param next wordnode - next word in list
     */
    public WordNode (String word, WordNode next){
        _word = word;
        _appear = 1 ;
        _next = next;
    }//end of constructor

    /**
     * constructor - if next is not given sets it to null
     * @param word - string - word to add to the node
     */
    public WordNode (String word){
        _word = word;
        _appear = 1;
        _next = null;
    }//end of constructor

    //can add methods

    /**
     * method to get the next node
     * @return Wordnode - next wordnode
     */
    public WordNode getNext(){
        return _next;
    }//end of getNext

    /**
     * set a new next to WordNode
     * @param newNext - Wordnode - the new Wordnode
     */
    public void setNext(WordNode newNext){
        _next = newNext;
    }//end of setNext

    /**
     * method to extract the word that is stored in the current Wordnode
     * @return String - the word stored
     */
    public String getWord(){
        return _word;
    }//end of getWord


    /**
     * set how many times specific word appeared in the original text
     * @param app - int number of times a word appeared
     */
    public void setAppear(int app){
        _appear = app;
    }// end of addAppear

    /**
     * extract how many times specific word appeared in the original text
     * @return int - appearences of the word in the text
     */
    public int getAppear(){
        return _appear;
    }// end of getAppear

}//end of WordNode

