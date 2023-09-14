package service;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import models.Aluno;
import models.AlunoDAO;
import spark.Request;
import spark.Response;

public class AlunoService {

    private AlunoDAO alunoDAO = new AlunoDAO();
    private String form;

    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;

    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_DESCRICAO = 2;

    public AlunoService() {
        makeForm();
    }

    public void makeForm() {
        makeForm(FORM_INSERT, new Aluno(), FORM_ORDERBY_DESCRICAO);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Aluno(), orderBy);
    }

    public void makeForm(int tipo, Aluno aluno, int orderBy) {
		
		String nomeArquivo = "form.html";
		form = "";
		
		try{
			
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umAluno = "";
		if(tipo != FORM_INSERT) {
			
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/aluno/list/1\">Novo Produto</a></b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";
			umAluno += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			
			String action = "/aluno/";
			String name, descricao, buttonLabel;
			
			if (tipo == FORM_INSERT){
				
				action += "insert";
				name = "Inserir Aluno";
				descricao = "Matricula: ";
				buttonLabel = "Inserir";
				
			} 
			else {
				
				action += "update/" + aluno.getNome();
				name = "Atualizar Nome (Nome " + aluno.getNome() + ")";
				descricao = aluno.getMatricula();
				buttonLabel = "Atualizar";
			}
			
			umAluno += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Matricula: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			
			umAluno += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";
			umAluno += "\t</form>";		
		} 
		else if (tipo == FORM_DETAIL){
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (ID " + aluno.getNome() + ")</b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Matricula: "+ aluno.getMatricula() +"</td>";

			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";

			umAluno += "\t\t\t<td>&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		
		
		form = form.replaceFirst("<UM-PRODUTO>", umAluno);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Produtos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Aluno> alunos = alunoDAO.get();
		if (orderBy == FORM_ORDERBY_ID) {                 	
			alunos = alunoDAO.getOrderByNome();
		} 
		else if (orderBy == FORM_ORDERBY_DESCRICAO) {		
			alunos = alunoDAO.getOrderByMatricula();
		}
		

		int i = 0;
		String bgcolor = "";
		
		for (Aluno a : alunos) {
			
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + a.getNome() + "</td>\n" +
            		  "\t<td>" + a.getMatricula() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/aluno/" + a.getNome() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/aluno/update/" + a.getNome() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + a.getNome() + "', '" + a.getMatricula() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);				
	}
	
	
    public Object insert(Request request, Response response) {
        String nome = request.queryParams("nome");
        String matricula = request.queryParams("matricula");

        String resp = "";

        Aluno aluno = new Aluno(nome, matricula);

        if (AlunoDAO.inserirAluno(aluno)) {
            resp = "Aluno (" + matricula + ") cadastrado!";
            response.status(201); // 201 Created
        } else {
            resp = "Aluno (" + matricula + ") não cadastrado!";
            response.status(404); // 404 Not found
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

	
	public Object get(Request request, Response response) {
		
		String nome = request.queryParams("nome");
		Aluno aluno = (Aluno) alunoDAO.get(nome);
		
		if (aluno != null) {
			
			response.status(200); // success
			makeForm(FORM_DETAIL, aluno, FORM_ORDERBY_DESCRICAO);
        } 
		else {
			
            response.status(404); // 404 Not found
            String resp = "Aluno " + nome + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		
		String nome = request.queryParams("nome");	
		Aluno aluno = (Aluno) alunoDAO.get(nome);
		
		if (aluno != null) {
			
			response.status(200); // success
			makeForm(FORM_UPDATE, aluno, FORM_ORDERBY_DESCRICAO);
        } 
		else {
			
            response.status(404); // 404 Not found
            String resp = "Aluno " + nome + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
		
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
	    
		return form;
	}			
	
	public Object update(Request request, Response response) {
		
		String nome = request.queryParams("nome");
		Aluno aluno = (Aluno) alunoDAO.get(nome);
		
        String resp = "";       

        if (aluno != null) {
        	
        	aluno.setMatricula(request.queryParams("matricula"));
        	AlunoDAO.update(aluno);
        	
        	response.status(200); // success
            resp = "Produto (Nome " + aluno.getNome() + ") atualizado!";
        } 
        
        else {
            response.status(404); // 404 Not found
            resp = "Produto (ID \" + aluno.getNome() + \") não encontrado!";
        }
        
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
		
		String nome = request.queryParams("nome");
        Aluno aluno = (Aluno) alunoDAO.get(nome);
        
        String resp = "";       

        if (aluno != null) {
        	
            alunoDAO.delete(nome);
            response.status(200); // success
            resp = "Aluno (" + aluno + ") excluído!";
        } 
        
        else {
            response.status(404); // 404 Not found
            resp = "Aluno (" + aluno + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}