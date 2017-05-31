import org.junit.Test;

public class TestDimensionNumber {
    @Test
    public void testInit1() {
        DimensionNumber dn = new DimensionNumber(123.45, "kg");
        assert (dn.toString().equals("123.45(kg)"));
    }

    @Test
    public void testInit2() {
        DimensionNumber dn = new DimensionNumber(123.45);
        assert (dn.toString().equals("123.45"));
    }

    @Test
    public void testInit3() {
        DimensionNumber dn = new DimensionNumber("123.45kg");
        assert (dn.toString().equals("123.45(kg)"));
    }

    @Test
    public void testInit4() {
        DimensionNumber dn = new DimensionNumber(123.45, "m*kg/s");
        assert (dn.toString().equals("123.45(kg*m/s)"));
    }

    @Test
    public void testInit5() {
        DimensionNumber dn = new DimensionNumber(123.45, "kg*m/s");
        assert (dn.toString().equals("123.45(kg*m/s)"));
    }

    @Test
    public void testInit6() {
        DimensionNumber dn = new DimensionNumber(10, "1/s");
        assert (dn.toString().equals("10.0(1/s)"));
    }

    @Test
    public void testAdd() {
        DimensionNumber dn1 = new DimensionNumber(23.45, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(1000.55, "kg*m/s");
        dn1.add(dn2);
        assert (dn1.toString().equals("1024.0(kg*m/s)"));
    }

    @Test
    public void testSubtract() {
        DimensionNumber dn1 = new DimensionNumber(2024.55, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(1000.55, "kg*m/s");
        dn1.subtract(dn2);
        assert (dn1.toString().equals("1024.0(kg*m/s)"));
    }

    @Test
    public void testCompare() {
        DimensionNumber dn1 = new DimensionNumber(23.45, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(1000.55, "kg*m/s");
        assert dn1.compareTo(dn2) < 0;
    }

    @Test
    public void testCompareEquals() {
        DimensionNumber dn1 = new DimensionNumber(1000.55, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(1000.55, "kg*m/s");
        assert dn1.compareTo(dn2) == 0;
    }

    @Test
    public void testMultiply1() {
        DimensionNumber dn1 = new DimensionNumber(23.45, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(100);
        dn1.multiply(dn2);
        assert (dn1.toString().equals("2345.0(kg*m/s)"));
    }

    @Test
    public void testMultiply2() {
        DimensionNumber dn1 = new DimensionNumber(3, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(3, "s/m*kg");
        dn1.multiply(dn2);
        assert (dn1.toString().equals("9.0"));
    }

    @Test
    public void testMultiply3() {
        DimensionNumber dn1 = new DimensionNumber(23.45, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(100, "s");
        dn1.multiply(dn2);
        assert (dn1.toString().equals("2345.0(kg*m)"));
    }

    @Test
    public void testDivide1() {
        DimensionNumber dn1 = new DimensionNumber(10000, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(100);
        dn1.divide(dn2);
        assert (dn1.toString().equals("100.0(kg*m/s)"));
    }

    @Test
    public void testDivide2() {
        DimensionNumber dn1 = new DimensionNumber(10000, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(100, "kg*m/s");
        dn1.divide(dn2);
        assert (dn1.toString().equals("100.0"));
    }

    @Test
    public void testDivide3() {
        DimensionNumber dn1 = new DimensionNumber(10000, "kg*m/s");
        DimensionNumber dn2 = new DimensionNumber(100, "m*kg*m/s");
        dn1.divide(dn2);
        assert (dn1.toString().equals("100.0(1/m)"));
    }
}
