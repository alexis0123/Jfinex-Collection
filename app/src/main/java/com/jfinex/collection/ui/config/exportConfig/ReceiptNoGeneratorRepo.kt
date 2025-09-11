package com.jfinex.collection.ui.config.exportConfig

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class StudentsWithRN(
    val block: String,
    val name: String,
    val receiptNumbers: Map<String, Int>
)

const val START = 100000
const val INCREMENT = 10000

class ReceiptNoGeneratorRepo @Inject constructor() {

    private var lastBase = START

    fun generateReceiptNo(
        studentsList: List<List<String>>, // each = [block, name]
        fields: List<String>
    ): Pair<List<StudentsWithRN>, Map<String, Int>> {

        val fieldToReceipts: Map<String, List<Int>> = fields.associate { field ->
            val baseForField = lastBase
            val receipts = (baseForField until baseForField + studentsList.size).shuffled()
            lastBase += INCREMENT
            field to receipts
        }

        val studentsWithRN = studentsList.mapIndexed { studentIndex, studentRow ->
            val block = studentRow.getOrNull(0).orEmpty()
            val name = studentRow.getOrNull(1).orEmpty()
            val receiptNumbers = fields.associateWith { field ->
                fieldToReceipts[field]!![studentIndex]
            }
            StudentsWithRN(block, name, receiptNumbers)
        }

        val newBaseNumbers = fields.associateWith {
            val nextBase = lastBase
            lastBase += INCREMENT
            nextBase
        }

        return studentsWithRN to newBaseNumbers
    }
}