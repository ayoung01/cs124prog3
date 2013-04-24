import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class PrePartition {
  private Random generator;
	private int[] p;
	private Long[] a_prime;

	public PrePartition(Long[] arr) {
    generator = new Random(System.currentTimeMillis());
		p = gen_random_p(arr.length);
		a_prime = prePartition(arr);
	}

	// generates a random P array of length len
  public int[] gen_random_p(int len) {
    int[] p = new int[len];
    for (int i = 0; i < len; i++) {
      // generates a random int from 0 to 100 inclusive
      p[i] = generator.nextInt(len);
    }
    return p;
  }

  // given A, return A'
  public Long[] prePartition(Long[] a) {
    int len = a.length;
    // initialize a_prime to all zeroes
    Long[] a_prime = new Long[len];
    for (int i = 0; i < a_prime.length; i++) {
      a_prime[i] = new Long(0);
    }
    // for (int i = 0; i < a_prime.length; i++) {
    //   System.out.println(a_prime[i].longValue());
    // }
    // System.out.println(a_prime.length);

    for (int i = 0; i < len; i++) {
      a_prime[p[i]] += a[i];
    }
    return a_prime;
  }

  public long residue() {
  	KarmarkarKarp kk = new KarmarkarKarp(a_prime);
  	return kk.residue();
  }
}
