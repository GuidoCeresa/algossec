/*
 * Main script to setup algosvers on installation
 */

def sourceFile
def targetFile

// copy NimbleBootStrap.groovy into project
sourceFile = "${pluginBasedir}/grails-app/conf/NimbleBootStrap.groovy"
targetFile = "${basedir}/grails-app/conf/NimbleBootStrap.groovy"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: true)
ant.delete(file:sourceFile)
print('------------')
print('Algossec - creato (o sovrascritto) NimbleBootStrap')
print('------------')

// copy NimbleConfig.groovy into project
sourceFile = "${pluginBasedir}/grails-app/conf/NimbleConfig.groovy"
targetFile = "${basedir}/grails-app/conf/NimbleConfig.groovy"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: true)
ant.delete(file:sourceFile)
print('------------')
print('Algossec - creato (o sovrascritto) NimbleConfig')
print('------------')


// copy NimbleSecurityFilters.groovy into project
sourceFile = "${pluginBasedir}/grails-app/conf/NimbleSecurityFilters.groovy"
targetFile = "${basedir}/grails-app/conf/NimbleSecurityFilters.groovy"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: true)
ant.delete(file:sourceFile)
print('------------')
print('Algossec - creato (o sovrascritto) NimbleSecurityFilters')
print('------------')


// copy NimbleUrlMappings.groovy into project
sourceFile = "${pluginBasedir}/grails-app/conf/NimbleUrlMappings.groovy"
targetFile = "${basedir}/grails-app/conf/NimbleUrlMappings.groovy"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: true)
ant.delete(file:sourceFile)
print('------------')
print('Algossec - creato (o sovrascritto) NimbleUrlMappings')
print('------------')



// copy Readme into project
sourceFile = "${pluginBasedir}/grails-app/Readme"
targetFile = "${basedir}/README-Sec"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: true)
ant.delete(file:sourceFile)
print('------------')
print('Algossec - creato (o sovrascritto) README-Sec')
print('------------')
