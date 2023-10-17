import org.koin.dsl.module
import service.RichService

// All service will inject from here
val services = module {
  single { RichService() }
}