namespace WinApp3
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            btnAnadir = new Button();
            lstNombres = new ListBox();
            label1 = new Label();
            txtNombre = new TextBox();
            btnCerrar = new Button();
            SuspendLayout();
            // 
            // btnAnadir
            // 
            btnAnadir.Anchor = AnchorStyles.Top | AnchorStyles.Right;
            btnAnadir.Location = new Point(230, 133);
            btnAnadir.Name = "btnAnadir";
            btnAnadir.Size = new Size(75, 23);
            btnAnadir.TabIndex = 0;
            btnAnadir.Text = "Añadir";
            btnAnadir.UseVisualStyleBackColor = true;
            btnAnadir.Click += btnAnadir_Click;
            // 
            // lstNombres
            // 
            lstNombres.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            lstNombres.FormattingEnabled = true;
            lstNombres.ItemHeight = 15;
            lstNombres.Location = new Point(94, 177);
            lstNombres.Name = "lstNombres";
            lstNombres.SelectionMode = SelectionMode.MultiExtended;
            lstNombres.Size = new Size(209, 169);
            lstNombres.TabIndex = 1;
            lstNombres.SelectedIndexChanged += lstNombres_SelectedIndexChanged;
            lstNombres.KeyDown += lstNombres_KeyDown;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(94, 107);
            label1.Name = "label1";
            label1.Size = new Size(51, 15);
            label1.TabIndex = 2;
            label1.Text = "Nombre";
            // 
            // txtNombre
            // 
            txtNombre.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right;
            txtNombre.Location = new Point(151, 104);
            txtNombre.Name = "txtNombre";
            txtNombre.Size = new Size(154, 23);
            txtNombre.TabIndex = 3;
            // 
            // btnCerrar
            // 
            btnCerrar.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;
            btnCerrar.Location = new Point(230, 370);
            btnCerrar.Name = "btnCerrar";
            btnCerrar.Size = new Size(75, 23);
            btnCerrar.TabIndex = 4;
            btnCerrar.Text = "Cerrar";
            btnCerrar.UseVisualStyleBackColor = true;
            btnCerrar.Click += btn_Cerrar_Click;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 528);
            Controls.Add(btnCerrar);
            Controls.Add(txtNombre);
            Controls.Add(label1);
            Controls.Add(lstNombres);
            Controls.Add(btnAnadir);
            Name = "Form1";
            Text = "Form1";
            Load += Form1_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button btnAnadir;
        private ListBox lstNombres;
        private Label label1;
        private TextBox txtNombre;
        private Button btnCerrar;
    }
}
