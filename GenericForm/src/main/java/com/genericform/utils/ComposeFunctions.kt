package com.genericform.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateRectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDownMenuWithTextField(
    modifier: Modifier = Modifier,
    options: List<String> = listOf(""),
    textFieldLabel: String = "",
    style: TextStyle = TextStyle.Default,
    onValueChange: (String) -> Unit,
    initialSelectValue:String = ""
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(if(options.contains(initialSelectValue)) {initialSelectValue} else {""}) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedOptionText,
            onValueChange = { selectedOptionText = it },
            label = { Text(text = textFieldLabel, maxLines = 1) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            textStyle = style,
        )

        val filteringOptions = options.filter { it.contains(selectedOptionText, ignoreCase = true) }

        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                            onValueChange(selectionOption)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        else {
            selectedOptionText = ""
        }
    }
}


@Composable
fun LoadImageWithPlaceholder(
    bitmap: MutableState<Bitmap?>,
    placeholder: Int,
    url:String = "",
    modifier: Modifier
) {
    val context = LocalContext.current

    var bytes by remember { mutableStateOf<ByteArray?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { result ->
        if (result != null) {
            val item = context.contentResolver.openInputStream(result)
            bytes = item?.readBytes()
            item?.close()

            bytes?.let {
                val bos = ByteArrayOutputStream()
                BitmapFactory.decodeByteArray(it, 0, it.size)
                    .compress(Bitmap.CompressFormat.JPEG, 50, bos)
                bitmap.value = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.toByteArray().size)
            }
        }
    }
    Box(
        modifier = modifier
            .clickable { launcher.launch("image/*") },
        contentAlignment = Alignment.Center,
    ) {
        if (bitmap.value != null) {
            Image(
                bitmap = bitmap.value!!.asImageBitmap(),
                contentDescription = "Selected Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize().clip(CircleShape)
            )
        } else if(url.isNotEmpty()) {
            AsyncImage(
                model = url,
                contentDescription = "Placeholder Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        else {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = "Placeholder Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    println("image pick and set")

//        Spacer(modifier = Modifier.height(8.dp))
//        Button(onClick = { launcher.launch("image/*") }) {
//            Text(text = "Select Image")
//        }


}


@Composable
fun CreateCheckBoxes(label:String, options:List<String>,initialSelectedOption: String = "",modifier: Modifier) {
    val mutableStateListCheckBox = remember {
        val arr = Array(options.size){false}
        if(initialSelectedOption.isNotEmpty() && options.contains(initialSelectedOption)) {
            arr[options.indexOf(initialSelectedOption)] = true
        }
        mutableStateListOf(*arr)
    }
    Column {
        for (i in options.indices) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = mutableStateListCheckBox[i],
                    onCheckedChange = { checked ->
                        mutableStateListCheckBox[i] = checked
                    },
                    modifier = modifier
                )
                Text(text = options[i])
            }
        }
    }
    println(mutableStateListCheckBox.size)
}


@Composable
fun CreateRadioButtons(label:String, options:List<String>,initialSelectedOption:String= "",modifier: Modifier) {
    var selectedRadio by remember { mutableStateOf(initialSelectedOption) }
    Column {
        for (i in options.indices) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedRadio == options[i],
                    onClick = {
                        selectedRadio = options[i]
                    },
                    modifier = modifier
                )
                ClickableText(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White)) {
                        append(options[i])
                    }

                }, onClick = {
                    selectedRadio = options[i]
                })
            }
        }
    }
}



@Composable
fun TextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    maxLines: Int = 5,
    charLimit: Int = Int.MAX_VALUE, // No limit by default
    textStyle: TextStyle = TextStyle.Default,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                if (it.length <= charLimit) onValueChange(it)
            },
            placeholder = { Text(text = placeholder) },
            textStyle = textStyle,
            maxLines = maxLines,
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )

        // Optional: Show character count
        if (charLimit != Int.MAX_VALUE) {
            Text(
                text = "${value.length} / $charLimit",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.End)
            )
        }

        // Optional: Show error message
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun LoadStepper(stepper: MutableState<Int>) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Card(
            modifier = Modifier
                .padding(10.dp)
                .width(30.dp)
                .height(30.dp)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (stepper.value > 0) {
                            stepper.value -= 1
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "-", style = TextStyle(fontSize = 18.sp))
            }
        }

        Card(
            modifier = Modifier
                .width(40.dp)
                .height(30.dp)

        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stepper.value.toString(), style = TextStyle(fontSize = 16.sp))
            }
        }

        Card(
            modifier = Modifier
                .padding(10.dp)
                .width(30.dp)
                .height(30.dp)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        stepper.value += 1
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "+", style = TextStyle(fontSize = 18.sp))
            }
        }

    }
}
