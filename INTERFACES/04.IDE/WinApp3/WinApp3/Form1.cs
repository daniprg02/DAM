using System.Windows.Forms;

namespace WinApp3
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void btn_Cerrar_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.txtNombre.Text = "";
            this.lstNombres.Items.Clear();
            lstNombres.Items.Add(txtNombre.Text);
            // Asignar los valores para "anclar" los controles al formulario
            // El TextBox1 se anclará Arriba, Izquierda y Derecha
            this.txtNombre.Anchor = (AnchorStyles.Top |
           AnchorStyles.Left | AnchorStyles.Right);
            // El botón Añadir lo hará Arriba y a la derecha
            this.btnAnadir.Anchor = (AnchorStyles.Top |
            AnchorStyles.Right);
            // El listbox lo hará en los cuatro vértices
            this.lstNombres.Anchor = (AnchorStyles.Top |
           AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Bottom);
            // El botón cerrar sólo lo hará a la derecha y abajo
            this.btnCerrar.Anchor = (AnchorStyles.Right |
           AnchorStyles.Bottom);
        }

        private void btnAnadir_Click(object sender, EventArgs e)
        {
            this.lstNombres.Items.Add(this.txtNombre.Text);
        }

        private void lstNombres_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.txtNombre.Text = this.lstNombres.SelectedItem.ToString();
        }

        private void lstNombres_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Delete)
            {
                // Elemento en la posición i del listBox
                int i;
                // Borra las palabras seleccionadas del listBox
                for (i = lstNombres.SelectedItems.Count - 1; i >= 0; i--)
                {
                    lstNombres.Items.Remove(lstNombres.SelectedItems[i]);
                }
            }
        }
    }
}
