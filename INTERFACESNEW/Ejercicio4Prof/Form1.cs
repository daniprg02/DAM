using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Ejercicio4Prof
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
           
            this.Text = txtIntroducir.Text;
            //Limpiar el TextBox
            txtIntroducir.Clear();
            //Ocultar el TextBox y el botón
            txtIntroducir.Hide();
            lblTitulo.Hide();
            btnCambiar.Hide();

            this.introducirToolStripMenuItem.Text = "Introducir nuevo título";


        }

        private void introducirToolStripMenuItem_Click(object sender, EventArgs e)
        {
            

            txtIntroducir.Show();
            lblTitulo.Show();



        }

        private void txtIntroducir_TextChanged(object sender, EventArgs e)
        {
            if(txtIntroducir.Text != "")
            {
                btnCambiar.Show();
            }
            else
            {
                btnCambiar.Hide();
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            txtIntroducir.Hide();
            btnCambiar.Hide();
            lblTitulo.Hide();

        }

        private void azulToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.BackColor = Color.Blue;
        }

        private void rojoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.BackColor = Color.Red;
        }

        private void verdeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.BackColor = Color.Green;
        }

        private void amarilloToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.BackColor = Color.Yellow;
        }

        private void marrónToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.BackColor = Color.SaddleBrown;
        }

        private void negroToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.BackColor = Color.Black;
        }

        private void colorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.BackColor = Color.WhiteSmoke;
        }
    }
}
