package hu.aut.android.kotlintodolist.touch

interface TodoTouchHelperAdapter {

    fun onItemDismissed(position: Int)

    fun onItemMoved(fromPosition: Int, toPosition: Int)
}