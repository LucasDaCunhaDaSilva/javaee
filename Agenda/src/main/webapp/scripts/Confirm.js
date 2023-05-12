
/**
 * Confirmar a Exclusão de elementos
 * @author Lucas da Cunha da Silva
 */


 function confirmDelete(nome , id){
	 let result = confirm("Confirmar exclusão de "+nome+"?");
	
	 if(result === true){
		window.location.href = "delete?id="+id;
	 }
	 
 }
 