import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.io.IOException;
import javax.lang.model.element.Element;

/**
 * Class with two different methods to count inversions in an array of integers.
 * @author Layanne El Assaad lae2146
 * @version 1.0.0 November 17, 2022
 */
public class InversionCounter {

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses nested loops to run in Theta(n^2) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsSlow(int[] array) {
        // TODO
        long inversionCount = 0; //variable that stores the inversion count
        //the slow inversion counter uses nested for loops. Time complexity: O(n^2)
        for (int i = 0; i<array.length - 1; i++){ // loop through each element
            for (int j = i+1; j<array.length; j++){ //compares element to the element to its right
                if (array[j]<array[i]){
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses mergesort to run in Theta(n lg n) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsFast(int[] array) {
        // Make a copy of the array so you don't actually sort the original
        // array.
        int[] arrayCopy = new int[array.length],
                scratch =  new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        // TODO - fix return statement
        //we will use the merge sort algorithm that was provided
        return mergeSortHelper(arrayCopy, scratch, 0, arrayCopy.length-1, 0);
    }

    private static long mergeSortHelper(int[]array, int[] scratch, int low, int high, long inversionsCount){

        if (low<high) {
            int mid = low + (high - low) / 2;
            inversionsCount = mergeSortHelper(array, scratch, low, mid, inversionsCount);
            inversionsCount = mergeSortHelper(array, scratch, mid + 1, high, inversionsCount);
            int i = low, j = mid + 1;
            for (int k = low; k <= high; k++) {
                if (i <= mid && (j > high || array[i] <= array[j])) {
                    scratch[k] = array[i++];
                } else {
                    scratch[k] = array[j++];
                    inversionsCount = inversionsCount + (mid + 1 - i);
                }
            }

            for (int k = low; k <= high; k++) {
                array[k] = scratch[k];
            }
        }

        return inversionsCount;
    }

    /**
     * Reads an array of integers from stdin.
     * @return an array of integers
     * @throws IOException if data cannot be read from stdin
     * @throws NumberFormatException if there is an invalid character in the
     * input stream
     */
    private static int[] readArrayFromStdin() throws IOException,
            NumberFormatException {
        List<Integer> intList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        int value = 0, index = 0, ch;
        boolean valueFound = false;
        while ((ch = reader.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                valueFound = true;
                value = value * 10 + (ch - 48);
            } else if (ch == ' ' || ch == '\n' || ch == '\r') {
                if (valueFound) {
                    intList.add(value);
                    value = 0;
                }
                valueFound = false;
                if (ch != ' ') {
                    break;
                }
            } else {
                throw new NumberFormatException(
                        "Invalid character '" + (char)ch +
                                "' found at index " + index + " in input stream.");
            }
            index++;
        }

        int[] array = new int[intList.size()];
        Iterator<Integer> iterator = intList.iterator();
        index = 0;
        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }
        return array;
    }

    public static void main(String[] args) {
        try {
            boolean slow = false;

            if (args.length > 0) {
                if (Objects.equals(args[0], "slow")) {
                    slow = true;
                    System.out.println("Usage: java InversionCounter [slow]");
                }else if (args.length >1){
                    throw new UnsupportedOperationException("java InversionCounter [slow]");
                }else {
                    System.out.println("Unrecognized option " + args[0] + ".");
                }
            }

            System.out.print("Enter sequence of integers, each followed by a space: ");


            int[] arr = readArrayFromStdin();

            if (arr.length <=0){
                throw new Exception("Sequence of integers not received");
            }

            if (slow) {
                System.out.println("number of inversions: " + countInversionsSlow(arr));
            } else {
                System.out.println("number of inversions: " + countInversionsFast(arr));
            }
            System.exit(0);


        } catch (UnsupportedOperationException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
