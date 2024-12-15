package org.mangorage.render.core;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RenderStack implements IRender {

    public static RenderStack of() {
        return new RenderStack();
    }

    private final Map<String, IRender> renderMap = new HashMap<>();
    private final List<IRender> renders = new ArrayList<>();
    private final RenderStack parent;

    private RenderStack() { // ROOT
        this.parent = null;
    }

    private RenderStack(RenderStack parent) {
        this.parent = parent;
    }

    public RenderStack push(String id) {
        return (RenderStack) renderMap.computeIfAbsent(id, k -> new RenderStack(this));
    }

    public RenderStack pop() {
        return parent;
    }

    public RenderStack addRenderer(IRender render) {
        this.renders.add(render);
        return this;
    }

    @Override
    public void render(Graphics graphics) {
        renders.forEach(r -> r.render(graphics));
        renderMap.forEach((k, v) -> v.render(graphics));
    }

}
