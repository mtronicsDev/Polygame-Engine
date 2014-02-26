package mEngine.core;

import mEngine.audio.AudioController;
import mEngine.audio.AudioSource;
import mEngine.graphics.GraphicsController;
import mEngine.interactive.components.CollideComponent;
import mEngine.interactive.components.ControllerKeyboardMouse;
import mEngine.interactive.components.MovementComponent;
import mEngine.interactive.components.RenderComponent;
import mEngine.interactive.components.Camera;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gui.GUIElement;
import mEngine.interactive.gui.GUIScreen;
import mEngine.interactive.gui.GUIText;
import mEngine.interactive.gui.primitives.GUIEllipse;
import mEngine.interactive.gui.primitives.GUIQuadTextured;
import mEngine.physics.forces.ForceController;
import mEngine.util.PreferenceHelper;
import mEngine.util.ResourceHelper;
import mEngine.util.RuntimeHelper;
import mEngine.util.TimeHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static mEngine.core.ObjectController.*;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        PreferenceHelper.loadPreferences("res/preferences/mEngine.mmp");
        ResourceHelper.initialize();
        GraphicsController.createDisplay(60, "mEngine Test Run");
        AudioController.initializeOpenAL();
        TimeHelper.setupTiming();
        RuntimeHelper.initialize();

        ForceController.addForce(new Vector3f(0, -0.981f, 0)); //Gravity

        //GameObject Time ;)
        GameObject object;

        addGameObject(new GameObject(new Vector3f(), new Vector3f()));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("texturedStar")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false)
        );
        object.addComponent(
                "movementComponent",
                new MovementComponent()
        );
        object.addComponent(
                "controller",
                new ControllerKeyboardMouse(
                    new float[] {0.1f, 0.075f, 0.075f, 0.075f, 0.075f, 0.075f, 0.075f, 1.1f},
                    false
                )
        );
        object.addComponent(
                "camera",
                new Camera()
        );

        addGameObject(new GameObject(new Vector3f(0, 0, -20), new Vector3f()));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("12star")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false)
        );

        addGameObject(new GameObject(new Vector3f(0, 0, -5), new Vector3f(0, 0, 0)));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("texturedStar")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false)
        );

        addGUIScreen(new GUIScreen(
                new GUIElement[]{
                        new GUIText(new Vector2f(5, 5), "Current FPS", 15),
                        new GUIText(new Vector2f(5, 25), "Current RAM", 15),
                        new GUIText(new Vector2f(5, 50), "x: 0", 15),
                        new GUIText(new Vector2f(5, 70), "y: 0", 15),
                        new GUIText(new Vector2f(5, 90), "z: 0", 15)
                }, true));

        addGUIScreen(new GUIScreen(
                new GUIElement[]{
                        new GUIQuadTextured(new Vector2f(Display.getWidth() / 2 - 32, Display.getHeight() / 2 - 32), new Vector2f(64, 64), "reticule"),
                        new GUIEllipse(new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2), new Vector2f(100, 200))
                }, true));

        AudioController.setListener(getGameObject(0));

        for(AudioSource source : audioSources) { source.play(); }
        Mouse.setGrabbed(true);

        GameLoop.loop();

    }

    public static void pauseGame() {

        Mouse.setGrabbed(false);
        for(AudioSource source : audioSources) { source.pause(); }

        isGamePaused = true;

    }

    public static void unPauseGame() {

        Mouse.setGrabbed(true);
        for(AudioSource source : audioSources) { source.play(); }

        isGamePaused = false;

    }

    public static void stopGame() {

        AudioController.killALData();
        System.exit(0);

    }

}
