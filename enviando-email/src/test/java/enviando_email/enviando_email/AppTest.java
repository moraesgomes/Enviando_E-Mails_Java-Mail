package enviando_email.enviando_email;

public class AppTest {
	
	
	@org.junit.Test
	public void testeEmail()throws Exception{
		    
		     StringBuilder builderTextoEmail = new StringBuilder();
		     builderTextoEmail.append("Olá! ,<br/><br/>");
		     builderTextoEmail.append("Você está recebendo acesso ao curso de java <br/></br>");
		     builderTextoEmail.append("<b>Login:</b> Admin<br/>");
		     builderTextoEmail.append("<b>Senha:</b> Admin<br/>");
		     
		     builderTextoEmail.append("<a target=\"_blank\" href=\"https://www.google.com\" style=\"color: #2525A7; "
		     		+ "padding: 14px 25px; text-align: center; text-decoration: none; display: "
		     		+ "inline-block; border-radius: 30px; font-size: 20px; font-family: courier;"
		     		+ " border: 3px solid green; background-color: #99DA39; font-weight: 900;\">Acessar Portal</a><br/><br/>");
		     builderTextoEmail.append("<span style=\"font-size:8px\">Ass>.: Haroldo Gomes </span>");
		
		     ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail(
		    		 "moraesgomes@gmail.com", 
		    		 "Haroldo Gomes", 
		    		 "testando e-mail com java",
		    		 builderTextoEmail.toString());
		     
			
			enviaEmail.enviarEmailAnexo(true);
			Thread.sleep(5000);
			
		
		
		
	}
    

    
}
