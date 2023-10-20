package viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import model.Permit
import model.TextFieldState
import org.slf4j.LoggerFactory
import utils.StandaloneUtil

class RichKeyViewModel : ViewModel() {
  companion object {
    private val log = LoggerFactory.getLogger(RichKeyViewModel::class.java)
  }

  private val _taxId1 = MutableStateFlow(TextFieldState("", false))
  val taxId1 = _taxId1
  private val _enableValidate = MutableStateFlow(false)
  val enableValidate = _enableValidate

  fun generateStandaloneKey(permit: Permit, yearEnd2: String): Pair<String, String> {
    val content = StandaloneUtil.genLockContent(permit, yearEnd2, permit.businessNo)
    val pair = StandaloneUtil.encodeKeyLockPair(content)
    log.info("generate: {}", pair)
    return pair
  }

  fun onClick() {
    println(_taxId1.value.text)
    println(_taxId1.value.isError)
//    println(taxId2State.isError)
//    println(taxId3State.isError)
    _enableValidate.value = true
    if (_taxId1.value.isError) {
      log.warn("validate error")
      return
    }

//    val permit = Permit(
//      success = true,
//      businessNo = listOf(_taxId1State.text, taxId2State.text, taxId3State.text),
//      localPrintAuthorizedQuantity = localPrintCount.toInt(),
//      csvTransformAuthorizedQuantity = csvTransformCount.toInt(),
//      txtTransformAuthorizedQuantity = txtTransformCount.toInt(),
//      transferAuthorizedQuantity = transferCount.toInt(),
//      ftpGrabberAuthorizedQuantity = ftpCount.toInt(),
//    )
//    val (k, l) = generateStandaloneKey(permit, yearEnd)
//    key = k
//    lock = l
    println("generate OK")
  }

  fun updateField(event: RichKeyEvent) {
    when (event) {
      is RichKeyEvent.UpdateTaxId1 -> _taxId1.value = event.state
    }
  }

}


sealed class RichKeyEvent() {
  data class UpdateTaxId1(val state: TextFieldState) : RichKeyEvent()
}