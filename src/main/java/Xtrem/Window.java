package Xtrem;

import Util.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;



public class Window {
   private int width,height;
   private String title;

   private long glfwWindow;

    public float r, g, b, a;
    private boolean fadeToBlack = false;

   private static Window window=null;


   private static XSScene currentScene;


    private Window(){

        this.width=1920;
        this.height=1080;
        this.title="XGE";
        r = 1;
        b = 1;
        g = 1;
        a = 1;

    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new XSLevelEditorScene();
                //currentScene.init();
                break;
            case 1:
                currentScene = new XSLevelScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }
    public static Window get(){
        if(Window.window==null){
            Window.window=new Window();
        }
        return Window.window;
    }

    public void run(){
        System.out.println("Hello LWJGL"+ Version.getVersion()+"!");
        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();



    }
    public void init(){
        //Setup an error callback

        GLFWErrorCallback.createPrint(System.err).set();
        //Initialize GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Unable to Initialize GLFW.");

        }

        //Configure Glfw

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        //Create the Window

        glfwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL,NULL);

        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to create the GLFW Window");
        }

        glfwSetCursorPosCallback(glfwWindow, XSMouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, XSMouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, XSMouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, XSKeyListener::keyCallback);

        //Make the OPENGL context current

        glfwMakeContextCurrent(glfwWindow);

        //Enable v-sync

        glfwSwapInterval(1);

        //Make the show

        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        Window.changeScene(0);

    }

    public void loop(){

        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)){

            //Poll events

            glfwPollEvents();
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();

            dt = endTime - beginTime;

            beginTime = endTime;


            System.out.println(dt);


        }
    }

}
