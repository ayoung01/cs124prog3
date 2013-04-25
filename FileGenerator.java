import java.io.*;
import java.nio.charset.Charset;
import java.util.Random;
import java.lang.Math;

// generates n.txt with 100 random longs from 1 to 10^12
// hope this works ^_^
public class FileGenerator {
    private static final long MAX_LONG = 100000000000L;
    private static final int NUM_INPUTS = 100;
    public static void main(String[] args){
        genFile(Integer.parseInt(args[0]));
    }

    public static void genFile(int n) {
        Random gen = new Random();
        try {
            // Create file 
            FileWriter fstream = new FileWriter(n + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);

            for (int i = 0; i < NUM_INPUTS; i++) {
                long next = nextLong(gen, MAX_LONG);
                String s = "" + next;
                // String s = "" + (long)(Math.round(Math.random() * MAX_LONG));
                out.write(s + "\r\n");
            }
            //Close the output stream
            out.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static long nextLong(Random rng, long n) {
       // error checking and 2^x checking removed for simplicity.
       long bits, val;
       do {
          bits = (rng.nextLong() << 1) >>> 1;
          val = bits % n;
       } while (bits-val+(n-1) < 0L);
       return val;
    }
}