import java.util.*;

public class Сhislo_s_razmernost {

    private double mant;
    private double pokaz;
    private List<String> chisl;
    private List<String> znam;

    public Сhislo_s_razmernost(double mant, double pokaz, List<String> chisl, List<String> znam ) {
        this.mant = mant;     // Мантиса числа
        this.pokaz = pokaz;   // Показатель степени
        this.chisl = chisl;   // Числитель размерности
        this.znam = znam;     // Знаменатель размерности
    }
    public double getMant(){
        return mant;
    }
    public double getPokaz(){
        return pokaz;
    }
    public List<String> getZnam(){
        return znam;
    }
    public List<String> getChisl(){
        return chisl;
    }
     public void setMant(int mant){
         this.mant = mant;
     }
     public void setPokaz(int pokaz){
         this.pokaz = pokaz;
     }
    public int stepen(double a,double b){
        int result = 1;
        for (int i = 0; i<b; i++) {
            result *= a;
        }
        return result;
    }
    public Сhislo_s_razmernost peren(Сhislo_s_razmernost m){ // Перенос десятки из мантисы в показатель числа
            int count = 0;
            double newMant = m.mant;
        while (newMant % 10 == 0 ){
            count ++;
            newMant /= 10;
        }
        return new Сhislo_s_razmernost(newMant, pokaz + count, this.chisl, this.znam );
    }

    public Сhislo_s_razmernost perendel(Сhislo_s_razmernost m){
        int count = 0;
        double newMant = m.mant;
        while (newMant < 1){
            count ++;
            newMant *= 10;
        }
        return new Сhislo_s_razmernost(newMant, pokaz - count, this.chisl, this.znam);
    }


    public Сhislo_s_razmernost sokr (Сhislo_s_razmernost n){ // Сокращение числителя и знаменателя в размерности
        if (n.chisl.isEmpty() || n.znam.isEmpty()){
            return this;
        }

        else {
            for (String ellemChisl: chisl){
                for (String ellemZnam: znam){
                    if (ellemChisl.equals(ellemZnam)){
                        n.chisl.remove(ellemChisl);
                        n.znam.remove(ellemZnam);
                    }
                }
            }
        }
        return this;
    }

    public Сhislo_s_razmernost sorted (){
        Collections.sort(this.chisl);
        Collections.sort(this.znam) ;
        return this;
    }


    public Сhislo_s_razmernost slozh(Сhislo_s_razmernost other) throws IllegalArgumentException {//Сложение
        other.sorted();
        if (((chisl.equals(other.chisl) && (znam.equals(other.znam))))){
            if (pokaz == other.pokaz) {
                Сhislo_s_razmernost  result = new Сhislo_s_razmernost(mant + other.mant, pokaz, chisl, znam);
                return peren(result);
            }
            if (pokaz > other.pokaz) {
                Сhislo_s_razmernost result = new Сhislo_s_razmernost(mant * stepen(10,pokaz - other.pokaz) + other.mant, other.pokaz, chisl, znam);
                return peren(result);
            }
            if (pokaz < other.pokaz) {
                Сhislo_s_razmernost result = new Сhislo_s_razmernost(other.mant * stepen(10,other.pokaz - pokaz) + mant, pokaz, chisl, znam);
                return peren(result);
            }

        }
        throw new IllegalArgumentException();
    }



     public Сhislo_s_razmernost vich(Сhislo_s_razmernost other) throws IllegalArgumentException { //Вычетание
         return this.slozh(new Сhislo_s_razmernost(-other.mant, pokaz, chisl, znam));
     }


     public Сhislo_s_razmernost ymn(Сhislo_s_razmernost other) {  //Умножение
         ArrayList<String> newChisl = new ArrayList<String>();
         newChisl.addAll(chisl);
         newChisl.addAll(other.chisl);
         ArrayList<String> newZnam = new ArrayList<String>();
         newZnam.addAll(znam);
         newZnam.addAll(other.znam);
         Сhislo_s_razmernost result = new Сhislo_s_razmernost(mant * other.mant, pokaz + other.pokaz, newChisl, newZnam);
         result.sorted();
         return peren(sokr(result));
     }


     public Сhislo_s_razmernost del(Сhislo_s_razmernost other) { // Деление
         ArrayList<String> newChisl = new ArrayList<String>();
         newChisl.addAll(chisl);
         newChisl.addAll(other.chisl);
         ArrayList<String> newZnam = new ArrayList<String>();
         newZnam.addAll(znam);
         newZnam.addAll(other.znam);
         Сhislo_s_razmernost result = new Сhislo_s_razmernost(mant / other.mant, pokaz - other.pokaz, newChisl, newZnam );
         result.sorted();
         return peren(sokr(result));
     }

     public String sravn(Сhislo_s_razmernost other) {
         Сhislo_s_razmernost k = this.vich(other);
         if (k.mant < 0){
             return  "Правое число больше";
         }
         if (k.mant > 0){
             return  "Левое число больше";
         }
         else return "Числа равны";
     }

    @Override
    public boolean equals(Object object) {
       return  (this.mant == mant && this.znam == znam && this.chisl.toString() == chisl.toString() && this.znam.toString() == znam.toString());
    }

    public boolean equals(List<String> n) {
        return  (this.toString() == n.toString());
    }

}
