/**
 * Validação de Formulário
 * @author Lucas da Cunha da Silva
 */

 function validar(){
	let name  = frmContato.name.value;
	let fone  = frmContato.fone.value;
	
	if(name === ""){
		alert("Preencha o campo nome.");
		frm.name.focus();
		return false;
	}else if(fone === ""){
		alert("Preencha o campo fone.");
		frm.fone.focus();
		return false;
	}else{
		document.forms["frmContato"].submit();
	}
 }
 