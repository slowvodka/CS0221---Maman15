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

        //how many words O(n)
        int wordCounter = 0;
        for (int i = 0; i < text1.length(); i++) {
            if (text1.charAt(i) == ' ')
                wordCounter++;
        }

        String[] textAsArray = new String[wordCounter];

        int endOfWord = 0;
        String word = "";
        //breaking and adding the text to unsorted list \O(n)
        int i = 0;
        //complexity is O(n) - because we know the worst case is a m<n word length so complexity
        //of indexof here is 1
        while (endOfWord != -1 && text1.length() > 0 )
        {
            endOfWord = text1.indexOf(" ");
            if (endOfWord != -1)
            {
                word = text1.substring(0, endOfWord);
                textAsArray[i] = word;
                i = i+1;
                text1 = text1.substring(endOfWord+1);
            }
        }

        //complexity O(nlog(n))
        mergeSort(textAsArray,wordCounter);

        //compexity O(n)
        for (i = wordCounter-1 ; i>=0; i-- )
            addToTop(textAsArray[i]);

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
     * iterates over the list, removes duplicates and add them to one node
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
     * implementation of merge sort
     */
    private void mergeSort(String[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        String[] l = new String[mid];
        String[] r = new String[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);

    }// end of mergeSort

    /**
     * implementation of merge sort
     */
    private void merge(String[] a, String[] l, String[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].compareTo(r[j]) <= 0) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }//end of merge

    /**
     * private method to check if list is empty
     * @return boolean - true if list is empty
     */
    private boolean isEmpty(){
        return _first==null;
    }//end of isEmpty - private

}//end of TextList
