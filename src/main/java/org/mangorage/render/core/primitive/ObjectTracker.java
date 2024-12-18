package org.mangorage.render.core.primitive;

import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;

public class ObjectTracker<T> {

    // Map to track object byte ID and its addition count
    private final Byte2IntMap objectCountMap = new Byte2IntOpenHashMap();

    // Map to track objects by their reference and the associated byte ID
    private final Object2ByteMap<T> objectIdMap = new Object2ByteOpenHashMap<>();

    // Map to track byte ID to object for reverse lookup
    private final Byte2ObjectMap<T> byteIdToObjectMap = new Byte2ObjectOpenHashMap<>();

    // Variable to track the next byte ID to assign
    private byte nextByteId = -128;

    // Method to add an object and return its byte ID
    public byte addObject(T object) {
        byte id;

        // Check if the object already has an ID
        if (objectIdMap.containsKey(object)) {
            id = objectIdMap.getByte(object);
            int count = objectCountMap.getOrDefault(id, 0);
            objectCountMap.put(id, count + 1);
        } else {
            // Assign a new ID to this object
            id = nextByteId++;
            objectIdMap.put(object, id);
            byteIdToObjectMap.put(id, object);  // Store the reverse mapping
            objectCountMap.put(id, 1); // Start with count 1 for the first addition
        }

        return id;
    }

    // Method to remove the object by reference, and return the byte ID if removed
    public byte removeObject(T object) {
        if (objectIdMap.containsKey(object)) {
            byte id = objectIdMap.getByte(object);
            int count = objectCountMap.get(id);

            // If count > 1, decrement the count; otherwise, remove it and return the byte ID
            if (count > 1) {
                objectCountMap.put(id, count - 1);
            } else {
                objectCountMap.remove(id);
                byteIdToObjectMap.remove(id);
                objectIdMap.remove(object);
                return id;  // Return the byte ID after it has been removed
            }
        }
        return -1;  // Return -1 if the object was not found
    }

    // Method to remove or decrement the object by byte ID
    public byte removeObjectById(byte id) {
        if (byteIdToObjectMap.containsKey(id)) {
            T object = byteIdToObjectMap.get(id);
            int count = objectCountMap.get(id);

            // If count > 1, decrement the count; otherwise, remove it and return the byte ID
            if (count > 1) {
                objectCountMap.put(id, count - 1);
            } else {
                objectCountMap.remove(id);
                byteIdToObjectMap.remove(id);
                objectIdMap.remove(object);
                return id;  // Return the byte ID after it has been removed
            }
        }
        return -1;  // Return -1 if the byte ID does not correspond to any object
    }

    // Method to get the object by its byte ID
    public T getObjectById(byte id) {
        return byteIdToObjectMap.get(id);  // Retrieve the object using the byte ID
    }

    // Optional: Method to clear all objects and reset the tracker
    public void clearAll() {
        objectCountMap.clear();
        objectIdMap.clear();
        byteIdToObjectMap.clear();
        nextByteId = 0; // Reset ID counter
    }

    public int getAmount(T id) {
        if (!objectIdMap.containsKey(id)) return -1;
        return objectCountMap.get(objectIdMap.get(id));
    }
}