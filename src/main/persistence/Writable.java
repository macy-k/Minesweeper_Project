// Inspiration for the design of persistence taken from JsonSerialization Demo

package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
