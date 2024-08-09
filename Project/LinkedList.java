public class LinkedList {
    SearchNode head; //head of linkedlist
    int size; //size of linkedlist

    public void addData(ClimateRecord data){
        SearchNode info = new SearchNode(data); //creating new node with details of new song

        if(size==0){ // if the list is empty
            head = info;
        }
        else{
            info.next = head;
            head = info;
        }
        size++;
    }

    public void display(){ //displaying playlist in order
        SearchNode h = head;
        int count=1;
        while(count<=size){ //condition to go through the whole list
            System.out.println(h.data);//need to print all content
            h = h.next;
            count++;
        }
    }

}