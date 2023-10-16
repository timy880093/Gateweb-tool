package service

import model.Permit
import org.slf4j.LoggerFactory
import utils.StandaloneUtil

class RichService {
  companion object {
    private val log = LoggerFactory.getLogger(javaClass)
  }

  fun generateStandaloneKey(permit: Permit, yearEnd2: String): Pair<String, String> {
    val content = StandaloneUtil.genLockContent(permit, yearEnd2, permit.businessNo)
    val pair = StandaloneUtil.encodeKeyLockPair(content)
    log.info("generate: {}", pair)
    return pair
  }
}
