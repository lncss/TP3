class Celula {
    public int elemento;
    public Celula inf, sup, esq, dir;
 
    public Celula(){
       this(0, null, null, null, null);
    }
 
    public Celula(int elemento){
       this(elemento, null, null, null, null);
    }
 
    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
       this.elemento = elemento;
       this.inf = inf;
       this.sup = sup;
       this.esq = esq;
       this.dir = dir;
    }
 }

public class Matriz {
    private Celula inicio;
    private int linha, coluna;
 
    public Matriz (){
        this(3, 3);
    }
 
    public Matriz (int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
 
        //alocar a matriz com this.linha linhas e this.coluna colunas

        this.inicio = new Celula(); // criar a primeira celula

        //criar a coluna na qual a primeira celula esta localizada
        Celula temp = this.inicio;

        for(int i = 1; i < this.linha; i++){
           temp.inf = new Celula();
           temp.inf.sup = temp;
           temp = temp.inf;
        }

        temp = null;

        //criar as demais linhas e colunas da matriz

        for(int j  = 0; j < this.linha; j++){
            //obter o valor de temp
            temp = this.inicio;
            for(int k = 0; k < j; k++){
                temp = temp.inf;
            }

            //criar celulas a direita da matriz
            for(int y = 1; y < this.coluna; y++){
                temp.dir = new Celula();
                temp.dir.esq = temp;
                temp = temp.dir;
            }
        }

        temp = null;

        //conectar a primeira celula com a segunda celula
        if(this.linha > 1){
            temp = this.inicio.inf;
            for(int i = 1; i < this.linha; i++){
                Celula c = temp.sup; //ponteiro superior
                Celula d = temp; //ponteiro inferior

                //caminhar pela matriz conectando as celulas
                while(c != null && d != null){
                    c.inf = d;
                    d.sup = c;

                    c = c.dir;
                    d = d.dir;
                }

                temp = temp.inf;
            }
        }
    }//fim do construtor

    public Matriz soma(Matriz x)throws Exception{
        Matriz resp = new Matriz(x.linha,x.coluna);
        Celula tmp = resp.inicio;
        for(int i = 0; i < this.linha; i++){
            Celula tmp2 = tmp;
            for(int j = 0; j < this.coluna; j++){
                tmp2.elemento = somar(this,x,j,i);
                tmp2 = tmp2.dir;
            }

            tmp = tmp.inf;
        }

        return resp;
    }

    private int somar(Matriz a, Matriz b,int x, int y){
        int soma;
        Celula c = a.inicio;
        Celula d = b.inicio;

        for(int i = 0; i < x; i++){
            c = c.dir;
            d = d.dir;
        }

        for(int i = 0; i < y; i++){
            c = c.inf;
            d = d.inf;
        }

        soma = c.elemento + d.elemento;

        return soma;
    }

    public Matriz multiplicacao (Matriz m)throws Exception{
        Matriz resp = new Matriz(m.linha, m.coluna);
        Celula a = resp.inicio;
        int i = 0;
        while(a != null && i < this.linha){
            Celula b = a;
            int j = 0;
            while(b != null && i < this.coluna){
                b.elemento = multiplicar(this, m, i, j);
                b = b.dir;
                j++;
            }

            a = a.inf;
            i++;
        }
       return resp;
    }

    private int multiplicar(Matriz a, Matriz b, int x, int y){
        int multiplicacao = 0;
        Celula c = a.inicio;
        Celula d = b.inicio;

        for(int i = 0; i < x; i++){
            c = c.inf;
        }

        for(int i = 0; i < y; i++){
            d = d.dir;
        }

        int e, f;
        while(c != null && d != null){
            e = c.elemento;
            f = d.elemento;
            multiplicacao += e * f;
            c = c.dir;
            d = d.inf;
        }

        return multiplicacao;
    }
 
 
    public void mostrarDiagonalPrincipal (){
        if(this.linha == this.coluna){
            Celula i = inicio;
            while(i != null){
                MyIO.print(i.elemento + " ");

                i = i.dir;
                if(i != null){
                    i = i.inf;
                }
            }    
            MyIO.println("");
        }
    }
 
    public void mostrarDiagonalSecundaria (){
        if(this.linha == this.coluna){
            Celula i;

            for(i = inicio; i.dir != null; i = i.dir);

            while(i != null){
                MyIO.print(i.elemento + " ");
                i = i.inf;
                if(i != null){
                    i = i.esq;
                }
            }

            MyIO.println("");
        }
    }

    public void mostrar(){
        for(Celula i = inicio; i != null; i = i.inf){
            for(Celula j = i; j != null; j = j.dir){
                MyIO.println(j.elemento + " ");
            }
            MyIO.println("");
        }
    }

    public void leMatriz(){
        for(Celula a = this.inicio; a != null; a = a.inf){
            for(Celula b = a; b != null; b = b.dir){
                b.elemento = MyIO.readInt();
            }
        }
    }  
    
    public static void main(String[] args)throws Exception{
        int numOperacoes = MyIO.readInt();
        for(int i = 0; i < numOperacoes; i++){
            //Matriz 1
            int linha = MyIO.readInt();
            int coluna = MyIO.readInt();
            Matriz matriz1  = new Matriz(linha, coluna);
            for (Celula aux = matriz1.inicio; aux != null; aux = aux.inf) {
                for (Celula aux2 = aux; aux2 != null; aux2 = aux2.dir) {
                    aux2.elemento = MyIO.readInt();
                }
            }

            //Matriz 2
            linha = MyIO.readInt();
            coluna = MyIO.readInt();
            Matriz matriz2 = new Matriz(linha, coluna);
            for (Celula aux = matriz2.inicio; aux != null; aux = aux.inf) {
                for (Celula aux2 = aux; aux2 != null; aux2 = aux2.dir) {
                    aux2.elemento = MyIO.readInt();
                }
            }
            
            //soma
            Matriz soma = matriz1.soma(matriz2);

            //multiplicacao
            Matriz multiplicacao = matriz1.multiplicacao(matriz2);

            //imprimir
            matriz1.mostrarDiagonalPrincipal();
            matriz1.mostrarDiagonalSecundaria();

            for (Celula aux = soma.inicio; aux != null; aux = aux.inf) {
                for (Celula aux2 = aux; aux2 != null; aux2 = aux2.dir) {
                    MyIO.print(aux2.elemento + " ");
                }
                MyIO.println("");
            }

            for (Celula aux = multiplicacao.inicio; aux != null; aux = aux.inf) {
                for (Celula aux2 = aux; aux2 != null; aux2 = aux2.dir) {
                    MyIO.print(aux2.elemento + " ");
                }
                MyIO.println("");
            }
        }
    }
}



