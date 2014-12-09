/* IMPORTANT: class must not be public. */


import java.io.BufferedReader;
import java.io.InputStreamReader;


class TestClass {
    public static void main(String args[] ) throws Exception {
        
   
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int N = Integer.parseInt(line);
        for (int i = 0; i < N; i++) {
        	 String text = br.readLine();
        	 int people = Integer.parseInt(text);
        	 System.out.println(people*(people+1)/2);
        }
    }
}