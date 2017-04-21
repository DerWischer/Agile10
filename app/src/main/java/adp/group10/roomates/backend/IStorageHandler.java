package adp.group10.roomates.backend;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Created by Joshua Jungen on 20.04.2017.
 */

public interface IStorageHandler {

    /**
     * Writes a new object or updates an existing one at the specified key
     *
     * @param node  Node in the database
     * @param key   Key to store the object
     * @param value Object to store. Must be {@link java.io.Serializable}
     * @see DatabaseReference#setValue(Object)
     */
    public void setValue(String node, String key, Object value);

    /**
     * Writes an object at a automatically generated key
     *
     * @param node  Node in the database
     * @param value Object to store. Must be {@link java.io.Serializable}
     * @return Key of the stored object
     * @see DatabaseReference#push()
     */
    public String pushValue(String node, Object value);

    /**
     * Reads a value
     *
     * @param node         Node in the database
     * @param key          Key of the value
     * @param classOfValue Class of the value. (e.g. String.class, User.class, Group.class)
     * @return Object
     * @see DatabaseReference#addListenerForSingleValueEvent(ValueEventListener)
     */
    public <T> T getValue(String node, String key, Class<T> classOfValue);

    /**
     * Removes a value
     *
     * @param node Node in the database
     * @param key  Key of the value
     * @see DatabaseReference#removeValue()
     */
    public void removeValue(String node, String key);

}
