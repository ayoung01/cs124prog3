import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class KarmarkarKarp {
	
	private MaxHeap<Long> heap;
	public KarmarkarKarp(Long[] arr) {
		heap = new MaxHeap<Long>(arr, arr.length, arr.length);
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
    }
    // when the heap contains 1 element, return
    return heap.removemax().longValue();
  }

  
}