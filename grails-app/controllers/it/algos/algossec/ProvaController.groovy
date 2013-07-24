package it.algos.algossec

import it.algos.algossec.Prova
import org.springframework.dao.DataIntegrityViolationException

class ProvaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [provaInstanceList: Prova.list(params), provaInstanceTotal: Prova.count()]
    }

    def create() {
        [provaInstance: new Prova(params)]
    }

    def save() {
        def provaInstance = new Prova(params)
        if (!provaInstance.save(flush: true)) {
            render(view: "create", model: [provaInstance: provaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'prova.label', default: 'Prova'), provaInstance.id])
        redirect(action: "show", id: provaInstance.id)
    }

    def show(Long id) {
        def provaInstance = Prova.get(id)
        if (!provaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'prova.label', default: 'Prova'), id])
            redirect(action: "list")
            return
        }

        [provaInstance: provaInstance]
    }

    def edit(Long id) {
        def provaInstance = Prova.get(id)
        if (!provaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'prova.label', default: 'Prova'), id])
            redirect(action: "list")
            return
        }

        [provaInstance: provaInstance]
    }

    def update(Long id, Long version) {
        def provaInstance = Prova.get(id)
        if (!provaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'prova.label', default: 'Prova'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (provaInstance.version > version) {
                provaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'prova.label', default: 'Prova')] as Object[],
                          "Another user has updated this Prova while you were editing")
                render(view: "edit", model: [provaInstance: provaInstance])
                return
            }
        }

        provaInstance.properties = params

        if (!provaInstance.save(flush: true)) {
            render(view: "edit", model: [provaInstance: provaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'prova.label', default: 'Prova'), provaInstance.id])
        redirect(action: "show", id: provaInstance.id)
    }

    def delete(Long id) {
        def provaInstance = Prova.get(id)
        if (!provaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'prova.label', default: 'Prova'), id])
            redirect(action: "list")
            return
        }

        try {
            provaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'prova.label', default: 'Prova'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'prova.label', default: 'Prova'), id])
            redirect(action: "show", id: id)
        }
    }
}
