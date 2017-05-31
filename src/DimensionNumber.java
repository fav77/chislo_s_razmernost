import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
Класс DimensionNumber - основной. Внутри него есть класс Dimension - класс размерности (за bonus points, как сказано
в задании).
Размерность задается как последовательность букв, разделенных символом умножения(*). Так же в последовательности букв
может быть одно деление(/), если это дробь. Еще размерность может задаваться в виде 1/... (если в числителе ничего нет).
Примеры задания размерностей смотри в тестах testInit#.
При выводе размерность выводится в скобочках.
 */

public class DimensionNumber implements Comparable<DimensionNumber> {
    private double number;
    private Dimension dim;

    public DimensionNumber(double number, String dim) {
        this.number = number;
        try {
            this.dim = new Dimension(dim);
        } catch (Exception e) {
            System.out.println("Неправильно задана размерность");
            System.exit(0);
        }
    }

    public DimensionNumber(double number) {
        this(number, null);
    }

    public DimensionNumber(String str) {
        String s = str.toLowerCase();
        //выделение при помощи регулярного выражения числа из строки. Тут строка делится на массив строк по разделителю - любой букве.
        //Мы из массива берем только первую строку - число.
        String num = s.split("[a-z]")[0];
        String dim = s.substring(num.length(), s.length());
        try {
            number = new Double(num);
        } catch (Exception e) {
            System.out.println("Неправильно задано число");
            System.exit(0);
        }
        try {
            this.dim = new Dimension(dim);
        } catch (Exception e) {
            System.out.println("Неправильно задана размерность");
            System.exit(0);
        }
    }

    public void add(DimensionNumber other) {
        if (!isDimensionsEquals(other)) throw new UnsupportedOperationException("Размерности не совпадают");
        number += other.number;
    }

    public void subtract(DimensionNumber other) {
        if (!isDimensionsEquals(other)) throw new UnsupportedOperationException("Размерности не совпадают");
        number -= other.number;
    }

    public void multiply(DimensionNumber other) {
        number *= other.number;
        dim.multiply(other.dim);
    }

    public void divide(DimensionNumber other) {
        number /= other.number;
        dim.divide(other.dim);
    }

    private boolean isDimensionsEquals(DimensionNumber other) {
        return dim.getDim().equals(other.dim.getDim());
    }

    @Override
    public int compareTo(DimensionNumber other) {
        if (!isDimensionsEquals(other)) throw new UnsupportedOperationException("Размерности не совпадают");
        return Double.compare(number, other.number);
    }

    @Override
    public String toString() {
        return number + dim.toString();
    }

    static class Dimension {
        //из википедии
        private static final String[] DIMENSIONS = {"m", "kg", "s", "a", "k", "mol", "cd"};
        private String dim;

        Dimension(String dim) {
            //без размерности
            if (dim == null) {
                this.dim = "";
            } else {
                if (!isCorrect(dim)) throw new IllegalArgumentException("Неправильно указана размерность");
                this.dim = dim;
            }
            //приводим к стандартному виду
            standard();
        }

        void multiply(Dimension other) {
            //в случае, если размерность - дробь, надо умножать ее особым образом
            int i = dim.indexOf('/');
            if (i == -1) {
                dim = dim + "*" + other.getDim();
            } else {
                int j = other.getDim().indexOf('/');
                if (j == -1) {
                    dim = dim.substring(0, i) + "*" + other.getDim() + dim.substring(i, dim.length());
                } else {
                    dim = dim.substring(0, i) + "*" + other.getDim().substring(0, j) +
                            dim.substring(i, dim.length()) + "*" + other.getDim().substring(j + 1, other.getDim().length());
                }
            }
            standard();
        }

        void divide(Dimension other) {
            int i = dim.indexOf('/');
            int j = other.getDim().indexOf('/');
            if (i == -1) {
                if (dim.equals("")) {
                    dim = "1";
                }
                if (j == -1) {
                    dim = dim + "/" + other.getDim();
                } else {
                    dim = dim + "*" + other.getDim().substring(j + 1, other.getDim().length()) + "/" + other.getDim().substring(0, j);
                }
            } else {
                if (j == -1) {
                    dim = dim + "*" + other.getDim();
                } else {
                    dim = dim.substring(0, i) + "*" + other.getDim().substring(j + 1, other.getDim().length()) +
                            dim.substring(i, dim.length()) + "*" + other.getDim().substring(0, j);
                }
            }
            standard();
        }

        @Override
        public String toString() {
            if (dim.equals("")) {
                return "";
            }
            return "(" + dim + ")";
        }

        private static boolean isCorrect(String str) {
            //без размерности
            if (str.equals("")) {
                return true;
            }
            String s = str.toLowerCase();
            //ругялярные выражения для всех типов допустимых размерностей
            if (!(s.matches("([a-z]+\\*?)+(/([a-z]+\\*?)+)?") || s.matches("1/[a-z]+\\*?"))) {
                return false;
            }
            String[] atoms = s.split("[*/]");
            int i = 0;
            if (atoms[0].equals("1")) {
                if (atoms.length == 1) {
                    return false;
                }
                i++;
            }
            //проверка всех составляющих размерности на равенство чему-либо из СИ
            for (; i < atoms.length; i++) {
                String atom = atoms[i];
                boolean isCorrectDimension = false;
                for (String atom2 : DIMENSIONS) {
                    if (atom.equals(atom2)) {
                        isCorrectDimension = true;
                        break;
                    }
                }
                if (!isCorrectDimension) {
                    return false;
                }
            }
            return true;
        }

        private String getDim() {
            return dim;
        }

        //сокращение дроби, при наличии одинаковых множителей в числителе и знаменателе
        private void cut() {
            int i = dim.indexOf('/');
            if (i != -1) {
                String chisl = dim.substring(0, i);
                String znam = dim.substring(i + 1, dim.length());
                ArrayList<String> chislAtoms = new ArrayList<>(Arrays.asList(chisl.split("\\*")));
                ArrayList<String> znamAtoms = new ArrayList<>(Arrays.asList(znam.split("\\*")));
                int j = 0;
                while (j < znamAtoms.size()) {
                    String atom = znamAtoms.get(j);
                    if (chislAtoms.remove(atom)) {
                        znamAtoms.remove(atom);
                    } else {
                        j++;
                    }
                }
                StringBuilder str = new StringBuilder();
                if (chislAtoms.size() == 0) {
                    if (znamAtoms.size() == 0) {
                        dim = "";
                        return;
                    }
                    str.append("1");
                } else {
                    for (String s : chislAtoms) {
                        str.append(s + "*");
                    }
                    str.deleteCharAt(str.length() - 1);
                }
                if (znamAtoms.size() != 0) {
                    str.append("/");
                    for (String s : znamAtoms) {
                        str.append(s + "*");
                    }
                    str.deleteCharAt(str.length() - 1);
                }

                dim = str.toString();
            }
        }

        //соритровка размерностей, чтоб сравнивать было легче. Да и визуально лучше выглядит так
        //заодно и cut() здесь делается
        private void standard() {
            cut();
            int i = dim.indexOf('/');
            StringBuilder str = new StringBuilder();
            if (i == -1) {
                ArrayList<String> atoms = new ArrayList<>(Arrays.asList(dim.split("\\*")));
                Collections.sort(atoms);
                for (String atom : atoms) {
                    str.append(atom + "*");
                }
                str.deleteCharAt(str.length() - 1);
            } else {
                String chisl = dim.substring(0, i);
                String znam = dim.substring(i + 1, dim.length());
                if (chisl.equals("1")) {
                    str.append("1");
                } else {
                    ArrayList<String> atoms = new ArrayList<>(Arrays.asList(chisl.split("\\*")));
                    Collections.sort(atoms);
                    for (String atom : atoms) {
                        str.append(atom + "*");
                    }
                    str.deleteCharAt(str.length() - 1);
                }
                str.append("/");
                ArrayList<String> atoms = new ArrayList<>(Arrays.asList(znam.split("\\*")));
                Collections.sort(atoms);
                for (String atom : atoms) {
                    str.append(atom + "*");
                }
                str.deleteCharAt(str.length() - 1);
            }
            dim = str.toString();
        }
    }
}
