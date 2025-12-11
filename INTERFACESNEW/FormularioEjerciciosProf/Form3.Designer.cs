namespace FormularioEjerciciosProf
{
    partial class Form3
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btnCerrar = new System.Windows.Forms.Button();
            this.btnTerminarApp = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // btnCerrar
            // 
            this.btnCerrar.Location = new System.Drawing.Point(93, 68);
            this.btnCerrar.Name = "btnCerrar";
            this.btnCerrar.Size = new System.Drawing.Size(108, 52);
            this.btnCerrar.TabIndex = 0;
            this.btnCerrar.Text = "Cerrar este formulario";
            this.btnCerrar.UseVisualStyleBackColor = true;
            this.btnCerrar.Click += new System.EventHandler(this.btnCerrar_Click);
            // 
            // btnTerminarApp
            // 
            this.btnTerminarApp.Location = new System.Drawing.Point(210, 299);
            this.btnTerminarApp.Name = "btnTerminarApp";
            this.btnTerminarApp.Size = new System.Drawing.Size(86, 35);
            this.btnTerminarApp.TabIndex = 1;
            this.btnTerminarApp.Text = "Terminar App";
            this.btnTerminarApp.UseVisualStyleBackColor = true;
            this.btnTerminarApp.Click += new System.EventHandler(this.btnTerminarApp_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(103, 33);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(87, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "FORMULARIO 3";
            // 
            // Form3
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(308, 346);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnTerminarApp);
            this.Controls.Add(this.btnCerrar);
            this.Name = "Form3";
            this.Text = "Form3";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnCerrar;
        private System.Windows.Forms.Button btnTerminarApp;
        private System.Windows.Forms.Label label1;
    }
}