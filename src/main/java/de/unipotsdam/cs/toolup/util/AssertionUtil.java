package de.unipotsdam.cs.toolup.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

public class AssertionUtil {

    public static <T> void assertContainsAll(Collection<T> expectedCollection, Collection<? extends T> actualCollection) {
        if (expectedCollection == null && actualCollection == null) {
            return;
        }

        if (expectedCollection == null ^ actualCollection == null) {
            throw new AssertionError("Expected: " + expectedCollection + "\n but was: " + actualCollection);
        }

        if (!expectedCollection.containsAll(actualCollection)) {
            String expected = ToStringBuilder.reflectionToString(expectedCollection);
            String actual = ToStringBuilder.reflectionToString(actualCollection);
            throw new AssertionError("Expected: " + expected + "\n but was: " + actual);
        }
    }

    public static <T> void assertCollectionEquals(Collection<T> expectedCollection, Collection<T> actualCollection) {
        if (expectedCollection == null && actualCollection == null) {
            return;
        }

        if (expectedCollection == null ^ actualCollection == null) {
            throw new AssertionError("Expected: " + expectedCollection + "\n but was: " + actualCollection);
        }

        if (expectedCollection.size() != actualCollection.size()) {
            throw new AssertionError("Expected size " + expectedCollection.size() + "\n but was: " + actualCollection.size());
        }
        assertContainsAll(expectedCollection, actualCollection);
        assertContainsAll(actualCollection, expectedCollection);
    }
}
