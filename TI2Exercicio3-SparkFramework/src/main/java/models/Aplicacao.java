package models;

import service.AlunoService;

import static spark.Spark.*;


public class Aplicacao {
	
	private static AlunoService alunoService = new AlunoService();
	
	public static void main(String [] args) {
		
		
	        port(6789);
	        
	        staticFiles.location("/src");
	        
	        post("/aluno/insert", (request, response) -> alunoService.insert(request, response));

	        get("/aluno/:id", (request, response) -> alunoService.get(request, response));
	        
	        get("/aluno/list/:orderby", (request, response) -> alunoService.get(request, response));

	        get("/aluno/update/:id", (request, response) -> alunoService.update(request, response));
	        
	        post("/aluno/update/:id", (request, response) -> alunoService.update(request, response));
	           
	        get("/aluno/delete/:id", (request, response) -> alunoService.delete(request, response));
	}
}
