import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class QuickSelect {

    public static void main(String[] args) {
        Random rd = new Random();
        Scanner scanner = new Scanner(System.in);

        // Get the number of elements from the user
        System.out.print("Enter the number of elements in the array: ");
        int n = scanner.nextInt();

        // Defined array using random function, so that it will generate random n values
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt(); // storing random integers in an array

        }

        // Show the output of random generated points
        System.out.println("N elements " + Arrays.toString(arr));

        // middle value
        int k = n / 2;

        // function for calculating time in nanoseconds
        long start = System.nanoTime();

        SelectPivot(arr, 0, n - 1, k - 1);

        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);

        // Call the SelectPivot function to get the pivot
        System.out.println("Median element " + SelectPivot(arr, 0, n - 1, k - 1));
    }

    private static int SelectPivot(int[] arr, int start, int end, int k) {
        if (start <= end) {
            int pivot = MedianOfMedians(arr, start, end);
            int MiddleIndex = partition(arr, start, end, pivot);
            if (MiddleIndex == k) {
                return arr[MiddleIndex];
            } else if (MiddleIndex > k) {
                return SelectPivot(arr, start, MiddleIndex - 1, k);
            } else {
                return SelectPivot(arr, MiddleIndex + 1, end, k);
            }
        }
        return 999999999;
    }

    // Function to get the median of medians from n/5 groups.
    private static int MedianOfMedians(int[] arr, int s, int e) {
        int n = e - s + 1;
        int groups = (int) Math.ceil((double) n / 5); // Used ceil function to divide the  array into n / 5 groups
        int[] medians = new int[groups];

        for (int i = 0; i < groups; i++) {
            int groupStart = s + i * 5;
            int groupEnd = Math.min(groupStart + 4, e);
            medians[i] = findMedian(arr, groupStart, groupEnd);
        }

        if (medians.length == 1) {
            return medians[0];
        }
        return SelectPivot(medians, 0, medians.length - 1, medians.length / 2);
    }

    // Function to return the median of the array
    private static int findMedian(int[] arr, int s, int e) {
        Arrays.sort(arr, s, e + 1);
        return arr[s + (e - s) / 2];
    }

    // function to find the exact position of pivot and partition the array into 2 halves
    private static int partition(int[] arr, int s, int e, int pivot) {
        for (int i = s; i <= e; i++) {
            if (arr[i] == pivot) {
                swap(arr, i, e);
                break;
            }
        }
        int pIndex = s;
        for (int i = s; i < e; i++) {
            if (arr[i] <= pivot) {
                swap(arr, i, pIndex);
                pIndex++;
            }
        }
        swap(arr, pIndex, e);
        return pIndex;
    }

    // swap function
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}