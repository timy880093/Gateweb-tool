package viewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Permit
import model.RichKeyForm
import model.TextFieldState
import org.slf4j.LoggerFactory
import utils.StandaloneUtil
import view.page.validateIs2Words
import view.page.validateIs8Words
import view.page.validateIsNumberOrEmpty

class RichKeyViewModel : ViewModel() {
  companion object {
    private val log = LoggerFactory.getLogger(RichKeyViewModel::class.java)
  }

  private val scope = CoroutineScope(Dispatchers.Default)
  private val _formState = MutableStateFlow(init())
  val formState = _formState.asStateFlow()

  private fun init(): RichKeyForm {
    return RichKeyForm(
      taxId1 = TextFieldState("", listOf(validateIs8Words to "必須 8 碼數字")),
      taxId2 = TextFieldState("00000000", listOf(validateIs8Words to "必須 8 碼數字")),
      taxId3 = TextFieldState("00000000", listOf(validateIs8Words to "必須 8 碼數字")),
      yearEnd2 = TextFieldState("40", listOf(validateIs2Words to "必須 2 碼數字")),
      localPrintCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      csvTransformCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      txtTransformCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      transferCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      ftpCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      cloudPrintCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      emailCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      webCount = TextFieldState("", listOf(validateIsNumberOrEmpty to "必須數字或不填")),
      enableValidate = false,
      key = "",
      lock = ""
    )
  }

  fun onChange(event: RichKeyEvent) {
    _formState.value.run {
      when (event) {
        is RichKeyEvent.UpdateTaxId1 -> _formState.value = copy(taxId1 = taxId1.updateText(event.state))
        is RichKeyEvent.UpdateTaxId2 -> _formState.value = copy(taxId2 = taxId2.updateText(event.state))
        is RichKeyEvent.UpdateTaxId3 -> _formState.value = copy(taxId3 = taxId3.updateText(event.state))
        is RichKeyEvent.UpdateYearEnd2 -> _formState.value = copy(yearEnd2 = yearEnd2.updateText(event.state))
        is RichKeyEvent.UpdateLocalPrintCount -> _formState.value =
          copy(localPrintCount = localPrintCount.updateText(event.state))

        is RichKeyEvent.UpdateCsvTransformCount -> _formState.value =
          copy(csvTransformCount = csvTransformCount.updateText(event.state))

        is RichKeyEvent.UpdateTxtTransformCount -> _formState.value =
          copy(txtTransformCount = txtTransformCount.updateText(event.state))

        is RichKeyEvent.UpdateTransferCount -> _formState.value =
          copy(transferCount = transferCount.updateText(event.state))

        is RichKeyEvent.UpdateFtpCount -> _formState.value = copy(ftpCount = ftpCount.updateText(event.state))
      }
    }

  }

  fun onSubmit() {
    scope.launch(Dispatchers.Default) {
      _formState.value.run {
        Thread.sleep(3000)

        log.info("generate")
        if (isError()) {
          log.warn("validate error")
          _formState.value = copy(enableValidate = true)
          return@launch
        }
        val permit = Permit(
          success = true,
          businessNo = listOf(taxId1.text, taxId2.text, taxId3.text),
          localPrintAuthorizedQuantity = localPrintCount.text.toIntOrNull() ?: 0,
          csvTransformAuthorizedQuantity = csvTransformCount.text.toIntOrNull() ?: 0,
          txtTransformAuthorizedQuantity = txtTransformCount.text.toIntOrNull() ?: 0,
          transferAuthorizedQuantity = transferCount.text.toIntOrNull() ?: 0,
          ftpGrabberAuthorizedQuantity = ftpCount.text.toIntOrNull() ?: 0,
        )
        val (k, l) = generateStandaloneKey(permit, yearEnd2.text)
        // _formState.value 在 rub scope 只能設定一次，否則會後蓋前
        _formState.value = copy(key = k, lock = l, enableValidate = true)
        log.info("generate OK: key[{}] , lock[{}]", k, l)
      }
    }
  }

  fun onClear() {
    scope.launch(Dispatchers.Default) {
      _formState.value = init()
    }
  }

  private fun generateStandaloneKey(permit: Permit, yearEnd2: String): Pair<String, String> {
    val content = StandaloneUtil.genLockContent(permit, yearEnd2, permit.businessNo)
    val pair = StandaloneUtil.encodeKeyLockPair(content)
    return pair
  }

}


sealed class RichKeyEvent {
  data class UpdateTaxId1(val state: String) : RichKeyEvent()
  data class UpdateTaxId2(val state: String) : RichKeyEvent()
  data class UpdateTaxId3(val state: String) : RichKeyEvent()
  data class UpdateYearEnd2(val state: String) : RichKeyEvent()
  data class UpdateLocalPrintCount(val state: String) : RichKeyEvent()
  data class UpdateCsvTransformCount(val state: String) : RichKeyEvent()
  data class UpdateTxtTransformCount(val state: String) : RichKeyEvent()
  data class UpdateTransferCount(val state: String) : RichKeyEvent()
  data class UpdateFtpCount(val state: String) : RichKeyEvent()
}
