
import org.junit.Test;
import org.junit.Before;

import java.util.*;

import static org.junit.Assert.*;

public class test {
    List<String> chisl1 = Arrays.asList("км","с");
    List<String> znam1 = Arrays.asList("км");
    List<String> chisl2 = Arrays.asList("км","с");
    List<String> znam2 = Arrays.asList("км");
    List<String> chisl3 = Arrays.asList("км","с","с","км");
    Сhislo_s_razmernost n4 = new Сhislo_s_razmernost(5.0, 0.0, chisl3, znam1);
    Сhislo_s_razmernost n1 = new Сhislo_s_razmernost(2.0, 0.0, chisl1, znam1);
    Сhislo_s_razmernost n2 = new Сhislo_s_razmernost(3.0, 0.0, chisl2, znam2);
    Сhislo_s_razmernost n3 = new Сhislo_s_razmernost(5.0, 0.0, chisl1, znam1);


    @Test
    public void sloz(){
        assertEquals(n3, n1.slozh(n2));
    }

    @Test
    public void sortd(){
        assertEquals(0, n4.sorted());
    }
}
