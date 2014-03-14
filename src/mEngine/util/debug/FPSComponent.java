package mEngine.util.debug;

import mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import mEngine.util.debug.profiler.Profiler;
import org.lwjgl.util.vector.Vector2f;

public class FPSComponent extends GUIGraph {


    public FPSComponent(Vector2f size, double[] values) {

        super(size, values);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getFPSGraph((int) size.x);
        super.onUpdate();

    }
}
