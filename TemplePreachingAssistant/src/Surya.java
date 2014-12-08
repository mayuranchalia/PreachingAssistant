import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Surya {

	String test = "surya";

	public Surya(String test) {
		this.test = test;
	}

	@Override
	public int hashCode() {

		return test.length();
	}

	@Override
	public String toString() {
		return this.test;
	}

	@Override
	public boolean equals(Object o) {

		if (o != null && o instanceof Surya) {
			return this.toString() == o.toString();

		} else {
			return false;
		}

	}

	public static void main(String[] args) {
		Map<Surya,Surya> set = new HashMap<>();
		Surya t1 = new Surya("surya");
		Surya t2 = new Surya("mayur");
		Surya t3 = new Surya("surya");

		set.put(t1,t2);
		set.put(t2,t2);
		set.put(t3,t3);
		System.out.println(set.size());
		System.out.println(set);
		System.out.println(set.get(t3));
	}

}
