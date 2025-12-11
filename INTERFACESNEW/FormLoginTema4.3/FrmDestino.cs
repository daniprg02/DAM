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
    public partial class FrmDestino : Form
    {
        public FrmDestino()
        {
            InitializeComponent();
        }

        private void btnVolver_Click(object sender, EventArgs e)
        {
            //volver al formulario de origen
            this.Close();
            //Volver a frmLogin

            frmLogin frm = new frmLogin();
            frm.Show();

        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            String elemento = listBox1.SelectedItem.ToString();
            
            //Cambiar el color de fondo formulario segun el elemento seleccionado
            switch (elemento)
            {
                case "AZUL OSCURO":
                    this.BackColor = Color.DarkBlue;
                    break;
                case "ROJO OSCURO":
                    this.BackColor = Color.DarkRed;
                    break;
                case "AMARILLO OSCURO":
                    this.BackColor = Color.DarkKhaki;
                    break;
                case "NEGRO":
                    this.BackColor = Color.Black;
                    break;

            }

        }
    
    }
}
