package persistence;

import org.json.JSONObject;

// Interface for JSON persistence based off of JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
