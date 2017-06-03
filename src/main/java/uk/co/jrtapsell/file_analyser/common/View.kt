package uk.co.jrtapsell.file_analyser.common

import javafx.fxml.Initializable
import java.io.File
import java.net.URL
import java.util.*
import java.util.function.Consumer
import java.util.stream.Stream

interface View : Initializable{
    fun setOnLoad(function: Consumer<Long?>)
    fun setOnUpdate(function: Consumer<Long?>)
    fun chooseDirectory(): File
    fun setDataVisible(state: Boolean)
    fun setFileData(title: String, data: Stream<DataPair>)
    fun setExtensionData(title: String, data: Stream<DataPair>)
    fun setFolderData(title: String, data: Stream<DataPair>)
    fun setTotalText(text: String)
    fun initialize()

    @Override
    override fun initialize(location: URL, resources: ResourceBundle) {
        initialize()
    }
}