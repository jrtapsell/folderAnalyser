package uk.co.jrtapsell.file_analyser.common

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class Model {
    fun getData(rootPath: Path): List<MeasuredFile> {
        return Files.walk(rootPath)
            .filter{path -> Files.isRegularFile(path)}
            .map{path -> rootPath.relativize(path)}
            .map{relativePath -> MeasuredFile(relativePath, rootPath) }
            .collect(Collectors.toList())
    }

    fun humanise(bytes: Long): String {
        if (bytes <= 1023) {
            return "$bytes b"
        }
        var value : Double = bytes.toDouble()
        var suffix = "b"
        for (x in listOf("kb", "mb", "gb")) {
            if (value < 1024) {
                break
            }
            value /= 1024
            suffix = x
        }
        return String.format("%.3f %s", value, suffix)
    }
}
