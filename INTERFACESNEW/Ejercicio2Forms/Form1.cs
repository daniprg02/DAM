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
            //Crear un nuevo formulario hijo
            Form hijo = new Form();
            //Establecer el formulario principal como MDI Container
            this.IsMdiContainer = true;
            //Establecer el formulario hijo como hijo del formulario principal
            hijo.MdiParent = this;
            //Mostrar el formulario hijo
            hijo.Show();
            //Organizar los formularios hijos en cascada
            this.LayoutMdi(MdiLayout.Cascade);

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
            //organizar los formularios hijos en mosaico
            this.LayoutMdi(MdiLayout.TileVertical);
        }

        private void iconosToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.LayoutMdi(MdiLayout.ArrangeIcons);
        }
    }
}
