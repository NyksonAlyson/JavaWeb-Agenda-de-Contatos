/**
 * 
 */

function confirmar(idcon){
	let resposta = confirm("confirma a exclusão deste arquivo?")
	if(resposta === true){
		//alert(idcon)
		window.location.href="delete?idcon="+ idcon
			
		
	}
}