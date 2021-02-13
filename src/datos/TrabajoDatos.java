package datos;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class TrabajoDatos extends JFrame implements ActionListener {
	//Enunciado.
	private JLabel TextAlumn,TextAsign,TextNota,labelResultado,labelModif;
	//Contenedor.
	private JPanel contentPane;
	//Campos de Textos.
	private JTextField alumno,asignatura,nota,intro;
	//Botones.
	private JButton botonenvio,botonConsulta,botonBorrar;
	//Constructor.
	public TrabajoDatos() {
		
		contentPane = new JPanel();//Inicializo el contenedor//
		contentPane.setLayout(null);//Desactiva automatica colocacion.
		setContentPane(contentPane);//Permite configuración de contentPane//
	
	//ENUNCIADO:
		
		 //ALUMNO
		TextAlumn=new JLabel("Ingresar nombre del Alumno");//Inicializo y establezco el texto a aparecer//
		TextAlumn.setBounds(60,30,168,40);//Establece el lugar donde estará dentro del contenedor además de poder configurar el ancho y la altura//
		contentPane.add(TextAlumn);//Añade al Contenedor//
		 //ASIGNATURA.
		TextAsign=new JLabel("Ingresar Asignatura");//Inicializo y establezco el texto a aparecer//
		TextAsign.setBounds(60,90,150,40);//Establece el lugar donde estará dentro del contenedor además de poder configurar el ancho y la altura//
		contentPane.add(TextAsign);//Añade al Contenedor//
		 //NOTA.
		TextNota=new JLabel("Ingresar Nota del Alumno");//Inicializo y establezco el texto a aparecer//
		TextNota.setBounds(60,170,150,40);//Establece el lugar donde estará dentro del contenedor además de poder configurar el ancho y la altura//
		contentPane.add(TextNota);//Añade al Contenedor//
		 //RESULTADO
		labelResultado=new JLabel("Resultado");//Inicializo y establezco el texto a aparecer//
		labelResultado.setBounds(650,120,229,40);//Establece el lugar donde estará dentro del contenedor además de poder configurar el ancho y la altura//
		contentPane.add(labelResultado);//Añade al Contenedor//
		 //MODIFICACION O ELIMINACIÓN
		labelModif=new JLabel("Ingresar dato a modificar o eliminar");//Inicializo y establezco el texto a aparecer//
		labelModif.setBounds(60,250,229,40);//Establece el lugar donde estará dentro del contenedor además de poder configurar el ancho y la altura//
		contentPane.add(labelModif);//Añade al Contenedor//
		
	//INGRESOS.
		
		 //ALUMNO
		alumno=new JTextField();
		alumno.setBounds(320,30,168,40);
		alumno.setColumns(5);
		contentPane.add(alumno);
		 //ASIGNATURA.
		asignatura=new JTextField();
		asignatura.setBounds(320,90,168,40);
		asignatura.setColumns(5);
		contentPane.add(asignatura);
		 //NOTA.
		nota=new JTextField();
		nota.setBounds(320,170,200,40);
		nota.setColumns(5);
		contentPane.add(nota);
		 //
		intro=new JTextField();
		intro.setBounds(320,250,200,40);
		intro.setColumns(5);
		contentPane.add(intro);
	//BOTONES.
		botonenvio=new JButton("Registrar");
		botonenvio.setBounds(320,300,150,40);
		botonenvio.addActionListener(this);
		contentPane.add(botonenvio);
		
		botonConsulta=new JButton("Consulta");
		botonConsulta.setBounds(140,300,150,40);
		botonConsulta.addActionListener(this);
		contentPane.add(botonConsulta);
		
		botonBorrar=new JButton("Borrar Registro");
		botonBorrar.setBounds(500,300,150,40);
		botonBorrar.addActionListener(this);
		contentPane.add(botonBorrar);
	//SQL.
		cargarDriver();
		
	}
	//METODO que permite relacionarse con la base de datos//
	public void cargarDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception ex){
			setTitle(ex.toString());
		}
	}
	//FUNCION BOTON.
	public void actionPerformed(ActionEvent e) {
		//Establece el envio de informacion tras pulsarlo.
		if (e.getSource()==botonenvio) {
			labelResultado.setText("");
			try {
			Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3307/bd1","root","806090");//IMPORTANTE:Al momento de iniciar o probar en otro PC se debe cambiar la dirrecion del localhost,y usuario y contraseña del local host para que no se produzca un error evitable.
			Statement comando=conexion.createStatement();//Establece conexión//
			comando.executeUpdate("insert into datos(alumno,asignatura,nota) values ('"+alumno.getText()+"','"+asignatura.getText()+"',"+nota.getText()+")");//Establece las ordenes para la base de datos.
			conexion.close();//Cierra la conexión//
			labelResultado.setText("Se Registraron Datos");//Enunciado que parecerá indicando que la orden ha sido dado.//
			alumno.setText("");//Limpia cualquier antigua orden dada a esta.//
			asignatura.setText("");//Limpia cualquier antigua orden dada a esta.//
			nota.setText("");//Limpia cualquier antigua orden dada a esta.//
		}   
			catch(SQLException ex){//En caso de fallo con SQL//
			  setTitle(ex.toString());
			  }
		}
		
		if (e.getSource()==botonConsulta) {//Establece el regreso de informacion tras pulsarlo.
			labelResultado.setText("");//Limpia cualquier antigua orden dada a esta.//
			alumno.setText("");//Limpia cualquier antigua orden dada a esta.//
			asignatura.setText("");//Limpia cualquier antigua orden dada a esta.//
			nota.setText("");//Limpia cualquier antigua orden dada a esta.//
			try {
				Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3307/bd1","root","806090");//IMPORTANTE:Al momento de iniciar o probar en otro PC se debe cambiar la dirrecion del localhost,y usuario y contraseña del local host para que no se produzca un error evitable.
				Statement comando=conexion.createStatement();//Establece conexión//
				ResultSet registro = comando.executeQuery("select alumno,asignatura,nota from datos where alumno='"+intro.getText()+"'");//Establece las ordenes para la base de datos.
				if (registro.next()==true) {
					alumno.setText(registro.getString("alumno"));//Donde aparecerá la información devuelta.//
					asignatura.setText(registro.getString("asignatura"));//Donde aparecerá la información devuelta.//
					nota.setText(registro.getString("nota"));//Donde aparecerá la información devuelta.//
				}else {
					labelResultado.setText("No hay alumno con ese nombre");//Enunciado que parecerá indicando que la orden ha sido dado.//
					
				}
				conexion.close();
			} catch(SQLException ex){//En caso de fallo con SQL//
				setTitle(ex.toString());
			}
			
		}
		if (e.getSource()==botonBorrar) {//Establece la función del boton borrar//
			try {
				Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3307/bd1","root","806090");//IMPORTANTE:Al momento de iniciar o probar en otro PC se debe cambiar la dirrecion del localhost,y usuario y contraseña del local host para que no se produzca un error evitable.
				Statement comando=conexion.createStatement();//Establece conexión//
				comando.executeUpdate("delete from datos where alumno='"+intro.getText()+"'");//Establece las ordenes para la base de datos.
				conexion.close();//Cierra conexión//
				labelResultado.setText("Se Borraron Datos");//Enunciado que parecerá indicando que la orden ha sido dado.//
				
			}catch(SQLException ex) {//En caso de fallo con SQL//
				setTitle(ex.toString());
			}
		}
		
	}
	
	public static void main(String[] args) {//Basicamente menu principal que establece ordenes como direccion y visibilidad.//
		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				TrabajoDatos Trabajo=new TrabajoDatos();
				Trabajo.setBounds(500,250,1000,400);//La dirección en la cual se establecerá la página al ejecutarla.//
				Trabajo.setResizable(false);//Con esto activado permite redimensionar la ventana ,pero nosotros al darle unos parametros especificos ,lo desactivamos.//
				Trabajo.setVisible(true);//Permiteme la visibilizacion//
				Trabajo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Permiteme que nosotros realizemos el cierre de la aplicación//. 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	}
}
