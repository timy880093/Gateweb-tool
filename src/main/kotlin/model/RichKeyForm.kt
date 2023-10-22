package model

data class RichKeyForm(
  val taxId1: TextFieldState,
  val taxId2: TextFieldState,
  val taxId3: TextFieldState,
  val yearEnd2: TextFieldState,
  val localPrintCount: TextFieldState,
  val csvTransformCount: TextFieldState,
  val txtTransformCount: TextFieldState,
  val transferCount: TextFieldState,
  val ftpCount: TextFieldState,
  val cloudPrintCount: TextFieldState,
  val emailCount: TextFieldState,
  val webCount: TextFieldState,
  val enableValidate: Boolean,
  val key: String,
  val lock: String,
) {
  fun isError(): Boolean =
    taxId1.isError() || taxId2.isError() || taxId3.isError() || yearEnd2.isError() || localPrintCount.isError()
        || csvTransformCount.isError() || txtTransformCount.isError() || transferCount.isError() || ftpCount.isError()
        || cloudPrintCount.isError() || emailCount.isError() || webCount.isError()

}