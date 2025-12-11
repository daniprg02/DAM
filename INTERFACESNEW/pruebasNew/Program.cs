using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace pruebasNew
{
    internal class Program
    {
        static void Main(string[] args)
        {
            //Console.WriteLine("¿Cuál es tu animal favorito?");
            //string animal = Console.ReadLine();
            //Console.WriteLine("Tu animal favorito es: " + animal);

            /*
            Console.WriteLine("Indique el radio");
            double radio = Convert.ToDouble(Console.ReadLine());

            double superficie = 4 * Math.PI * (radio * radio);
            double volumen = (4.0 / 3.0) * Math.PI * (radio * radio * radio);

            superficie = Math.Round(superficie, 2);
            volumen = Math.Round(volumen, 2);

            Console.Write("La superficie es: " + superficie + "\n");
            Console.WriteLine("El volumen es: " + volumen);
            */

            /*
            Console.WriteLine("Indique el valor del primer número:");
            int num1 = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Indique el valor del segundo número:");
            int num2 = Convert.ToInt32(Console.ReadLine());

            int suma = num1 + num2;
            int resta = num1 - num2;
            int multiplicacion = num1 * num2;
            double division = (double)num1 / (double)num2;
            double resto = num1 % num2;

            Console.WriteLine("El número " + num1 + " + con el número " + num2 + " es: " + suma);
            Console.WriteLine("El número " + num1 + " - con el número " + num2 + " es: " + resta);
            Console.WriteLine("El número " + num1 + " * con el número " + num2 + " es: " + multiplicacion);
            Console.WriteLine("El número " + num1 + " / con el número " + num2 + " es: " + Math.Round(division, 2));
            Console.WriteLine("El número " + num1 + " % con el número " + num2 + " es: " + resto);
            */

            /*
            //Escribir un programa que solicita al usuario tres letras y lo muestre al revés
            
            //Version simple
            Console.WriteLine("Indique una letra :");
            char letra1 = Convert.ToChar(Console.ReadLine());
            Console.WriteLine("Indique la segunda letra:");
            char letra2 = Convert.ToChar(Console.ReadLine());
            Console.WriteLine("Indique la tercera letra:");
            char letra3 = Convert.ToChar(Console.ReadLine());
            Console.WriteLine("Las letras al revés son: " + letra3 + letra2 + letra1);

            //Version con arrays
            Console.WriteLine("Introduzca las letras");
            String letras = Console.ReadLine();
            char[] letrasArray = letras.ToCharArray();
            Array.Reverse(letrasArray);
            String letrasReves = "";
            letrasReves = letrasReves + new string(letrasArray);
            Console.WriteLine("Las letras al revés son: " + letrasReves);
            */

            /*
            //Crea un programa que convierta de grados centrigrados a Kelvin y Fahrenheit
            //Solicite al usuario la cantidad en grados centigrados para convertirlos usando las tablas de conversión: 
            //Kelvin = Centigrados + 273
            //Fahrenheit = celsius * 18/10 + 32

            Console.WriteLine("Indique la cantidad en grados centígrados:");
            double centigrados = Convert.ToDouble(Console.ReadLine());
            double kelvin = centigrados + 273;
            double fahrenheit = (centigrados * 18.0 / 10.0) + 32.0;
            kelvin = Math.Round(kelvin, 2);
            fahrenheit = Math.Round(fahrenheit, 2);
            Console.WriteLine("La cantidad en Kelvin es: " + kelvin);
            Console.WriteLine("La cantidad en Fahrenheit es: " + fahrenheit);
            */
            /*
            //Escribe un programa que pregunte al usuario por un número entero y muestre su resultado en una tabla de multiplicar del 1 al 10.
            Console.WriteLine("Indique un número:");
            int numero = Convert.ToInt32(Console.ReadLine());
            for (int i = 1; i <= 10; i++)
            {
                int resultado = numero * i;
                Console.WriteLine(numero + " x " + i + " = " + resultado);

            }
            */

            /*
            //Escribe un programa que solicite un número entero al usuario y imprima el resultado de sumarlo al cuadrado 

            int numero;
            Console.WriteLine("Indique un número entero:");
            numero = Convert.ToInt32(Console.ReadLine());
            int cuadrado = numero * numero;
            Console.WriteLine("El resultado de sumar " + numero + " al cuadrado es: " + cuadrado);
            */
            /*
            //Escribe un programa que pida tres números flotantes al usuario y muestre el resultado de multiplicarlos entre sí.
            double num1, num2, num3, resultado;

            Console.Write("Indique el primer número -> ");
            num1 = Convert.ToDouble(Console.ReadLine());

            Console.Write("Indique el segundo número -> ");
            num2 = Convert.ToDouble(Console.ReadLine());

            Console.Write("Indique el tercer número -> ");
            num3 = Convert.ToDouble(Console.ReadLine());

            resultado = num1 * num2 * num3;
            
            Console.WriteLine("El resultado de multiplicar " + num1 + ", " + num2 + " y " + num3 + " es: " + resultado);
            */

            /*
            //Mostrar por la consola todas las tablas de multiplicar del 1 al 10

            for (int i = 1; i <= 10; i++)
            {
                Console.WriteLine("\nTabla de multiplicar del " + i + ":");
                for (int j = 1; j <= 10; j++)
                {
                    int res = i * j;
                    Console.WriteLine(i + " x " + j + " = " + res);
                }
            }

            Console.WriteLine("\nPresione una tecla para finalizar...");
            Console.ReadKey();
            */
            /*
            // Escribe un programa que con la estructura if, else if, else. Pedira por consola 3 números enteros y tiene que decir cual es el mayor de los tres.
            // Hay que comprobar si los tres números son iguales

            int num1, num2, num3;
            Console.Write("Indique el primer número ");
            num1 = Convert.ToInt32(Console.ReadLine());
            Console.Write("Indique el segundo número ");
            num2 = Convert.ToInt32(Console.ReadLine());
            Console.Write("Indique el tercer número ");
            num3 = Convert.ToInt32(Console.ReadLine());

            //Comprobamos si los tres números son iguales
            if (num1 == num2 && num1 == num3)
            {
                Console.WriteLine("Has introducido tres números iguales: " + num1);
            }
            else
            {
                //Comprobamos si el primero es el mayor
                if (num1 >= num2 && num1 >= num3)
                {
                    Console.WriteLine("El mayor de todos es : " + num1);
                }
               // comprobamos si el segundo es el mayor
                else if (num2 >= num1 && num2 >= num3)
                {
                    Console.WriteLine("El mayor de todos es :  " + num2);
                }
                // Por descarte, el tercero es el mayor
                else
                {
                    Console.WriteLine("El mayor de todos es :  " + num3);

                }
            }
            */
            /*
            // Necesito un programa que pida al usuario un tipo de operación con simbolos (+ , - , * , /) y dos números enteros.
            // Según el símbolo introducido, se realizará la operación correspondiente y se mostrará el resultado por consola.

            char operacion;
            int numero1, numero2;
            int suma, resta, multiplicacion, division;
           

            Console.Write("Indique la operación a realizar (+ , - , * , /) : ");
            operacion = Convert.ToChar(Console.ReadLine());
            
            Console.Write("Indique el primer número: ");
            numero1 = Convert.ToInt32(Console.ReadLine());
            //Si el número es 0 y la operación es división, mostramos mensaje de error y salimos
            if (numero1 == 0 && operacion == '/')
            {
                Console.WriteLine("Amigo, no puedes dividir por 0");
                return;
            }
            Console.Write("Indique el segundo número: ");
            numero2 = Convert.ToInt32(Console.ReadLine());
            //Si el número es 0 y la operación es división, mostramos mensaje de error y salimos
            if (numero2 == 0 && operacion == '/')
            {
                Console.WriteLine("Amigo, no puedes dividir por 0");
                return;
            }

            switch (operacion)
            {
                case '+':
                {
                     suma = numero1 + numero2;
                     Console.WriteLine("El resultado de la suma es: " + suma);
                     break;
                }
                case '-':
                {
                     resta = numero1 - numero2;
                     Console.WriteLine("El resultado de la resta es: " + resta);
                     break;
                }
                case '*':
                {
                     multiplicacion = numero1 * numero2;
                     Console.WriteLine("El resultado de la multiplicación es: " + multiplicacion);
                     break;
                }
                case 'x':
                {
                     multiplicacion = numero1 * numero2;
                     Console.WriteLine("El resultado de la multiplicación es: " + multiplicacion);
                     break;
                }
                case '/':
                {
                     division = numero1 / numero2;
                     Console.WriteLine("El resultado de la división es: " + division);
                     break;
                }
                default:
                {
                     Console.WriteLine("No tengo guardada esa opción de cálculo");
                     break;
                }
            

            }
        */

            /*
            //Generar 6 números aleatorios entre 1 y 49 para una apuesta de la Primitiva
            //Variables
            Random numero = new Random();
            int primitiva;

            for(int i = 1; i <= 6; i++)
            {
                primitiva = numero.Next(1, 50);
                if(i == 6)
                {
                    //En la última vuelta del bucle, cerramos sin coma
                    Console.Write(primitiva);
                    break;
                }
                Console.Write(primitiva + ",");
                
            }
            Console.WriteLine("\n");
            Console.WriteLine("¿Quieres jugar al reintro? pulse en su teclado [S] o [N]");
            char reintegro = Convert.ToChar(Console.ReadLine());
            reintegro = char.ToLower(reintegro);

            if( reintegro == 's')
            {
                int numeroReintegro = numero.Next(0, 10);
                Console.WriteLine("Su número de reintegro es: " + numeroReintegro);
                Console.WriteLine("Sería 1 euro más, gracias y mucha suerte");
            }
            else
            {
                Console.WriteLine("¿Estás seguro? pulse en su teclado [S] o [N]");
                char seguro = Convert.ToChar(Console.ReadLine());
                seguro = char.ToLower(seguro);
                if(seguro == 's')
                {
                    Console.WriteLine("De acuerdo! Hasta pronto");
                }
                else
                {
                    int numeroReintegro = numero.Next(0, 10);
                    Console.WriteLine("Su número de reintegro es: " + numeroReintegro);
                    Console.WriteLine("Sería 1 euro más, gracias y mucha suerte");
                }
            }

            */

            /*
            int numero = 0;
            int veces = 0;
            bool salir = false;

            while (!salir)
            {
                Console.WriteLine("Indique un número");
                numero = Convert.ToInt32(Console.ReadLine());
                Console.WriteLine("Indique cuántas veces quiere que se repita el número");
                veces = Convert.ToInt32(Console.ReadLine());

                for (int i = 1; i <= veces; i++)
                {
                    Console.WriteLine(i + ": " + numero);
                }

                Console.WriteLine("¿Desea repetir la operación? (S/N)");
                char respuesta = Convert.ToChar(Console.ReadLine());
                respuesta = char.ToLower(respuesta);
                
                if (respuesta == 'n')
                {
                    salir = true;
                }
            }

            */


            /*
            int numero = 0;

            Console.WriteLine("Indique un número");
            numero = Convert.ToInt32(Console.ReadLine());

            if (numero % 2 == 0)
            {
                Console.WriteLine("El número " + numero + " que has introducido -> es par");
            }
            else
            {
                Console.WriteLine("El número " + numero + " que has introducido -> es impar");
            }

           */

            /*
            int numero;
            Console.WriteLine("Indique un número");
            numero = Convert.ToInt32(Console.ReadLine());

            int factorial = 1;

            for (int i = numero; i >= 1; i--)
            {
                factorial *= i;
            }

            Console.WriteLine("El factorial es: " + factorial);


            Console.WriteLine("El factorial de " + numero + " es " + factorial);
            */

            /*
            int[] numeros = {8, 99, 101, 229, 1002, 2, 45, 90, 1001, 10, 80};

            Console.WriteLine("Mostrando los números del array... " + "\n");
            
            for (int i = 1; i < numeros.Length; i++)
            {
                Console.WriteLine("Número " + i + ": " + numeros[i]);

            }

            Console.WriteLine("\nPresione una tecla para finalizar...");
            Console.ReadKey();
            */
            /*
            int numero = 1; 

            while (true)
            {
                Console.Write(numero + " ");
                if(numero % 10 == 0)
                {
                    Console.WriteLine();
                }
                numero++;
                if(numero == 101)
                {
                    break;
                }
            }
            */

            /*
            int numero = 1;

            while (true)
            {
                if ((numero % 3 == 0) || (numero % 2 == 0))
                {
                    Console.Write(numero + " ");
                }
                
                if (numero % 10 == 0 )
                {
                    Console.WriteLine();
                }
                numero++;

                if( numero == 101)
                {
                    break;
                }
            }

            */

            /*

            int[] numeros = { 8, 99, 101, 229, 1002, 2, 45, 90, 1001, 10, 80 };

            Console.WriteLine("Mostrando los números del array desordenados " + "\n");

            for (int i = 1; i < numeros.Length; i++)
            {
                Console.WriteLine("Número " + i + ": " + numeros[i]);

            }

            Console.WriteLine("\n" + "Mostrando los números del array ordenados " + "\n");

            Array.Sort(numeros);

            for (int i = 1; i < numeros.Length; i++)
            {
                Console.WriteLine("Número " + i + ": " + numeros[i]);

            }

            Console.WriteLine("\nPresione una tecla para finalizar...");
            Console.ReadKey();

            */
            /*
            //Número 0, positivo o negativo 
            int numero = 0; 

            Console.WriteLine("Inserte un número por favor"); 
            numero = int.Parse(Console.ReadLine());

            if (numero == 0)
            {
                Console.WriteLine("El número es 0");
            } else if (numero > 1)
            {
                Console.WriteLine("El número es positivo"); 
            }
            else
            {
                Console.WriteLine("El número es negativo");
            }

            */

            /*
            //Switch pedir número y dia de la semana 

            int numero = 0;


            Console.WriteLine("Escriba un número del 1 al 7"); 
            
            numero = int.Parse(Console.ReadLine());

            if (numero < 1 || numero > 7)
            {
                while (numero < 1 || numero > 7) 
                { 
                    Console.WriteLine("Vuelva a escribir un número válido");
                    numero = int.Parse(Console.ReadLine());
                }
            }

            switch (numero)
            {
                case 1: Console.WriteLine("Lunes");
                    break;

                case 2: Console.WriteLine("Martes");
                    break;

                case 3:
                    Console.WriteLine("Miércoles");
                    break;

                case 4:
                    Console.WriteLine("Jueves");
                    break;

                case 5:
                    Console.WriteLine("Viernes");
                    break;

                case 6:
                    Console.WriteLine("Sábado");
                    break;

                case 7:
                    Console.WriteLine("Domingo");
                    break; 

                default: Console.WriteLine("Opción no válida");
                    break;
            }


            */
            /*
            // número regresivo 

            int numero = 0; 
            Console.WriteLine("Indique un número mayor que 1"); 
            numero = int.Parse(Console.ReadLine());

            if(numero <= 1)
            {
                while (numero <= 1)
                {
                    Console.WriteLine("Indique un número mayor que 1");
                    numero = int.Parse(Console.ReadLine());
                }
            }

            

            while (true)
            {
                if(numero == 0)
                {
                    break; 
                } 

                Console.WriteLine(numero);
                numero--; 

            }

            */
            /*
            
            //Cuadrado entero con *

            for (int i = 0; i < 6; i++)
            {
                Console.WriteLine();

                for (int j = 0; j < 6; j++)
                {
                    Console.Write("*"); 
                }
            }

            */

        }

    }
}
