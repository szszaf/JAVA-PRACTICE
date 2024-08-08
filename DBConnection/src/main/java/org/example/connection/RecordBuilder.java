package main.java.org.example.connection;

import java.util.HashMap;
import java.util.Map;

public class RecordBuilder {
    private Map<String, Object> fields;
    private boolean isBuild;

    public RecordBuilder() {
        this.fields = new HashMap<String, Object>();
        this.isBuild = false;
    }

    public String addValue(String fieldName, Object value) {
        fields.put(fieldName, value);
        return fieldName;
    }

    public void build() {
        this.isBuild = true;
    }

    public boolean clear() {
        fields.clear();
        this.isBuild = false;
        return true;
    }

    public Map<String, Object> getFields() {
        if (this.isBuild) return this.fields;
        return null;
    }

    public boolean isBuild() {
        return this.isBuild;
    }
}
