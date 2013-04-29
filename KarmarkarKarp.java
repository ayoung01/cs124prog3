import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.io.*;
import java.nio.charset.Charset;

public class KarmarkarKarp {
	
	private MaxHeap<Long> heap;
  private MaxHeap<Long> savedHeap;
  private String log;
  private int flag;

	// if x is 1, then we are in verbose (debugging) mode!
  public KarmarkarKarp(Long[] arr, int x) {
    log = Arrays.toString(arr) + "\r\n";
    long sum = 0;
    for (int i = 0; i < arr.length; i++) {
      sum += arr[i];
    }
    log = log + "SUM: " + sum + "\r\n";

    Long[] arr_copy = arr.clone();
		heap = new MaxHeap<Long>(arr_copy, arr.length, arr.length);
    savedHeap = heap;
    flag = x;
	}

	// returns the KK residue given a list of longs
  public long residue() {
    // while max heap has more than 1 element
    // remove top two elements from max heap and take the difference
    while (heap.heapsize() > 1) {
      Long x = heap.removemax();
      Long y = heap.removemax();

      // insert the difference into the heap
      heap.insert(x - y);
      if (flag == 1) {
        log = log + "Popped MAX1 (" + x + ")\r\n";
        log = log + "Popped MAX2 (" + y + ")\r\n";
        log = log + "Difference inserted (" + (x-y) + ")\r\n";
      }
    }
    // when the heap contains 1 element, return
    long residue = heap.removemax().longValue();

    if (flag ==1) {
      log = log + "FINAL RESIDUE: " + residue;
      try {
        // Create file 
        FileWriter fstream = new FileWriter("log.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(log);
        out.close();
      }
      catch (Exception e){
          System.err.println("Error: " + e.getMessage());
      }
    }
    return residue;
  }  
}
