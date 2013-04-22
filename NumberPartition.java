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
  static int max_iter = 25000;
  private static final long MAX_LONG = 100000000000L;

	public static void main(String[] args) {
    String filename = args[0];
    Long[] test = {new Long(10), new Long(8),
      new Long(7), new Long(6), new Long(5)};
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
    System.out.println(np.karmarkarKarp(a));
    System.out.println(np.random_alg(a));
	}

  public Long[] prePartition(Long[] a, Long[] p) {
    int len = a.length;
    // initialize ans to all zeroes
    Long[] ans = new Long[len];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = new Long(0);
    }

    for (int i = 0; i < len; i++) {
      ans[(int)(p[i].longValue())] += a[i];
    }
    return ans;
  }

  public long karmarkarKarp(Long[] arr) {
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

  public static long random_alg(Long[] arr){
    Random generator = new Random(System.currentTimeMillis());
    int rand = 0;
    Long best_residue = new Long(MAX_LONG);
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

  public static long hill_climb(Long[] in_arr){
    Random generator = new Random();
    Long[] solution = new Long[100];
    Long[] neighbor = new Long[100];
    Long best_residue = MAX_LONG;
    int rand = 0;
    int rand_index = 0;

    // for(int j=0; j<max_iter; j++){

    // }

    Long current_residue = 0;
    solution = generate_rand_soln();
    for(int i=0; i<100; i++){
      current_residue += solution[i]*in_arr[i];
    }
    neighbor = find_neighbor(solution);
    
  }
  public static Long[] find_neighbor(Long[] solution){
    Random generator = new Random();
    int rand = generator.nextInt() % 2;
    int rand_index = generator.nextInt() % 100;
    Long[] neighbor = new Long[100];
    if(rand == 0){
      solution[rand_index] = -solution[rand_index];
    }
    else{
      solution[rand_index] = -solution[rand_index];
      rand_index = generator.nextInt() % 100;
    }
  }
  public static Long[] generate_rand_soln(){
    Random generator = new Random();
    int rand = 0;
    for(int i=0; i<100; i++){
      rand = generator.nextInt();
      if(rand % 2 == 0){
        solution[i] = 1;
      }
      else{
        solution[i] = -1;
      }
    }
    return solution;
  }
}
