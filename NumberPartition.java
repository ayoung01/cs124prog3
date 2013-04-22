import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author Albert Young, Peregrine Badger
 */

public class NumberPartition {
	public NumberPartition() {

	}


	public static void main(String[] args) {
    String filename = args[1];
    Long[] a = new Long[100];
    try {
      InputStream fis = new FileInputStream(filename);
      BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
      for (int i = 0; i < 100; i++) {
        a[i] = new Long(Long.parseLong(br.readLine()));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    NumberPartition np = new NumberPartition();
    System.out.println(np.KarmarkarKarp(a));
	}

  public long KarmarkarKarp(Long[] arr) {
    MaxHeap<Long> heap = new MaxHeap<Long>(arr, arr.length, arr.length);
    if (arr.length < 1) {
      System.out.println("Incorrect input size");
      return -1;
    }
    // while max heap has more than 1 element
    // remove top two elements from max heap and take the difference
    while (heap.heapsize() > 1) {
      long a = heap.removemax().longValue();
      long b = heap.removemax().longValue();
      Long difference = new Long (a - b);
      // reinsert the difference into the heap
      heap.insert(difference);
    }
    // when the heap contains 1 element, return
    return heap.removemax().longValue();
  }
}
