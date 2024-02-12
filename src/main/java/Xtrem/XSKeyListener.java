package Xtrem;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class XSKeyListener {
    private static XSKeyListener instance;
    private boolean keyPressed[] = new boolean[350];//amout of key binding glfw have 350

    private XSKeyListener() {

    }

    public static XSKeyListener get() {
        if (XSKeyListener.instance == null) {
            XSKeyListener.instance = new XSKeyListener();
        }

        return XSKeyListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}
