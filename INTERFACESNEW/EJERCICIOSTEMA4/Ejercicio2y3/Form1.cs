using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Ejercicio2Forms
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void organizarMDIsToolStripMenuItem_Click(object sender, EventArgs e)
        {
          

        }

        private void horizontalToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Organizar los formularios hijos en horizontal
            this.LayoutMdi(MdiLayout.TileHorizontal);

        }

        private void verticalToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.LayoutMdi(MdiLayout.TileVertical);

        }

        private void mosaicoToolStripMenuItem_Click(object sender, EventArgs e)
        {
           
            this.LayoutMdi(MdiLayout.Cascade);
        }

        private void iconosToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.LayoutMdi(MdiLayout.ArrangeIcons);
        }

        private void nuevoFormularioToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Crear una instancia del formulario NuevoForm
            NuevoForm nuevoFormulario = new NuevoForm();
            //Establecer el formulario padre
            nuevoFormulario.MdiParent = this;
            //Mostrar el formulario
            nuevoFormulario.Show();


        }
    }
}
