using Negocio.EntitiesDTO;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Presentacion.Views
{
    public partial class FormAltaLibro : Form
    {
        public FormAltaLibro()
        {
            InitializeComponent();
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            
        }

        private void FormAltaLibro_Load(object sender, EventArgs e)
        {
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button3_Click(object sender, EventArgs e)
        {

        }

        private void FormAltaLibro_Load_1(object sender, EventArgs e)
        {
            comCategoria.DataSource = new Negocio.Management.ProductManagement().ObtenerCategorias().Select(x => x.CategoryName).ToList();
            comboBox2.DataSource = new Negocio.Management.ProductManagement().ObtenerProveedores().Select(x => x.SuppliersName).ToList();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnCerrar_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void comboBox3_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void btnAceptar_Click(object sender, EventArgs e)
        {
            ProductsDTO productoDTO = new ProductsDTO();
            productoDTO.ProductName = txtNombre.Text;
            productoDTO.SupplierID = new Negocio.Management.ProductManagement().ObtenerProveedores().Where(x => x.SuppliersName == comboBox2.Text).First().SupplierID;
            productoDTO.CategoryID = new Negocio.Management.ProductManagement().ObtenerCategorias().Where(x => x.CategoryName == cbCategoria.Text).First().CategoryID;
            productoDTO.QuantityPerUnit = txtCantidad.Text;
            productoDTO.UnitPrice = Convert.ToDecimal(txtPrecio.Text);

            new Negocio.Management.ProductManagement().AltaProducto(productoDTO);

            this.Close();
        }
    }
}
