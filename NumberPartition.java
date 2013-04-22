import java.util.Random;
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

 //  static Long[] test_long = {new Long(10),new Long(15),new Long(3),new Long(10),new Long(9),new Long(19),new Long(19),new Long(1),new Long(15),new Long(15),new Long(15),new Long(15),new Long(15),new Long(15)};
  static int max_iter = 2225000;
  private static final long MAX_LONG = 100000000000L;


	public static void main(String[] args) {
    String filename = args[0];
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
    System.out.println(np.Random_alg(a));
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

  public static long Random_alg(Long[] arr){
    Random generator = new Random(110000);
    int rand = 0;
    Long best_residue = new Long(MAX_LONG);
    // this ^^ should be the largest number in set, or initialized on first pass...
    Long current_residue = new Long(0);
    for(int iter = 0; iter<max_iter; iter++){
      current_residue = new Long(0);
      for(int arr_iter = 0; arr_iter<arr.length; arr_iter++){
        rand = generator.nextInt();
        if((rand % 2) == 0){
          current_residue += arr[arr_iter];
        }
        else{
          current_residue -= arr[arr_iter];
        }        
      }
      current_residue = Math.abs(current_residue);
      if (current_residue < best_residue){
        best_residue = current_residue;
      }
      // System.out.println(best_residue.longValue());
    }
    return best_residue.longValue();
  }
}
