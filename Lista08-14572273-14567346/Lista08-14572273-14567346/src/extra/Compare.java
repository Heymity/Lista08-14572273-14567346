package extra;

//Rodando esse codigo vemos que claramente o StringBuffer é consideravelmente mais rápido, aproximadamente 100 vezes
// Isso é devido ao fato do StringBuffer ser mutavel, ele não precisa copiar a string toda vez que uma concatenação é feita
public class Compare {
	public static String str;
	public static StringBuffer strBuff;
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		str = "0";
		for (int i = 0; i < 100000; i++) {
			str += "1";
		}
		long elapsedStr = System.currentTimeMillis() - start;
		
		start = System.currentTimeMillis();
		strBuff = new StringBuffer();
		for (int i = 0; i < 100000; i++) {
			strBuff.append("1");
		}
		long elapsedBuff = System.currentTimeMillis() - start;
		
		System.out.printf("String concatenation took %d ms, String buffer took %d ms\n",elapsedStr, elapsedBuff);
	}
}
