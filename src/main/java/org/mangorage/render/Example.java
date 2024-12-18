package org.mangorage.render;

import org.mangorage.render.core.primitive.ObjectTracker;

public class Example {
    public static void main(String[] args) {
        ObjectTracker<String> check = new ObjectTracker<>();

        System.out.println(
                check.addObject("Object A")
        );

        System.out.println(
                check.addObject("Object B")
        );

        System.out.println(
                check.addObject("Object C")
        );

        check.removeObject("Object C");

        check.removeObject("Object A");

        System.out.println(
                check.addObject("Object A")
        );
    }
}
