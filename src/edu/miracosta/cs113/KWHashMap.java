package edu.miracosta.cs113;

public interface KWHashMap<K, V> {

    /**
     * Returns the value associated with the specified key.
     * @param key input object value to access.
     * @return the specified object value, null if the key is not present
     */
    V get(Object key) ;

    /**
     * Checks if table is empty.
     * @return true if the table contains no ket-value mappings, false otherwise
     */
    boolean isEmpty() ;

    /**
     * Associates the specified value with the specified key.
     * @param key specified key object to associate with value
     * @param value specified value object to associate with a key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key
     */
    V put(K key, V value) ;

    /**
     * Removes the mapping for key from table if iti is present (optional operation)>
     * @param key to remove mapping
     * @return the previous value associated eiht the specified key, or null if there was no mapping
     */
    V remove(Object key) ;

    /**
     * Determines size of table (whole number value).
     * @return size of the table
     */
    int size() ;

}
