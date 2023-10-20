package utils

fun String.isNumber(): Boolean {
  return this.toIntOrNull() != null
}