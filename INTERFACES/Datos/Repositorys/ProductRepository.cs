using Datos.Infraestructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
namespace Datos.Repositorys
{
    public class ProductRepository
    {
        public List<Products> ObtenerProductos()
        {
            List<Products> products = new List<Products>(); 
            try
            {   
                //Abrir la BD
                using (var contexto = new NorthwindEntities()) { products = contexto.Products.ToList(); }
                return products;
            }
            catch (Exception) 
            {
                return products; 
            }
        }

        public List<Categories> ObtenerCategorias()
        {
            List<Categories> categories = new List<Categories>(); try
            { //Abrir la BD
              using (var contexto = new NorthwindEntities()) 
              {
                    categories = contexto.Categories.ToList();
                }
                return categories;
            }
            catch (Exception) {
                return categories; 
            }
        }

        public List<Suppliers> ObtenerProveedores()
        {
            List<Suppliers> proveedores = new List<Suppliers>();
            try
            { //Abrir la BD
                using (var contexto = new NorthwindEntities())
                {
                    proveedores = contexto.Suppliers.ToList();
                }
                return proveedores;
            }
            catch (Exception)
            {
                return proveedores;
            }
        }
    }
}