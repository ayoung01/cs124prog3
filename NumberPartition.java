import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
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
  private final int MAX_ITER = 25000;
  private final long MAX_LONG = 100000000000L;
  private final static int NUM_INPUTS = 100; 

	public static void main(String[] args) {
    String filename = args[0];
    Long[] inputList = new Long[NUM_INPUTS];
    try {
      InputStream fis = new FileInputStream(filename);
      BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
      for (int i = 0; i < NUM_INPUTS; i++) {
        inputList[i] = new Long(Long.parseLong(br.readLine()));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    NumberPartition np = new NumberPartition();
    int[] P = np.gen_random_p(inputList.length);
    Long[] partitioned = np.prePartition(inputList, P);
    // System.out.println(Arrays.toString(P));

    // System.out.println();
    // System.out.println(Arrays.toString(partitioned));

    // System.out.println(np.karmarkarKarp(partitioned));
    
    // System.out.println(np.karmarkarKarp(inputList));
    // System.out.println(np.random_alg(inputList));
    System.out.println(np.random_alg2(inputList));
    // System.out.println(np.hill_climb(inputList));
	}

  // generates a random P array of length len
  public int[] gen_random_p (int len) {
    Random generator = new Random();
    int[] p = new int[len];
    for (int i = 0; i < len; i++) {
      p[i] = generator.nextInt(len);
    }
    return p;
  }

  // given A and randomly generated P, return A'
  public Long[] prePartition(Long[] a, int[] p) {
    int len = a.length;
    // initialize a_prime to all zeroes
    Long[] a_prime = new Long[len];
    for (int i = 0; i < a_prime.length; i++) {
      a_prime[i] = new Long(0);
    }

    for (int i = 0; i < len; i++) {
      a_prime[p[i]] += a[i];
    }
    // System.out.println(Arrays.toString(a_prime));
    return a_prime;
  }

  // returns the KK residue given a list of longs
  public long karmarkarKarp(Long[] arr) {
    MaxHeap<Long> heap = new MaxHeap<Long>(arr, arr.length, arr.length);
    if (arr.length < 1) {
      System.out.println("Incorrect input size");
      return -1;
    }

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

  public long random_alg(Long[] arr){
    Random generator = new Random(System.currentTimeMillis());
    int rand = 0;
    Long best_residue = new Long(MAX_LONG);
    Long current_residue = new Long(0);
    for(int iter = 0; iter<MAX_ITER; iter++){
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

  public long hill_climb(Long[] in_arr){
    Random generator = new Random(System.currentTimeMillis());
    int[] solution = new int[100];
    int[] neighbor = new int[100];
    Long best_residue = new Long(0);
    Long current_residue = new Long(0);
    int rand = 0;
    int rand_index = 0;
    solution = generate_rand_soln();
    for(int i=0; i<100; i++){
      best_residue += solution[i]*in_arr[i];
    }
    best_residue = Math.abs(best_residue);
    for(int j=0; j<MAX_ITER; j++){
      neighbor = find_neighbor(solution);
      for(int i=0; i<100; i++){
        current_residue += neighbor[i]*in_arr[i];
      }
      current_residue = Math.abs(current_residue);
      if(current_residue < best_residue){
        best_residue = current_residue;
      }
    }
    return best_residue;
  }
  public int[] find_neighbor(int[] solution){
    Random generator = new Random(System.currentTimeMillis());
    int rand = generator.nextInt() % 2;
    int[] switch_array = new int[100];
    for(int i=0; i<100; i++){
      switch_array[i] = i;
    }
    Collections.shuffle(Arrays.asList(switch_array));
    int rand_index = switch_array[0];

    Long[] neighbor = new Long[100];
    if(rand == 0){
      solution[rand_index] = -solution[rand_index];
    }
    else{
      solution[rand_index] = -solution[rand_index];
      rand_index = switch_array[1];
      solution[rand_index] = -solution[rand_index];
    }
    return solution;
  }
  public int[] generate_rand_soln(){
    Random generator = new Random();
    int rand = 0;
    int[] solution = new int[100];
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

  // returns KK residue from random prepartion solution
  public long random_alg2(Long[] arr) {
    Long[] a = arr;
    int[] best_p = gen_random_p(arr.length);
    int best_iter = -1;
    long best_residue = MAX_LONG;
    long current_residue;
    for (int iter = 0; iter < 25000; iter++) {
      int[] p = gen_random_p(arr.length);
      Long[] a_prime = prePartition(a, p);
      // System.out.println(Arrays.toString(a_prime));
      current_residue = karmarkarKarp(a_prime);
      System.out.println("Current residue: (" + iter + ") " + current_residue);
      if (current_residue < best_residue) {
        best_residue = current_residue;
        best_p = p;
        best_iter = iter;
        System.out.println("Best residue: " + best_residue);
      }
    }
    System.out.println("Best P: (" + best_iter + ")" + Arrays.toString(best_p));
    return best_residue;
  }
}
