using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FormTema4._2
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void btnRedirigir_Click(object sender, EventArgs e)
        {
            Form formulario2 = new Form();
            formulario2.MdiParent = this; 
            formulario2.Show();
        }

        private void btnCerrarForm_Click(object sender, EventArgs e)
        {
            this.ActiveMdiChild.Close();
        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
  

            //Mostrar los textos al cargar el formulario
            
            


        }

        private void btnPropiedades_Click(object sender, EventArgs e)
        {
            string propiedades = "";
            propiedades = this.listBox1.SelectedItem.ToString();

            switch (propiedades)
            {
                case "ArrangeIcons":
                    this.LayoutMdi(MdiLayout.ArrangeIcons);
                    break;
                    case "Cascade":
                    this.LayoutMdi(MdiLayout.Cascade);
                    break;
                    case "TileHorizontal":
                    this.LayoutMdi(MdiLayout.TileHorizontal);
                    break;
                    case "TileVertical":
                    this.LayoutMdi(MdiLayout.TileVertical);
                    break;  
                    default:
                    MessageBox.Show("Seleccione una propiedad válida");
                    break;
            }

            Form formHijo = new Form();
            formHijo.MdiParent = this;
            formHijo.Show();

        }

        private void btnCerrar_Click(object sender, EventArgs e)
        {
            this.ActiveMdiChild.Close();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}
