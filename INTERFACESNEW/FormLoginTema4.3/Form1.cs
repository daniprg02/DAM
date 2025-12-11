using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FormLoginTema4._3
{
    public partial class frmLogin : Form
    {
        public frmLogin()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            //Poner el txtNombre con el campo USUARIO 
            this.lblNombre.ForeColor = Color.Blue;
            this.lblContrasena.ForeColor = Color.Blue;

            this.lblNombre.Text = "USUARIO";
            this.lblContrasena.Text = "CONTRASEÑA";

            this.btnEnviar.ForeColor = Color.Blue;
            this.btnLimpiar.ForeColor = Color.Blue;
            this.btnSalir.ForeColor = Color.Blue;

            this.txtUsuario.BackColor = Color.LightGray;
            this.txtContra.BackColor = Color.LightGray;

            this.txtUsuario.ForeColor = Color.DarkBlue;
            this.txtContra.ForeColor = Color.DarkBlue;

            //Poner el txtContra para que oculte los caracteres
            this.txtContra.PasswordChar = '*';



        }

        private void lblNombre_Click(object sender, EventArgs e)
        {

        }

        private void lblContrasena_Click(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void btnEnviar_Click(object sender, EventArgs e)
        {
            string mensaje = "";

            if (txtUsuario.Text == "juan" && txtContra.Text == "vespa")
            {
                FrmDestino frm = new FrmDestino();
                this.Hide();
                frm.Show();
            }
            else
            {
                mensaje = "Acceso denegado, revisa los campos";
                MessageBox.Show(mensaje);
                this.txtUsuario.Clear();
                this.txtContra.Clear();
                this.txtUsuario.Focus();
            }
        }

        private void btnLimpiar_Click(object sender, EventArgs e)
        {
            this.txtUsuario.Clear();
            this.txtContra.Clear();
            //llevar el foco al txtUsuario
            this.txtUsuario.Focus();
        }

        private void btnSalir_Click(object sender, EventArgs e)
        {
            this.Close();
            Application.Exit();
        }

        private void btnSalir_KeyDown(object sender, KeyEventArgs e)
        {
            //Cuando se pulse la tecla ESCAPE se cierre la aplicacion
            if (e.KeyCode == Keys.Escape)
            {
                this.Close();
                Application.Exit();
            }
        }

        private void btnEnviar_KeyDown(object sender, KeyEventArgs e)
        {

        }
    }
}

