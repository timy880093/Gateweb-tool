package viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import model.Permit
import model.TextFieldState
import org.slf4j.LoggerFactory
import utils.StandaloneUtil
import utils.ValidateLogic

class RichKeyViewModel : ViewModel() {
  companion object {
    private val log = LoggerFactory.getLogger(RichKeyViewModel::class.java)
  }

  private val _taxId1 = MutableStateFlow(TextFieldState("", false))
  val taxId1 = _taxId1
  private val _taxId2 = MutableStateFlow(TextFieldState("00000000", false))
  val taxId2 = _taxId2
  private val _taxId3 = MutableStateFlow(TextFieldState("00000000", false))
  val taxId3 = _taxId3
  private val _yearEnd2 = MutableStateFlow(TextFieldState("40", false))
  val yearEnd2 = _yearEnd2
  private val _localPrintCount = MutableStateFlow(TextFieldState("", false))
  val localPrintCount = _localPrintCount
  private val _csvTransformCount = MutableStateFlow(TextFieldState("", false))
  val csvTransformCount = _csvTransformCount
  private val _txtTransformCount = MutableStateFlow(TextFieldState("", false))
  val txtTransformCount = _txtTransformCount
  private val _transferCount = MutableStateFlow(TextFieldState("", false))
  val transferCount = _transferCount
  private val _ftpCount = MutableStateFlow(TextFieldState("", false))
  val ftpCount = _ftpCount
  private val _cloudPrintCount = MutableStateFlow(TextFieldState("", false))
  val cloudPrintCount = _cloudPrintCount
  private val _emailCount = MutableStateFlow(TextFieldState("", false))
  val emailCount = _emailCount
  private val _webCount = MutableStateFlow(TextFieldState("", false))
  val webCount = _webCount
  private val _key = MutableStateFlow("")
  val key = _key
  private val _lock = MutableStateFlow("")
  val lock = _lock
  private val _enableValidate = MutableStateFlow(false)
  val enableValidate = _enableValidate

  fun generateStandaloneKey(permit: Permit, yearEnd2: String): Pair<String, String> {
    val content = StandaloneUtil.genLockContent(permit, yearEnd2, permit.businessNo)
    val pair = StandaloneUtil.encodeKeyLockPair(content)
    log.info("generate: {}", pair)
    return pair
  }

  fun generate() {
    log.info("generate")
//    println(taxId2State.isError)
//    println(taxId3State.isError)
    _enableValidate.value = true
    println(_taxId1.value)
    println(_taxId2.value)
    println(_taxId3.value)
    if (_taxId1.value.isError || _taxId2.value.isError || _taxId3.value.isError) {
      log.warn("validate error")
      return
    }

    val permit = Permit(
      success = true,
      businessNo = listOf(taxId1.value.text, taxId3.value.text, taxId3.value.text),
      localPrintAuthorizedQuantity = localPrintCount.value.text.toIntOrNull() ?: 0,
      csvTransformAuthorizedQuantity = csvTransformCount.value.text.toIntOrNull() ?: 0,
      txtTransformAuthorizedQuantity = txtTransformCount.value.text.toIntOrNull() ?: 0,
      transferAuthorizedQuantity = transferCount.value.text.toIntOrNull() ?: 0,
      ftpGrabberAuthorizedQuantity = ftpCount.value.text.toIntOrNull() ?: 0,
    )
    val (k, l) = generateStandaloneKey(permit, yearEnd2.value.text)
    _key.value = k
    _lock.value = l
    println("generate OK")
  }

  fun onChange(event: RichKeyEvent) {
    when (event) {
      is RichKeyEvent.UpdateTaxId1 -> _taxId1.value = event.state
      is RichKeyEvent.UpdateTaxId2 -> _taxId2.value = event.state
      is RichKeyEvent.UpdateTaxId3 -> _taxId3.value = event.state
      is RichKeyEvent.UpdateYearEnd2 -> _yearEnd2.value = event.state
      is RichKeyEvent.UpdateLocalPrintCount -> _localPrintCount.value = event.state
      is RichKeyEvent.UpdateCsvTransformCount -> _csvTransformCount.value = event.state
      is RichKeyEvent.UpdateTxtTransformCount -> _txtTransformCount.value = event.state
      is RichKeyEvent.UpdateTransferCount -> _transferCount.value = event.state
      is RichKeyEvent.UpdateFtpCount -> _ftpCount.value = event.state
    }
  }

  fun validateTaxId(value: String): List<String> {
    val errors = mutableListOf<String>()
    when {
      !ValidateLogic.isNotBlank(value) -> errors + "統編必填"
      !ValidateLogic.is8Words(value) -> errors + "統編需 8 碼"
    }
    return errors
  }

  fun reset(){
    log.info("reset")
    _taxId1.value = TextFieldState("",false)
    _taxId2.value = TextFieldState("00000000",false)
    _taxId3.value = TextFieldState("00000000",false)
    _yearEnd2.value = TextFieldState("40",false)
    _localPrintCount.value = TextFieldState("",false)
    _csvTransformCount.value = TextFieldState("",false)
    _txtTransformCount.value = TextFieldState("",false)
    _transferCount.value = TextFieldState("",false)
    _ftpCount.value = TextFieldState("",false)
    _cloudPrintCount.value = TextFieldState("",false)
    _emailCount.value = TextFieldState("",false)
    webCount.value = TextFieldState("",false)
    key.value = ""
    lock.value = ""
  }

}


sealed class RichKeyEvent {
  data class UpdateTaxId1(val state: TextFieldState) : RichKeyEvent()
  data class UpdateTaxId2(val state: TextFieldState) : RichKeyEvent()
  data class UpdateTaxId3(val state: TextFieldState) : RichKeyEvent()
  data class UpdateYearEnd2(val state: TextFieldState) : RichKeyEvent()
  data class UpdateLocalPrintCount(val state: TextFieldState) : RichKeyEvent()
  data class UpdateCsvTransformCount(val state: TextFieldState) : RichKeyEvent()
  data class UpdateTxtTransformCount(val state: TextFieldState) : RichKeyEvent()
  data class UpdateTransferCount(val state: TextFieldState) : RichKeyEvent()
  data class UpdateFtpCount(val state: TextFieldState) : RichKeyEvent()
}
