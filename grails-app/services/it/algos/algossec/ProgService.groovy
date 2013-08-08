package it.algos.algossec

import grails.plugin.nimble.core.Permission
import grails.plugin.nimble.core.Role
import grails.plugin.nimble.core.UserBase
import org.apache.shiro.authz.permission.AllPermission

class ProgService {

    public static final String PROG_ROLE = 'Programmatore'

    def permissionService

    /**
     * Provides programmator capability to a user account.
     *
     * @param user A valid user object that should be assigned global prog rights
     *
     * @pre Passed user object must have been validated to ensure
     * that hibernate does not auto persist the object to the repository prior to service invocation
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    boolean add(UserBase user) {
        // Grant prog role
        def progRole = Role.findByName(PROG_ROLE)

        if (!progRole) {
            log.error("Unable to located default prog role")
            throw new RuntimeException("Unable to locate default prog role")
        }

        log.info("Trying to add user [$user.id]:$user.username as prog")

        progRole.addToUsers(user)
        user.addToRoles(progRole)

        if (!progRole.save()) {
            log.error "Unable to grant prog privilege to [$user.id]:$user.username"
            progRole.errors.each {  log.error '[${user.username}] - ' + it }
            progRole.discard()
            user.discard()
            return false
        }

        if (!user.save()) {
            log.error "Unable to grant prog role to [$user.id]$user.username failed to modify user account"
            user.errors.each { log.error it }

            throw new RuntimeException("Unable to grant prog role to [$user.id]$user.username")
        }

        // Grant programmator 'ALL' permission
        Permission progPermission = new Permission(target:'*')
        progPermission.managed = true
        progPermission.type = Permission.adminPerm

        permissionService.createPermission(progPermission, user)

        log.info "Granted prog privileges to [$user.id]:$user.username"
        return true
    }

    /**
     * Removes prog capability from a user account.
     *
     * @param user A valid user object that should have global prog rights removed
     *
     * @pre Passed user object must have been validated to ensure
     * that hibernate does not auto persist the object to the repository prior to service invocation
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    boolean remove(UserBase user) {
        def progRole = Role.findByName(PROG_ROLE)

        if (!progRole) {
            log.error("Unable to located default prog role")
            throw new RuntimeException("Unable to locate default prog role")
        }

        if(progRole.users.size() < 2) {
            log.warn("Unable to remove user from prog, would leave no system prog available")
            return false
        }

        progRole.removeFromUsers(user)
        user.removeFromRoles(progRole)

        if (!progRole.save()) {
            log.error "Unable to revoke prog privilege from [$user.id]$user.username"
            progRole.errors.each { log.error it }

            progRole.discard()
            user.discard()
            return false
        }

        if (!user.save()) {
            log.error "Unable to revoke prog privilege from [$user.id]$user.username failed to modify user account"
            user.errors.each { log.error it }

            throw new RuntimeException("Unable to revoke prog privilege from [$user.id]$user.username failed to modify user account")
        }

        // Revoke administrative 'ALL' permission(s)
        def permToRemove = []
        user.permissions.each {
            if (it.type.equals(AllPermission.name) || it.type.equals(grails.plugin.nimble.auth.AllPermission.name)) {
                permToRemove.add(it)
                log.debug("Found $it.type for user [$user.id]$user.username adding to remove queue")
            }
        }

        permToRemove.each {
            user.permissions.remove(it)
            log.debug("Removing $it.type from user [$user.id]$user.username")
        }

        if (!user.save()) {
            log.error "Unable to revoke prog permission from [$user.id]$user.username failed to modify user account"
            user.errors.each { log.error it }

            throw new RuntimeException("Unable to revoke prog permission from [$user.id]$user.username")
        }

        log.info "Revoked prog privilege from [$user.id]$user.username"
        return true
    }
}
