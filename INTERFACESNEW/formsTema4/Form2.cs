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
    public partial class Form2 : Form
    {
        public Form2()
        {
            InitializeComponent();
            this.btnSalir.Text = "SALIR";
            this.lstLista.Anchor = (AnchorStyles.Top | AnchorStyles.Right | AnchorStyles.Bottom | AnchorStyles.Left);
            this.btnSalir.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
            this.btnLimpiar.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
            this.btnBuscar.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
            this.txtBarra.Anchor = (AnchorStyles.Left | AnchorStyles.Right);
            this.lblNombre.Anchor = (AnchorStyles.Top | AnchorStyles.Right);

        }

        private void btnSalir_Click(object sender, EventArgs e)
        {
            this.Close();
            
        }

        private void lstLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.lstLista.SelectedItem != null)
            {
                txtBarra.Text = this.lstLista.SelectedItem.ToString();
            }
        }

        private void btnLimpiar_Click(object sender, EventArgs e)
        {
            this.lstLista.Items.Clear();
        }

        private void txtBarra_TextChanged(object sender, EventArgs e)
        {
            //Cuando se escriba en la barra de búsqueda, se filtre la lista
            string filtro = this.txtBarra.Text.ToLower();

        }

        private void btnBuscar_Click(object sender, EventArgs e)
        {
            //Añadir elementos a la lista según el texto de la barra de búsqueda
            string textoBusqueda = this.txtBarra.Text;
            if (!string.IsNullOrEmpty(textoBusqueda))
            {
                this.lstLista.Items.Add(textoBusqueda);
                this.txtBarra.Clear();
            }

        }

        private void Form2_Load(object sender, EventArgs e)
        {
          

        }

        private void lstLista_PreviewKeyDown(object sender, PreviewKeyDownEventArgs e)
        {
           

        }

        private void lstLista_KeyDown(object sender, KeyEventArgs e)
        {
            {
                if (e.KeyCode == Keys.Delete)
                {
                    // Elemento en la posición i del listBox
                    int i;
                    // Borra las palabras seleccionadas del listBox
                    for (i = lstLista.SelectedItems.Count - 1; i >= 0; i--)
                    {
                        lstLista.Items.Remove(lstLista.SelectedItems[i]);
                    }
                }
            }
        }
    }
}
