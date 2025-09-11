package com.jfinex.collection.data.local.components

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

fun dateToday(): LocalDate = LocalDate.now()

fun formattedDateToday(): String = dateToday().format(formatter)

fun formattedDate(date: LocalDate): String = date.format(formatter)