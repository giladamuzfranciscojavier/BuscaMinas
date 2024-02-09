package buscaminas;

import java.util.Random;

public class BuscaMinas {
    private int[][] tablero;
    private int[][] descubiertas;
    private boolean explosion=false;
    private boolean win=false;
    private int restantes;
    
    /** Prepara un juego de Buscaminas en base al tamaño y número de minas proporcionados
     * 
     * @param tamano El lado del tablero (cuadrado)
     * @param minas El número de minas. Si es 1 o menor habrá una mina, mientras que si es superior al número de casillas del tablero 
     * se minarán todas las casillas excepto una (ejemplo: si se indican 99 minas para un tamaño de 5 [25 casillas] habrán 24 minas)
     */
    public BuscaMinas(int tamano, int minas){
        //Si el tamaño es menor que 3 se establece en 3 (de lo contrario tendría poca gracia)
        if(tamano<3)
            tamano=3;
                
        //Si hay más minas que casillas todas las casillas estarán minadas excepto una (si alguien quiere jugar a la ruleta rusa versión bestia quien soy yo para juzgar)
        if(minas>tamano*tamano)
            minas = (tamano*tamano)-1;

        //Si el número de minas es 0 o negativo se ajustará por defecto a 1
        if(minas<1)
            minas=1;

        restantes=(tamano*tamano)-minas;
        start(tamano, minas);
    }

    /** Indica si se ha destapado una mina (y por lo tanto perdido el juego)
     * 
     * @return Devolverá true si se ha destapado una mina. En caso contrario devolverá false
     */
    public boolean isExploded(){
        return explosion;
    }

    /** Indica si se ha han destapado todas las casillas sin minar (y por lo tanto ganado el juego)
     * 
     * @return Devolverá true si se han destapado todas las casillas sin minar. En caso contrario devolverá false
     */
    public boolean isWin(){
        return win;
    }

    /** Getter del tablero con la solución
     * 
     * @return Devuelve el tablero con la solución
     */
    public int[][] getTablero(){
        return tablero.clone();
    }

    /** Getter de las casillas descubiertas
     * 
     * @return Devuelve el estado de visibilidad de las casillas del tablero
     */
    public int[][] getDescubiertas(){
        return descubiertas.clone();
    }

    /** Indica cuantas casillas no minadas quedan por destapar
     * 
     * @return Devuelve el número de casillas no minadas sin destapar
     */
    public int getRestantes(){
        return restantes;
    }


    /** Ejecuta un turno del jugador, destapando la casilla proporcionada. Devuelve un mensaje a tratar por la interfaz en forma de número entero
     * 
     * @param x Coordenada x de la casilla
     * @param y Coordenada y de la casilla
     * @return -1: Destapada una mina (pierde la partida)<br> 0: Continúa el juego<br>
     *  1: Destapadas todas las casillas no minadas (gana el juego)<br> 2: Casilla ya destapada<br> 99: Coordenadas no válidas
     */
    public int descubre(int x, int y){

        if(x<0||x>tablero.length-1||y<0||y>tablero.length){
            return 99;
        }

        if(descubiertas[x][y]!=0){
            return 2;
        }

        if(tablero[x][y]!=9){
            restantes--;
            descubiertas[x][y]=1;
            if(tablero[x][y]==0){
                descubreSiVacias(tablero, x, y);
            }
            if(restantes==0){
                win=true;
                return 1;
            }            
            return 0;
        }

        else{
            descubiertas[x][y]=2;
            explosion=true;
            return -1;
        }      
        
    }





    private void start(int tamano, int minas){
        Random r = new Random();
        tablero = new int[tamano][tamano];
        descubiertas = new int[tamano][tamano];

        //Se colocan las minas
        for(int i=0;i<minas;i++){

            //Se elige casilla mediante coordenadas aleatorias
            int x = r.nextInt(tamano);
            int y = r.nextInt(tamano);

            //Si la casilla no tiene mina se coloca una
            if(tablero[x][y]!=9){
                tablero[x][y]=9;
            }
            //Si la casilla ya tenía mina se descuenta la iteración del bucle para que se vuelva a intentar
            else{
                i--;
            }
        }

        //Se calculan las minas adyacentes a cada casilla
        for(int i=0;i<tamano;i++){
            for(int j=0;j<tamano;j++){
                if(tablero[i][j]==9){
                    minaAdyacente(tablero, i, j);
                }
            }
        }
    }

    //Se suma 1 a las casillas adyacentes a una mina
    private void minaAdyacente(int[][] tablero, int x, int y){

        //Primera fila
        if(x>0){
            if(tablero[x-1][y]!=9){                             //Centro
                tablero[x-1][y]++;
            }

            if(y>0 && tablero[x-1][y-1]!=9){                    //Izquierda
                tablero[x-1][y-1]++;
            }

            if(y<tablero.length-1 && tablero[x-1][y+1]!=9){     //Derecha
                tablero[x-1][y+1]++;
            }

        }

        //Segunda fila
        if(y>0 && tablero[x][y-1]!=9){                          //Izquierda
            tablero[x][y-1]++;
        }
        if(y<tablero.length-1 && tablero[x][y+1]!=9){           //Derecha
            tablero[x][y+1]++;
        }

        //Tercera fila
        if(x<tablero.length-1){     
            if(tablero[x+1][y]!=9){                             //Centro
                tablero[x+1][y]++;
            }

            if(y>0 && tablero[x+1][y-1]!=9){                    //Izquierda
                tablero[x+1][y-1]++;
            }

            if(y<tablero.length-1 && tablero[x+1][y+1]!=9){     //Derecha
                tablero[x+1][y+1]++;
            }

        }

    }

    
    //Descubre casillas adyacentes si estas a su vez no tienen minas adyacentes
    private void descubreSiVacias(int[][] tablero, int x, int y){

        //Primera fila
        if(x>0){
            if(descubiertas[x-1][y]==0){                             //Centro
                restantes--;
                descubiertas[x-1][y]=1;
                if(tablero[x-1][y]==0)
                    descubreSiVacias(tablero,x-1,y);
            }

            if(y>0 && descubiertas[x-1][y-1]==0){                    //Izquierda
                restantes--;
                descubiertas[x-1][y-1]=1;
                if(tablero[x-1][y-1]==0)
                    descubreSiVacias(tablero,x-1,y-1 );
            }

            if(y<tablero.length-1 && descubiertas[x-1][y+1]==0){     //Derecha
                restantes--;
                descubiertas[x-1][y+1]=1;
                if(tablero[x-1][y+1]==0)
                    descubreSiVacias(tablero, x-1,y+1);
            }

        }

        //Segunda fila
        if(y>0 && descubiertas[x][y-1]==0){                          //Izquierda
            restantes--;
            descubiertas[x][y-1]=1;
            if(tablero[x][y-1]==0)
                descubreSiVacias(tablero,x,y-1);
        }
        if(y<tablero.length-1 && descubiertas[x][y+1]==0){           //Derecha
            restantes--;
            descubiertas[x][y+1]=1;
            if(tablero[x][y+1]==0)
                descubreSiVacias(tablero, x,y+1);
        }

        //Tercera fila
        if(x<tablero.length-1){     
            if(descubiertas[x+1][y]==0){                             //Centro
                restantes--;
                descubiertas[x+1][y]=1;
                if(tablero[x+1][y]==0)
                    descubreSiVacias(tablero,x+1,y);
            }

            if(y>0 && descubiertas[x+1][y-1]==0){                    //Izquierda
                restantes--;
                descubiertas[x+1][y-1]=1;
                if(tablero[x+1][y-1]==0)
                    descubreSiVacias(tablero,x+1,y-1);
            }

            if(y<tablero.length-1 && descubiertas[x+1][y+1]==0){     //Derecha
                restantes--;
                descubiertas[x+1][y+1]=1;
                if(tablero[x+1][y+1]==0)
                    descubreSiVacias(tablero,x+1,y+1);
            }

        }

    }    

}
