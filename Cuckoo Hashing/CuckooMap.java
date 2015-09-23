import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class CuckooMap<K extends Hashable, V> implements Map<K, V> {
	final double MAX_LOADFACTOR = 0.8;
	
	private class Bucket<K extends Comparable, V> implements Map.Entry<K, V>, Comparable {
		
		K key;
		V value;
		boolean deleted = false;
		
		public Bucket(K k, V v) {
			key = k;
			value= v;
		}
		
		public String toString() {
			return "Key : " + key + " Value: " + value;
		}
	
		public int compareTo(Object o) {
			Bucket a = (Bucket)o;
			return key.compareTo(a.key);
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}
		
	}
	
	private Bucket<K, V> [] t1, t2;
	int count1 = 0;
	int count2 = 0;
	Random rand = new Random();
	int a = rand.nextInt(999)+1;
	int b = rand.nextInt(999)+1;
	
	public CuckooMap(int capacity) {
		t1 = new Bucket[capacity];
		t2 = new Bucket[capacity];
	}

	@Override
	public int size() {
		return count1 + count2;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		K k = (K) key;
		for(int i = 0; i < t1.length; i++) {
			if(t1[i] != null) {
				if(t1[i].getKey().compareTo(k) == 0) {
					return true;
				}
			}
			if (t2[i] != null) {
				if(t2[i].getKey().compareTo(k) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		V v = (V) value;
		for(int i = 0; i < t1.length; i++) {
			if(t1[i] != null) {
				if(t1[i].value.equals(v)) {
					return true;
				}
			}
			if(t2[i] != null) {
				if(t2[i].value.equals(v)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public V get(Object key) {
		K k = (K) key;
		int index = hash1(k);
		int index2 = hash2(k);
		V v = null;
		if(containsKey(k)) {
			if(t1[index] != null) {
				if(t1[index].key.equals(k)) {
					v = t1[index].value;
					return v;
				}
			}
			if(t2[index] != null) {
				if (t2[index2].key.equals(k)){
					v = t2[index2].value;
					return v;			
				}
			}
		}
		return v;
	}
	
	private int hash1(K key) {
		return (key.hashCode() * a) % t1.length;
	}
	
	private int hash2(K key) {
		return (key.hashCode() * b) % t2.length;
	}

	@Override
	public V put(K key, V value) {
		int index = hash1(key);
		int index2 = hash2(key);
		int kickCount = 0;
		V v = null;
		Bucket<K,V> kickedBucket = null;
		Bucket<K,V> newBucket = new Bucket<>(key, value);
		boolean push = true;

		if(containsKey(key)) {
			if (t1[index] != null) {
				if(t1[index].compareTo(newBucket) == 0) {
					v = t1[index].value;
					t1[index].value = value;
					return v;
				}
			}
			if (t2[index2] != null) {
				if(t2[index2].compareTo(newBucket) == 0) {
					v = t2[index2].value;
					t2[index2].value = value;
					return v;
				}
			}
		}
		while(push) {
			if(t1[index] != null) {
				kickedBucket = t1[index];
				t1[index] = newBucket;
				kickCount++;
				if(t2[index2] != null){	
						newBucket = t2[index2];
						t2[index2] = kickedBucket;
						kickCount++;
				} else {
					t2[index2] = kickedBucket;
					kickCount = 0;
					count2++;
					push = false;
				}
			} else {
				t1[index] = newBucket;
				kickCount = 0;
				count1++;
				push = false;
			}
			if(kickCount > t1.length || loadfactor() > MAX_LOADFACTOR) {
				regrow();
			}
		}
		return null;
	}

	@Override
	public V remove(Object key) {
		K k = (K)key;
		int index = hash1(k);
		int index2 = hash2(k);
		V v = null;
		if(key == null) {
			return v;
		}
		if(t1[index].key.equals(k)) {
			v = t1[index].value;
			t1[index] = null;
			count1--;
			return v;
		} else if (t2[index2].key.equals(k)){
			v = t2[index2].value;
			t2[index2] = null;
			count2--;
			return v;			
		}
		return null;
	}
	
	private void regrow() {
		count1 = 0;
		count2 = 0;
		a = rand.nextInt(999) +1;
		b = rand.nextInt(999) +1;
		Bucket<K, V> [] old = t1;
		Bucket<K, V> [] old2 = t2;
		t1 = new Bucket[old.length * 2];
		t2 = new Bucket[old2.length * 2];
		for(int i = 0; i < old.length; i++) {
			if(old[i] != null) {
				put(old[i].key, old[i].value);
			}
		}
		for(int i = 0; i <old2.length; i++){
			if(old2[i] != null) {
				put(old2[i].key, old2[i].value);
			}
		}
	}


	@Override
	public void clear() {
		t1 = null;
		t2 = null;
		count1 = 0;
		count2 = 0;
	}
	
	private double loadfactor() {
		return Math.max(((double) count1 / t1.length), ((double) count2 / t2.length));
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for(java.util.Map.Entry<? extends K, ? extends V> a: m.entrySet()) {
			put(a.getKey(), a.getValue());
		}
	}	
	
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> a = new TreeSet<>();
		for(int i = 0; i < t1.length; i++) {
			if(t1[i]!= null) {
				a.add(t1[i]);
			}
			if(t2[i] != null) {
				a.add(t2[i]);
			}
		}
		return a;
	}

	@Override
	public Set<K> keySet() {
		Set<K> a = new TreeSet<>();
		for(int i = 0; i <t1.length; i++) {
			if(t1[i] != null) {
				a.add(t1[i].key);
			}
			if(t2[i] != null) {
				a.add(t2[i].key);
			}
		}
		return a;
	}

	@Override
	public Collection<V> values() {
		Collection<V> a = new ArrayList<>();
		for(int i = 0; i <t1.length; i++) {
			if(t1[i] != null) {
				a.add(t1[i].value);
			}
			if(t2[i] != null) {
				a.add(t2[i].value);
			}
		}
		return a;
	}


	
	
	public void dumpTable() {
		System.out.println("Dumping the table1 size: " + size() + " lf: " + loadfactor());
		for (int i =0; i < t1.length ; ++i) {
			System.out.println("Row: " + i+ " t1 : " + t1[i] + "		 t2: " + t2[i]);
			
		}
	}
	

}
