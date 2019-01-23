#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
int i;
int scelta;
bool continua;
char operandi;
double operando1_Double;
double operando2_Double;
double risultatoDouble;
int operando1_Int;
int operando2_Int;
int risultatoInt;
void addizioneInt(int x, int y, int *res){
*res=x + y;
}
void addizioneDouble(double x, double y, double *res){
*res=x + y;
}
void moltiplicazioneInt(int x, int y, int *res){
int i=0;
*res=0;
while( i < y ){
*res=*res + x;
i=i + 1;
}
}
void moltiplicazioneDouble(double x, double y, double *res){
int i=0;
*res=0;
while( i < y ){
*res=*res + x;
i=i + 1;
}
}
void dividi(int x, int y, int *res){
int i=x;
*res=0;
while( i >= y ){
i=i - y;
*res=*res + 1;
}
}
void potenza(int x, int y, int *res){
int i=1;
*res=1;
while( i <= y ){
*res=*res * x;
i=i + 1;
}
}
void fibonacci(int x, int *res){
int f0=0,f1=1,i=2,ris;
if( x == 1) {
ris=1;
}
while( i <= x ){
ris=f1 + f0;
f0=f1;
f1=ris;
i=i + 1;
}
*res=ris;
}
int main(void){
continua=true;
while( continua ){
printf("  Questo programma permette di svolgere una serie di operazioni  \n ");
printf("  Scegli il tipo di operazione che vuoi eseguire, digita:  \n ");
printf("  - 2 per eseguire la somma di due numeri  \n ");
printf("  - 3 per eseguire la moltiplicazione di due numeri  \n ");
printf("  - 4 per eseguire la divisione intera fra due numeri positivi  \n ");
printf("  - 5 per eseguire l’elevamento a potenza   \n ");
printf("  - 6 per eseguire la successione di Fibonacci   \n ");
scanf(" %d", &scelta);
if( scelta == 2 || scelta == 3) {
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
addizioneInt(operando1_Int,operando2_Int, &risultatoInt);
printf("  la somma vale=  %d \n ",risultatoInt);
}
else{
if( scelta == 2 && operandi == 'd') {
addizioneDouble(operando1_Double,operando2_Double, &risultatoDouble);
printf("  la somma vale=  %lf \n ",risultatoDouble);
}
}
if( scelta == 3 && operandi == 'i' ){
moltiplicazioneInt(operando1_Int,operando2_Int, &risultatoInt);
printf("  la moltiplicazione vale=  %d \n ",risultatoInt);
}
else{
if( scelta == 3 && operandi == 'd') {
moltiplicazioneDouble(operando1_Double,operando2_Double, &risultatoDouble);
printf("  la moltiplicazione vale=  %lf \n ",risultatoDouble);
}
}
}
if( scelta == 4) {
operando1_Int=0;
operando2_Int=0;
risultatoInt=0;
printf("  Inserisci dividendo  \n ");
scanf(" %d", &operando1_Int);
printf("  Inserisci divisore  \n ");
scanf(" %d", &operando2_Int);
dividi(operando1_Int,operando2_Int, &risultatoInt);
printf("  la divisione vale vale=   %d \n ",risultatoInt);
}
if( scelta == 5) {
operando1_Int=0;
operando2_Int=0;
risultatoInt=0;
printf("  Inserisci base  \n ");
scanf(" %d", &operando1_Int);
printf("  Inserisci esponente  \n ");
scanf(" %d", &operando2_Int);
potenza(operando1_Int,operando2_Int, &risultatoInt);
printf("  la potenza vale=   %d \n ",risultatoInt);
}
if( scelta == 6) {
operando1_Int=0;
risultatoInt=0;
printf("  Inserisci  numero  \n ");
scanf(" %d", &operando1_Int);
fibonacci(operando1_Int, &risultatoInt);
printf("  la sequenza di fibonacci vale=   %d \n ",risultatoInt);
}
if( scelta < 1 || scelta > 6) {
printf("  errore, il numero inserito non corrisponde a nessuna delle possibili scelte. Riprova!  \n ");
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