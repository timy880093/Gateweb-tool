package model

data class TextFieldState(
  val text: String,
  val validators: List<Pair<(String) -> Boolean, String>>
) {
  fun errorMessage() = validators.filter { (validator, _) -> !validator(text) }.map { (_, message) -> message }
  fun isError() = errorMessage().isNotEmpty()
  fun updateText(value: String): TextFieldState = copy(text = value)
}