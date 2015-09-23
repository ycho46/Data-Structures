import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class test {
	
	public static void main(String[] args) {
		CuckooMap<MyInteger, MyString> map = new CuckooMap<>(10);
	List<MyInteger> intList = new ArrayList<MyInteger>();
	List<MyString> stringList = new ArrayList<MyString>();
	for (int i = 0; i < 100 ; ++i) {
		intList.add(new MyInteger(i));
		stringList.add(new MyString("A" + i));
	}
	
	
		for (int i = 0; i <17 ; ++i) {
			map.put(intList.get(i), stringList.get(i));
			//System.out.println(map.get(intList.get(i)));
			System.out.println(map.keySet());
			//map.dumpTable();
			System.out.println(map.size());
		}
		
		MyString rep = new MyString("New Value");
		System.out.println(map.put(intList.get(10), rep));
		System.out.println("");System.out.println("");System.out.println("");System.out.println("");
		map.dumpTable();
		System.out.println(map.get(intList.get(10)));
		//map.dumpTable();
//		System.out.println(stringList.get(5));
//		System.out.println(map.get(intList.get(5)));
////		map.dumpTable();
//		
//	}
//	
//	HashMap<MyInteger, MyString> set = new HashMap<>();
//	for (int i = 0; i < 7 ; ++i) {
//		set.put(intList.get(i), stringList.get(i));
//	}
//	map.putAll(set);
//	map.dumpTable();
//	System.out.println(map.keySet());
//	assertTrue(map.containsKey(intList.get(3)));
//	assertTrue(map.containsValue(stringList.get(4)));
//	
	}
	
}
