/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package esercitazione_4;

import java.util.Scanner;

public class stoProvando {

	public static void main(String[] args) {

		int i;
		int scelta;
		double operando1_Double;
		double operando2_Double;
		double risultatoDouble;

		int operando1_Int;
		int operando2_Int;
		int risultatoInt;
		Scanner input = new Scanner(System.in);
		System.out.println(	"Questo programma permette di svolgere una serie di calcoli");
		scelta=-1;
		i=-1;
		while ( i!=0){

			System.out.println(	"Scegli il tipo di operazione che vuoi eseguire:\n"+
					" 2 per eseguire una moltiplicazione\n 3 per eseguire una sottrazione\n"
					+ " 4 per eseguire una divisione\n 5 per eseguire un' addizione ");
			scelta=input.nextInt();


			if(scelta==2 || scelta==4){
				operando1_Double=0;
				operando2_Double=0;
				risultatoDouble=0;

				System.out.println(	"Inserisci primo numero");
				operando1_Double=input.nextDouble();
				System.out.println("Inserisci secondo numero");
				operando2_Double=input.nextDouble();


				if(scelta==2) {
					risultatoDouble=operando1_Double*operando2_Double;
					System.out.println("valore moltiplicazione= "+risultatoDouble);

				}
				else{
					if(scelta==4) {
						risultatoDouble=operando1_Double/operando2_Double;
						System.out.println("valore divisione= "+risultatoDouble);


					}
				}
			}

			else{
				if(scelta==3 || scelta==5){
					operando1_Int=0;
					operando2_Int=0;
					risultatoInt=0;


					System.out.println(	"Inserisci primo numero");
					operando1_Int=input.nextInt();
					System.out.println("Inserisci secondo numero");
					operando2_Int=input.nextInt();

					if(scelta==3) {
						risultatoInt=operando1_Int-operando2_Int;
						System.out.println("valore sottrazione= "+ risultatoInt);
					}
					else{

						if(scelta==5){

							risultatoInt=operando1_Int+operando2_Int;
							System.out.println("valoreaddizione= "+ risultatoInt);

						}
					}
				}
			}
			System.out.println("vuoi eseguire un'altra operazione?(inserisci 0 per termiare e 1 per continuare)");

			i =input.nextInt();
		}

		System.out.println("Programma finito");
	}




}


