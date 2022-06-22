package Util

object Time
{
    private val startTime = System.nanoTime()

    fun getTime() : Long
    {
        return System.nanoTime() - startTime
    }
}