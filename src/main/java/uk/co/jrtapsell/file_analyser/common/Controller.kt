package uk.co.jrtapsell.file_analyser.common

import java.util.function.Consumer

class Controller(val view: uk.co.jrtapsell.file_analyser.common.View, val model: Model) {

    init {
        view.setOnLoad(Consumer { loadNewDirectory(it) })
        view.setOnUpdate(Consumer { reloadDirectory(it) })
    }

    val sorter : Comparator<DataPair> = Comparator.comparingDouble<DataPair> { p -> p.value}.reversed()

    var currentData: List<MeasuredFile>? = null

    fun loadNewDirectory(limit: Long?) {
        var folder: java.io.File = view.chooseDirectory() ?: return
        var data = loadData(folder)
        currentData = data
        update(data, limit)
    }

    fun reloadDirectory(limit: Long?) {
        val temp = currentData
        if (temp != null) {
            update(temp, limit)
        }
    }

    fun displayData(data: List<MeasuredFile>, limit: Long?, prefix: String) {
        var byExtension = data.stream()
                .collect(java.util.stream.Collectors.groupingBy<MeasuredFile, String> {it.extension})
                .entries.stream()
                .map{totalSizes(it)}
                .sorted(sorter)
        var byFile = data.stream()
                .map { p -> DataPair(p.fullName, p.size.toDouble()) }
                .sorted(sorter)
        var byDirectory = data.stream()
                .collect(java.util.stream.Collectors.groupingBy<MeasuredFile, String>({ it.getRootDir() }))
                .entries.stream()
                .map{totalSizes(it)}
                .sorted(sorter)

        if (limit != null) {
            byDirectory = byDirectory.limit(limit)
            byFile = byFile.limit(limit)
            byExtension = byExtension.limit(limit)
        }

        view.setDataVisible(true)
        view.setFileData(prefix + "Files", byFile)
        view.setExtensionData(prefix + "Extensions", byExtension)
        view.setFolderData(prefix + "Folders", byDirectory)
        var full = data.stream().mapToDouble{it.size.toDouble() }
                .sum()

        val suffix: String
        if (full > 1024) {
            full /= 1024.0
            suffix = "kb"
        } else {
            suffix = "b"
        }
        view.setTotalText(String.format("%.3f %s", full, suffix))
    }

    fun loadData(dir: java.io.File): List<MeasuredFile> {
        return model.getData(dir.toPath())
    }

    fun update(data: List<MeasuredFile>, limit: Long?) {
        var prefix = ""
        if (limit != null) {
            prefix = "Top $limit "
        }
        displayData(data, limit, prefix)
    }

    fun totalSizes(p: MutableMap.MutableEntry<String, List<MeasuredFile>>): DataPair {
        return DataPair(
                p.key,
                p.value.stream()
                        .map({ q -> q.size })
                        .mapToDouble({ z -> z.toDouble() })
                        .sum())
    }
}