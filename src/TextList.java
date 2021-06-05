/**
 * TextList is a class the implements a text as a list
 * the only variable that it stores is a pointer to the first WordNode
 *
 * this class recieves a text, break it to words, sort it alphabetically and count how many times a
 * word appears.
 */
public class TextList {

    //variables
    private WordNode _first;

    //constructors
    /**
     * default constructor, initializes first
     */
    public TextList (){
        _first = null;
    }//end of default constructor

    /**
     * constructor that takes a text of words(only small letters a-z)
     * saparated by spaces and breaks it to a list.
     * the list is sorted lexicographically, every unique word gets only one node but count
     * the number of appearances of it
     *
     * time Complexity - O(nlogn) (because of merge sort)
     *
     * @param text - String - words separated by one space
     */
    public TextList (String text){
        this();
        String text1 = text + " ";

        //how many words O(n) since checks every position if it is " "
        int wordCounter = 0;
        for (int i = 0; i < text1.length(); i++) {
            if (text1.charAt(i) == ' ')
                wordCounter++;
        }


        int endOfWord = 0;
        String word = "";

        //complexity is O(n) - because the longest word is around 52 characters so it is constant

        //of indexof here is 1
        while (endOfWord != -1 && text1.length() > 0 )
        {
            endOfWord = text1.indexOf(" ");
            if (endOfWord != -1)
            {
                word = text1.substring(0, endOfWord);
                addToTop(word);
                text1 = text1.substring(endOfWord+1);
            }
        }

        this._first = this.mergeSort(this._first);


        //Complexity (O(n)
        mergeAppear();
    }//end of second constructor


    //methods

    /**
     * add a word to the existing database
     * word is added in the right place alphabetically.
     * @param word - String - a words to add
     *
     * Time complexity (O(n)
     */
    public void addToData (String word) {
        boolean done = false;
        //word is empty
        if (word.equals("") || word.equals(" "))
            done = true;
        //list is empty
        if (!done && this.isEmpty()){
            _first = new WordNode(word);
            done = true;
        }
        //list not empty
        if (!done)
        {
            for (WordNode cur = _first ; !done ; cur = cur.getNext())
            {
                //before first
                if (!done && cur == _first && word.compareTo(cur.getWord()) < 0 )
                {
                    WordNode newFirst = new WordNode(word, cur);
                    _first = newFirst;
                    done = true;
                }

                //in the end
                if (!done && cur.getNext() == null && word.compareTo(cur.getWord()) > 0) {
                    WordNode insert = new WordNode(word);
                    cur.setNext(insert);
                    done = true;
                }
                //if same
                if (!done && word.compareTo(cur.getWord()) == 0)
                {
                    cur.setAppear(cur.getAppear() + 1);
                    done = true;
                }
                //in the middle
                if (!done && word.compareTo(cur.getWord()) > 0 && word.compareTo(cur.getNext().getWord()) < 0){
                    WordNode insert = new WordNode(word, cur.getNext());
                    cur.setNext(insert);
                    done = true;
                }
            }//loop end
        }

    }//end of addToData

    /**
     * generates a String from list in the format:
     * 'word'   numberOfAppearances
     * example: for input "hakuna matata":
     * hakuna   1
     * matata   1
     * @return String - list as a string
     *
     * Time complexity O(n)
     */
    public String toString(){
        String s = "";
        for (WordNode cur = this._first ; cur != null ; cur = cur.getNext())
        {
            s = s + cur.getWord() + "\t" + cur.getAppear() + "\n";
        }
        return s;
    }//end of toString

    /**
     * this method counts how many words are in the textlist
     * example = "scar is the true true king" returns 6
     * @return int - how many words
     *
     * Time complexity O(n)
     *
     */
    public int howManyWords (){
        int count =0;
        if (!this.isEmpty())
            for (WordNode cur = this._first ; cur != null ; cur = cur.getNext())
                count+= cur.getAppear();
        return count;
    }//end of howManyWords

    /**
     * this method counts how many words are in the textlist
     * example = "scar is the true true king" returns 5
     * @return int - how many unique words
     *
     * Time complexity O(n)
     *
     */
    public int howManyDifferentWords (){
        int count =0;
        if (!this.isEmpty())
            for (WordNode cur = this._first ; cur != null ; cur = cur.getNext())
                count++;
        return count;
    }// end of howManyDifferentWords

    /**
     * method finds the most frequent word in list
     * in case 2 words have same frequency, returns the one that is earlier lexicographically
     * if input is empty returns empty string
     * @return - String - the most frequent word in list
     *
     * Time complexity O(n)
     *
     */
    public String mostFrequentWord (){
        int max = 0;
        String s = "";
        for (WordNode cur = this._first ; cur != null ; cur = cur.getNext()){
            if (max < cur.getAppear())
            {
                max = cur.getAppear();
                s = cur.getWord();
            }
        }
        return s;
    } //end of mostFrequentWord

    /**
     * methods that count how many words stats with char given
     * @param letter - char - letter that the words start with
     * @return int - how many words start with this char
     *
     * Time complexity O(n)
     *
     */
    public int howManyStarting (char letter){
        int count =0;
        if (!this.isEmpty())
            for (WordNode cur = this._first ; cur != null ; cur = cur.getNext())
                if (cur.getWord().indexOf(letter) == 0)
                    count = count + cur.getAppear();
        return count;
    }//end of howManyStarting

    /**
     * recursively finds out the most most Frequent Starting Letter - wrapper for actual recursive function
     * if the list is empty returns " " as most requent
     * @return char - the letter that is most frequent
     */
    public char mostFrequentStartingLetter (){
        //maxChar - the char to be returned, curChar - variable to make sure I am on the same char
        //maxApp - counter for appearances, curMax - counter for appearances of curChar
        char maxChar = 'a', curChar = 'a';
        int maxApp = 0, curMax = 0;
        //only if the list is not empty
        if (!this.isEmpty())
            return mostFrequentStartingLetter(_first,maxChar,maxApp,curChar,curMax);
        return ' ';
    }//end of mostFrequentStartingLetter

    /**
     * recursively finds out the most most Frequent Starting Letter
     * @param cur - WordNode - current node
     * @param maxChar - char - currently most frequent character - updated during recursion
     * @param maxApp - int - currently most frequent character number of appearances- updated during recursion
     * @param curChar - char - character that is evaluated now
     * @param curMax - int - appearances of character that is evaluated now
     * @return char - maxChar - most frequent character
     */
    private char mostFrequentStartingLetter (WordNode cur,char maxChar, int maxApp, char curChar, int curMax){
        //if it is more frequent then previous - update
        if (curMax > maxApp)
        {
            maxApp = curMax;
            maxChar = curChar;
        }
        //ending conditions - no next in list
        if (cur == null)
            return maxChar;
        //if we have the same starting character - add it to counter
        if (cur.getWord().charAt(0) == curChar)
        {
            curMax += cur.getAppear();
        }
        //if now we are in a different starting character
        else{

            //in any case start to count again the new char
            curMax = cur.getAppear();
            curChar = cur.getWord().charAt(0);
        }
        //go to next word
        return mostFrequentStartingLetter(cur.getNext(),maxChar,maxApp,curChar,curMax);
    }//end of mostFrequentStartingLetter


    /**
     * private method
     * iterates over the list, removes duplicates and add them to one node, updates _Appear
     */
    private void mergeAppear(){
        //list not empty
        if (!this.isEmpty() && _first.getNext() != null)
        {
            //the increment of the cur is only when the next word is different
            for (WordNode cur = _first; cur!=null;)
            {
                if (cur.getNext() !=null)
                    if (cur.getWord().compareTo(cur.getNext().getWord()) == 0) {
                        cur.setAppear(cur.getAppear() + 1);
                        cur.setNext(cur.getNext().getNext());

                    }
                    else
                        cur = cur.getNext();
                else
                    cur = cur.getNext();

            }
        }
    }//end of mergeAppear

    /**
     * private method
     * this method adds a word to the top of the list
     * @param word - String - one word
     */
    private void addToTop(String word) {
        boolean done = false;
        //word is empty
        if (word.equals("") || word.equals(" "))
            done = true;
        //list is empty
        if (!done && this.isEmpty()) {
            _first = new WordNode(word);
            done = true;
        }

        if (!done)
        {
            WordNode newFirst = new WordNode(word, _first);
            _first = newFirst;

        }
    }// end of addToTop

    /**
     * private method to check if list is empty
     * @return boolean - true if list is empty
     */
    private boolean isEmpty(){
        return _first==null;
    }//end of isEmpty - private

    //implement mergesort

    /**
     * private method that implements merge sort for lists
     * @param a wordnode
     * @param b wordnode
     * @return wordnode
     */
    private WordNode sortedMerge(WordNode a, WordNode b)
    {
        WordNode result = null;
        // Base cases
        if (a == null)
            return b;
        if (b == null)
            return a;

        // Pick either a or b, and recur
        if (a.getWord().compareTo(b.getWord()) < 0 ) {
            result = a;
            result.setNext(sortedMerge(a.getNext(), b));
        }
        else {
            result = b;
            result.setNext(sortedMerge(a, b.getNext())) ;
        }
        return result;
    }

    /**
     * this method implements list merge sort
     * @param h - Wordnode
     * @return Wordenode
     */
    private WordNode mergeSort(WordNode h)
    {
        // Base case : if head is null
        if (h == null || h.getNext() == null) {
            return h;
        }

        // get the middle of the list
        WordNode middle = getMiddle(h);
        WordNode nextofmiddle = middle.getNext();

        // set the next of middle node to null
        middle.setNext(null);

        // Apply mergeSort on left list
        WordNode left = mergeSort(h);

        // Apply mergeSort on right list
        WordNode right = mergeSort(nextofmiddle);

        // Merge the left and right lists
        WordNode sortedlist = sortedMerge(left, right);
        return sortedlist;
    }

    /** Utility function to get the middle of the linked list **/
    private static WordNode getMiddle(WordNode head)
    {
        if (head == null)
            return head;

        WordNode slow = head, fast = head;

        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow;
    }

}//end of TextList
