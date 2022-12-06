import java.io.IOException;

public class Labels {

    public static void main(String[] args) throws IOException {
        Symbols syms = new Symbols();

        for (var sym : syms.symbols) {
            if (sym.name != null) {
                System.out.println(sym.name);
            }
        }
    }
}
