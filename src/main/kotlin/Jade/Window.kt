package Jade

import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

class Window()
{
    val width = 1920
    val height = 1080
    val title = "mario"
    var glfwWindow: Long = 0; // Window Address

    companion object
    {
        private var window: Window? = null

        fun get() : Window
        {
            if (window == null){
                window = Window()
            }

            return window as Window
        }
    }

    fun run()
    {
        println("Hello LWJGL " + Version.getVersion() + " =)")

        init()
        loop()

        // Free memory
        glfwFreeCallbacks(glfwWindow)
        GLFW.glfwDestroyWindow(glfwWindow)

        // Terminate GLFW and free error callback
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)?.free()
    }

    private fun init()
    {
        // Setup Error callback
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW
        if (!GLFW.glfwInit())
        {
            throw java.lang.IllegalStateException("Could not initialize GLFW")
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE)

        // Create window
        glfwWindow = GLFW.glfwCreateWindow(width, height, title, NULL, NULL)
        if (glfwWindow == NULL)
        {
            throw java.lang.IllegalStateException("Unable to create GLFW Window")
        }

        // Setup Mouse Listener
        GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::cursorPositionCallback)
        GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback)
        GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::scrollCallback)

        // Setup Keyboard Listener
        GLFW.glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback)

        // Make openGl context current
        GLFW.glfwMakeContextCurrent(glfwWindow)

        // Enable v-sync
        GLFW.glfwSwapInterval(1)

        // Make window visible
        GLFW.glfwShowWindow(glfwWindow)

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    private fun loop()
    {
        while (!GLFW.glfwWindowShouldClose(glfwWindow))
        {
            // Pool events
            GLFW.glfwPollEvents()

            // Set window color
            glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
            glClear(GL_COLOR_BUFFER_BIT)

            // Test Keyboard Listener
            if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_SPACE)) println("SPAAAAAACE!!")

            GLFW.glfwSwapBuffers(glfwWindow)
        }
    }
}