package uk.co.jrtapsell.file_analyser.common

class MeasuredFile(relative: java.nio.file.Path, root: java.nio.file.Path) {

    val pathElements: List<String>
    val size: Long
    val extension: String
    val fullName: String = relative.toString()
    val absolutePath: java.nio.file.Path
    val name : String

    init {
        val nameCount = relative.nameCount
        pathElements = java.util.ArrayList<String>(nameCount)
        (0..nameCount - 1).mapTo(pathElements) { relative.getName(it).toString() }
        absolutePath = root.resolve(relative)
        size = java.nio.file.Files.size(absolutePath)
        name = pathElements[pathElements.size - 1]
        extension = getExtension(name)
    }

    private fun getExtension(lastName: String): String {
        val last = lastName.lastIndexOf('.')
        return when (last) {
            -1 -> ""
            else -> lastName.substring(last + 1)
        }
    }

    fun getRootDir(): String {
        return when (pathElements.size) {
            0,1 -> "./"
            else -> {pathElements[0]}
        }
    }

    override fun toString(): String {
        return "File{name=$name size=$size}"
    }
}
