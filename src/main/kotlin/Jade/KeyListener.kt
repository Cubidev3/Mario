package Jade

import org.lwjgl.glfw.GLFW

class KeyListener
{
    private val keyPressed = BooleanArray(GLFW.GLFW_KEY_LAST + 1)

    companion object
    {
        private var keyListener: KeyListener? = null

        fun get() : KeyListener
        {
            if (keyListener == null)
            {
                keyListener = KeyListener()
            }

            return keyListener as KeyListener
        }

        fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int)
        {
            if (key >= get().keyPressed.size || key < 0) return

            if (action == GLFW.GLFW_PRESS)
            {
                get().keyPressed[key] = true
            }
            else if (action == GLFW.GLFW_RELEASE)
            {
                get().keyPressed[key] = false
            }
        }

        fun isKeyPressed(key: Int) : Boolean
        {
            if (key >= get().keyPressed.size || key < 0) return false
            return get().keyPressed[key]
        }
    }
}