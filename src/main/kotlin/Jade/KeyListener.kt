package Jade

import org.lwjgl.glfw.GLFW

object KeyListener
{
    private val keyPressed = BooleanArray(GLFW.GLFW_KEY_LAST + 1)

    fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int)
    {
        if (key >= keyPressed.size || key < 0) return

        if (action == GLFW.GLFW_PRESS)
        {
            keyPressed[key] = true
        }
        else if (action == GLFW.GLFW_RELEASE)
        {
            keyPressed[key] = false
        }
    }

    fun isKeyPressed(key: Int) : Boolean
    {
        if (key >= keyPressed.size || key < 0) return false
        return keyPressed[key]
    }
}