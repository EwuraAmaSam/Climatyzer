
/**
 * A class that sorts data in an array according to some specified key
 * The data in the array are objects so when a key is passed, we create the method for the key
 * And then use that method to access the value of the key and sort based on that
 * 
 * @author Daniel, Ewura Ama, Dave, Christine
 */
import java.lang.reflect.*;

public class DataSorting {
    /**
     * A method to recursively divide an array into 2 until the base case is reached
     * When the base case is reached, it starts merging them up until they come
     * together
     * 
     * @param array the array to be sorted
     * @param key   the parameter that we will base on to sort the array
     */
    public static void mergeSort(ClimateRecord[] array, String key) {
        // Checking if the array has more than 1 element
        // There will be no need to sort if the element is only 1

        if (array.length > 1) {

            // Finding the middle index and creating
            // Arrays to hold the left and right sub arrays

            int mid = array.length / 2;

            ClimateRecord[] leftSide = new ClimateRecord[mid];
            ClimateRecord[] rightSide = new ClimateRecord[array.length - mid];

            // Copying the array elements into the left and right sub arrays

            System.arraycopy(array, 0, leftSide, 0, mid);
            System.arraycopy(array, mid, rightSide, 0, array.length - mid);

            // Recursively calling the mergeSort method on the left and right side

            mergeSort(leftSide, key);
            mergeSort(rightSide, key);

            // Calling the merge method which is defined below to merge the elements
            // Back
            merge(array, leftSide, rightSide, key);

        }
    }

    /**
     * A method to merge two sub arrays while sorting them.
     * In this method, two values from both arrays are compared at a time
     * And the one that is smaller gets added to the array and its index gets
     * increased
     * This continuous until the elements in one sub array are done
     * In which case the rest of the elements in the other sub array are added
     * 
     * @param array     the array to be sorted
     * @param leftSide  the left half of the instantaneous array
     * @param rightSide the right side of the instantaneous array
     * @param key       the parameter on which we are sorting
     */
    @SuppressWarnings("unchecked")
    private static void merge(ClimateRecord[] array, ClimateRecord[] leftSide, ClimateRecord[] rightSide, String key) {
        int i = 0; // Index for the left subarray
        int j = 0; // Index for the right sub array
        int k = 0; // Index for the main array

        // Trying to get a method to use for the specified key
        // It will be possible that there
        // Is no such key
        try {
            String method = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
            Method getter = ClimateRecord.class.getMethod(method);
            while (i < leftSide.length && j < rightSide.length) {
                // Casting the value we get from the method call to a comparable so that we can
                // compare
                @SuppressWarnings("rawtypes")
                Comparable leftSideValue = (Comparable) getter.invoke(leftSide[i]);
                
                @SuppressWarnings("rawtypes")
                Comparable rightSideValue = (Comparable) getter.invoke(rightSide[j]);

                // Compare the right and left values
                
                if (leftSideValue.compareTo(rightSideValue) <= 0) {
                    array[k] = leftSide[i];
                    i++;
                    k++;

                } else {
                    array[k] = rightSide[j];

                    k++;
                    j++;
                }
            }
            // Put the remain elements in the left sub array into the
            // Array if there are any
            while (i < leftSide.length) {
                array[k] = leftSide[i];
                k++;
                i++;

            }
            // Put the remain elements in the right sub array into the
            // Array if there are any
            while (j < rightSide.length) {
                array[k] = rightSide[j];
                k++;
                j++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}