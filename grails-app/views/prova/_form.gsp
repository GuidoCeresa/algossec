<%@ page import="it.algos.algossec.Prova" %>



<div class="fieldcontain ${hasErrors(bean: provaInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="prova.nome.label" default="Nome" />
		
	</label>
	<g:textField name="nome" value="${provaInstance?.nome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: provaInstance, field: 'numero', 'error')} required">
	<label for="numero">
		<g:message code="prova.numero.label" default="Numero" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numero" type="number" value="${provaInstance.numero}" required=""/>
</div>

