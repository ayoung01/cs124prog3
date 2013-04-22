import java.io.*;
import java.nio.charset.Charset;

// generates n.txt with 100 random longs from 1 to 10^12
// hope this works ^_^
public class FileGenerator {
    private static final long MAX_LONG = 100000000000L;
    public static void main(String[] args){
        genFile(Integer.parseInt(args[0]));
    }

    public static void genFile(int n) {
        try {
            // Create file 
            FileWriter fstream = new FileWriter(n + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);
            for (int i = 0; i < 100; i++) {
                String s = "" + (long)(Math.round(Math.random() * MAX_LONG));
                out.write(s + "\r\n");
            }
            //Close the output stream
            out.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}