
package it.algos.algossec

class User extends grails.plugin.nimble.core.UserBase {

	// Extend UserBase with your custom values here
    static constraints = {
        username(blank: false, unique: true, minSize: 3, maxSize: 255)
    }

}
