namespace FormularioEjerciciosProf
{
    partial class Form1
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.btnSegundo = new System.Windows.Forms.Button();
            this.btnTercer = new System.Windows.Forms.Button();
            this.btnTerminar = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btnTercer);
            this.groupBox1.Controls.Add(this.btnSegundo);
            this.groupBox1.Location = new System.Drawing.Point(85, 114);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(287, 215);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            // 
            // btnSegundo
            // 
            this.btnSegundo.Location = new System.Drawing.Point(56, 47);
            this.btnSegundo.Name = "btnSegundo";
            this.btnSegundo.Size = new System.Drawing.Size(172, 49);
            this.btnSegundo.TabIndex = 0;
            this.btnSegundo.Text = "Mostrar segundo formulario";
            this.btnSegundo.UseVisualStyleBackColor = true;
            this.btnSegundo.Click += new System.EventHandler(this.btnSegundo_Click);
            // 
            // btnTercer
            // 
            this.btnTercer.Location = new System.Drawing.Point(56, 124);
            this.btnTercer.Name = "btnTercer";
            this.btnTercer.Size = new System.Drawing.Size(172, 49);
            this.btnTercer.TabIndex = 1;
            this.btnTercer.Text = "Mostrar tercer formulario";
            this.btnTercer.UseVisualStyleBackColor = true;
            this.btnTercer.Click += new System.EventHandler(this.btnTercer_Click);
            // 
            // btnTerminar
            // 
            this.btnTerminar.Location = new System.Drawing.Point(291, 335);
            this.btnTerminar.Name = "btnTerminar";
            this.btnTerminar.Size = new System.Drawing.Size(81, 34);
            this.btnTerminar.TabIndex = 2;
            this.btnTerminar.Text = "Terminar";
            this.btnTerminar.UseVisualStyleBackColor = true;
            this.btnTerminar.Click += new System.EventHandler(this.btnTerminar_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(432, 518);
            this.Controls.Add(this.btnTerminar);
            this.Controls.Add(this.groupBox1);
            this.Name = "Form1";
            this.Text = "Form1";
            this.groupBox1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button btnTercer;
        private System.Windows.Forms.Button btnSegundo;
        private System.Windows.Forms.Button btnTerminar;
    }
}

