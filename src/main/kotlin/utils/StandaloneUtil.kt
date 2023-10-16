package utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import model.Permit
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object StandaloneUtil {
  private val LOG = LoggerFactory.getLogger(javaClass)
  private val mapper = ObjectMapper().registerKotlinModule()

  fun login(key: String, lock: String): Permit? {
    try {
      val lockBytes = Base64.getDecoder().decode(lock)
      if (!checkKeyAndLockEquals(key, lock)) return null
      val text = mapper.readTree(String(lockBytes)).get("auth").textValue()!!

      val licenseQuantity = text.substring(0, 1)
      // 二進制，開放各功能的 license
      val license = Integer.toBinaryString(text.substring(1, 4).toInt()).padStart(8, '0')
      val year2 = text.substring(4, 6)
      val businessNo1 = text.substring(6, 14)
      val businessNo2 = text.substring(14, 22)
      val businessNo3 = text.substring(22, 30)
      val expireDate =
        LocalDate.of(year2.toInt() + 2000, 12, 31).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

      val localPrintAuthorizedQuantity = if (license[7] == '1') licenseQuantity.toInt() else 0
      val cloudPrintAuthorizedQuantity = if (license[6] == '1') licenseQuantity.toInt() else 0
      val csvTransformAuthorizedQuantity = if (license[5] == '1') licenseQuantity.toInt() else 0
      val txtTransformAuthorizedQuantity = if (license[4] == '1') licenseQuantity.toInt() else 0
      val transferAuthorizedQuantity = if (license[3] == '1') licenseQuantity.toInt() else 0
      val ftpGrabberAuthorizedQuantity = if (license[2] == '1') licenseQuantity.toInt() else 0
      val emailAuthorizedQuantity = 0
      val webAuthorizedQuantity = 0

      val businessNos = mutableListOf<String>()
      if (businessNo1 != "00000000")
        businessNos.add(businessNo1)
      if (businessNo2 != "00000000")
        businessNos.add(businessNo2)
      if (businessNo3 != "00000000")
        businessNos.add(businessNo3)

      return Permit(
        true,
        businessNos,
        localPrintAuthorizedQuantity,
        expireDate,
        cloudPrintAuthorizedQuantity,
        expireDate,
        csvTransformAuthorizedQuantity,
        expireDate,
        txtTransformAuthorizedQuantity,
        expireDate,
        transferAuthorizedQuantity,
        expireDate,
        ftpGrabberAuthorizedQuantity,
        expireDate,
        emailAuthorizedQuantity,
        expireDate,
        webAuthorizedQuantity,
        expireDate
      )
    } catch (e: Exception) {
      LOG.error("login Error: {}", e.message)
      return null
    }

  }

  fun checkKeyAndLockEquals(key: String?, lock: String?): Boolean {
    if (key.isNullOrBlank() || lock.isNullOrBlank()) {
      LOG.warn("key or lock is null, login failed")
      return false
    }
    val keyBytes = Base64.getDecoder().decode(key)!!
    val lockBytes = Base64.getDecoder().decode(lock)!!
    val check = lockBytes + "77183770".toByteArray()
    val lockSHA = DigestUtils.sha256(check)
    return keyBytes.contentEquals(lockSHA)
  }

  fun genLockContent(permit: Permit, yearEnd2: String, businessNos: List<String>): String {
    permit.run {
      val licenseQuantity = listOf(
        cloudPrintAuthorizedQuantity,
        localPrintAuthorizedQuantity,
        csvTransformAuthorizedQuantity,
        transferAuthorizedQuantity,
        ftpGrabberAuthorizedQuantity,
        txtTransformAuthorizedQuantity,
        webGrabberAuthorizedQuantity,
        emailGrabberAuthorizedQuantity
      ).max()
      if (licenseQuantity >= 10) return ""

      val localPrintQ = localPrintAuthorizedQuantity.toPermit()
      val cloudPrintQ = 0
      val csvQ = csvTransformAuthorizedQuantity.toPermit()
      val txtQ = txtTransformAuthorizedQuantity.toPermit()
      val transferQ = transferAuthorizedQuantity.toPermit()
      val ftpQ = ftpGrabberAuthorizedQuantity.toPermit()
      val emailQ = 0
      val webQ = 0
      val binaryString = "$webQ$emailQ$ftpQ$transferQ$txtQ$csvQ$cloudPrintQ$localPrintQ"
      val license = Integer.parseInt(binaryString, 2).toString().padStart(3, '0')

      return "$licenseQuantity$license$yearEnd2${businessNos[0]}${businessNos[1]}${businessNos[2]}"
    }


  }

  private fun Int.toPermit(): String = if (this > 0) "1" else "0"

  fun encodeKeyLockPair(lockContent: String): Pair<String, String> {
    val node = mapper.createObjectNode().put("auth", lockContent)
    val lockBytes = mapper.writeValueAsString(node).toByteArray()
    val encodeLock = Base64.getEncoder().encodeToString(lockBytes)

    val lockSHA = DigestUtils.sha256(lockBytes + "77183770".toByteArray())
    val encodeKey = Base64.getEncoder().encodeToString(lockSHA)

    return Pair(encodeKey, encodeLock)
  }

}