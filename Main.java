import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws Exception {

        // Read JSON file
        String content = new String(Files.readAllBytes(Paths.get("input.json")));
        JSONObject obj = new JSONObject(content);

        JSONObject keys = obj.getJSONObject("keys");
        int k = keys.getInt("k");

        BigInteger[] x = new BigInteger[k];
        BigInteger[] y = new BigInteger[k];

        // IMPORTANT: Take roots from 1 to k exactly
        for (int i = 1; i <= k; i++) {

            String key = String.valueOf(i);
            JSONObject root = obj.getJSONObject(key);

            int base = Integer.parseInt(root.getString("base"));
            String value = root.getString("value");

            x[i - 1] = new BigInteger(key);
            y[i - 1] = new BigInteger(value, base);
        }

        // Lagrange interpolation at x = 0
        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {

            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    numerator = numerator.multiply(x[j].negate());
                    denominator = denominator.multiply(x[i].subtract(x[j]));
                }
            }

            // Multiply first, divide only once
            BigInteger term = y[i].multiply(numerator);

            // Ensure exact division
            term = term.divide(denominator);

            secret = secret.add(term);
        }

        System.out.println("Secret: " + secret);
    }
}
