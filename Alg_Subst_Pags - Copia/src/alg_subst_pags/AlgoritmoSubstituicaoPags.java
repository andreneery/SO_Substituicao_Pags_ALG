
package alg_subst_pags;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
//import java.util.stream.IntStream;

public class AlgoritmoSubstituicaoPags {
    //Criar os dados das páginas e seus valores de referenciada ou modificada
    static String paginas[] = {"A","B","C","D","E","F","G","H","I","J"};  
    static int referenciada[] = {1,1,0,1,0,1,1,1,0,0};
    static int modificada[] =   {0,0,1,1,0,1,1,0,0,0};
    static int numPaginas = paginas.length;

    static Scanner leia = new Scanner(System.in);
    static int algoritmo;
    
   
    public static void NUR( ) {
        //Classifica as pags em classes(0,1,2,3) com base nos valores de R e M
        int classeNUR[] = new int[numPaginas];
        for(int i=0; i< numPaginas; i++){
            if (referenciada[i]==1){
                if(modificada[i]==1){
                    classeNUR[i]=3;
                }
                else {
                    classeNUR[i]=2;
                }
            }
            else if(modificada[i]==1){
                classeNUR[i]=1;
            }
            else classeNUR[i]=0;
        }
        
        //Verifica qual a classe mais baixa ocupada 
        int menorClasse = Arrays.stream(classeNUR)
        .min()
        .getAsInt();
        //Conta quantos itens estão presentes na classe para determinar o tamanho da array futura
        int nClasse = 0;
        for(int i=0;i<numPaginas;i++){
            if(classeNUR[i]==menorClasse){
                nClasse++;
            }
        }
                
        //grava o índice de todas as paginas da menor classe em uma nova array
        int indexPags[] = new int[nClasse];
        int j=0;
        for(int i=0;i<numPaginas;i++){
            if(classeNUR[i]==menorClasse){
                indexPags[j]=i;
                j++;
            }
        }
        //sortear um número aleatório dessa nova array
        Random sorteador = new Random();
        int randomIndex = sorteador.nextInt(indexPags.length);
        int pagSorteada= indexPags[randomIndex];
        //Usar ele como o índice para substituir as informações pelas da página nova 
        //(Pags substituídas devem ser E,I ou J pois R e M = 0) 
        paginas[pagSorteada] = "X";
        referenciada[pagSorteada] = 0;
        modificada[pagSorteada] = 0;
        
        
    }
    
    private static void FIFO( ) {
    //Apaga a pag mais antiga e adiciona a nova no fim da fila
        for(int i=0;i<numPaginas-1;i++){
            paginas[i]=paginas[i+1]; 
            referenciada[i]=referenciada[i+1];
            modificada[i]=modificada[i+1];
        }
        paginas[numPaginas-1]="X";
        referenciada[numPaginas-1]=0;
        modificada[numPaginas-1]=0;       
              
    }
    
    private static void SC() {        
        //Fifo, porém as pags que tiverem R=1 tem o R zerado e vão para o fim da fila

        while(referenciada[0] != 0){
            //Cria variaveis para guardar o valor da primeira pagina
            String pag1 = paginas[0];
            int mod1= modificada[0];
            int i;
            //Sobe uma posição cada item do vetor
            for(i=0;i<numPaginas-1;i++){           
                paginas[i]=paginas[i+1]; 
                referenciada[i]=referenciada[i+1];
                modificada[i]=modificada[i+1];
            }
            //Passa a pagina inicial para o final, alterando r para 0
            paginas[i]=pag1;
            referenciada[i]=0;
            modificada[i]=mod1;            
        }      
        //Depois de garantir que o primeiro elemento tem R=0, realiza o FIFO
        for(int i=0;i<numPaginas-1;i++){
            paginas[i]=paginas[i+1]; 
            referenciada[i]=referenciada[i+1];
            modificada[i]=modificada[i+1];
        }
        paginas[numPaginas-1]="X";
        referenciada[numPaginas-1]=0;
        modificada[numPaginas-1]=0;       
 
    }
    
    private static void Clock( ) {        
        //Sc porém não muda as posições, se R=0 remove, se R=1 transforma em R=0
        int i = 0;
        while(referenciada[i] != 0){
            referenciada[i]=0;
            //verificar se chegou ao fim do vetor e se é necessário reiniciar 
            if(i == numPaginas-1)
                i=0;
            else i++;
        }
        //Coloca a página nova no primeiro item identificado  
        paginas[i]="X";
        referenciada[i]=0;
        modificada[i]=0;
    }
    
        
    private static void MRU( ) {
        
        //Criar um vetor com dados de contador de uso    
        int usoPagina[] = {5,7,3,8,5,4,6,2,6,3};

        //organizar as listas do menos usado para o mais usado
        int aux;
        String paux;
        for(int i=0; i<numPaginas; i++){
            for(int j=i+1; j<numPaginas; j++){
                if(usoPagina[i]>usoPagina[j]){
                    //organizar o vetor de uso em ordem crescente
                    aux=usoPagina[i];
                    usoPagina[i]=usoPagina[j];
                    usoPagina[j]=aux;

                    //organizar o vetor de paginas por ordem de uso
                    paux=paginas[i];
                    paginas[i]=paginas[j];
                    paginas[j]=paux;
                    
                    //organizar o vetor de referenciada por ordem de uso
                    aux=referenciada[i];
                    referenciada[i]=referenciada[j];
                    referenciada[j]=aux;
                    
                    //organizar o vetor de modificada por ordem de uso
                    aux=modificada[i];
                    modificada[i]=modificada[j];
                    modificada[j]=aux;
                }  
            }
        }
        //Substitui a pag de menor valor pela nova página
        paginas[0]="X";
        referenciada[0]=0;
        modificada[0]=0;       
    
    }

    public static void main(String[] args) {
        
        //AlgoritmoSubstituicaoPags obj = new AlgoritmoSubstituicaoPags();
        
        // Criar Switch Case para escolher qual será usada
        System.out.println("Informe o numero do algoritmo voce gostaria de utilizar: [1]NUR [2]FIFO [3]SC [4]CLOCK [5]MRU");
        algoritmo = leia.nextInt();
        switch (algoritmo){
            case 1:
                NUR();
                break;
            case 2:
                FIFO();
                break;
            case 3:
                SC();
                break;
            case 4:
                Clock();
                break;
            case 5:
                MRU();
                break;
            default:
                System.out.println("Numero invalido");

        }
        System.out.println(Arrays.toString(paginas));
    }
    
}
