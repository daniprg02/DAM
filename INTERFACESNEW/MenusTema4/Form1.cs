using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MenusTema4

{
    public partial class Form1 : Form

    {
        int contador = 0;
        bool mostrarListBox = false;
        public Form1()
        {
            InitializeComponent();
        }

        private void cerrarToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void fuenteToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void lblContador_Click(object sender, EventArgs e)
        {
            //Cuando se de clic en el label, se abre la ventana de dialogo para cambiar el color de la fuente
            ColorDialog colorDialog = new ColorDialog();
            if (colorDialog.ShowDialog() == DialogResult.OK)
            {
                lblContador.ForeColor = colorDialog.Color;
            }

        }

        private void rojoOscuroToolStripMenuItem_Click(object sender, EventArgs e)
        {
            
            lblContador.ForeColor = Color.DarkRed;

        }

        private void azulOscuroToolStripMenuItem_Click(object sender, EventArgs e)
        {
            lblContador.ForeColor = Color.DarkBlue;
        }

        private void negroToolStripMenuItem_Click(object sender, EventArgs e)
        {
            lblContador.ForeColor = Color.Black;   
        }

        private void moradoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            lblContador.ForeColor = Color.Purple;
        }

        private void chillerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            
            lblContador.Font = new Font("Chiller", lblContador.Font.Size);

        }

        private void cascadiaMonoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            lblContador.Font = new Font("Cascadia Mono", lblContador.Font.Size);
        }

        private void rockwellToolStripMenuItem_Click(object sender, EventArgs e)
        {
            lblContador.Font = new Font("Rockwell", lblContador.Font.Size);
        }

        private void guardarToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Cuando se le di clic en guardar, se abre la ventana de dialogo para guardar el archivo
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            if (saveFileDialog.ShowDialog() == DialogResult.OK)
            {
                //Guardar el contenido del label en un archivo de texto
                System.IO.File.WriteAllText(saveFileDialog.FileName, lblContador.Text);
            }
        }

        private void abrirToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Cuando se le di clic en abrir, se abre la ventana de dialogo para abrir un archivo
            OpenFileDialog openFileDialog = new OpenFileDialog();

        }

        private void imágenesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Multiselect = true;
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                //Cargar las imagenes seleccionadas en el ImageList
                foreach (string file in openFileDialog.FileNames)
                {
                    imgMotos.Images.Add(Image.FromFile(file));
                }
            }
        }

        private void pb1_Click(object sender, EventArgs e)
        {

        }

        private void btnDerecha_Click(object sender, EventArgs e)
        {

        }

        private void btnIzquierda_Click(object sender, EventArgs e)
        {

        }

        private void irAForm2ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Crear una instancia del formulario 2
            Form2 form2 = new Form2();
            //Mostrar el formulario 2
            form2.Show();
            //Ocultar el formulario 1
            this.Hide();

        }

        private void irAFormularioExternoToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            listBox1.Items.AddRange(new string[] { "Rojo", "Azul", "Verde", "Negro", "Morado" });
            //Cuando se cargue el formulario, ocultar el listbox1
            listBox1.Visible = false;

        }

        private void nuevoMensajeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //mostrar un message box con un mensaje personalizado
            MessageBox.Show("Error en el programa");
            //Si el usuario da clic en aceptar, se cambia el texto del label
            lblContador.Text = "Era broma";

        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //Si se selecciona un color en el listbox, se cambia el color del listbox1

            string colorSeleccionado = listBox1.SelectedItem.ToString();
            switch (colorSeleccionado)
            {
                case "Rojo":
                    listBox1.BackColor = Color.Red;
                    break;
                case "Azul":
                    listBox1.BackColor = Color.Blue;
                    break;
                case "Verde":
                    listBox1.BackColor = Color.Green;
                    break;
                case "Negro":
                    listBox1.BackColor = Color.Black;
                    break;
                case "Morado":
                    listBox1.BackColor = Color.Purple;
                    break;
            }

            }

        private void mostrarListBoxToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Cuando se le de clic en el menu, se muestra el listbox1
            if (!mostrarListBox)
            {
                listBox1.Visible = true;
                mostrarListBox = true;
                //Cambiar el texto del menu a ocultar listbox
                mostrarListBoxToolStripMenuItem.Text = "Ocultar ListBox";
            }
            else
            {
                listBox1.Visible = false;
                mostrarListBox = false;
                //Cambiar el texto del menu a mostrar listbox
                mostrarListBoxToolStripMenuItem.Text = "Mostrar ListBox";
            }
        }
    }
}
