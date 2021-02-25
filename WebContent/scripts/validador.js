/**
 * 
 */

function validar(){
	let nome = frmContato.nome.value
	let fone = frmContato.fone.value
	let email = frmContato.email.value 
	
	if(nome === ""){
		alert('preencha o campo nome')
		frmContato.nome.focus()
			return false
			
	}else if (fone === ""){
		alert ('Preencha o campo fone')
		frmContato.fone.focus()
			return false
	}else if(email === ""){
		alert('preencha o caampo e-mail')
		frmContato.email.focus()
			return false
	}else{
		
	document.forms["frmContato"].submit()

	}
}
