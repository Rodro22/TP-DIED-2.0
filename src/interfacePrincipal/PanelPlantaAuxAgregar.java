package interfacePrincipal;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.List;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import modelo.Insumo;
import modelo.Planta;
import modelo.StockInsumo;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelPlantaAuxAgregar extends JPanel {
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField textMax;
	private JTextField textInicial;
	private JTable table;

	public DefaultTableModel modeloAux;
	private final String[] columnas = {"Id: ", "Nombre: ", "Costo: ", "Descripcion: ", "Posicion: "};
	public Planta plantaAux;


	/**
	 * Create the panel.
	 */
	public PanelPlantaAuxAgregar(List<Insumo> listaInsumos, List<StockInsumo> listaStockInsumos, List<Planta> listaPlantas) {
		
		
		setLayout(null);
		setBounds(100, 100, 700, 400);
		
		inicializar(listaInsumos);
		//ESTO ME PERMITE TRABAJAR CON EL JTABLE
		modeloAux = new DefaultTableModel();
		modeloAux.setColumnIdentifiers(columnas);
		Object obj[] = null;
		
		for (int i = 0; i < listaInsumos.size(); i++) {
		modeloAux.addRow(obj);
		Insumo getC = listaInsumos.get(i);
		modeloAux.setValueAt(getC.getId(), i, 0);
		modeloAux.setValueAt(getC.getNombre(), i, 1);
		modeloAux.setValueAt(getC.getCosto(), i, 2);
		modeloAux.setValueAt(getC.getDescripcion(), i, 3);
		modeloAux.setValueAt(i, i, 4);
		}
		
		
		
		
		
		
		
		
		
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 11, 46, 14);
		add(lblNombre);
		
		JLabel lblId = new JLabel("Id: ");
		lblId.setBounds(10, 53, 46, 14);
		add(lblId);
		
		JLabel lblAcopio = new JLabel("Acopio: ");
		lblAcopio.setBounds(10, 78, 46, 14);
		add(lblAcopio);
		
		txtId = new JTextField();
		txtId.setBounds(89, 47, 214, 20);
		add(txtId);
		txtId.setColumns(10);
		String unId = txtId.getText();
		
		txtNombre = new JTextField();
		txtNombre.setToolTipText("");
		txtNombre.setBounds(89, 8, 214, 20);
		add(txtNombre);
		txtNombre.setColumns(10);
		String unNombre = txtNombre.getText();
		
		JRadioButton rdbAcopio = new JRadioButton("Seleccionar");
		rdbAcopio.setBounds(129, 74, 109, 23);
		add(rdbAcopio);
		if(rdbAcopio.isSelected()==true) {
			String unAcopio = "true";
		}
		
		JButton btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 if (table.getSelectedRow() != -1) {

		        	 int i = (int) modeloAux.getValueAt(table.getSelectedRow(), 0);
		        	 
		        	 	Integer id = (int) modeloAux.getValueAt(table.getSelectedRow(), 0);
		        	 	String nombre = (String) modeloAux.getValueAt(table.getSelectedRow(), 1);
		        	 	Double costo = (Double) modeloAux.getValueAt(table.getSelectedRow(), 2);
		        	 	String descripcion = (String) modeloAux.getValueAt(table.getSelectedRow(), 3);        	 	
		        	 
		        	 	Insumo unInsumo = new Insumo (id, nombre, descripcion, costo);
		        	 	for(Planta p: listaPlantas) {
		        	 		if(p.idplanta == plantaAux.idplanta) {
		        	 			p.unStock.lista_insumo.add(unInsumo);
		        	 			System.out.println("Planta: " + p.nombre_planta + "Insumo: "+ p.unStock.lista_insumo);
		        	 		}
		        	 	}
		        	 	
		        	 	
		        	 	int disponible = Integer.parseInt(textInicial.getText());
		        	 	int max = Integer.parseInt(textMax.getText());
		        	 	
		        	 	StockInsumo stockInsumoAux = new StockInsumo (unInsumo, plantaAux.unStock, max, disponible);
		        	 	listaStockInsumos.add(stockInsumoAux);
		        	 	JOptionPane.showMessageDialog(null, "Se agrego el Insumo: "+ unInsumo.getNombre() + " a la planta " + plantaAux.nombre_planta, "Accion del sistema", JOptionPane.INFORMATION_MESSAGE);
		        	 	
		        	 	
		        } else {
		            JOptionPane.showMessageDialog(null, "Debe seleccionar una opcion primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
		        }
				
				 
				
				
			}
		});
		btnAgregar.setBounds(385, 307, 89, 23);
		add(btnAgregar);
		

		
		JButton btnCrear = new JButton("CREAR");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					if((txtId.getText().length() != 0) && (txtNombre.getText().length() != 0) ) {
						
						plantaAux = new Planta(Integer.parseInt(txtId.getText()), txtNombre.getText(), rdbAcopio.isSelected());	
						listaPlantas.add(plantaAux);
		        	 	JOptionPane.showMessageDialog(null, "Se agrego la planta " + plantaAux.nombre_planta, "Accion del sistema", JOptionPane.INFORMATION_MESSAGE);
		        	 	System.out.println(listaPlantas);
					}
			}
			});
	
		
		
		
		
		btnCrear.setBounds(368, 24, 89, 23);
		add(btnCrear);
		
		JLabel lblSeleccioneInsumo = new JLabel("Seleccione Insumo:");
		lblSeleccioneInsumo.setBounds(10, 113, 149, 28);
		add(lblSeleccioneInsumo);
		
		JLabel lblIngreseCantidadMaxima = new JLabel("Ingrese Cantidad Maxima: ");
		lblIngreseCantidadMaxima.setBounds(25, 280, 160, 14);
		add(lblIngreseCantidadMaxima);
		
		JLabel lblIngreseCantidadInicial = new JLabel("Ingrese Cantidad Inicial: ");
		lblIngreseCantidadInicial.setBounds(28, 311, 142, 14);
		add(lblIngreseCantidadInicial);
		
		textMax = new JTextField();
		textMax.setBounds(220, 277, 120, 20);
		add(textMax);
		textMax.setColumns(10);
		
		textInicial = new JTextField();
		textInicial.setBounds(220, 308, 120, 20);
		add(textInicial);
		textInicial.setColumns(10);
		setVisible(true);

	}
	
	public void inicializar(List<Insumo> listaInsumos) {

		table = new JTable(mostrarElementos2(listaInsumos));
//		table_1.setBounds(40, 171, 595, 150);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 143, 626, 122);
		add(scrollPane);
		
	}
	public DefaultTableModel mostrarElementos2(List<Insumo> listaInsumos) {
		modeloAux = new DefaultTableModel();
		modeloAux.setColumnIdentifiers(columnas);
		Object obj[] = null;
		
		for (int i = 0; i < listaInsumos.size(); i++) {
			modeloAux.addRow(obj);
			Insumo getC = listaInsumos.get(i);
			modeloAux.setValueAt(getC.getId(), i, 0);
			modeloAux.setValueAt(getC.getNombre(), i, 1);
			modeloAux.setValueAt(getC.costo, i, 2);
			modeloAux.setValueAt(getC.getDescripcion(), i, 3);
			modeloAux.setValueAt(i, i, 4);
		}
		return modeloAux;
	}
	
	
	
	
}
