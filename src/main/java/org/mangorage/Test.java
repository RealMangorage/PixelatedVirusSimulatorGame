package org.mangorage;

import org.mangorage.render.core.primitive.ObjectByteBackedTracker;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        ObjectByteBackedTracker<UUID> tracker = new ObjectByteBackedTracker<>();
        Set<UUID> list = new HashSet<>();

        for (int i = 0; i < 256; i++) {
            list.add(UUID.randomUUID());
        }

        list.forEach(id -> {
            try {
                tracker.addObject(id);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });

        list.forEach(a -> {
            tracker.removeObject(a);
        });
        var a = 1;
    }
}
