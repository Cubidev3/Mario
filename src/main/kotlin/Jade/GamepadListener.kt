package Jade

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWGamepadState

class GamepadListener
{
    private val gamepadsConnected = BooleanArray(GLFW.GLFW_JOYSTICK_LAST + 1)

    private fun getGamepadsAlreadyIn()
    {
        for (gamepad in (0..GLFW.GLFW_JOYSTICK_LAST))
        {
            gamepadsConnected[gamepad] = GLFW.glfwJoystickIsGamepad(gamepad)
        }
    }

    companion object
    {
        private var gamepadListener: GamepadListener? = null

        fun get() : GamepadListener
        {
            if (gamepadListener == null)
            {
                gamepadListener = GamepadListener()
                gamepadListener!!.getGamepadsAlreadyIn()
            }

            return gamepadListener as GamepadListener
        }

        fun gamepadCallback(gamepad: Int, action: Int)
        {
            if (gamepad >= get().gamepadsConnected.size || gamepad < 0) return

            if (action == GLFW.GLFW_CONNECTED)
            {
                if (!GLFW.glfwJoystickIsGamepad(gamepad)) return
                get().gamepadsConnected[gamepad] = true
            }
            else if (action == GLFW.GLFW_DISCONNECTED)
            {
                get().gamepadsConnected[gamepad] = false
            }
        }

        fun isGamepadConnected(gamepad: Int) : Boolean
        {
            if (gamepad >= get().gamepadsConnected.size || gamepad < 0) return false
            return get().gamepadsConnected[gamepad]
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
}