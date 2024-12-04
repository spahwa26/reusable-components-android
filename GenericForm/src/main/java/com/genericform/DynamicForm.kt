package com.genericform

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.genericform.enums.FormFieldInputType
import com.genericform.models.FormField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.genericform.enums.FieldType
import com.genericform.utils.CreateCheckBoxes
import com.genericform.utils.CreateRadioButtons
import com.genericform.utils.DatePickerModal
import com.genericform.utils.LoadImageWithPlaceholder
import com.genericform.utils.LoadStepper
import com.genericform.utils.MyDropDownMenuWithTextField
import com.genericform.utils.TextArea
import com.genericform.utils.creditCardFilter


@SuppressLint("MutableCollectionMutableState")
@Composable
fun GenericForm(
    fields: List<FormField>,
    fieldsType: FieldType = FieldType.SIMPLE,
    onSubmit: (Map<String, TextFieldValue>, Map<String, String>) -> Unit) {

    if(fields.isEmpty()) return

    val formMapData = remember {

        val stateMap = mutableStateMapOf<String, TextFieldValue>()

        return@remember stateMap

    }
    val storeOtherData = mutableMapOf<String, String>()

    if(fieldsType == FieldType.SIMPLE) {
        var showModal by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf("") }
        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fields.forEach { field ->
                when (field.inputType) {
                    is FormFieldInputType.Text -> {
                        TextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style
                        )
                    }

                    is FormFieldInputType.Number -> TextField(
                        value = formMapData[field.name] ?: TextFieldValue(""),
                        onValueChange = {
                            formMapData[field.name] = it
                        },
                        label = { Text(field.label) },
                        textStyle = field.style,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )

                    is FormFieldInputType.Email -> {
                        TextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    is FormFieldInputType.Password -> {
                        TextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }
                    // TODO: have to correct date picker
                    is FormFieldInputType.Date -> {
                        Row {
                            Button(onClick = {
                                showModal = true
                            }) {
                                Text("Select Date")
                            }

                            Text(selectedDate)
                            if (showModal) {
                                DatePickerModal(onDateSelected = {
                                    selectedDate = it.toString()
                                },
                                    onDismiss = {
                                        showModal = false
                                    })
                            }
                        }
                    }

                    // TODO: card-number field in non outline
                    is FormFieldInputType.CardNumber -> {

                    }

                    // TODO: month drop down field in non outline
                   is  FormFieldInputType.MonthDropDown -> {

                    }

                    // TODO: year drop down field in non outline
                    is FormFieldInputType.YearDropDown -> {

                    }

                    // TODO: have to code custom field in non outline
                    is FormFieldInputType.Custom -> {

                    }

                    // TODO: implement simple custom drop down
                    is FormFieldInputType.CustomDropDown -> {
                        field.inputType.options
                    }

                    // TODO: implement simple checkbox upper label code
                    is FormFieldInputType.CheckBox -> {
                        CreateCheckBoxes(field.inputType.fieldName,field.inputType.options, modifier = field.inputType.modifier)
                    }

                    // TODO: implement simple radio button
                    is FormFieldInputType.RadioButton -> {

                    }

                    // TODO: implement simple image pick
                    is FormFieldInputType.PickImage -> {

                    }

                    // TODO: implement simple stepper control
                    is FormFieldInputType.StepperControl -> {

                    }

                    is FormFieldInputType.TextArea -> {

                    }
                }
            }

            Button(onClick = { onSubmit(formMapData.toMap(), storeOtherData) }) {
                Text("Submit")
            }
        }
    }
    else {
        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fields.forEach { field ->
                when (field.inputType) {
                    is FormFieldInputType.Text -> {
                        OutlinedTextField(
                            value = formMapData[field.name] ?: TextFieldValue(field.inputType.text),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style,
                            modifier = field.inputType.modifier
                        )
                    }
                    is FormFieldInputType.Number -> {
                        OutlinedTextField(value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = field.inputType.modifier
                        )
                    }
                    is FormFieldInputType.Email -> {
                        var isEmailValid by remember { mutableStateOf(true) }
                        val emailRegex = Patterns.EMAIL_ADDRESS

                        OutlinedTextField(
                            value = formMapData[field.name] ?: TextFieldValue(field.inputType.emailText),
                            onValueChange = {
                                formMapData[field.name] = it
                                isEmailValid = emailRegex.matcher(it.text).matches()
                                if(it.text.isEmpty()) {
                                    isEmailValid = true
                                }
                            },
                            label = {
                                Text(if (isEmailValid) field.label else "${field.label} (Invalid)")
                            },
                            isError = !isEmailValid,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = field.inputType.modifier
                        )
                    }

                    is FormFieldInputType.Password -> {
                        OutlinedTextField(value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = field.inputType.modifier,
                            maxLines = 1,

                        )
                    }

                    // TODO: implement date text field
                    is FormFieldInputType.Date -> {

                    }

                    is FormFieldInputType.CardNumber -> {
                        OutlinedTextField(value = formMapData[field.name] ?: TextFieldValue(field.inputType.cardText),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            visualTransformation = {
                                creditCardFilter(it)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = field.inputType.modifier
                        )
                    }

                    is FormFieldInputType.MonthDropDown -> {
                        MyDropDownMenuWithTextField(options = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"),
                            textFieldLabel = "Month", style = field.style, onValueChange = {
                                storeOtherData[field.name] = it
                            },
                            initialSelectValue = field.inputType.initialSelectedMonth,
                            modifier = field.inputType.modifier)
                    }

                    is FormFieldInputType.YearDropDown -> {
                        MyDropDownMenuWithTextField(options = listOf("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"),
                            textFieldLabel = "Year", style = field.style, onValueChange = {
                                storeOtherData[field.name] = it
                            },
                            initialSelectValue = field.inputType.initialSelectedYear,
                            modifier = field.inputType.modifier)
                    }

                    is FormFieldInputType.Custom -> {
                        Row(modifier = field.inputType.modifier) {
                            field.listOfFields.forEach {
                                when (it.inputType) {
                                    is FormFieldInputType.Text -> {
                                    }
                                    is FormFieldInputType.Number -> {
                                    }
                                    is FormFieldInputType.Email -> {
                                    }
                                    is FormFieldInputType.Password -> {
                                    }
                                    is FormFieldInputType.Date -> {
                                    }
                                    is FormFieldInputType.CardNumber -> {
                                    }
                                    is FormFieldInputType.MonthDropDown -> {
                                        MyDropDownMenuWithTextField(options = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"),
                                            textFieldLabel = "Month", style = field.style, modifier = it.inputType.modifier.weight(1f), onValueChange = { selectedOption ->
                                                storeOtherData[field.name] = selectedOption
                                            },
                                            initialSelectValue = it.inputType.initialSelectedMonth)
                                    }
                                    is FormFieldInputType.YearDropDown -> {
                                        MyDropDownMenuWithTextField(options = listOf("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"),
                                            textFieldLabel = "Year", style = field.style, modifier = it.inputType.modifier.weight(1f), onValueChange = { selectedOption ->
                                                storeOtherData[field.name] = selectedOption
                                            },
                                            initialSelectValue = it.inputType.initialSelectedYear)
                                    }

                                    // should not be implemented
                                    is FormFieldInputType.Custom -> {
                                    }

                                    is FormFieldInputType.CustomDropDown -> {
                                        if(it.inputType.options.isNotEmpty()) {
                                            MyDropDownMenuWithTextField(
                                                options = it.inputType.options,
                                                textFieldLabel = it.inputType.fieldName, style = field.style, onValueChange = { changedValue ->
                                                    storeOtherData[field.name] = changedValue
                                                },
                                                initialSelectValue = it.inputType.initialSelectedOption)
                                        }
                                    }

                                    // TODO: implement inner checkbox upper label code
                                    is FormFieldInputType.CheckBox -> {
                                        CreateCheckBoxes(it.inputType.fieldName,it.inputType.options, modifier = it.inputType.modifier)
                                    }

                                    // TODO: implement inner radio button
                                    is FormFieldInputType.RadioButton -> {
                                    }

                                    // TODO: implement inner image pick
                                    is FormFieldInputType.PickImage -> {
                                    }

                                    // TODO: implement inner text area
                                    is FormFieldInputType.TextArea -> {
                                    }

                                    is FormFieldInputType.StepperControl -> {
                                    }
                                }
                            }
                        }
                    }

                    is FormFieldInputType.CustomDropDown -> {
                        if(field.inputType.options.isNotEmpty()) {
                            MyDropDownMenuWithTextField(options = field.inputType.options,
                                textFieldLabel = field.inputType.fieldName, style = field.style, onValueChange = {
                                    storeOtherData[field.name] = it
                                },
                                initialSelectValue = field.inputType.initialSelectedOption)
                        }
                    }

                    // TODO: implement outline checkbox upper label code
                    is FormFieldInputType.CheckBox -> {
                        CreateCheckBoxes(field.inputType.fieldName,field.inputType.options,field.inputType.initialSelectedOption, modifier = field.inputType.modifier)
                    }

                    // TODO: implement outline radio button upper label code
                    is FormFieldInputType.RadioButton -> {
                        CreateRadioButtons(field.inputType.fieldName,field.inputType.options,field.inputType.initialSelectedOption, modifier = field.inputType.modifier)
                    }

                    // TODO: implement outline image pick
                    is FormFieldInputType.PickImage-> {
                        val bi = remember { mutableStateOf<Bitmap?>(null) }
                        if(field.inputType.url.isNotEmpty()) {
                            LoadImageWithPlaceholder(bi,R.drawable.baseline_image_24,field.inputType.url, modifier = field.inputType.modifier)
                        }
                        else if(field.inputType.placeHolder != null) {
                            LoadImageWithPlaceholder(bi,field.inputType.placeHolder, modifier = field.inputType.modifier)
                        }
                        else {
                            LoadImageWithPlaceholder(bi,R.drawable.baseline_image_24, modifier = field.inputType.modifier)
                        }
                    }

                    is FormFieldInputType.TextArea -> {
                        var text by remember { mutableStateOf(field.inputType.textAreaText) }
                        val charLimit = 200
                        TextArea(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            charLimit = charLimit,
                            modifier = field.inputType.modifier
                        )
                    }

                    // TODO: implement outline stepper control UI enhancement
                    is FormFieldInputType.StepperControl -> {
                        val stepper = remember { mutableIntStateOf(0) }
                        LoadStepper(stepper)
                    }
                }
            }

            Button(onClick = {
                onSubmit(formMapData.toMap(),storeOtherData)
            }) {
                Text("Submit")
            }
        }
    }
}


// other useful functions
