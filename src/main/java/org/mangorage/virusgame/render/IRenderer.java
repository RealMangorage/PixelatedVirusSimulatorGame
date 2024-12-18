package org.mangorage.virusgame.render;

import java.awt.*;

public interface IRenderer<A, B, C> {
    void render(Graphics graphics, A a, B b, C c);
}
