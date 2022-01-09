public class SymbolTable {
	private static final int INIT_CAPACITY = 7;

	/* Number of key-value pairs in the symbol table */
	private int N;
	/* Size of linear probing table */
	private int M;
	/* The keys */
	private String[] keys;
	/* The values */
	private Character[] vals;

	public SymbolTable() {
		this(INIT_CAPACITY);
	}

	public SymbolTable(int capacity) {
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
	}

	public int size() {
		return N;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean contains(String key) {
		return get(key) != null;
	}

	public int hash(String key) {
		int i;
		int v = 0;

		for (i = 0; i < key.length(); i++) {
			v += key.charAt(i);
		}
		return v % M;
	}

	//---------------------------------------------------------------------
	//---------------------------------------------------------------------


	public void put(String key, Character val) {
		int pos = hash(key);
		if (key == null) {
			return;
		}else if (val == null) {
			delete(key);
			return;
		}

		for (int i = 0; i < M; i++) { 				//Fixat komplettering "Er put anropar contains som itererar över listan, för att sedan själv iterera över listan. 
			//Det blir också onödigt arbete, speciellt när get i många fall går igenom precis hela listan.
			if (keys[(pos + i) % M] != null) {
				if(keys[(pos + i) % M].equals(key)) {
					vals[(pos + i) % M] = val;
					return;			
				}
			} else {
				keys[(pos + i) % M] = key;
				vals[(pos + i) % M] = val;
				N += 1;
				return;
			}
		}

	}




	public Character get(String key) {
		int h_pos = hash(key);
		for (int i = 0; i < M; i++) {
			int pos = (h_pos + i) % M;
			if (keys[pos] != null) {
				if(keys[pos].equals(key)) {
					return vals[pos];				
				}
			} else {
				return null; //Fixat komplettering "Ifall get anropas med en nyckel som inte finns i tabellen så går den igenom hela listan. Det är onödigt."
			}
		}
		return null;
	}



	public void delete(String key) {
		int pos = hash(key);
		int ref = 0;
		for (int i = 0; i < M; i++) {
			if (keys[((pos + i) % M)] != null) {
				if(keys[(pos + i) % M].equals(key)) {
					keys[(pos + i) % M] = null;
					vals[(pos + i) % M] = null;
					ref = (pos + i) % M;
					N -= 1;
					break;			
				} else if (i == (M - 1)){
					return;
				}
			} else {
				return; //Fixat komplettering: "Samma sak (Ifall get anropas med en nyckel som inte finns i tabellen så går den igenom hela listan. Det är onödigt.) gäller delete med en icke-existerande nyckel."
			}
		}

		int tmp = ref;
		int i = 1;
		while ((keys[(tmp + i) % M] != null) && (i < M)) {

			if (hash(keys[(tmp + i) % M]) != (tmp + i) % M) {
				keys[ref] = keys[(tmp + i) % M];
				vals[ref] = vals[(tmp + i) % M];
				ref = (tmp + i) % M;
				keys[ref] = null;
				vals[ref] = null;
			}
			i += 1;
		}
	}


	//---------------------------------------------------------------------------------------------
	public void dump() {
		String str = "";

		for (int i = 0; i < M; i++) {
			str = str + i + ". " + vals[i];
			if (keys[i] != null) {
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			} else {
				str = str + " -";
			}
			System.out.println(str);
			str = "";
		}
	}
}