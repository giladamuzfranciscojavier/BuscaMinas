package principal;
import buscaminas.BuscaMinas;
import java.util.Scanner;

public class Interface {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        BuscaMinas juego = null;

        //Bucle para gestionar reinicios
        while(true){                 
            juego=nuevoJuego(sc);
            printDescubiertas(juego.getSolución(), juego.getDescubiertas());     
            //Bucle del juego
            while (!juego.isExploded() && !juego.isWin()) {     
                System.out.println("Casillas restantes: "+juego.getRestantes());
                System.out.println("\nIntroduce fila (1-"+juego.getSolución().length+")");       
                int f = tratamientoEntrada(sc);
                System.out.println("Introduce columna (1-"+juego.getSolución().length+")");  
                int c = tratamientoEntrada(sc);
    
                int men = juego.descubre(f-1, c-1);
    
                switch (men) {
                    case 0:
                        System.out.println();
                        printDescubiertas(juego.getSolución(), juego.getDescubiertas());
                        System.out.println("Zona despejada");
                        break;
    
                    case -1:
                        System.out.println();
                        printTablero(juego.getSolución());
                        System.out.println("EXPLOSIÓN!!!");
                        break;
    
                    case 1:
                        System.out.println();
                        printTablero(juego.getSolución());
                        System.out.println("CAMPO DESPEJADO!!!");
                        break;
                
                    case 2:
                        System.out.println("Ya hemos despejado esa zona!!\n");
                        printDescubiertas(juego.getSolución(), juego.getDescubiertas());
                        break;
                        
                    default:
                        System.out.println("Error, las coordenadas introducidas no son válidas\n");
                        printDescubiertas(juego.getSolución(), juego.getDescubiertas());
                        break;
                }            
            }  
            
            System.out.println("Quieres jugar otra vez? (S/N)");
            String in = sc.nextLine();
            if(in.length()<1 || in.toUpperCase().charAt(0)!='S'){
                System.exit(0);;
            }
        }
         
    }




    static BuscaMinas nuevoJuego(Scanner sc){
        System.out.println("Introduce el tamaño del tablero (máximo 92, siempre será cuadrado)");
        int tam = tratamientoEntrada(sc);
        System.out.println("Introduce el número de minas (en caso de introducir más de la cuenta se minarán todas las casillas menos una)");
        int min = tratamientoEntrada(sc);
        return new BuscaMinas(tam, min);
    }

    static int tratamientoEntrada(Scanner sc){
        try{
            return Integer.parseInt(sc.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("Entrada errónea, vuelve a intentarlo");
            return tratamientoEntrada(sc);
        }
    }
    
    static void printTablero(int[][] tablero){
        for(int i=0;i<tablero.length;i++){
            System.out.print("| ");
            for(int j=0;j<tablero.length;j++){
                if(tablero[i][j]==9) System.out.print("* | ");
                else System.out.print(tablero[i][j]+" | ");
            }
            System.out.println();
        }
    }

    static void printDescubiertas(int[][] tablero, int[][] descubiertas){
        for(int i=0;i<tablero.length;i++){
            System.out.print("| ");
            for(int j=0;j<tablero.length;j++){
                char c;
                if(descubiertas[i][j]>0){
                    c=Character.forDigit(tablero[i][j], 10);
                }
                else{
                    c='#';
                }
                System.out.print(c+" | ");
            }
            System.out.println();
        }
    }
}