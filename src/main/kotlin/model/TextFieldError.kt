package model

data class TextFieldError(val message: String, val validateCondition: (String)->Boolean)