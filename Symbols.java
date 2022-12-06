import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

enum SymbolType {
    FUNC,
    FILE,
    OBJECT,
    SECTION,
    NOTYPE
}

enum SymbolScope {
    WEAK,
    LOCAL,
    GLOBAL,
    NOSCOPE
}

class Symbol {

    public int id;
    public long address;
    public int size;
    public SymbolType type;
    public SymbolScope scope;
    public String section;
    public String name;

    Symbol(String line) {
        var data = line.trim().split("\\s+");
        this.id = Integer.parseInt(data[0].replace(":", ""));
        this.address = Long.valueOf(data[1].toUpperCase(), 16);
        this.size = this.parseSize(data[2]);
        this.type = this.parseType(data[3]);
        this.scope = this.parseScope(data[4]);
        this.section = data[6];
        this.name = (data.length > 7) ? data[7] : null;
    }

    private int parseSize(String size) {
        var isHex = size.contains("x") ? true : false;
        size = (isHex) ? size.replace("0x", "") : size;
        return Integer.parseInt(size, isHex ? 16 : 10);
    }

    private SymbolScope parseScope(String scope) {
        switch (scope) {
            case "WEAK":
                return SymbolScope.WEAK;
            case "LOCAL":
                return SymbolScope.LOCAL;
            case "GLOBAL":
                return SymbolScope.GLOBAL;
            default:
                return SymbolScope.NOSCOPE;
        }
    }

    private SymbolType parseType(String type) {
        switch (type) {
            case "FUNC":
                return SymbolType.FUNC;
            case "SECTION":
                return SymbolType.SECTION;
            case "OBJECT":
                return SymbolType.OBJECT;
            case "FILE":
                return SymbolType.FILE;
            default:
                return SymbolType.NOTYPE;
        }
    }
}

public class Symbols {

    public List<Symbol> symbols = new ArrayList<Symbol>();

    Symbols() throws IOException {
        var path = Path.of("../bfbb/symbols.txt");
        var text = Files.readString(path);

        text.lines().forEach(line -> {
            symbols.add(new Symbol(line));
        });
    }
}