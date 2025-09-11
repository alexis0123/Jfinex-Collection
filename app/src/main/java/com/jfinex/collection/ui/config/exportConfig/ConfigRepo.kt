package com.jfinex.collection.ui.config.exportConfig

import javax.inject.Inject
import com.jfinex.collection.ui.field.FieldsRepository

class ConfigRepository @Inject constructor(
    private val fieldsRepository: FieldsRepository,
    private val receiptRepo: ReceiptNoGeneratorRepo
) {

    // Helper to check if there are any fields added
    fun hasFields(): Boolean = fieldsRepository.fields.isNotEmpty()

    fun buildConfig(students: List<List<String>>): Config {
        val fields = fieldsRepository.fields.associate { field ->
            field.name to field.category
        }

        if (fields.isEmpty()) throw IllegalStateException("No fields added. Add at least one field before export.")
        if (students.isEmpty()) throw IllegalStateException("CSV has no valid students.")

        val (studentsList, newBases) = receiptRepo.generateReceiptNo(
            studentsList = students,
            fields = fields.keys.toList()
        )
        val studentsWithRN: Map<String, StudentConfig> =
            studentsList.associate { student ->
                makeKey(student.name.trim(), student.block.trim()) to StudentConfig(
                    block = student.block,
                    receipts = student.receiptNumbers
                )
            }

        return Config(
            newBaseNumbers = newBases,
            fields = fields,
            studentsWithReceiptNumber = studentsWithRN
        )
    }
}