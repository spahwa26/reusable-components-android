package com.genericform.models

import androidx.compose.ui.text.TextStyle
import com.genericform.enums.FormFieldInputType

/**
@param fieldRow -1 for default
 */

data class FormField(
    val name: String,
    val label: String,
    val inputType: FormFieldInputType,
    val style: TextStyle,
    val validator: (String) -> Boolean = { true },
    val listOfFields: List<FormField> = emptyList(),
    val listOfCustomDropDowns: List<String> = emptyList(),

)