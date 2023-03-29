package lab7.server.config

object ConfigurationProvider {
    private var configuration: ServerConfiguration? = null
    fun setConfiguration(config: ServerConfiguration) {
        if (configuration != null) {
            throw ConfigurationProviderAlreadyInitializedException()
        }
        configuration = config
    }

    fun getConfiguration(): ServerConfiguration {
        if (this.configuration == null) {
            throw ConfigurationProviderNotInitializedException()
        } else {
            return configuration!!
        }
    }
}
