package de.unipotsdam.cs.toolup.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.testng.AssertJUnit.fail;

public class JSONUtil {

    /**
     * If actualJson does not contain the equal content to expectedJson, this method calls testng's fail().
     * The order of elements is not relevant.
     */
    public static void assertEqualJSONContent(JSONObject expectedJson,
                                              JSONObject actualJson) throws JSONException {
        if (!equalJSONContent(expectedJson, actualJson)) {
            fail("Expected: \n" + expectedJson.toString(4) + "\n\n But was: " + actualJson.toString(4));
        }
    }

    public static boolean equalJSONContent(JSONObject firstJson,
                                           JSONObject secondJson) throws JSONException {
        return containsJSONContent(firstJson, secondJson) && containsJSONContent(secondJson, firstJson);
    }

    /**
     * Returns true, only if the first JSONObject contains all the content of the second JSONObject.
     */
    public static boolean containsJSONContent(JSONObject firstJson,
                                              JSONObject secondJson) throws JSONException {
        boolean equal = true;

        String[] keys = JSONObject.getNames(secondJson);
        for (String key : keys) {
            Object value = secondJson.get(key);
            if (!thisJSONhasEqualProperty(firstJson, key, value)) return false;
        }
        return equal;
    }

    private static boolean thisJSONhasEqualProperty(JSONObject firstJson,
                                                    String key, Object value) throws JSONException {
        Object compareValue = firstJson.opt(key);

        return equalJsonValues(value, compareValue);
    }

    private static boolean equalJsonValues(Object value, Object compareValue)
            throws JSONException {
        if (compareValue instanceof JSONObject) {
            return containsJSONContent((JSONObject) compareValue, (JSONObject) value);
        } else if (compareValue instanceof JSONArray) {
            return containsJSONContent((JSONArray) compareValue, (JSONArray) value);
        } else {
            return value.equals(compareValue);
        }
    }

    private static boolean containsJSONContent(JSONArray firstJson,
                                               JSONArray secondJson) throws JSONException {
        for (int i = 0; i < secondJson.length(); i++) {
            Object value = secondJson.get(i);
            if (arrayDoesNotContainValue(firstJson, value)) {
                return false;
            }
        }
        return true;
    }

    private static boolean arrayDoesNotContainValue(JSONArray firstJson,
                                                    Object value) throws JSONException {
        for (int i = 0; i < firstJson.length(); i++) {
            Object compareValue = firstJson.get(i);
            if (equalJsonValues(value, compareValue)) {
                return false;
            }
        }
        return true;
    }

}
