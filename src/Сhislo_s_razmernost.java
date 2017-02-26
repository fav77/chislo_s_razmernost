 public class Сhislo_s_razmernost {

    private int mant;
    private int pokaz;
    private final String razmer;

    public Сhislo_s_razmernost(int mant, int pokaz, String razmer) {
        this.mant = mant;     // Мантиса числа
        this.pokaz = pokaz;   // Показатель степени
        this.razmer = razmer; // Размерность
    }
    public int getMant(){
        return mant;
    }
    public int getPokaz(){
        return pokaz;
    }
    public String getRazmer(){
        return razmer;
    }
     public void setMant(int mant){
         this.mant = mant;
     }
     public void setPokaz(int pokaz){
         this.pokaz = pokaz;
     }
    private int stepen(int a,int b){
        int result = 1;
        for (int i = 0; i<b; i++) {
            result *= a;
        }
        return result;
    }


    public Сhislo_s_razmernost slozh(Сhislo_s_razmernost other) throws IllegalArgumentException {
        if (razmer == other.razmer) {
            Сhislo_s_razmernost result = new Сhislo_s_razmernost(mant, pokaz, razmer);
            if (pokaz == other.pokaz)
                result = new Сhislo_s_razmernost(mant + other.mant, pokaz, razmer);
            if (pokaz > other.pokaz) {
                result = new Сhislo_s_razmernost(mant * stepen(10,pokaz - other.pokaz) + other.mant, other.pokaz, razmer);
            }
            if (pokaz < other.pokaz) {
                result = new Сhislo_s_razmernost(other.mant * stepen(10,other.pokaz - pokaz) + mant, pokaz, razmer);
            }
            while (result.mant % 10 == 0){
                result.setPokaz(result.pokaz + 1);
                result.setMant(result.mant / 10);
            }
        }
        throw new IllegalArgumentException();
    }



     public Сhislo_s_razmernost vich(Сhislo_s_razmernost other) throws IllegalArgumentException {
         return this.slozh(new Сhislo_s_razmernost(-other.mant, pokaz, razmer));
     }
     public Сhislo_s_razmernost ymn(Сhislo_s_razmernost other) throws IllegalArgumentException {
         if




         while (result.mant % 10 == 0){
             result.setPokaz(result.pokaz + 1);
             result.setMant(result.mant / 10);
         }
     }

}
