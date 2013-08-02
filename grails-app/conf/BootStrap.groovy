import it.algos.algossec.ShiroRole
import it.algos.algossec.ShiroUser
import org.apache.shiro.crypto.hash.Sha256Hash

class BootStrap {

    def init = { servletContext ->
        ShiroRole progRole
        ShiroRole adminRole
        ShiroRole guestRole
        ShiroUser progUser
        ShiroUser adminUser
        ShiroUser guestUser

        //--programmatore
        progRole = ShiroRole.findByName('Prog')
        if (progRole) {
            log.debug 'Esiste ruolo: ' + progRole.name
        } else {
            progRole = new ShiroRole(name: 'Prog')
            progRole.addToPermissions("*:*")
            progRole.save(flush: true, failOnError: true)
            log.debug 'Creato un nuovo ruolo: ' + progRole.name
        }// fine del blocco if-else
        progUser = ShiroUser.findByUsername('gac')
        if (progUser) {
            log.debug 'Esiste utente: ' + progUser.username
        } else {
            progUser = new ShiroUser(username: 'gac', passwordHash: new Sha256Hash('fulvia').toHex())
            progUser.addToRoles(progRole)
            // progUser.addToPermissions("*:*")
            progUser.save(flush: true, failOnError: true)
            log.debug 'Creato un nuovo utente: ' + progUser.username
        }// fine del blocco if-else

        //--administrator
        adminRole = ShiroRole.findByName('Admin')
        if (adminRole) {
            log.debug 'Esiste ruolo: ' + adminRole.name
        } else {
            adminRole = new ShiroRole(name: 'Admin')
            adminRole.addToPermissions("*:*")
            adminRole.save(flush: true, failOnError: true)
            log.debug 'Creato un nuovo ruolo: ' + adminRole.name
        }// fine del blocco if-else
        adminUser = ShiroUser.findByUsername('admin')
        if (adminUser) {
            log.debug 'Esiste utente: ' + adminUser.username
        } else {
            adminUser = new ShiroUser(username: 'admin', passwordHash: new Sha256Hash('password').toHex())
            adminUser.addToRoles(adminRole)
            //   adminUser.addToPermissions("*:list")
            adminUser.save(flush: true, failOnError: true)
            log.debug 'Creato un nuovo utente: ' + adminUser.username
        }// fine del blocco if-else

        //--guest
        guestRole = ShiroRole.findByName("Guest")
        if (guestRole) {
            log.debug 'Esiste ruolo: ' + guestRole.name
        } else {
            guestRole = new ShiroRole(name: "Guest")
            guestRole.addToPermissions("prova:index,list")
            guestRole.save(flush: true, failOnError: true)
            log.debug 'Creato un nuovo ruolo: ' + guestRole.name
        }// fine del blocco if-else
        guestUser = ShiroUser.findByUsername('guest')
        if (guestUser) {
            log.debug 'Esiste utente: ' + guestUser.username
        } else {
            guestUser = new ShiroUser(username: 'guest', passwordHash: new Sha256Hash('').toHex())
            guestUser.addToRoles(guestRole)
            // guestUser.addToPermissions("*:list")
            guestUser.save(flush: true, failOnError: true)
            log.debug 'Creato un nuovo utente: ' + guestUser.username
        }// fine del blocco if-else

    }// fine della closure

    def destroy = {
    }// fine della closure
}// fine della classe di tipo BootStrap
