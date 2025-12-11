using System;
using Datos.Infraestructure;
using Datos.Repositorys;
using Negocio.EntitiesDTO;
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
            List<Products> productos = new Datos.Repositorys.ProductRepository().ObtenerProductos(); List<ProductsDTO> productsDTOs = new List<ProductsDTO>();
            foreach (var item in productos) 
            { var dto = new ProductsDTO();
                dto.ProductName = item.ProductName;
                dto.SupplierID = item.SupplierID;
                dto.CategoryID = item.CategoryID;
                dto.QuantityPerUnit = item.QuantityPerUnit;
                dto.UnitPrice = item.UnitPrice;
                productsDTOs.Add(dto);
            }
            return productsDTOs;
        }
        public List<CategoryDTO> ObtenerCategorias()
        {
            List<Categories> categorias = new 
                Datos.Repositorys.ProductRepository().ObtenerCategorias();
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


        public List<SupplierDTO> ObtenerProveedores()
        {
            // Obtenemos las entidades desde la base de datos
            List<Suppliers> suppliers = new Datos.Repositorys.ProductRepository().ObtenerProveedores();

            // Creamos la lista de DTOs (correcto)
            List<SupplierDTO> proveedoresDTO = new List<SupplierDTO>();

            // Convertimos cada entidad en DTO
            foreach (var item in suppliers)
            {
                var dto = new SupplierDTO();
                dto.SupplierID = item.SupplierID;
                dto.ContactName = item.ContactName;
                dto.CompanyName = item.CompanyName; // 👈 probablemente querías esto, no ContactName dos veces
                proveedoresDTO.Add(dto);
            }

            return proveedoresDTO;
        }

    }
}