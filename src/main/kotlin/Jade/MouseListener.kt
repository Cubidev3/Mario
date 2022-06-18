package Jade

import org.lwjgl.glfw.GLFW

class MouseListener
{
    private var scrollX: Double = 0.0
    private var scrollY: Double = 0.0

    private var xPos: Double = 0.0
    private var yPos: Double = 0.0
    private var lastY: Double = 0.0
    private var lastX: Double = 0.0

    private val mouseButtonPressed = BooleanArray(GLFW.GLFW_MOUSE_BUTTON_LAST + 1)
    private var isDragging = false

    companion object
    {
        private var mouseListener: MouseListener? = null

        fun get(): MouseListener
        {
            if (mouseListener == null)
            {
                mouseListener = MouseListener()
            }

            return mouseListener as MouseListener
        }

        fun cursorPositionCallback(window: Long, xPos: Double, yPos: Double)
        {
            get().lastX = get().xPos
            get().lastY = get().yPos
            get().xPos = xPos
            get().yPos = yPos

            // Check for Dragging
            for (isPressed in get().mouseButtonPressed)
            {
                if (isPressed)
                {
                    get().isDragging = true
                    break
                }
            }
        }

        fun mouseButtonCallback(window: Long, button: Int, action: Int, mods: Int)
        {
            if (button >= get().mouseButtonPressed.size || button < 0) return

            if (action == GLFW.GLFW_PRESS)
            {
                get().mouseButtonPressed[button] = true
            }
            else if (action == GLFW.GLFW_RELEASE)
            {
                get().mouseButtonPressed[button] = false
                get().isDragging = false
            }
        }

        fun scrollCallback(window: Long, xOffset: Double, yOffset: Double)
        {
            get().scrollX = xOffset
            get().scrollY = yOffset
        }

        fun endFrame()
        {
            get().scrollX = 0.0
            get().scrollY = 0.0
            get().lastX = get().xPos
            get().lastY = get().yPos
        }

        fun getX() : Float
        {
            return get().xPos as Float
        }

        fun getY() : Float
        {
            return get().yPos as Float
        }

        fun getDx() : Float
        {
            return (get().xPos - get().lastX) as Float
        }

        fun getDy() : Float
        {
            return (get().yPos - get().lastY) as Float
        }

        fun getScrollX() : Float
        {
            return get().scrollX as Float
        }

        fun getScrollY() : Float
        {
            return get().scrollY as Float
        }

        fun isDragging() : Boolean
        {
            return get().isDragging
        }

        fun mouseButtonDown(button: Int) : Boolean
        {
            if (button >= get().mouseButtonPressed.size || button < 0) return false
            return get().mouseButtonPressed[button]
        }
    }
}