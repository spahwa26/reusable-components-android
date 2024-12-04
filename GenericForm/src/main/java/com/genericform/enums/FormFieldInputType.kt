package com.genericform.enums

import androidx.compose.ui.Modifier

//enum class FormFieldInputType {
//    TEXT, NUMBER, EMAIL, PASSWORD, DATE, CARDNUMBER, MONTHDROPDOWN, YEARDROPDOWN, CUSTOM, CUSTOMDROPDOWN
//}

sealed class FormFieldInputType {
    data class Text(val text:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class Number(val modifier: Modifier = Modifier) : FormFieldInputType()
    data class Email(val emailText:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class Password(val modifier: Modifier = Modifier) : FormFieldInputType()
    data class Date(val modifier: Modifier = Modifier) : FormFieldInputType()
    data class CardNumber(val cardText:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class MonthDropDown(val initialSelectedMonth:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class YearDropDown(val initialSelectedYear:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class PickImage(val url:String = "",val placeHolder:Int? = null, val modifier: Modifier = Modifier) : FormFieldInputType()
    // TODO: spell check text area, restrict particular words text area
    data class TextArea(val textAreaText:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class Custom(val modifier: Modifier = Modifier) : FormFieldInputType()
    data class CheckBox(val fieldName:String,val options: List<String>,val initialSelectedOption:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class RadioButton(val fieldName:String,val options: List<String>,val initialSelectedOption:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class CustomDropDown(val fieldName:String,val options: List<String>,val initialSelectedOption:String = "", val modifier: Modifier = Modifier) : FormFieldInputType()
    data class StepperControl(val textInMid:Boolean) : FormFieldInputType()
}
