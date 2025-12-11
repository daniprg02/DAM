using Datos.Infrastructure;
using Negocio.EntitiesDTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Negocio.Management
{
    public class ProductManagement
    {
        public List<ProductsDTO> ObtenerProductos()
        {
            List<Products> productos = new
           Datos.Repositories.ProductRepository().ObtenerProductos();
            List<ProductsDTO> productsDTOs = new List<ProductsDTO>();
            //Hacemos el Cast
            foreach (var item in productos)
            {
                var dto = new ProductsDTO();
                dto.ProductName = item.ProductName;
                dto.SupplierID = item.SupplierID;
                dto.CategoryID = item.CategoryID;
                dto.QuantityPerUnit = item.QuantityPerUnit;
                dto.UnitPrice = item.UnitPrice;
                dto.UnitsInStock = item.UnitsInStock;
                dto.UnitsOnOrder = item.UnitsOnOrder;
                dto.Discontinued = item.Discontinued;  
                dto.ReorderLevel = item.ReorderLevel;
                productsDTOs.Add(dto);
            }
            return productsDTOs;
        }

        public List<CategoryDTO> ObtenerCategorias()
         {
            List<Categories> categorias = new
           Datos.Repositories.ProductRepository().ObtenerCategorias();
            List<CategoryDTO> categoriesDTOs = new List<CategoryDTO>();
            //Hacemos el Cast
            foreach (var item in categorias)
            {
                var dto = new CategoryDTO();
                dto.CategoryID = item.CategoryID;
                dto.CategoryName = item.CategoryName;
                categoriesDTOs.Add(dto);
            }
            return categoriesDTOs;
        }

        public List<SuppliersDTO> ObtenerProveedores()
        {
            List<Suppliers> suppliers = new
           Datos.Repositories.ProductRepository().ObtenerProveedores();
            List<SuppliersDTO> suppliersDTOs = new List<SuppliersDTO>();
            //Hacemos el Cast
            foreach (var item in suppliers)
            {
                var dto = new SuppliersDTO();
                dto.SupplierID = item.SupplierID;
                dto.SuppliersName = item.CompanyName;
                suppliersDTOs.Add(dto);
            }
            return suppliersDTOs;
        }

        public void AltaProducto(ProductsDTO productoDTO)
        {
            Products producto = new Products();
            producto.ProductName = productoDTO.ProductName;
            producto.SupplierID = productoDTO.SupplierID;
            producto.CategoryID = productoDTO.CategoryID;
            producto.QuantityPerUnit = productoDTO.QuantityPerUnit;
            producto.UnitPrice = productoDTO.UnitPrice;

            new Datos.Repositories.ProductRepository().AltaProducto(producto);
        }
    }
}
