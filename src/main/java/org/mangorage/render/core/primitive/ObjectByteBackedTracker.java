package org.mangorage.render.core.primitive;

import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;

import java.util.Stack;

public class ObjectByteBackedTracker<T> {

    private final Byte2IntMap objectCountMap = new Byte2IntOpenHashMap();
    private final Object2ByteMap<T> objectIdMap = new Object2ByteOpenHashMap<>();
    private final Byte2ObjectMap<T> byteIdToObjectMap = new Byte2ObjectOpenHashMap<>();
    private final Stack<Byte> freedByteIds = new Stack<>();

    private boolean added = false;
    private byte nextByteId = -128;

    public byte addObject(T object) {
        byte id;

        // Check if the object already has an ID
        if (objectIdMap.containsKey(object)) {
            id = objectIdMap.getByte(object);
            int count = objectCountMap.getOrDefault(id, 0);
            objectCountMap.put(id, count + 1);
        } else {
            // If no freed byte IDs are available and we have exhausted the ID space, throw an exception
            if (freedByteIds.isEmpty() && nextByteId == -128 && added) {
                throw new IllegalStateException("No more byte IDs available");
            }

            // Reuse a freed byte ID if available, otherwise allocate a new ID
            if (!freedByteIds.isEmpty()) {
                id = freedByteIds.pop(); // Reuse a freed byte ID
            } else {
                id = nextByteId++; // Allocate a new byte ID
            }

            objectIdMap.put(object, id);
            byteIdToObjectMap.put(id, object);
            objectCountMap.put(id, 1);
        }


        this.added = true;

        return id;
    }

    public byte removeObject(T object) {
        if (objectIdMap.containsKey(object)) {
            byte id = objectIdMap.getByte(object);
            int count = objectCountMap.get(id);

            if (count > 1) {
                objectCountMap.put(id, count - 1);
            } else {
                objectCountMap.remove(id);
                byteIdToObjectMap.remove(id);
                objectIdMap.remove(object);
                freedByteIds.push(id); // Free the byte ID for reuse
                return id;
            }
        }
        return -1;
    }

    public byte removeObjectById(byte id) {
        if (byteIdToObjectMap.containsKey(id)) {
            T object = byteIdToObjectMap.get(id);
            int count = objectCountMap.get(id);

            if (count > 1) {
                objectCountMap.put(id, count - 1);
            } else {
                objectCountMap.remove(id);
                byteIdToObjectMap.remove(id);
                objectIdMap.remove(object);
                freedByteIds.push(id); // Free the byte ID for reuse
                return id;
            }
        }
        return -1;
    }

    public T getObjectById(byte id) {
        return byteIdToObjectMap.get(id);
    }

    public void clearAll() {
        objectCountMap.clear();
        objectIdMap.clear();
        byteIdToObjectMap.clear();
        freedByteIds.clear(); // Clear freed byte IDs
        nextByteId = 0; // Reset ID counter
    }

    public int getAmount(T id) {
        if (!objectIdMap.containsKey(id)) return -1;
        return objectCountMap.get(objectIdMap.get(id));
    }
}