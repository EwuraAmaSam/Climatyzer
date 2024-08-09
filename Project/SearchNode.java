/** 
 * This is a abstract class for the search Node that is used in the binary search class
 * @author Ewurama , Dave ,Daniel, Christine 
 */
public class SearchNode {// this is to be used in a singly linked list to keep record of found search data
    ClimateRecord data;
    SearchNode next;
    /**
     * This is the node for the linkedlist
     * @param data the information being taken for search
     */
    public SearchNode(ClimateRecord data){
        this.data= data;
    }
}
