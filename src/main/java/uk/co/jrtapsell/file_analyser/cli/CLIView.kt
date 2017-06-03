package uk.co.jrtapsell.file_analyser.cli

import uk.co.jrtapsell.file_analyser.common.DataPair
import uk.co.jrtapsell.file_analyser.common.View
import java.io.File
import java.util.function.Consumer
import java.util.stream.Stream

/**
 * @author James Tapsell
 */
class CLIView: View {
    override fun setOnLoad(function: Consumer<Long?>) {
    }

    override fun setOnUpdate(function: Consumer<Long?>) {
    }

    override fun chooseDirectory(): File {
        println("Enter directory name")
        return File(readLine())
    }

    override fun setDataVisible(state: Boolean) {
    }

    override fun setFileData(title: String, data: Stream<DataPair>) {
        display("File", data)
    }

    override fun setExtensionData(title: String, data: Stream<DataPair>) {
        display("Extension", data)
    }

    override fun setFolderData(title: String, data: Stream<DataPair>) {
        display("Folder", data)
    }

    private fun display(title: String, data: Stream<DataPair>) {
        println()
        println("---- $title sizes ----")
        data.forEach({
            println("$it")
        })
    }

    override fun setTotalText(text: String) {
        println("Total Size: $text")
    }

    override fun initialize() {
    }

}