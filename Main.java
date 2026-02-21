import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        BigInteger result1 = solve("input1.json");
        BigInteger result2 = solve("input2.json");

        System.out.println("Output for Test Case 1:");
        System.out.println(result1);

        System.out.println("Output for Test Case 2:");
        System.out.println(result2);
    }

    public static BigInteger solve(String fileName) throws Exception {

        String content = new String(Files.readAllBytes(Paths.get(fileName)));

        int k = Integer.parseInt(content.split("\"k\":")[1].split("[^0-9]")[0]);

        List<BigInteger> xList = new ArrayList<>();
        List<BigInteger> yList = new ArrayList<>();

        for (int i = 1; xList.size() < k; i++) {

            if (!content.contains("\"" + i + "\"")) continue;

            String block = content.split("\"" + i + "\"")[1];

            String baseStr = block.split("\"base\":")[1].split("\"")[1];
            String valueStr = block.split("\"value\":")[1].split("\"")[1];

            int base = Integer.parseInt(baseStr);

            xList.add(BigInteger.valueOf(i));
            yList.add(new BigInteger(valueStr, base));
        }

        BigInteger[] x = xList.toArray(new BigInteger[0]);
        BigInteger[] y = yList.toArray(new BigInteger[0]);

        return lagrangeAtZero(x, y, k);
    }

    public static BigInteger lagrangeAtZero(BigInteger[] x, BigInteger[] y, int k) {

        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {

            BigInteger term = y[i];

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    term = term.multiply(x[j].negate())
                               .divide(x[i].subtract(x[j]));
                }
            }

            result = result.add(term);
        }

        return result;
    }
}
