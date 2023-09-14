package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends DAO{

	public AlunoDAO(){
		
		super();
		conectar();
	}
	
	public void finalizar() {
		
		close();
	}
	
	/*----------INSERINDO ALUNO------------------------*/
	public static boolean inserirAluno(Aluno aluno) {
		
		boolean status = false;
		try {
			
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO Aluno (nome, matricula) " + "VALUES ('"+aluno.getNome()+ "', '" + aluno.getMatricula() + "');";
			
			System.out.println(sql);
			st.executeUpdate(sql);
			
			st.close();
			status = true;
			
		} catch (SQLException u) {
			
			throw new RuntimeException(u);
		}
		
		return status;
		}
	/*------------------------------------------------*/
	
	/*--------IMPRIMINDO ALUNOS-----------------------*/
	
	public static List<Aluno> getAll(){
		
		List<Aluno> alunos = new ArrayList<>();
		
		try {
			
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT * FROM aluno";
			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				Aluno aluno = new Aluno(rs.getString("nome"), rs.getString("matricula"));
				alunos.add(aluno);
			}
			
			st.close();
			} 
			catch(Exception e) {
				
				System.out.println(e.getMessage());
		}
		return alunos;
	}
	
	/*------------------------------------------------*/
	
	/*---------------RETORNANDO ATRIBUTOS-------------*/
	
		public List<Aluno> get(){
			return get("");
		}
	
		
		public List<Aluno> getOrderByNome(){
			return get("nome");
		}
		
		public List<Aluno> getOrderByMatricula(){
			return get("matricula");
		}
		
		
		public List<Aluno> get(String orderBy) {
			
			List<Aluno> alunos = new ArrayList<Aluno>();
			
			try {
				
				Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				String sql = "SELECT * FROM aluno" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
				
				ResultSet rs = st.executeQuery(sql);	           
		        while(rs.next()) {	
		        	
		        	Aluno a = new Aluno (rs.getString("nome"), rs.getString("matricula"));
		        	alunos.add(a);
		        }
		        st.close();
		        
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			return alunos;
		}
	
	/*------------------------------------------------*/
	
	/*---------------EXCLUINDO UM ALUNO---------------*/

	public boolean delete(String matricula) {
		
		boolean status = false;
		
			try {
				
				Statement st = conexao.createStatement();

				String sql = "DELETE FROM aluno WHERE matricula = " + matricula;
				System.out.println(sql);
				
				st.executeUpdate(sql);
				st.close();
				status = true;
				
			} 
			catch (SQLException u) {
				throw new RuntimeException(u);
			}
			
			return status;
		}
	
	/*------------------------------------------------*/
	
	/*-------------RETORNANDO POR MATRICULA-----------*/
	
	public static Aluno getByMatricula(int matricula) {
		
		Aluno aluno = null;
			try {
				
				Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				String sql = "SELECT * FROM aluno where matricula = " + matricula;
				
				System.out.println(sql);
				ResultSet rs = st.executeQuery(sql);
				
				if (rs.next()) {
					aluno = new Aluno(rs.getString("nome"), rs.getString("matricula"));
				}
				
				st.close();
			} 
			catch (Exception e) {
				
				System.err.println(e.getMessage());
			}
		return aluno;
	}
	
	/*------------------------------------------------*/
	
	/*-----------------ATUALIZANDO ALUNO--------------*/
	
	public static boolean update(Aluno aluno) {
		
		boolean status = false;
		
			try {
				
			Statement st = conexao.createStatement();
			
			String sql = "UPDATE aluno SET nome = '" + aluno.getNome() + "', matricula = '"
			+ aluno.getMatricula() + "'" + " WHERE matricula = " + aluno.getMatricula();
			
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} 
		catch (SQLException u) {
			
			throw new RuntimeException(u);
		}
		return status;
	}
	
	/*------------------------------------------------*/
}
