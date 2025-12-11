using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Negocio.Management 
{ public class PruebaDeConexion 
    { public Boolean GetPruebaDeConexion() 
        { return new Datos.Repositorys.PruebaDeConexion().ProbarConexion(); 
        } 
    } 
}
