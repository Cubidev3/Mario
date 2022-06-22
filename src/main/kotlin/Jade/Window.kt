package Jade

import Util.Time
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

object Window
{
    private const val width = 1920
    private const val height = 1080
    private const val title = "mario"
    var glfwWindow: Long = 0 // Window Address

    var r = 1f
    var g = 1f
    var b = 1f
    var a = 1f
    var fadeToBlack = false

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

        // Setup Gamepad Listener
        GLFW.glfwSetJoystickCallback(GamepadListener::gamepadCallback)
        GamepadListener.init()

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
        GL.createCapabilities()
    }

    private fun loop()
    {
        // Calculate delta time
        var startTime = Time.getTime()
        var endTime = Time.getTime()

        while (!GLFW.glfwWindowShouldClose(glfwWindow))
        {
            // Pool events
            GLFW.glfwPollEvents()

            // Set window color
            glClearColor(r, g, b, a)
            glClear(GL_COLOR_BUFFER_BIT)

            // Test Keyboard
            if (fadeToBlack)
            {
                r = (r - 0.01f).coerceAtLeast(0f)
                g = (g - 0.01f).coerceAtLeast(0f)
                b = (b - 0.01f).coerceAtLeast(0f)
            }
            else
            {
                r = (r + 0.01f).coerceAtMost(1f)
                g = (g + 0.01f).coerceAtMost(1f)
                b = (b + 0.01f).coerceAtMost(1f)
            }

            fadeToBlack = GamepadListener.isPressed(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_B)

            GLFW.glfwSwapBuffers(glfwWindow)

            // Calculates delta time
            endTime = Time.getTime()
            val dt = (endTime - startTime) * 1E-9
            val fps = 1/dt
            println("delta time: ${dt}s")
            println("fps: ${fps}FPS")
            startTime = endTime
        }
    }
}