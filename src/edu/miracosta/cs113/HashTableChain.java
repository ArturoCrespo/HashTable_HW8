package edu.miracosta.cs113;

import java.util.*;

public class HashTableChain<K, V> implements Map<K, V> {

    //array of references to linked list also have Entries
    private LinkedList<Entry<K, V>>[] table ; // A table of references to linked lists of Entry objects

    private int numKeys ; // number of keys (entries) in the table

    private static final int CAPACITY = 101 ; // size of table

    private static final int LOAD_THRESHOLD = 3 ; // the maximum load factor

    // CONSTRUCTOR

    public HashTableChain() {
        table = new LinkedList[CAPACITY] ;
    }


    @Override
    public int size() {
        return numKeys ;
    }

    @Override
    public boolean isEmpty() {
        return numKeys == 0 ;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = key.hashCode() % table.length;

        if(index < 0) {
            index += table.length;
        }

        if(table[index] == null) {
            return false;
        }

        for(Entry<K, V> nextItem : table[index]) {
            if(nextItem.getKey().equals(key)) {
                return true ;
            }
        }

        return false ;
    }

    @Override
    public boolean containsValue(Object value) {

        for(LinkedList<Entry<K, V>> entrySet : table) {

            if(entrySet != null) {

                for(Map.Entry<K, V> nextItem : entrySet) {

                    if(value.equals(nextItem.getValue())) {
                        return true ;
                    }

                }

            }

        }

        return false;
    }

    @Override
    public V get(Object key) {
        
        int index = key.hashCode() % table.length ;
        
        if(index < 0) {
            index += table.length ;
        }
        
        if(table[index] == null) {
            return null ;
        }
        
        // search the list at table[index] to find the key
        for(Entry<K, V> nextItem : table[index]) {
            if(nextItem.key.equals(key)) {
                return nextItem.value ;
            }
        }
        
        // key is NOT in the table
        return null ;
    }

    @Override
    public V put(K key, V value) {
        
        int index = key.hashCode() % table.length ;
        
        if(index < 0) {
            index += table.length; ;
        }
        
        if(table[index] == null) {
            
            // create a new linked list at table[index]
            table[index] = new LinkedList<Entry<K, V>>() ;
        }
        
        // search the list at table[index] to FIND the key
        for(Entry<K, V> nextItem : table[index]) {
            
            // if search is SUCCESS - replace OLD value
            if(nextItem.key.equals(key)) {
                
                // replace value for this key
                V oldVal = nextItem.value ;
                nextItem.setValue(value) ;
                return oldVal ;
            }
        }
        
        // key is NOT found in table? - ADD new item
        table[index].addFirst(new Entry<K, V>(key, value)) ;
        
        // increment number of keys
        numKeys++ ;
        
        if(numKeys > (LOAD_THRESHOLD * table.length)) {
            rehash() ;
        }
        
        // none of conditions met: return NULL
        return null ;
    }

    private void rehash() {
        LinkedList<Entry<K, V>>[] oldTable = table ;
        table = new LinkedList[2 * oldTable.length + 1] ;
        numKeys = 0 ;

        for (LinkedList<Entry<K, V>> table : oldTable) {

            if (table != null) {

                for (Entry<K, V> nextItem : table) {

                    put(nextItem.getKey(), nextItem.getValue());
                }

            }
        }
    }

    @Override
    public V remove(Object key) {
        int index = key.hashCode() % table.length ;

        if(index < 0) {
            index += table.length ;
        }

        if(table[index] == null) {

            // key is not in the table
            return null ;
        }

        for(Entry<K, V> nextItem : table[index]) {

            if(nextItem.getKey().equals(key)) {

                V value = nextItem.getValue() ;
                table[index].remove(nextItem) ;

                // decrement
                numKeys-- ;

                if(table[index].isEmpty()) {
                    table[index] = null ;
                }

                return value ;

            }

        }

        // none of conditions met - return null
        return null ;
    }

    @Override
    public void clear() {

        // set every element to null
        Arrays.fill(table, null);

        // reset num of keys
        numKeys = 0 ;
    }

    @Override
    public Set<K> keySet() {

        Set<K> keys = new HashSet<K>(size());

        for(LinkedList<Entry<K, V>> entrySet : table) {

            if (entrySet != null) {

                for(Entry<K, V> nextItem : entrySet) {

                    if(nextItem != null) {

                        keys.add(nextItem.getKey());
                    }
                }
            }
        }

        return keys ;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet() ;
    }

    @Override
    public String toString() {
        StringBuilder tableString = new StringBuilder() ;

        for(LinkedList<Entry<K, V>> entrySet : table) {

            if(entrySet != null) {

                for(Entry<K, V> nextItem : entrySet) {

                    tableString.append(nextItem.toString()).append(" ");
                }

                tableString.append("\n");
            }

        }
        return tableString.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Hashtable)) {
            return false;
        }

        Hashtable h = (Hashtable) obj;

        return h.equals(this);
    }

    @Override public int hashCode() {
        int code = 0;
        Set<Map.Entry<K, V>> entrySet = entrySet();
        for (Map.Entry<K, V> nextItem : entrySet) {
             code += nextItem.hashCode();
        }
        return code ;
    }

    //STUBBED METHODS

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        //stub
    }

    @Override
    public Collection<V> values() {
        return null; //stub
    }



    // ENTRY CLASS

    private static class Entry<K, V> implements Map.Entry<K, V> {

        // DATA FIELDS

        private K key ;
        private V value ;

        // CONSTRUCTOR

        /**
         * Creates a new key-value pair.
         * @param key object to pair with value.
         * @param value object to pair with key.
         */
        public Entry(K key, V value) {
            this.key = key ;
            this.value = value ;
        }

        // METHODS

        /**
         * Retrieves the key.
         * @return object key data.
         */
        public K getKey() {
            return key ;
        }

        /**
         * Retrieves the value.
         * @return object value data.
         */
        public V getValue() {
            return value ;
        }

        /**
         * Sets the value.
         * @param newValue passed argument new value.
         * @return the old value.
         */
        public V setValue(V newValue) {
            V oldVal = this.value ;
            this.value = newValue ;
            return oldVal ;
        }

        @Override
        public String toString() {
            return key + "=" + value ;
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

    } // end of Entry inner class



    // ENTRY SET CLASS

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new SetIterator() ;
        }

        @Override
        public int size() {
            return numKeys ;
        }

    } // end of EntrySet inner class

    private class SetIterator implements Iterator<Map.Entry<K, V>> {

        private int index = 0 ;

        private Entry<K, V> lastItemReturned = null ;

        private Iterator<Entry<K, V>> iter = null ;


        @Override
        public boolean hasNext() {
            if(iter != null && iter.hasNext()) {
                return true ;
            }

            do {
                index++ ;

                if(index >= table.length) {
                    return false;
                }
            } while (table[index] == null);

            iter = table[index].iterator();
            return iter.hasNext();
        }

        @Override
        public Map.Entry<K, V> next() {
            if(hasNext()) {
                return lastItemReturned = iter.next() ;
            }
            throw new NoSuchElementException() ;
        }

        @Override
        public void remove() {
            if(lastItemReturned == null) {
                throw new NoSuchElementException() ;
            }

            iter.remove() ;
            lastItemReturned = null ;
        }

    } // end of SetIterator inner class

} // end of HashTableChain class

