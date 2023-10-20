package utils

object ValidateLogic {
  fun isNotBlank(value: String): Boolean = value.isNotBlank()
  fun is8Words(value: String): Boolean = value.length == 8
}