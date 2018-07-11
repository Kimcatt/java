package example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * CopyOnWrite并发容器用于读多写少的并发场景。比如白名单，黑名单。
 *
 * @param <K>
 * @param <V>
 */
public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {

	private volatile Map<K, V> internalMap;
	
	public CopyOnWriteMap() {
		internalMap = new HashMap<K, V>();
	}

	@Override
	public int size() {
		return internalMap.size();
	}

	@Override
	public boolean isEmpty() {
		return internalMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return internalMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return internalMap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return internalMap.get(key);
	}

	@Override
	public V put(K key, V value) {
		synchronized(this) {
			HashMap<K, V> map = new HashMap<>(internalMap);
			V val = map.put(key, value);
			setMap(map);
			return val;
		}
	}

	@Override
	public V remove(Object key) {
		synchronized(this) {
			HashMap<K, V> map = new HashMap<>(internalMap);
			V val = map.remove(key);
			this.setMap(map);
			return val;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		synchronized (this) {
			HashMap<K, V> map = new HashMap<K, V>(internalMap);
			map.putAll(map);
			setMap(map);
		}
	}

	@Override
	public void clear() {
		synchronized (this) {
			HashMap<K, V> map = new HashMap<K, V>();
			setMap(map);
		}
	}

	@Override
	public Set<K> keySet() {
		return internalMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return internalMap.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return internalMap.entrySet();
	}
	
	private void setMap(Map<K, V> map) {
		this.internalMap = map;
	}

}
