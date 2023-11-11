// Inspiration for the design of persistence taken from JsonSerialization Demo

package persistence;

import org.json.JSONObject;

// interface for classes that can be written to a json file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
