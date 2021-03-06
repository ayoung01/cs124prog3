import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.io.*;
import java.nio.charset.Charset;


/**
 * @author Albert Young, Peregrine Badger
 */

public class NumberPartition {

  private final static int MAX_ITER = 250000;
  private final static long MAX_LONG = 100000000000L;
  private final static int NUM_INPUTS = 100;
  private static boolean verbose = false; 

	public static void main(String[] args) {
    String filename = args[0];
    Long[] inputList = readInputs(filename);
    NumberPartition np = new NumberPartition();
    if (verbose) {
      // System.out.println("Residue: " + np.random_alg(inputList));
      // System.out.println("Residue Hill Climb: " + np.hill_climb(inputList));
      System.out.println("Residue Sim Annealed: " + np.sim_annealed(inputList));
      // System.out.println("RESIDUE: " + np.random_alg_pp(inputList));
      // System.out.println("RESIDUE: " + np.random_alg_pp_hill(inputList));
      // System.out.println("RESIDUE: " + np.random_pp_sim_annealed(inputList));
      System.out.println("ITERATIONS: " + MAX_ITER);
    }
	}

  public static Long[] readInputs(String filename) {
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
    return inputList;  
  }

  public long random_alg(Long[] arr){
    Random generator = new Random();
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
        if (verbose)
          System.out.println("best_residue: "+best_residue);
        best_residue = current_residue;
      }
    }
    return best_residue.longValue();
  }

  public long hill_climb(Long[] in_arr){
    int[] solution = new int[NUM_INPUTS];
    int[] neighbor = new int[NUM_INPUTS];
    Long best_residue;
    Long new_residue;
    solution = generate_rand_soln();
    best_residue = calculate_residue(solution, in_arr);
    for(int j=0; j<MAX_ITER; j++){

      neighbor = find_neighbor(solution);
      new_residue = calculate_residue(neighbor,in_arr);

      if(new_residue < best_residue){
        if (verbose)
          System.out.println("best_residue: "+new_residue);
        best_residue = new_residue;
        solution = neighbor;
      }
    }
    return best_residue;
  }

  public long calculate_residue(int[] pos_neg, Long[] magnitudes){
    Long residue = new Long(0);
    for(int i=0;i<NUM_INPUTS;i++){
      residue += pos_neg[i]*magnitudes[i];
    }
    return Math.abs(residue);
  }
  public int[] find_neighbor(int[] solution){
    Random generator = new Random();
    int rand = generator.nextInt(2);
    int rand_index = generator.nextInt(NUM_INPUTS);
    int rand_index2;
    if(rand == 0){
      solution[rand_index] = -solution[rand_index];
    }
    else{
      solution[rand_index] = -solution[rand_index];
      do{
        rand_index2 = generator.nextInt(NUM_INPUTS);
      }while(rand_index2 == rand_index);
      solution[rand_index2] = -solution[rand_index2];
    }
    return solution;
  }
  public int[] generate_rand_soln(){
    Random generator = new Random();
    int rand;
    int[] solution = new int[100];
    for(int i=0; i<NUM_INPUTS; i++){
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
  public long sim_annealed(Long[] in_arr){
    int[] solution = new int[NUM_INPUTS];
    int[] neighbor = new int[NUM_INPUTS];
    Long best_residue = MAX_LONG;
    Long current_residue;
    Long new_residue;
    
    solution = generate_rand_soln();
    current_residue = calculate_residue(solution, in_arr);
    best_residue = current_residue;

    for(int iter=0; iter<MAX_ITER; iter++){
      neighbor = find_neighbor(solution);
      new_residue = calculate_residue(neighbor,in_arr);
      double probability = anneal(iter, new_residue, current_residue);
      
      if (Math.random() < probability || new_residue < current_residue){
        if (verbose)
          System.out.println("current_residue: "+new_residue);
        current_residue = new_residue;
        solution = neighbor;
        if(current_residue < best_residue){
          best_residue = current_residue;
        }
      }
    }
    return best_residue;
  }

  public double anneal(int iter, long res_a, long res_b){
    double temp = Math.pow(10,10)*Math.pow(0.8,iter/300);
    return Math.exp(-(res_a - res_b)/temp);
  }

  public long random_alg_pp(Long[] arr) {
    Long[] saved = arr;

    long best_residue = MAX_LONG;
    long current_residue;

    for (int iter = 0; iter < MAX_ITER; iter++) {
      PrePartition pp = new PrePartition(arr);
      current_residue = pp.residue();
      if (current_residue < best_residue) {
        best_residue = current_residue;
        saved = pp.get_a_prime();
        if (verbose)
          System.out.println("Best residue: " + best_residue);
      }
    }
    if (verbose)
      Karmarkar_debug(saved);
    return best_residue;
  }

  public long random_alg_pp_hill(Long[] arr){
    PrePartition pp = new PrePartition(arr);
    long best_residue = MAX_LONG;
    long current_residue;
    for (int iter = 0; iter < MAX_ITER; iter++) {
      pp.getNeighbor();
      current_residue = pp.residue();
      if (current_residue < best_residue) {
        best_residue = current_residue;
        if (verbose)
          System.out.println("Best residue: " + best_residue);
      } 
    }
    return best_residue;
  }

  public long random_pp_sim_annealed(Long[] arr){
    PrePartition pp = new PrePartition(arr);
    long best_residue = MAX_LONG;
    long current_residue;
    for (int iter = 0; iter < MAX_ITER; iter++) {
      pp.get_annealed_neighbor(iter);
      current_residue = pp.residue();
      if (current_residue < best_residue) {
        best_residue = current_residue;
        if (verbose)
          System.out.println("Best residue: " + best_residue);
      } 
    }
    return best_residue;
  }

  public static Long[] generateRandomInputs(int num) {
    Long[] inputList = new Long[num];
    Random gen = new Random();
    for (int i = 0; i < inputList.length; i++) {
      inputList[i] = FileGenerator.nextLong(gen, MAX_LONG);
    }
    return inputList;
  }

  public void Karmarkar_debug(Long[] arr) {
    KarmarkarKarp kk = new KarmarkarKarp(arr, 1);
    kk.residue();
  }

  // for debugging purposess
  public static long calculate_sum(Long[] inputList) {
    long sum = 0;
    for (int i = 0; i < inputList.length; i++) {
      sum += inputList[i];
    }
    return sum;
  }
}
