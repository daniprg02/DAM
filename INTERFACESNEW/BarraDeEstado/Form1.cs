using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace BarraDeEstado
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void toolStripStatusLabel1_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            toolStripStatusLabel1.Text = DateTime.Now.ToString("HH:mm:ss");
        }

        private void toolStripStatusLabel2_Click(object sender, EventArgs e)
        {
            toolStripStatusLabel2.Text = "www.google.com";
            System.Diagnostics.Process.Start("http://www.google.com");
        }

        private void btnAnadir_Click(object sender, EventArgs e)
        {
            ToolStripStatusLabel nuevoLabel = new ToolStripStatusLabel();
            nuevoLabel.Text = DateTime.Now.ToString("HH:mm:ss");
            nuevoLabel.ForeColor = Color.Purple;
            nuevoLabel.Font = new Font("Arial", 10, FontStyle.Italic);
            nuevoLabel.BackColor = Color.Yellow;

            statusStrip1.Items.Add(nuevoLabel);

        }
    }
}
