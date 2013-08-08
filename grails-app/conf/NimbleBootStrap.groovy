/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */



import grails.plugin.nimble.InstanceGenerator
import grails.plugin.nimble.core.AdminsService
import grails.plugin.nimble.core.ProfileBase
import grails.plugin.nimble.core.Role
import grails.plugin.nimble.core.UserBase
import grails.plugin.nimble.core.UserService
import it.algos.algossec.NimbleConst
import it.algos.algossec.Profile
import it.algos.algossec.User

/*
 * Allows applications using Nimble to undertake process at BootStrap that are related to Nimbe provided objects
 * such as Users, Role, Groups, Permissions etc.
 *
 * Utilizing this BootStrap class ensures that the Nimble environment is populated in the backend data repository correctly
 * before the application attempts to make any extenstions.
 */
class NimbleBootStrap {

    def grailsApplication

    def nimbleService
    def userService
    def adminsService
    def progService
    def roleService

    def init = { servletContext ->
        User prog
        User admin
        User testUser
        Profile progProfile
        Profile adminProfile
        Profile userProfile

        // The following must be executed
        //--creazione del ruolo Admin
        //--creazione del ruolo User
        nimbleService.init()

        // Execute any custom Nimble related BootStrap for your application below
        creaRuoloProgrammatore()

        // Create Prog account
        if (!User.findByUsername(NimbleConst.PROG_NAME)) {
            prog = (User) InstanceGenerator.user(grailsApplication)
            prog.username = NimbleConst.PROG_NAME
            prog.pass = 'gac123'
            prog.passConfirm = 'gac123'
            prog.enabled = true

            progProfile = (Profile) InstanceGenerator.profile(grailsApplication)
            progProfile.fullName = NimbleConst.PROG_NAME
            progProfile.owner = prog
            prog.profile = progProfile

            log.info("Creating default user account with username:user")

            def savedUser = userService.createUser(prog)
            if (savedUser.hasErrors()) {
                savedUser.errors.each { log.error(it) }
                throw new RuntimeException("Error creating example user")
            }// fine del blocco if

            progService.add(prog)
            adminsService.add(prog)
        }// fine del blocco if

        // Create Admin account
        if (!User.findByUsername(NimbleConst.ADMIN_NAME)) {
            admin = (User) InstanceGenerator.user(grailsApplication)
            admin.username = NimbleConst.ADMIN_NAME
            admin.pass = "admin123"
            admin.passConfirm = "admin123"
            admin.enabled = true

            adminProfile = (Profile) InstanceGenerator.profile(grailsApplication)
            adminProfile.fullName = NimbleConst.ADMIN_NAME
            adminProfile.owner = admin
            admin.profile = adminProfile

            log.info("Creating default admin account with username:admin")

            def savedAdmin = userService.createUser(admin)
            if (savedAdmin.hasErrors()) {
                savedAdmin.errors.each { log.error(it) }
                throw new RuntimeException("Error creating administrator")
            }// fine del blocco if

            adminsService.add(admin)
        }// fine del blocco if

        // Create example User account
        if (!User.findByUsername(NimbleConst.USER_NAME)) {
            testUser = (User) InstanceGenerator.user(grailsApplication)
            testUser.username = NimbleConst.USER_NAME
            testUser.pass = 'user123'
            testUser.passConfirm = 'user123'
            testUser.enabled = true

            userProfile = (Profile) InstanceGenerator.profile(grailsApplication)
            userProfile.fullName = NimbleConst.USER_NAME
            userProfile.owner = testUser
            testUser.profile = userProfile

            log.info("Creating default user account with username:user")

            def savedUser = userService.createUser(testUser)
            if (savedUser.hasErrors()) {
                savedUser.errors.each { log.error(it) }
                throw new RuntimeException("Error creating example user")
            }// fine del blocco if
        }// fine del blocco if

    }// fine della closure

    //--crea un nuovo ruolo come programmatore
    //--diverso dal ruolo di Admin e superiore
    def creaRuoloProgrammatore = {

        // Perform all base Nimble setup
        Role progRole = Role.findByName(progService.PROG_ROLE)
        if (!progRole) {
            progRole = new Role(description: 'Funzioni di programmatore',
                    name: NimbleConst.PROG_ROLE,
                    protect: true)
            progRole.save()

            if (progRole.hasErrors()) {
                progRole.errors.each { log.error(it) }
                throw new RuntimeException("Unable to create valid users role")
            }// fine del blocco if
        }// fine del blocco if
    }// fine della closure

    //--metodo invocato direttamente da Grails
    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
