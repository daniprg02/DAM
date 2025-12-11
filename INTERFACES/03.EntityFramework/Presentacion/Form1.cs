using Presentacion.Views;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Presentacion
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if(new Negocio.Management.PruebaDeConexion().GetPruebaDeConexion())
 {
                label1.Text = "La conexión es correcta";
            }
            else
            {
                label1.Text = "Conexión con la BD no disponible";
            }

        }

        private void button2_Click(object sender, EventArgs e)
        {
            new FormConsultaProducts().ShowDialog();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }


    }
}
