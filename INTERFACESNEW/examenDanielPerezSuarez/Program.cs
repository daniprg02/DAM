using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace examenDanielPerezSuarez
{
    internal class Program
    {
        static void Main(string[] args)
        {
            int opcion = Convert.ToInt32(Console.ReadLine());

            switch (opcion)
            {
                case 1:
                    ejercicio1();
                    break;
                case 2:
                    ejercicio2();
                    break;
                case 3:
                    ejercicio3();
                    break;
                case 4:
                    ejercicio4();
                    break;
                case 5:
                    ejercicio5();
                    break;
                case 6:
                    ejercicio6();
                    break;
                case 7:
                    ejercicio7();
                    break;
                case 8:
                    ejercicio8();
                    break;
                case 9:
                    ejercicio9();
                    break;
                case 10:
                    ejercicio10();
                    break;
                default: Console.WriteLine("No valido");
                    break;
            }
        }



            private static void ejercicio1()
        {
            //Ejercicio 1: 

            string nombre = "";
            string ciudad = "";

            Console.WriteLine("Ingrese un nombre -> ");
            nombre = Console.ReadLine();
            Console.WriteLine("Ingrese una ciudad -> ");
            ciudad = Console.ReadLine();

            Console.WriteLine("Hola " + nombre + " bienvenido a " + ciudad);
        }
        private static void ejercicio2()
        {
            //Ejercicio 2: 

            int a = 0, b = 0, c = 0;
            int resultado1 = 0, resultado2 = 0;

            Console.WriteLine("Escriba el primer número -> ");
            a = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Escriba el segundo número ->");
            b = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Escriba el tercer número ->");
            c = Convert.ToInt32(Console.ReadLine());

            resultado1 = (a + b) * c;
            resultado2 = a * c + b * c;

            Console.WriteLine("Los resultados de: (a + b) * c es " + resultado1);
            Console.WriteLine("Los resultados de a * c + b * c es " + resultado2);
        }
        private static void ejercicio3()
        {
            //Ejercicio 3: 
            int numero = 0;

            Console.WriteLine("Ingrese un número ");
            numero = Convert.ToInt32(Console.ReadLine());

            for (int i = 0; i < 5; i++)
            {
                //Para que no de salto de linea en la primera iteración 
                if (i > 0)
                {
                    Console.WriteLine();
                }
                for (int j = 0; j < 3; j++)
                {
                    Console.Write(numero);
                }
            }
        }
        private static void ejercicio4()
        {
            //Ejercicio 4: 

            bool salir = false;
            int numero = 0, multi = 0;


            while (!salir)
            {
                Console.WriteLine("Ingrese un número, para salir del programa ingrese 0 ");
                numero = Convert.ToInt32(Console.ReadLine());

                if (numero == 0)
                {
                    salir = true;
                    break;
                }

                multi = 10 * numero;
                Console.WriteLine("El número " + numero + " x 10 es igual a = " + multi);
                Console.WriteLine();

            }
        }
        private static void ejercicio5()
        {
            //Ejericio 5 
            bool salir = false;
            int numero = 0, sumaTotal = 0;


            while (!salir)
            {
                Console.WriteLine("Ingrese un número, para salir del programa ingrese 0 ");
                numero = Convert.ToInt32(Console.ReadLine());

                if (numero == 0)
                {
                    salir = true;
                    Console.WriteLine("Programa Terminado ");
                    break;
                }

                sumaTotal += numero;


            }

            Console.WriteLine("La suma total de los números introducidos es: " + sumaTotal);
        }
        private static void ejercicio6()
        {
            //Ejercicio 6 
            int x = 0;
            int y = 0;
            int multi = 0;


            Console.WriteLine("Ingrese el número X: ");
            x = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Ingrese el número Y: ");
            y = Convert.ToInt32(Console.ReadLine());

            for (int i = x; i <= y; i++)
            {
                for (int j = 1; j <= 10; j++)
                {
                    multi = i * j;
                    Console.WriteLine(i + " x " + j + " = " + multi);
                }
                Console.WriteLine("------------");
            }
        }
        private static void ejercicio7()
        {
            //Ejercicio 7 
            bool salir = false;
            int numPositivos = 0;
            int numNegativos = 0;
            int numero = 0;

            while (!salir)
            {
                Console.WriteLine("Ingrese un número positivo");
                numero = Convert.ToInt32(Console.ReadLine());

                if (numero < 0)
                {
                    Console.WriteLine(numPositivos + " dígitos");
                    Console.WriteLine("Advertencia: es un número negativo");
                    while (numero < 0)
                    {
                        numero /= 10;
                        numNegativos++;
                    }
                    Console.WriteLine(numNegativos + " dígitos");
                    salir = true;
                }

                while (numero > 0)
                {
                    numero /= 10;
                    numPositivos++;
                }
            }
        }
        private static void ejercicio8()
        {
            //Ejercicio 8 
            String texto;


            Console.WriteLine("Hola, ingrese un texto: ");

            texto = Console.ReadLine();

            foreach (char n in texto)
            {
                if ("0123456789".Contains(n))
                {
                    Console.WriteLine("Tiene un número: " + texto);
                    break;
                }
                else
                {
                    Console.WriteLine("No es un número " + texto);
                    break;
                }
            }
        }
        private static void ejercicio9()
        {
            //Ejercicio 9

            bool pares = false;
            int num1 = 0, num2 = 0;

            Console.WriteLine("Ingrese un número ");
            num1 = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Ingrese otro número ");
            num2 = Convert.ToInt32(Console.ReadLine());

            if (num1 % 2 == 0 && num2 % 2 == 0)
            {
                pares = true;


            }

            Console.WriteLine(pares);
        }
        private static void ejercicio10()
        {
            //Ejercicio 10 
            int numero = 0;
            int numPos = 0, contPos = 0;
            int numNeg = 0, contNeg = 0;
            int mediaPos = 0, mediaNeg = 0;

            for (int i = 1; i < 11; i++)
            {
                Console.Write("Ingrese el número " + i + " -> ");
                numero = Convert.ToInt32(Console.ReadLine());

                if (numero > 0)
                {
                    contPos++;
                    numPos += numero;
                }
                if (numero < 0)
                {
                    contNeg++;
                    numNeg += numero;
                }

            }

            mediaNeg = numNeg / contNeg;
            mediaPos = numPos / contPos;

            Console.WriteLine("1. La media de números negativos es: " + mediaNeg);
            Console.WriteLine("2. La media de números positivos es: " + mediaPos);
        }

    
    
      
    }
}
