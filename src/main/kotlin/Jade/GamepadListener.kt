package Jade

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWGamepadState

object GamepadListener
{
    private val gamepadsConnected = BooleanArray(GLFW.GLFW_JOYSTICK_LAST + 1)

    fun init()
    {
        for (joystick in (GLFW.GLFW_JOYSTICK_1..GLFW.GLFW_JOYSTICK_LAST))
        {
            gamepadsConnected[joystick] = GLFW.glfwJoystickIsGamepad(joystick)
        }
    }
    fun gamepadCallback(gamepad: Int, action: Int)
    {
        if (gamepad >= gamepadsConnected.size || gamepad < 0) return

        if (action == GLFW.GLFW_CONNECTED)
        {
            if (!GLFW.glfwJoystickIsGamepad(gamepad)) return
            gamepadsConnected[gamepad] = true
        }
        else if (action == GLFW.GLFW_DISCONNECTED)
        {
            gamepadsConnected[gamepad] = false
        }
    }

    fun isGamepadConnected(gamepad: Int) : Boolean
    {
        if (gamepad >= gamepadsConnected.size || gamepad < 0) return false
        return gamepadsConnected[gamepad]
    }

    fun isPressed(gamepad: Int, button: Int) : Boolean
    {
        if (!isGamepadConnected(gamepad)) return false

        val state = GLFWGamepadState.create()

        if (GLFW.glfwGetGamepadState(gamepad, state))
        {
            if (button >= state.buttons().limit() || button < 0) return false // button value is out of bounds
            return state.buttons()[button] == 1.toByte() // 1 -> Pressed, 0 -> Not Pressed
        }

        return false
    }

    fun getAxis(gamepad: Int, axis: Int) : Float
    {
        if (!isGamepadConnected(gamepad)) return 0f

        val state = GLFWGamepadState.create()

        if (GLFW.glfwGetGamepadState(gamepad, state))
        {
            if (axis >= state.axes().limit() || axis < 0) return 0f
            return state.axes(axis)
        }

        return 0f
    }
}