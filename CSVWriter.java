import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVWriter {

    private static final int NUMTRIALS = 50;
    private static final int NUM_INPUTS = 100;

    // usage: CSVWriter outputFile.csv
    public static void main(String[] args) throws IOException {
        String outputFile = args[0];
        
        FileWriter fw = new FileWriter(outputFile);
        PrintWriter pw = new PrintWriter(fw);

        NumberPartition np = new NumberPartition();

        // write the first row to file
        pw.print("KarmarkarKarp");
        pw.print(",");
        pw.print("Random");
        pw.print(",");
        pw.print("RandomPP");
        pw.print(",");
        pw.print("Random_hill");
        pw.print(",");
        pw.print("Random_hillPP");
        pw.print(",");
        pw.print("Random_anneal");
        pw.print(",");
        pw.print("Random_annealPP");
        pw.print(",");
        pw.print("KarmarkarKarp_TIME");
        pw.print(",");
        pw.print("Random_TIME");
        pw.print(",");
        pw.print("RandomPP_TIME");
        pw.print(",");
        pw.print("Random_hill_TIME");
        pw.print(",");
        pw.print("Random_hillPP_TIME");
        pw.print(",");
        pw.print("Random_anneal_TIME");
        pw.print(",");
        pw.println("Random_annealPP_TIME");

        for (int i = 0; i < NUMTRIALS; i++) {
            Long[] inputList = NumberPartition.generateRandomInputs(NUM_INPUTS);
            KarmarkarKarp kk = new KarmarkarKarp(inputList, 0);

            long start0 = System.currentTimeMillis();
            long kk_residue = kk.residue();
            long time0 = System.currentTimeMillis() - start0;
            System.out.println("KK: " + kk_residue);

            long start1 = System.currentTimeMillis();
            long random_alg = np.random_alg(inputList);
            long time1 = System.currentTimeMillis() - start1;
            System.out.println("Random: " + random_alg);

            long start2 = System.currentTimeMillis();
            long random_alg_pp = np.random_alg_pp(inputList);
            long time2 = System.currentTimeMillis() - start2;
            System.out.println("RandomPP: " + random_alg_pp);

            long start3 = System.currentTimeMillis();
            long hill_climb = np.hill_climb(inputList);
            long time3 = System.currentTimeMillis() - start3;
            System.out.println("Random_hill: " + hill_climb);

            long start4 = System.currentTimeMillis();
            long random_alg_pp_hill = np.random_alg_pp_hill(inputList);
            long time4 = System.currentTimeMillis() - start4;
            System.out.println("Random_hillPP: " + random_alg_pp_hill);

            long start5 = System.currentTimeMillis();
            long sim_annealed = np.sim_annealed(inputList);
            long time5 = System.currentTimeMillis() - start5;
            System.out.println("Random_anneal: " + sim_annealed);

            long start6 = System.currentTimeMillis();
            long random_pp_sim_annealed = np.random_pp_sim_annealed(inputList);
            long time6 = System.currentTimeMillis() - start6;
            System.out.println("Random_annealPP: " + random_pp_sim_annealed);

            // write a new row to file with residue values
            pw.print("" + kk_residue);
            pw.print(",");
            pw.print("" + random_alg);
            pw.print(",");
            pw.print("" + random_alg_pp);
            pw.print(",");
            pw.print("" + hill_climb);
            pw.print(",");
            pw.print("" + random_alg_pp_hill);
            pw.print(",");
            pw.print("" + sim_annealed);
            pw.print(",");
            pw.print("" + random_pp_sim_annealed);
            pw.print(",");
            pw.print("" + time0);
            pw.print(",");
            pw.print("" + time1);
            pw.print(",");
            pw.print("" + time2);
            pw.print(",");
            pw.print("" + time3);
            pw.print(",");
            pw.print("" + time4);
            pw.print(",");
            pw.print("" + time5);
            pw.print(",");
            pw.println("" + time6);
        }

        // flush the output to the file
        pw.flush();
        
        // close the PrintWriter
        pw.close();
        
        // close the FileWriter
        fw.close();        
    }
}
