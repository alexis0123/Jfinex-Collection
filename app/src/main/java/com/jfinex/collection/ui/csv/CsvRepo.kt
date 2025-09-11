package com.jfinex.collection.ui.csv

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.opencsv.CSVReader
import javax.inject.Inject
import java.io.InputStreamReader

data class CsvResult(
    val valid: List<List<String>>,
    val ignored: List<List<String>>
)

private val rowPattern = Regex("""^[1-4][A-Z]$""")

class CsvRepository @Inject constructor() {

    fun readCsvFromUri(contentResolver: ContentResolver, uri: Uri): CsvResult {
        val validRows = mutableListOf<List<String>>()
        val ignoredRows = mutableListOf<List<String>>()

        contentResolver.openInputStream(uri)?.use { inputStream ->
            InputStreamReader(inputStream).use { isr ->
                CSVReader(isr).use { csvReader ->
                    var nextLine = csvReader.readNext()
                    while (nextLine != null) {
                        if (nextLine.size >= 2 &&
                            rowPattern.matches(nextLine[0].trim()) &&
                            nextLine[1].isNotBlank()
                        ) {
                            validRows.add(listOf(nextLine[0].trim(), nextLine[1].trim()))
                        } else {
                            val cell1 = nextLine.getOrNull(0)?.trim().orEmpty()
                            val cell2 = nextLine.getOrNull(1)?.trim().orEmpty()
                            ignoredRows.add(listOf(cell1, cell2))
                        }
                        nextLine = csvReader.readNext()
                    }
                }
            }
        } ?: throw IllegalArgumentException("Unable to open stream for URI: $uri")

        return CsvResult(validRows, ignoredRows)
    }

    fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
        var fileName: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    fileName = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (fileName == null) {
            fileName = uri.path
            val cut = fileName?.lastIndexOf('/')
            if (cut != -1 && cut != null) {
                fileName = fileName?.substring(cut + 1)
            }
        }
        return fileName
    }
}