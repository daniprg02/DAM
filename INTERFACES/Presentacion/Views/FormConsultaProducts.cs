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
    public partial class FormConsultaProducts : Form
    {
        public FormConsultaProducts()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            dataGridView1.DataSource = new Negocio.Management.ProductManagement().ObtenerProductos();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            FormAltaProducto formAlta = new FormAltaProducto();
            formAlta.ShowDialog();
        }
    }
}
