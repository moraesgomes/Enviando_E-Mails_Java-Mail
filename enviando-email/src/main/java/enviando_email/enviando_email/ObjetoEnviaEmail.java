package enviando_email.enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "moraeshmgomes@gmail.com";
	private String senha = "uievnggaovehrofr";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaEmail(String listaDestinatario,String nomeRemetente,String assuntoEmail, String textoEmail) {
		
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHtml) throws Exception {

		Properties prop = new Properties();
		prop.put("mail.smtp.ssl.trust", "*");
		prop.put("mail.smtp.auth", "true");/* Autorização */
		prop.put("mail.smtp.starttls", "true");/* Autenticação */
		prop.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor gmail */
		prop.put("mail.smtp.port", "465");/* porta do servidor */
		prop.put("mail.smtp.socketFactory.port", "465");/* Especifica a porta a ser conectada */
		prop.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");/* Classe Socketde conexão ao smtp */

		Session session = Session.getDefaultInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(userName, senha);
			}
		});

		Address[] toUser = InternetAddress
				.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));/* Quem esta enviando */
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assuntoEmail);
		if(envioHtml) {
			
			message.setContent(textoEmail, "text/html;charset=utf-8");
		}else {
			message.setText(textoEmail);
		}
		
		

		Transport.send(message);

	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception {

		Properties prop = new Properties();
		prop.put("mail.smtp.ssl.trust", "*");
		prop.put("mail.smtp.auth", "true");/* Autorização */
		prop.put("mail.smtp.starttls", "true");/* Autenticação */
		prop.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor gmail */
		prop.put("mail.smtp.port", "465");/* porta do servidor */
		prop.put("mail.smtp.socketFactory.port", "465");/* Especifica a porta a ser conectada */
		prop.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");/* Classe Socketde conexão ao smtp */

		Session session = Session.getDefaultInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(userName, senha);
			}
		});

		Address[] toUser = InternetAddress
				.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));/* Quem esta enviando */
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assuntoEmail);
		
		/*Parte 1 do e-mail que é o texto e a descrição do e-mail */
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
				
		if(envioHtml) {
			
			corpoEmail.setContent(textoEmail, "text/html;charset=utf-8");
		}else {
			corpoEmail.setText(textoEmail);
		}
		
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorPDF());/*Certificado*/
		arquivos.add(simuladorPDF());/*Nota Fiscal*/
		arquivos.add(simuladorPDF());/*imagem*/
		arquivos.add(simuladorPDF());/*documento de texo*/
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);
		
		int index = 0;
		
		for (FileInputStream fileInputStream : arquivos) {
			
			/*Parte 2 do e-mail que são os anexos em pdf */
			MimeBodyPart anexoEmail = new MimeBodyPart();
			/*Onde é passado o simuladorDePdf , passa-se o arquivo gravado no banco de dados*/
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "aplication/pdf")) );
			anexoEmail.setFileName("anexoemail" + index+ ".pdf" );
			multipart.addBodyPart(anexoEmail);
			
			index++;
			
		}
		
		
		
		
		message.setContent(multipart);

		Transport.send(message);

	}
	/** Esse método simula o PDF ou qualquer arquivo que possa ser enviado por anexo no email
	 *  Pode pegar o arquivo no banco de dados base64 ,byte[], Stream de arquivos.
	 *  Pode estar numa banco de dados ou uma pasta.
	 *  
	 *  Retorna um pdf em branco  o texto do paragrafo de exemplo.
	 * */
	private FileInputStream simuladorPDF() throws Exception{
		
		Document document  = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteúdo do PDF com java mail , texto do pdf"));
		document.close();
		
		return  new FileInputStream(file);
		
	}
}
