using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace formsTema4
{
    public partial class frmFormulario : Form
    {
        public frmFormulario()
        {
            InitializeComponent();
        }

        private void lblInfo_Click(object sender, EventArgs e)
        {

        }

        private void frmFormulario_Load(object sender, EventArgs e)
        {
            
        }

        private void btnSalir_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnAceptar_Click(object sender, EventArgs e)
        {
            if(txtInfo.Text!="")
            {
                MessageBox.Show("Hola " + txtInfo.Text);
            }
            else
            {
                MessageBox.Show("El cuadro de texto está vacío. Por favor, ingrese algún texto.");
            }
        }

        private void txtInfo_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
