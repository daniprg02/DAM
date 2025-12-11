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

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            FormAltaLibro formAlta = new FormAltaLibro();
            formAlta.ShowDialog();
            dataGridView1.DataSource = new Negocio.Management.ProductManagement().ObtenerProductos();
        }
    }
}
