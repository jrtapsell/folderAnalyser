package uk.co.jrtapsell.file_analyser.cli

import uk.co.jrtapsell.file_analyser.common.Controller
import uk.co.jrtapsell.file_analyser.common.Model
import uk.co.jrtapsell.file_analyser.common.View

fun main(args: Array<String>) {
    val c = Controller(CLIView(), Model())
    c.loadNewDirectory()
}
