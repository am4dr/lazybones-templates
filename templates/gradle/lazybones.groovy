import java.nio.file.Files
import java.nio.file.Paths

def defaultConfig = new ConfigSlurper().parse( '''
version {
    kotlin = '1.1.3-2'
    ben_manes_versions = '0.15.0'
    groovy = '2.4.10'
    commons_cli = '1.4'
    slf4j = '1.7.25'
    spock = '1.1-groovy-2.4-rc4'
}
''')

def configPath = Paths.get(System.getProperty('user.home')).resolve('.config/lazybones/am4dr-gradle-template-config.groovy').toRealPath()
def config = {
    if (Files.exists(configPath)) {
        println "info: config file found at: $configPath, use this"
        defaultConfig.merge(new ConfigSlurper().parse(configPath.toFile().text))
    }
    else {
        println "info: config file not found at $configPath, use default config"
        defaultConfig
    }
}.call()
def versions = config.version.with {[
    KOTLIN_VERSION: kotlin,
    BEN_MANES_VERSIONS_VERSION: ben_manes_versions,
    GROOVY_VERSION: groovy,
    COMMONS_CLI_VERSION: commons_cli,
    SLF4J_VERSION: slf4j,
    SPOCK_VERSION: spock,
]}
processTemplates('build.gradle', versions)


