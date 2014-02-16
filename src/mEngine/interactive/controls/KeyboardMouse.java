package mEngine.interactive.controls;

import mEngine.core.ObjectController;
import mEngine.interactive.components.Component;
import mEngine.interactive.components.ControlComponent;
import mEngine.interactive.components.MovementComponent;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.Input;
import mEngine.util.KeyAlreadyAssignedException;
import mEngine.util.PreferenceHelper;
import mEngine.util.componentHelper.ComponentHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static mEngine.util.Input.getKey;

public class KeyboardMouse extends Controller {

    public KeyboardMouse() {

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

        try {

            Input.assignKey("forward", Keyboard.KEY_W);
            Input.assignKey("backward", Keyboard.KEY_S);
            Input.assignKey("right", Keyboard.KEY_D);
            Input.assignKey("left", Keyboard.KEY_A);

            Input.assignKey("up", Keyboard.KEY_E);
            Input.assignKey("down", Keyboard.KEY_Q);

            Input.assignKey("jump", Keyboard.KEY_SPACE);
            Input.assignKey("sprint", Keyboard.KEY_LSHIFT);
            Input.assignKey("sneak", Keyboard.KEY_C);

        } catch (KeyAlreadyAssignedException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public void updateObject(GameObject obj) {

        MovementComponent movementComponent = ComponentHelper.components.get(ObjectController.gameObjects.indexOf(obj)).movementComponent;
        ControlComponent controlComponent = ComponentHelper.components.get(ObjectController.gameObjects.indexOf(obj)).controlComponent;

        if(movementComponent != null) {

            //Calculating the rotation
            float pitch = obj.rotation.x;
            float yaw = obj.rotation.y;

            final float MAX_UP_ANGLE = 90;
            final float MAX_DOWN_ANGLE = -90;

            float deltaMouseX = Mouse.getDX() * rotationSpeed;
            float deltaMouseY = Mouse.getDY() * rotationSpeed;

            if (yaw + deltaMouseX >= 360) {

                yaw = yaw + deltaMouseX - 360;

            } else if (yaw + deltaMouseX < 0) {

                yaw = 360 - yaw + deltaMouseX;

            } else {

                yaw += deltaMouseX;

            }

            if (pitch - deltaMouseY >= MAX_DOWN_ANGLE && pitch - deltaMouseY <= MAX_UP_ANGLE) {

                pitch += -deltaMouseY;

            } else if (pitch - deltaMouseY < MAX_DOWN_ANGLE) {

                pitch = MAX_DOWN_ANGLE;

            } else if (pitch - deltaMouseY > MAX_UP_ANGLE) {

                pitch = MAX_UP_ANGLE;

            }

            movementComponent.rotate(pitch, yaw, obj);

            if(sprintModeToggle) { if(Input.isKeyDown(getKey("sprint"))) movementComponent.sprint(obj); }
            else { if(Input.isKeyPressed(getKey("sprint"))) movementComponent.sprint(obj); }

            if(sneakModeToggle) { if(Input.isKeyDown(getKey("sneak"))) movementComponent.sneak(obj); }
            else { if(Input.isKeyPressed(getKey("sneak"))) movementComponent.sneak(obj); }

            if(Input.isKeyPressed(getKey("forward"))) movementComponent.moveForward(obj);
            if(Input.isKeyPressed(getKey("backward"))) movementComponent.moveBackward(obj);
            if(Input.isKeyPressed(getKey("right"))) movementComponent.moveLeft(obj);
            if(Input.isKeyPressed(getKey("left"))) movementComponent.moveRight(obj);

            if(Input.isKeyPressed(getKey("up")) && controlComponent.capableOfFlying) movementComponent.moveUp(obj);
            if(Input.isKeyPressed(getKey("down")) && controlComponent.capableOfFlying) movementComponent.moveDown(obj);

            if(continuouslyJumping) { if(Input.isKeyPressed(getKey("jump"))) movementComponent.jump(obj); }
            else { if(Input.isKeyDown(getKey("jump"))) movementComponent.jump(obj); }

        }

    }

}
