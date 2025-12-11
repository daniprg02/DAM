using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TimerTema4NEW
{
    public partial class Form1 : Form
    {
        int segundos = 0;
        int minutos = 0;
        Color randomColor2;
        Color randomColor;
        //Crear variable para almacenar el color aleatorio

        String colorName;
        String colorName2;



        public Form1()
        {
            InitializeComponent();

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            segundos++;
            if (segundos == 60)
            {
                segundos = 0;
                minutos++;
            }
            //asignar los segundos y minutos a los labels
            lblSegundos.Text = segundos.ToString("00");
            lblMinutos.Text = minutos.ToString("00");

            //cambiar color cada 1 segundo



            if (segundos % 2 == 0)
            {
                Random rand2 = new Random();
                int r2 = rand2.Next(0, 256);
                int g2 = rand2.Next(0, 256);
                int b2 = rand2.Next(0, 256);
                randomColor2 = Color.FromArgb(r2, g2, b2);
                this.BackColor = randomColor2;
                colorName = randomColor2.Name;
            }
            else
            {
                Random rand = new Random();
                int r = rand.Next(0, 256);
                int g = rand.Next(0, 256);
                int b = rand.Next(0, 256);
                randomColor = Color.FromArgb(r, g, b);
                crono.BackColor = randomColor;
                colorName2 = randomColor.Name;
            }
            
            toolStripStatusLabel1.Text ="Color de fondo: " + colorName;
            toolStripStatusLabel1.BackColor = Color.WhiteSmoke;
            toolStripStatusLabel2.Text = "Color de fondo del cronómetro: " + colorName2;
            toolStripStatusLabel2.BackColor = Color.WhiteSmoke;


        }

        private void button2_Click(object sender, EventArgs e)
        {
            timer1.Stop();
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            timer1.Start();
        }

        private void minutos_Click(object sender, EventArgs e)
        {

        }

        private void segundos_Click(object sender, EventArgs e)
        {
            
        }

        private void btnRestar_Click(object sender, EventArgs e)
        {
            timer1.Stop();
            segundos = 0;
            minutos = 0;
            timer1.Start();
        }

        private void btnSalir_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void toolStripStatusLabel1_Click(object sender, EventArgs e)
        {
            
            

        }

        private void toolStripStatusLabel2_Click(object sender, EventArgs e)
        {
            
        }
    }
}
