/*
 * Main script to setup algosvers on installation
 */

def targetFile

// delete NimbleBootStrap.groovy from project
targetFile = "${basedir}/grails-app/conf/NimbleBootStrap.groovy"
ant.delete(file:targetFile)
print('------------')
print('Algossec - cancellato NimbleBootStrap')
print('------------')

// delete NimbleConfig.groovy from project
targetFile = "${basedir}/grails-app/conf/NimbleConfig.groovy"
ant.delete(file:targetFile)
print('------------')
print('Algossec - cancellato NimbleConfig')
print('------------')

// delete NimbleSecurityFilters.groovy from project
targetFile = "${basedir}/grails-app/conf/NimbleSecurityFilters.groovy"
ant.delete(file:targetFile)
print('------------')
print('Algossec - cancellato NimbleSecurityFilters')
print('------------')

// delete NimbleUrlMappings.groovy from project
targetFile = "${basedir}/grails-app/conf/NimbleUrlMappings.groovy"
ant.delete(file:targetFile)
print('------------')
print('Algossec - cancellato NimbleUrlMappings')
print('------------')



