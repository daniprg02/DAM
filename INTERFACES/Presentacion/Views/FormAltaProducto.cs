using System;
using System.Linq;
using System.Windows.Forms;

namespace Presentacion.Views
{
    public partial class FormAltaProducto : Form
    {
        public FormAltaProducto()
        {
            InitializeComponent();
        }

        private void FormAltaProducto_Load(object sender, EventArgs e)
        {
            // Crear el gestor de negocio
            var gestor = new Negocio.Management.ProductManagement();

            // Obtener las categorías
            var categorias = gestor.ObtenerCategorias();

            // Cargar datos en el ComboBox (usa el nombre real del control)
            comboBox2.DataSource = categorias;
            comboBox2.DisplayMember = "CategoryName";
            comboBox2.ValueMember = "CategoryID";
        }

    }
}
