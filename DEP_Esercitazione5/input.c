#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
int i;
int scelta;
char operandi;
double operando1_Double;
double operando2_Double;
double risultatoDouble;
bool continua;
int operando1_Int;
int operando2_Int;
int risultatoInt;
void moltiplicazioneDouble(double x, double y, double *res){
*res=x * y;
}
void moltiplicazioneInt(int x, int y, int *res){
*res=x * y;
}
void addizioneInt(int x, int y, int *res){
*res=x + y;
}
void addizioneDouble(double x, double y, double *res){
*res=x + y;
}
void divisioneInt(int x, int y, int *res){
*res=x / y;
}
void divisioneDouble(double x, double y, double *res){
*res=x / y;
}
void sottrazioneInt(int x, int y, int *res){
*res=x - y;
}
void sottrazioneDouble(double x, double y, double *res){
*res=x - y;
}
int main(void){
printf("  Questo programma permette di svolgere una serie di calcoli  \n ");
scelta=-1;
i=-1;
operandi=' ';
continua=true;
while( continua ){
printf("  Scegli il tipo di operazione che vuoi eseguire, digita:  \n ");
printf("  - 2 per eseguire una moltiplicazione  \n ");
printf("  - 3 per eseguire una sottrazione  \n ");
printf("  - 4 per eseguire una divisione  \n ");
printf("  - 5 per eseguire un' addizione   \n ");
scanf(" %d", &scelta);
printf("  Scegli il tipo di operandi da usare, digita:  \n ");
printf("  - i per interi  \n ");
printf("  - d per double  \n ");
scanf("  %c", &operandi);
if( operandi == 'i' ){
operando1_Int=0;
operando2_Int=0;
risultatoInt=0;
printf("  Inserisci primo numero  \n ");
scanf(" %d", &operando1_Int);
printf("  Inserisci secondo numero  \n ");
scanf(" %d", &operando2_Int);
}
else{
if( operandi == 'd') {
operando1_Double=0;
operando2_Double=0;
risultatoDouble=0;
printf("  Inserisci primo numero  \n ");
scanf(" %lf", &operando1_Double);
printf("  Inserisci secondo numero  \n ");
scanf(" %lf", &operando2_Double);
}
}
if( scelta == 2 && operandi == 'i' ){
moltiplicazioneInt(operando1_Int,operando2_Int, &risultatoInt);
printf("  valore moltiplicazione=  %d \n ",risultatoInt);
}
else{
if( scelta == 2 && operandi == 'd') {
moltiplicazioneDouble(operando1_Double,operando2_Double, &risultatoDouble);
printf("  valore moltiplicazione=  %lf \n ",risultatoDouble);
}
}
if( scelta == 3 && operandi == 'i' ){
sottrazioneInt(operando1_Int,operando2_Int, &risultatoInt);
printf("  valore sottrazoione=  %d \n ",risultatoInt);
}
else{
if( scelta == 3 && operandi == 'd') {
sottrazioneDouble(operando1_Double,operando2_Double, &risultatoDouble);
printf("  valore sottrazoione=  %d \n ",risultatoInt);
}
}
if( scelta == 4 && operandi == 'i' ){
divisioneInt(operando1_Int,operando2_Int, &risultatoInt);
printf("  valore divisione=  %d \n ",risultatoInt);
}
else{
if( scelta == 4 && operandi == 'd') {
divisioneDouble(operando1_Double,operando2_Double, &risultatoDouble);
printf("  valore divisione=  %lf \n ",risultatoDouble);
}
}
if( scelta == 5 && operandi == 'i' ){
addizioneInt(operando1_Int,operando2_Int, &risultatoInt);
printf("  valore addizione=  %d \n ",risultatoInt);
}
else{
if( scelta == 5 && operandi == 'd') {
addizioneDouble(operando1_Double,operando2_Double, &risultatoDouble);
printf("  valore addizione=  %lf \n ",risultatoDouble);
}
}
printf("  vuoi eseguire un'altra operazione?(inserisci 0 per termiare o 1 per continuare)  \n ");
scanf(" %d", &i);
if( i == 0) {
continua=false;
}
}
printf("  Programma finito  \n ");

 return 0;
}