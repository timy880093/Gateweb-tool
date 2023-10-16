package model

data class Permit(
  val success: Boolean,
  val businessNo: List<String>,
  val localPrintAuthorizedQuantity: Int = 0,
  val localPrintExpiryDate: String = "",
  val cloudPrintAuthorizedQuantity: Int = 0,
  val cloudPrintExpiryDate: String = "",
  val csvTransformAuthorizedQuantity: Int = 0,
  val csvTransformExpiryDate: String = "",
  val txtTransformAuthorizedQuantity: Int = 0,
  val txtTransformExpiryDate: String = "",
  val transferAuthorizedQuantity: Int = 0,
  val transferExpiryDate: String = "",
  val ftpGrabberAuthorizedQuantity: Int = 0,
  val ftpGrabberExpiryDate: String = "",
  /* unused */
  val emailGrabberAuthorizedQuantity: Int = 0,
  val emailGrabberExpiryDate: String = "",
  val webGrabberAuthorizedQuantity: Int = 0,
  val webGrabberExpiryDate: String = "",
)

