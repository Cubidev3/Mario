package Jade

import org.lwjgl.glfw.GLFW

object MouseListener
{
    private var scrollX: Double = 0.0
    private var scrollY: Double = 0.0

    private var xPos: Double = 0.0
    private var yPos: Double = 0.0
    private var lastY: Double = 0.0
    private var lastX: Double = 0.0

    private val mouseButtonPressed = BooleanArray(GLFW.GLFW_MOUSE_BUTTON_LAST + 1)
    private var isDragging = false

    fun cursorPositionCallback(window: Long, xPos: Double, yPos: Double)
    {
        lastX = this.xPos
        lastY = this.yPos
        this.xPos = xPos
        this.yPos = yPos

        // Check for Dragging
        for (isPressed in mouseButtonPressed)
        {
            if (isPressed)
            {
                isDragging = true
                break
            }
        }
    }

    fun mouseButtonCallback(window: Long, button: Int, action: Int, mods: Int)
    {
        if (button >= mouseButtonPressed.size || button < 0) return

        if (action == GLFW.GLFW_PRESS)
        {
            mouseButtonPressed[button] = true
        }
        else if (action == GLFW.GLFW_RELEASE)
        {
            mouseButtonPressed[button] = false
            isDragging = false
        }
    }

    fun scrollCallback(window: Long, xOffset: Double, yOffset: Double)
    {
        scrollX = xOffset
        scrollY = yOffset
    }

    fun endFrame()
    {
        scrollX = 0.0
        scrollY = 0.0
        lastX = xPos
        lastY = yPos
    }

    fun getX() : Float
    {
        return xPos as Float
    }

    fun getY() : Float
    {
        return yPos as Float
    }

    fun getDx() : Float
    {
        return (xPos - lastX) as Float
    }

    fun getDy() : Float
    {
        return (yPos - lastY) as Float
    }

    fun getScrollX() : Float
    {
        return scrollX as Float
    }

    fun getScrollY() : Float
    {
        return scrollY as Float
    }

    fun isDragging() : Boolean
    {
        return isDragging
    }

    fun mouseButtonDown(button: Int) : Boolean
    {
        if (button >= mouseButtonPressed.size || button < 0) return false
        return mouseButtonPressed[button]
    }
}