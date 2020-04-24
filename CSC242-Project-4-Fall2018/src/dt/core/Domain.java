package dt.core;

import dt.util.ArraySet;

/**
 * A Domain (of a Variable) is a Set of possible values (Strings).
 */
public class Domain extends ArraySet<String> {
	
	public Domain() {
		super();
	}
	
	public Domain(String... values) {
		this();
		for (String s : values) {
			add(s);
		}
	}
	
}
