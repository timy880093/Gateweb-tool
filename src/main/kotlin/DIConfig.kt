import org.koin.dsl.module
import viewModel.RichKeyViewModel

// All service will inject from here
val services = module {
  single { RichKeyViewModel() }
}